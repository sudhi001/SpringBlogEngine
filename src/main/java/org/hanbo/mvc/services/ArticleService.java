package org.hanbo.mvc.services;

import org.hanbo.mvc.models.ArticleDataModel;
import org.hanbo.mvc.models.ArticleListPageDataModel;

public interface ArticleService
{
   ArticleDataModel saveArticle(ArticleDataModel articleDataModel);
   
   ArticleDataModel getArticleById(String articleId);

   ArticleDataModel getArticleById(String articleId, String authorId);

   ArticleDataModel updateArticle(ArticleDataModel articleDataModel);
   
   ArticleListPageDataModel getUserArticleList(
      int pageIdx, String authorId
   );
   
   void deleteOwnerArticle(String articleId, String ownerId);
   
   void publishOwnerArticle(String articleId, String ownerId, boolean articleToPublish);
   
   ArticleListPageDataModel getAllViewablePosts(int pageIdx);
   
   String createBlogPostFromImage(String ownerId, String imageId,
      String postTitle, String postKeywords, String postContent, String basedUrlPath);
}
