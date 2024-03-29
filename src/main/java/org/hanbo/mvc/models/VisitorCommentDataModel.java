package org.hanbo.mvc.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hanbo.mvc.exceptions.WebAppException;
import org.hanbo.mvc.utilities.DateToString;
import org.springframework.util.StringUtils;

public class VisitorCommentDataModel
{
   private String commentId;
   
   private String refObjectId;
   
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
   
   private String commentUserId;
   
   private String commentUserName;
   
   private String commentUserFullName;
   
   private String commentUserProfileId;

   private String commentUserIconId;
   
   private List<VisitorCommentDataModel> childComments;

   public VisitorCommentDataModel()
   {
      setChildComments(new ArrayList<VisitorCommentDataModel>());
   }
   
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
   
   public String getCommentUserId()
   {
      return commentUserId;
   }

   public void setCommentUserId(String commentUserId)
   {
      this.commentUserId = commentUserId;
   }
   

   public String getCommentUserName()
   {
      return commentUserName;
   }

   public void setCommentUserName(String commentUserName)
   {
      this.commentUserName = commentUserName;
   }

   public String getCommentUserFullName()
   {
      return commentUserFullName;
   }

   public void setCommentUserFullName(String commentUserFullName)
   {
      this.commentUserFullName = commentUserFullName;
   }

   public String getCommentUserProfileId()
   {
      return commentUserProfileId;
   }

   public void setCommentUserProfileId(String commentUserProfileId)
   {
      this.commentUserProfileId = commentUserProfileId;
   }

   public String getCommentUserIconId()
   {
      return commentUserIconId;
   }

   public void setCommentUserIconId(String commentUserIconId)
   {
      this.commentUserIconId = commentUserIconId;
   }

   public String getRefObjectId()
   {
      return refObjectId;
   }

   public void setRefObjectId(String refObjectId)
   {
      this.refObjectId = refObjectId;
   }

   public List<VisitorCommentDataModel> getChildComments()
   {
      return childComments;
   }

   public void setChildComments(List<VisitorCommentDataModel> childComments)
   {
      this.childComments = childComments;
   }
   
   public void validateDataModel()
   {
      validateCommentContent();
      
      if (StringUtils.isEmpty(this.refObjectId))
      {
         throw new WebAppException("Cmment reference object id is null or empty.",
            WebAppException.ErrorType.DATA);
      }
      
      if (StringUtils.isEmpty(this.getCommentUserId()))
      {
         validateCommenterInfoNotEmpty();
      }
      else
      {
         validateCommenterInfo();
      }
   }
}
