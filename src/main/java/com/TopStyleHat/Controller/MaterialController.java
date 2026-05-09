package com.TopStyleHat.Controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.TopStyleHat.DTO.MaterialDTO;
import com.TopStyleHat.Service.MaterialService;

@RestController
@RequestMapping("/api/v1/Materiales")

public class MaterialController {

    @Autowired
    MaterialService materialService;


    //Mostrar todas las Marcas
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




}
