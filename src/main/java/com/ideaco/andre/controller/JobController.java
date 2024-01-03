package com.ideaco.andre.controller;

import com.ideaco.andre.model.JobModel;
import com.ideaco.andre.service.JobService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@RequestBody JobModel jobModel) {
        JobModel registeredUser = jobService.register(jobModel);
        if (registeredUser != null) {
            return ResponseEntity.ok(getSuccessResponse("User registered successfully"));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Object> loginUser(@RequestBody LoginRequest loginRequest) {
        boolean loginSuccess = jobService.login(loginRequest.getEmail(), loginRequest.getPassword());
        if (loginSuccess) {
            return ResponseEntity.ok(getSuccessResponse("Login successful"));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(getFailureResponse("Login failed"));
        }
    }

    @GetMapping("/users")
    public ResponseEntity<JobModel> getUserById(@PathVariable String userId) {
        try {
            Long parsedUserId = Long.parseLong(userId);
            JobModel user = jobService.getUserById(parsedUserId);
            if (user != null) {
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        }
    }


    @GetMapping("/all")
    public ResponseEntity<List<JobModel>> getAllUsers() {
        List<JobModel> allUsers = jobService.getAllUsers();
        return ResponseEntity.ok(allUsers);
    }

    private Object getSuccessResponse(String message) {
        return new Response(true, message);
    }

    private Object getFailureResponse(String message) {
        return new Response(false, message);
    }

    static class Response {
        private final boolean success;
        private final String message;

        public Response(boolean success, String message) {
            this.success = success;
            this.message = message;
        }

        public boolean isSuccess() {
            return success;
        }

        public String getMessage() {
            return message;
        }
    }

    @Setter
    @Getter
    static class LoginRequest {
        private String email;
        private String password;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
