package org.hanbo.mvc.services;

import org.hanbo.mvc.models.UserInfoDataModel;
import org.hanbo.mvc.models.UserProfileDataModel;
import org.hanbo.mvc.models.UserSignupDataModel;
import org.springframework.web.multipart.MultipartFile;

public interface LoginUserService
{
   void validateUserInfoData(UserSignupDataModel userInfo);
   void addUser(UserSignupDataModel userToAdd);
   UserInfoDataModel getUserById(String userId);
   UserInfoDataModel getUser(String userName);
   void saveUserProfile(UserProfileDataModel userProfile);
   UserProfileDataModel getUserProfile(String userId);
   void changeUserProfileIcon(String ownerId, String iconName, String profileId, MultipartFile userIconToSave);
}
