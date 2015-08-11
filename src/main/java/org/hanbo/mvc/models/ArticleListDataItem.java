package org.hanbo.mvc.models;

import java.util.Date;

public class ArticleListDataItem
{
   private String articleTitle;
   private String articleSummary;
   private Date createDate;
   private Date updateDate;
   
   private String authorId;
   private String authorName;
   
   public String getArticleTitle()
   {
      return articleTitle;
   }
   
   public void setArticleTitle(String articleTitle)
   {
      this.articleTitle = articleTitle;
   }
   
   public String getArticleSummary()
   {
      return articleSummary;
   }
   
   public void setArticleSummary(String articleSummary)
   {
      this.articleSummary = articleSummary;
   }
   
   public Date getCreateDate()
   {
      return createDate;
   }
   
   public void setCreateDate(Date createDate)
   {
      this.createDate = createDate;
   }
   
   public Date getUpdateDate()
   {
      return updateDate;
   }
   
   public void setUpdateDate(Date updateDate)
   {
      this.updateDate = updateDate;
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
}
