package jp.co.pise.projecttemplate_android.Data.Repository.Impl.Orm.Orma;

import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import com.github.gfx.android.orma.Inserter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import jp.co.pise.projecttemplate_android.Data.Entities.PokemonEntity;
import jp.co.pise.projecttemplate_android.Data.Entities.PokemonTypeEntity;
import jp.co.pise.projecttemplate_android.Data.Entities.TypeEntity;
import jp.co.pise.projecttemplate_android.Data.Entities.UserEntity;
import jp.co.pise.projecttemplate_android.Data.Repository.IPokemonRepository;
import jp.co.pise.projecttemplate_android.Data.Repository.Impl.RepositoryConditions;

/**
 * Entityへのアクセスが役割
 * 使用者から、DBなのか、APIなのかを隠すためにRepositoryが存在している
 * 今回はDB（ORMAライブラリ）を使用している
 * APIや別のライブラリに変更する場合は、別クラスを作成する必要がある
 */
public class OrmaPokemonRepository extends OrmaRepository<PokemonEntity> implements IPokemonRepository {

    @Override
    public List<PokemonEntity> FindAll() {
        return orma.selectFromPokemonEntity().toList();
    }

    @Override
    public io.reactivex.Observable<PokemonEntity> FirstOrDefault(List<RepositoryConditions> conditions) {
        List<PokemonEntity> pokemonEntities = super.Where(orma.selectFromPokemonEntity(), conditions);
        if (pokemonEntities.size() > 0) {
            PokemonEntity pokemonEntity = pokemonEntities.get(0);

            //decode image
            String base64Str = pokemonEntity.imageEncoded;
            byte[] decodedBytes = Base64.decode(
                    base64Str.substring(base64Str.indexOf(",")  + 1),
                    Base64.DEFAULT
            );

            pokemonEntity.image = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
            //Load types data in objs
            for (PokemonTypeEntity potyE: orma.selectFromPokemonTypeEntity().toList()) {
                if (potyE.pokemonEntity.id == pokemonEntity.id) {
                    pokemonEntity.types.add(potyE.typeEntity);
                    potyE.typeEntity.inflateData();
                }
            }
            return Observable.fromIterable(pokemonEntities);
        }
        return null;

    }

    @Override
    public List<PokemonEntity> Where(List<RepositoryConditions> conditions) {
        return super.Where(orma.selectFromPokemonEntity(), conditions);
    }

    public Observable<PokemonEntity> WhereObservable(List<RepositoryConditions> conditions) {
        List<PokemonEntity> results = this.Where(orma.selectFromPokemonEntity(), conditions);
        return Observable.fromIterable(results).map(new Function<PokemonEntity, PokemonEntity>() {
            @Override
            public PokemonEntity apply(PokemonEntity pokemonEntity) throws Exception {
                //decode image
                String base64Str = pokemonEntity.imageEncoded;
                byte[] decodedBytes = Base64.decode(
                        base64Str.substring(base64Str.indexOf(",")  + 1),
                        Base64.DEFAULT
                );

                pokemonEntity.image = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
                //Load types data in objs
                for (PokemonTypeEntity potyE: orma.selectFromPokemonTypeEntity().toList()) {
                    if (potyE.pokemonEntity.id == pokemonEntity.id) {
                        pokemonEntity.types.add(potyE.typeEntity);
                        potyE.typeEntity.inflateData();
                    }
                }
                return pokemonEntity;
            }
        });
    }

    @Override
    public void Delete(PokemonEntity userEntity) {

    }

    @Override
    public void DeleteRange(List<PokemonEntity> userEntities) {
        for(PokemonEntity entity : userEntities)
        {
            orma.deleteFromUserEntity().IdEq(entity.id).execute();
        }
    }

    @Override
    public void Update(PokemonEntity userEntity) {
        orma.updateUserEntity().IdEq(userEntity.id).Name("なまえ").execute();
    }

    @Override
    public void Insert(PokemonEntity userEntity) {
        //orma.insertIntoPokemonEntity(userEntity);
        orma.transactionNonExclusiveSync(() -> {
            Inserter<PokemonEntity> inserter = orma.prepareInsertIntoPokemonEntity();
            inserter.execute(userEntity);
            Log.e("---","perform persist " + userEntity.name + " with id = " + userEntity.getId());

        for (TypeEntity te: userEntity.getTypes()) {
            if (orma.selectFromTypeEntity().typeNameEq(te.type.name).toList().size() == 0) {
                //type not in db, add type

                //In case load from api, refill data to save to db
                te.inflateData();
                    Inserter<TypeEntity> inserter2 = orma.prepareInsertIntoTypeEntity();
                    inserter2.execute(te);

                //orma.insertIntoTypeEntity(te);
            }
            MapPokeType(userEntity, te);
        }
        });
    }

    public void MapPokeType(PokemonEntity pokemonEntity, TypeEntity typeEntity) {

        if (typeEntity.typeName == null) {
            typeEntity.typeName = typeEntity.type.name;
            typeEntity.typeUrl = typeEntity.type.url;
        }
        PokemonTypeEntity pokemonTypeEntity = new PokemonTypeEntity();
        pokemonTypeEntity.pokemonEntity = pokemonEntity;
        pokemonTypeEntity.typeEntity = typeEntity;
        pokemonTypeEntity.typeSlot = typeEntity.slot;

        Log.e("debug","trying map " + pokemonEntity.name + " with id = " + pokemonEntity.id + " to type " + typeEntity.typeName);
        orma.insertIntoPokemonTypeEntity(pokemonTypeEntity);
    }

    @Override
    public void InsertOrUpdate(PokemonEntity userEntity) {
        List<PokemonEntity> users = orma.selectFromPokemonEntity().nameEq(userEntity.name).toList();
        if(users == null || users.size() < 1)
        {
            Insert(userEntity);
        }
        else
        {
            Log.e("Fallback;", "update is cancel for " + userEntity.name);
            //Update(userEntity);
        }
    }

    @Override
    public int ExecSqlCommand(String sql, Object[] parameters) {
        return 0;
    }
}
