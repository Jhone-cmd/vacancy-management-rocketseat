package br.com.jhonecmd.vacancy_management.exceptions;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor()
public class ErrorMessageDTO {

    @Schema(example = "Name is required.")
    private String error;
    @Schema(example = "name")
    private String filed;
}
