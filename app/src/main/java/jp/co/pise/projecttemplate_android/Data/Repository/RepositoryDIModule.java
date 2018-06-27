package jp.co.pise.projecttemplate_android.Data.Repository;

import dagger.Module;
import dagger.Provides;
import jp.co.pise.projecttemplate_android.Data.Repository.Impl.Http.Retrofit.RetrofitPokemonRepository;
import jp.co.pise.projecttemplate_android.Data.Repository.Impl.Http.Retrofit.RetrofitUserRepository;
import jp.co.pise.projecttemplate_android.Data.Repository.Impl.Orm.Orma.OrmaUserRepository;

/**
 * DIする為のクラス
 */
@Module
public class RepositoryDIModule {
    @Provides
    public IUserRepository provideUserRepository(){
        return new OrmaUserRepository();
        // Retorifを使いたい場合はここを切り替える
        // return new RetrofitUserRepository();
    }

    @Provides
    public IPokemonRepository providePokemonRepository(){
        return new RetrofitPokemonRepository();
        // Retorifを使いたい場合はここを切り替える
        // return new RetrofitUserRepository();
    }
}
