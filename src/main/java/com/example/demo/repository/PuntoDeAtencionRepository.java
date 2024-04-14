package com.example.demo.repository;

import com.example.demo.model.PuntoDeAtencion;
import com.example.demo.model.TipoAtencion;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface PuntoDeAtencionRepository extends JpaRepository<PuntoDeAtencion, Integer> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO puntos_de_atencion(tipo_atencion,ubicacion,oficina_id) VALUES (:tipo_atencion, :ubicacion, :oficina_id)",
    nativeQuery = true)
    void insertPunto(@Param("tipo_atencion") String tipo_atencion,
                     @Param("ubicacion") String ubicacion,
                     @Param("oficina_id") long oficina_id);

    default void insertPunto(@Param("tipo_atencion") TipoAtencion tipo_atencion,
                     @Param("ubicacion") String ubicacion,
                     @Param("oficina_id") long oficina){
        insertPunto(tipo_atencion.toString(),ubicacion,oficina);
    }

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM puntos_de_atencion p WHERE p.id = :id", nativeQuery = true)
    void deletePunto(@Param("id") long id);




}
