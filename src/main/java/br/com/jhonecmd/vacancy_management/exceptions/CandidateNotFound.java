package br.com.jhonecmd.vacancy_management.exceptions;

public class CandidateNotFound extends RuntimeException {
    public CandidateNotFound() {
        super("Candidate not found!");
    }
}
