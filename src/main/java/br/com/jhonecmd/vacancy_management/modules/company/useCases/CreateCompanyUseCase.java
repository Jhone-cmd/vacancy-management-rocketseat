package br.com.jhonecmd.vacancy_management.modules.company.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jhonecmd.vacancy_management.exceptions.ResourceAlreadyExists;
import br.com.jhonecmd.vacancy_management.modules.company.entities.CompanyEntity;
import br.com.jhonecmd.vacancy_management.modules.company.repositories.CompanyRepository;

@Service
public class CreateCompanyUseCase {

    @Autowired
    private CompanyRepository companyRepository;

    public void execute(CompanyEntity companyEntity) {

        this.companyRepository.findByEmail(companyEntity.getEmail()).ifPresent((company) -> {
            throw new ResourceAlreadyExists();
        });

        this.companyRepository.save(companyEntity);
        return;
    }
}
