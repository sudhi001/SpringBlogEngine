package org.hanbo.mvc.entities;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
@Table(name = "gallery")
@Indexed
public class Gallery
{
   @Id
   @Column(name = "id", nullable = false, length = 45)
   private String id;

   @Column(name = "title", nullable = false, length = 96)
   @Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
   private String title;
   
   @Column(name = "description", nullable = true, length = 3072)
   @Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
   private String description;

   @Column(name = "keywords", nullable = true, length = 128)
   @Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
   private String keywords;
   
   @Column(name = "createdate", nullable = false)
   @Temporal(TemporalType.TIMESTAMP)
   private Date createDate;
   
   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "ownerid")
   private LoginUser owner;
   
   @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
   @JoinTable(name = "imagetogallery", joinColumns = { 
         @JoinColumn(name = "galleryid", nullable = false, updatable = false) }, 
         inverseJoinColumns = { @JoinColumn(name = "imageid", 
               nullable = false, updatable = false) })
   private Set<Image> galleryImages;

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

   public String getDescription()
   {
      return description;
   }

   public void setDescription(String description)
   {
      this.description = description;
   }

   public String getKeywords()
   {
      return keywords;
   }

   public void setKeywords(String keywords)
   {
      this.keywords = keywords;
   }
   
   public Date getCreateDate()
   {
      return this.createDate;
   }

   public void setCreateDate(Date createDate)
   {
      this.createDate = createDate;
   }
   
   public LoginUser getOwner()
   {
      return owner;
   }

   public void setOwner(LoginUser owner)
   {
      this.owner = owner;
   }

   public Set<Image> getAssociatedImages()
   {
      return galleryImages;
   }

   public void setAssociatedImages(Set<Image> galleryImages)
   {
      this.galleryImages = galleryImages;
   }
}
