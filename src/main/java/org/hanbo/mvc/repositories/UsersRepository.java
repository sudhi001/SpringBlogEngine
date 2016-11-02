package org.hanbo.mvc.repositories;

import java.util.Set;

import org.hanbo.mvc.entities.LoginUser;
import org.hanbo.mvc.entities.UserProfile;
import org.hanbo.mvc.entities.UserRole;
import org.hibernate.Session;

public interface UsersRepository
{
   boolean isUserExists(String userName);

   void addUser(
      LoginUser userToSave, Set<UserRole> userRoles
   );

   LoginUser getUser(String userName);
   LoginUser getUserById(String userId);
   LoginUser getUserById(Session session, String userId);
   
   void saveUserProfile(UserProfile userProfile);
   UserProfile getUserProfileByUserId(String userId);
   
   void updateUserProfileIcon(String ownerId, String profileId, String userIconId);
}