package br.com.jhonecmd.vacancy_management.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor()
public class ErrorMessageDTO {
    private String error;
    private String filed;
}
