package it.prova.pokeronline.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.prova.pokeronline.dto.RuoloDTO;
import it.prova.pokeronline.dto.UtenteDTO;
import it.prova.pokeronline.service.RuoloService;
import it.prova.pokeronline.service.UtenteService;
import it.prova.pokeronline.utility.UtilityForm;
import it.prova.pokeronline.validation.ValidationNoPassword;
import it.prova.pokeronline.validation.ValidationWithPassword;

@Controller
@RequestMapping(value = "/autoRegistrazione")
public class AutoRegistrazione {
	
	@Autowired
	private UtenteService utenteService;
	
	@GetMapping("/autoReg")
	public String autoInsert(Model model) {
		model.addAttribute("insert_utente_attr", new UtenteDTO());
		return "/registrazione/registrazione";
	}
	
	@PostMapping("/save")
	public String saveReg(
			@Validated({ ValidationWithPassword.class,
					ValidationNoPassword.class }) @ModelAttribute("insert_utente_attr") UtenteDTO utenteDTO,
			BindingResult result, Model model, RedirectAttributes redirectAttrs) {

		if (!result.hasFieldErrors("password") && !utenteDTO.getPassword().equals(utenteDTO.getConfermaPassword()))
			result.rejectValue("confermaPassword", "password.diverse");

		if (result.hasErrors()) {
			return "/autoRegistrazione/autoReg";
		}

		utenteService.inserisciNuovo(utenteDTO.buildUtenteModel(false));

		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		return "redirect:login";
	}

}
