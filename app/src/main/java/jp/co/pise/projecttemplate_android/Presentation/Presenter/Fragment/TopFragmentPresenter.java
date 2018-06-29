package jp.co.pise.projecttemplate_android.Presentation.Presenter.Fragment;

import android.util.Log;
import android.widget.EditText;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import jp.co.pise.projecttemplate_android.Data.Entities.PokemonEntity;
import jp.co.pise.projecttemplate_android.Data.Entities.UserEntity;
import jp.co.pise.projecttemplate_android.Domain.Models.TopFragmentModel;
import jp.co.pise.projecttemplate_android.Domain.UseCases.PokemonManager;
import jp.co.pise.projecttemplate_android.Domain.UseCases.UserManager;
import jp.co.pise.projecttemplate_android.Presentation.Event.Activity.MainActivityAsyncEvent;
import jp.co.pise.projecttemplate_android.Presentation.Event.Fragment.TopFragmentAsyncEvent;
import jp.co.pise.projecttemplate_android.Presentation.Presenter.IPresenter;
import jp.co.pise.projecttemplate_android.Presentation.View.Fragment.TopFragment;
import jp.co.pise.projecttemplate_android.R;

public class TopFragmentPresenter implements IPresenter {

    public enum EventType
    {
        None,
        UserRegistComplete,
    }

    TopFragmentModel model;
    UserManager userManager;
    PokemonManager pkmManager;
    String currentQuery = "";
    public TopFragment topFragment;


    public TopFragmentPresenter(Observable<String> searchText){
        EventBus.getDefault().register(this);
        model = new TopFragmentModel();
        model.searchText = searchText;
        model.Title = "たいとるだよ";
        model.pkms = new ArrayList<PokemonEntity>();
        pkmManager = new PokemonManager();
        int i = 1;
        int finalI = i;
        fetchPkm(1);
        userManager = new UserManager();
        consumeObs(pkmManager.searchPokemon(""));
        searchText.subscribe(new DisposableObserver<String>() {
            @Override
            protected void onStart() {
                super.onStart();
                Log.d("Search-start", "Search start");
            }

            @Override
            public void onNext(String s) {
                if (s.isEmpty()) {
                    //load default list
//                    model.pkms.removeAll(model.pkms);
//                    fetchPkm(1);
                }
                currentQuery = s;
                consumeObs(pkmManager.searchPokemon(s));
            }

            @Override
            public void onError(Throwable e) {
                Log.d("Search-error", "Search cause error");
            }

            @Override
            public void onComplete() {
                Log.d("Search-error", "Search cause error");
            }
        });
    }

    public void consumeObs(Observable<PokemonEntity> pkmeObs) {
        model.pkms.removeAll(model.pkms);
        pkmeObs.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new DisposableObserver<PokemonEntity>() {
            @Override
            public void onNext(PokemonEntity pokemonEntity) {
                model.pkms.add(pokemonEntity);

            }

            @Override
            public void onError(Throwable e) {
                Log.e("ERORR", e.getMessage());
            }

            @Override
            public void onComplete() {
                //continue to load until get 100 pkm :3
                EventBus.getDefault().post(TopFragmentAsyncEvent.loadPokeComplete(null));

            }
        });
    }

    public void fetchPkm(int i) {
        pkmManager.getPokemon(i + "").subscribeOn(Schedulers.io()) // optional if you do not wish to override the default behavior
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new DisposableObserver<PokemonEntity>() {
            @Override
            public void onNext(PokemonEntity pokemonEntity) {
                //EventBus.getDefault().post(TopFragmentAsyncEvent.loadPokeComplete(pokemonEntity));
            }

            @Override
            public void onError(Throwable e) {
                Log.e("ERORR", e.getMessage());
            }

            @Override
            public void onComplete() {
                //continue to load until get 100 pkm :3
                if (i < 10) {
                    int y = i + 1;
                    fetchPkm(y);
                }
            }
        });
    }

    public void RegistUser(String name)
    {
        userManager.RegistUser(new UserEntity(name));
        EventBus.getDefault().post(new TopFragmentAsyncEvent(TopFragmentAsyncEvent.EventType.UserRegistComplete));
    }

    @Subscribe
    public void onModelUpdate(TopFragmentAsyncEvent event)
    {
        if(event.IsSuccess())
        {
            if (event.eventType == TopFragmentAsyncEvent.EventType.LoadPokeComplete) {
                if (event.loadedPoke != null) {
                    if (event.loadedPoke.getName().contains(currentQuery)) {
                        if (currentQuery.isEmpty()) {
                            model.pkms.add(event.loadedPoke);
                            EventBus.getDefault().post(TopFragmentAsyncEvent.newPokeAdded(event.loadedPoke));
                        } else {
                            model.pkms.add(event.loadedPoke);
                            EventBus.getDefault().post(TopFragmentAsyncEvent.newPokeAdded(event.loadedPoke));
                        }
                    }
                }
            }
        }

        //EventBus.getDefault().post(new MainActivityAsyncEvent(MainActivityAsyncEvent.EventType.UserRegistComplete));
    }

    public TopFragmentModel GetModel(){
        return model;
    }
}
