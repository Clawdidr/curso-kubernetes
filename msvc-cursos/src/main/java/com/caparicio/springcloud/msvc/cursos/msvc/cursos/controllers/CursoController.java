package com.caparicio.springcloud.msvc.cursos.msvc.cursos.controllers;

import com.caparicio.springcloud.msvc.cursos.msvc.cursos.models.Usuario;
import com.caparicio.springcloud.msvc.cursos.msvc.cursos.models.entity.Curso;
import com.caparicio.springcloud.msvc.cursos.msvc.cursos.services.CursoService;
import feign.FeignException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Collections.singletonMap;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.ResponseEntity.*;

@RestController
@AllArgsConstructor
public class CursoController {
  private final CursoService cursoService;

  @GetMapping
  public ResponseEntity<List<Curso>> listar() {
    return ok(cursoService.listar());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Curso> detalle(@PathVariable Long id) {
    Optional<Curso> cursoOpt = cursoService.porIdConUsuarios(id);
    return cursoOpt.map(ResponseEntity::ok).orElseGet(() -> notFound().build());
  }

  @PostMapping("/")
  public ResponseEntity<?> crear(@Valid @RequestBody Curso curso, BindingResult result) {
    if (result.hasErrors()) {
      return validar(result);
    }
    Curso cursoDb = cursoService.guardar(curso);

    return status(CREATED).body(cursoDb);
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> editar(@Valid @RequestBody Curso curso, BindingResult result,
                                  @PathVariable Long id) {
    if (result.hasErrors()) {
      return validar(result);
    }
    Optional<Curso> cursoOpt = cursoService.porId(id);
    if (cursoOpt.isPresent()) {
      Curso cursoDb = cursoOpt.get();
      cursoDb.setNombre(curso.getNombre());

      return ok(cursoService.guardar(cursoDb));
    }

    return notFound().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> eliminar(@PathVariable Long id) {
    Optional<Curso> cursoOpt = cursoService.porId(id);
    if (cursoOpt.isPresent()) {
      cursoService.eliminar(id);

      return ok().build();
    }

    return notFound().build();
  }

  @PutMapping("/asignar-usuario/{cursoId}")
  public ResponseEntity<?> asignarUsuario(@RequestBody Usuario usuario,
                                          @PathVariable Long cursoId) {
    Optional<Usuario> usuarioOpt;
    try {
      usuarioOpt = cursoService.asignarUsuario(usuario, cursoId);
    } catch (FeignException fe) {
      return ResponseEntity.status(NOT_FOUND).body(singletonMap("mensajeError",
          "No existe el usuario por el id o error en la comunicación: " + fe.getMessage()));
    }
    return usuarioOpt.map(ResponseEntity::ok).orElseGet(() -> notFound().build());
  }

  @PostMapping("/crear-usuario/{cursoId}")
  public ResponseEntity<?> crearUsuario(@RequestBody Usuario usuario, @PathVariable Long cursoId) {
    Optional<Usuario> usuarioOpt;
    try {
      usuarioOpt = cursoService.crearUsuario(usuario, cursoId);
    } catch (FeignException fe) {
      return ResponseEntity.status(NOT_FOUND).body(singletonMap("mensajeError",
          "No se pudo crear el usuario o error en la comunicación: " + fe.getMessage()));
    }
    return usuarioOpt.map(ResponseEntity::ok).orElseGet(() -> notFound().build());
  }

  @DeleteMapping("/eliminar-usuario/{cursoId}")
  public ResponseEntity<?> eliminarUsuario(@RequestBody Usuario usuario,
                                           @PathVariable Long cursoId) {
    Optional<Usuario> usuarioOpt;
    try {
      usuarioOpt = cursoService.eliminarUsuario(usuario, cursoId);
    } catch (FeignException fe) {
      return ResponseEntity.status(NOT_FOUND).body(singletonMap("mensajeError",
          "No existe el usuario por el id o error en la comunicación: " + fe.getMessage()));
    }
    return usuarioOpt.map(ResponseEntity::ok).orElseGet(() -> notFound().build());
  }

  @DeleteMapping("/eliminar-usuario/{id}")
  public ResponseEntity<?> eliminarCursoUsuario(@PathVariable Long id) {
    cursoService.eliminarCursoUsuarioPorId(id);

    return noContent().build();
  }

  private static ResponseEntity<Map<String, String>> validar(BindingResult result) {
    Map<String, String> errores = new HashMap<>();
    result.getFieldErrors().forEach(error -> errores.put(error.getField(),
        "El campo " + error.getField() + " " + error.getDefaultMessage()));

    return badRequest().body(errores);
  }
}
