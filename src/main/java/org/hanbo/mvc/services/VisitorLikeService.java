package org.hanbo.mvc.services;

public interface VisitorLikeService
{
   void addVisitorLikeToArticle(String articleId, boolean likeIt, String sourceIp);
}
