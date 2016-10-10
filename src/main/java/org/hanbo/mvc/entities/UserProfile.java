package org.hanbo.mvc.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "userprofile")
public class UserProfile
{
   @Id
   @Column(name = "id", nullable = false, length = 45)
   private String id;
   
   @Column(name = "firstname", nullable = false, length = 64)
   private String firstName;
   
   @Column(name = "lastname", nullable = false, length = 64)
   private String lastName;
   
   @Column(name = "age", nullable = true)
   private int age;

   @Column(name = "gender", nullable = true, length = 6)
   private String gender;

   @Column(name = "location", nullable = true, length = 128)
   private String location;
   
   @Column(name = "profession", nullable = true, length = 128)
   private String profession;

   @Column(name = "introduction", nullable = true, length = 4096)
   private String introduction;

   @Column(name = "createdate", nullable = false, columnDefinition="DATETIME")
   @Temporal(TemporalType.TIMESTAMP)
   private Date createDate;

   @Column(name = "updatedate", nullable = false, columnDefinition="DATETIME")
   @Temporal(TemporalType.TIMESTAMP)
   private Date updateDate;
   
   @OneToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "ownerid")
   private LoginUser owner;
   
   @OneToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "usericonid")
   private FileResource userIcon;

   public String getId()
   {
      return id;
   }

   public void setId(String id)
   {
      this.id = id;
   }

   public int getAge()
   {
      return age;
   }

   public void setAge(int age)
   {
      this.age = age;
   }

   public String getGender()
   {
      return gender;
   }

   public void setGender(String gender)
   {
      this.gender = gender;
   }

   public String getLocation()
   {
      return location;
   }

   public void setLocation(String location)
   {
      this.location = location;
   }

   public String getProfession()
   {
      return profession;
   }

   public void setProfession(String profession)
   {
      this.profession = profession;
   }

   public String getIntroduction()
   {
      return introduction;
   }

   public void setIntroduction(String introduction)
   {
      this.introduction = introduction;
   }

   public LoginUser getOwner()
   {
      return owner;
   }

   public void setOwner(LoginUser owner)
   {
      this.owner = owner;
   }

   public FileResource getUserIcon()
   {
      return userIcon;
   }

   public void setUserIcon(FileResource userIcon)
   {
      this.userIcon = userIcon;
   }

   public String getFirstName()
   {
      return firstName;
   }

   public void setFirstName(String firstName)
   {
      this.firstName = firstName;
   }

   public String getLastName()
   {
      return lastName;
   }

   public void setLastName(String lastName)
   {
      this.lastName = lastName;
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
}
