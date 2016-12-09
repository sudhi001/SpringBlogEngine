package org.hanbo.mvc.models;

import java.util.ArrayList;
import java.util.List;

public class UserUpdatesPageDataModel
   extends ItemListPageDataModel
{
   private List<UserUpdateDataModel> userUpdates;

   public UserUpdatesPageDataModel()
   {
      userUpdates = new ArrayList<UserUpdateDataModel>();
   }
   
   public List<UserUpdateDataModel> getUserUpdates()
   {
      return userUpdates;
   }

   public void setUserUpdates(List<UserUpdateDataModel> userUpdates)
   {
      this.userUpdates = userUpdates;
   }
}
