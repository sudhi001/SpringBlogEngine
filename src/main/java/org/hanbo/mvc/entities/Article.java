package org.hanbo.mvc.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;

@Entity
@Table(name = "article")
@Indexed
public class Article
{
   @Id
   @Column(name = "id", nullable = false, length = 45)
   private String id;
   
   @Column(name = "title", nullable = false, length = 128)
   @Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
   private String title;
   
   @Column(name = "keywords", nullable = true, length = 128)
   @Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
   private String keywords;
   
   @Column(name = "category", nullable = true, length = 64)
   @Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
   private String category;

   @Column(name = "summary", nullable = true, length = 512)
   @Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
   private String summary;

   @Column(name = "content", nullable = true)
   @Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
   private String content;
   
   @Column(name = "createdate", nullable = false)
   @Temporal(TemporalType.TIMESTAMP)
   private Date createDate;
   
   @Column(name = "updatedate", nullable = false)
   @Temporal(TemporalType.TIMESTAMP)
   private Date updateDate;
   
   @Column(name = "articletype", nullable = false, length = 16)
   private String articleType;
   
   @Column(name = "published", nullable = false)
   private boolean published;
   
   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "authorid")
   private LoginUser author;

   public String getId()
   {
      return id;
   }

   public void setId(String id)
   {
      this.id = id;
   }

   public String getTitle()
   {
      return title;
   }

   public void setTitle(String title)
   {
      this.title = title;
   }

   public String getKeywords()
   {
      return keywords;
   }

   public void setKeywords(String keywords)
   {
      this.keywords = keywords;
   }

   public String getCategory()
   {
      return category;
   }

   public void setCategory(String category)
   {
      this.category = category;
   }

   public String getSummary()
   {
      return summary;
   }

   public void setSummary(String summary)
   {
      this.summary = summary;
   }

   public String getContent()
   {
      return content;
   }

   public void setContent(String content)
   {
      this.content = content;
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

   public LoginUser getAuthor()
   {
      return author;
   }

   public void setAuthor(LoginUser author)
   {
      this.author = author;
   }
   
   public String getArticleType()
   {
      return articleType;
   }

   public void setArticleType(String articleType)
   {
      this.articleType = articleType;
   }

   public boolean isPublished()
   {
      return published;
   }

   public void setPublished(boolean published)
   {
      this.published = published;
   }
   
   @Override
   public int hashCode()
   {
      return id.hashCode();
   }
}
