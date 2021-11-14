package it.prova.pokeronline.dto;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import it.prova.pokeronline.model.Tavolo;

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
	
	@NotBlank(message = "{creatore.notblank}")
	private String creatore;

	public TavoloDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TavoloDTO(Long id, String denominazione, Date dataCreazione, Integer esperienzaMin, Integer cifraMin, String creatore) {
		super();
		this.id = id;
		this.denominazione = denominazione;
		this.dataCreazione = dataCreazione;
		this.esperienzaMin = esperienzaMin;
		this.cifraMin = cifraMin;
		this.creatore = creatore;
	}
	
	public TavoloDTO(String denominazione, Date dataCreazione, Integer esperienzaMin, Integer cifraMin) {
		super();
		this.denominazione = denominazione;
		this.dataCreazione = dataCreazione;
		this.esperienzaMin = esperienzaMin;
		this.cifraMin = cifraMin;
	}
	
	public TavoloDTO(Integer esperienzaMin, Integer cifraMin) {
		super();
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
	
	public Tavolo buildTavoloModel() {
		return new Tavolo(this.id, this.denominazione, this.dataCreazione, this.esperienzaMin, this.cifraMin);
	}
	
	public static TavoloDTO buildTavoloDTOFromModel(Tavolo tavoloModel) {
		return new TavoloDTO(tavoloModel.getId(), tavoloModel.getDenominazione(), tavoloModel.getDataCreazione(),
				tavoloModel.getEsperienzaMin(), tavoloModel.getCifraMin(), tavoloModel.getCreatore());
	}
	
	public static List<TavoloDTO> createTavoloDTOListFromModelList(List<Tavolo> modelListInput) {
		return modelListInput.stream().map(tavoloEntity -> {
			return TavoloDTO.buildTavoloDTOFromModel(tavoloEntity);
		}).collect(Collectors.toList());
	}


}
