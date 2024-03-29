package org.hanbo.mvc.repositories;

import java.util.List;

import org.hanbo.mvc.entities.UserStatus;

public interface UserStatusesRepository
{
   void saveUserStatus(UserStatus userStatusToSave);
   
   long getViewableUserStatusesCount();
   
   List<UserStatus> getViewableUserStatuses(int pageIdx, int maxItemsCount);

   long getAllUserStatusesCount(String ownerId);
   
   List<UserStatus> getAllUserStatuses(String ownerId, int pageIdx, int maxItemsCount);
   
   UserStatus getUserStatus(String userId, String userUpdateId);
}
