package org.hanbo.mvc.services.utilities;

import java.util.ArrayList;
import java.util.List;

import org.hanbo.mvc.entities.Article;
import org.hanbo.mvc.entities.ArticleIcon;
import org.hanbo.mvc.models.ArticleDataModel;
import org.hanbo.mvc.models.SimplifiedArticleDataModel;

public class ArticleDataModelEntityMapping
{
   public static Article fromDataModel(ArticleDataModel articleDataModel)
   {
      Article retVal = new Article();
      
      retVal.setArticleType(articleDataModel.getArticleType());
      retVal.setArticleCategory(articleDataModel.getArticleCategory());
      retVal.setArticleContent(articleDataModel.getArticleContent());
      retVal.setArticleKeywords(articleDataModel.getArticleKeywords());
      retVal.setPublished(articleDataModel.isArticlePublished());
      retVal.setArticleSummary(articleDataModel.getArticleSummary());
      retVal.setArticleTitle(articleDataModel.getArticleTitle());
      
      retVal.setCreateDate(articleDataModel.getArticleCreateDate());
      retVal.setUpdateDate(articleDataModel.getArticleUpdateDate());
      
      return retVal;
   }
   
   public static void updateExistingEntity(
      ArticleDataModel articleDataModel, Article articleToUpdate)
   {
      articleToUpdate.setArticleType(articleDataModel.getArticleType());
      articleToUpdate.setArticleCategory(articleDataModel.getArticleCategory());
      articleToUpdate.setArticleContent(articleDataModel.getArticleContent());
      articleToUpdate.setArticleKeywords(articleDataModel.getArticleKeywords());
      articleToUpdate.setPublished(articleDataModel.isArticlePublished());
      articleToUpdate.setArticleSummary(articleDataModel.getArticleSummary());
      articleToUpdate.setArticleTitle(articleDataModel.getArticleTitle());
      
      articleToUpdate.setCreateDate(articleDataModel.getArticleCreateDate());
      articleToUpdate.setUpdateDate(articleDataModel.getArticleUpdateDate());      
   }
   
   
   public static ArticleDataModel toDataModel(Article article)
   {
      ArticleDataModel retVal = new ArticleDataModel();
      
      retVal.setArticleCategory(article.getArticleCategory());
      retVal.setArticleContent(article.getArticleContent());
      retVal.setArticleCreateDate(article.getCreateDate());
      retVal.setArticleId(article.getId());
      retVal.setArticleKeywords(article.getArticleKeywords());
      retVal.setArticlePublished(article.isPublished());
      retVal.setArticleSummary(article.getArticleSummary());
      retVal.setArticleTitle(article.getArticleTitle());
      retVal.setArticleType(article.getArticleType());
      retVal.setArticleUpdateDate(article.getUpdateDate());

      setArticleAuthorInfo(article, retVal);
      
      return retVal;
   }

   private static void setArticleAuthorInfo(Article article, SimplifiedArticleDataModel retVal)
   {
      if (article != null && retVal != null && article.getAuthor() != null)
      {
         retVal.setAuthorId(article.getAuthor().getId());
         retVal.setAuthorUserName(article.getAuthor().getUserName());
         
         if (article.getAuthor().getUserProfile() != null)
         {
            retVal.setAuthorName(String.format("%s %s",
               article.getAuthor().getUserProfile().getFirstName(),
               article.getAuthor().getUserProfile().getLastName()));
            
            if (article.getAuthor().getUserProfile().getUserIcon() != null)
            {
               retVal.setAuthorIconId(article.getAuthor()
                  .getUserProfile()
                  .getUserIcon()
                  .getId());
            }
         }
      }
   }

   public static List<SimplifiedArticleDataModel> toDataListItems(List<Article> articles)
   {
      List<SimplifiedArticleDataModel> retVals
         = new ArrayList<SimplifiedArticleDataModel>();
      
      for (Article article : articles)
      {
         SimplifiedArticleDataModel articleDataModel
            = toDataListItem(article);
         
         retVals.add(articleDataModel);
      }
      
      return retVals;
   }
   
   public static SimplifiedArticleDataModel toDataListItem(Article article)
   {
      SimplifiedArticleDataModel retVal = new SimplifiedArticleDataModel();
      retVal.setArticleId(article.getId());
      retVal.setArticlePublished(article.isPublished());
      retVal.setArticleSummary(article.getArticleSummary());
      retVal.setArticleTitle(article.getArticleTitle());
      retVal.setArticleType(article.getArticleType());
      retVal.setArticleCreateDate(article.getCreateDate());
      retVal.setArticleUpdateDate(article.getUpdateDate());
      
      setArticleAuthorInfo(article, retVal);
      
      return retVal;
   }
   
   public static List<SimplifiedArticleDataModel> toDataListItemsWithIcons(List<ArticleIcon> articleIcons)
   {
      List<SimplifiedArticleDataModel> retVals
         = new ArrayList<SimplifiedArticleDataModel>();
      if (articleIcons != null)
      {
         for (ArticleIcon articleIcon : articleIcons)
         {
            SimplifiedArticleDataModel articleDataModel
               = toDataListItem(articleIcon);
            
            retVals.add(articleDataModel);
         }
      }
      
      return retVals;
   }
   
   public static SimplifiedArticleDataModel toDataListItem(ArticleIcon articleIcon)
   {
      SimplifiedArticleDataModel retVal = new SimplifiedArticleDataModel();
      
      if (articleIcon != null)
      {
         if (articleIcon.getArticle() != null)
         {
            retVal.setArticleId(articleIcon.getArticle().getId());
            retVal.setArticlePublished(articleIcon.getArticle().isPublished());
            retVal.setArticleSummary(articleIcon.getArticle().getArticleSummary());
            retVal.setArticleTitle(articleIcon.getArticle().getArticleTitle());
            retVal.setArticleType(articleIcon.getArticle().getArticleType());
            retVal.setArticleCreateDate(articleIcon.getArticle().getCreateDate());
            retVal.setArticleUpdateDate(articleIcon.getArticle().getUpdateDate());
            
            if (articleIcon.getArticle().getAuthor() != null)
            {
               setArticleAuthorInfo(articleIcon.getArticle(), retVal);
            }
            
            if (articleIcon.getArticleIcon() != null)
            {
               retVal.setArticleIconId(articleIcon.getArticleIcon().getId());
            }
         }
      }
      
      return retVal;
   }
}
