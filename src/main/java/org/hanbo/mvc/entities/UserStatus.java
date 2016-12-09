package org.hanbo.mvc.entities;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "userstatus")
public class UserStatus
{
   @Id
   @Column(name = "id", nullable = false, length = 45)
   private String id;

   @Column(name = "statuscontent", nullable = false, length = 1024)
   private String statusContent;
   
   @Column(name = "createdate", columnDefinition="DATETIME", nullable = false)
   @Temporal(TemporalType.TIMESTAMP)
   private Date createDate;
   
   @Column(name = "updatedate", columnDefinition="DATETIME", nullable = false)
   @Temporal(TemporalType.TIMESTAMP)
   private Date upateDate;
   
   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "ownerid")
   private LoginUser owner;

   @OneToMany(fetch = FetchType.LAZY, mappedBy = "relatedUserStatus")
   private Set<VisitorComment> relatedComments;
   
   @OneToMany(fetch = FetchType.LAZY, mappedBy = "relatedUserStatus")
   private Set<VisitorLike> relatedLikes;
   
   public String getId()
   {
      return id;
   }

   public void setId(String id)
   {
      this.id = id;
   }

   public String getStatusContent()
   {
      return statusContent;
   }

   public void setStatusContent(String statusContent)
   {
      this.statusContent = statusContent;
   }

   public Date getCreateDate()
   {
      return createDate;
   }

   public void setCreateDate(Date createDate)
   {
      this.createDate = createDate;
   }

   public Date getUpateDate()
   {
      return upateDate;
   }

   public void setUpateDate(Date upateDate)
   {
      this.upateDate = upateDate;
   }

   public LoginUser getOwner()
   {
      return owner;
   }

   public void setOwner(LoginUser owner)
   {
      this.owner = owner;
   }
}
