package jp.co.pise.projecttemplate_android.Data.Entities;

import android.graphics.Bitmap;

import com.github.gfx.android.orma.annotation.Column;
import com.github.gfx.android.orma.annotation.PrimaryKey;
import com.github.gfx.android.orma.annotation.Table;

import java.util.ArrayList;
import java.util.List;

@Table
public class PokemonEntity {
    @PrimaryKey(autoincrement = false)
    public int id;

    @Column()
    public String name;

    public List<DescriptionEntity> descriptions;

    public List<TypeEntity> types = new ArrayList<TypeEntity>();

    public Bitmap image;

    public PokemonEntity()
    {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PokemonEntity(String name)
    {
        this.name = name;
    }

    public List<DescriptionEntity> getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(List<DescriptionEntity> descriptions) {
        this.descriptions = descriptions;
    }

    public List<TypeEntity> getTypes() {
        return types;
    }

    public void setTypes(List<TypeEntity> types) {
        this.types = types;
    }
}
