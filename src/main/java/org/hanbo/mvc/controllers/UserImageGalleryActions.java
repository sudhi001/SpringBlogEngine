package org.hanbo.mvc.controllers;

import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.hanbo.mvc.controllers.utilities.ActionsUtil;
import org.hanbo.mvc.models.GalleryDisplayPageDataModel;
import org.hanbo.mvc.models.GalleryImagesPageDisplayDataModel;
import org.hanbo.mvc.models.ImageDisplayDetail;
import org.hanbo.mvc.models.PageMetadata;
import org.hanbo.mvc.models.UserPrincipalDataModel;
import org.hanbo.mvc.models.json.GenericJsonResponse;
import org.hanbo.mvc.models.json.SearchUserPhotoResponse;
import org.hanbo.mvc.services.UserImageGalleryService;
import org.hanbo.mvc.utilities.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import org.apache.log4j.*;

@Controller
public class UserImageGalleryActions
{
   private static Logger _logger = LogManager.getLogger(UserImageGalleryActions.class);
   
   @Autowired
   private ActionsUtil _util;
   
   @Autowired
   private UserImageGalleryService _imageGalleryService;

   @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STAFF')")
   @RequestMapping(value = "/admin/galleries/allMyGalleries/{pageIdx}", method=RequestMethod.GET)
   public ModelAndView allMyGalleries(
      @PathVariable("pageIdx")
      Integer pageIdx)
   {
      UserPrincipalDataModel loginUser = this._util.getLoginUser();
      if (loginUser == null)
      {
         return _util.createErorrPageViewModel(
            "User Authorization Failure", "User cannot be found.");
      }
      
      if (pageIdx == null)
      {
         pageIdx = 0;
      }

      GalleryDisplayPageDataModel pageDataModel =
      _imageGalleryService.getUserGalleries(loginUser.getUserId(), pageIdx);
      
      PageMetadata pageMetadata
         = _util.creatPageMetadata("All My Galleries");
      ModelAndView retVal
         = _util.getDefaultModelAndView(
              "userGalleriesList", pageMetadata);
      retVal.addObject("userGalleriesListPageModel", pageDataModel);
      
      return retVal;
   }
   
   @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STAFF')")
   @RequestMapping(value = "/admin/gallery/{galleryId}/page/{pageIdx}", method=RequestMethod.GET)
   public ModelAndView galleryImagesPage(
      @PathVariable("galleryId")
      String galleryId,
      @PathVariable("pageIdx")
      int pageIdx
   )
   {
      UserPrincipalDataModel loginUser = this._util.getLoginUser();
      if (loginUser == null)
      {
         return _util.createErorrPageViewModel(
            "User Authorization Failure", "User cannot be found.");
      }
      
      GalleryImagesPageDisplayDataModel pageDisplayData
         = _imageGalleryService.getUserGalleryImages(loginUser.getUserId(), galleryId, pageIdx);
      
      if (pageDisplayData == null)
      {
         return _util.createErorrPageViewModel(
            "Not Found", "The gallery is not found.");
      }
      
      PageMetadata pageMetadata
         = _util.creatPageMetadata("Gallery Images");
      ModelAndView retVal
         = _util.getDefaultModelAndView(
              "userGalleryImages", pageMetadata);
      retVal.addObject("userGalleryImagesPageModel", pageDisplayData);
      
      return retVal;
   }
   
   @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STAFF')")
   @RequestMapping(value = "/admin/image/{imageId}", method=RequestMethod.GET)
   public ModelAndView showImageDetail(
      @PathVariable("imageId")
      String imageId
   )
   {
      UserPrincipalDataModel loginUser = this._util.getLoginUser();
      if (loginUser == null)
      {
         return _util.createErorrPageViewModel(
            "User Authorization Failure", "User cannot be found.");
      }
      
      ImageDisplayDetail imageDetail
         = _imageGalleryService.getUserImageDetail(imageId, loginUser.getUserId());
      if (imageDetail == null)
      {
         return _util.createErorrPageViewModel(
            "Not Found", "The image is not found.");
      }
      
      PageMetadata pageMetadata
         = _util.creatPageMetadata("");
      ModelAndView retVal
         = _util.getDefaultModelAndView(
              "userImageDetail", pageMetadata);
      retVal.addObject("imageDetail", imageDetail);
      
      return retVal;
   }
   
