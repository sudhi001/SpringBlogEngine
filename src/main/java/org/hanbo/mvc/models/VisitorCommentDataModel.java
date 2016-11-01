package org.hanbo.mvc.models;

import java.util.Date;

import org.hanbo.mvc.utilities.DateToString;

public class VisitorCommentDataModel
{
   private String commentId;
   
   private String parentCommentId;
   
   private String commenterName;
   
   private String commenterEmail;
   
   private String commentTitle;
   
   private String commentContent;
   
   private boolean commentPrivate;
   
   private boolean commentApproved;
   
   private Date commentCreateDate;
   
   private Date commentUpdateDate;

   public String getCommentId()
   {
      return commentId;
   }

   public void setCommentId(String commentId)
   {
      this.commentId = commentId;
   }

   public String getParentCommentId()
   {
      return parentCommentId;
   }

   public void setParentCommentId(String parentCommentId)
   {
      this.parentCommentId = parentCommentId;
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

   public boolean isCommentPrivate()
   {
      return commentPrivate;
   }

   public void setCommentPrivate(boolean commentPrivate)
   {
      this.commentPrivate = commentPrivate;
   }

   public boolean isCommentApproved()
   {
      return commentApproved;
   }

   public void setCommentApproved(boolean commentApproved)
   {
      this.commentApproved = commentApproved;
   }

   public Date getCommentCreateDate()
   {
      return commentCreateDate;
   }

   public String getCommentCreateDateString()
   {
      return DateToString.dateStringForDisplay(commentCreateDate);
   }
   
   public void setCommentCreateDate(Date commentCreateDate)
   {
      this.commentCreateDate = commentCreateDate;
   }

   public Date getCommentUpdateDate()
   {
      return commentUpdateDate;
   }
   
   public String getCommentUpdateDateString()
   {
      return DateToString.dateStringForDisplay(commentUpdateDate);
   }

   public void setCommentUpdateDate(Date commentUpdateDate)
   {
      this.commentUpdateDate = commentUpdateDate;
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
