package jp.co.pise.projecttemplate_android.Presentation.Presenter.Fragment;

import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
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

public class TopFragmentPresenter implements IPresenter {

    public enum EventType
    {
        None,
        UserRegistComplete,
    }

    TopFragmentModel model;
    UserManager userManager;
    PokemonManager pkmManager;
    public TopFragment topFragment;


    public TopFragmentPresenter(){
        model = new TopFragmentModel();
        model.Title = "たいとるだよ";
        model.pkms = new ArrayList<PokemonEntity>();
        for(int i=1;i<=10;i++) {
            model.pkms.add(new PokemonEntity());
        }
        pkmManager = new PokemonManager();
        for(int i=1;i<=10;i++) {
            int finalI = i;
            pkmManager.getPokemon(i + "").subscribeOn(Schedulers.io()) // optional if you do not wish to override the default behavior
                    .observeOn(AndroidSchedulers.mainThread()).subscribe(new DisposableObserver<PokemonEntity>() {
                @Override
                public void onNext(PokemonEntity pokemonEntity) {
                    model.pkms.set(finalI - 1, pokemonEntity);
                    topFragment.dataUpdate();
                }

                @Override
                public void onError(Throwable e) {
                    Log.e("ERORR", e.getMessage());
                }

                @Override
                public void onComplete() {

                }
            });
        }
        userManager = new UserManager();
    }

    public void RegistUser(String name)
    {
        userManager.RegistUser(new UserEntity(name));
        EventBus.getDefault().post(new TopFragmentAsyncEvent(TopFragmentAsyncEvent.EventType.UserRegistComplete));
    }

    public TopFragmentModel GetModel(){
        return model;
    }
}
