package com.caparicio.springcloud.msvc.cursos.msvc.cursos.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;

@Entity
@Table(name = "cursos")
@Getter
@Setter
public class Curso {
  public Curso() {
    cursoUsuarios = new ArrayList<>();
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @NotBlank
  private String nombre;
  @OneToMany(cascade = ALL, orphanRemoval = true)
  private List<CursoUsuario> cursoUsuarios;

  public void addCursoUsuario(CursoUsuario cursoUsuario) {
    cursoUsuarios.add(cursoUsuario);
  }

  public void removeCursoUsuario(CursoUsuario cursoUsuario) {
    cursoUsuarios.remove(cursoUsuario);
  }
}
