package br.com.jhonecmd.vacancy_management.modules.candidate.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.jhonecmd.vacancy_management.exceptions.ResourceAlreadyExists;
import br.com.jhonecmd.vacancy_management.modules.candidate.entities.CandidateEntity;
import br.com.jhonecmd.vacancy_management.modules.candidate.repositories.CandidateRepository;

@Service
public class CreateCandidateUseCase {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void execute(CandidateEntity candidateEntity) {

        this.candidateRepository.findByEmail(candidateEntity.getEmail()).ifPresent((candidate) -> {
            throw new ResourceAlreadyExists();
        });

        var password = this.passwordEncoder.encode(candidateEntity.getPassword());

        candidateEntity.setPassword(password);

        this.candidateRepository.save(candidateEntity);
        return;
    }
}
