package org.hanbo.mvc.models;

import java.util.Date;
import org.hanbo.mvc.utilities.DateToString;

public class SimplifiedArticleDataModel
{
   private String articleId;
   private String articleTitle;
   private String articleCategory;
   private String articleKeywords;
   private String articleSummary;
   private String articleType;
   private boolean articlePublished;
   
   private Date articleCreateDate;
   private Date articleUpdateDate;
   
   private String authorId;
   private String authorUserName;   
   private String authorName;
   private String authorIconId;
   
   public SimplifiedArticleDataModel()
   {      
   }
   
   public String getArticleId()
   {
      return articleId;
   }
   
   public void setArticleId(String articleId)
   {
      this.articleId = articleId;
   }
   
   public String getArticleTitle()
   {
      return articleTitle;
   }
   
   public void setArticleTitle(String articleTitle)
   {
      this.articleTitle = articleTitle;
   }
   
   public String getArticleCategory()
   {
      return articleCategory;
   }
   
   public void setArticleCategory(String articleCategory)
   {
      this.articleCategory = articleCategory;
   }
   
   public String getArticleKeywords()
   {
      return articleKeywords;
   }
   
   public String[] getArticleKeywordList()
   {
      String[] retVals = articleKeywords.split(",");
      return retVals;
   }

   public void setArticleKeywords(String aricleKeywords)
   {
      this.articleKeywords = aricleKeywords;
   }
   
   public String getArticleSummary()
   {
      return articleSummary;
   }
   
   public void setArticleSummary(String articleSummary)
   {
      this.articleSummary = articleSummary;
   }
   
   public Date getArticleCreateDate()
   {
      return articleCreateDate;
   }
   
   public String getArticleCreateDateString()
   {
      return 
      DateToString.dateToString(articleCreateDate, "MM/dd/yyyy HH:mm:ss");
   }
   
   public void setArticleCreateDate(Date articleCreateDate)
   {
      this.articleCreateDate = articleCreateDate;
   }
   
   public Date getArticleUpdateDate()
   {
      return articleUpdateDate;
   }
   
   public String getArticleUpdateDateString()
   {
      return 
      DateToString.dateToString(articleUpdateDate, "MM/dd/yyyy HH:mm:ss");
   }

   public void setArticleUpdateDate(Date articleUpdateDate)
   {
      this.articleUpdateDate = articleUpdateDate;
   }
   
   public String getArticleType()
   {
      return articleType;
   }

   public void setArticleType(String articleType)
   {
      this.articleType = articleType;
   }
   
   public String getAuthorId()
   {
      return authorId;
   }
   
   public void setAuthorId(String authorId)
   {
      this.authorId = authorId;
   }
   
   public String getAuthorName()
   {
      return authorName;
   }
   
   public void setAuthorName(String authorName)
   {
      this.authorName = authorName;
   }

   public boolean isArticlePublished()
   {
      return articlePublished;
   }

   public void setArticlePublished(boolean articlePublished)
   {
      this.articlePublished = articlePublished;
   }

   public String getAuthorIconId()
   {
      return authorIconId;
   }

   public void setAuthorIconId(String authorIconId)
   {
      this.authorIconId = authorIconId;
   }

   public String getAuthorUserName()
   {
      return authorUserName;
   }

   public void setAuthorUserName(String authorUserName)
   {
      this.authorUserName = authorUserName;
   }
}
