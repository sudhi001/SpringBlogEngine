package org.hanbo.mvc.models.json;

public class CommentJsonDataModel
{
   private String commentTitle;
   
   private String commenterName;
   
   private String commenterEmail;
   
   private String commentContent;

   public String getCommenTitle()
   {
      return commentTitle;
   }

   public void setCommenTitle(String commentTitle)
   {
      this.commentTitle = commentTitle;
   }

   public String getCommentContent()
   {
      return commentContent;
   }

   public void setCommentContent(String commentContent)
   {
      this.commentContent = commentContent;
   }

   public String getCommenterEmail()
   {
      return commenterEmail;
   }

   public void setCommenterEmail(String commenterEmail)
   {
      this.commenterEmail = commenterEmail;
   }

   public String getCommenterName()
   {
      return commenterName;
   }

   public void setCommenterName(String commenterName)
   {
      this.commenterName = commenterName;
   }
}
