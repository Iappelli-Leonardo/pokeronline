package it.prova.pokeronline.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.prova.pokeronline.model.Tavolo;
import it.prova.pokeronline.model.Utente;
import it.prova.pokeronline.service.TavoloService;
import it.prova.pokeronline.service.UtenteService;

@Controller
@RequestMapping(value = "/gioco")
public class GiocoController {
	@Autowired
	TavoloService tavoloService;

	@Autowired
	UtenteService utenteService;

	@GetMapping("/show/{idTavolo}")
	public String show(@PathVariable(required = true) Long idTavolo, Model model) {
		Tavolo tavolo = tavoloService.caricaSingoloElemento(idTavolo);
		model.addAttribute("show_tavolo_attr", tavolo);
		return "gioca/show";
	}

	@PostMapping("/avviaGioco/{idTavolo}")
	public String avviaGioco(@PathVariable(required = true) Long idTavolo, Model model, HttpServletRequest request) {

		Utente utenteInSessione = utenteService.findByUsername(request.getUserPrincipal().getName());

		utenteInSessione = utenteService.caricaSingoloUtente(utenteInSessione.getId());
		Tavolo tavoloPerGiocare = tavoloService.caricaSingoloElemento(idTavolo);

		model.addAttribute("show_tavolo_attr", tavoloPerGiocare);
		if (utenteInSessione.getTavoloGioco() != tavoloPerGiocare && utenteInSessione.getTavoloGioco() != null) {
			model.addAttribute("errorMessage", "Stai giocando a una partita!");
			return "gioca/show";
		}
		if (utenteInSessione.getEsperienzaAccumulata() < tavoloPerGiocare.getEsperienzaMin()) {
			model.addAttribute("errorMessage", "Non hai abbastanza esperienza per giocare!");
			return "gioca/show";
		}
		if (utenteInSessione.getCreditoAccumulato() < tavoloPerGiocare.getCifraMin()) {
			model.addAttribute("errorMessage", "Non hai abbastanza credito per giocare!");
			return "gioca/show";
		}

		model.addAttribute("successMessage", "Gioca a Poker!");
		return "gioca/gioca";
	}

	@PostMapping("/giocaPoker/{idTavolo}")
	public String partita(@PathVariable(required = true) Long idTavolo, Model model, HttpServletRequest request) {

		Utente utenteInSessione = utenteService.findByUsername(request.getUserPrincipal().getName());

		utenteInSessione = utenteService.caricaSingoloUtente(utenteInSessione.getId());
		Tavolo tavoloPerGiocare = tavoloService.caricaSingoloElemento(idTavolo);

		utenteInSessione
				.setCreditoAccumulato(utenteInSessione.getCreditoAccumulato() + tavoloPerGiocare.getCifraMin());
		utenteInSessione.setTavoloGioco(tavoloPerGiocare);
		utenteService.aggiorna(utenteInSessione);
		tavoloPerGiocare.getGiocatori().add(utenteInSessione);
		tavoloService.aggiorna(tavoloPerGiocare);

		model.addAttribute("show_tavolo_attr", tavoloPerGiocare);
		model.addAttribute("successMessage", "Hai Vinto!");
		return "gioca/gioca";
	}

	@PostMapping("/exit/{idTavolo}")
	public String exitPartita(@PathVariable(required = true) Long idTavolo, Model model,
			RedirectAttributes redirectAttrs, HttpServletRequest request) {

		Utente utenteInSessione = utenteService.findByUsername(request.getUserPrincipal().getName());
		utenteInSessione = utenteService.caricaSingoloUtente(utenteInSessione.getId());
		Tavolo tavoloPerGiocare = tavoloService.caricaSingoloElemento(idTavolo);

		utenteInSessione.setEsperienzaAccumulata(
				utenteInSessione.getEsperienzaAccumulata() + tavoloPerGiocare.getEsperienzaMin() + 1);

		utenteInSessione.setTavoloGioco(null);
		utenteService.aggiorna(utenteInSessione);

		tavoloPerGiocare.getGiocatori().remove(utenteInSessione);
		tavoloService.aggiorna(tavoloPerGiocare);

		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		return "redirect:/home";

	}
}
