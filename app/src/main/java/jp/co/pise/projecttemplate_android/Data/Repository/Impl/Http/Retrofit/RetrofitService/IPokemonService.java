package jp.co.pise.projecttemplate_android.Data.Repository.Impl.Http.Retrofit.RetrofitService;

import java.util.List;

import io.reactivex.Observable;
import jp.co.pise.projecttemplate_android.Data.Entities.PokemonEntity;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IPokemonService {
    @GET("api/v2/pokemon/{id}")
    Observable<PokemonEntity> getPoke(@Path("id") String id);
}
