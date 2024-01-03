package com.ideaco.andre.service;

import com.ideaco.andre.model.JobModel;
import com.ideaco.andre.repository.JobModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobService {

    private final JobModelRepository jobModelRepository;
    @Autowired
    public JobService(JobModelRepository jobModelRepository) {
        this.jobModelRepository = jobModelRepository;
    }

    public boolean login(String userEmail, String userPassword) {
        return jobModelRepository.findByUserEmailAndUserPassword(userEmail, userPassword);
    }

    public JobModel register(JobModel jobModel) {
        return jobModelRepository.save(jobModel);
    }
    public JobModel getUserByEmail(String userEmail) {
        return jobModelRepository.findByUserEmail(userEmail);
    }


    public List<JobModel> getAllUsers() {
        return jobModelRepository.findAll();
    }

    public JobModel getUserById(Long userId) {
        return null;
    }
}
