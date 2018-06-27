package jp.co.pise.projecttemplate_android.Domain.UseCases;

import javax.inject.Inject;

import jp.co.pise.projecttemplate_android.Data.Entities.UserEntity;
import jp.co.pise.projecttemplate_android.Data.Repository.DaggerRepositoryDIConponent;
import jp.co.pise.projecttemplate_android.Data.Repository.IUserRepository;

public class UserManager {

    @Inject
    IUserRepository userRepository;

    public UserManager(){
        userRepository = DaggerRepositoryDIConponent.create().UserRepository();
    }

    public void RegistUser(UserEntity entity)
    {
        userRepository.Insert(entity);
    }
}
