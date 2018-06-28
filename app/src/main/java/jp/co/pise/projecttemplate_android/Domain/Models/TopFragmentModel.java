package jp.co.pise.projecttemplate_android.Domain.Models;

import java.util.ArrayList;

import io.reactivex.Observable;
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

    public Observable<String> searchText;

    public ArrayList<PokemonEntity> pkms;

}
