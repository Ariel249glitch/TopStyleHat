package com.TopStyleHat.Controller;

import com.TopStyleHat.DTO.BoletaDTO;
import com.TopStyleHat.Model.Boleta;
import com.TopStyleHat.Service.BoletaService;
import jakarta.validation.Valid;
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
import java.util.List;

@RestController
@RequestMapping("/api/v1/boletas")
public class BoletaController {

    @Autowired
    private BoletaService boletaService;

    // GET /api/v1/boletas - Listar todas
    @GetMapping
    public ResponseEntity<?> listarTodas() {
        List<BoletaDTO> boletas = boletaService.obtenerTodas();
        if (boletas.isEmpty()) {
            return new ResponseEntity<>("No hay boletas registradas", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(boletas, HttpStatus.OK);
    }

    // GET /api/v1/boletas/{id} - Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        try {
            BoletaDTO boleta = boletaService.buscarPorId(id);
            return new ResponseEntity<>(boleta, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Boleta no encontrada", HttpStatus.NOT_FOUND);
        }
    }

    // POST /api/v1/boletas - Crear boleta
    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody Boleta boleta) {
        try {
            Boleta nuevaBoleta = boletaService.guardar(boleta);
            return new ResponseEntity<>(nuevaBoleta, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al guardar la boleta", HttpStatus.BAD_REQUEST);
        }
    }

    // PUT /api/v1/boletas/{id} - Actualizar boleta
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id, @RequestBody Boleta boleta) {
        try {
            Boleta boletaActualizada = boletaService.actualizar(id, boleta);
            return new ResponseEntity<>(boletaActualizada, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Boleta no encontrada", HttpStatus.NOT_FOUND);
        }
    }

    // DELETE /api/v1/boletas/{id} - Eliminar boleta
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        String resultado = boletaService.eliminar(id);
        if (resultado.contains("exitosamente")) {
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(resultado, HttpStatus.NOT_FOUND);
        }
    }
}
