package it.prova.pokeronline.repository.tavolo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import it.prova.pokeronline.model.Tavolo;

public interface TavoloRepository extends CrudRepository<Tavolo, Long>, CustomTavoloRepository {
	Tavolo findByDenominazione(String denominazione);
	
	List<Tavolo> findAllByUtenti_IdIs(Long id);
	
}
