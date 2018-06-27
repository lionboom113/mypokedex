package jp.co.pise.projecttemplate_android.Data.Repository.Impl.Orm.Orma;

import java.util.List;

import jp.co.pise.projecttemplate_android.Data.Entities.PokemonEntity;
import jp.co.pise.projecttemplate_android.Data.Entities.UserEntity;
import jp.co.pise.projecttemplate_android.Data.Repository.IUserRepository;
import jp.co.pise.projecttemplate_android.Data.Repository.Impl.RepositoryConditions;

/**
 * Entityへのアクセスが役割
 * 使用者から、DBなのか、APIなのかを隠すためにRepositoryが存在している
 * 今回はDB（ORMAライブラリ）を使用している
 * APIや別のライブラリに変更する場合は、別クラスを作成する必要がある
 */
public class OrmaUserRepository extends OrmaRepository<UserEntity> implements IUserRepository {

    @Override
    public List<UserEntity> FindAll() {
        return orma.selectFromUserEntity().toList();
    }

    @Override
    public io.reactivex.Observable<PokemonEntity> FirstOrDefault(List<RepositoryConditions> conditions) {
        List<UserEntity> users = super.Where(orma.selectFromUserEntity(), conditions);
        return null;
    }

    @Override
    public List<UserEntity> Where(List<RepositoryConditions> conditions) {
        return super.Where(orma.selectFromUserEntity(), conditions);
    }

    @Override
    public void Delete(UserEntity userEntity) {
        orma.deleteFromUserEntity().IdEq(userEntity.Id).execute();
    }

    @Override
    public void DeleteRange(List<UserEntity> userEntities) {
        for(UserEntity entity : userEntities)
        {
            orma.deleteFromUserEntity().IdEq(entity.Id).execute();
        }
    }

    @Override
    public void Update(UserEntity userEntity) {
        orma.updateUserEntity().IdEq(userEntity.Id).Name("なまえ").execute();
    }

    @Override
    public void Insert(UserEntity userEntity) {
        orma.insertIntoUserEntity(userEntity);
    }

    @Override
    public void InsertOrUpdate(UserEntity userEntity) {
        List<UserEntity> users = orma.selectFromUserEntity().IdEq(userEntity.Id).toList();
        if(users == null || users.size() < 1)
        {
            Insert(userEntity);
        }
        else
        {
            Update(userEntity);
        }
    }

    @Override
    public int ExecSqlCommand(String sql, Object[] parameters) {
        return 0;
    }
}
