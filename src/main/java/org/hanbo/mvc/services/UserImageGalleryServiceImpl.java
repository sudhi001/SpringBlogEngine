package org.hanbo.mvc.services;

import java.io.File;
import java.io.FilenameFilter;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hanbo.mvc.entities.Image;
import org.hanbo.mvc.entities.LoginUser;
import org.hanbo.mvc.exceptions.WebAppException;
import org.hanbo.mvc.models.ImageDisplayDetail;
import org.hanbo.mvc.models.ImageDisplayPageDataModel;
import org.hanbo.mvc.models.ItemListPageDataModel;
import org.hanbo.mvc.repositories.ImageGalleryRepository;
import org.hanbo.mvc.repositories.UsersRepository;
import org.hanbo.mvc.services.utilities.ImageDataModelEntityMapping;
import org.hanbo.mvc.utilities.FileStreamUtil;
import org.hanbo.mvc.utilities.IdUtil;
import org.hanbo.mvc.utilities.ImageFileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@PropertySource("classpath:/site.properties")
public class UserImageGalleryServiceImpl
   implements UserImageGalleryService
{
   @Autowired
   private ImageGalleryRepository _imageGalleryRepo;
   
   @Autowired
   private UsersRepository _usersRepo;
   
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
   
   @Override
   public void uploadImage(String userId, String imageTitle,
      String imageKeywords, MultipartFile imageToUpload,
      MultipartFile snapshotToUpload)
   {
      validateUploadedImage(imageToUpload);
      
      LoginUser imageOwner = _usersRepo.getUserById(userId);
      if (imageOwner == null)
      {
         throw new WebAppException(
            String.format("Unable to find user with id [%s]", userId),
            WebAppException.ErrorType.SECURITY);
      }
      
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
         // create the thumbnail of the image
         ImageFileUtil imgFileUtil = new ImageFileUtil();
         imgFileUtil.setOriginalFile(fileName);
         imgFileUtil.imageFileDimensions();
         
         imgWidth = imgFileUtil.getOriginalImageFileSizeX();
         imgHeight = imgFileUtil.getOriginalImageFileSizeY();
         
         imgFileUtil.determineAspectRatio();
         imgFileUtil.setResizedImageWidth(240);
         imgFileUtil.calculateNewFileDimensions();
         
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

	   // Save the snapshot image file to disk
	   String snapFileName = "";
	   String snapFileShortName = "";
	   
	   int snapShotFileWidth = 0;
      int snapShotFileHeight = 0;
	   
	   if (!snapshotToUpload.isEmpty())
	   {
	      String snapFileExt = 
	         getFileNameExtension(snapshotToUpload.getOriginalFilename());
	      
         snapFileName
            = filePath + imageId + "-snap" + snapFileExt;
         snapFileShortName
            = fileShortPath + imageId + "-snap" + snapFileExt;
         try
         {
            FileStreamUtil.saveFileToServer(snapFileName, snapshotToUpload.getInputStream());

            ImageFileUtil imgFileUtil = new ImageFileUtil();
            imgFileUtil.setOriginalFile(snapFileName);
            imgFileUtil.imageFileDimensions();
            
            snapShotFileWidth = imgFileUtil.getOriginalImageFileSizeX();
            snapShotFileHeight = imgFileUtil.getOriginalImageFileSizeY();
            
            image.setSnapshotFilePath(snapFileShortName);
            image.setSnapshotSizeX(snapShotFileWidth);
            image.setSnapshotSizeY(snapShotFileHeight);
         }
         catch(Exception e)
         {
            throw new WebAppException(
               String.format("Unexpected error when save file [%s]", fileName),
               WebAppException.ErrorType.FUNCTIONAL, e);
         }
	   }
	   else
	   {
         image.setSnapshotFilePath("");
         image.setSnapshotSizeX(0);
         image.setSnapshotSizeY(0);
	   }
      
      // save it to DB.
	   _imageGalleryRepo.saveImage(image);
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
