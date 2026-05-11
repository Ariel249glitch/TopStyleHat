package com.TopStyleHat.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.TopStyleHat.DTO.ClienteDTO;
import com.TopStyleHat.Model.Cliente;
import com.TopStyleHat.Repository.ClienteRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    // Mostrar todos los clientes
    public List<ClienteDTO> obtenerTodos() {
        List<ClienteDTO> clientesDTO = new ArrayList<>();
        for (Cliente cliente : clienteRepository.findAll()) {
            clientesDTO.add(convertirADTO(cliente));
        }
        return clientesDTO;
    }

    // Buscar por ID
    public ClienteDTO buscarPorId(Integer id) {
        Cliente cliente = clienteRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        return convertirADTO(cliente);
    }

    // Guardar cliente
    public Cliente guardar(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    // Actualizar cliente
    public Cliente actualizar(Integer id, Cliente clienteActualizado) {
        Cliente clienteExistente = clienteRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Cliente no existe"));
        
        if (clienteActualizado.getNombre() != null) {
            clienteExistente.setNombre(clienteActualizado.getNombre());
        }
        if (clienteActualizado.getComuna() != null) {
            clienteExistente.setComuna(clienteActualizado.getComuna());
        }
        if (clienteActualizado.getRegion() != null) {
            clienteExistente.setRegion(clienteActualizado.getRegion());
        }
        if (clienteActualizado.getDireccion() != null) {
            clienteExistente.setDireccion(clienteActualizado.getDireccion());
        }        
        return clienteRepository.save(clienteExistente);
    }

    // Eliminar cliente
    public String eliminar(Integer id) {
        try {
            Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no existe"));
            clienteRepository.delete(cliente);
            return "El cliente '" + cliente.getNombre() + "' ha sido eliminado exitosamente";
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    // Convertir Entity a DTO 
    private ClienteDTO convertirADTO(Cliente cliente) {
        ClienteDTO dto = new ClienteDTO();
        dto.setId(cliente.getId());
        dto.setNombre(cliente.getNombre());
        dto.setComuna(cliente.getComuna());
        dto.setRegion(cliente.getRegion());
        dto.setDireccion(cliente.getDireccion());
        
        if (cliente.getBoletas() != null) {
            List<String> nombresBoletas = new ArrayList<>();
            for (var boleta : cliente.getBoletas()) {
                nombresBoletas.add("Boleta #" + boleta.getId());
            }
            dto.setNombresBoletas(nombresBoletas);
        } else {
            dto.setNombresBoletas(new ArrayList<>());
        }        
        return dto;
    }
}
