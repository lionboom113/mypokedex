package jp.co.pise.projecttemplate_android.Data.Entities;

import com.github.gfx.android.orma.annotation.Column;
import com.github.gfx.android.orma.annotation.PrimaryKey;
import com.github.gfx.android.orma.annotation.Table;

@Table
public class PokemonTypeEntity {
    @PrimaryKey(autoincrement = true)
    public int id;

    @Column(indexed = true)
    public PokemonEntity pokemonEntity;

    @Column(indexed = true)
    public TypeEntity typeEntity;

    @Column
    public Integer typeSlot;
}
