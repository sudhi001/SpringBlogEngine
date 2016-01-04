package org.hanbo.mvc.services;

import java.util.List;

import org.hanbo.mvc.entities.Image;
import org.hanbo.mvc.models.ImageDisplayDetail;
import org.hanbo.mvc.models.ImageDisplayPageDataModel;
import org.hanbo.mvc.models.ItemListPageDataModel;
import org.hanbo.mvc.repositories.ImageGalleryRepository;
import org.hanbo.mvc.services.utilities.ImageDataModelEntityMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
@PropertySource("classpath:/site.properties")
public class UserImageGalleryServiceImpl
   implements UserImageGalleryService
{
   @Autowired
   private ImageGalleryRepository _imageGalleryRepo;
   
   @Autowired
   private Environment configValues;
   
   public ImageDisplayPageDataModel getUserImages(String ownerId, int pageIdx)
   {
      int itemsCount = getConfigValue_ImagesPerPage();
      
      List<Image> imageList = 
      _imageGalleryRepo.getUserImages(ownerId, pageIdx, itemsCount);
      
      List<ImageDisplayDetail> listOfImageDisplay = 
      ImageDataModelEntityMapping.entitiesToImageDisplayDetailList(imageList);
      
      ImageDisplayPageDataModel retVal = new ImageDisplayPageDataModel();
      
      if (imageList.size() <= 0)
      {
         ItemListPageDataModel.createEmptyPageDataModel(retVal);
         return retVal;
      }
      else
      {
         int imagesCount = (int)_imageGalleryRepo.getUserImagesCount(ownerId);
         
         retVal.setListItems(listOfImageDisplay);
         ItemListPageDataModel.<ImageDisplayPageDataModel>createPageModel(
            retVal, imageList.size(), imagesCount, pageIdx, itemsCount);
      }
      
      return retVal;
   }
   
   private int getConfigValue_ImagesPerPage()
   {
      String itemsCount = configValues.getProperty("OwnerImagesPerPage");  
      int itemsCountVal = Integer.parseInt(itemsCount);

      return itemsCountVal;
   }
}
