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

import com.TopStyleHat.DTO.GorroDTO;
import com.TopStyleHat.Model.Gorro;
import com.TopStyleHat.Service.GorroService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/gorros")
public class GorroController {

    @Autowired
    private GorroService gorroService;

    @GetMapping
    public ResponseEntity<?> TodosLosGorros(){
        List<GorroDTO> gorros = gorroService.obtenerTodos();
        if (!gorros.isEmpty()) {
            return new ResponseEntity<>(gorros, HttpStatus.OK);
        }
        return new ResponseEntity<>("No hay Gorros", HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> BuscarPorId(@PathVariable Integer id){
        try {
            GorroDTO gorro = gorroService.buscarPorId(id);
            return new ResponseEntity<>(gorro, HttpStatus.ACCEPTED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("No se encontro el gorro", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> agregarGorro(@Valid @RequestBody Gorro gorro){
        try {
            return new ResponseEntity<>(gorroService.guardarGorro(gorro), HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("No se guardo el gorro", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/id")
    public ResponseEntity<Gorro> actualizarGorro (@PathVariable Integer id, @RequestBody Gorro hat){
        try {
            Gorro newHat = gorroService.ActualizarGorro(id, hat);
            return new ResponseEntity<>(newHat, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/id")
    public ResponseEntity<String> eliminarGorro(@PathVariable Integer id){
        String resultado = gorroService.Eliminar(id);

        if (resultado.contains("eliminado")) {
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(resultado, HttpStatus.NOT_FOUND);
        }
    }




}
