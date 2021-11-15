package it.prova.pokeronline.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import it.prova.pokeronline.dto.RuoloDTO;
import it.prova.pokeronline.dto.UtenteDTO;
import it.prova.pokeronline.model.Utente;
import it.prova.pokeronline.service.RuoloService;
import it.prova.pokeronline.service.UtenteService;
import it.prova.pokeronline.utility.UtilityForm;
import it.prova.pokeronline.validation.ValidationNoPassword;
import it.prova.pokeronline.validation.ValidationWithPassword;


@Controller
@RequestMapping(value = "/utente")
public class UtenteController {

	@Autowired
	private UtenteService utenteService;

	@Autowired
	private RuoloService ruoloService;

	@GetMapping
	public ModelAndView listAllUtenti() {
		ModelAndView mv = new ModelAndView();
		List<Utente> utenti = utenteService.listAllUtenti();
		mv.addObject("utente_list_attribute", utenti);
		mv.setViewName("utente/list");
		return mv;
	}
	
	@GetMapping("/search")
	public String searchUtente(Model model) {
		model.addAttribute("mappaRuoliConSelezionati_attr", UtilityForm
				.buildCheckedRolesForPages(RuoloDTO.createRuoloDTOListFromModelList(ruoloService.listAll()), null));
		return "utente/search";
	}

	@PostMapping("/list")
	public String listUtenti(Utente utenteExample, ModelMap model, HttpServletRequest request) {
		String[] ruoliInput = request.getParameterValues("ruoliIds");
		List<Utente> utenti = utenteService.findByExample(utenteExample, ruoliInput);
		model.addAttribute("utente_list_attribute", utenti);
		return "utente/list";
	}

	@GetMapping("/insert")
	public String create(Model model) {
		model.addAttribute("mappaRuoliConSelezionati_attr", UtilityForm
				.buildCheckedRolesForPages(RuoloDTO.createRuoloDTOListFromModelList(ruoloService.listAll()), null));
		model.addAttribute("insert_utente_attr", new UtenteDTO());
		return "utente/insert";
	}

	// per la validazione devo usare i groups in quanto nella insert devo validare
	// la pwd, nella edit no
	@PostMapping("/save")
	public String save(
			@Validated({ ValidationWithPassword.class,
					ValidationNoPassword.class }) @ModelAttribute("insert_utente_attr") UtenteDTO utenteDTO,
			BindingResult result, Model model, RedirectAttributes redirectAttrs) {

		if (!result.hasFieldErrors("password") && !utenteDTO.getPassword().equals(utenteDTO.getConfermaPassword()))
			result.rejectValue("confermaPassword", "password.diverse");

		if (result.hasErrors()) {
			model.addAttribute("mappaRuoliConSelezionati_attr", UtilityForm.buildCheckedRolesForPages(
					RuoloDTO.createRuoloDTOListFromModelList(ruoloService.listAll()), utenteDTO.getRuoliIds()));
			return "utente/insert";
		}
		utenteService.inserisciNuovo(utenteDTO.buildUtenteModel(true));

		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		return "redirect:/utente";
	}

	@GetMapping("/edit/{idUtente}")
	public String edit(@PathVariable(required = true) Long idUtente, Model model) {
		Utente utenteModel = utenteService.caricaSingoloUtenteConRuoli(idUtente);
		model.addAttribute("edit_utente_attr", UtenteDTO.buildUtenteDTOFromModel(utenteModel));
		model.addAttribute("mappaRuoliConSelezionati_attr",
				UtilityForm.buildCheckedRolesFromRolesAlreadyInUtente(
						RuoloDTO.createRuoloDTOListFromModelList(ruoloService.listAll()),
						RuoloDTO.createRuoloDTOListFromModelSet(utenteModel.getRuoli())));
		return "utente/edit";
	}

	@PostMapping("/update")
	public String update(@Validated(ValidationNoPassword.class) @ModelAttribute("edit_utente_attr") UtenteDTO utenteDTO,
			BindingResult result, Model model, RedirectAttributes redirectAttrs, HttpServletRequest request) {

		if (result.hasErrors()) {
			model.addAttribute("mappaRuoliConSelezionati_attr", UtilityForm.buildCheckedRolesForPages(
					RuoloDTO.createRuoloDTOListFromModelList(ruoloService.listAll()), utenteDTO.getRuoliIds()));
			return "utente/edit";
		}
		utenteService.aggiorna(utenteDTO.buildUtenteModel(true));

		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		return "redirect:/utente";
	}

