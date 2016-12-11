package org.hanbo.mvc.services;

import org.hanbo.mvc.models.UserUpdatesPageDataModel;

public interface UserStatusesService
{
   UserUpdatesPageDataModel getViewableUserUpdates(int pageIdx);

   UserUpdatesPageDataModel getAllUserUpdates(String userId, int pageIdx);
}
