package org.hanbo.mvc.models;

import java.util.Date;

import org.hanbo.mvc.exceptions.WebAppException;
import org.hanbo.mvc.utilities.DateToString;
import org.springframework.util.StringUtils;

public class VisitorCommentDataModel
{
   private String commentId;
   
   private String parentCommentId;
   
   private String commenterName;
   
   private String commenterEmail;
   
   private String commentTitle;
   
   private String commentContent;
   
   private String commentSourceIp;
   
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
   
   public void validateCommenterInfoNotEmpty()
   {
      if (StringUtils.isEmpty(this.commenterName))
      {
         throw new WebAppException("Commenter's full name cannot be null.",
            WebAppException.ErrorType.DATA);
      }
      
      if (StringUtils.isEmpty(this.commenterEmail))
      {
         throw new WebAppException("Commenter's full email address cannot be null.",
            WebAppException.ErrorType.DATA);
      }
      
      if (StringUtils.isEmpty(this.commentSourceIp))
      {
         throw new WebAppException("Commenter's current IP cannot be null or empty.",
            WebAppException.ErrorType.DATA);
      }
   }
   
   public void validateCommenterInfo()
   {
      if (!StringUtils.isEmpty(this.commenterName) && this.commenterName.length() > 96)
      {
         throw new WebAppException("Commenter's full name contains too many characters.",
            WebAppException.ErrorType.DATA);
      }
      
      if (!StringUtils.isEmpty(this.commenterEmail) && this.commenterEmail.length() > 96)
      {
         throw new WebAppException("Commenter's full email address contains too many characters.",
            WebAppException.ErrorType.DATA);
      }
      
      if (StringUtils.isEmpty(this.commentSourceIp))
      {
         throw new WebAppException("Commenter's current IP cannot be null or empty.",
            WebAppException.ErrorType.DATA);
      }
   }
   
   public void validateCommentContent()
   {
      if (!StringUtils.isEmpty(commentTitle) && commentTitle.length() > 128)
      {
         throw new WebAppException("Comment title contains too many characters.",
            WebAppException.ErrorType.DATA);
      }
      
      if (StringUtils.isEmpty(commentContent))
      {
         throw new WebAppException("Comment content cannot be null or empty.",
            WebAppException.ErrorType.DATA);
      }
      
      if (!StringUtils.isEmpty(commentContent) && commentContent.length() > 512)
      {
         throw new WebAppException("Comment content contains too many characters.",
            WebAppException.ErrorType.DATA);
      }
   }

   public String getCommentSourceIp()
   {
      return commentSourceIp;
   }

   public void setCommentSourceIp(String commentSourceIp)
   {
      this.commentSourceIp = commentSourceIp;
   }
}
