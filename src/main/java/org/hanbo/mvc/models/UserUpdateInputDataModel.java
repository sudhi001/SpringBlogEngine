package org.hanbo.mvc.models;

public class UserUpdateInputDataModel
{
   private String userUpdateId;
   
   private String userUpdateTitle;
   
   private String userUpdateContent;
   
   private boolean userUpdatePublished;

   public String getUserUpdateId()
   {
      return userUpdateId;
   }

   public void setUserUpdateId(String userUpdateId)
   {
      this.userUpdateId = userUpdateId;
   }

   public String getUserUpdateTitle()
   {
      return userUpdateTitle;
   }

   public void setUserUpdateTitle(String userUpdateTitle)
   {
      this.userUpdateTitle = userUpdateTitle;
   }

   public String getUserUpdateContent()
   {
      return userUpdateContent;
   }

   public void setUserUpdateContent(String userUpdateContent)
   {
      this.userUpdateContent = userUpdateContent;
   }

   public boolean isUserUpdatePublished()
   {
      return userUpdatePublished;
   }

   public void setUserUpdatePublished(boolean userUpdatePublished)
   {
      this.userUpdatePublished = userUpdatePublished;
   }
}
