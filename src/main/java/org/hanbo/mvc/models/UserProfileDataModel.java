package org.hanbo.mvc.models;

import java.util.Date;

import org.hanbo.mvc.exceptions.WebAppException;
import org.hanbo.mvc.utilities.DateToString;
import org.springframework.util.StringUtils;

public class UserProfileDataModel
{
   private String userId;
   
   private String userName;

   private String userEmail;
   
   private String userProfileId;
   
   private String userFirstName;
      
   private String userLastName;
   
   private int userAge;

   private String userGender;
   
   private String userLocation;
   
   private String userProfession;
   
   private String userIntroduction;
   
   private String userIconId;
   
   private Date profileCreateDate;

   private Date profileUpdateDate;
   

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

   public String getUserProfileId()
   {
      return userProfileId;
   }

   public void setUserProfileId(String userProfileId)
   {
      this.userProfileId = userProfileId;
   }

   public String getUserFirstName()
   {
      return userFirstName;
   }

   public void setUserFirstName(String userFirstName)
   {
      this.userFirstName = userFirstName;
   }

   public String getUserLastName()
   {
      return userLastName;
   }

   public void setUserLastName(String userLastName)
   {
      this.userLastName = userLastName;
   }

   public int getUserAge()
   {
      return userAge;
   }

   public void setUserAge(int userAge)
   {
      this.userAge = userAge;
   }

   public String getUserGender()
   {
      return userGender;
   }

   public void setUserGender(String userGender)
   {
      this.userGender = userGender;
   }

   public String getUserLocation()
   {
      return userLocation;
   }

   public void setUserLocation(String userLocation)
   {
      this.userLocation = userLocation;
   }

   public String getUserProfession()
   {
      return userProfession;
   }

   public void setUserProfession(String userProfession)
   {
      this.userProfession = userProfession;
   }

   public String getUserIntroduction()
   {
      return userIntroduction;
   }

   public void setUserIntroduction(String userIntroduction)
   {
      this.userIntroduction = userIntroduction;
   }

   public String getUserIconId()
   {
      return userIconId;
   }

   public void setUserIconId(String userIconId)
   {
      this.userIconId = userIconId;
   }

   public String getUserEmail()
   {
      return userEmail;
   }

   public void setUserEmail(String userEmail)
   {
      this.userEmail = userEmail;
   }
   
   public Date getProfileCreateDate()
   {
      return profileCreateDate;
   }
   
   public String getProfileCreateDateString()
   {
      return DateToString.dateStringForDisplay(profileCreateDate);
   }

   public void setProfileCreateDate(Date profileCreateDate)
   {
      this.profileCreateDate = profileCreateDate;
   }

   public Date getProfileUpdateDate()
   {
      return profileUpdateDate;
   }
   
   public String getProfileUpdateDateString()
   {
      return DateToString.dateStringForDisplay(profileUpdateDate);
   }

   public void setProfileUpdateDate(Date profileUpdateDate)
   {
      this.profileUpdateDate = profileUpdateDate;
   }
   
   public void validateUserProfile()
   {
      if (StringUtils.isEmpty(userId))
      {
         throw new WebAppException("User Id is null or empty.", WebAppException.ErrorType.DATA);
      }

      if (userId.length() > 45)
      {
         throw new WebAppException("User Id contains too many characters.", WebAppException.ErrorType.DATA);
      }
      
      if (StringUtils.isEmpty(userFirstName))
      {
         throw new WebAppException("User first name is null or empty.", WebAppException.ErrorType.DATA);
      }
      
      if (userFirstName.length() > 64)
      {
         throw new WebAppException("User first name contains too many characters.", WebAppException.ErrorType.DATA);
      }

      if (StringUtils.isEmpty(userLastName))
      {
         throw new WebAppException("User last name is null or empty.", WebAppException.ErrorType.DATA);
      }
      
      if (userLastName.length() > 64)
      {
         throw new WebAppException("User last name contains too many characters.", WebAppException.ErrorType.DATA);
      }
      
      if (!StringUtils.isEmpty(userGender) && userGender.length() > 6)
      {
         throw new WebAppException("User gender contains too many characters.", WebAppException.ErrorType.DATA);         
      }
      
      if (!StringUtils.isEmpty(userLocation) && userLocation.length() > 128)
      {
         throw new WebAppException("User location contains too many characters.", WebAppException.ErrorType.DATA);         
      }
      
      if (!StringUtils.isEmpty(userProfession) && userProfession.length() > 128)
      {
         throw new WebAppException("User profession contains too many characters.", WebAppException.ErrorType.DATA);         
      }
      
      if (!StringUtils.isEmpty(userIntroduction) && userIntroduction.length() > 4096)
      {
         throw new WebAppException("User introduction contains too many characters.", WebAppException.ErrorType.DATA);         
      }
   }


}
