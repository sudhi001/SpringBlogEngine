package org.hanbo.mvc.models;

public class CommentInputDataModel
{
   private String originId;
   private boolean commentPrivate;
   private String parentCommentId;
   private String commentTitle;
   private String commentContent;   
   private String commenterName;
   private String commenterEmail;

   public String getOriginId()
   {
      return originId;
   }

   public void setOriginId(String originId)
   {
      this.originId = originId;
   }

   public String getParentCommentId()
   {
      return parentCommentId;
   }

   public void setParentCommentId(String parentCommentId)
   {
      this.parentCommentId = parentCommentId;
   }
   
   public boolean isCommentPrivate()
   {
      return commentPrivate;
   }

   public void setCommentPrivate(boolean commentPrivate)
   {
      this.commentPrivate = commentPrivate;
   }
   
   public String getCommentTitle()
   {
      return commentTitle;
   }

   public void setCommentTitle(String commentTitle)
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

   public String getCommenterName()
   {
      return commenterName;
   }

   public void setCommenterName(String commenterName)
   {
      this.commenterName = commenterName;
   }

   public String getCommenterEmail()
   {
      return commenterEmail;
   }

   public void setCommenterEmail(String commenterEmail)
   {
      this.commenterEmail = commenterEmail;
   }
}
