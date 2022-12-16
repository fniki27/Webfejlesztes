package com.example.webfejlesztes_project.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserService service;

    @GetMapping("/users")
    public String showUserList(Model model) {
        List<User> listUsers = service.listAll();
        model.addAttribute("listUsers", listUsers);
        return "users";
    }

    @GetMapping("/admin")
    public String showAdminList(Model model) {
        List<User> listUsers = service.listAll();
        model.addAttribute("listUsers", listUsers);
        return "manage_users";
    }

    @GetMapping("/admin/new")
    public String createNewUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("pageTitle", "Add New User");
        return "create_user";
    }

    @PostMapping("/admin/save")
    public String saveUser(User user, RedirectAttributes redirect) {
        service.saveUser(user);
        redirect.addFlashAttribute("message", "User saved successfully!");
        return  "redirect:/admin";
    }

    @GetMapping("/admin/update/{id}")
    public String editUser(@PathVariable("id") Integer id, Model model, RedirectAttributes redirect) {
        try {
            User user = service.getUserId(id);
            model.addAttribute("user", user);
            model.addAttribute("pageTitle", "Update User (ID: " + id + ")");

            return "create_user";
        } catch (UserNotFoundException e) {
            redirect.addFlashAttribute("message", e.getMessage());
        }

        return  "redirect:/admin";
    }

    @GetMapping("/admin/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, RedirectAttributes redirect) {
        try {
            service.deleteUser(id);
            redirect.addFlashAttribute("message", "User " + id + " deleted successfully!");
        } catch (UserNotFoundException e) {
            redirect.addFlashAttribute("message", e.getMessage());
        }

        return  "redirect:/admin";
    }

}
