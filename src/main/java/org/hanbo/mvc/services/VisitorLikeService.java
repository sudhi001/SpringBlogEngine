package org.hanbo.mvc.services;

import org.hanbo.mvc.models.json.ArticleVisitorLikeStatistics;

public interface VisitorLikeService
{
   void addVisitorLikeToArticle(String articleId, boolean likeIt, String sourceIp);

   ArticleVisitorLikeStatistics getArticleVisitorLikeStatistics(String articleId);
}
