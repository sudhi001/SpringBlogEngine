package org.hanbo.mvc.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.CascadeType;

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
   
   @Column(name = "articletitle", nullable = false, length = 128)
   @Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
   private String articleTitle;
   
   @Column(name = "articlekeywords", nullable = true, length = 128)
   @Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
   private String articleKeywords;
   
   @Column(name = "articlecategory", nullable = true, length = 64)
   @Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
   private String articleCategory;

   @Column(name = "articlesummary", nullable = true, length = 512)
   @Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
   private String articleSummary;

   @Column(name = "articlecontent", nullable = true)
   @Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
   private String articleContent;
   
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
   
   @OneToOne(fetch = FetchType.LAZY, mappedBy="article", cascade={CascadeType.ALL})
   private PermaLink permaLinks;

   @OneToOne(fetch = FetchType.LAZY, mappedBy="article")
   private ArticleIcon articleIcon;
   
   @OneToMany(fetch = FetchType.LAZY, mappedBy = "relatedArticle")
   private Set<VisitorComment> relatedComments;
   
   public Article()
   {
      relatedComments = new HashSet<VisitorComment>();
   }
   
   public String getId()
   {
      return id;
   }

   public void setId(String id)
   {
      this.id = id;
   }

   public String getArticleTitle()
   {
      return articleTitle;
   }

   public void setArticleTitle(String title)
   {
      this.articleTitle = title;
   }

   public String getArticleKeywords()
   {
      return articleKeywords;
   }

   public void setArticleKeywords(String keywords)
   {
      this.articleKeywords = keywords;
   }

   public String getArticleCategory()
   {
      return articleCategory;
   }

   public void setArticleCategory(String category)
   {
      this.articleCategory = category;
   }

   public String getArticleSummary()
   {
      return articleSummary;
   }

   public void setArticleSummary(String summary)
   {
      this.articleSummary = summary;
   }

   public String getArticleContent()
   {
      return articleContent;
   }

   public void setArticleContent(String content)
   {
      this.articleContent = content;
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

   public PermaLink getPermaLinks()
   {
      return permaLinks;
   }

   public void setPermaLinks(PermaLink permaLinks)
   {
      this.permaLinks = permaLinks;
   }

   public ArticleIcon getArticleIcon()
   {
      return articleIcon;
   }

   public void setArticleIcon(ArticleIcon articleIcon)
   {
      this.articleIcon = articleIcon;
   }

   public Set<VisitorComment> getRelatedComments()
   {
      return relatedComments;
   }

   public void setRelatedComments(Set<VisitorComment> relatedComments)
   {
      this.relatedComments = relatedComments;
   }
}
