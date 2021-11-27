/**
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netphenix.closeit.lite.service;

import com.netphenix.closeit.lite.bean.ResponseMessage;
import com.netphenix.closeit.lite.model.User;
import com.netphenix.closeit.lite.bean.UserPrinciple;
import com.netphenix.closeit.lite.exception.ClientMessageException;
import com.netphenix.closeit.lite.exception.DatabaseException;
import com.netphenix.closeit.lite.model.Organization;
import com.netphenix.closeit.lite.model.UserDealRole;
import com.netphenix.closeit.lite.security.jwt.JwtAuthTokenFilter;
import com.netphenix.closeit.lite.utils.Utils;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.netphenix.closeit.lite.repository.UserRepository;

/**
 * *
 *
 * @author jp
 */
@Transactional
@Service
public class UserService implements UserDetailsService {

    static Logger log = Logger.getLogger(UserService.class.getName());

    short activeStatus = (short) 1;

    @Autowired
    EmailService emailService;

    @Autowired
    UserRepository repository;

    @Autowired
    PasswordEncoder encoder;

    @Value("${dealsplus.app.jwtSecret}")
    private String jwtSecret;
    @Value("${spring.mail.username}")
    private String adminMail;

    @Value("${dealsplus.app.jwtExpiration}")
    private int jwtExpiration;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    OrganizationService organizationService;

    @Autowired
    private UserDealRoleService userDealRoleService;

    private final JwtAuthTokenFilter jwtAuthTokenFilter = new JwtAuthTokenFilter();

    // This method is written as suggested by Rekha 
    public User save(User user) throws DatabaseException {
        log.debug("Method: save");
        try {
            log.debug("User " + user.getUsername() + " is saved");
            User savedObj = repository.save(user);
            if (savedObj.getHashCode() == null) {
                setAndSaveHashcode(savedObj);
            }
            return savedObj;
        } catch (Exception e) {
            throw new DatabaseException("Database error while saving user");
        }

    }

    public User getCurrentUser() {
        log.debug("*************************************************** ShareCapitalForm3 getCurrentUser *******************************************************************");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrinciple up = (UserPrinciple) authentication.getPrincipal();
        Optional<User> user = getById(up.getId());
        final User currentUser = user.isPresent() ? user.get() : null;
        if (currentUser == null) {
            throw new AuthenticationException("Invalid User") {
            };
        }
        log.debug("*************************************************** return currentUser *******************************************************************");
        return currentUser;
    }

    public void setAndSaveHashcode(User user) {
        String originalString = user.getId() + user.getUsername() + user.getCreatedTime();
        String sha256hex = DigestUtils.sha256Hex(originalString);
        user.setHashCode(sha256hex);
        repository.save(user);
        }

    public void generateHashCodeForUsers() {
        List<User> userList = repository.getUserWithoutHashCode();
        for (User user : userList) {
            setAndSaveHashcode(user);
        }
    }

    DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

