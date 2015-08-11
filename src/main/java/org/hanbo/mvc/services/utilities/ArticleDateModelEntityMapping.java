package org.hanbo.mvc.services.utilities;

import org.hanbo.mvc.entities.Article;
import org.hanbo.mvc.models.ArticleDataModel;

public class ArticleDateModelEntityMapping
{
   public static Article fromDataModel(ArticleDataModel articleDataModel)
   {
      Article retVal = new Article();
      
      retVal.setArticleType(articleDataModel.getArticleType());
      retVal.setCategory(articleDataModel.getArticleCategory());
      retVal.setContent(articleDataModel.getArticleContent());
      retVal.setKeywords(articleDataModel.getArticleKeywords());
      retVal.setPublished(articleDataModel.isArticlePublished());
      retVal.setSummary(articleDataModel.getArticleSummary());
      retVal.setTitle(articleDataModel.getArticleTitle());
      
      retVal.setCreateDate(articleDataModel.getArticleCreateDate());
      retVal.setUpdateDate(articleDataModel.getArticleUpdateDate());
      
      return retVal;
   }
   
   public static void updateExistingEntity(
      ArticleDataModel articleDataModel, Article articleToUpdate)
   {
      articleToUpdate.setArticleType(articleDataModel.getArticleType());
      articleToUpdate.setCategory(articleDataModel.getArticleCategory());
      articleToUpdate.setContent(articleDataModel.getArticleContent());
      articleToUpdate.setKeywords(articleDataModel.getArticleKeywords());
      articleToUpdate.setPublished(articleDataModel.isArticlePublished());
      articleToUpdate.setSummary(articleDataModel.getArticleSummary());
      articleToUpdate.setTitle(articleDataModel.getArticleTitle());
      
      articleToUpdate.setCreateDate(articleDataModel.getArticleCreateDate());
      articleToUpdate.setUpdateDate(articleDataModel.getArticleUpdateDate());      
   }
   
   
   public static ArticleDataModel toDataModel(Article article)
   {
      ArticleDataModel retVal = new ArticleDataModel();
      
      retVal.setArticleCategory(article.getCategory());
      retVal.setArticleContent(article.getContent());
      retVal.setArticleCreateDate(article.getCreateDate());
      retVal.setArticleId(article.getId());
      retVal.setArticleKeywords(article.getKeywords());
      retVal.setArticlePublished(article.isPublished());
      retVal.setArticleSummary(article.getSummary());
      retVal.setArticleTitle(article.getTitle());
      retVal.setArticleType(article.getArticleType());
      retVal.setArticleUpdateDate(article.getUpdateDate());
      retVal.setAuthorId(article.getAuthor().getId());
      retVal.setAuthorName(article.getAuthor().getUserName());
      
      return retVal;
   }
}
