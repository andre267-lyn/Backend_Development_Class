package com.ideaco.andre.repository;

import com.ideaco.andre.model.JobModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface JobRepository extends JpaRepository<JobModel, Long> {
    @Query(value = "SELECT * FROM tab_andre", nativeQuery = true)
    List<JobModel> findByDataAllUser();

    @Query(value = "SELECT * FROM tab_andre WHERE user_name = :userName", nativeQuery = true)
    List<JobModel> findByDataNameUser(@Param("userName") String userName);

    Optional<JobModel> findByUserEmail(String userEmail);
}