    public ResponseMessage resetPassword(User userObj, String url) throws DatabaseException {
        log.debug("Method: resetPassword");
        String username = userObj.getUsername();
        Integer resetOtp = userObj.getResetOtp();
        User user = getUserByUsesName(username);
        if (user != null && url != null) {
            String password = new Utils().passwordGenerator();
            user.setLoginAttempt(null);
            user.setStatus(null);
            user.setOtp(null);
             Integer resetAttempt = user.getResetAttempt();
            if (user.getResetOtp() == null) {
                return new ResponseMessage("OTP not available", "failure");
            }
            if (resetOtp != null) {
                if (!resetOtp.equals(user.getResetOtp())) {
                    log.warn("Invalid OTP entered for reset, user" + user.getUsername());
                    user.setResetOtp(null);
                    save(user);
                    return new ResponseMessage("Enter valid OTP", "failure");
                }
            } else {
                return new ResponseMessage("OTP is required", "failure");
            }
            if (resetAttempt != null) {
                resetAttempt = resetAttempt + 1;
                log.warn(user.getUsername() + " attempting reset password is " + resetAttempt + " time");
                if (resetAttempt == 3) {
                    try {
                        sendResetExceedAttemptEmail(user.getUsername());
                       } catch (MessagingException ex) {
                        log.error("Error in mail sending exceeded password reset attempt for user: " + user.getUsername());
                                              }
                    log.warn(user.getUsername() + " attempt 3 time password reset");
                }
                if (resetAttempt >= 3) {
                    user.setStatus("locked");
                    log.warn(user.getUsername() + " account is locked after 3 time password reset attempts");
                }
            } else {
                resetAttempt = 1;
                log.warn(user.getUsername() + " attempting reset password is " + resetAttempt + " time");
            }
            // Set password only for unlocked user
            if (user.getStatus() == null || !user.getStatus().equalsIgnoreCase("locked")) {
                user.setPassword(encoder.encode(password));
            }
            user.setLastResetTime(new Date());
            user.setResetAttempt(resetAttempt);
            user.setResetOtp(null);
            user.setLastResetOtpSentTime(null);
            save(user);
            if (user.getStatus() != null && user.getStatus().equalsIgnoreCase("locked")) {
                return new ResponseMessage("Your account is locked", "failure");
//                return "locked";
            }
            if (user.getUsername() != null && password != null) {
                try {
                    String subject = "Reset password";
                    String name = user.getFirstName() != null ? user.getFirstName() : "" + " " + user.getLastName() != null ? user.getLastName() : "";
                    sendUsernameAndPassword(name, user.getUsername(), password, url, subject);
                } catch (MessagingException ex) {
                    log.error("Reset password: Error while sending a mail for " + username);
                    return new ResponseMessage("Error while sending a mail for", "failure");
                }
            }
            return new ResponseMessage("Password reset successfully and sent to your mail", "success");
//            return "success";
        }
        log.info("Username: " + username + " not found ");
        return new ResponseMessage("Invalid username", "failure");
//        return "failure";
    }

    // Currently compare date is not used
    public Integer compareDate(Date lastResetDate, Integer resetAttempt) {
        Date today = new Date();
        try {
            today = formatter.parse(formatter.format(today));
            if (lastResetDate != null) {
                lastResetDate = formatter.parse(formatter.format(lastResetDate));
            }
        } catch (ParseException ex) {
            log.error("Time converion error");
            log.error(ex.getMessage());
        }
        if (lastResetDate != null && !today.equals(lastResetDate)) {
            resetAttempt = null;
        }
        return resetAttempt;
    }

    public void sendResetExceedAttemptEmail(String username) throws MessagingException {
        log.debug("Method: sendResetExceedAttemptEmail");
        String template;
        template = "<p>Welcome to DealPlus</p>\n"
                + "<p>Noticed! User " + username + ", more than three password reset attempts today </p>\n"
                + "<p>Regards&nbsp;</p>\n"
                + "<p>DealsPlus</p>\n"
                + "";
        String subject = "Password reset attempt";
         emailService.sendHTMLEmail(adminMail, adminMail, null, subject, template);
    }

