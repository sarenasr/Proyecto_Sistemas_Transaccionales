package com.example.demo.repository;

import com.example.demo.model.OperacionPrestamo;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface OperacionPrestamoRepository extends JpaRepository<OperacionPrestamo, Long> {
}
