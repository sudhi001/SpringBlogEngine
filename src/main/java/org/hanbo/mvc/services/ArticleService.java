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
}
