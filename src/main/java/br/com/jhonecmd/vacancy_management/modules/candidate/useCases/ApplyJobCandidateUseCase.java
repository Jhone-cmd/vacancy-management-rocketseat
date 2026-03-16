package br.com.jhonecmd.vacancy_management.modules.candidate.useCases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jhonecmd.vacancy_management.exceptions.CandidateNotFound;
import br.com.jhonecmd.vacancy_management.exceptions.JobNotFound;
import br.com.jhonecmd.vacancy_management.modules.candidate.entities.ApplyJobEntity;
import br.com.jhonecmd.vacancy_management.modules.candidate.repositories.ApplyJobRepository;
import br.com.jhonecmd.vacancy_management.modules.candidate.repositories.CandidateRepository;
import br.com.jhonecmd.vacancy_management.modules.company.job.repositories.JobRepository;

@Service
public class ApplyJobCandidateUseCase {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private ApplyJobRepository applyJobRepository;

    public ApplyJobEntity execute(UUID candidateId, UUID jobId) {

        this.candidateRepository.findById(candidateId).orElseThrow(() -> new CandidateNotFound());

        this.jobRepository.findById(jobId).orElseThrow(() -> new JobNotFound());

        var applyJob = ApplyJobEntity.builder().candidateId(candidateId).jobId(jobId).build();

        applyJob = this.applyJobRepository.save(applyJob);

        return applyJob;

    }
}
