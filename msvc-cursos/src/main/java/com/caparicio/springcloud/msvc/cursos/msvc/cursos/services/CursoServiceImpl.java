package com.caparicio.springcloud.msvc.cursos.msvc.cursos.services;

import com.caparicio.springcloud.msvc.cursos.msvc.cursos.clients.UsuarioClientRest;
import com.caparicio.springcloud.msvc.cursos.msvc.cursos.models.Usuario;
import com.caparicio.springcloud.msvc.cursos.msvc.cursos.models.entity.Curso;
import com.caparicio.springcloud.msvc.cursos.msvc.cursos.models.entity.CursoUsuario;
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
  private final UsuarioClientRest clientRest;

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
  @Transactional(readOnly = true)
  public Optional<Curso> porIdConUsuarios(Long id) {
    Optional<Curso> optCurso = cursoRepository.findById(id);
    if (optCurso.isPresent()) {
      Curso curso = optCurso.get();
      if (!curso.getCursoUsuarios().isEmpty()) {
        List<Long> ids = curso.getCursoUsuarios().stream().map(CursoUsuario::getUsuarioId).toList();
        curso.setUsuarios(clientRest.obtenerAlumnosPorCurso(ids));
      }
      return Optional.of(curso);
    }
    return Optional.empty();
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
  @Transactional
  public void eliminarCursoUsuarioPorId(Long id) {
    cursoRepository.eliminarCursoUsuarioPorId(id);
  }

  @Override
  @Transactional
  public Optional<Usuario> asignarUsuario(Usuario usuario, Long cursoId) {
    Optional<Curso> optCurso = cursoRepository.findById(cursoId);

    if (optCurso.isPresent()) {
      Usuario usuarioMsvc = clientRest.detalle(usuario.getId());

      CursoUsuario cursoUsuario = new CursoUsuario();
      cursoUsuario.setUsuarioId(usuarioMsvc.getId());

      Curso curso = optCurso.get();
      curso.addCursoUsuario(cursoUsuario);
      cursoRepository.save(curso);

      return Optional.of(usuarioMsvc);
    }

    return Optional.empty();
  }

  @Override
  @Transactional
  public Optional<Usuario> crearUsuario(Usuario usuario, Long cursoId) {
    Optional<Curso> optCurso = cursoRepository.findById(cursoId);

    if (optCurso.isPresent()) {
      Usuario usuarioNuevoMsvc = clientRest.crear(usuario);

      CursoUsuario cursoUsuario = new CursoUsuario();
      cursoUsuario.setUsuarioId(usuarioNuevoMsvc.getId());

      Curso curso = optCurso.get();
      curso.addCursoUsuario(cursoUsuario);
      cursoRepository.save(curso);

      return Optional.of(usuarioNuevoMsvc);
    }

    return Optional.empty();
  }

  @Override
  @Transactional
  public Optional<Usuario> eliminarUsuario(Usuario usuario, Long cursoId) {
    Optional<Curso> optCurso = cursoRepository.findById(cursoId);

    if (optCurso.isPresent()) {
      Usuario usuarioMsvc = clientRest.detalle(usuario.getId());

      CursoUsuario cursoUsuario = new CursoUsuario();
      cursoUsuario.setUsuarioId(usuarioMsvc.getId());

      Curso curso = optCurso.get();
      curso.removeCursoUsuario(cursoUsuario);
      cursoRepository.save(curso);

      return Optional.of(usuarioMsvc);
    }

    return Optional.empty();
  }
}
