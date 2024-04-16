package com.example.demo.repository;

import com.example.demo.model.TipoId;
import com.example.demo.model.TipoPersona;
import com.example.demo.model.TipoUsuario;
import com.example.demo.model.Usuario;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query(value = "SELECT * FROM usuarios",nativeQuery = true)
    List<Usuario> findAll();

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO usuarios (login,contrasena,nombre,nacionalidad,direccion_Fisica,direccion_Electronica, telefono, ciudad, departamento, codigo_Postal, tipo_Id, tipo_Usuario, tipo_Persona)  VALUES(:login,:contrasena,:nombre,:nacionalidad,:direccionFisica,:direccionElectronica,:telefono,:ciudad,:departamento,:codigoPostal, :tipoId, :tipoUsuario, :tipoPersona)",
    nativeQuery = true)
    void insertUsuario(@Param("login") String login, @Param("contrasena") String contrasena,
                       @Param("nombre") String nombre, @Param("nacionalidad") String nacionlidad,
                       @Param("direccionFisica") String direccionFisica, @Param("direccionElectronica") String direccionElectronica,
                       @Param("telefono") String telefono, @Param("ciudad") String ciudad,
                       @Param("departamento") String departamento, @Param("codigoPostal") String codigoPostal,
                       @Param("tipoId") String tipoId, @Param("tipoUsuario") String tipoUsuario, @Param("tipoPersona") String tipoPersona);

    default void insertUsuario(@Param("login") String login, @Param("contrasena") String contrasena,
                               @Param("nombre") String nombre, @Param("nacionalidad") String nacionalidad,
                               @Param("direccionFisica") String direccionFisica, @Param("direccionElectronica") String direccionElectronica,
                               @Param("telefono") String telefono, @Param("ciudad") String ciudad,
                               @Param("departamento") String departamento, @Param("codigoPostal") String codigoPostal,
                               TipoId tipoId, TipoUsuario tipoUsuario, TipoPersona tipoPersona) {
        insertUsuario(login, contrasena, nombre, nacionalidad, direccionFisica, direccionElectronica,
                telefono, ciudad, departamento, codigoPostal, tipoId.toString(), tipoUsuario.toString(), tipoPersona.toString());
    }
    List<Usuario> findByNombre(String nombre);

    // Find users by name containing and user type
    List<Usuario> findByNombreContainingAndTipoUsuario(String nombre, TipoUsuario tipoUsuario);

    // Find users by name containing
    List<Usuario> findByNombreContaining(String nombre);

    // Find users by user type
    List<Usuario> findByTipoUsuario(TipoUsuario tipoUsuario);
}
