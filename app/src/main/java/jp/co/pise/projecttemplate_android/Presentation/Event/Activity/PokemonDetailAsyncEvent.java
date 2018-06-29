package jp.co.pise.projecttemplate_android.Presentation.Event.Activity;

import jp.co.pise.projecttemplate_android.Presentation.Event.AsyncEvent;
import jp.co.pise.projecttemplate_android.Presentation.Event.IEventType;

public class MainActivityAsyncEvent extends AsyncEvent {

    public enum EventType implements IEventType
    {
        None,
        UserRegistComplete,
    }

    public MainActivityAsyncEvent(EventType type){
        isSuccess = false;
        this.eventType = type;
    }

    public boolean IsSuccess()
    {
        return isSuccess;
    }
}
