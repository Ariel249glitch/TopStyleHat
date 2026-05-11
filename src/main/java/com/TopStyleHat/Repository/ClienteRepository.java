package com.TopStyleHat.Repository;

import com.TopStyleHat.Model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    List<Cliente> findByComuna(String comuna);

    @Query("SELECT c FROM Cliente c WHERE c.region = :region")
    List<Cliente> buscarPorRegion(@Param("region") String region);
}
