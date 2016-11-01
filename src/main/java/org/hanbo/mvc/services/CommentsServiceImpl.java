package org.hanbo.mvc.services;

import org.hanbo.mvc.exceptions.WebAppException;
import org.hanbo.mvc.repositories.CommentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class CommentsServiceImpl implements CommentsService
{
   @Autowired
   private CommentsRepository _commentsRepo;

   @Override
   public void addArticleComment(String articleId, String commentUserId,
      String commentAuthor, String commentAuthorEmail, String commentTitle,
      String commentContent)
   {
      validateComments(articleId, commentUserId,
         commentAuthor, commentAuthorEmail, commentTitle,
         commentContent);
      
      
   }
   
   private void validateComments(String articleId, String commentUserId,
      String commentAuthor, String commentAuthorEmail, String commentTitle,
      String commentContent)
   {
      if (StringUtils.isEmpty(articleId))
      {
         throw new WebAppException("associated article id is null or empty.",
            WebAppException.ErrorType.DATA);
      }
      
      if (StringUtils.isEmpty(commentUserId))
      {
         if (StringUtils.isEmpty(commentAuthor))
         {
            throw new WebAppException("When the commenter is not logged in, commenter's full name cannot be null.",
               WebAppException.ErrorType.DATA);
         }
         
         if (StringUtils.isEmpty(commentAuthorEmail))
         {
            throw new WebAppException("When the commenter is not logged in, commenter's full email address cannot be null.",
               WebAppException.ErrorType.DATA);
         }
      }
      
      if (!StringUtils.isEmpty(commentAuthor) && commentAuthor.length() > 96)
      {
         throw new WebAppException("Commenter's full name has too many characters.",
               WebAppException.ErrorType.DATA);
      }
  
      if (!StringUtils.isEmpty(commentAuthorEmail) && commentAuthorEmail.length() > 96)
      {
         throw new WebAppException("Commenter's full email address has too many characters.",
               WebAppException.ErrorType.DATA);
      }
      
      if (!StringUtils.isEmpty(commentTitle) && commentTitle.length() > 96)
      {
         throw new WebAppException("Commenter's full email address has too many characters.",
               WebAppException.ErrorType.DATA);
      }
   }
}
