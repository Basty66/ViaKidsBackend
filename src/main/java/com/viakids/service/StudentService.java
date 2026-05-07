package com.viakids.service;

import com.viakids.model.Student;
import com.viakids.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StudentService {
    
    private final Map<String, Student> students = new HashMap<>();
    
    public StudentService() {
        // Datos de ejemplo
        Student s1 = new Student();
        s1.setId("S001");
        s1.setNombre("Mateo García");
        s1.setCurso("4to B");
        s1.setRut("20.123.456-7");
        s1.setApoderado("Carlos García");
        s1.setTelefono("+56912345678");
        s1.setBusId("B001");
        s1.setBusPatente("AB-1234");
        s1.setRuta("Ruta Norte");
        s1.setColegio("Colegio Los Andes");
        s1.setEstado(Student.StudentStatus.EN_ESPERA);
        students.put(s1.getId(), s1);
    }
    
    public List<Student> getAllStudents() {
        return new ArrayList<>(students.values());
    }
    
    public Student getStudentById(String id) {
        Student s = students.get(id);
        if (s == null) throw new RuntimeException("Estudiante no encontrado");
        return s;
    }
    
    public Student createStudent(Student student) {
        if (student.getId() == null) {
            student.setId("S" + System.currentTimeMillis());
        }
        students.put(student.getId(), student);
        return student;
    }
    
    public Student updateStudent(String id, Student studentDetails) {
        Student student = getStudentById(id);
        student.setNombre(studentDetails.getNombre());
        student.setCurso(studentDetails.getCurso());
        student.setRut(studentDetails.getRut());
        student.setApoderado(studentDetails.getApoderado());
        student.setTelefono(studentDetails.getTelefono());
        student.setBusId(studentDetails.getBusId());
        student.setBusPatente(studentDetails.getBusPatente());
        student.setRuta(studentDetails.getRuta());
        student.setColegio(studentDetails.getColegio());
        student.setEstado(studentDetails.getEstado());
        return student;
    }
    
    public void deleteStudent(String id) {
        students.remove(id);
    }
    
    public Student getStudentByRut(String rut) {
        return students.values().stream()
            .filter(s -> rut.equals(s.getRut()))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));
    }
}
