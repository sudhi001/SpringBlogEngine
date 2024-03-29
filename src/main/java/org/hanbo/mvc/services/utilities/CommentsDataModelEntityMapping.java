package org.hanbo.mvc.services.utilities;

import java.util.ArrayList;
import java.util.List;

import org.hanbo.mvc.entities.VisitorComment;
import org.hanbo.mvc.models.VisitorCommentDataModel;
import org.springframework.util.StringUtils;

public class CommentsDataModelEntityMapping
{
   public static VisitorCommentDataModel toDataModel_ArticleComment(VisitorComment commentEntity)
   {
      if (commentEntity != null)
      {
         VisitorCommentDataModel retVal = new VisitorCommentDataModel();
         if (retVal != null)
         {
            toDataModel_UserComment(commentEntity,  retVal);
            if (commentEntity.getRelatedArticle() != null)
            {   
               retVal.setRefObjectId(commentEntity.getRelatedArticle().getId());
            }
         }
         
         return retVal;
      }
      
      return null;
   }

   public static VisitorCommentDataModel toDataModel_UserComment(VisitorComment commentEntity, VisitorCommentDataModel retVal)
   {
      if (commentEntity != null && retVal != null)
      {
         retVal.setCommentApproved(commentEntity.isCommentApproved());
         retVal.setCommentPrivate(commentEntity.isCommentApproved());
         retVal.setCommentContent(commentEntity.getContent());
         retVal.setCommentCreateDate(commentEntity.getCreateDate());
         retVal.setCommenterEmail(commentEntity.getCommenterEmail());
         retVal.setCommenterName(commentEntity.getCommenter());
         retVal.setCommentId(commentEntity.getId());
         retVal.setCommentSourceIp(commentEntity.getSourceIp());
         retVal.setCommentTitle(commentEntity.getTitle());
         retVal.setCommentUpdateDate(commentEntity.getUpdateDate());
         if (commentEntity.getOwner() != null)
         {
            retVal.setCommentUserId(commentEntity.getOwner().getId());
            retVal.setCommentUserName(commentEntity.getOwner().getUserName());
            if (commentEntity.getOwner().getUserProfile() != null)
            {
               retVal.setCommentUserProfileId(commentEntity.getOwner().getUserProfile().getId());
               
               if (!StringUtils.isEmpty(commentEntity.getOwner().getUserProfile().getFirstName())
                  && !StringUtils.isEmpty(commentEntity.getOwner().getUserProfile().getLastName()))
               {
                  retVal.setCommentUserFullName(
                     String.format("%s %s", commentEntity.getOwner().getUserProfile().getFirstName(),
                        commentEntity.getOwner().getUserProfile().getLastName())
                  );
               }
               
               if (commentEntity.getOwner().getUserProfile().getUserIcon() != null)
               {
                  retVal.setCommentUserIconId(commentEntity.getOwner().getUserProfile().getUserIcon().getId());
               }
            }
         }
         
         if (commentEntity.getParentComment() != null)
         {
            retVal.setParentCommentId(commentEntity.getParentComment().getId());
         }
         
         return retVal;
      }
      
      return null;
   }

   
   public static List<VisitorCommentDataModel> toDataModels_ArticleComments(List<VisitorComment> commentEntities)
   {
      List<VisitorCommentDataModel> retVals = new ArrayList<VisitorCommentDataModel>();
      if (commentEntities != null && commentEntities.size() > 0)
      {
         for (VisitorComment commentEntity : commentEntities)
         {
            VisitorCommentDataModel dataModel = toDataModel_ArticleComment(commentEntity);
            
            if (dataModel != null)
            {
               retVals.add(dataModel);
            }
         }
      }
      
      return retVals;
   }
   
   public static List<VisitorCommentDataModel> toDataModels_UserComments(List<VisitorComment> commentEntities)
   {
      List<VisitorCommentDataModel> retVals = new ArrayList<VisitorCommentDataModel>();
      if (commentEntities != null && commentEntities.size() > 0)
      {
         for (VisitorComment commentEntity : commentEntities)
         {
            VisitorCommentDataModel dataModel = new VisitorCommentDataModel();
            VisitorCommentDataModel retValElem = toDataModel_UserComment(commentEntity, dataModel);
            
            if (retValElem != null)
            {
               retVals.add(retValElem);
            }
         }
      }
      
      return retVals;
   }
}
