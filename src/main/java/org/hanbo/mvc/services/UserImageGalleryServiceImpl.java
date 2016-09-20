package org.hanbo.mvc.services;

import java.io.File;
import java.io.FilenameFilter;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hanbo.mvc.entities.Gallery;
import org.hanbo.mvc.entities.Image;
import org.hanbo.mvc.entities.LoginUser;
import org.hanbo.mvc.exceptions.WebAppException;
import org.hanbo.mvc.models.GalleryDisplayDetail;
import org.hanbo.mvc.models.GalleryDisplayPageDataModel;
import org.hanbo.mvc.models.GalleryImagesPageDisplayDataModel;
import org.hanbo.mvc.models.ImageDisplayDetail;
import org.hanbo.mvc.models.ItemListPageDataModel;
import org.hanbo.mvc.repositories.ImageGalleryRepository;
import org.hanbo.mvc.repositories.UsersRepository;
import org.hanbo.mvc.services.utilities.ImageDataModelEntityMapping;
import org.hanbo.mvc.utilities.FileStreamUtil;
import org.hanbo.mvc.utilities.IdUtil;
import org.hanbo.mvc.utilities.ImageFile;
import org.hanbo.mvc.utilities.ImageFileProcessingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import org.apache.log4j.*;

@Service
@PropertySource("classpath:/site.properties")
public class UserImageGalleryServiceImpl
   implements UserImageGalleryService
{
   private static Logger _logger = LogManager.getLogger(UserImageGalleryServiceImpl.class);
   
   @Autowired
   private ImageGalleryRepository _imageGalleryRepo;
   
   @Autowired
   private UsersRepository _usersRepo;
   
   @Autowired
   private Environment configValues;
   
   @Override
   public GalleryDisplayPageDataModel getUserGalleries(String ownerId, int pageIdx)
   {
      int itemsCount = getConfigValue_ImagesPerPage();
      
      List<Gallery> galleryList = 
         _imageGalleryRepo.getUserGalleries(ownerId, pageIdx, itemsCount);
      
      List<GalleryDisplayDetail> listOfGalleriesDisplay = 
      ImageDataModelEntityMapping.entitiesToGalleriesDisplayDetailList(galleryList);
      
      GalleryDisplayPageDataModel retVal = new GalleryDisplayPageDataModel();
      
      if (galleryList.size() <= 0)
      {
         ItemListPageDataModel.createEmptyPageDataModel(retVal);
         return retVal;
      }
      else
      {
         int galleriesCount = (int)_imageGalleryRepo.getUserGalleriesCount(ownerId);
         
         retVal.setListItems(listOfGalleriesDisplay);
         ItemListPageDataModel.<GalleryDisplayPageDataModel>createPageModel(
            retVal, galleryList.size(), galleriesCount, pageIdx, itemsCount);
      }
      
      return retVal;
   }
   
   @Override
   public GalleryImagesPageDisplayDataModel getUserGalleryImages(String ownerId, String galleryId, int pageIdx)
   {
      GalleryImagesPageDisplayDataModel retVal = null;
      
      int itemsCount = getConfigValue_ImagesPerPage();
            
      Gallery gallery = this._imageGalleryRepo.getUserGallery(ownerId, galleryId);
      if (gallery != null)
      {
         retVal = new GalleryImagesPageDisplayDataModel();
         
         GalleryDisplayDetail galleryDisplayDetail =
         ImageDataModelEntityMapping.entityToGalleryDisplayDetail(gallery);
         retVal.setGalleryDetail(galleryDisplayDetail);

         int totalImagesCount
            =  (int)this._imageGalleryRepo.getGalleryImagesCount(ownerId, galleryId);
         
         List<Image> galleryImages
            = this._imageGalleryRepo.getGalleryImages(ownerId, galleryId, pageIdx, itemsCount);
         
         if (galleryImages != null && galleryImages.size() > 0)
         {
            List<ImageDisplayDetail> imageDisplayDetails = 
               ImageDataModelEntityMapping.entitiesToImageDisplayDetailList(galleryImages);
            
            ItemListPageDataModel.createPageModel(retVal, imageDisplayDetails.size(), totalImagesCount, pageIdx, itemsCount);
            retVal.setImagesPageList(imageDisplayDetails);
         }
         else
         {
            ItemListPageDataModel.createEmptyPageDataModel(retVal);
            retVal.setImagesPageList(new ArrayList<ImageDisplayDetail>());
         }
      }
      
      return retVal;
   }
   
   @Override
   public void uploadImage(String userId, String galleryId,
      String imageTitle, String imageKeywords, MultipartFile imageToUpload)
   {
      validateUploadedImage(imageToUpload);
      
      LoginUser imageOwner = _usersRepo.getUserById(userId);
      if (imageOwner == null)
      {
         throw new WebAppException(
            String.format("Unable to find user with id [%s]", userId),
            WebAppException.ErrorType.SECURITY);
      }
      
      validateImageData(imageTitle, imageKeywords);
      
      String imageId = IdUtil.generateUuid();
      
      String fileExt
         = getFileNameExtension(imageToUpload.getOriginalFilename());
      
      // Save the image file to disk
	   String filePath = imageFilePath(imageId, true);
	   String fileShortPath = partialImageFilePath(imageId);
	   String fileName = filePath + imageId + fileExt;
	   String fileShortName = fileShortPath + imageId + fileExt;
	   
	   Image image = new Image();
	   image.setId(imageId);
	   image.setTitle(imageTitle);
	   image.setFilePath(fileShortName);
	   image.setImageName(imageId + fileExt);
	   image.setKeywords(imageKeywords);
	   image.setUploadDate(new Date());
	   image.setOwner(imageOwner);
	   
	   try
	   {
	      FileStreamUtil.saveFileToServer(fileName, imageToUpload.getInputStream());
	   }
	   catch(Exception e)
	   {
	      throw new WebAppException(
	         String.format("Unexpected error when save file [%s]", fileName),
	         WebAppException.ErrorType.FUNCTIONAL, e);
	   }
	  
      int imgWidth = 0;
      int imgHeight = 0;
      int newImgWidth = 0;
      int newImgHeight = 0;
      
      try
      {
         String imageMagickFilePath = this.configValues.getProperty("ImageMagickFilePath");
         // create the thumbnail of the image
         ImageFile imgFileUtil = new ImageFile(new ImageFileProcessingUtil(imageMagickFilePath));
         imgFileUtil.setOriginalFile(fileName);
         imgFileUtil.imageFileDimensions();
         
         imgWidth = imgFileUtil.getOriginalImageFileSizeX();
         imgHeight = imgFileUtil.getOriginalImageFileSizeY();
         
         imgFileUtil.determineAspectRatio();
         imgFileUtil.setResizedImageWidth(250);
         imgFileUtil.calculateNewFileDimensions(false);
         
         String resizedFileName = filePath + imageId + "-thumb" + fileExt;
         String resizedFileShortName = fileShortPath + imageId + "-thumb" + fileExt;
         imgFileUtil.setResizeFile(resizedFileName);
         imgFileUtil.resizeImageFile();
         
         newImgWidth = imgFileUtil.getResizedFileSizeX();
         newImgHeight = imgFileUtil.getResizedFileSizeY();
         
         image.setFileSizeX(imgWidth);
         image.setFileSizeY(imgHeight);
         
         image.setThumbnailFilePath(resizedFileShortName);
         image.setThumbnailSizeX(newImgWidth);
         image.setThumbnailSizeY(newImgHeight);
      }
      catch(Exception e)
      {
         throw new WebAppException(
            String.format("Unexpected error when trying to resize file [%s]", fileName),
            WebAppException.ErrorType.FUNCTIONAL, e);       
      }
      
      _logger.info(galleryId);
      
      try
      {
         // save it to DB.
   	   _imageGalleryRepo.saveImage(image, galleryId, imageOwner.getId());
      }
      catch(Exception e)
      {
         throw new WebAppException(
            String.format("Unable to add new image [%s] to gallery with id [%s]", image.getId(), galleryId),
            WebAppException.ErrorType.FUNCTIONAL); 
      }
   }
   
   @Override
   public void uploadImages(String userId, String galleryId, MultipartFile[] imagesToUpload)
   {
      
   }

   
   @Override
   public boolean downloadImage(
      String imageId, String type, OutputStream outputStream
   ) throws Exception
   {
      String imagePath =imageFilePath(imageId, false);
      String tempFileName = imageId.toLowerCase();
      if (!StringUtils.isEmpty(type))
      {
         tempFileName =  tempFileName + "-" + type + ".";
      }
      else
      {
         tempFileName =  tempFileName + ".";
      }
      final String fileToSearch = tempFileName;
      
      File dirFile = new File(imagePath);
      if (dirFile.exists())
      {
         File[] filesFound = dirFile.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
               return name.contains(fileToSearch);
            }
         });
         
         if (filesFound.length > 0)
         {
            FileStreamUtil.readFileToOutputStream(filesFound[0].getAbsolutePath(), outputStream);
            return true;
         }
      }
      
      return false;
   }
   
   @Override
   public void addGallery(String ownerId, String galleryTitle,
      String galleryKeywords, String galleryDesc)
   {
      validateGalleryData(galleryTitle, galleryKeywords, galleryDesc);
      
      LoginUser galleryOwner = _usersRepo.getUserById(ownerId);
      if (galleryOwner == null)
      {
         throw new WebAppException(
            String.format("Unable to find user with id [%s]", ownerId),
            WebAppException.ErrorType.SECURITY);
      }
      
      Date dateNow = new Date();
      
      Gallery gallery = new Gallery();
      gallery.setTitle(galleryTitle);
      gallery.setKeywords(galleryKeywords);
      gallery.setDescription(galleryDesc);
      gallery.setId(IdUtil.generateUuid());
      gallery.setOwner(galleryOwner);
      gallery.setCreateDate(dateNow);
      gallery.setUpdateDate(dateNow);
      
      this._imageGalleryRepo.saveGallery(gallery);
   }
   
   @Override
   public void setGalleryVisibility(String ownerId, String galleryId, boolean showGallery)
   {
      if (StringUtils.isEmpty(ownerId))
      {
         throw new WebAppException(
            "the gallery owner id is null or empty",
            WebAppException.ErrorType.DATA);
      }
      
      if (StringUtils.isEmpty(galleryId))
      {
         throw new WebAppException(
            "the gallery id is null or empty",
            WebAppException.ErrorType.DATA);
      }
      
      this._imageGalleryRepo.setGalleryVisibility(ownerId, galleryId, showGallery);
   }
   
   @Override
   public void enableGallery(String ownerId, String galleryId, boolean enableGallery)
   {
      if (StringUtils.isEmpty(ownerId))
      {
         throw new WebAppException(
            "the gallery owner id is null or empty",
            WebAppException.ErrorType.DATA);
      }
      
      if (StringUtils.isEmpty(galleryId))
      {
         throw new WebAppException(
            "the gallery id is null or empty",
            WebAppException.ErrorType.DATA);
      }
      
      this._imageGalleryRepo.enableGallery(ownerId, galleryId, enableGallery);
   }
   
   @Override
   public ImageDisplayDetail getUserImageDetail(String imageId, String ownerId)
   {
      if (StringUtils.isEmpty(ownerId))
      {
         throw new WebAppException(
            "the gallery owner id is null or empty",
            WebAppException.ErrorType.DATA);
      }
      
      if (StringUtils.isEmpty(imageId))
      {
         throw new WebAppException(
            "the image id is null or empty",
            WebAppException.ErrorType.DATA);
      }
      
      Image imageFound = 
      this._imageGalleryRepo.getUserImage(imageId, ownerId);
      
      if (imageFound != null)
      {
         ImageDisplayDetail retVal = 
         ImageDataModelEntityMapping.entityToImageDisplayDetail(imageFound);
                  
         return retVal;
      }
      
      return null;
   }
   
   @Override
   public void editImageDetails(String userId, String imageId,
      String imageTitle, String imageKeywords,
      boolean imageActive)
   {
      if (StringUtils.isEmpty(userId))
      {
         throw new WebAppException(
            "User id cannot be null or empty string",
            WebAppException.ErrorType.SECURITY);
      }
      
      if (StringUtils.isEmpty(imageId))
      {
         throw new WebAppException(
            "Gallery id cannot be null or empty string",
            WebAppException.ErrorType.DATA);
      }
      
      this.validateImageData(imageTitle, imageKeywords);
      
      Image imageFound = 
         this._imageGalleryRepo.getUserImage(imageId, userId);
      if (imageFound != null)
      {
         imageFound.setActive(imageActive);
         imageFound.setTitle(imageTitle);
         imageFound.setKeywords(imageKeywords);
         
         this._imageGalleryRepo.saveImage(imageFound);
      }
   }

   @Override
   public void editGalleryDetails(String userId, String galleryId,
      String galleryTitle, String galleryKeywords,
      String galleryDesc, boolean galleryActive, boolean galleryVisible)
   {
      if (StringUtils.isEmpty(userId))
      {
         throw new WebAppException(
            "User id cannot be null or empty string",
            WebAppException.ErrorType.SECURITY);
      }
      
      if (StringUtils.isEmpty(galleryId))
      {
         throw new WebAppException(
            "Gallery id cannot be null or empty string",
            WebAppException.ErrorType.DATA);
      }
      
      validateGalleryData(galleryTitle, galleryKeywords, galleryDesc);
      
      Gallery galleryFound =
         this._imageGalleryRepo.getUserGallery(userId, galleryId);
      if (galleryFound != null)
      {
         galleryFound.setActive(galleryActive);
         galleryFound.setDescription(galleryDesc);
         galleryFound.setKeywords(galleryKeywords);
         galleryFound.setTitle(galleryTitle);
         galleryFound.setVisible(galleryVisible);
         galleryFound.setUpdateDate(new Date());
         
         this._imageGalleryRepo.saveGallery(galleryFound);
      }
   }
   
   private void validateGalleryData(String galleryTitle, String galleryKeywords, String galleryDesc)
   {
      if (StringUtils.isEmpty(galleryTitle))
      {
         throw new WebAppException(
            "Gallery title cannot be null or empty string",
            WebAppException.ErrorType.DATA);
      }
      else if (galleryTitle.length() > 96)
      {
         throw new WebAppException(
            "Gallery title can only have max 96 characters",
            WebAppException.ErrorType.DATA);
      }
      
      if (!StringUtils.isEmpty(galleryKeywords) && galleryKeywords.length() > 128)
      {
         throw new WebAppException(
            "Gallery keywords can only have max 128 characters",
            WebAppException.ErrorType.DATA);
      }
      
      if (!StringUtils.isEmpty(galleryDesc) && galleryDesc.length() > 3072)
      {
         throw new WebAppException(
            "Gallery description can only have max 3027 characters",
            WebAppException.ErrorType.DATA);
      }
   }
   
   private void validateImageData(String imageTitle, String imageKeywords)
   {
      if (StringUtils.isEmpty(imageTitle))
      {
         throw new WebAppException(
            "Image title cannot be null or empty string",
            WebAppException.ErrorType.DATA);
      }
      else if (imageTitle.length() > 96)
      {
         throw new WebAppException(
            "Image title can only have max 96 characters",
            WebAppException.ErrorType.DATA);
      }
      
      if (!StringUtils.isEmpty(imageKeywords) && imageKeywords.length() > 128)
      {
         throw new WebAppException(
            "Image keywords can only have max 128 characters",
            WebAppException.ErrorType.DATA);
      }
   }
   
   private String imageFilePath(String imageId, boolean createDir)
   {
      StringBuilder sb = new StringBuilder();

      String resourceBasePath
      	= getConfigValue_ImagesFileBaseDir();
      sb.append(resourceBasePath);
   
      sb.append("/images/");
      if (createDir)
         FileStreamUtil.directoryPathExists(sb.toString());
      
      char char1 = imageId.charAt(0);
      char char2 = imageId.charAt(1);
      sb.append(char1);
      sb.append("/");
      if (createDir)
         FileStreamUtil.directoryPathExists(sb.toString());
      
      sb.append(char2);
      sb.append("/");
      if (createDir)
         FileStreamUtil.directoryPathExists(sb.toString());
      
      return sb.toString();
   }
   
   private void validateUploadedImage(MultipartFile imageToUpload)
   {
      if (imageToUpload.isEmpty())
      {
         throw new WebAppException(
            "Uploaded file is empty",
            WebAppException.ErrorType.DATA);
      }
   }
   
   private String partialImageFilePath(String imageId)
   {
      StringBuilder sb = new StringBuilder();
      sb.append("images/");

      char char1 = imageId.charAt(0);
      char char2 = imageId.charAt(1);
      sb.append(char1);
      sb.append("/");
      sb.append(char2);
      sb.append("/");

      return sb.toString();
   }
   
   private String getFileNameExtension(String shortFileName)
   {
      int lastDotPos = shortFileName.lastIndexOf(".");
      if (lastDotPos >= 0 && lastDotPos <= (shortFileName.length()-1))
      {
         return shortFileName.substring(lastDotPos);
      }
      
      return "";
   }
   
   private int getConfigValue_ImagesPerPage()
   {
      String itemsCount = configValues.getProperty("OwnerImagesPerPage");
      int itemsCountVal = Integer.parseInt(itemsCount);

      return itemsCountVal;
   }
   
   private String getConfigValue_ImagesFileBaseDir()
   {
      String imageFileBaseDir = configValues.getProperty("OwnerImagesFileBaseDir");  

      return imageFileBaseDir;
   }
}
