package com.ideaco.andre.controller;

import com.ideaco.andre.dto.JobDTO;
import com.ideaco.andre.model.JobModel;
import com.ideaco.andre.service.FileService;
import com.ideaco.andre.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class JobController {

    @Autowired
    private JobService jobService;

    @Autowired
    private FileService fileService;

    @GetMapping("/data")
    public List<JobModel> getDataAllUser() {
        return jobService.getDataAllJob();
    }

    @GetMapping("/data/name")
    public List<JobModel> getDataByNameUser(@RequestParam("userName") String userName) {
        return jobService.getDataByTitle(userName);
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(
            @RequestParam("userName") String userName,
            @RequestParam("userPassword") String userPassword,
            @RequestParam("userEmail") String userEmail,
            @RequestParam("userPhone") String userPhone,
            @RequestParam("userAddress") String userAddress,
            @RequestParam("userResume") String userResume) {
        try {
            JobModel jobModel = new JobModel();
            jobModel.setUserName(userName);
            jobModel.setUserPassword(userPassword);
            jobModel.setUserEmail(userEmail);
            jobModel.setUserPhone(userPhone);
            jobModel.setUserAddress(userAddress);
            jobModel.setUserResume(userResume);

            JobModel registeredUser = jobService.createJob(jobModel);
            return ResponseEntity.ok(
                    "Registration successful \n" +
                            "User ID: " + registeredUser.getUserId() + "\n" +
                            "User Name: " + registeredUser.getUserName() + "\n" +
                            "User Email: " + registeredUser.getUserEmail() + "\n" +
                            "User Password: " + registeredUser.getUserPassword() + "\n" +
                            "User Phone: " + registeredUser.getUserPhone() + "\n" +
                            "User Address: " + registeredUser.getUserAddress() + "\n" +
                            "User Resume: " + registeredUser.getUserResume()
            );
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(
            @RequestParam("userEmail") String userEmail,
            @RequestParam("userPassword") String userPassword
    ) {
        try {
            JobModel loggedInUser = jobService.loginUser(userEmail, userPassword);
            return ResponseEntity.ok("Login successful. User ID: " + loggedInUser.getUserId());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /* POST */
    @PostMapping("/createUser")
    public JobModel postCreateUser(
            @RequestParam("userName") String userName,
            @RequestParam("userPassword") String userPassword,
            @RequestParam("userEmail") String userEmail,
            @RequestParam("userPhone") String userPhone,
            @RequestParam("userAddress") String userAddress,
            @RequestParam("userResume") String userResume
    ) {
        return jobService.postUser(
                userName,
                userPassword,
                userEmail,
                userPhone,
                userAddress,
                userResume
        );
    }

    @PostMapping("/createMultipleUser")
    public List<JobModel> postCreateMultipleUser(@RequestBody List<JobModel> userModels) {
        return jobService.postMultipleUser(userModels);
    }

    /* PUT */
    @PutMapping("/putUpdateUser")
    public JobModel putUpdateUser(@RequestBody JobModel userModel) {
        return jobService.putUpdateUser(userModel);
    }

    /* PATCH */
    @PatchMapping("/patchUpdateUser")
    public JobModel patchUpdateUser(
            @RequestParam("userId") int userId,
            @RequestParam("userName") String userName
    ) {
        return jobService.patchUpdateUser(userId, userName);
    }

    /* DELETE */
    @DeleteMapping("/deleteUser")
    public String deleteUser(@RequestParam("userId") int userId) {
        boolean response = jobService.deleteUser(userId);

        if (response) {
            return "Delete success";
        } else {
            return "Delete failed";
        }
    }

    /* MULTIPART */
    @PostMapping("/uploadFile")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is empty");
        }

        try {
            String fileName = file.getOriginalFilename();
            fileService.saveFile(fileName, file); // Assuming fileService handles file saving
            return ResponseEntity.ok("File uploaded successfully: " + fileName);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to upload file: " + e.getMessage());
        }
    }



    /* DTO */
    @GetMapping("/datadto")
    public JobDTO getDataWithDTO(@RequestParam("userId") int userId) {
        return jobService.dataWithDTO(userId);
    }
}
