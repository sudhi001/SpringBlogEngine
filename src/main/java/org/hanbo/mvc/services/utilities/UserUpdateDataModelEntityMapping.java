package org.hanbo.mvc.services.utilities;

import java.util.ArrayList;
import java.util.List;

import org.hanbo.mvc.entities.UserStatus;
import org.hanbo.mvc.models.UserUpdateDataModel;

public class UserUpdateDataModelEntityMapping
{
   public static UserUpdateDataModel toDataModel(UserStatus entity)
   {
      UserUpdateDataModel retVal = null;
      if (entity != null)
      {
         retVal = new UserUpdateDataModel();
         
         retVal.setUserStatusId(entity.getId());
         retVal.setStatusContent(entity.getStatusContent());
         retVal.setStatusCreateDate(entity.getCreateDate());
         retVal.setStatusUpdateDate(entity.getUpateDate());
         
         if (entity.getOwner() != null)
         {
            retVal.setStatusOwnerId(entity.getOwner().getId());
            retVal.setStatusOwnerUserEmail(entity.getOwner().getUserEmail());
            retVal.setStatusOwnerUserName(entity.getOwner().getUserName());
            
            if (entity.getOwner().getUserProfile() != null)
            {
               retVal.setStatusOwnerUserFullName(
                  String.format("%s %s",
                     entity.getOwner().getUserProfile().getFirstName(),
                     entity.getOwner().getUserProfile().getLastName()
                  )
               );
               
               retVal.setStatusOwnerUserProfileId(entity.getOwner().getUserProfile().getId());
               if (entity.getOwner().getUserProfile().getUserIcon() != null)
               {
                  retVal.setStatusOwnerUserIconId(entity.getOwner().getUserProfile().getUserIcon().getId());
               }
            }
            retVal.setStatusOwnerUserFullName(entity.getOwner().getUserName());
         }
      }
      
      return retVal;
   }
   
   public static List<UserUpdateDataModel> toDataModels(List<UserStatus> entities)
   {
      List<UserUpdateDataModel> retVal = new ArrayList<UserUpdateDataModel>();
      if (entities != null && entities.size() > 0)
      {
         for (UserStatus status : entities)
         {
            UserUpdateDataModel dataModel = toDataModel(status);
            if (dataModel != null)
            {
               retVal.add(dataModel);
            }
         }
      }
      
      return retVal;
   }
}
