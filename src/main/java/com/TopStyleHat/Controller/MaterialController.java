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
import com.TopStyleHat.DTO.MaterialDTO;

import com.TopStyleHat.Model.Material;
import com.TopStyleHat.Service.MaterialService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/Material")

public class MaterialController {

    @Autowired
    MaterialService materialService;


    //Mostrar todos los materiales
    @GetMapping
    public ResponseEntity<?> TodosLosMateriales(){
        List <MaterialDTO> materiales = materialService.mostrarTodos();
        if (!materiales.isEmpty()) {
            return new ResponseEntity<>("No hay Materiales", HttpStatus.OK);
        }
        return new ResponseEntity<>("No hay Materiales", HttpStatus.NO_CONTENT);
    }

    //Buscar por id
    @GetMapping("/{id}")
    public ResponseEntity<?> BuscarPorId(@PathVariable Integer id){
        try {
            MaterialDTO material = materialService.buscarPorId(id);
            return new ResponseEntity<>(material, HttpStatus.ACCEPTED); 
        } catch (RuntimeException e) {
            return new ResponseEntity<>("No se encontro la Marca", HttpStatus.NOT_FOUND);
        }
    }

    //Agregar
    @PostMapping
    public ResponseEntity<?> agregarMaterial(@Valid @RequestBody Material material){
        try {
            return new ResponseEntity<>(materialService.guardarMaterial(material), HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>("No se guardo el material", HttpStatus.BAD_REQUEST);
        }
    }

    //Actualizar
    @PutMapping("/id")
    public ResponseEntity<Material> actualizarStuff(@PathVariable Integer id, @RequestBody Material stuff){
        try {
            Material newStuff = materialService.actualizarMaterial(id, stuff);
            return new ResponseEntity<>(newStuff, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Eliminar
    @DeleteMapping
    public ResponseEntity<String> eliminarStuff(@PathVariable Integer id){
        String resultado = materialService.eliminarMaterial(id);

        if (resultado.contains("Eliminado")){
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(resultado, HttpStatus.NOT_FOUND);
        }
    }






}
