package com.caparicio.springcloud.msvc.cursos.msvc.cursos.services;

import com.caparicio.springcloud.msvc.cursos.msvc.cursos.models.Usuario;
import com.caparicio.springcloud.msvc.cursos.msvc.cursos.models.entity.Curso;
import com.caparicio.springcloud.msvc.cursos.msvc.cursos.repositories.CursoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CursoServiceImpl implements CursoService {
  private final CursoRepository cursoRepository;

  @Override
  @Transactional(readOnly = true)
  public List<Curso> listar() {
    return (List<Curso>) cursoRepository.findAll();
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<Curso> porId(Long id) {
    return cursoRepository.findById(id);
  }

  @Override
  @Transactional
  public Curso guardar(Curso curso) {
    return cursoRepository.save(curso);
  }

  @Override
  @Transactional
  public void eliminar(Long id) {
    cursoRepository.deleteById(id);
  }

  @Override
  public Optional<Usuario> asignarUsuario(Usuario usuario, Long cursoId) {
    return Optional.empty();
  }

  @Override
  public Optional<Usuario> crearUsuario(Usuario usuario, Long id) {
    return Optional.empty();
  }

  @Override
  public Optional<Usuario> eliminarUsuario(Usuario usuario, Long cursoId) {
    return Optional.empty();
  }
}
