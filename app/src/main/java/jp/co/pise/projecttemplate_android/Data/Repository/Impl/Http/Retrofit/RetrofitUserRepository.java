package jp.co.pise.projecttemplate_android.Data.Repository.Impl.Http.Retrofit;

import java.util.List;

import jp.co.pise.projecttemplate_android.Data.Entities.PokemonEntity;
import jp.co.pise.projecttemplate_android.Data.Entities.UserEntity;
import jp.co.pise.projecttemplate_android.Data.Repository.IUserRepository;
import jp.co.pise.projecttemplate_android.Data.Repository.Impl.RepositoryConditions;

public class RetrofitUserRepository extends RetrofitRepository<UserEntity>  implements IUserRepository {

    @Override
    public List<UserEntity> FindAll() {
        return null;
    }

    @Override
    public io.reactivex.Observable<PokemonEntity> FirstOrDefault(List<RepositoryConditions> conditions) {
        return null;
    }

    @Override
    public List<UserEntity> Where(List<RepositoryConditions> conditions) {
        return null;
    }

    @Override
    public void Delete(UserEntity entity) {

    }

    @Override
    public void DeleteRange(List<UserEntity> userEntities) {

    }

    @Override
    public void Update(UserEntity entity) {

    }

    @Override
    public void Insert(UserEntity entity) {

    }

    @Override
    public void InsertOrUpdate(UserEntity entity) {

    }

    @Override
    public int ExecSqlCommand(String sql, Object[] parameters) {
        return 0;
    }
}
