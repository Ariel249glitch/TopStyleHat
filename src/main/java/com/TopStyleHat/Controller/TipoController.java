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

import com.TopStyleHat.DTO.TipoDTO;
import com.TopStyleHat.Model.Tipo;
import com.TopStyleHat.Service.TipoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/Tipo")
public class TipoController {

    @Autowired
    TipoService tipoService;

    //Mostrar todos los tipos 
    @GetMapping
    public ResponseEntity<?> TodosLosTipos(){
        List<TipoDTO> tipo = tipoService.MostrarTodas();
        if (!tipo.isEmpty()) {
            return new ResponseEntity<>("No hay tipo", HttpStatus.OK);
        }
        return new ResponseEntity<>("No hay tipo", HttpStatus.NO_CONTENT);
    }

    //Agregar
    @PostMapping
    public ResponseEntity<?> agregarTipo(@Valid @RequestBody Tipo tipo){
        try {
            return new ResponseEntity<>(tipoService.guardarTipo(tipo), HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("No se guardo el tipo", HttpStatus.BAD_REQUEST);
        }
    }

    //Actualizar
    @PutMapping("/id")
    public ResponseEntity<Tipo> actualizarTipe(@PathVariable Integer id, @RequestBody Tipo Tipe){
        try {
            Tipo newTipe = tipoService.actualizarTipo(id, Tipe);
            return new ResponseEntity<>(newTipe, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Eliminar
    @DeleteMapping
    public ResponseEntity<String> eliminarTipe(@PathVariable Integer id){
        String resultado = tipoService.EliminarTipo(id);

        if (resultado.contains("Eliminado")){
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(resultado, HttpStatus.NOT_FOUND);
        }

    }
}

