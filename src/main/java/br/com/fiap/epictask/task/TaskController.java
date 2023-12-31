package br.com.fiap.epictask.task;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.fiap.epictask.user.User;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/task")
public class TaskController {

    @Autowired
    TaskService service;

    @Autowired
    MessageSource message;

    @GetMapping
    public String index(Model model, @AuthenticationPrincipal OAuth2User user){
        model.addAttribute("username", user.getAttribute("name"));
        model.addAttribute("avatar_url", user.getAttribute("avatar_url"));
        model.addAttribute("tasks", service.findAll());
        return "task/index";
    }
    
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirect){
        if (service.delete(id)){
            redirect.addFlashAttribute("success", message.getMessage("task.delete.success", null, LocaleContextHolder.getLocale()));
        }else{
            redirect.addFlashAttribute("error", "Tarefa não encontrada");
        }
        return "redirect:/task";
    }

    @GetMapping("new")
    public String form(Task task, Model model, @AuthenticationPrincipal OAuth2User user){
        model.addAttribute("username", user.getAttribute("name"));
        model.addAttribute("avatar_url", user.getAttribute("avatar_url"));
        return "task/form";
    }

    @PostMapping
    public String create(@Valid Task task, BindingResult binding, RedirectAttributes redirect){
        if (binding.hasErrors()) return "/task/form";

        service.save(task);
        redirect.addFlashAttribute("success", "Tarefa cadastrada com sucesso");
        return "redirect:/task";
    }

    @GetMapping("/inc/{id}")
    public String incrementStatus(@PathVariable Long id, RedirectAttributes redirect){
        if (!service.increment(id)){
            redirect.addFlashAttribute("error", "Erro ao alterar status da tarefa");
        }
        return "redirect:/task";
    }  
    
    @GetMapping("/dec/{id}")
    public String decrementStatus(@PathVariable Long id, RedirectAttributes redirect){
        if (!service.decrement(id)){
            redirect.addFlashAttribute("error", "Erro ao alterar status da tarefa");
        }
        return "redirect:/task";
    }

    @GetMapping("/catch/{id}")
    public String catchTask(@PathVariable Long id, @AuthenticationPrincipal OAuth2User user){
        service.catchTask(id, User.convert(user));
        return "redirect:/task";
    }
    

    
}
