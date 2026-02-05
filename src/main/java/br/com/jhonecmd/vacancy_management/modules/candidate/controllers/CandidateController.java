package br.com.jhonecmd.vacancy_management.modules.candidate.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.jhonecmd.vacancy_management.modules.candidate.entities.CandidateEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/candidates")
public class CandidateController {

    @PostMapping("")
    public void postMethodName(@RequestBody CandidateEntity candidateEntity) {
        System.out.println(candidateEntity.getName());
        return;
    }

}
