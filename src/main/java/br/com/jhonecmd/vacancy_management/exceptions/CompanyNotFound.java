package br.com.jhonecmd.vacancy_management.exceptions;

public class CompanyNotFound extends RuntimeException {
    public CompanyNotFound() {
        super("Company not found!");
    }
}