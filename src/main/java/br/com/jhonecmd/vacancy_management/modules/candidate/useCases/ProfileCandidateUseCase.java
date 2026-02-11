package br.com.jhonecmd.vacancy_management.modules.candidate.useCases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.jhonecmd.vacancy_management.modules.candidate.dto.ProfileCandidateDTO;
import br.com.jhonecmd.vacancy_management.modules.candidate.repositories.CandidateRepository;

@Service
public class ProfileCandidateUseCase {

    @Autowired
    private CandidateRepository candidateRepository;

    public ProfileCandidateDTO execute(UUID candidateId) {
        var candidate = this.candidateRepository.findById(candidateId).orElseThrow(() -> {
            throw new UsernameNotFoundException("User not found!");
        });
        var profileCandidateDTO = ProfileCandidateDTO
                .builder().id(candidateId.toString()).name(candidate.getName()).email(candidate.getEmail())
                .description(candidate.getDescription()).build();
        return profileCandidateDTO;
    }
}
