package it.prova.pokeronline.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "tavolo")
public class Tavolo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "denominazione")
	private String denominazione;
	@Column(name = "dataCreazione")
	private Date dataCreazione;
	@Column(name = "esperienzaMin")
	private Integer esperienzaMin;
	@Column(name = "cifraMin")
	private Integer cifraMin;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "utenteCreatore_id", nullable = false)
	private Utente utenteCreatore;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tavoloGioco")
	private Set<Utente> giocatori = new HashSet<Utente>();

	public Tavolo() {
		super();
	}

	public Tavolo(String denominazione, Date dataCreazione, int esperienzaMin, int cifraMin) {
		super();
		this.denominazione = denominazione;
		this.dataCreazione = dataCreazione;
		this.esperienzaMin = esperienzaMin;
		this.cifraMin = cifraMin;
	}

	public Tavolo(Long id, String denominazione, Date dataCreazione, Integer esperienzaMin, Integer cifraMin,
			Utente  utenteCreatore) {
		super();
		this.id = id;
		this.denominazione = denominazione;
		this.dataCreazione = dataCreazione;
		this.esperienzaMin = esperienzaMin;
		this.cifraMin = cifraMin;
		this. utenteCreatore =  utenteCreatore;
	}

	public Tavolo(Long id, String denominazione, Date dataCreazione, int esperienzaMin, int cifraMin,
			Utente utenteCreatore, Set<Utente> giocatori) {
		super();
		this.id = id;
		this.denominazione = denominazione;
		this.dataCreazione = dataCreazione;
		this.esperienzaMin = esperienzaMin;
		this.cifraMin = cifraMin;
		this.utenteCreatore = utenteCreatore;
		this.giocatori = giocatori;
	}

	public Tavolo(Long id, String denominazione, Date dataCreazione, int esperienzaMin, int cifraMin) {
		super();
		this.id = id;
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

	public Utente getUtenteCreatore() {
		return utenteCreatore;
	}

	public void setUtenteCreatore(Utente utenteCreatore) {
		this.utenteCreatore = utenteCreatore;
	}

	public Utente getCreatore() {
		return utenteCreatore;
	}

	public void setCreatore(Utente utenteCreatore) {
		this.utenteCreatore = utenteCreatore;
	}

	public Set<Utente> getGiocatori() {
		return giocatori;
	}

	public void setGiocatori(Set<Utente> giocatori) {
		this.giocatori = giocatori;
	}

	

}
