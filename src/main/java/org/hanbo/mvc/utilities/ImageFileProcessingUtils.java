package org.hanbo.mvc.utilities;

public class ImageFileProcessingUtils
{
   private String externalProgramPath;
   
   public ImageFileProcessingUtils(String extProgPath)
   {
      externalProgramPath = extProgPath;
   }
   
   public void resizeImage(String originalImgFile,
      String destImgFile,
      boolean center,
      boolean transparentBg,
      int destWidth,
      int destHeight)
   {
      String destImageDimension
         = String.format("%dx%d", destWidth, destHeight);
      
      ProgramInvoker invoker
         = new ProgramInvoker();
      invoker.setCommandExec(externalProgramPath);
      invoker.getCmdArguments().add(originalImgFile);
      invoker.getCmdArguments().add("-size");
      invoker.getCmdArguments().add(destImageDimension);      
      invoker.getCmdArguments().add("-extent");
      invoker.getCmdArguments().add(destImageDimension);
      if (center)
      {
         invoker.getCmdArguments().add("-gravity");
         invoker.getCmdArguments().add(destImageDimension);
      }
      invoker.getCmdArguments().add("-background");
      if (transparentBg)
      {
         invoker.getCmdArguments().add("none");
      }
      else
      {
         invoker.getCmdArguments().add("white");         
      }
      invoker.getCmdArguments().add(destImgFile);
      
      invoker.execute();
   }
}
