package br.com.jhonecmd.vacancy_management.exceptions;

public class InvalidCredentials extends RuntimeException {
    public InvalidCredentials() {
        super("Invalid Credentials!");
    }
}
