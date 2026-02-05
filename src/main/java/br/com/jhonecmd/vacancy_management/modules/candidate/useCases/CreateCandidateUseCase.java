package br.com.jhonecmd.vacancy_management.modules.candidate.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.jhonecmd.vacancy_management.exceptions.ResourceAlreadyExists;
import br.com.jhonecmd.vacancy_management.modules.candidate.entities.CandidateEntity;
import br.com.jhonecmd.vacancy_management.modules.candidate.repositories.CandidateRepository;
import jakarta.validation.Valid;

@Service
public class CreateCandidateUseCase {

    @Autowired
    private CandidateRepository candidateRepository;

    public void execute(@Valid @RequestBody CandidateEntity candidateEntity) {

        this.candidateRepository.findByEmail(candidateEntity.getEmail()).ifPresent((candidate) -> {
            throw new ResourceAlreadyExists();
        });

        this.candidateRepository.save(candidateEntity);
        return;
    }
}
