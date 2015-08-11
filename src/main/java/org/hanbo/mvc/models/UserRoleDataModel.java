package org.hanbo.mvc.models;

public class UserRoleDataModel
{
   private String roldeId;
   private String roleDisplayName;
   private String roleName;
   
   public String getRoldeId()
   {
      return roldeId;
   }
   
   public void setRoldeId(String roldeId)
   {
      this.roldeId = roldeId;
   }

   public String getRoleDisplayName()
   {
      return roleDisplayName;
   }

   public void setRoleDisplayName(String roleDisplayName)
   {
      this.roleDisplayName = roleDisplayName;
   }

   public String getRoleName()
   {
      return roleName;
   }

   public void setRoleName(String roleName)
   {
      this.roleName = roleName;
   }
}
