package jp.co.pise.projecttemplate_android.Domain.Models;

import java.util.ArrayList;

import jp.co.pise.projecttemplate_android.Data.Entities.PokemonEntity;

public class TopFragmentModel {
    public String Title;
    public String Name;

    public String getId(){
        return this.Title;
    }

    public String getName(){
        return this.Name;
    }

    public ArrayList<PokemonEntity> pkms;

}
