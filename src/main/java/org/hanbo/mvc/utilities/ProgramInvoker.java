package org.hanbo.mvc.utilities;

import java.util.List;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ProgramInvoker
{
   private String commandExec;
   private List<String> cmdArguments;
   private String cmdStdOut;
   private String cmdErrOut;
   private int exitCode;
   
   public ProgramInvoker()
   {
      setCmdArguments(new ArrayList<String>());
   }

   public ProgramInvoker(String cmdExec, List<String> args)
   {
      this();
      this.commandExec = cmdExec;
      this.cmdArguments.addAll(args);
   }
   
   public void execute()
   {
      try
      {
         List<String> commandAndArgs = new ArrayList<String>();
         commandAndArgs.add(this.commandExec);
         commandAndArgs.addAll(this.cmdArguments);
         
         Process process = new ProcessBuilder(commandAndArgs).start();
         process.waitFor();
         this.cmdStdOut = readExecutionStdOut(process.getInputStream());
         this.cmdErrOut = readExecutionStdOut(process.getErrorStream());
         
         exitCode = process.exitValue();
      }
      catch(Exception e)
      {
         throw new RuntimeException("Error running external command.", e);
      }
   }
   
   private String readExecutionStdOut(InputStream is)
      throws IOException
   {
      StringBuilder sb = new StringBuilder(); 
      try (InputStream readFrom = is)
      {
         try (InputStreamReader isr = new InputStreamReader(readFrom))
         {
            try (BufferedReader br = new BufferedReader(isr))
            {
               String line;
               while ((line = br.readLine()) != null) {
                  sb.append(line);
               }
            }
         }
      }
      
      return sb.toString();
   }
   
   public String getCommandExec()
   {
      return commandExec;
   }

   public void setCommandExec(String commandExec)
   {
      this.commandExec = commandExec;
   }

   public List<String> getCmdArguments()
   {
      return cmdArguments;
   }

   public void setCmdArguments(List<String> cmdArguments)
   {
      this.cmdArguments = cmdArguments;
   }

   public String getCmdStdOut()
   {
      return cmdStdOut;
   }

   public void setCmdStdOut(String cmdStdOut)
   {
      this.cmdStdOut = cmdStdOut;
   }

   public String getCmdErrOut()
   {
      return cmdErrOut;
   }

   public void setCmdErrOut(String cmdErrOut)
   {
      this.cmdErrOut = cmdErrOut;
   }

   public int getExitCode()
   {
      return exitCode;
   }

   public void setExitCode(int exitCode)
   {
      this.exitCode = exitCode;
   }
}
