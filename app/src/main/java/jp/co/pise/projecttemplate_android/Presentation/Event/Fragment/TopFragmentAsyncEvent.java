package jp.co.pise.projecttemplate_android.Presentation.Event.Fragment;

import android.view.View;

import jp.co.pise.projecttemplate_android.Data.Entities.PokemonEntity;

public class TopFragmentAsyncEvent {

    public enum EventType
    {
        None,
        UserRegistComplete,
        LoadPokeComplete,
        AddedPokeToList,
        PokemonTap
    }

    private boolean isSuccess;
    public EventType eventType;
    public PokemonEntity loadedPoke;
    public int position;
    public View view;

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

    public static TopFragmentAsyncEvent newPokeAdded(PokemonEntity loadedPoke) {
        TopFragmentAsyncEvent event = new TopFragmentAsyncEvent();
        event.eventType = EventType.AddedPokeToList;
        event.loadedPoke = loadedPoke;
        event.isSuccess = true;
        return event;
    }

    public static TopFragmentAsyncEvent pokemonTap(PokemonEntity loadedPoke, View view) {
        TopFragmentAsyncEvent event = new TopFragmentAsyncEvent();
        event.eventType = EventType.PokemonTap;
        event.loadedPoke = loadedPoke;
        event.isSuccess = true;
        event.view = view;
        return event;
    }

    public boolean IsSuccess()
    {
        return isSuccess;
    }
}
