package org.hanbo.mvc.services;

import org.hanbo.mvc.models.ItemListPageDataModel;
import org.hanbo.mvc.models.UserUpdateDataModel;
import org.hanbo.mvc.models.UserUpdatesPageDataModel;

import java.util.ArrayList;
import java.util.List;

import org.hanbo.mvc.entities.UserStatus;
import org.hanbo.mvc.repositories.CommentsRepository;
import org.hanbo.mvc.repositories.UserStatusesRepository;
import org.hanbo.mvc.repositories.VisitorLikeRepository;
import org.hanbo.mvc.services.utilities.UserUpdateDataModelEntityMapping;
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
