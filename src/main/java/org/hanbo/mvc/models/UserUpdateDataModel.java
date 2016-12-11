package org.hanbo.mvc.models;

import java.util.Date;

import org.hanbo.mvc.utilities.DateToString;

public class UserUpdateDataModel
{
   private String userStatusId;
   
   private String statusTitle;
   
   private String statusContent;
   
   private Date statusCreateDate;

   private Date statusUpdateDate;
   
   private String statusOwnerId;

   private String statusOwnerUserName;

   private String statusOwnerUserFullName;
   
   private String statusOwnerUserEmail;
   
   private String statusOwnerUserProfileId;

   private String statusOwnerUserIconId;
   
   private int commentsCount;
   
   private int visitorLikesCount;

   private int visitorDislikesCount;

   public String getUserStatusId()
   {
      return userStatusId;
   }

   public void setUserStatusId(String userStatusId)
   {
      this.userStatusId = userStatusId;
   }

   public String getStatusTitle()
   {
      return statusTitle;
   }

   public void setStatusTitle(String statusTitle)
   {
      this.statusTitle = statusTitle;
   }
   
   public String getStatusContent()
   {
      return statusContent;
   }

   public void setStatusContent(String statusContent)
   {
      this.statusContent = statusContent;
   }

   public Date getStatusCreateDate()
   {
      return statusCreateDate;
   }

   public String getStatusCreateDateString()
   {
      return 
      DateToString.dateToString(statusCreateDate, "MM/dd/yyyy HH:mm:ss");
   }
   
   public void setStatusCreateDate(Date statusCreateDate)
   {
      this.statusCreateDate = statusCreateDate;
   }

   public Date getStatusUpdateDate()
   {
      return statusUpdateDate;
   }
   
   public String getStatusUpdateDateString()
   {
      return 
      DateToString.dateToString(statusUpdateDate, "MM/dd/yyyy HH:mm:ss");
   }

   public void setStatusUpdateDate(Date statusUpdateDate)
   {
      this.statusUpdateDate = statusUpdateDate;
   }

   public String getStatusOwnerId()
   {
      return statusOwnerId;
   }

   public void setStatusOwnerId(String statusOwnerId)
   {
      this.statusOwnerId = statusOwnerId;
   }

   public String getStatusOwnerUserName()
   {
      return statusOwnerUserName;
   }

   public void setStatusOwnerUserName(String statusOwnerUserName)
   {
      this.statusOwnerUserName = statusOwnerUserName;
   }

   public String getStatusOwnerUserFullName()
   {
      return statusOwnerUserFullName;
   }

   public void setStatusOwnerUserFullName(String statusOwnerUserFullName)
   {
      this.statusOwnerUserFullName = statusOwnerUserFullName;
   }

   public String getStatusOwnerUserEmail()
   {
      return statusOwnerUserEmail;
   }

   public void setStatusOwnerUserEmail(String statusOwnerUserEmail)
   {
      this.statusOwnerUserEmail = statusOwnerUserEmail;
   }

   public String getStatusOwnerUserIconId()
   {
      return statusOwnerUserIconId;
   }

   public void setStatusOwnerUserIconId(String statusOwnerUserIconId)
   {
      this.statusOwnerUserIconId = statusOwnerUserIconId;
   }

   public String getStatusOwnerUserProfileId()
   {
      return statusOwnerUserProfileId;
   }

   public void setStatusOwnerUserProfileId(String statusOwnerUserProfileId)
   {
      this.statusOwnerUserProfileId = statusOwnerUserProfileId;
   }

   public int getCommentsCount()
   {
      return commentsCount;
   }

   public void setCommentsCount(int commentsCount)
   {
      this.commentsCount = commentsCount;
   }

   public int getVisitorLikesCount()
   {
      return visitorLikesCount;
   }

   public void setVisitorLikesCount(int visitorLikesCount)
   {
      this.visitorLikesCount = visitorLikesCount;
   }

   public int getVisitorDislikesCount()
   {
      return visitorDislikesCount;
   }

   public void setVisitorDislikesCount(int visitorDislikesCount)
   {
      this.visitorDislikesCount = visitorDislikesCount;
   }
}
