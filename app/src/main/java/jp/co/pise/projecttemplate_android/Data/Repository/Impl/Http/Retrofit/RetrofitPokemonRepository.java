package jp.co.pise.projecttemplate_android.Data.Repository.Impl.Http.Retrofit;

import java.util.List;


import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import jp.co.pise.projecttemplate_android.Data.Entities.PokemonEntity;
import jp.co.pise.projecttemplate_android.Data.Repository.IPokemonRepository;
import jp.co.pise.projecttemplate_android.Data.Repository.Impl.Http.Retrofit.RetrofitService.IPokemonService;
import jp.co.pise.projecttemplate_android.Data.Repository.Impl.RepositoryConditions;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitPokemonRepository extends RetrofitRepository<PokemonEntity>  implements IPokemonRepository {

    @Override
    public List<PokemonEntity> FindAll() {
        return null;
    }

    @Override
    public Observable<PokemonEntity> FirstOrDefault(List<RepositoryConditions> conditions) {

        RxJava2CallAdapterFactory rxAdapter = RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(rxAdapter)
                .build();

        IPokemonService service = retrofit.create(IPokemonService.class);

        Observable<PokemonEntity> result = service.getPoke(conditions.get(0).value);
        return result;
    }

    @Override
    public List<PokemonEntity> Where(List<RepositoryConditions> conditions) {
        return null;
    }

    @Override
    public void Delete(PokemonEntity entity) {

    }

    @Override
    public void DeleteRange(List<PokemonEntity> userEntities) {

    }

    @Override
    public void Update(PokemonEntity entity) {

    }

    @Override
    public void Insert(PokemonEntity entity) {

    }

    @Override
    public void InsertOrUpdate(PokemonEntity entity) {

    }

    @Override
    public int ExecSqlCommand(String sql, Object[] parameters) {
        return 0;
    }
}
