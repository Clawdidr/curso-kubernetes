package com.caparicio.springcloud.msvc.usuarios.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@Entity
@Table(name="usuarios")
public class Usuario {
  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;
  @NotEmpty
  private String nombre;
  @Column(unique = true)
  private String email;
  private String password;
}
