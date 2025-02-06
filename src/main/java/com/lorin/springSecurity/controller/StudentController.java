package com.lorin.springSecurity.controller;

import com.lorin.springSecurity.entities.Student;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class StudentController
{
    private List<Student> students = new ArrayList<>(List.of(
            new Student(1, "lorin", 60),
            new Student(2, "melek", 65)
    ));
    @GetMapping("/students")
    public List<Student> getStudents()
    {
        return students;
    }
    @GetMapping("/csrf-token")
    public CsrfToken getCsrfToken(HttpServletRequest request)
    {
        return (CsrfToken) request.getAttribute("_csrf");
    }//token ı manuel olarak almak için yazdık.

    @PostMapping("/students")
    public List<Student> addStudents(@RequestBody Student student)
    {
        students.add(student);
        return students;
    }

}
