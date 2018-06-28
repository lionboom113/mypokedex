package jp.co.pise.projecttemplate_android.Presentation.Event.Fragment;

import jp.co.pise.projecttemplate_android.Data.Entities.PokemonEntity;

public class TopFragmentAsyncEvent {

    public enum EventType
    {
        None,
        UserRegistComplete,
        LoadPokeComplete
    }

    private boolean isSuccess;
    public EventType eventType;
    public PokemonEntity loadedPoke;
    public int position;

    public TopFragmentAsyncEvent(){
        this(TopFragmentAsyncEvent.EventType.None);
    }

    public TopFragmentAsyncEvent(TopFragmentAsyncEvent.EventType type){
        isSuccess = false;
        this.eventType = type;
    }

    public static TopFragmentAsyncEvent loadPokeComplete(PokemonEntity loadedPoke) {
        TopFragmentAsyncEvent event = new TopFragmentAsyncEvent();
        event.eventType = EventType.LoadPokeComplete;
        event.loadedPoke = loadedPoke;
        event.isSuccess = true;
        return event;
    }

    public boolean IsSuccess()
    {
        return isSuccess;
    }
}
