package org.hanbo.mvc.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hanbo.mvc.entities.VisitorComment;
import org.hanbo.mvc.exceptions.WebAppException;
import org.hanbo.mvc.models.ArticleCommentDataModel;
import org.hanbo.mvc.repositories.CommentsRepository;
import org.hanbo.mvc.services.utilities.CommentsDataModelEntityMapping;
import org.hanbo.mvc.utilities.IdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@PropertySource("classpath:/site.properties")
public class CommentsServiceImpl implements CommentsService
{
   @Autowired
   private CommentsRepository _commentsRepo;

   @Autowired
   private Environment configValues;
   
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
         commentEntity.setCommentApproved(true);//(commentToSave.isCommentApproved()); //TODO
         commentEntity.setCommentPrivate(false);//(commentToSave.isCommentPrivate()); //TODO
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
   
   @Override
   public List<ArticleCommentDataModel> getViewableArticleComments(String articleId)
   {
      int maxDisplayedComments = getMaxCommentsForDisplay();
      
      List<ArticleCommentDataModel> retVals = new ArrayList<ArticleCommentDataModel>();
      if (!StringUtils.isEmpty(articleId))
      {
         List<VisitorComment> comments = _commentsRepo.loadArticleViewableComments(articleId, maxDisplayedComments);
         
         retVals
            = CommentsDataModelEntityMapping.toDataModels_ArticleComments(comments);
         
         retVals
            = associateArticleComments(retVals);
      }
      
      return retVals;
   }
   
   @Override
   public List<ArticleCommentDataModel> getArticleComments(String articleId, int pageIdx)
   {
      int adminCommentsPerPageCount = getAdminCommentsCountForDisplay();
      
      List<ArticleCommentDataModel> retVals = new ArrayList<ArticleCommentDataModel>();
      if (!StringUtils.isEmpty(articleId))
      {
         List<VisitorComment> comments = _commentsRepo.loadArticleComments(articleId,
            pageIdx,
            adminCommentsPerPageCount);
         
         retVals
            = CommentsDataModelEntityMapping.toDataModels_ArticleComments(comments);
         
         retVals
            = associateArticleComments(retVals);
      }
      
      return retVals;
   }
   
   private List<ArticleCommentDataModel> associateArticleComments(List<ArticleCommentDataModel> unorderedCommentsList)
   {
      List<ArticleCommentDataModel> retVals = new ArrayList<ArticleCommentDataModel>();
      if (unorderedCommentsList != null && !unorderedCommentsList.isEmpty())
      {
         Map<String, ArticleCommentDataModel> commentsAssociation = new HashMap<String, ArticleCommentDataModel>();
         for (ArticleCommentDataModel comment : unorderedCommentsList)
         {
            if (comment != null)
            {
               String commentId = comment.getCommentId();
               if (!StringUtils.isEmpty(commentId))
               {
                  if (!commentsAssociation.containsKey(commentId))
                  {
                     commentsAssociation.put(commentId, comment);
                  }
               }
               
               String parentCommentId = comment.getParentCommentId();
               if (!StringUtils.isEmpty(parentCommentId))
               {
                  if (commentsAssociation.containsKey(parentCommentId))
                  {
                     ArticleCommentDataModel parentComment = commentsAssociation.get(parentCommentId);
                     if (parentComment != null)
                     {
                        parentComment.getChildComments().add(comment);
                     }
                  }
               }
            }
         }
         
         for (Map.Entry<String, ArticleCommentDataModel> entry : commentsAssociation.entrySet())
         {
            if (entry != null && entry.getValue() != null)
            {
               ArticleCommentDataModel comment = entry.getValue();
               if (StringUtils.isEmpty(comment.getParentCommentId()))
               {
                  retVals.add(comment);
               }
            }
         }
      }
      
      return retVals;
   }
   
   private int getMaxCommentsForDisplay()
   {
      String itemsCount = configValues.getProperty("maxDisplayedComments");
      
      int itemsCountVal = Integer.parseInt(itemsCount);
      
      return itemsCountVal;
   }
   
   private int getAdminCommentsCountForDisplay()
   {
      String itemsCount = configValues.getProperty("adminCommentsCountPerPage");
      
      int itemsCountVal = Integer.parseInt(itemsCount);
      
      return itemsCountVal;
   }
}