    /**
     * Function Name addOrUpdate Input User user Output User Description add or
     * update the user details get user id equal to null then set active=1 and
     * user password is encoder and set the encoder password set user password.
     *
     * @param user
     * @param url
     * @return
     * @throws com.netphenix.closeit.lite.exception.DatabaseException
     * @throws com.netphenix.closeit.lite.exception.ClientMessageException
     */
    public User addOrUpdate(User user, String url) throws DatabaseException, ClientMessageException {
        log.debug("Method: addOrUpdate");
        // New user
        // TODO: If url is null no save action will be done
        if (user.getId() == null) {
            user.setActive(activeStatus);
            String password = new Utils().passwordGenerator();
            user.setPassword(password);
            user.setPassword(encoder.encode(password));
            // Creating user
            try {
                user = save(user);
                log.info("User " + user.getUsername() + " successfully created");
            } catch (DatabaseException e) {
                throw new DatabaseException("Database error while saving user");
            }
            try {
                if (user.getUsername() != null && password != null) {
                    String subject = "Welcome to DealsPlus";
                    String name = (user.getFirstName() != null ? user.getFirstName() : "") +" " + (user.getLastName() != null ? user.getLastName() : "");
                    sendUsernameAndPassword(name, user.getUsername(), password, url, subject);
                    }
            } catch (MessagingException ex) {
                // If error when sending a mail after creating user, Remove the user
                delete(user.getId());
                log.info("Error while sending a mail, so removing the user " + user.getUsername());
                user = null;
                throw new ClientMessageException("Error while sending a mail, user not saved");
            }
        }
        // Existing user update
        if (user != null && user.getId() != null && user.getPassword() == null) {
            Optional<User> optionalUser = getById(user.getId());
            user.setPassword(optionalUser.get().getPassword());
            try {
                user = save(user);
                log.info("User " + user.getUsername() + " successfully updated");
                if (user != null && user.getIsAdmin() == 1) {
                    userDealRoleService.deleteByUserHashCode(user.getId());
                }
            } catch (DatabaseException e) {
                throw new DatabaseException("Database error while updating user");
            }
        }
        return user;
    }

    public void sendUsernameAndPassword(String name, String username, String password, String url, String subject)
            throws MessagingException {
        log.debug("Method: sendUsernameAndPassword");
        String template = "<p>Dear " + name + "</p>\n"
                + "<p>Welcome to DealPlus!</p>\n"
                + "<p>Please login with your email id as username.</p>\n"
                + "<p>Your password is: " + password + "</p>\n"
                + "<p>Please store the above password safely for future use. You may reset the password via the application log-in page.</p>\n"
                + "<p>When logging in, you will also be asked for a One Time Password (OTP) in addition to your user name and password."
                + " The OTP is sent to your email id which you need to enter as a second factor authentication.</p>\n"
                + "<p><a href=\"" + url + "\"> Link to the Application Login Page</a>&nbsp;</p>\n"
                + "<p>Regards&nbsp;</p>\n"
                + "<p>DealsPlus</p>\n"
                + "";
        emailService.sendHTMLEmail(adminMail, username, null, subject, template);
    }

    /**
     * Function Name getById Input int id Output User Description get user
     * detail by user Id
     *
     * @param id
     * @return
     */
    public Optional<User> getById(int id) {
        log.debug("Method: getById");
        Optional<User> optionalUser = repository.findById(id);
        return optionalUser;
    }

    public Optional<User> getByHashCode(String hashCode) {
        log.debug("Method: getByHashCode");
        Optional<User> optionalUser = repository.getUserByHashCode(hashCode);
        return optionalUser;
    }

    public User getUserByHashCode(String hashCode) {
        log.debug("Method: getByHashCode");
        Optional<User> optionalUser = repository.getUserByHashCode(hashCode);
        final User user = optionalUser.isPresent() ? optionalUser.get() : null;
        return user;
    }

    /**
     * Function Name getAll Input null Output List Description getAll user
     * detail
     *
     * @return
     */
    public List<User> getAll() {
        log.debug("Method: getAll");
        return (List<User>) repository.findAll();
    }

    /**
     * Function Name getActiveUser Input null Output List Description
     * getActiveUser user detail
     *
     * @return
     */
    public List<User> getActiveUser() {
        log.debug("Method: getActiveUser");
        return (List<User>) repository.getActiveUsers(activeStatus);
    }

    public String[] getActiveMailSender() {
        log.debug("Method: getActiveMailSender");
        return repository.getActiveMailSender(activeStatus);
    }

    public List<User> getActiveMailSenderUser() {
        log.debug("Method: getActiveMailSenderUser");
        return repository.getActiveMailSenderUser(activeStatus);
    }

    /**
     * Function Name delete Input int id Output List Description getAll user
     * detail
     *
     * @param id
     */
    public void delete(int id) {
        log.debug("Method: delete");
        try {
            repository.deleteById(id);
            log.info("User id: " + id + " successfully deleted");
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error("Error while deleting user id: " + id);
        }
    }

