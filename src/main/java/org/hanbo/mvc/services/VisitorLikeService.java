package org.hanbo.mvc.services;

import org.hanbo.mvc.models.json.VisitorLikeStatistics;

public interface VisitorLikeService
{
   void addVisitorLikeToArticle(String articleId, boolean likeIt, String sourceIp);

   VisitorLikeStatistics getArticleVisitorLikeStatistics(String articleId);

   void addVisitorLikeToImage(String imageId, boolean likeIt, String sourceIp);

   VisitorLikeStatistics getImageVisitorLikeStatistics(String imageId);
}
