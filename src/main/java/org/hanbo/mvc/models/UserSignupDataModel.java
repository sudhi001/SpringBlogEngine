package org.hanbo.mvc.models;

import org.apache.commons.lang.StringUtils;

public class UserSignupDataModel
   extends UserInfoDataModel
{
   private String userPass1;
   private String userPass2;
   
   public String getUserPass1()
   {
      return userPass1;
   }

   public void setUserPass1(String userPass1)
   {
      this.userPass1 = userPass1;
   }

   public String getUserPass2()
   {
      return userPass2;
   }

   public void setUserPass2(String userPass2)
   {
      this.userPass2 = userPass2;
   }
   
   @Override
   public void validateUserInfo()
   {
      if (StringUtils.isEmpty(this.userPass1))
      {
         throw new IllegalArgumentException("User email is null or empty");
      }

      if (this.userPass1.length() > 24)
      {
         throw new IllegalArgumentException("User password has characters length greater than 24.");
      }

      if (StringUtils.isEmpty(this.userPass2))
      {
         throw new IllegalArgumentException("User email is null or empty");
      }

      if (this.userPass2.length() > 24)
      {
         throw new IllegalArgumentException("User password has characters length greater than 24.");
      }
      
      if (!this.userPass1.equals(this.userPass2))
      {
         throw new IllegalArgumentException("User entered passwords does not match.");
      }
   }
}
