package it.prova.pokeronline.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import it.prova.pokeronline.dto.UtenteDTO;
import it.prova.pokeronline.model.Tavolo;
import it.prova.pokeronline.model.Utente;
import it.prova.pokeronline.service.UtenteService;

@Controller
@RequestMapping(value = "/addCredito")
public class AddCredito {
	
	@Autowired
	private UtenteService utenteService;
	
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
	
	
	private String buildJsonResponseSingleUser(Utente utente) {
		JsonArray ja = new JsonArray();

		JsonObject jo = new JsonObject();
		jo.addProperty("credito", utente.getCreditoAccumulato());
		jo.addProperty("exp", utente.getEsperienzaAccumulata());
		ja.add(jo);

		return new Gson().toJson(ja);
	}
	
	//per passare i paramentri all'index.jsp e mostrare il credito e l'esperienza1
	@GetMapping("/caricaParametri")
	public @ResponseBody String caricaParametri(HttpServletRequest request) {
		
		Utente utente = utenteService.findByUsername(request.getUserPrincipal().getName());
		return buildJsonResponseSingleUser(utente);
	}
	
	@GetMapping("/goToMyLastGame")
	public String goToMyLastGame(Model model, HttpServletRequest request) {
		Utente utente = utenteService.findByUsername(request.getUserPrincipal().getName());
		Tavolo tavoloPerGiocare = utente.getTavoloGioco();

		if (utente.getTavoloGioco() == null)
			return "index";

		model.addAttribute("show_tavolo_attr", tavoloPerGiocare);
		return "gioca/gioca";
	}


}
