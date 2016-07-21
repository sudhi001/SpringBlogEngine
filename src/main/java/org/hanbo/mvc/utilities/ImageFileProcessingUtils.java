package org.hanbo.mvc.utilities;

public class ImageFileProcessingUtils
{
   private String externalProgramsPath;
   
   public ImageFileProcessingUtils(String extProgsPath)
   {
      externalProgramsPath = extProgsPath;
   }
   
   public boolean resizeImage(String originalImgFile,
      String destImgFile,
      boolean center,
      boolean transparentBg,
      int destWidth,
      int destHeight)
   {
      String exeProgPath = getProgramPath(externalProgramsPath, "convert");
      String destImageDimension
         = String.format("%dx%d", destWidth, destHeight);
      
      ProgramInvoker invoker
         = new ProgramInvoker();
      invoker.setCommandExec(exeProgPath);
      invoker.getCmdArguments().add("-trim");
      invoker.getCmdArguments().add(originalImgFile);
      invoker.getCmdArguments().add("-resize");
      invoker.getCmdArguments().add(destImageDimension);
      if (center)
      {
         invoker.getCmdArguments().add("-gravity");
         invoker.getCmdArguments().add("Center");
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
      invoker.getCmdArguments().add("-extent");
      invoker.getCmdArguments().add(destImageDimension);
      invoker.getCmdArguments().add(destImgFile);
      
      invoker.execute();
      
      return invoker.getExitCode() == 0;
   }
   
   public String getImageInfo(String imageFileName)
   {
      String retVal = null;
      
      String exeProgPath = getProgramPath(externalProgramsPath, "identify");

      ProgramInvoker invoker
         = new ProgramInvoker();
      invoker.setCommandExec(exeProgPath);
      invoker.getCmdArguments().add("-ping");
      invoker.getCmdArguments().add("-format");
      invoker.getCmdArguments().add("\"%m %w %h\"");
      invoker.getCmdArguments().add(imageFileName);
      
      invoker.execute();
      boolean opSuccess = invoker.getExitCode() == 0; 
      if (opSuccess)
      {
         retVal = invoker.getCmdStdOut();
      }
      
      return retVal;
   }
   
   private static String getProgramPath(String programPath, String programName)
   {
      String fullProgramName;
      if (isWindows())
      {
         fullProgramName = String.format("%s\\%s.exe", programPath, programName);
      }
      else
      {
         fullProgramName = String.format("%s/%s", programPath, programName);
      }
      
      return fullProgramName;
   }
   
   private static boolean isWindows()
   {
      String osName = System.getProperty("os.name");
      return osName.contains("windows");
   }
}
