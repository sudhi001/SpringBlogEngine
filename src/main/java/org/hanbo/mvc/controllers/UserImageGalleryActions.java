package org.hanbo.mvc.controllers;

import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.hanbo.mvc.controllers.utilities.ActionsUtil;
import org.hanbo.mvc.models.GalleryDisplayPageDataModel;
import org.hanbo.mvc.models.ImageDisplayPageDataModel;
import org.hanbo.mvc.models.PageMetadata;
import org.hanbo.mvc.models.UserPrincipalDataModel;
import org.hanbo.mvc.services.UserImageGalleryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserImageGalleryActions
{
   @Autowired
   private ActionsUtil _util;
   
   @Autowired
   private UserImageGalleryService _imageGalleryService;
   
   @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STAFF')")
   @RequestMapping(value = "/admin/images/allMyImages", method=RequestMethod.GET)
   public ModelAndView allMyImages()
   {
      UserPrincipalDataModel loginUser = this._util.getLoginUser();
      if (loginUser == null)
      {
         return _util.createErorrPageViewModel(
            "User Authorization Failure", "User cannot be found.");
      }

      ImageDisplayPageDataModel pageDataModel =
      _imageGalleryService.getUserImages(loginUser.getUserId(), 0);
      
      PageMetadata pageMetadata
         = _util.creatPageMetadata("All My Images");
      ModelAndView retVal
         = _util.getDefaultModelAndView(
              "userImageList", pageMetadata
           );
      retVal.addObject("userImagesListPageModel", pageDataModel);
      
      return retVal;
   }
   
   @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STAFF')")
   @RequestMapping(value = "/admin/galleries/allMyGalleries", method=RequestMethod.GET)
   public ModelAndView allMyGalleries()
   {
      UserPrincipalDataModel loginUser = this._util.getLoginUser();
      if (loginUser == null)
      {
         return _util.createErorrPageViewModel(
            "User Authorization Failure", "User cannot be found.");
      }

      GalleryDisplayPageDataModel pageDataModel =
      _imageGalleryService.getUserGalleries(loginUser.getUserId(), 0);
      
      PageMetadata pageMetadata
         = _util.creatPageMetadata("All My Galleries");
      ModelAndView retVal
         = _util.getDefaultModelAndView(
              "userGalleriesList", pageMetadata
           );
      retVal.addObject("userGalleriesListPageModel", pageDataModel);
      
      return retVal;
   }
   
   @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STAFF')")
   @RequestMapping(value = "/admin/galleries/addGallery", method=RequestMethod.POST)
   public ModelAndView addImage(
      @RequestParam("galleryTitle")
      String galleryTitle,
      @RequestParam("galleryKeywords")
      String galleryKeywords,
      @RequestParam("galleryDesc")
      String galleryDesc
   )
   {
      UserPrincipalDataModel loginUser = this._util.getLoginUser();
      if (loginUser == null)
      {
         return _util.createErorrPageViewModel(
            "User Authorization Failure", "User cannot be found.");
      }

      _imageGalleryService.addGallery(loginUser.getUserId(), galleryTitle, galleryKeywords, galleryDesc);
      
      
      return _util.createRedirectPageView("redirect:/admin/galleries/allMyGalleries");
   }
   
   
   @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STAFF')")
   @RequestMapping(value = "/admin/images/addImage", method=RequestMethod.POST)
   public ModelAndView addImage(
      @RequestParam("imageTitle")
      String imageTitle,
      @RequestParam("imageKeywords")
      String imageKeywords,
      @RequestParam("imageToUpload")
      MultipartFile imageToUpload,
      @RequestParam("snapshotToUpload")
      MultipartFile snapshotToUpload
   )
   {
      UserPrincipalDataModel loginUser = this._util.getLoginUser();
      if (loginUser == null)
      {
         return _util.createErorrPageViewModel(
            "User Authorization Failure", "User cannot be found.");
      }
      
      String userId = loginUser.getUserId();
      
      _imageGalleryService.uploadImage(userId, imageTitle, imageKeywords,
         imageToUpload, snapshotToUpload);
      
      return _util.createRedirectPageView("redirect:/admin/images/allMyImages");
   }
   
   @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STAFF')")
   @RequestMapping(value = "/secure/image-thumb/{imageId}", method=RequestMethod.GET)
   public void secureImageThumb(
      @PathVariable("imageId")
      String imageId,
      HttpServletResponse response
   )
   {
      if (_imageGalleryService.imageSnapshotAvailable(imageId))
      {
         downloadImage(imageId, "snap", response);
      }
      else
      {
         downloadImage(imageId, "thumb", response);
      }
   }

   @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STAFF')")
   @RequestMapping(value = "/secure/image-full/{imageId}", method=RequestMethod.GET)
   public void secureImageDownload(
      @PathVariable("imageId")
      String imageId,
      HttpServletResponse response
   )
   {
      downloadImage(imageId, "", response);
   }

   private void downloadImage(String imageId, String type, HttpServletResponse response)
   {
      try
      {
         OutputStream s = response.getOutputStream();
         
         boolean retVal = this._imageGalleryService.downloadImage(
            imageId, type, s
         );
         
         response.setStatus(retVal? 200 : 404);
      }
      catch(Exception e)
      {
         response.setStatus(404);
      }
   }
}
