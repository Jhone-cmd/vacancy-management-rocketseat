package br.com.jhonecmd.vacancy_management.modules.company.job.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.jhonecmd.vacancy_management.modules.company.job.entities.JobEntity;

public interface JobRepository extends JpaRepository<JobEntity, UUID> {
    Optional<JobEntity> findByName(String name);
}
