package com.udom.school.controller;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.udom.school.model.Admin;
import com.udom.school.model.Result;
import com.udom.school.model.Students;
import com.udom.school.repostory.AdminRepo;
import com.udom.school.repostory.Resultrep;
import com.udom.school.repostory.Studentrep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
public class Regcontroller {

    @Autowired
    private Studentrep studentrep;

    @Autowired
    private Resultrep rs;

    @Autowired
    private AdminRepo ap;

    @RequestMapping("/")
    public String register() {
        return "register.html";
    }

    @PostMapping("/addStudent")
    public String addStudent(@RequestParam String fullname,
                             @RequestParam String parentphone,
                             @RequestParam String candidate,
                             @RequestParam String password,
                             Model model) {
        Students st = studentrep.findByCandidate(candidate);
        if (st == null) {
            Students student = new Students();
            student.setFullname(fullname);
            student.setParentphone(parentphone);
            student.setCandidate(candidate);
            student.setPassword(password);

            studentrep.save(student);
            return "redirect:/listStudents";
        }

        model.addAttribute("errorMessage", "The candidate number already used by another student");
        return "register";
    }

    @GetMapping("/listStudents")
    public String listStudents(Model model) {
        List<Students> studentsList = studentrep.findAll();
        model.addAttribute("students", studentsList);
        return "list.html";
    }

    @GetMapping("/students/view")
    public String viewStudent(@RequestParam String candidate, Model model) {
        Students student = studentrep.findByCandidate(candidate);
        model.addAttribute("student", student);
        return "profile";
    }

    @PostMapping("/addResults")
    public String addResults(@RequestParam String candidate, @RequestParam String physics, @RequestParam String chemistry, @RequestParam String mathematics, @RequestParam String date) {
        Result result = new Result();
        result.setCandidate(candidate);
        result.setPhysics(physics);
        result.setChemistry(chemistry);
        result.setMathematics(mathematics);
        result.setDate(date);
        rs.save(result);
        return "redirect:/students/view?candidate=" + candidate;
    }

    @GetMapping("/students/results")
    public String viewStudentResults(@RequestParam String candidate, Model model) {
        Students student = studentrep.findByCandidate(candidate);
        List<Result> results = rs.findByCandidate(candidate);
        model.addAttribute("student", student);
        model.addAttribute("results", results);
        return "result";
    }

    @GetMapping("/students/results/edit")
    public String edit(
            @RequestParam String candidate,
            @RequestParam String date,
            Model model
    ) {
        Result result = rs.findByCandidateAndDate(candidate, date);
        Students student = studentrep.findByCandidate(candidate);
        model.addAttribute("student", student);
        model.addAttribute("result", result);
        return "edit";
    }

    @PostMapping("/students/results/update")
    public String updateResult(@RequestParam String candidate, @RequestParam String physics, @RequestParam String chemistry, @RequestParam String mathematics, @RequestParam String date) {
        Result result = rs.findByCandidateAndDate(candidate, date);
        result.setPhysics(physics);
        result.setChemistry(chemistry);
        result.setMathematics(mathematics);
        rs.save(result);
        return "redirect:/students/results?candidate=" + candidate;
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @GetMapping("/home")
    public String home(Model model, HttpSession session) {
        Students student = (Students) session.getAttribute("student");
        if (student != null) {
            model.addAttribute("student", student);
            model.addAttribute("results", rs.findByCandidate(student.getCandidate()));
        }
        return "home";
    }

    @PostMapping("/loginUser")
    public String login(@RequestParam String candidate, @RequestParam String password, HttpSession session, Model model) {
        Students student = studentrep.findByCandidateAndPassword(candidate, password);
        if (student != null) {
            session.setAttribute("student", student);
            return "redirect:/home";
        }
        model.addAttribute("errorMessage", "Invalid username or password");
        return "login";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

    @PostMapping("/adminLogin")
    public String adminLogin(
            @RequestParam String username,
            @RequestParam String password,
            Model model
    ) {
        Admin admin = ap.findByUsernameAndPassword(username, password);
        if (admin != null) {
            return "redirect:/listStudents";
        }
        model.addAttribute("errorMessage", "Invalid username or password");
        return "admin";

    }

    @GetMapping("/print")
    public void printResults(HttpServletResponse response, HttpSession session) throws DocumentException, IOException {
        Students student = (Students) session.getAttribute("student");
        if (student == null) {
            response.sendRedirect("/login");
            return;
        }

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=results.pdf");

        Document document = new Document();
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        document.add(new Paragraph("Student Name: " + student.getFullname()));
        document.add(new Paragraph("Parent Phone Number: " + student.getParentphone()));
        document.add(new Paragraph("Candidate: " + student.getCandidate()));
        document.add(new Paragraph("\nResults:"));

        List<Result> results = rs.findByCandidate(student.getCandidate());
        for (Result result : results) {
            document.add(new Paragraph("PHYSICS: " + result.getPhysics()));
            document.add(new Paragraph("CHEMISTRY: " + result.getChemistry()));
            document.add(new Paragraph("MATHEMATICS: " + result.getMathematics()));
            document.add(new Paragraph("UPDATED ON: " + result.getDate()));
            document.add(new Paragraph("\n"));
        }

        document.close();
    }
}

