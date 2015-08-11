package org.hanbo.mvc.repositories;

import org.hanbo.mvc.entities.Article;

public interface ArticlesRepository
{
   void saveArticle(Article article);
   
   Article getReportById(String articleId);
   
   Article getReportById(String articleId, String authorId);
}
