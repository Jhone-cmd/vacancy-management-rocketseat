package br.com.jhonecmd.vacancy_management.modules.candidate.repositories;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import br.com.jhonecmd.vacancy_management.modules.candidate.entities.CandidateEntity;

public interface CandidateRepository extends JpaRepository<CandidateEntity, UUID> {

}
