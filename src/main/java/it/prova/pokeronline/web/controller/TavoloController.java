package it.prova.pokeronline.web.controller;

import org.springframework.http.MediaType;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
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

import it.prova.pokeronline.dto.TavoloDTO;
import it.prova.pokeronline.dto.UtenteDTO;
import it.prova.pokeronline.model.Tavolo;
import it.prova.pokeronline.model.Utente;
import it.prova.pokeronline.service.TavoloService;
import it.prova.pokeronline.service.UtenteService;


@Controller
@RequestMapping(value = "/tavolo")
public class TavoloController {

	@Autowired
	private TavoloService tavoloService;
	
	@Autowired
	private UtenteService utenteService;
	
	@GetMapping
	public ModelAndView listAllRegisti() {
		ModelAndView mv = new ModelAndView();
		List<Tavolo> tavoli = tavoloService.listAllElements();
		// trasformiamo in DTO
		mv.addObject("tavoli_list_attribute", TavoloDTO.createTavoloDTOListFromModelList(tavoli));
		mv.setViewName("tavolo/list");
		return mv;
	}
	@GetMapping("/findMyTables")
	public String findMieiTavoli(Model model, HttpServletRequest request) {
		List<Tavolo> tavoli = tavoloService.cercaMieiTavoli(utenteService.findByUsername(request.getUserPrincipal().getName()));
		model.addAttribute("tavoli_list_attribute", tavoli);
		return "tavolo/list";
	}

	@GetMapping("/insert")
	public String createTavolo(Model model) {
		model.addAttribute("insert_tavolo_attr", new TavoloDTO());
		return "tavolo/insert";
	}

	@PostMapping("/save")
	public String saveTavolo(@Valid @ModelAttribute("insert_tavolo_attr") TavoloDTO tavoloDTO, BindingResult result,
			RedirectAttributes redirectAttrs,  HttpServletRequest request) {

		if (result.hasErrors())
			return "tavolo/insert";

		Utente utente = utenteService.findByUsername(request.getUserPrincipal().getName());
		UtenteDTO creatore = new UtenteDTO();
		creatore.setId(utente.getId());
		tavoloDTO.setUtenteCreatore(creatore);

		tavoloService.inserisciNuovo(tavoloDTO.buildTavoloModel());

		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		return "redirect:/tavolo/findMyTables";	}
	
	@GetMapping("/search")
	public String searchTavolo(Model model) {
		model.addAttribute("search_tavolo_attr", new TavoloDTO());
		return "tavolo/search";
	}
	
	@PostMapping("/list")
	public String listTavoli(TavoloDTO tavoloExample, ModelMap model) {
		List<Tavolo> tavoli = tavoloService.findByExample(tavoloExample);
		model.addAttribute("tavoli_list_attribute", TavoloDTO.createTavoloDTOListFromModelList(tavoli));
		return "tavolo/listaGenerica";
	}
	
	@GetMapping("/show/{idTavolo}")
	public String show(@PathVariable(required = true) Long idTavolo, Model model) {
		Tavolo tavolo = tavoloService.caricaSingoloElemento(idTavolo);
		model.addAttribute("show_tavolo_attr", tavolo);
		return "tavolo/show";
	}
	
	@GetMapping("/edit/{idTavolo}")
	public String edit(@PathVariable(required = true) Long idTavolo, Model model) {
		Tavolo tavolo = tavoloService.caricaSingoloElemento(idTavolo);
		model.addAttribute("update_tavolo_attr", tavolo);
		return "tavolo/edit";
	}

	@PostMapping("/modifica")
	public String saveUpdate(@Valid @ModelAttribute("update_tavolo_attr") TavoloDTO tavoloDTO, BindingResult result,
			RedirectAttributes redirectAttrs,  HttpServletRequest request,  Model model) {

		if (result.hasErrors())
			return "tavolo/edit";

		Tavolo tavolo = tavoloService.caricaSingoloElemento(tavoloDTO.getId());

		if (tavolo.getGiocatori().size() == 0) {

			tavoloDTO.setGiocatori(tavolo.getGiocatori());
			tavoloDTO.setUtenteCreatore(UtenteDTO.buildUtenteDTOFromModel(tavolo.getCreatore()));

			tavoloService.aggiorna(tavoloDTO.buildTavoloModel());
		
		} else
			request.setAttribute("errorMessage", "Ci sono ancora giocatori che stanno giocando");

		List<Tavolo> tavoli = tavoloService
				.cercaMieiTavoli(utenteService.findByUsername(request.getUserPrincipal().getName()));
		model.addAttribute("tavoli_list_attribute", tavoli);
		
		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		return "redirect:/tavolo";
	}

	@GetMapping("/delete/{idTavolo}")
	public String delete(@PathVariable(required = true) Long idTavolo, Model model) {

		Tavolo tavolo = tavoloService.caricaSingoloElemento(idTavolo);
		model.addAttribute("delete_tavolo_attr", tavolo);
		return "tavolo/delete";
	}

	@PostMapping("/elimina")
	public String salvadelete(@RequestParam Long idTavolo, Model model, RedirectAttributes redirectAttrs,
			HttpServletRequest request) {

		if (tavoloService.caricaSingoloTavoloConGiocatori(idTavolo).getGiocatori().size() == 0) {
			tavoloService.rimuoviById(idTavolo);
			redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		} 
		else
		request.setAttribute("errorMessage", "Ci sono ancora giocatori che stanno giocando");

		List<Tavolo> tavoli = tavoloService.cercaMieiTavoli(utenteService.findByUsername(request.getUserPrincipal().getName()));
		model.addAttribute("tavoli_list_attribute", tavoli);
	
		return "tavolo/list";
	}
	
	@GetMapping("/gestione")
	public String gestione(Model model) {
		return "tavolo/searchGestioneTavolo";
	}
	
	

	@PostMapping("/listGestione")
	public String listGestione(@ModelAttribute("search_gestione_tavolo_attr") TavoloDTO tavoloDTO, Model model,
			RedirectAttributes redirectAttrs, HttpServletRequest request) {
		
		List<Tavolo> tavoli = tavoloService.findByExampleGestione(tavoloDTO, request.getUserPrincipal().getName());

		model.addAttribute("tavoli_list_attribute", TavoloDTO.createTavoloDTOListFromModelList(tavoli));
		return "tavolo/list";
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

}
