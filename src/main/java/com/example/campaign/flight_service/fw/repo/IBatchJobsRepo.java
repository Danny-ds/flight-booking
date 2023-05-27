package com.example.campaign.flight_service.fw.repo;

import com.example.campaign.flight_service.fw.entity.BatchJobEntity;
import com.example.campaign.flight_service.fw.entity.JobEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author cb-dhana
 */
public interface IBatchJobsRepo extends JpaRepository<BatchJobEntity, Long> {

    List<BatchJobEntity> findByJobEntity(JobEntity jobEntity);
}
