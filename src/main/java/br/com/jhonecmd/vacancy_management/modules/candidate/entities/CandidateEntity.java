package br.com.jhonecmd.vacancy_management.modules.candidate.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
@Entity(name = "candidates")
public class CandidateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Email(message = "The email field is invalid.")
    @Column(length = 100, unique = true, nullable = false)
    private String email;

    @Length(min = 10, max = 100, message = "The password length must be between 10 and 100 characters.")
    private String password;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String curriculum;

    @CreationTimestamp
    private LocalDateTime createAt;

}
