package jp.co.pise.projecttemplate_android.Presentation.Event;

public abstract class AsyncEvent implements IAsyncEvent {
    protected boolean isSuccess;
    protected IEventType eventType;

    @Override
    public void initialize(IEventType type) {
        this.eventType = type;
    }
}
