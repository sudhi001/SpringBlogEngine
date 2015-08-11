package org.hanbo.mvc.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "userrole")
public class UserRole
{
   @Id
   @Column(name = "id")
   @GeneratedValue
   private long id;
   
   @Column(name = "rolename", nullable = false, length = 64)
   private String roleName;
   
   @Column(name = "createdate", columnDefinition="DATETIME")
   @Temporal(TemporalType.TIMESTAMP)
   private Date createDate;
  
   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "userid", nullable= false)
   private LoginUser associatedUser;
   
   public UserRole()
   {
   }

   public long getId()
   {
      return id;
   }
   
   public void setRoleName(String roleName)
   {
      this.roleName = roleName;
   }
   
   public String getRoleName()
   {
      return this.roleName;
   }
   
   public Date getCreateDate()
   {
      return createDate;
   }

   public void setCreateDate(Date createDate)
   {
      this.createDate = createDate;
   }

   public LoginUser getAssociatedUser()
   {
      return associatedUser;
   }

   public void setAssociatedUser(LoginUser associatedUser)
   {
      this.associatedUser = associatedUser;
   }
   
   @Override
   public int hashCode()
   {
      return (int)this.id;
   }
}
