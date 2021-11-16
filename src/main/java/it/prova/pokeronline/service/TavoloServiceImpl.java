package it.prova.pokeronline.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.pokeronline.dto.TavoloDTO;
import it.prova.pokeronline.model.Tavolo;
import it.prova.pokeronline.model.Utente;
import it.prova.pokeronline.repository.tavolo.TavoloRepository;
import it.prova.pokeronline.repository.utente.UtenteRepository;

@Service
public class TavoloServiceImpl implements TavoloService{

	@Autowired
	TavoloRepository tavoloRepository;
	
	@Autowired
	UtenteRepository utenteRepository;
	
	@Transactional(readOnly = true)
	public List<Tavolo> listAllElements() {
		return (List<Tavolo>)tavoloRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Tavolo caricaSingoloElemento(Long id) {
		return tavoloRepository.findById(id).orElse(null);
	}
	
	@Transactional
	public void aggiorna(Tavolo tavoloInstance) {
		tavoloRepository.save(tavoloInstance);
	}

	@Transactional
	public void inserisciNuovo(Tavolo tavoloInstance) {
		tavoloRepository.save(tavoloInstance);
	}

	@Transactional
	public void rimuovi(Tavolo tavoloInstance) {
		tavoloRepository.delete(tavoloInstance);
	}

	@Transactional(readOnly = true)
	public List<Tavolo> findByExample(Tavolo example) {
		return tavoloRepository.findByExample(example);
	}

	@Transactional(readOnly = true)
	public Tavolo cercaPerDenominazione(String denominazione) {
		return tavoloRepository.findByDenominazione(denominazione);
	}

	@Override
	public List<Tavolo> cercaMieiTavoli(Utente utenteInstance) {
		return tavoloRepository.findAllByUtenteCreatore_IdIs(utenteInstance.getId());
	}

	@Transactional(readOnly = true)
	public Tavolo caricaSingoloTavoloConGiocatori(Long id) {
		return tavoloRepository.findByIdConGiocatori(id).orElse(null);
	}

	@Override
	public void rimuoviById(Long id) {
		tavoloRepository.deleteById(id);
	}
	

	@Transactional(readOnly = true)
	public List<Tavolo> findByExampleGestione(TavoloDTO tavolo, String username) {
		
		Utente utente = utenteRepository.findByUsernameConRuoli(username).get();
		
		if(utente.isAdmin()) {
			return tavoloRepository.findByExampleConCreatore(tavolo);
		}
		
		return tavoloRepository.findByExampleMieiTavoli(tavolo, utente.getId());
	}

}
