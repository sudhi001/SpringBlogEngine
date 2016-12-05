package org.hanbo.mvc.services;

import org.hanbo.mvc.models.json.ArticleVisitorLikeStatistics;
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
   public ArticleVisitorLikeStatistics getArticleVisitorLikeStatistics(String articleId)
   {
      if (_visitorLikeRepo != null
         && !StringUtils.isEmpty(articleId))
      {
         ArticleVisitorLikeStatistics retVal = new ArticleVisitorLikeStatistics();
         
         long likeCount = _visitorLikeRepo.getArticleVisitorLikesCount(articleId);
         long dislikeCount = _visitorLikeRepo.getArticleVisitorDislikesCount(articleId);
         
         retVal.setArticleId(articleId);
         retVal.setLikeCount(likeCount);
         retVal.setDislikeCount(dislikeCount);
         
         return retVal;
      }
      
      return null;
   }
}
