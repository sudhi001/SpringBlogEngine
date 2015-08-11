package org.hanbo.mvc.services.utilities;

import java.util.Date;

import org.hanbo.mvc.entities.LoginUser;
import org.hanbo.mvc.models.UserInfoDataModel;
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
}
