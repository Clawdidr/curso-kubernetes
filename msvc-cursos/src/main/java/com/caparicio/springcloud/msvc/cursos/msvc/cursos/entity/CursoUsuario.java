package com.caparicio.springcloud.msvc.cursos.msvc.cursos.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "cursos_usuarios")
@Getter
@Setter
public class CursoUsuario {
  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;
  @Column(name = "usuario_id", unique = true)
  private Long usuarioId;

  @Override
  public final boolean equals(Object o) {
    if (this == o) return true;
    if (o == null) return false;
    if (!(o instanceof CursoUsuario co)) {
      return false;
    }

    return this.usuarioId != null && this.usuarioId.equals(co.usuarioId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, usuarioId);
  }
}
