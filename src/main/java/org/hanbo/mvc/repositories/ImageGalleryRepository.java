package org.hanbo.mvc.repositories;

import java.util.List;

import org.hanbo.mvc.entities.Image;

public interface ImageGalleryRepository
{
   void saveImage(Image image);
 
   long getUserImagesCount(String ownerId);
   
   List<Image> getUserImages(String ownerId, int pageIdx, int itemsCount);
}
