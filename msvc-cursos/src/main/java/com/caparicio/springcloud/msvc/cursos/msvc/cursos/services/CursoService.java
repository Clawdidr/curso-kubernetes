package com.caparicio.springcloud.msvc.cursos.msvc.cursos.services;

import com.caparicio.springcloud.msvc.cursos.msvc.cursos.models.Usuario;
import com.caparicio.springcloud.msvc.cursos.msvc.cursos.models.entity.Curso;

import java.util.List;
import java.util.Optional;

public interface CursoService {
  List<Curso> listar();
  Optional<Curso> porId(Long id);
  Optional<Curso> porIdConUsuarios(Long id);
  Curso guardar(Curso curso);
  void eliminar(Long id);
  Optional<Usuario> asignarUsuario(Usuario usuario, Long cursoId);
  Optional<Usuario> crearUsuario(Usuario usuario, Long cursoId);
  Optional<Usuario> eliminarUsuario(Usuario usuario, Long cursoId);
}
