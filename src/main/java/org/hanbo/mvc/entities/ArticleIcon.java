package org.hanbo.mvc.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "articleicon")
public class ArticleIcon
{
   @Id
   @Column(name = "id", nullable = false, length = 45)
   private String id;
   
   @OneToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "articleid")
   private Article article;
      
   @OneToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "resourceid")
   private Resource iconResource;

   public String getId()
   {
      return id;
   }

   public void setId(String id)
   {
      this.id = id;
   }

   public Article getArticle()
   {
      return article;
   }

   public void setArticle(Article article)
   {
      this.article = article;
   }

   public Resource getIconResource()
   {
      return iconResource;
   }

   public void setIconResource(Resource iconResource)
   {
      this.iconResource = iconResource;
   }
}
