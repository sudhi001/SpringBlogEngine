package org.hanbo.mvc.entities;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "resource")
@Inheritance(strategy = InheritanceType.JOINED) 
public class Resource
{   
   @Id
   @Column(name = "id", nullable = false, length = 45)
   private String id;

   @Column(name = "name", nullable = false, length = 96)
   private String name;

   @Column(name = "restype", nullable = false, length = 24)
   private String resType;
   
   @Column(name = "createdate", nullable = false)
   @Temporal(TemporalType.TIMESTAMP)
   private Date createDate;

   @Column(name = "updatedate", nullable = false)
   @Temporal(TemporalType.TIMESTAMP)
   private Date updateDate;
   
   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "ownerid")
   private LoginUser owner;
   
   @OneToOne(fetch = FetchType.LAZY, mappedBy="iconResource", cascade={CascadeType.ALL})
   private ArticleIcon articleIcon;
   
   public String getId()
   {
      return id;
   }

   public void setId(String id)
   {
      this.id = id;
   }

   public String getName()
   {
      return name;
   }

   public void setName(String name)
   {
      this.name = name;
   }

   public String getResourceType()
   {
      return resType;
   }

   public void setResourceType(String resType)
   {
      this.resType = resType;
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

   public LoginUser getOwner()
   {
      return owner;
   }

   public void setOwner(LoginUser owner)
   {
      this.owner = owner;
   }

   public ArticleIcon getArticleIcon()
   {
      return articleIcon;
   }

   public void setArticleIcon(ArticleIcon articleIcon)
   {
      this.articleIcon = articleIcon;
   }
}
