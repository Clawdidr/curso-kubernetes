package com.caparicio.springcloud.msvc.cursos.msvc.cursos.controllers;

import com.caparicio.springcloud.msvc.cursos.msvc.cursos.entity.Curso;
import com.caparicio.springcloud.msvc.cursos.msvc.cursos.services.CursoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.ResponseEntity.*;

@RestController
@AllArgsConstructor
public class CursoController {
  final private CursoService cursoService;

  @GetMapping
  public ResponseEntity<List<Curso>> listar() {
    return ok(cursoService.listar());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Curso> detalle(@PathVariable Long id) {
    Optional<Curso> cursoOpt = cursoService.porId(id);
    return cursoOpt.map(ResponseEntity::ok).orElseGet(() -> notFound().build());
  }

  @PostMapping("/")
  public ResponseEntity<Curso> crear(@RequestBody Curso curso) {
    Curso cursoDb = cursoService.guardar(curso);

    return status(CREATED).body(cursoDb);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Curso> editar(@RequestBody Curso curso, @PathVariable Long id) {
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
}
