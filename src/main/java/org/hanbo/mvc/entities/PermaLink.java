package org.hanbo.mvc.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "permalink")
public class PermaLink {
   @Id
   @Column(name = "id", nullable = false, length = 45)
   private String id;
   
   @Column(name = "path", nullable = false, length = 128)
   private String path;

   @Column(name = "pagereplacement", nullable = false)
   private boolean pageReplacement;
   
   @OneToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "articleid")
   private Article article;
   
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

   public String getPath()
   {
      return path;
   }

   public void setPath(String path)
   {
      this.path = path;
   }
   
   public boolean isPageReplacement()
   {
      return pageReplacement;
   }

   public void setPageReplacement(boolean pageReplacement)
   {
      this.pageReplacement = pageReplacement;
   }

   public Article getAssociatedArticle()
   {
      return article;
   }

   public void setAssociatedArticle(Article associatedArticle)
   {
      this.article = associatedArticle;
   }

   public LoginUser getAssociatedAuthor()
   {
      return author;
   }

   public void setAssociatedAuthor(LoginUser associatedAuthor)
   {
      this.author = associatedAuthor;
   }
}