    /**
     * Function Name deleteAll Input null Output void Description deleteAll user
     * detail
     */
    public void deleteAll() {
        log.debug("Method: deleteAll");
        try {
            repository.deleteAll();
            log.info("All users are successfully deleted");
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error("Error while deleting all users");
        }
    }

    /**
     * Function Name authenticate Input String username, String authentication
     * Output User Description check the user details and user status = active
     * then set the user LastLoginTime and save the user.
     *
     * @param username
     * @param authentication
     * @return
     * @throws com.netphenix.closeit.lite.exception.DatabaseException
     * @throws javax.mail.MessagingException
     */
    public User authenticate(String username, String authentication) throws DatabaseException, MessagingException {
        log.debug("Method: authenticate");
        User user = repository.findUserByUserName(username, activeStatus);
        if (user != null && authentication != null) {
            if (user.getResetAttempt() != null && user.getResetAttempt() >= 3) {
                try {
                    sendMailForSuccessLogin(user.getUsername());
                } catch (MessagingException ex) {
                    log.error("Authenticate: Error while sending a authentication mail for admin");
                }
            }            
            user.setLastLoginTime(new Date());
            user.setLastOtpSentTime(null);
            user.setLoginAttempt(null);
            user.setResetAttempt(null);
            user.setStatus(null);
            user.setOtp(null);
            save(user);
            log.debug("User " + user.getUsername() + " last login updated");
        }
        return user;
    }

    public void sendMailForSuccessLogin(String username) throws MessagingException {
        log.debug("Method: sendMailForSuccessLogin");
        String template = "<p>Welcome to DealPlus</p>\n"
                + "<p>User: " + username + " successfully logged in</p>\n"
                + "<p>Regards&nbsp;</p>\n"
                + "<p>DealsPlus</p>\n"
                + "";
        String subject = "Successful login";
        emailService.sendHTMLEmail(adminMail, adminMail, null, subject, template);
    }

    /**
     * Function Name loadUserByUsername Input String username Output UserDetails
     * Description get user details by user name and active status =1 then build
     * with user object or details
     *
     * @param username
     * @return
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("Method: loadUserByUsername");
        User user = repository.findUserByUserName(username);
        return UserPrinciple.build(user);
    }

//    @Override
    public UserDetails loadUserByDealHashCode(String dealHashCode, String userHashCode) throws UsernameNotFoundException {
        log.debug("Method: loadUserByUsername");
        UserDealRole udr = userDealRoleService.getRoleByDealHashCode(dealHashCode, userHashCode);
//        User user = repository.findUserByUserName(username);
        if (udr != null) {
            return UserPrinciple.build(udr);
        }
        return null;
    }

    /**
     * Function Name getUserByUsesName Input String username, Integer id Output
     * Boolean Description get user name by username and user Id user object is
     * null return true other false.
     *
     * @param username
     * @param id
     * @return
     */
    public Boolean getUserByUsesName(String username, Integer id) {
        log.debug("Method: getUserByUsesName");
        User user = repository.getUserByUsesName(username, id);
        Boolean isExists = false;
        if (user != null) {
            isExists = true;
        }
        return user == null;
//        return isExists;
    }

    /**
     * Function Name findUserByUserNameAndOtp Input String username, Integer otp
     * Output User Description get user details
     *
     * @param username
     * @param otp
     * @return
     */
    public User findUserByUserNameAndOtp(String username, Integer otp) {
        log.debug("Method: getUserByUsesName");
        return repository.findUserByUserNameAndOtp(username, activeStatus, otp);
    }

    /**
     * Function Name getUserByUsesName Input String username Output User
     * Description get user name by username then User object is null return
     * true other false.
     *
     * @param username
     * @return
     */
    public User getUserByUsesName(String username) {
        log.debug("Method: getUserByUsesName");
        User user = repository.getUserByUsesName(username);
        return user;
    }

