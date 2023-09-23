package com.caparicio.springcloud.msvc.usuarios.services;

import com.caparicio.springcloud.msvc.usuarios.client.CursoClienteRest;
import com.caparicio.springcloud.msvc.usuarios.models.entity.Usuario;
import com.caparicio.springcloud.msvc.usuarios.repositories.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {
  private final UsuarioRepository usuarioRepository;
  private final CursoClienteRest cursoClienteRest;

  @Override
  @Transactional(readOnly = true)
  public List<Usuario> listar() {
    return (List<Usuario>) usuarioRepository.findAll();
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<Usuario> porId(Long id) {
    return usuarioRepository.findById(id);
  }

  @Override
  @Transactional
  public Usuario guardar(Usuario usuario) {
    return usuarioRepository.save(usuario);
  }

  @Override
  @Transactional
  public void eliminar(Long id) {
    usuarioRepository.deleteById(id);
    cursoClienteRest.eliminarCursoUsuarioPorId(id);
  }

  @Override
  @Transactional(readOnly = true)
  public List<Usuario> listarPorIds(Iterable<Long> ids) {
    return (List<Usuario>) usuarioRepository.findAllById(ids);
  }

  @Override
  public Optional<Usuario> porEmail(String email) {
    return usuarioRepository.porEmail(email);
  }

  @Override
  public boolean existePorEmail(String email) {
    return usuarioRepository.existsByEmail(email);
  }
}
