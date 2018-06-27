package jp.co.pise.projecttemplate_android.Data.Repository;

import dagger.Component;

/**
 * 使用するModuleの定義
 */
@Component(modules = RepositoryDIModule.class)
public interface RepositoryDIConponent {
    IUserRepository UserRepository();
    IPokemonRepository PokemonRepository();
}
