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

import com.TopStyleHat.DTO.ClienteDTO;
import com.TopStyleHat.Model.Cliente;
import com.TopStyleHat.Service.ClienteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    // GET /api/v1/clientes - Listar todos
    @GetMapping
    public ResponseEntity<?> listarTodos() {
        List<ClienteDTO> clientes = clienteService.obtenerTodos();
        if (clientes.isEmpty()) {
            return new ResponseEntity<>("No hay clientes registrados", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }

    // GET /api/v1/clientes/{id} - Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        try {
            ClienteDTO cliente = clienteService.buscarPorId(id);
            return new ResponseEntity<>(cliente, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Cliente no encontrado", HttpStatus.NOT_FOUND);
        }
    }

    // POST /api/v1/clientes - Crear cliente
    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody Cliente cliente) {
        try {
            Cliente nuevoCliente = clienteService.guardar(cliente);
            return new ResponseEntity<>(nuevoCliente, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al guardar el cliente", HttpStatus.BAD_REQUEST);
        }
    }

    // PUT /api/v1/clientes/{id} - Actualizar cliente
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id, @RequestBody Cliente cliente) {
        try {
            Cliente clienteActualizado = clienteService.actualizar(id, cliente);
            return new ResponseEntity<>(clienteActualizado, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("Cliente no encontrado", HttpStatus.NOT_FOUND);
        }
    }

    // DELETE /api/v1/clientes/{id} - Eliminar cliente
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        String resultado = clienteService.eliminar(id);
        if (resultado.contains("exitosamente")) {
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(resultado, HttpStatus.NOT_FOUND);
        }
    }
}
