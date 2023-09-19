package com.caparicio.springcloud.msvc.usuarios.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@Entity
@Table(name = "usuarios")
public class Usuario {
  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;
  @NotBlank
  private String nombre;
  @NotBlank
  @Email
  @Column(unique = true)
  private String email;
  @NotBlank
  private String password;
}
