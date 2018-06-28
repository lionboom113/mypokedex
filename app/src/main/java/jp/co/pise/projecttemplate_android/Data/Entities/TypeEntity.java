package jp.co.pise.projecttemplate_android.Data.Entities;

import com.github.gfx.android.orma.annotation.Column;
import com.github.gfx.android.orma.annotation.PrimaryKey;
import com.github.gfx.android.orma.annotation.Table;

@Table
public class TypeEntity {
    @PrimaryKey(autoincrement = false)
    public String typeName;

    @Column
    public String typeUrl;

    public NamedAPIResource type;

    public int slot;

    public NamedAPIResource getType() {
        return type;
    }

    public void setType(NamedAPIResource type) {
        this.type = type;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public void inflateData() {
        if (type != null) {
            typeName = type.name;
            typeUrl = type.url;
        } else {
            type = new NamedAPIResource();
            type.url = typeUrl;
            type.name = typeName;
        }
    }
}
