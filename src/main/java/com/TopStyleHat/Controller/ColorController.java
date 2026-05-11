package com.TopStyleHat.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.TopStyleHat.DTO.ColorDTO;
import com.TopStyleHat.Model.Color;
import com.TopStyleHat.Service.ColorService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/colores")
public class ColorController {

    @Autowired
    private ColorService colorService;

    // GET /api/v1/colores - Listar todos los colores
    @GetMapping
    public ResponseEntity<?> listarTodos() {
        List<ColorDTO> colores = colorService.obtenerTodos();
        if (colores.isEmpty()) {
            return new ResponseEntity<>("No hay colores registrados", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(colores, HttpStatus.OK);
    }

    // GET /api/v1/colores/{id} - Buscar color por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        try {
            ColorDTO color = colorService.buscarPorId(id);
            return new ResponseEntity<>(color, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Color no encontrado", HttpStatus.NOT_FOUND);
        }
    }

    // POST /api/v1/colores - Crear color
    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody Color color) {
        try {
            Color nuevoColor = colorService.guardar(color);
            return new ResponseEntity<>(nuevoColor, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al guardar el color", HttpStatus.BAD_REQUEST);
        }
    }

    // PUT /api/v1/colores/{id} - Actualizar color
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id, @RequestBody Color color) {
        try {
            Color colorActualizado = colorService.actualizar(id, color);
            return new ResponseEntity<>(colorActualizado, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Color no encontrado", HttpStatus.NOT_FOUND);
        }
    }

    // DELETE /api/v1/colores/{id} - Eliminar color
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        String resultado = colorService.eliminar(id);
        if (resultado.contains("exitosamente")) {
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(resultado, HttpStatus.NOT_FOUND);
        }
    }
}