    /**
     * Function Name getByUserId Input int id Output User Description get user
     * details by user Id
     *
     * @param id
     * @return
     */
    public User getByUserId(int id) {
        log.debug("Method: getByUserId");
        Optional<User> optionalUser = repository.findById(id);
        final User user = optionalUser.isPresent() ? optionalUser.get() : null;
        return user;
    }

    public String refreshToken(HttpServletRequest request, String username) {
        String token = jwtAuthTokenFilter.getJwt(request);
        UserDetails details = loadUserByUsername(username);
        Map<String, Object> claims = new HashMap<>();
        token = doGenerateToken(claims, details.getUsername());
        return token;
    }

    //while creating the token -
    //1. Define  claims of the token, like Issuer, Expiration, Subject, and the ID
    //2. Sign the JWT using the HS512 algorithm and secret key.
    //3. According to JWS Compact Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
    //   compaction of the JWT to a URL-safe string 
    public String doGenerateToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration * 1000))
                .signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
    }

    /**
     * Function Name sendOtp Input username Output void Description Otp send to
     * user
     *
     * @param user
     * @return
     * @throws com.netphenix.closeit.lite.exception.DatabaseException
     * @throws javax.mail.MessagingException
     */
    public ResponseMessage sendOTP(User user) throws DatabaseException, MessagingException {
        log.debug("Method: sendOtp");
        String username = user.getUsername();
        String password = user.getPassword();
        User userObj = getUserByUsesName(username);
        if (userObj != null) {
            if (userObj.getActive() != 1) {
                log.warn("User inactive for " + userObj.getUsername() + ", otp not sent");
                return new ResponseMessage("User inactive", "failure");
            }
            if (userObj.getStatus() != null && userObj.getStatus().equalsIgnoreCase("locked")) {
                log.warn("Account already locked for " + userObj.getUsername() + ", otp not sent");
                return new ResponseMessage("Your account is locked", "failure");
            }
            try {
                Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(username, password));
                if (!authentication.isAuthenticated()) {
                    log.warn("Invalid login details for user: " + username + " while sending otp");
                    return new ResponseMessage("Username / password incorrect", "failure");
                }
            } catch (AuthenticationException e) {
                log.warn("Invalid login details for user: " + username + " while sending otp");
                String invalidLoginMessage = invalidLoginAttempt(userObj);
                if (invalidLoginMessage != null) {
                    log.warn(invalidLoginMessage);
                    return new ResponseMessage(invalidLoginMessage, "failure");
                }
                return new ResponseMessage("Enter valid login details", "failure");
            }
            if (userObj.getLastOtpSentTime() != null) {
                if (Utils.getSecondsFromTime(userObj.getLastOtpSentTime()) <= 90) {
                    return new ResponseMessage("Please wait, Next OTP can be requested after "
                            + (90 - Utils.getSecondsFromTime(userObj.getLastOtpSentTime()))
                            + " seconds", "failure");
                }
            }
            int otp = new Random().nextInt(900000) + 100000;
            userObj.setOtp(otp);
            userObj.setLastOtpSentTime(new Date());
            save(userObj);
            try {
                if (userObj.getUsername() != null) {
                    sendOTPToEmail(otp, userObj.getUsername(), "login");
                }
            } catch (MessagingException ex) {
                ex.printStackTrace();
                // If error when sending a mail 
                log.warn("Error while sending OTP " + ex.getMessage());
                return new ResponseMessage("Error while sending OTP", "failure");
            }
            return new ResponseMessage("A one time password has been sent to your email address", "success");
        }
        log.warn(username + " is not available");
        return new ResponseMessage("User not available", "failure");
    }

    /**
     * Function Name sendOtp Input username Output void Description Otp send to
     * user
     *
     * @param user
     * @return
     * @throws com.netphenix.closeit.lite.exception.DatabaseException
     * @throws javax.mail.MessagingException
     */
    public ResponseMessage sendResetOTP(User user) throws DatabaseException, MessagingException {
        log.debug("Method: sendResetOTP");
        String username = user.getUsername();
        User userObj = getUserByUsesName(username);
        if (userObj != null) {
            if (userObj.getActive() != 1) {
                log.warn("User inactive for " + userObj.getUsername() + ", otp not sent");
                return new ResponseMessage("User inactive", "failure");
            }
            if (userObj.getStatus() != null && userObj.getStatus().equalsIgnoreCase("locked")) {
                log.warn("Account already locked for " + userObj.getUsername() + ", otp not sent");
                return new ResponseMessage("Your account is locked", "failure");
            }
            if (userObj.getLastResetOtpSentTime() != null) {
                if (Utils.getSecondsFromTime(userObj.getLastResetOtpSentTime()) <= 90) {
                    return new ResponseMessage("Remaining time for next otp is "
                            + (90 - Utils.getSecondsFromTime(userObj.getLastResetOtpSentTime()))
                            + " seconds", "failure");
                }
            }
            int otp = new Random().nextInt(900000) + 100000;
            userObj.setResetOtp(otp);
            //login attempt null
            userObj.setLoginAttempt(null);
            userObj.setStatus(null);
            userObj.setLastResetOtpSentTime(new Date());
            save(userObj);
            try {
                if (userObj.getUsername() != null) {
                    sendOTPToEmail(otp, userObj.getUsername(), "reset");
                }
            } catch (MessagingException ex) {
                // If error when sending a mail 
                log.warn("Error while sending OTP" + ex);
                return new ResponseMessage("Error while sending OTP", "failure");
            }
            return new ResponseMessage("A one time password has been sent to your email address", "success");
        }
        log.warn(username + " is not available");
        return new ResponseMessage("User not available", "failure");
    }

    /**
     * Function Name sendOtp Input username Output void Description Otp send to
     * user
     *
     * @param hashcode
     * @param url
     * @return
     * @throws com.netphenix.closeit.lite.exception.DatabaseException
     * @throws javax.mail.MessagingException
     */
    public ResponseMessage unlockUser(String hashcode, String url) throws DatabaseException, MessagingException {
        log.debug("Method: sendResetOTP");
        Optional<User> userOptional = getByHashCode(hashcode);
        if (userOptional.isPresent()) {
            User userObj = userOptional.get();
            String username = userObj.getUsername();
            if (userObj.getActive() != 1) {
                log.warn("User inactive for " + userObj.getUsername() + ", otp not sent");
                return new ResponseMessage("User inactive", "failure");
            }
            String password = new Utils().passwordGenerator();
            userObj.setPassword(encoder.encode(password));
            userObj.setStatus(null);
            userObj.setResetAttempt(null);
            userObj.setLoginAttempt(null);
            try {
                if (userObj.getUsername() != null && url != null) {
                    save(userObj);
                    String subject = "DealPlus Account unlocked ";
                    String name = (userObj.getFirstName() != null ? userObj.getFirstName() : "") + " " + (userObj.getLastName() != null ? userObj.getLastName() : "");
                    sendUsernameAndPassword(name, username, password, url, subject);
                }
            } catch (MessagingException ex) {
                // If error when sending a mail 
                log.warn("Error while sending OTP" + ex);
                return new ResponseMessage("Error while sending password", "failure");
            }
            return new ResponseMessage("User unlocked successfully", "success");
        }
        log.warn("User not available");
        return new ResponseMessage("User not available", "failure");
    }

    public void sendOTPToEmail(Integer otp, String username, String type) throws MessagingException {
        log.debug("Method: sendOtpToEmail");
        String template = "<p>Welcome to DealPlus</p>\n"
                + "<p>Your one time password for current " + type + " is " + otp + "</p>\n"
                + "<p>Regards&nbsp;</p>\n"
                + "<p>DealsPlus</p>\n"
                + "";
        String subject = "OTP";
        emailService.sendHTMLEmail(adminMail, username, null, subject, template);
    }

    public String invalidLoginAttempt(User userObj) throws DatabaseException {
        String message = null;
        Integer loginAttempt = userObj.getLoginAttempt();
        if (loginAttempt != null) {
            loginAttempt = loginAttempt + 1;
            if (loginAttempt == 3) {
                message = "Attempt exceeded 3 times, account locked";
                userObj.setStatus("locked");
                log.warn(userObj.getUsername() + " account is locked after 3 attempts");
            }
            if (loginAttempt == 2) { 
                message = "Last 2 attempt failed next login attempt failure will lock the account";
                log.warn(userObj.getUsername() + " last 2 attempt failed next login attempt failure will lock the account");
            }
        } else {
            loginAttempt = 1;
        }
        userObj.setLoginAttempt(loginAttempt);
        save(userObj);
        return message;
    }

    public Map scriptingForUserOrganizationToOrganization() {
        log.info("Organization Script Start .... ");
        List<User> users = getAll();       
        if (users != null && users.size() > 0) {
            for (Iterator<User> iterator = users.iterator(); iterator.hasNext();) {
                User next = iterator.next();
                if (next.getOrganization() != null) {                                 
                    Organization organization = organizationService.getOrganizationByName(next.getOrganization());
                    if (organization != null) {
                        next.setOrganizationId(organization);
                        log.info("Organization name is already in Organization table");
                        log.info("Add OrganizationId in User organizationId");
                    } else {
                        organization = new Organization();
                        organization.setName(next.getOrganization());
                        organization.setDescription(next.getOrganization());
                        organization.setCreatedTime(next.getCreatedTime());
                        organization.setUpdatedTime(new Date());
//                        organization.setCreatedBy(next);
//                        organization.setUpdatedBy(next);
                        organizationService.addOrUpdate(organization); 
                        next.setOrganizationId(organization);
                        log.info("Organization name is added in Organization table");
                        log.info("Add OrganizationId in User organizationId");
                    }
                }       
            }       
        }
        log.info("Organization Script done ...! ");
        Map map = new HashMap();
        map.put("status", "Success");
        return map;
    }

    public List<User> getResponsibleUserByOrganization(Integer orgId) {
        List<User> users = repository.getResponsibleUserByOrganization(orgId);
        return users;
    }

    public ResponseMessage userChangeActiveStatus(User user) throws DatabaseException {
        User user1 = getUserByHashCode(user.getHashCode());
        user1.setActive(user.getActive());
        save(user1);
        Map map = new HashMap();
        map.put("hashCode", user1.getHashCode());
        map.put("username", user1.getUsername());
        map.put("active", user1.getActive());
        return new ResponseMessage("User Status Updated", map);
    }

    public Map getByUserNameExits(String userName) {
        User user = repository.getByUserNameExits(userName);
        Map userMap = new HashMap();
        if (user != null && user.getActive() == 1) {
            userMap.put("message", "User Name already exits");
            userMap.put("status", "active");
        } else if (user != null && user.getActive() == 0) {
            userMap.put("message", "User name already exits do u want to active this user ? ");
            userMap.put("status", "inActive");
            userMap.put("hashCode", user.getHashCode());
        } else {
            userMap.put("message", "User Name does not exits");
            userMap.put("status", "new");
        }
        return userMap;
    }

    public ResponseMessage softDeleteForUser(String hashCode) throws DatabaseException {
        User user = getUserByHashCode(hashCode);
        if (user != null) {
            Short status = 0;
            user.setUsername(null);
            user.setOrganization(null);
            user.setOrganizationId(null);
            user.setActive(status);
            user.setStatus("Delete");
            user.setSendMail(status);
            user.setPhone(null);
            save(user);
        }
        return new ResponseMessage("Deleted Successfully");
    }

    public List<User> getActiveAndInActiveUser() {
        log.debug("Method: getActiveUser");
        generateHashCodeForUsers();
        return (List<User>) repository.getActiveAndInActiveUser();
    }

    public ResponseMessage existingUserChangeActiveStatus(User user) throws DatabaseException {
        User user1 = getUserByHashCode(user.getHashCode());
        user1.setActive(user.getActive());
        save(user1);
        return new ResponseMessage("User Status Updated", user1);
    }

    public ResponseMessage isvalid(){
       return new ResponseMessage("Success");
      }
}
