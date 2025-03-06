package com.wjcarpi.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wjcarpi.modelo.Mueble;

public interface MuebleRepository extends JpaRepository<Mueble, Long> {
}