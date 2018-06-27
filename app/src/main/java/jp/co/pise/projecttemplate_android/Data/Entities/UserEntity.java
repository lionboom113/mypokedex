package jp.co.pise.projecttemplate_android.Data.Entities;

import com.github.gfx.android.orma.annotation.Column;
import com.github.gfx.android.orma.annotation.PrimaryKey;
import com.github.gfx.android.orma.annotation.Table;

/**
 * Created by 和樹 on 2017/03/09.
 */

@Table
public class UserEntity {

    @PrimaryKey(autoincrement = true)
    public long Id;

    @Column()
    public String Name;

    public UserEntity()
    {
    }

    public UserEntity(String name)
    {
        this.Name = name;
    }
}
