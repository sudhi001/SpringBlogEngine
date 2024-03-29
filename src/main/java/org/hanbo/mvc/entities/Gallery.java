package org.hanbo.mvc.entities;

import java.util.Date;
import java.util.HashSet;
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

   @Column(name = "gallerytitle", nullable = false, length = 96)
   @Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
   private String galleryTitle;
   
   @Column(name = "gallerydescription", nullable = true, length = 3072)
   @Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
   private String galleryDescription;

   @Column(name = "gallerykeywords", nullable = true, length = 128)
   @Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
   private String galleryKeywords;
   
   @Column(name = "active", nullable = false)
   private boolean active;

   @Column(name = "visible", nullable = false)
   private boolean visible;
   
   @Column(name = "createdate", nullable = false)
   @Temporal(TemporalType.TIMESTAMP)
   private Date createDate;
   
   @Column(name = "updatedate", nullable = false)
   @Temporal(TemporalType.TIMESTAMP)
   private Date updateDate;
   
   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "ownerid")
   private LoginUser owner;
   
   @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
   @JoinTable(name = "imagetogallery", joinColumns = { 
      @JoinColumn(name = "galleryid", nullable = false, updatable = false) }, 
      inverseJoinColumns = { @JoinColumn(name = "imageid", 
         nullable = false, updatable = false) })
   private Set<Image> galleryImages;

   public Gallery()
   {
      setActive(true);
      setVisible(true);
      galleryImages = new HashSet<Image>(); 
   }
   
   public String getId()
   {
      return id;
   }

   public void setId(String id)
   {
      this.id = id;
   }

   public String getGalleryTitle()
   {
      return galleryTitle;
   }

   public void setGalleryTitle(String title)
   {
      this.galleryTitle = title;
   }

   public String getGalleryDescription()
   {
      return galleryDescription;
   }

   public void setGalleryDescription(String description)
   {
      this.galleryDescription = description;
   }

   public String getGalleryKeywords()
   {
      return galleryKeywords;
   }

   public void setGalleryKeywords(String keywords)
   {
      this.galleryKeywords = keywords;
   }
   
   public Date getCreateDate()
   {
      return this.createDate;
   }

   public void setCreateDate(Date createDate)
   {
      this.createDate = createDate;
   }
   
   public Date getUpdateDate()
   {
      return this.updateDate;
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

   public Set<Image> getAssociatedImages()
   {
      return galleryImages;
   }

   public void setAssociatedImages(Set<Image> galleryImages)
   {
      this.galleryImages = galleryImages;
   }

   public boolean isActive()
   {
      return active;
   }

   public void setActive(boolean active)
   {
      this.active = active;
   }

   public boolean isVisible()
   {
      return visible;
   }

   public void setVisible(boolean visible)
   {
      this.visible = visible;
   }
}
