package com.ideaco.andre.repository;

import com.ideaco.andre.model.JobModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobModelRepository extends JpaRepository<JobModel, Long> {
    boolean findByUserEmailAndUserPassword(String userEmail, String userPassword);
    JobModel findByUserEmail(String userEmail);
}
