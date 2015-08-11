package org.hanbo.mvc.models;

public class LoginDataModel
{
   private String userName;
   private String userPassword;
   
   public String getUserName()
   {
      return userName;
   }
   
   public void setUserName(String userName)
   {
      this.userName = userName;
   }

   public String getUserPassword()
   {
      return userPassword;
   }

   public void setUserPassword(String userPassword)
   {
      this.userPassword = userPassword;
   }
}
