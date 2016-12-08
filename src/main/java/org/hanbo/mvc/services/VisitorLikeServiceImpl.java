package org.hanbo.mvc.services;

import org.hanbo.mvc.models.json.VisitorLikeStatistics;
import org.hanbo.mvc.repositories.VisitorLikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.lang.StringUtils;

@Service
public class VisitorLikeServiceImpl
   implements VisitorLikeService
{
   @Autowired
   private VisitorLikeRepository _visitorLikeRepo;

   @Override
   public void addVisitorLikeToArticle(String articleId, boolean likeIt, String sourceIp)
   {
      if (_visitorLikeRepo != null
         && !StringUtils.isEmpty(articleId)
         && !StringUtils.isEmpty(sourceIp))
      {
         _visitorLikeRepo.addVisitorLikeToArticle(articleId, likeIt, sourceIp);
      }
   }
   
   @Override
   public VisitorLikeStatistics getArticleVisitorLikeStatistics(String articleId)
   {
      if (_visitorLikeRepo != null
         && !StringUtils.isEmpty(articleId))
      {
         VisitorLikeStatistics retVal = new VisitorLikeStatistics();
         
         long likeCount = _visitorLikeRepo.getArticleVisitorLikesCount(articleId);
         long dislikeCount = _visitorLikeRepo.getArticleVisitorDislikesCount(articleId);
         
         retVal.setArticleId(articleId);
         retVal.setLikeCount(likeCount);
         retVal.setDislikeCount(dislikeCount);
         
         return retVal;
      }
      
      return null;
   }
   
   @Override
   public void addVisitorLikeToImage(String imageId, boolean likeIt, String sourceIp)
   {
      if (_visitorLikeRepo != null
         && !StringUtils.isEmpty(imageId)
         && !StringUtils.isEmpty(sourceIp))
      {
         _visitorLikeRepo.addVisitorLikeToImage(imageId, likeIt, sourceIp);
      }
   }

   @Override
   public VisitorLikeStatistics getImageVisitorLikeStatistics(String imageId)
   {
      if (_visitorLikeRepo != null
         && !StringUtils.isEmpty(imageId))
      {
         VisitorLikeStatistics retVal = new VisitorLikeStatistics();
         
         long likeCount = _visitorLikeRepo.getImageVisitorLikesCount(imageId);
         long dislikeCount = _visitorLikeRepo.getImageVisitorDislikesCount(imageId);
         
         retVal.setArticleId(imageId);
         retVal.setLikeCount(likeCount);
         retVal.setDislikeCount(dislikeCount);
         
         return retVal;
      }
      
      return null;
   }
}
