package jp.co.pise.projecttemplate_android.Presentation.Event.Activity;

import jp.co.pise.projecttemplate_android.Presentation.Event.AsyncEvent;
import jp.co.pise.projecttemplate_android.Presentation.Event.IEventType;

public class PokemonDetailAsyncEvent extends AsyncEvent {

    public enum EventType implements IEventType
    {
        None,
        UserRegistComplete,
        LoadDataComplete
    }

    public PokemonDetailAsyncEvent(EventType type){
        isSuccess = true;
        this.eventType = type;
    }

    public boolean IsSuccess()
    {
        return isSuccess;
    }
}
