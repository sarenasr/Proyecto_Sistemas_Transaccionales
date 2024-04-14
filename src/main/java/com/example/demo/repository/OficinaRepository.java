package com.example.demo.repository;

import com.example.demo.model.Oficina;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface OficinaRepository extends JpaRepository<Oficina, Long> {
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO oficinas (direccion,nombre,numero_puntos_atencion,gerente_id) VALUES (:direccion,:nombre,:numero_puntos_atencion,:gerente_id)",
    nativeQuery = true)
    void insertOficina(@Param("direccion") String direccion,@Param("nombre")String nombre,
                       @Param("numero_puntos_atencion") int numero_puntos_atencion,
                       @Param("gerente_id") Long gerente_id);

}
