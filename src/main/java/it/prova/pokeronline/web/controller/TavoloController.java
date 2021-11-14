package it.prova.pokeronline.web.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.prova.pokeronline.dto.TavoloDTO;
import it.prova.pokeronline.model.Tavolo;
import it.prova.pokeronline.service.TavoloService;


@Controller
@RequestMapping(value = "/tavolo")
public class TavoloController {

	@Autowired
	private TavoloService tavoloService;
	
	@GetMapping
	public ModelAndView listAllRegisti() {
		ModelAndView mv = new ModelAndView();
		List<Tavolo> tavoli = tavoloService.listAllElements();
		// trasformiamo in DTO
		mv.addObject("tavoli_list_attribute", TavoloDTO.createTavoloDTOListFromModelList(tavoli));
		mv.setViewName("tavolo/list");
		return mv;
	}

	@GetMapping("/insert")
	public String createTavolo(Model model) {
		model.addAttribute("insert_tavolo_attr", new TavoloDTO());
		return "tavolo/insert";
	}

	@PostMapping("/save")
	public String saveTavolo(@Valid @ModelAttribute("insert_tavolo_attr") TavoloDTO tavoloDTO, BindingResult result,
			RedirectAttributes redirectAttrs) {

		if (result.hasErrors()) {
			return "tavolo/insert";
		}
		tavoloService.inserisciNuovo(tavoloDTO.buildTavoloModel());

		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		return "redirect:/tavolo";
	}

	@GetMapping("/search")
	public String searchRegista() {
		return "tavolo/search";
	}
	
	@PostMapping("/list")
	public String listRegisti(TavoloDTO tavoloExample, ModelMap model) {
		List<Tavolo> registi = tavoloService.findByExample(tavoloExample.buildTavoloModel());
		model.addAttribute("registi_list_attribute", TavoloDTO.createTavoloDTOListFromModelList(registi));
		return "tavolo/list";
	}

}
