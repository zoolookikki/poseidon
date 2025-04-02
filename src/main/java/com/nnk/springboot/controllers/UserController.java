package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.dto.UserCreateRequestDto;
import com.nnk.springboot.dto.UserUpdateRequestDto;
import com.nnk.springboot.exception.UsernameAlreadyExistsException;
import com.nnk.springboot.mapper.UserMapper;
import com.nnk.springboot.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
@Log4j2
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/list")
    public String list(Model model) {
        // ici users est lié à List<UserDto>.
        model.addAttribute("users", userService.findAll());

        return "user/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("user", new UserCreateRequestDto());

        return "user/add";
    }

    @PostMapping("/validate")
    // @ModelAttribute permet de lier les champs d’un formulaire HTML à un objet Java.
    public String create(@Valid @ModelAttribute("user") UserCreateRequestDto userCreateRequestDto,
                         BindingResult result) {
        if (result.hasErrors()) {
            return "user/add";
        }

        try {
            userService.save(userCreateRequestDto);
        } catch (UsernameAlreadyExistsException ex) {
            // erreur affichée sur ce champ idem erreurs de saisie contrôlées dans les Dtos.
            result.rejectValue("username", "error.user", ex.getMessage());
            return "user/add";
        }

        return "redirect:/user/list";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable Integer id,
                                 Model model) {
        User user = userService.findById(id);

        // transformation en UserUpdateRequestDto pour laisser la saisie du mot de passe libre.
        UserUpdateRequestDto userUpdateRequestDto = userMapper.toUpdateRequestDto(user);
        model.addAttribute("user", userUpdateRequestDto);

        return "user/update";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable Integer id,
                         @Valid @ModelAttribute("user") UserUpdateRequestDto userUpdateRequestDto,
                         BindingResult result) {
        log.debug("PostMapping/update,id="+id+",userUpdateRequestDto="+userUpdateRequestDto);

        if (result.hasErrors()) {
            return "user/update";
        }

        try {
            userService.update(id, userUpdateRequestDto);
        } catch (UsernameAlreadyExistsException ex) {
            result.rejectValue("username", "error.user", ex.getMessage());
            return "user/update";
        }

        return "redirect:/user/list";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        userService.deleteById(id);

        return "redirect:/user/list";
    }

}
