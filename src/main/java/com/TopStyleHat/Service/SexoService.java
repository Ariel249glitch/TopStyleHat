package com.TopStyleHat.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.TopStyleHat.DTO.SexoDTO;
import com.TopStyleHat.Model.Sexo;
import com.TopStyleHat.Repository.SexoRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class SexoService {

    @Autowired
    SexoRepository sexoRepository;

    //Mostrar todos
    public List<SexoDTO> mostrarTodos(){
        List<SexoDTO> sexos = new ArrayList<>();
        for (Sexo sexo : sexoRepository.findAll()) {
            sexos.add(convertirADTO(sexo));  
        }
        return sexos;
    }

    //Convertir DTO
    public SexoDTO convertirADTO(Sexo sexo){
        SexoDTO sexoDTO = new SexoDTO();
        sexoDTO.setId(sexo.getId());
        sexoDTO.setNombre(sexo.getNombre());
        return sexoDTO;
    }

    //Buscar por Id
    public SexoDTO buscarPorId(Integer id){
        Sexo sexo = sexoRepository.findById(id).orElseThrow(() -> new RuntimeException( "no encontrado"));
        return convertirADTO(sexo);
    }

    //Guardar 
    public SexoDTO guardarSexo(Sexo nuevoSexo){
        Sexo SexoGuardado = sexoRepository.save(nuevoSexo);
        return convertirADTO(SexoGuardado);
    }

    //Actualizar
    public Sexo actualizarSexo(Integer id, Sexo sexo){
        Sexo genero = sexoRepository.findById(id).orElseThrow(() ->  new RuntimeException("no existe"));
        if (sexo.getNombre() != null) {
            genero.setNombre(sexo.getNombre());
        }
        return sexoRepository.save(genero);
    }

    //Eliminar 
    public String eliminarSexo(Integer id){
        try {
            Sexo sexo = sexoRepository.findById(id).orElseThrow(() ->  new RuntimeException(" no se puede eliminar el sexo con id" + id + "No existe"));
            sexoRepository.delete(sexo);
            return "el sexo '" + sexo.getNombre() + "' a sido eliminado";
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }


}
