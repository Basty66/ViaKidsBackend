package com.viakids.repository;

import com.viakids.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {
    Optional<Student> findByRut(String rut);
    List<Student> findByBusId(String busId);
    List<Student> findByRuta(String ruta);
}
