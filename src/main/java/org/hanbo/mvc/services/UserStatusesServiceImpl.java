package org.hanbo.mvc.services;

import org.hanbo.mvc.models.ItemListPageDataModel;
import org.hanbo.mvc.models.UserUpdateDataModel;
import org.hanbo.mvc.models.UserUpdateInputDataModel;
import org.hanbo.mvc.models.UserUpdatesPageDataModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hanbo.mvc.entities.LoginUser;
import org.hanbo.mvc.entities.UserStatus;
import org.hanbo.mvc.exceptions.WebAppException;
import org.hanbo.mvc.repositories.CommentsRepository;
import org.hanbo.mvc.repositories.UserStatusesRepository;
import org.hanbo.mvc.repositories.UsersRepository;
import org.hanbo.mvc.repositories.VisitorLikeRepository;
import org.hanbo.mvc.services.utilities.UserUpdateDataModelEntityMapping;
import org.hanbo.mvc.utilities.IdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
@PropertySource("classpath:/site.properties")
public class UserStatusesServiceImpl
   implements UserStatusesService 
{
   @Autowired
   private UserStatusesRepository userStatusesRepo;
   
   @Autowired
   private Environment configValues;
   
   @Autowired
   private CommentsRepository commentsRepo;
   
   @Autowired
   private VisitorLikeRepository visitorLikeRepo;
   
   @Autowired
   private UsersRepository userRepo;
   
   @Override
   public UserUpdatesPageDataModel getViewableUserUpdates(int pageIdx)
   {
      int maxItemsCount = getViewableUserStatusesCountForDisplay();
      
      int allUserUpdatesCount = (int)this.userStatusesRepo.getViewableUserStatusesCount();
      
      List<UserStatus> viewableStatuses = this.userStatusesRepo.getViewableUserStatuses(pageIdx, maxItemsCount);
      return 
      prepareUseUpdatesPageDataModel(viewableStatuses, allUserUpdatesCount, pageIdx, maxItemsCount);
   }
   
   private int getViewableUserStatusesCountForDisplay()
   {
      String itemsCount = configValues.getProperty("viewableUserUpdatesPerPage");
      
      int itemsCountVal = Integer.parseInt(itemsCount);
      
      return itemsCountVal;
   }
   
   private int getAdminUserStatusesCountForDisplay()
   {
      String itemsCount = configValues.getProperty("adminUserUpdatesPerPage");
      
      int itemsCountVal = Integer.parseInt(itemsCount);
      
      return itemsCountVal;
   }

   @Override
   public UserUpdatesPageDataModel getAllUserUpdates(String userId, int pageIdx)
   {
      int maxItemsCount = getAdminUserStatusesCountForDisplay();
      
      int allUserUpdatesCount = (int)this.userStatusesRepo.getAllUserStatusesCount(userId);
      
      List<UserStatus> viewableStatuses = this.userStatusesRepo.getAllUserStatuses(userId, pageIdx, maxItemsCount);

      return 
      prepareUseUpdatesPageDataModel(viewableStatuses, allUserUpdatesCount, pageIdx, maxItemsCount);
   }
   
   @Override
   public void addUserStatus(String userId, UserUpdateInputDataModel userUpdate)
   {
      validateUserUpdateInput(userId, userUpdate);
      
      LoginUser owner = userRepo.getUserById(userId);
      if (owner == null)
      {
         throw new WebAppException("Status owner cannot be found.", WebAppException.ErrorType.SECURITY);
      }
      
      Date dateNow = new Date();
      UserStatus statusToAdd = new UserStatus();
      statusToAdd.setId(IdUtil.generateUuid());
      statusToAdd.setStatusTitle(userUpdate.getUserUpdateTitle());
      statusToAdd.setStatusContent(userUpdate.getUserUpdateContent());
      statusToAdd.setCreateDate(dateNow);
      statusToAdd.setUpateDate(dateNow);
      statusToAdd.setViewable(userUpdate.isUserUpdatePublished());
      statusToAdd.setOwner(owner);
      
      this.userStatusesRepo.saveUserStatus(statusToAdd);
   }

   @Override
   public void editUserStatus(String userId, UserUpdateInputDataModel userUpdate)
   {
      validateUserUpdateInput(userId, userUpdate);
      
      LoginUser owner = userRepo.getUserById(userId);
      if (owner == null)
      {
         throw new WebAppException("Status owner cannot be found.", WebAppException.ErrorType.SECURITY);
      }
      
      String userUpdateId = userUpdate.getUserUpdateId();
      if (StringUtils.isEmpty(userUpdateId))
      {
         throw new WebAppException("Status ID is null or empty.", WebAppException.ErrorType.DATA);         
      }
         
      Date dateNow = new Date();
      UserStatus statusToEdit = this.userStatusesRepo.getUserStatus(userId, userUpdateId);
      
      if (statusToEdit != null)
      {
         statusToEdit.setStatusTitle(userUpdate.getUserUpdateTitle());
         statusToEdit.setStatusContent(userUpdate.getUserUpdateContent());
         statusToEdit.setUpateDate(dateNow);
         statusToEdit.setViewable(userUpdate.isUserUpdatePublished());

         this.userStatusesRepo.saveUserStatus(statusToEdit);
      }
   }
   
   private void validateUserUpdateInput(String userId, UserUpdateInputDataModel userUpdate)
   {
      if (StringUtils.isEmpty(userId))
      {
         throw new WebAppException("User ID is null or empty", WebAppException.ErrorType.SECURITY);
      }

      if (userUpdate == null)
      {
         throw new WebAppException("User status object is null or empty", WebAppException.ErrorType.DATA);
      }

      if (StringUtils.isEmpty(userUpdate.getUserUpdateTitle()))
      {
         throw new WebAppException("User status title is null or empty", WebAppException.ErrorType.DATA);
      }
      
      if (!StringUtils.isEmpty(userUpdate.getUserUpdateTitle()) && userUpdate.getUserUpdateTitle().length() > 128)
      {
         throw new WebAppException("User status title is too long, max length 128 characters", WebAppException.ErrorType.DATA);
      }
      
      if (StringUtils.isEmpty(userUpdate.getUserUpdateContent()))
      {
         throw new WebAppException("User status content is null or empty", WebAppException.ErrorType.DATA);
      }
      
      if (!StringUtils.isEmpty(userUpdate.getUserUpdateContent()) && userUpdate.getUserUpdateContent().length() > 1024)
      {
         throw new WebAppException("User status content is too long, max 1024 characters", WebAppException.ErrorType.DATA);
      }
   }
   
   private UserUpdatesPageDataModel prepareUseUpdatesPageDataModel(List<UserStatus> viewableStatuses, int allUserUpdatesCount, int pageIdx, int maxItemsCount)
   {
      UserUpdatesPageDataModel retVal = new UserUpdatesPageDataModel();
      if (viewableStatuses != null && viewableStatuses.size() > 0)
      {
         List<UserUpdateDataModel> listOfUpdates = 
         UserUpdateDataModelEntityMapping.toDataModels(viewableStatuses);
         
         for (UserUpdateDataModel update : listOfUpdates)
         {
            if (update != null)
            {
               int commentsCount = (int)commentsRepo.getUserStatusViewableCommentsCount(update.getUserStatusId());
               int visitorLikesCount = (int)this.visitorLikeRepo.getArticleVisitorLikesCount(update.getUserStatusId());
               int visitorDislikesCount = (int)this.visitorLikeRepo.getArticleVisitorDislikesCount(update.getUserStatusId());
               
               update.setCommentsCount(commentsCount);
               update.setVisitorLikesCount(visitorLikesCount);
               update.setVisitorDislikesCount(visitorDislikesCount);
            }
         }
         
         retVal.setUserUpdates(listOfUpdates);
         if (listOfUpdates != null && listOfUpdates.size() > 0)
         {
            ItemListPageDataModel.createPageModel(retVal, listOfUpdates.size(), allUserUpdatesCount, pageIdx, maxItemsCount);
         }
         else
         {
            retVal.setUserUpdates(new ArrayList<UserUpdateDataModel>());
            ItemListPageDataModel.createEmptyPageDataModel(retVal);
         }
      }
      else
      {
         retVal.setUserUpdates(new ArrayList<UserUpdateDataModel>());
         ItemListPageDataModel.createEmptyPageDataModel(retVal);
      }
      
      return retVal;
   }
}
