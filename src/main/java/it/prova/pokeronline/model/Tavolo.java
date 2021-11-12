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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tavolo")
	private Set<Utente> utenti = new HashSet<Utente>(0);

	public Tavolo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Tavolo(Long id, String denominazione, Date dataCreazione, Integer esperienzaMin, Integer cifraMin,
			Set<Utente> utenti) {
		super();
		this.id = id;
		this.denominazione = denominazione;
		this.dataCreazione = dataCreazione;
		this.esperienzaMin = esperienzaMin;
		this.cifraMin = cifraMin;
		this.utenti = utenti;
	}

	public Tavolo(String denominazione, Date dataCreazione, Integer esperienzaMin, Integer cifraMin,
			Set<Utente> utenti) {
		super();
		this.denominazione = denominazione;
		this.dataCreazione = dataCreazione;
		this.esperienzaMin = esperienzaMin;
		this.cifraMin = cifraMin;
		this.utenti = utenti;
	}

	public Tavolo(String denominazione, Date dataCreazione, Integer esperienzaMin, Integer cifraMin) {
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

	public Set<Utente> getUtenti() {
		return utenti;
	}

	public void setUtenti(Set<Utente> utenti) {
		this.utenti = utenti;
	}
	
}
