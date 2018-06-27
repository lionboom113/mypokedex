package jp.co.pise.projecttemplate_android.Data.Infrastracture.Orm;

import android.content.Context;

import com.github.gfx.android.orma.AccessThreadConstraint;

import jp.co.pise.projecttemplate_android.Data.Entities.OrmaDatabase;

public class OrmaProvider {
    static public OrmaDatabase ProvideDatabase(Context context){
        return OrmaDatabase.builder(context)
                .writeOnMainThread(AccessThreadConstraint.NONE) // UIスレッドでも読み書きできるようにする
                .build();
    }
}
