package com.caparicio.springcloud.msvc.usuarios.controllers;

import com.caparicio.springcloud.msvc.usuarios.models.entity.Usuario;
import com.caparicio.springcloud.msvc.usuarios.services.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.ResponseEntity.*;
import static org.springframework.http.ResponseEntity.notFound;

@RestController
@AllArgsConstructor
public class UsuarioController {
  private final UsuarioService usuarioService;

  @GetMapping
  public List<Usuario> listar() {
    return usuarioService.listar();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Usuario> detalle(@PathVariable Long id) {
    Optional<Usuario> usuarioOptional = usuarioService.porId(id);
    return usuarioOptional.map(ResponseEntity::ok).orElseGet(() -> notFound().build());
  }

  @PostMapping
  public ResponseEntity<Usuario> crear(@RequestBody Usuario usuario) {
    return status(CREATED).body(usuarioService.guardar(usuario));
  }

  @PutMapping("/{id}")
  public ResponseEntity<Usuario> editar(@RequestBody Usuario usuario, @PathVariable Long id) {
    Optional<Usuario> usuarioOpt = usuarioService.porId(id);
    if (usuarioOpt.isPresent()) {
      Usuario usuarioDb = usuarioOpt.get();
      usuarioDb.setNombre(usuario.getNombre());
      usuarioDb.setEmail(usuario.getEmail());
      usuarioDb.setPassword(usuario.getPassword());

      return ResponseEntity.ok(usuarioService.guardar(usuarioDb));
    }
    return ResponseEntity.notFound().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> eliminar(@PathVariable Long id) {
    Optional<Usuario> usuario = usuarioService.porId(id);
    if (usuario.isPresent()) {
      usuarioService.eliminar(id);

      return ok().build();
    }
    return notFound().build();
  }
}
