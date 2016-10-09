package org.hanbo.mvc.services;

import java.util.HashSet;
import java.util.Set;

import org.hanbo.mvc.entities.LoginUser;
import org.hanbo.mvc.entities.UserRole;
import org.hanbo.mvc.models.UserInfoDataModel;
import org.hanbo.mvc.models.UserProfileDataModel;
import org.hanbo.mvc.models.UserSignupDataModel;
import org.hanbo.mvc.repositories.UsersRepository;
import org.hanbo.mvc.services.utilities.UserInfoMappingUtil;
import org.hanbo.mvc.utilities.IdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginUserServiceImpl
   implements LoginUserService
{
   @Autowired
   private UsersRepository _usersRepository;

   @Override
   public void validateUserInfoData(UserSignupDataModel userInfo)
   {
      if (userInfo == null)
      {
         throw new IllegalArgumentException("User sign up data is null");
      }
      
      userInfo.validateUserInfo();
   }
   
   @Override
   public void addUser(UserSignupDataModel userToAdd)
   {
      final String roleName = "ROLE_USER";
      
      LoginUser newUser = UserInfoMappingUtil.fromUserInfoDataModel(userToAdd);
      newUser.setId(IdUtil.generateUuid());
      
      Set<UserRole> allUserRoles
         = new HashSet<UserRole>();
      
      UserRole role = new UserRole();
      role.setRoleName(roleName);
      allUserRoles.add(role);
      this._usersRepository.addUser(newUser, allUserRoles);
   }
   
   @Override
   public UserInfoDataModel getUserById(String userId)
   {
      LoginUser user = _usersRepository.getUserById(userId);
      return UserInfoMappingUtil.toUserInfoDataModel(user);
   }

   @Override
   public UserInfoDataModel getUser(String userName)
   {
      LoginUser user = _usersRepository.getUser(userName);
      return UserInfoMappingUtil.toUserInfoDataModel(user);
   }
   
   @Override
   public void createUserProfile(UserProfileDataModel userProfile)
   {
      
   }
   
   @Override
   public UserProfileDataModel getUserProfile(String userId)
   {
      return null;
   }
}
