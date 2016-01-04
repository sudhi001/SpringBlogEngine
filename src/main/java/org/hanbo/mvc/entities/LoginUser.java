package org.hanbo.mvc.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "user")
public class LoginUser
{
   @Id
   @Column(name = "id", nullable = false, length = 45)
   private String id;
   
   @Column(name = "username", nullable = false, length = 32)
   private String username;

   @Column(name = "userpass", nullable = false, length = 60)
   private String userpass;
   
   @Column(name = "useremail", nullable = false, length = 64)
   private String useremail;   
   
   @Column(name = "active", nullable = false)
   private boolean active;
   
   @Column(name = "createdate", columnDefinition="DATETIME")
   @Temporal(TemporalType.TIMESTAMP)
   private Date createDate;
   
   @Column(name = "updatedate", columnDefinition="DATETIME")
   @Temporal(TemporalType.TIMESTAMP)
   private Date updateDate;

   @OneToMany(fetch = FetchType.LAZY, mappedBy="associatedUser")
   private Set<UserRole> userRoles;
   
   @OneToMany(fetch = FetchType.LAZY, mappedBy="author")
   private Set<Article> articles;
   
   @OneToMany(fetch = FetchType.LAZY, mappedBy="author")
   private Set<PermaLink> permaLinks;

   @OneToMany(fetch = FetchType.LAZY, mappedBy="owner")
   private Set<Resource> userResources;
   
   @OneToMany(fetch = FetchType.LAZY, mappedBy="owner")
   private Set<Image> userImages;

   @OneToMany(fetch = FetchType.LAZY, mappedBy="owner")
   private Set<Gallery> userGalleries;

   public LoginUser()
   {
      userRoles = new HashSet<UserRole>();
      articles = new HashSet<Article>();
      permaLinks = new HashSet<PermaLink>();
      userResources = new HashSet<Resource>();
      userImages = new HashSet<Image>();
      userGalleries = new HashSet<Gallery>();
   }
   
   public String getId()
   {
      return id;
   }
   
   public void setId(String id)
   {
      this.id = id;
   }
   
   public String getUserName()
   {
      return username;
   }
   
   public void setUserName(String username)
   {
      this.username = username;
   }
   
   public String getUserPass()
   {
      return userpass;
   }
   
   public void setUserPass(String userpass)
   {
      this.userpass = userpass;
   }

   public String getUserEmail()
   {
      return useremail;
   }
   
   public void setUserEmail(String useremail)
   {
      this.useremail = useremail;
   }
   
   public void setActive(boolean active)
   {
      this.active = active;
   }
   
   public boolean isActive()
   {
      return this.active;
   }

   public void setCreateDate(Date createdate)
   {
      this.createDate = createdate;
   }

   public Date getCreateDate()
   {
      return this.createDate;
   }
   
   public void setUpdateDate(Date updatedate)
   {
      this.updateDate = updatedate;
   }

   public Date getUpdateDate()
   {
      return this.updateDate;
   }

   public Set<UserRole> getUserRoles()
   {
      return userRoles;
   }

   public void setUserRoles(Set<UserRole> userRoles)
   {
      this.userRoles = userRoles;
   }

   public Set<Article> getArticles()
   {
      return articles;
   }

   public void setArticles(Set<Article> articles)
   {
      this.articles = articles;
   }

   public Set<PermaLink> getPermaLinks()
   {
      return permaLinks;
   }

   public void setPermaLinks(Set<PermaLink> permaLinks)
   {
      this.permaLinks = permaLinks;
   }

   public Set<Resource> getUserResources()
   {
      return userResources;
   }

   public void setUserResources(Set<Resource> userResources)
   {
      this.userResources = userResources;
   }

   public Set<Image> getUserImages() {
      return userImages;
   }

   public void setUserImages(Set<Image> userImages) {
      this.userImages = userImages;
   }

   public Set<Gallery> getUserGalleries() {
      return userGalleries;
   }

   public void setUserGalleries(Set<Gallery> userGalleries) {
      this.userGalleries = userGalleries;
   }
}