	@PostMapping("/cambiaStato")
	public String cambiaStato(@RequestParam(name = "idUtenteForChangingStato", required = true) Long idUtente) {
		utenteService.changeUserAbilitation(idUtente);
		return "redirect:/utente";
	}

	@GetMapping("/cambiaPassword/{idUtente}")
	public String cambiaPassword(@PathVariable(required = true) Long idUtente, RedirectAttributes redirectAttrs) {
		Utente utente = utenteService.caricaSingoloUtente(idUtente);

		utenteService.aggiornaPassword(utente);
		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");

		return "redirect:/utente";

	}

	@GetMapping("/show/{idUtente}")
	public String showRegista(@PathVariable(required = true) Long idUtente, Model model) {
		model.addAttribute("show_utente_attr", utenteService.caricaSingoloUtente(idUtente));
		return "utente/show";
	}

	@GetMapping("/password")
	public String password(Model model) {
		model.addAttribute("new_password_attr", new UtenteDTO());
		return "utente/password";
	}

	@PostMapping("/savePassword")
	public String savePassword(
			@Validated({ ValidationWithPassword.class,
					ValidationNoPassword.class }) @ModelAttribute("new_password_attr") UtenteDTO utenteDTO,
			BindingResult result, Model model, RedirectAttributes redirectAttrs) {

		if (!result.hasFieldErrors("password") && !utenteDTO.getPassword().equals(utenteDTO.getConfermaPassword()))
			result.rejectValue("confermaPassword", "password.diverse");

		utenteService.aggiorna(utenteDTO.buildUtenteModel(true));

		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		return "redirect:/utente";
	}
	
	@PostMapping("/resetpw/{idUtente}")
	public String resetPw(@RequestParam Long idUtente, RedirectAttributes redirectAttrs) {

		utenteService.aggiornaPassword(utenteService.caricaSingoloUtente(idUtente));
		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		return "redirect:/utente";
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

	@GetMapping(value = "/searchUtentiAjax", produces = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody String searchTavolo(@RequestParam String term) {

		List<Utente> listaTavoloByTerm = utenteService.cercaByCognomENomeLike(term);
		return buildJsonResponse(listaTavoloByTerm);
	}

	private String buildJsonResponse(List<Utente> listaUtenti) {
		JsonArray ja = new JsonArray();

		for (Utente utenteItem : listaUtenti) {
			JsonObject jo = new JsonObject();
			jo.addProperty("value", utenteItem.getId());
			jo.addProperty("label", utenteItem.getNome() + " " + utenteItem.getCognome());
			ja.add(jo);
		}

		return new Gson().toJson(ja);
	}
	

	@GetMapping("/ricarica")
	public String ricaricaCredito(Model model) {
		return "utente/credito";
	}
	
	@PostMapping("/aggiungiCredito")
	public String aggiungiCredito(RedirectAttributes redirectAttrs, HttpServletRequest request,Model model) {
		int creditoDaAggiungere = Integer.parseInt(request.getParameter("ricarica"));
		String utenteInSessione = request.getUserPrincipal().getName();
		if(creditoDaAggiungere <= 0) {
			request.setAttribute("errorMessage", "Il credito inserito è negativo o pari a 0!");
			return "utente/credito";
		}else {
			
		utenteService.aggiungiCredito(utenteInSessione, creditoDaAggiungere);
		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		return "index";
		}
	}
	
	private String buildJsonResponseSingleUser(Utente utente) {
		JsonArray ja = new JsonArray();

		JsonObject jo = new JsonObject();
		jo.addProperty("credito", utente.getCreditoAccumulato());
		jo.addProperty("exp", utente.getEsperienzaAccumulata());
		ja.add(jo);

		return new Gson().toJson(ja);
	}
	
	@GetMapping("/caricaParametri")
	public @ResponseBody String caricaParametri(HttpServletRequest request) {
		
		Utente utente = utenteService.findByUsername(request.getUserPrincipal().getName());
		return buildJsonResponseSingleUser(utente);
	}
}
