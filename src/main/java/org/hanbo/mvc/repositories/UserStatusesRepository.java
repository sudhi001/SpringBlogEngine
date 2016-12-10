package org.hanbo.mvc.repositories;

import java.util.List;

import org.hanbo.mvc.entities.UserStatus;

public interface UserStatusesRepository
{
   void saveUserStatus(UserStatus userStatusToSave);
   
   long getViewableUserStatusesCount();
   
   List<UserStatus> getViewableUserStatuses(int pageIdx, int maxItemsCount);
}
