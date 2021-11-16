package it.prova.pokeronline.service;

import java.util.List;

import it.prova.pokeronline.dto.TavoloDTO;
import it.prova.pokeronline.model.Tavolo;
import it.prova.pokeronline.model.Utente;


public interface TavoloService {
	
	public List<Tavolo> listAllElements();

	public Tavolo caricaSingoloElemento(Long id);
	
	public Tavolo caricaSingoloTavoloConGiocatori(Long id);

	public void aggiorna(Tavolo tavoloInstance);

	public void inserisciNuovo(Tavolo tavoloInstance);

	public void rimuovi(Tavolo tavoloInstance);
	
	public void rimuoviById(Long id);
	
	public List<Tavolo> findByExample(TavoloDTO example);
	
	public Tavolo cercaPerDenominazione(String denominazione);
	
	public List<Tavolo> cercaMieiTavoli(Utente utenteInstance);
	
	public List<Tavolo> findByExampleGestione(TavoloDTO tavolo, String username);
}
