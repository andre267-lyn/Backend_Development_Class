package com.ideaco.andre.service;// JobService.java

// imports (sesuaikan jika ada)

import com.ideaco.andre.dto.JobDTO;
import com.ideaco.andre.model.JobModel;
import com.ideaco.andre.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    public JobDTO dataWithDTO(long userId) {
        Optional<JobModel> jobModelOptional = jobRepository.findById(userId);
        return jobModelOptional.map(this::convertDTO).orElse(null);
    }

    public boolean deleteUser(long userId) {
        try {
            jobRepository.deleteById(userId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public JobModel patchUpdateUser(long userId, String userName) {
        Optional<JobModel> optionalJobModel = jobRepository.findById(userId);
        return optionalJobModel.map(jobModel -> {
            jobModel.setUserName(userName);
            return jobRepository.save(jobModel);
        }).orElse(null);
    }

    public JobModel putUpdateUser(JobModel userModel) {
        return jobRepository.save(userModel);
    }

    public List<JobModel> postMultipleUser(List<JobModel> userModels) {
        return jobRepository.saveAll(userModels);
    }

    public JobModel postUser(String userName, String userPassword, String userEmail, String userPhone, String userAddress, String userResume) {
        JobModel jobModel = new JobModel();
        jobModel.setUserName(userName);
        jobModel.setUserPassword(userPassword);
        jobModel.setUserEmail(userEmail);
        jobModel.setUserPhone(userPhone);
        jobModel.setUserResume(userResume);
        return jobRepository.save(jobModel);
    }

    public JobModel loginUser(String userEmail, String userPassword) {
        return jobRepository.findByUserEmail(userEmail).orElse(null);
    }

    private JobDTO convertDTO(JobModel item) {
        JobDTO jobDTO = new JobDTO();
        jobDTO.setUserName(item.getUserName());
        jobDTO.setUserEmail(item.getUserEmail());
        jobDTO.setUserPhone(item.getUserPhone());
        jobDTO.setUserAddress(item.getUserAddress());
        jobDTO.setUserResume(item.getUserResume());

        return jobDTO;
    }

    public List<JobModel> getDataAllJob() {
        return jobRepository.findAll();
    }

    public List<JobModel> getDataByTitle(String userName) {
        return jobRepository.findByDataNameUser(userName);
    }

    public JobModel createJob(JobModel jobModel) {
        return jobRepository.save(jobModel);
    }

}
