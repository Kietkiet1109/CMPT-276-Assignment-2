package com.cmpt276.assignment2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.cmpt276.assignment2.models.*;

import jakarta.validation.Valid;

@Controller
public class StaffRatingController {

    @Autowired
    private StaffRatingRepository staffRatingRepository;

    /**
     * Index page: list all staff ratings.
     */
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("ratings", staffRatingRepository.findAll());
        return "index";
    }

    /**
     * Show the detail page for a single rating.
     */
    @GetMapping("/view/{id}")
    public String viewRating(@PathVariable Long id, Model model) {
        StaffRating rating = staffRatingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid rating ID: " + id));
        model.addAttribute("rating", rating);
        model.addAttribute("fancyTitle", rating.getDisplayTitle());
        return "detail";
    }

    /**
     * Show the create form.
     */
    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("staffRating", new StaffRating());
        model.addAttribute("roleTypes", RoleType.values());
        return "create";
    }

    /**
     * Show the edit form, pre-filled with the existing rating.
     */
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        StaffRating rating = staffRatingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid rating ID: " + id));
        model.addAttribute("staffRating", rating);
        model.addAttribute("rating", rating);
        model.addAttribute("roleTypes", RoleType.values());
        return "edit";
    }

    /**
     * Save (create or update) a rating. Re-renders the form on validation errors.
     */
    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("staffRating") StaffRating staffRating,
            BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("roleTypes", RoleType.values());
            // For edit form, we need the rating object for the ID display
            if (staffRating.getId() != null) {
                model.addAttribute("rating", staffRating);
                return "edit";
            }
            return "create";
        }
        // When updating, preserve the original createdAt timestamp
        if (staffRating.getId() != null) {
            StaffRating existing = staffRatingRepository.findById(staffRating.getId()).orElse(null);
            if (existing != null) {
                staffRating.setCreatedAt(existing.getCreatedAt());
            }
        }
        staffRatingRepository.save(staffRating);
        return "redirect:/";
    }

    /**
     * Delete a rating by ID with a confirmation via JS confirm() on the link.
     */
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        staffRatingRepository.deleteById(id);
        return "redirect:/";
    }
}
