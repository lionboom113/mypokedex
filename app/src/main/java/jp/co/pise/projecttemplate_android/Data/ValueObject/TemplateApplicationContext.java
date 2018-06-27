package jp.co.pise.projecttemplate_android.Data.ValueObject;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

public class TemplateApplicationContext extends Application {

    @Singleton
    public static Context Context;

    @Override
    public void onCreate() {
        super.onCreate();

        this.Context = this.getApplicationContext();
    }

    public static Context GetInstance()
    {
        return Context;
    }
}
