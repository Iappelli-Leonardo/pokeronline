package it.prova.pokeronline.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
import it.prova.pokeronline.model.Utente;
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
	
	@Autowired
	private RuoloService ruoloService;
	
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
			model.addAttribute("mappaRuoliConSelezionati_attr", UtilityForm.buildCheckedRolesForPages(
					RuoloDTO.createRuoloDTOListFromModelList(ruoloService.listAll()), utenteDTO.getRuoliIds()));
			return "/autoRegistrazione/autoReg";
		}
		if(utenteService.findByUsername(utenteDTO.getUsername()) == null) {
			utenteService.inserisciNuovo(utenteDTO.buildUtenteModel(true));

			redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
			return "redirect:/login";
		}else
			redirectAttrs.addFlashAttribute("errorMessage", "Operazione eseguita correttamente");
			return "/registrazione/registrazione";
	}
	
	@GetMapping("/resetuserpassword")
	public String resetUserPassword(Model model) {

		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		model.addAttribute("resetpw_utente_attr",
				UtenteDTO.buildUtenteDTOFromModel(utenteService.findByUsername(userDetails.getUsername())));
		return "utente/resetpassword";
	}

	@PostMapping("/saveresetuserpw")
	public String saveResetUserPw(RedirectAttributes redirectAttrs, HttpServletRequest request) {

		String vecchiaPassword = request.getParameter("oldpassword");
		String nuovaPassword = request.getParameter("password");
		String confermaPassword = request.getParameter("confermaPassword");
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String usernameUtenteSessione = userDetails.getUsername();

		Utente utenteInSessione = utenteService.findByUsername(usernameUtenteSessione);

		utenteService.cambiaPassword(nuovaPassword, vecchiaPassword, confermaPassword, utenteInSessione);

		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		return "redirect:/logout";
	}


}
