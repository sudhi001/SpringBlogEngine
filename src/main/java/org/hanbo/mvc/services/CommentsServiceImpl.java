package org.hanbo.mvc.services;

import java.util.Date;

import org.hanbo.mvc.entities.VisitorComment;
import org.hanbo.mvc.exceptions.WebAppException;
import org.hanbo.mvc.models.ArticleCommentDataModel;
import org.hanbo.mvc.repositories.CommentsRepository;
import org.hanbo.mvc.utilities.IdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class CommentsServiceImpl implements CommentsService
{
   @Autowired
   private CommentsRepository _commentsRepo;

   @Override
   public void addArticleComment(ArticleCommentDataModel commentToSave)
   {
      if (commentToSave != null)
      {
         commentToSave.validateDataModel();
         
         VisitorComment commentEntity = new VisitorComment();
         
         Date dateNow = new Date();
         
         commentEntity.setId(IdUtil.generateUuid());
         commentEntity.setTitle(commentToSave.getCommentTitle());
         commentEntity.setContent(commentToSave.getCommentContent());
         commentEntity.setCreateDate(dateNow);
         commentEntity.setUpdateDate(dateNow);
         commentEntity.setCommentApproved(commentToSave.isCommentApproved());
         commentEntity.setCommentPrivate(commentToSave.isCommentPrivate());
         commentEntity.setSourceIp(commentToSave.getCommentSourceIp());

         if (StringUtils.isEmpty(commentToSave.getCommentUserId()))
         {
            commentEntity.setCommenter(commentToSave.getCommenterName());
            commentEntity.setCommenterEmail(commentToSave.getCommenterEmail());
         }
         else 
         {
            commentEntity.setCommenter("");
            commentEntity.setCommenterEmail("");
            if (StringUtils.isEmpty(commentToSave.getCommentUserId()))
            {
               throw new WebAppException("Comment owner's user id cannot be null or empty.", WebAppException.ErrorType.SECURITY);
            }
         }
         
         _commentsRepo.saveArticleComment(commentToSave.getArticleId(),
            commentToSave.getCommentUserId(),
            commentToSave.getParentCommentId(),
            commentEntity);
      }
   }
}
