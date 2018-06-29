package jp.co.pise.projecttemplate_android.Presentation.Presenter;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.observers.DisposableObserver;
import jp.co.pise.projecttemplate_android.Data.Entities.PokemonEntity;
import jp.co.pise.projecttemplate_android.Domain.UseCases.PokemonManager;
import jp.co.pise.projecttemplate_android.Presentation.Event.Activity.PokemonDetailAsyncEvent;
import jp.co.pise.projecttemplate_android.Presentation.Event.Fragment.TopFragmentAsyncEvent;

public class PokemonDetailPresenter implements IPresenter{
    private PokemonManager pkm = new PokemonManager();
    public PokemonEntity model;

    public PokemonDetailPresenter() {

    }

    public void loadId(String id) {
        pkm.getPokemon(id).subscribe(new DisposableObserver<PokemonEntity>() {
            @Override
            public void onNext(PokemonEntity pokemonEntity) {
                model = pokemonEntity;
                EventBus.getDefault().post(new PokemonDetailAsyncEvent(PokemonDetailAsyncEvent.EventType.LoadDataComplete));
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }
}
