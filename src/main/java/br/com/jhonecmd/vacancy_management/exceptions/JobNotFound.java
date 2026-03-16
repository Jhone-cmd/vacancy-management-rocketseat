package br.com.jhonecmd.vacancy_management.exceptions;

public class JobNotFound extends RuntimeException {
    public JobNotFound() {
        super("Job not found!");
    }
}
