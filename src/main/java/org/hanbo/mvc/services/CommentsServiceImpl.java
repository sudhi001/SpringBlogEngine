package org.hanbo.mvc.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hanbo.mvc.entities.VisitorComment;
import org.hanbo.mvc.exceptions.WebAppException;
import org.hanbo.mvc.models.ItemListPageDataModel;
import org.hanbo.mvc.models.UserCommentsPageDataModel;
import org.hanbo.mvc.models.VisitorCommentDataModel;
import org.hanbo.mvc.models.json.CommentJsonDataModel;
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
   public void addArticleComment(VisitorCommentDataModel commentToSave)
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
         commentEntity.setCommentApproved(false);
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
         
         _commentsRepo.saveArticleComment(commentToSave.getRefObjectId(),
            commentToSave.getCommentUserId(),
            commentToSave.getParentCommentId(),
            commentEntity);
      }
   }
   
   @Override
   public List<VisitorCommentDataModel> getViewableArticleComments(String articleId)
   {
      int maxDisplayedComments = getMaxCommentsForDisplay();
      
      List<VisitorCommentDataModel> retVals = new ArrayList<VisitorCommentDataModel>();
      if (!StringUtils.isEmpty(articleId))
      {
         List<VisitorComment> comments = _commentsRepo.loadArticleViewableComments(articleId, maxDisplayedComments);

         retVals
            = CommentsDataModelEntityMapping.toDataModels_ArticleComments(comments);
      } 
      return retVals;
   }
   
   @Override
   public UserCommentsPageDataModel getUnapprovedComments(int pageIdx)
   {
      int adminCommentsPerPageCount = getAdminCommentsCountForDisplay();
      
      long unapprovedCommentsCount = _commentsRepo.getUnapprovedCommentsCount();
      
      List<VisitorComment> comments = _commentsRepo.getUnapprovedComments(pageIdx,
         adminCommentsPerPageCount);
      
      List<VisitorCommentDataModel> visitorComments
         = CommentsDataModelEntityMapping.toDataModels_UserComments(comments);

      UserCommentsPageDataModel retVal
         = new UserCommentsPageDataModel();
      if (visitorComments != null && visitorComments.size() > 0)
      {
         ItemListPageDataModel.<UserCommentsPageDataModel>createPageModel(retVal, visitorComments.size(),
            (int)unapprovedCommentsCount, pageIdx,
            adminCommentsPerPageCount);
         retVal.setUserComments(visitorComments);
         
         return retVal;
      }

      ItemListPageDataModel.createEmptyPageDataModel(retVal);
      return retVal;
   }
   
   @Override
   public List<VisitorCommentDataModel> getArticleComments(String articleId, int pageIdx)
   {
      int adminCommentsPerPageCount = getAdminCommentsCountForDisplay();
      
      List<VisitorCommentDataModel> retVals = new ArrayList<VisitorCommentDataModel>();
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
   
   @Override
   public boolean approveComment(String commentId)
   {
      if (!StringUtils.isEmpty(commentId))
      {
         return this._commentsRepo.approveComment(commentId);
      }
      
      return false;
   }
   
   @Override
   public boolean deleteComment(String commentId)
   {
      if (!StringUtils.isEmpty(commentId))
      {
         return this._commentsRepo.deleteComment(commentId);
      }
      
      return false;
   }
   
   @Override
   public CommentJsonDataModel loadComment(String commentId)
   {
      CommentJsonDataModel retVal = null;
      if (!StringUtils.isEmpty(commentId))
      {
         VisitorComment foundObj = this._commentsRepo.loadComment(commentId);
         if (foundObj != null)
         {
            retVal = new CommentJsonDataModel();
            retVal.setCommenTitle(foundObj.getTitle());
            retVal.setCommentContent(foundObj.getContent());
            
            if (foundObj.getOwner() != null)
            {
               retVal.setCommenterName(foundObj.getOwner().getUserName());
               retVal.setCommenterEmail(foundObj.getOwner().getUserEmail());
            }
            else
            {
               retVal.setCommenterName(foundObj.getCommenter());
               retVal.setCommenterEmail(foundObj.getCommenterEmail());
            }
         }
      }
      
      return retVal;
   }
   
   private List<VisitorCommentDataModel> associateArticleComments(List<VisitorCommentDataModel> unorderedCommentsList)
   {
      List<VisitorCommentDataModel> retVals = new ArrayList<VisitorCommentDataModel>();
      if (unorderedCommentsList != null && !unorderedCommentsList.isEmpty())
      {
         Map<String, VisitorCommentDataModel> commentsAssociation = new HashMap<String, VisitorCommentDataModel>();
         for (VisitorCommentDataModel comment : unorderedCommentsList)
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
                     VisitorCommentDataModel parentComment = commentsAssociation.get(parentCommentId);
                     if (parentComment != null)
                     {
                        parentComment.getChildComments().add(comment);
                     }
                  }
               }
            }
         }
         
         for (Map.Entry<String, VisitorCommentDataModel> entry : commentsAssociation.entrySet())
         {
            if (entry != null && entry.getValue() != null)
            {
               VisitorCommentDataModel comment = entry.getValue();
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

   @Override
   public void addImageComment(VisitorCommentDataModel comment) {
      // TODO Auto-generated method stub
   }
   
   @Override
   public List<VisitorCommentDataModel> getViewableImageComments(String imageId) {
      // TODO Auto-generated method stub
      return null;
   }

   @Override
   public VisitorCommentDataModel loadArticleComment(String articleId, String commentId)
   {
      if (!StringUtils.isEmpty(articleId) && !StringUtils.isEmpty(commentId))
      {
         VisitorComment comment = 
            _commentsRepo.loadArticleComment(articleId, commentId);
         
         VisitorCommentDataModel retVal = 
            CommentsDataModelEntityMapping.toDataModel_ArticleComment(comment);
         
         return retVal;
      }
      
      return null;
   }
}
