package org.hanbo.mvc.services;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.hanbo.mvc.entities.LoginUser;
import org.hanbo.mvc.entities.UserProfile;
import org.hanbo.mvc.entities.UserRole;
import org.hanbo.mvc.exceptions.WebAppException;
import org.hanbo.mvc.models.UserInfoDataModel;
import org.hanbo.mvc.models.UserProfileDataModel;
import org.hanbo.mvc.models.UserSignupDataModel;
import org.hanbo.mvc.repositories.UsersRepository;
import org.hanbo.mvc.services.utilities.UserInfoMappingUtil;
import org.hanbo.mvc.utilities.IdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
   public void saveUserProfile(UserProfileDataModel userProfile)
   {
      if (userProfile != null)
      {
         validateUserProfileModel(userProfile);
         
         UserProfile profileEntity
            = UserInfoMappingUtil.fromUserProfieModelToEntity(userProfile);
         if (profileEntity != null)
         {
            Date dateNow = new Date();
            if (!StringUtils.isEmpty(userProfile.getUserProfileId()))
            {
               profileEntity.setId(userProfile.getUserProfileId());
               profileEntity.setCreateDate(dateNow); //TODO this is wrong and must fix.
               profileEntity.setUpdateDate(dateNow);
            }
            else
            {
               String profileId = IdUtil.generateUuid();
               profileEntity.setId(profileId);
               profileEntity.setCreateDate(dateNow);
               profileEntity.setUpdateDate(dateNow);
            }            
            if (!StringUtils.isEmpty(userProfile.getUserId()))
            {
               LoginUser owner = _usersRepository.getUserById(userProfile.getUserId());
               profileEntity.setOwner(owner);
            }
            else
            {
               throw new WebAppException("Cannot find the ower of this profile.",  WebAppException.ErrorType.DATA);
            }
            
            this._usersRepository.saveUserProfile(profileEntity);
         }
      }
   }
   
   @Override
   public UserProfileDataModel getUserProfile(String userId)
   {
      if (StringUtils.isEmpty(userId))
      {
         return null;
      }
      
      UserProfile userProfile = this._usersRepository.getUserProfileByUserId(userId);
      if (userProfile != null)
      {
         return UserInfoMappingUtil.fromEntityToUserProfieModel(userProfile);
      }
      
      return null;
   }
   
   private void validateUserProfileModel(UserProfileDataModel userProfileModel)
   {
      if (StringUtils.isEmpty(userProfileModel.getUserFirstName()))
      {
         throw new WebAppException("User first name cannot be null or empty.",  WebAppException.ErrorType.DATA);
      }

      if (userProfileModel.getUserFirstName() != null && userProfileModel.getUserFirstName().length() > 64)
      {
         throw new WebAppException("User first name is too long, max 64 characters.",  WebAppException.ErrorType.DATA);
      }

      if (StringUtils.isEmpty(userProfileModel.getUserLastName()))
      {
         throw new WebAppException("User last name cannot be null or empty.",  WebAppException.ErrorType.DATA);
      }

      if (userProfileModel.getUserLastName() != null && userProfileModel.getUserLastName().length() > 64)
      {
         throw new WebAppException("User last name is too long, max 64 characters.",  WebAppException.ErrorType.DATA);
      }
      
      if (userProfileModel.getUserAge() < 18 || userProfileModel.getUserAge() > 150)
      {
         throw new WebAppException("User age is invalid, must be between 18 and 150.",  WebAppException.ErrorType.DATA);
      }

      if (!StringUtils.isEmpty(userProfileModel.getUserLocation()) && userProfileModel.getUserLocation().length() > 128)
      {
         throw new WebAppException("User location is too long, max 128 characters.",  WebAppException.ErrorType.DATA);
      }

      if (!StringUtils.isEmpty(userProfileModel.getUserProfession()) && userProfileModel.getUserProfession().length() > 128)
      {
         throw new WebAppException("User profession is too long, max 128 characters.",  WebAppException.ErrorType.DATA);
      }
      
      if (!StringUtils.isEmpty(userProfileModel.getUserIntroduction()) && userProfileModel.getUserIntroduction().length() > 4096)
      {
         throw new WebAppException("Profile introduction is too long, max 4096 characters.",  WebAppException.ErrorType.DATA);
      }
   }
}
