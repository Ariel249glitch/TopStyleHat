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

import com.TopStyleHat.DTO.MetodoEDTO;
import com.TopStyleHat.Model.MetodoE;
import com.TopStyleHat.Service.MetodoEService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/MetodoE")
public class MetodoEController {
    
    @Autowired
    MetodoEService metodoEService;

    //Mostrar todos los tipos 
    @GetMapping
    public ResponseEntity<?> TodosLosMetodoE(){
        List<MetodoEDTO> metodoE = metodoEService.MostrarTodas();
        if (!metodoE.isEmpty()) {
            return new ResponseEntity<>("No hay MetodoE", HttpStatus.OK);
        }
        return new ResponseEntity<>("No hay MetodoE", HttpStatus.NO_CONTENT);
    }

    //Agregar
    @PostMapping
    public ResponseEntity<?> agregarMetodoE(@Valid @RequestBody MetodoE metodoE){
        try {
            return new ResponseEntity<>(metodoEService.guardarMetodoE(metodoE), HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("No se guardo el MetodoE", HttpStatus.BAD_REQUEST);
        }
    }

    //Actualizar
    @PutMapping("/id")
    public ResponseEntity<MetodoE> actualizarMetodoE(@PathVariable Integer id, @RequestBody MetodoE MetodoE){
        try {
            MetodoE newMetodoE = metodoEService.actualizarMetodoE(id, MetodoE);
            return new ResponseEntity<>(newMetodoE, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
        //Eliminar
        @DeleteMapping("/id")
        public ResponseEntity<?> eliminarMetodoE(@PathVariable Integer id){
            try {
                metodoEService.EliminarMetodoE(id);
                return new ResponseEntity<>("MetodoE eliminado", HttpStatus.OK);
            } catch (RuntimeException e) {
                String resultado = "No se encontro el MetodoE";
                return new ResponseEntity<>(resultado, HttpStatus.NOT_FOUND);
            }
        }
}

