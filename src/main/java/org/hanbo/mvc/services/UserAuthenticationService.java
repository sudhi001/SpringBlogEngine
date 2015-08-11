package org.hanbo.mvc.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hanbo.mvc.entities.LoginUser;
import org.hanbo.mvc.entities.UserRole;
import org.hanbo.mvc.exceptions.WebAppException;
import org.hanbo.mvc.models.UserPrincipalDataModel;
import org.hanbo.mvc.repositories.UsersRepository;
import org.hanbo.mvc.utilities.UserPasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.apache.log4j.*;

public class UserAuthenticationService
   implements AuthenticationProvider
{
   private static Logger _logger = LogManager.getLogger(UserAuthenticationService.class);
   
   @Autowired
   private UsersRepository userRepository;
   
   @Override
   public Authentication authenticate(Authentication authentication)
   {
      String name = authentication.getName();
      String password = authentication.getCredentials().toString();
      
      if (name == null || name.length() <= 0)
      {
         throw new WebAppException("Use name (password) is NULL", WebAppException.ErrorType.SECURITY);
      }
      if (password == null || password.length() <= 0)
      {
         throw new WebAppException("User password is null or empty.", WebAppException.ErrorType.SECURITY);
      }
      
      LoginUser authenticatedUser = this.authenticateUser(name, password);
      if (authenticatedUser != null)
      {
         boolean isUserActive = authenticatedUser.isActive();
         if (!isUserActive)
         {
            return null;
         }
         
         UserPrincipalDataModel userPrincipal
            = createUserPrincipal(authenticatedUser);
         
         Authentication userAuth = createAuthentication(
            userPrincipal, password
         );
         
         return userAuth;
      }
      
      return null;
    }

   @Override
   public boolean supports(Class<?> authentication)
   {
      return authentication.equals(UsernamePasswordAuthenticationToken.class);
   }
   
   private Authentication createAuthentication(
      UserPrincipalDataModel userPrincipal, String credential
   )
   {
      List<GrantedAuthority> grantedAuths
         = createLoginUserAuthority(userPrincipal.getUserRole());
      
      if(grantedAuths.size() == 0)
      {
         return null;
      }
     
      credential = encryptPassword(credential);

      _logger.info("Creating authentication here...");
      
      Authentication auth
         = new UsernamePasswordAuthenticationToken(
            userPrincipal, credential, grantedAuths
         );

      _logger.info("Creating authentication here... Done.");

      return auth;
   }
   
   private LoginUser authenticateUser(String userName, String userPass)
   {
      LoginUser user = this.userRepository.getUser(userName);
      if (user != null)
      {
         String passEncrypted = user.getUserPass();
         
         if (UserPasswordUtil.passwordEquals(userPass, passEncrypted))
         {
            _logger.info("Authentication Successful.");
            return user;
         }
      }
      
      return null;
   }
   
   private List<GrantedAuthority> createLoginUserAuthority(String userRole)
   {
      List<GrantedAuthority> grantedAuths = new ArrayList<GrantedAuthority>();
      
      if (userRole.equals("ROLE_ADMIN"))
      {
         grantedAuths.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
         grantedAuths.add(new SimpleGrantedAuthority("ROLE_STAFF"));
         grantedAuths.add(new SimpleGrantedAuthority("ROLE_USER"));
         grantedAuths.add(new SimpleGrantedAuthority("ROLE_GUEST"));
      }
      else if (userRole.equals("ROLE_STAFF"))
      {
         grantedAuths.add(new SimpleGrantedAuthority("ROLE_STAFF"));
         grantedAuths.add(new SimpleGrantedAuthority("ROLE_USER"));
         grantedAuths.add(new SimpleGrantedAuthority("ROLE_GUEST"));
      }
      else if (userRole.equals("ROLE_USER"))
      {
         grantedAuths.add(new SimpleGrantedAuthority("ROLE_USER"));
         grantedAuths.add(new SimpleGrantedAuthority("ROLE_GUEST"));
      }
      else if (userRole.equals("ROLE_GUEST"))
      {
         grantedAuths.add(new SimpleGrantedAuthority("ROLE_GUEST"));
      }
      else
      {
         grantedAuths.clear();
      }
      
      return grantedAuths;
   }
   
   private static UserPrincipalDataModel createUserPrincipal(LoginUser userLoggedIn)
   {
      if (userWithNoRole(userLoggedIn))
      {
         return null;
      }
      
      String roleName = getLoggedInUserRoleName(userLoggedIn);
      
      UserPrincipalDataModel userPrincipal = new UserPrincipalDataModel();
      userPrincipal.setUserId(userLoggedIn.getId());
      userPrincipal.setUserName(userLoggedIn.getUserName());
      userPrincipal.setUserRole(roleName);
      userPrincipal.setUserActive(userLoggedIn.isActive());
      
      return userPrincipal;
   }
   
   private static boolean userWithNoRole(LoginUser userLoggedIn)
   {
      Set<UserRole> allRoles = userLoggedIn.getUserRoles();
      return allRoles.isEmpty();
   }

   private static String getLoggedInUserRoleName(LoginUser userLoggedIn)
   {
      Set<UserRole> allRoles = userLoggedIn.getUserRoles();
      
      UserRole role = (UserRole)(allRoles.toArray())[0];
      return role.getRoleName();
   }
   
   private static String encryptPassword(Object credential)
   {
      if (credential != null)
      {
         String password = (String)credential;
         
         return UserPasswordUtil.passwordEncryption(password);
      }
      
      throw new WebAppException("Use credential (password) is NULL", WebAppException.ErrorType.SECURITY);
   }
}
