package jp.co.pise.projecttemplate_android.Domain.UseCases;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import jp.co.pise.projecttemplate_android.Data.Definitions.Operator;
import jp.co.pise.projecttemplate_android.Data.Definitions.QueryType;
import jp.co.pise.projecttemplate_android.Data.Entities.PokemonEntity;
import jp.co.pise.projecttemplate_android.Data.Entities.UserEntity;
import jp.co.pise.projecttemplate_android.Data.Repository.DaggerRepositoryDIConponent;
import jp.co.pise.projecttemplate_android.Data.Repository.IPokemonRepository;
import jp.co.pise.projecttemplate_android.Data.Repository.IUserRepository;
import jp.co.pise.projecttemplate_android.Data.Repository.Impl.Orm.Orma.OrmaPokemonRepository;
import jp.co.pise.projecttemplate_android.Data.Repository.Impl.RepositoryConditions;

public class PokemonManager {

    @Inject
    IPokemonRepository pokemonRepository;

    OrmaPokemonRepository ormaPokemonRepository;

    public PokemonManager(){
        pokemonRepository = DaggerRepositoryDIConponent.create().PokemonRepository();
        ormaPokemonRepository = new OrmaPokemonRepository();
    }

//    public void RegistUser(UserEntity entity)
//    {
//        userRepository.Insert(entity);
//    }

    public Observable<PokemonEntity> getPokemon(String id) {

        RepositoryConditions conditions = new RepositoryConditions("id", id, Operator.EQUAL, QueryType.AND);
        List<RepositoryConditions> conditionsList = new ArrayList<RepositoryConditions>();
        conditionsList.add(conditions);
        Observable<PokemonEntity> observable = ormaPokemonRepository.FirstOrDefault(conditionsList);

        //In case cannot load from database, call api
        if (observable == null) {
            observable = pokemonRepository.FirstOrDefault(conditionsList);
            observable.map(new Function<PokemonEntity, PokemonEntity>() {
                @Override
                public PokemonEntity apply(PokemonEntity pokemonEntity) throws Exception {
                    ormaPokemonRepository.InsertOrUpdate(pokemonEntity);
                    return pokemonEntity;
                }
            });
        }
        return  observable;
    }
}
