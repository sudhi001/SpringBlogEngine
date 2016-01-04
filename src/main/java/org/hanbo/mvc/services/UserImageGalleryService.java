package org.hanbo.mvc.services;

import org.hanbo.mvc.models.ImageDisplayPageDataModel;

public interface UserImageGalleryService
{
   ImageDisplayPageDataModel getUserImages(String ownerId, int pageIdx);
}
