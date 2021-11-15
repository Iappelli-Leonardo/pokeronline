package it.prova.pokeronline.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import it.prova.pokeronline.model.Tavolo;
import it.prova.pokeronline.model.Utente;

public class TavoloDTO {
	
	private Long id;

	@NotBlank(message = "{denominazione.notblank}")
	@Size(min = 4, max = 15, message = "Il valore inserito '${validatedValue}' deve essere lungo tra {min} e {max} caratteri")
	private String denominazione;

	@NotNull(message = "{dataCreazione.notnull}")
	private Date dataCreazione;

	@NotNull(message = "{esperienzaMin.notnull}")
	@Min(1)
	private Integer esperienzaMin;
	
	@NotNull(message = "{cifraMin.notnull}")
	@Min(1)
	private Integer cifraMin;
	
	private UtenteDTO utenteCreatore;
	
	private Set<Utente> giocatori = new HashSet<Utente>();

	public TavoloDTO() {
		super();
		// TODO Auto-generated constructor stub
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

	public TavoloDTO(Long id, String denominazione, Date dataCreazione, Integer esperienzaMin, Integer cifraMin) {
		super();
		this.id = id;
		this.denominazione = denominazione;
		this.dataCreazione = dataCreazione;
		this.esperienzaMin = esperienzaMin;
		this.cifraMin = cifraMin;
	}

	public TavoloDTO(String denominazione, Date dataCreazione, Integer esperienzaMin, Integer cifraMin) {
		super();
		this.denominazione = denominazione;
		this.dataCreazione = dataCreazione;
		this.esperienzaMin = esperienzaMin;
		this.cifraMin = cifraMin;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
	
	
	public static TavoloDTO buildTavoloDTOFromModel(Tavolo tavolo) {
		return new TavoloDTO(tavolo.getId(), tavolo.getDenominazione(), tavolo.getDataCreazione(),
				tavolo.getEsperienzaMin(), tavolo.getCifraMin(),
				UtenteDTO.buildUtenteDTOFromModel(tavolo.getCreatore()), tavolo.getGiocatori());
	}

	public static List<TavoloDTO> createTavoloDTOListFromModelList(List<Tavolo> modelListInput) {
		return modelListInput.stream().map(registaEntity -> {
			return TavoloDTO.buildTavoloDTOFromModel(registaEntity);
		}).collect(Collectors.toList());
	}

	public Tavolo buildTavoloModel() {
		return new Tavolo(this.id, this.denominazione, this.dataCreazione, this.esperienzaMin, this.cifraMin,
				this.utenteCreatore.buildUtenteModel(false), this.giocatori);
	}
	


}
