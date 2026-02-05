package br.com.jhonecmd.vacancy_management.exceptions;

public class ResourceAlreadyExists extends RuntimeException {
    public ResourceAlreadyExists() {
        super("Resource already exists!");
    }
}
