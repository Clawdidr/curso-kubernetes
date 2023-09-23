package com.caparicio.springcloud.msvc.cursos.models.entity;

import com.caparicio.springcloud.msvc.cursos.models.Usuario;
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
    usuarios = new ArrayList<>();
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @NotBlank
  private String nombre;
  @OneToMany(cascade = ALL, orphanRemoval = true)
  @JoinColumn(name = "curso_id")
  private List<CursoUsuario> cursoUsuarios;
  @Transient
  private List<Usuario> usuarios;

  public void addCursoUsuario(CursoUsuario cursoUsuario) {
    cursoUsuarios.add(cursoUsuario);
  }

  public void removeCursoUsuario(CursoUsuario cursoUsuario) {
    cursoUsuarios.remove(cursoUsuario);
  }
}
