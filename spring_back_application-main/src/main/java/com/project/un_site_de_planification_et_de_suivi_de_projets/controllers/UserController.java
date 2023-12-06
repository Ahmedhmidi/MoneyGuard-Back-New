package com.project.un_site_de_planification_et_de_suivi_de_projets.controllers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.un_site_de_planification_et_de_suivi_de_projets.config.FileUploadUtil;
import com.project.un_site_de_planification_et_de_suivi_de_projets.entities.Image;
import com.project.un_site_de_planification_et_de_suivi_de_projets.entities.Notification;
import com.project.un_site_de_planification_et_de_suivi_de_projets.entities.Role;
import com.project.un_site_de_planification_et_de_suivi_de_projets.entities.User;
import com.project.un_site_de_planification_et_de_suivi_de_projets.repos.RoleRepository;
import com.project.un_site_de_planification_et_de_suivi_de_projets.services.ImageService;
import com.project.un_site_de_planification_et_de_suivi_de_projets.services.NotificationService;
import com.project.un_site_de_planification_et_de_suivi_de_projets.services.UserService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.project.un_site_de_planification_et_de_suivi_de_projets.entities.ERole.ROLE_Financial;

@RestController
@RequestMapping("/api/users")
public class UserController {


   private final UserService userService;
    private final RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    private ImageService imageService;


     private  final  NotificationService notificationService;
    @Autowired
    public UserController(UserService userService, NotificationService notificationService, ImageService imageService, RoleRepository roleRepository) {

        this.userService = userService;
        this.imageService = imageService ;
        this.roleRepository = roleRepository;
        this.notificationService = notificationService;

    }

    @PostMapping("/add")
    @ResponseBody
    public User add_New_User(@RequestBody User user) {
        return userService.addUser(user);
    }

    @JsonIgnore
    @GetMapping("/all")
    @ResponseBody
    public List<User> all_users(){
        return userService.findAllUsers();
    }


    @GetMapping("/id/{id}")
    @ResponseBody
    public User user_id(@PathVariable("id") long id){
        return UserService.findUserById(id);
    }


    @PutMapping("/update")
    @ResponseBody
    public void updateUser(@RequestBody User user){
        userService.updateUser(user); }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public void supp_user(@PathVariable("id") long id){
        userService.deleteUser(id); }

    @PostMapping("/{sid}/add-image")
    public void addImage(@PathVariable Long sid, @RequestParam("image") MultipartFile multipartFile) throws IOException {

        User user = UserService.findUserById(sid);

        userService.addUser(user);
        if (!multipartFile.isEmpty()) {
            String orgFileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            String ext = orgFileName.substring(orgFileName.lastIndexOf("."));
            String uploadDir = "users-photos/";
            String fileName = "user-" + user.getId() + ext;
            Image img = new Image(multipartFile, fileName, ext, multipartFile.getBytes());
            Image image = imageService.addImageLa(img);
            user.setImage(image);
            FileUploadUtil.saveFile(uploadDir,fileName,multipartFile);
            userService.updateUser(user);
        }
    }

    @RequestMapping(value = "/{sid}/display-image")
    public void getUserPhoto(HttpServletResponse response, @PathVariable("sid") long sid) throws Exception {
        User user = UserService.findUserById(sid);
        Image image = user.getImage();

        if(image != null) {
            response.setContentType(image.getFileType());
            InputStream inputStream = new ByteArrayInputStream(image.getData());
            IOUtils.copy(inputStream, response.getOutputStream());
        }
    }

    @JsonIgnore
    @GetMapping("/all/financials")
    @ResponseBody
    public List<User> all_financials(){

        List<User> allUsers = userService.findAllUsers();
        List<User> finanList = new ArrayList<>();
        for ( User user : allUsers){
            for (Role role : user.getRoles()){
                if (role.getName().toString().equals("ROLE_Financial")){
                    finanList.add(user);
                }
            }
        }
        return finanList;
    }


    @GetMapping("notifications/{userId}")
    public ResponseEntity<?> getNotificationsByUserId(@PathVariable long userId){
        User user = UserService.findUserById(userId);
        if (user == null){
            return ResponseEntity.badRequest().body("user not found");
        }
        Set<Notification> notifications = user.getNotifications();
        if (notifications.isEmpty()){
            return ResponseEntity.badRequest().body("notifications doesn't exist");
        }
        return ResponseEntity.ok(notifications);
    }







}
