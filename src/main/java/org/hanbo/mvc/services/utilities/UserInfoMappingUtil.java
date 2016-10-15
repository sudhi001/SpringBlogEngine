package org.hanbo.mvc.services.utilities;

import java.util.Date;

import org.hanbo.mvc.entities.LoginUser;
import org.hanbo.mvc.entities.UserProfile;
import org.hanbo.mvc.models.UserInfoDataModel;
import org.hanbo.mvc.models.UserProfileDataModel;
import org.hanbo.mvc.models.UserSignupDataModel;
import org.hanbo.mvc.utilities.UserPasswordUtil;

public class UserInfoMappingUtil
{
   public static UserInfoDataModel toUserInfoDataModel(LoginUser user)
   {
      if (user == null)
      {
         return null;
      }
      
      UserInfoDataModel retVal = new UserInfoDataModel();
      retVal.setUserId(user.getId());
      retVal.setUserName(user.getUserName());
      retVal.setUserEmail(user.getUserEmail());
      
      return retVal;
   }
   
   public static LoginUser fromUserInfoDataModel(UserSignupDataModel userInfo)
   {
      if (userInfo == null)
      {
         return null;
      }
     
      LoginUser user = new LoginUser();
      user.setUserName(userInfo.getUserName());
      user.setUserEmail(userInfo.getUserEmail());
      
      user.setUserPass(UserPasswordUtil.passwordEncryption(userInfo.getUserPass1()));
      user.setActive(true);
      
      Date dateNow = new Date();
      user.setCreateDate(dateNow);
      user.setUpdateDate(dateNow);
      
      return user;
   }
   
   public static UserProfile fromUserProfieModelToEntity(UserProfileDataModel userProfileModel)
   {
      if (userProfileModel == null)
      {
         return null;
      }
      
      UserProfile retVal = new UserProfile();
      retVal.setId(userProfileModel.getUserProfileId());
      retVal.setFirstName(userProfileModel.getUserFirstName());
      retVal.setLastName(userProfileModel.getUserLastName());
      retVal.setAge(userProfileModel.getUserAge());
      retVal.setGender(userProfileModel.getUserGender());
      retVal.setLocation(userProfileModel.getUserLocation());
      retVal.setProfession(userProfileModel.getUserProfession());
      retVal.setIntroduction(userProfileModel.getUserIntroduction());
      
      return retVal;
   }
   
   public static UserProfileDataModel fromEntityToUserProfieModel(UserProfile entity)
   {
      if (entity == null)
      {
         return null;
      }
      
      UserProfileDataModel retVal = new UserProfileDataModel();
      if (entity.getOwner() != null)
      {
         retVal.setUserId(entity.getOwner().getId());
         retVal.setUserName(entity.getOwner().getUserName());
         retVal.setUserEmail(entity.getOwner().getUserEmail());
      }
      
      retVal.setUserProfileId(entity.getId());
      retVal.setUserFirstName(entity.getFirstName());
      retVal.setUserLastName(entity.getLastName());
      retVal.setUserAge(entity.getAge());
      retVal.setUserGender(entity.getGender());
      retVal.setUserLocation(entity.getLocation());
      retVal.setUserProfession(entity.getProfession());
      retVal.setUserIntroduction(entity.getIntroduction());
      retVal.setProfileCreateDate(entity.getCreateDate());
      retVal.setProfileUpdateDate(entity.getCreateDate());
      
      if (entity.getUserIcon() != null)
      {
         retVal.setUserIconId(entity.getUserIcon().getId());
      }
      
      return retVal;
   }
}
