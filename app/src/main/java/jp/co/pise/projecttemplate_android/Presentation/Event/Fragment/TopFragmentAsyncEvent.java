package jp.co.pise.projecttemplate_android.Presentation.Event.Fragment;

public class TopFragmentAsyncEvent {

    public enum EventType
    {
        None,
        UserRegistComplete,
    }

    private boolean isSuccess;
    private EventType eventType;

    public TopFragmentAsyncEvent(){
        this(TopFragmentAsyncEvent.EventType.None);
    }

    public TopFragmentAsyncEvent(TopFragmentAsyncEvent.EventType type){
        isSuccess = false;
        this.eventType = type;
    }

    public boolean IsSuccess()
    {
        return isSuccess;
    }
}
