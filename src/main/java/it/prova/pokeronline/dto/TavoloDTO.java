package it.prova.pokeronline.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.stream.Collectors;

import it.prova.pokeronline.model.Tavolo;
import it.prova.pokeronline.model.Utente;

public class TavoloDTO {

	private Long id;

	@NotNull(message = "{esperienzaMin.notnull}")
	@Min(0)
	private Integer esperienzaMin;

	@NotNull(message = "{cifraMin.notnull}")
	@Min(0)
	private Integer cifraMin;

	@NotBlank(message = "{denominazione.notblank}")
	private String denominazione;
	
	@NotNull(message = "{dataCreazione.notnull}")
	private Date dataCreazione;

	private UtenteDTO utenteCreatore;

	private Set<Utente> giocatori = new HashSet<Utente>();

	private UtenteDTO giocatoreCercato;

	public TavoloDTO() {
	}

	public TavoloDTO(String denominazione,Date dataCreazione,  Integer esperienzaMin, Integer cifraMin, UtenteDTO utenteCreatore) {
		super();
		this.denominazione = denominazione;
		this.dataCreazione = dataCreazione;
		this.esperienzaMin = esperienzaMin;
		this.cifraMin = cifraMin;
		this.utenteCreatore = utenteCreatore;
	}
	

	public TavoloDTO(Long id,String denominazione, Date dataCreazione, Integer esperienzaMin, Integer cifraMin) {
		super();
		this.id = id;
		this.denominazione = denominazione;
		this.dataCreazione = dataCreazione;
		this.esperienzaMin = esperienzaMin;
		this.cifraMin = cifraMin;
	}
	
	public TavoloDTO(Long id, String denominazione, Date dataCreazione, Integer esperienzaMin, Integer cifraMin,
			UtenteDTO utenteCreatore, Set<Utente> giocatori) {
		super();
		this.id = id;
		this.denominazione = denominazione;
		this.dataCreazione = dataCreazione;
		this.esperienzaMin = esperienzaMin;
		this.cifraMin = cifraMin;
		this.utenteCreatore = utenteCreatore;
		this.giocatori = giocatori;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getEsperienzaMin() {
		return esperienzaMin;
	}

	public void setEsperienzaMin(Integer esperienzaMin) {
		this.esperienzaMin = esperienzaMin;
	}

	public Integer getCifraMin() {
		return cifraMin;
	}

	public void setCifraMin(Integer cifraMin) {
		this.cifraMin = cifraMin;
	}

	public String getDenominazione() {
		return denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	public Date getDataCreazione() {
		return dataCreazione;
	}

	public void setDataCreazione(Date dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	public UtenteDTO getUtenteCreatore() {
		return utenteCreatore;
	}

	public void setUtenteCreatore(UtenteDTO utenteCreatore) {
		this.utenteCreatore = utenteCreatore;
	}

	public Set<Utente> getGiocatori() {
		return giocatori;
	}

	public void setGiocatori(Set<Utente> giocatori) {
		this.giocatori = giocatori;
	}

	public UtenteDTO getGiocatoreCercato() {
		return giocatoreCercato;
	}

	public void setGiocatoreCercato(UtenteDTO giocatoreCercato) {
		this.giocatoreCercato = giocatoreCercato;
	}

	public Tavolo buildTavoloModel() {
		return new Tavolo(this.id, this.denominazione, this.dataCreazione, this.esperienzaMin, this.cifraMin, this.utenteCreatore.buildUtenteModel(true));
	}

	public static TavoloDTO buildTavoloDTOFromModel(Tavolo tavolo) {
		return new TavoloDTO(tavolo.getId(), tavolo.getDenominazione(), tavolo.getDataCreazione(),
				tavolo.getEsperienzaMin(), tavolo.getCifraMin(),
				UtenteDTO.buildUtenteDTOFromModel(tavolo.getUtenteCreatore()), tavolo.getGiocatori());
	}

	public static List<TavoloDTO> createTavoloDTOListFromModelList(List<Tavolo> modelListInput) {
		return modelListInput.stream().map(tavoloEntity -> {
			return TavoloDTO.buildTavoloDTOFromModel(tavoloEntity);
		}).collect(Collectors.toList());
	}

	@Override
	public String toString() {
		return "TavoloDTO [id=" + id + ", esperienzaMin=" + esperienzaMin + ", cifraMin=" + cifraMin
				+ ", denominazione=" + denominazione + ", dataCreazione=" + dataCreazione + ", utenteCreatore="
				+ utenteCreatore + ", giocatori=" + giocatori + ", giocatoreCercato=" + giocatoreCercato + "]";
	}

}