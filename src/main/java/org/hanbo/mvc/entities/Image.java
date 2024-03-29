package org.hanbo.mvc.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;

@Entity
@Table(name = "image")
@Indexed
public class Image
{
   @Id
   @Column(name = "id", nullable = false, length = 45)
   private String id;
   
   @Column(name = "imagetitle", nullable = true, length = 96)
   @Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
   private String imageTitle;
   
   @Column(name = "imagekeywords", nullable = true, length = 128)
   @Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
   private String imageKeywords;

   @Column(name = "imagename", unique = true, nullable = false, length = 64)
   @Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
   private String imageName;
   
   @Column(name = "filepath", nullable = false, length = 256)
   private String filePath;
   
   @Column(name = "thumbnailfilepath", nullable = false, length = 256)
   private String thumbnailFilePath;

   @Column(name = "active", nullable = false)
   private boolean active;
   
   @Column(name = "isnsfw", nullable = false)
   private boolean notSafeForWork;
   
   @Column(name = "uploaddate", nullable = false)
   @Temporal(TemporalType.TIMESTAMP)
   private Date uploadDate;
   
   @Column(name = "sizex", nullable = false)
   private int fileSizeX;
   
   @Column(name = "sizey", nullable = false)
   private int fileSizeY;
   
   @Column(name = "thumb_sizex", nullable = false)
   private int thumbnailSizeX;
   
   @Column(name = "thumb_sizey", nullable = false)
   private int thumbnailSizeY;
   
   @Column(name = "snapshot_sizex", nullable = true)
   private int snapshotSizeX;
   
   @Column(name = "snapshot_sizey", nullable = true)
   private int snapshotSizeY;
   
   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "ownerid")
   private LoginUser owner;
   
   @ManyToMany(fetch = FetchType.LAZY, mappedBy = "galleryImages")
   private Set<Gallery> associatedGalleries;

   @OneToMany(fetch = FetchType.LAZY, mappedBy = "relatedImage")
   private Set<VisitorComment> relatedComments;
   
   @OneToMany(fetch = FetchType.LAZY, mappedBy = "relatedImage")
   private Set<VisitorLike> relatedLikes;
   
   public Image()
   {
      active = true;
      associatedGalleries = new HashSet<Gallery>();
      relatedComments = new HashSet<VisitorComment>();
      relatedLikes = new HashSet<VisitorLike>();
   }
   
   public String getId()
   {
      return id;
   }

   public void setId(String id)
   {
      this.id = id;
   }

   public String getImageTitle()
   {
      return imageTitle;
   }

   public void setImageTitle(String title)
   {
      this.imageTitle = title;
   }
   
   public String getImageKeywords()
   {
      return imageKeywords;
   }

   public void setImageKeywords(String keywords)
   {
      this.imageKeywords = keywords;
   }

   public String getImageName()
   {
      return imageName;
   }

   public void setImageName(String imageName)
   {
      this.imageName = imageName;
   }

   public String getFilePath()
   {
      return filePath;
   }

   public void setFilePath(String filePath)
   {
      this.filePath = filePath;
   }

   public String getThumbnailFilePath()
   {
      return thumbnailFilePath;
   }

   public void setThumbnailFilePath(String thumbnailFilePath)
   {
      this.thumbnailFilePath = thumbnailFilePath;
   }

   public Date getUploadDate()
   {
      return uploadDate;
   }

   public void setUploadDate(Date uploadDate)
   {
      this.uploadDate = uploadDate;
   }

   public int getFileSizeX()
   {
      return fileSizeX;
   }

   public void setFileSizeX(int fileSizeX)
   {
      this.fileSizeX = fileSizeX;
   }

   public int getFileSizeY()
   {
      return fileSizeY;
   }

   public void setFileSizeY(int fileSizeY)
   {
      this.fileSizeY = fileSizeY;
   }

   public int getThumbnailSizeX()
   {
      return thumbnailSizeX;
   }

   public void setThumbnailSizeX(int thumbnailSizeX)
   {
      this.thumbnailSizeX = thumbnailSizeX;
   }

   public int getThumbnailSizeY()
   {
      return thumbnailSizeY;
   }

   public void setThumbnailSizeY(int thumbnailSizeY)
   {
      this.thumbnailSizeY = thumbnailSizeY;
   }

   public int getSnapshotSizeX()
   {
      return snapshotSizeX;
   }

   public void setSnapshotSizeX(int snapshotSizeX)
   {
      this.snapshotSizeX = snapshotSizeX;
   }

   public int getSnapshotSizeY()
   {
      return snapshotSizeY;
   }

   public void setSnapshotSizeY(int snapshotSizeY)
   {
      this.snapshotSizeY = snapshotSizeY;
   }

   public LoginUser getOwner()
   {
      return owner;
   }

   public void setOwner(LoginUser owner)
   {
      this.owner = owner;
   }
   
   public Set<Gallery> getAssociatedGalleries()
   {
      return associatedGalleries;
   }

   public void setAssociatedGalleries(Set<Gallery> associatedGalleries)
   {
      this.associatedGalleries = associatedGalleries;
   }

   public boolean isActive()
   {
      return active;
   }

   public void setActive(boolean active)
   {
      this.active = active;
   }
   
   public boolean isNotSafeForWork()
   {
      return notSafeForWork;
   }

   public void setNotSafeForWork(boolean notSafeForWork)
   {
      this.notSafeForWork = notSafeForWork;
   }

   public Set<VisitorComment> getRelatedComments()
   {
      return relatedComments;
   }

   public void setRelatedComments(Set<VisitorComment> relatedComments)
   {
      this.relatedComments = relatedComments;
   }

   public Set<VisitorLike> getRelatedLikes()
   {
      return relatedLikes;
   }

   public void setRelatedLikes(Set<VisitorLike> relatedLikes)
   {
      this.relatedLikes = relatedLikes;
   }
}
