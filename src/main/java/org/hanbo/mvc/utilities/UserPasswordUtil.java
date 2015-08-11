package org.hanbo.mvc.utilities;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserPasswordUtil
{
   public static String passwordEncryption(String userPass)
   {
      BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
      String hashedPassword = passwordEncoder.encode(userPass);
      
      return hashedPassword;
   }
   
   public static boolean passwordEquals(String unencryptedPass, String encryptedPass)
   {
      BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
      return passwordEncoder.matches(unencryptedPass, encryptedPass);
   }
}
