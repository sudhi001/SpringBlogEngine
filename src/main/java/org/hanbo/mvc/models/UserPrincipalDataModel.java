package org.hanbo.mvc.models;

public class UserPrincipalDataModel
{
   private String userId;
   private String userName;
   private String userRole;
   private boolean userActive;
   
   public UserPrincipalDataModel()
   {  
   }
   
   public String getUserName()
   {
      return this.userName;
   }

   public void setUserName(String userName)
   {
      this.userName = userName;
   }

   public String getUserId()
   {
      return this.userId;
   }

   public void setUserId(String userId)
   {
      this.userId = userId;
   }
   
   public String getUserRole()
   {
      return userRole;
   }

   public void setUserRole(String userRole)
   {
      this.userRole = userRole;
   }

   public boolean isUserActive()
   {
      return userActive;
   }

   public void setUserActive(boolean userActive)
   {
      this.userActive = userActive;
   }
}