   @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STAFF')")
   @RequestMapping(value = "/admin/galleries/addGallery", method=RequestMethod.POST)
   public ModelAndView addGallery(
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

      _imageGalleryService.addGallery(loginUser.getUserId(),
         galleryTitle, galleryKeywords, galleryDesc);
      
      return _util.createRedirectPageView("redirect:/admin/galleries/allMyGalleries/0");
   }
   
   @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STAFF')")
   @RequestMapping(value="/admin/galleries/showGallery",
      method=RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
   @ResponseBody
   public ResponseEntity<String> showGallery(
      @RequestParam("galleryId")
      String galleryId,
      @RequestParam("show")
      boolean showGallery
   )
   {
      UserPrincipalDataModel loginUser = this._util.getLoginUser();
      if (loginUser == null)
      {
         return new ResponseEntity<String>(
            JsonUtil.simpleErrorMessage("Not authorized"),
            HttpStatus.FORBIDDEN);
      }
      
      try
      {
         this._imageGalleryService.setGalleryVisibility(loginUser.getUserId(),
               galleryId, showGallery);
         return new ResponseEntity<String>(
            JsonUtil.convertObjectToJson(new GenericJsonResponse(true, "operation successful.")),
            HttpStatus.OK);
      }
      catch(Exception e)
      {
         return new ResponseEntity<String>(
            JsonUtil.simpleErrorMessage("Exception occurred."),
            HttpStatus.INTERNAL_SERVER_ERROR);
      }
   }

   @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STAFF')")
   @RequestMapping(value="/admin/galleries/setGalleryActive",
      method=RequestMethod.GET,
      produces = MediaType.APPLICATION_JSON_VALUE)
   @ResponseBody
   public ResponseEntity<String> enableGallery(
      @RequestParam("galleryId")
      String galleryId,
      @RequestParam("enable")
      boolean enableGallery
   )
   {
      UserPrincipalDataModel loginUser = this._util.getLoginUser();
      if (loginUser == null)
      {
         return new ResponseEntity<String>(
            JsonUtil.simpleErrorMessage("Not authorized"),
            HttpStatus.FORBIDDEN);
      }
      
      try
      {
         this._imageGalleryService.enableGallery(loginUser.getUserId(),
               galleryId, enableGallery);
         return new ResponseEntity<String>(
            JsonUtil.convertObjectToJson(new GenericJsonResponse(true, "operation successful.")),
            HttpStatus.OK);
      }
      catch(Exception e)
      {
         return new ResponseEntity<String>(
            JsonUtil.simpleErrorMessage("Exception occurred."),
            HttpStatus.INTERNAL_SERVER_ERROR);
      }

   }

   @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STAFF')")
   @RequestMapping(value = "/admin/images/uploadImage", method=RequestMethod.POST)
   public ModelAndView addImage(
      @RequestParam("uploadImageGalleryId")
      String galleryId,
      @RequestParam("imageTitle")
      String imageTitle,
      @RequestParam("imageKeywords")
      String imageKeywords,
      @RequestParam("imageToUpload")
      MultipartFile imageToUpload,
      @RequestParam(value="imageNotSafeForWork", defaultValue="false")
      boolean imageNotSafeForWork
   )
   {
      UserPrincipalDataModel loginUser = this._util.getLoginUser();
      if (loginUser == null)
      {
         return _util.createErorrPageViewModel(
            "User Authorization Failure", "User cannot be found.");
      }
      
      try
      {
         String userId = loginUser.getUserId();
         _imageGalleryService.uploadImage(userId, galleryId,
               imageTitle, imageKeywords,
               imageToUpload, imageNotSafeForWork);
         
         return _util.createRedirectPageView(
            String.format("redirect:/admin/gallery/%s/page/0", galleryId)
         );
      }
      catch(Exception e)
      {
         return _util.createErorrPageViewModel("Upload Error", "Error occurred when attempt to upload one image file.");
      }
   }

   @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STAFF')")
   @RequestMapping(value = "/admin/images/uploadImages", method=RequestMethod.POST)
   public ModelAndView addImages(
      @RequestParam("uploadImagesGalleryId")
      String galleryId,
      @RequestParam("imageToUpload")
      MultipartFile[] imagesToUpload)
   {
      // debug
      _logger.debug(String.format("gallery id: %s", galleryId));
      
      for (MultipartFile file : imagesToUpload)
      {
         _logger.debug(String.format("filename: %s", file.getName()));
         _logger.debug(String.format("original filename: %s", file.getOriginalFilename()));
      }
      
      
      UserPrincipalDataModel loginUser = this._util.getLoginUser();
      if (loginUser == null)
      {
         return _util.createErorrPageViewModel(
            "User Authorization Failure", "User cannot be found.");
      }
      
      try
      {
         String userId = loginUser.getUserId();
         _imageGalleryService.uploadImages(userId, galleryId,
            imagesToUpload);
         return _util.createRedirectPageView(
            String.format("redirect:/admin/gallery/%s/page/0", galleryId)
         );
      }
      catch(Exception e)
      {
         _logger.debug(e.getMessage());
         return _util.createErorrPageViewModel("Upload Error", "Error occurred when attempt to upload multiple image files.");
      }
   }
   
   @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STAFF')")
   @RequestMapping(value="/admin/image/editDetails",
      method=RequestMethod.POST)
   public ModelAndView editImageDetails(
      @RequestParam("imageId")
      String imageId,
      @RequestParam("imageTitle")
      String imageTitle,
      @RequestParam("imageKeywords")
      String imageKeywords,
      @RequestParam(value="imageActive", defaultValue="false")
      boolean imageActive,
      @RequestParam(value="imageNotSafeForWork", defaultValue="false")
      boolean imageNotSafeForWork)
   {
      UserPrincipalDataModel loginUser = this._util.getLoginUser();
      if (loginUser == null)
      {
         return _util.createErorrPageViewModel(
            "User Authorization Failure", "User cannot be found.");
      }
      
      String userId = loginUser.getUserId();
      _imageGalleryService.editImageDetails(userId, imageId,
         imageTitle, imageKeywords, imageActive, imageNotSafeForWork);
      
      return _util.createRedirectPageView(
         String.format("redirect:/admin/image/%s", imageId)
      );
   }

   @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STAFF')")
   @RequestMapping(value="/admin/gallery/editDetails",
      method=RequestMethod.POST)
   public ModelAndView editGalleryDetails(String galleryId, String galleryTitle,
      String galleryDesc, String galleryKeywords,
      boolean galleryActive, boolean galleryVisible)
   {
      UserPrincipalDataModel loginUser = this._util.getLoginUser();
      if (loginUser == null)
      {
         return _util.createErorrPageViewModel(
            "User Authorization Failure", "User cannot be found.");
      }
      
      String userId = loginUser.getUserId();
      _imageGalleryService.editGalleryDetails(userId, galleryId, galleryTitle,
         galleryKeywords, galleryDesc, galleryActive, galleryVisible);
      
      return _util.createRedirectPageView(
         String.format("redirect:/admin/gallery/%s/page/0", galleryId)
      );
   }
   
   @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STAFF')")
   @RequestMapping(value = "/secure/image-thumb/{imageId}", method=RequestMethod.GET)
   public void secureImageThumb(
      @PathVariable("imageId")
      String imageId,
      HttpServletResponse response
   )
   {
      downloadImage(imageId, "thumb", response);
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
   
   @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STAFF')")
   @RequestMapping(value = "/admin/images/allUserImages/json/{pageIdx}", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
   @ResponseBody
   public ResponseEntity<String> admin(
      @PathVariable("pageIdx")
      int pageIdx
   )
   {
      if (pageIdx < 0)
      {
         pageIdx = 0;
      }
      
      UserPrincipalDataModel loginUser = this._util.getLoginUser();
      if (loginUser == null)
      {
         _logger.info(String.format("Error: not logged in"));
         return new ResponseEntity<String>(
            JsonUtil.simpleErrorMessage("User not found"),
            HttpStatus.UNAUTHORIZED
         );
      }
      
      try
      {
         List<SearchUserPhotoResponse> foundPhotos =
         this._imageGalleryService.allUserImagesJson(loginUser.getUserId(), pageIdx);
         if (foundPhotos != null && foundPhotos.size() > 0)
         {
            String responseBody = JsonUtil.convertObjectToJson(foundPhotos);
            ResponseEntity<String> retVal = new ResponseEntity<String>(responseBody, HttpStatus.OK);
            return retVal;
         }
      
         return new ResponseEntity<String>(
            JsonUtil.simpleErrorMessage("User photos not found."),
            HttpStatus.NOT_FOUND
         );
      }
      catch(Exception e)
      {
         return new ResponseEntity<String>(
            JsonUtil.simpleErrorMessage("Unknown error has occurred."),
            HttpStatus.INTERNAL_SERVER_ERROR
         );
      }
   }
   

   @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STAFF')")
   @RequestMapping(value = "/admin/images/findImages", method=RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
   @ResponseBody
   public ResponseEntity<String> adminFindImages(
      @RequestParam("searchWords")
      String searchWords
   )
   {
      _logger.debug(String.format("searchWords: %s", searchWords));
      
      UserPrincipalDataModel loginUser = this._util.getLoginUser();
      if (loginUser == null)
      {
         _logger.debug(String.format("Error: not logged in"));
         return new ResponseEntity<String>(
            JsonUtil.simpleErrorMessage("User not found"),
            HttpStatus.UNAUTHORIZED
         );
      }
      
      try
      {
         List<SearchUserPhotoResponse> foundPhotos =
         this._imageGalleryService.findUserPhotos(loginUser.getUserId(), searchWords, 0);
         if (foundPhotos != null && foundPhotos.size() > 0)
         {
            String responseBody = JsonUtil.convertObjectToJson(foundPhotos);
            ResponseEntity<String> retVal = new ResponseEntity<String>(responseBody, HttpStatus.OK);
            return retVal;
         }
      
         return new ResponseEntity<String>(
            JsonUtil.simpleErrorMessage("User photos not found."),
            HttpStatus.NOT_FOUND
         );
      }
      catch(Exception e)
      {
         return new ResponseEntity<String>(
            JsonUtil.simpleErrorMessage("Unknown error has occurred."),
            HttpStatus.INTERNAL_SERVER_ERROR
         );
      }
   }

   @RequestMapping(value = "/galleries/{pageIdx}", method=RequestMethod.GET)
   public ModelAndView photoGalleries(
      @PathVariable("pageIdx")
      int pageIdx
   )
   {
      //_imageGalleryService.getViewableGalleries(pageIdx);
      
      PageMetadata pageMetadata
         = _util.creatPageMetadata("My Photos");
      ModelAndView retVal
         = _util.getDefaultModelAndView(
              "photoGalleries", pageMetadata);
      //retVal.addObject("imageDetail", imageDetail);
      
      return retVal;
   }
   
   @RequestMapping(value = "/public/image-thumb/{imageId}", method=RequestMethod.GET)
   public void publicImageThumb(
      @PathVariable("imageId")
      String imageId,
      HttpServletResponse response
   )
   {
      downloadImage(imageId, "thumb", response);
   }
   
   @RequestMapping(value = "/public/image/{imageId}", method=RequestMethod.GET)
   public void publicImageDownload(
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
