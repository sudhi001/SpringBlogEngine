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

@Entity
@Table(name = "visitorlike")
public class VisitorLike
{
   @Id
   @Column(name = "id", nullable = false, length = 45)
   private String id;

   @Column(name = "likeit", nullable = false)
   private boolean likeIt;

   @Column(name = "sourceip", nullable = false, length = 39)
   private String sourceIp;
   
   @Column(name = "createdate", nullable = false)
   @Temporal(TemporalType.TIMESTAMP)
   private Date createDate;
   
   @Column(name = "updatedate", nullable = false)
   @Temporal(TemporalType.TIMESTAMP)
   private Date updateDate;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "articleid")
   private Article relatedArticle;
   
   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "imageid")
   private Image relatedImage;

   public String getId()
   {
      return id;
   }

   public void setId(String id)
   {
      this.id = id;
   }

   public boolean isLikeIt()
   {
      return likeIt;
   }

   public void setLikeIt(boolean likeIt)
   {
      this.likeIt = likeIt;
   }

   public String getSourceIp()
   {
      return sourceIp;
   }

   public void setSourceIp(String sourceIp)
   {
      this.sourceIp = sourceIp;
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

   public Article getRelatedArticle()
   {
      return relatedArticle;
   }

   public void setRelatedArticle(Article relatedArticle)
   {
      this.relatedArticle = relatedArticle;
   }

   public Image getRelatedImage()
   {
      return relatedImage;
   }

   public void setRelatedImage(Image relatedImage)
   {
      this.relatedImage = relatedImage;
   }
}
