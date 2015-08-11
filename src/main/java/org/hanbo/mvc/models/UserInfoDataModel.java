package org.hanbo.mvc.models;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

public class UserInfoDataModel
{
   private String userId;
   private String userName;
   private String userEmail;
   
   private Set<UserRoleDataModel> userRoles;
   
   public UserInfoDataModel()
   {
      userRoles = new HashSet<UserRoleDataModel>();
   }
   
   public String getUserId()
   {
      return userId;
   }
   
   public void setUserId(String userId)
   {
      this.userId = userId;
   }

   public String getUserName()
   {
      return userName;
   }

   public void setUserName(String userName)
   {
      this.userName = userName;
   }

   public String getUserEmail()
   {
      return userEmail;
   }

   public void setUserEmail(String userEmail)
   {
      this.userEmail = userEmail;
   }
   
   public Set<UserRoleDataModel> getUserRoles()
   {
      return userRoles;
   }

   public void setUserRoles(Set<UserRoleDataModel> userRoles)
   {
      this.userRoles = userRoles;
   }

   public void validateUserInfo()
   {
      if (StringUtils.isEmpty(this.userName))
      {
         throw new IllegalArgumentException("Input user name is null or empty");
      }

      if (this.userName.length() > 64)
      {
         throw new IllegalArgumentException("User name has characters length greater than 64.");
      }

      if (StringUtils.isEmpty(this.userEmail))
      {
         throw new IllegalArgumentException("User email is null or empty");
      }

      if (this.userEmail.length() > 64)
      {
         throw new IllegalArgumentException("User email has characters length greater than 64.");
      }
      
      if (this.userRoles.isEmpty())
      {
         throw new IllegalArgumentException("User role list is empty");         
      }
   }
}
