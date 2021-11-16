package it.prova.pokeronline.repository.tavolo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.StringUtils;

import it.prova.pokeronline.dto.TavoloDTO;
import it.prova.pokeronline.model.Tavolo;

public class CustomTavoloRepositoryImpl implements CustomTavoloRepository{

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<Tavolo> findByExample(Tavolo example) {
		Map<String, Object> paramaterMap = new HashMap<String, Object>();
		List<String> whereClauses = new ArrayList<String>();

		StringBuilder queryBuilder = new StringBuilder("select t from Tavolo t where t.id = t.id ");

		if (StringUtils.isNotEmpty(example.getDenominazione())) {
			whereClauses.add("t.denominazione  like :denominazione ");
			paramaterMap.put("denominazione", "%" + example.getDenominazione() + "%");
		}
		if (example.getDataCreazione() != null) {
			whereClauses.add("t.dataCreazione >= :dataCreazione ");
			paramaterMap.put("dataCreazione", example.getDataCreazione());
		}
		if (example.getEsperienzaMin() != null) {
			whereClauses.add("t.esperienzaMin >= :esperienzaMin ");
			paramaterMap.put("esperienzaMin", example.getEsperienzaMin());
		}
		if (example.getCifraMin() != null) {
			whereClauses.add("t.cifraMin >= :cifraMin ");
			paramaterMap.put("cifraMin" , example.getCifraMin());
		}
		
		queryBuilder.append(!whereClauses.isEmpty()?" and ":"");
		queryBuilder.append(StringUtils.join(whereClauses, " and "));
		TypedQuery<Tavolo> typedQuery = entityManager.createQuery(queryBuilder.toString(), Tavolo.class);

		for (String key : paramaterMap.keySet()) {
			typedQuery.setParameter(key, paramaterMap.get(key));
		}

		return typedQuery.getResultList();
	}

	@Override
	public List<Tavolo> findByExampleConCreatore(TavoloDTO example) {

		Map<String, Object> paramaterMap = new HashMap<String, Object>();
		List<String> whereClauses = new ArrayList<String>();

		if(example.getEsperienzaMin() == null)
			example.setEsperienzaMin(0);
		
		if(example.getCifraMin() == null)
			example.setCifraMin(0);

		StringBuilder queryBuilder = new StringBuilder("select r from Tavolo r join fetch r.utenteCreatore uc where r.id = r.id");

		if (StringUtils.isNotEmpty(example.getDenominazione())) {
			whereClauses.add(" r.denominazione  like :denominazione ");
			paramaterMap.put("denominazione", "%" + example.getDenominazione() + "%");
		}
		if (example.getDataCreazione() != null) {
			whereClauses.add(" r.dataCreazione >= :dataCreazione ");
			paramaterMap.put("dataCreazione", example.getDataCreazione());
		}
		if (example.getEsperienzaMin() >= 0) {
			whereClauses.add(" r.esperienzaMin >= :esperienzaMin ");
			paramaterMap.put("esperienzaMin", example.getEsperienzaMin());
		}
		if (example.getCifraMin() >= 0) {
			whereClauses.add(" r.cifraMin >= :cifraMin ");
			paramaterMap.put("cifraMin", example.getCifraMin());
		}
		if (example.getUtenteCreatore() != null) {
			whereClauses.add(" uc.id = :idUtenteCreatore ");
			paramaterMap.put("idUtenteCreatore", example.getUtenteCreatore());
		}
		
		queryBuilder.append(!whereClauses.isEmpty()?" and ":"");
		queryBuilder.append(StringUtils.join(whereClauses, " and "));
		TypedQuery<Tavolo> typedQuery = entityManager.createQuery(queryBuilder.toString(), Tavolo.class);

		for (String key : paramaterMap.keySet()) {
			typedQuery.setParameter(key, paramaterMap.get(key));
		}

		return typedQuery.getResultList();
	}
	
	@Override
	public List<Tavolo> findByExampleMieiTavoli(TavoloDTO example, Long id) {
		
		Map<String, Object> paramaterMap = new HashMap<String, Object>();
		List<String> whereClauses = new ArrayList<String>();
		
		if(example.getEsperienzaMin() == null)
			example.setEsperienzaMin(0);
		
		if(example.getCifraMin() == null)
			example.setCifraMin(0);

		StringBuilder queryBuilder = new StringBuilder("select distinct r from Tavolo r join fetch r.utenteCreatore uc where r.id = r.id");

		if (StringUtils.isNotEmpty(example.getDenominazione())) {
			whereClauses.add(" r.denominazione  like :denominazione ");
			paramaterMap.put("denominazione", "%" + example.getDenominazione() + "%");
		}
		if (example.getDataCreazione() != null) {
			whereClauses.add(" r.dataCreazione >= :dataCreazione ");
			paramaterMap.put("dataCreazione", example.getDataCreazione());
		}
		if (example.getEsperienzaMin() >= 0) {
			whereClauses.add(" r.esperienzaMin >= :esperienzaMin ");
			paramaterMap.put("esperienzaMin", example.getEsperienzaMin());
		}
		if (example.getCifraMin() >= 0) {
			whereClauses.add(" r.cifraMin >= :cifraMin ");
			paramaterMap.put("cifraMin", example.getCifraMin());
		}
		
		queryBuilder.append(!whereClauses.isEmpty()?" and ":"");
		queryBuilder.append(StringUtils.join(whereClauses, " and "));
		queryBuilder.append(" and uc.id = " + id);
		TypedQuery<Tavolo> typedQuery = entityManager.createQuery(queryBuilder.toString(), Tavolo.class);

		for (String key : paramaterMap.keySet()) {
			typedQuery.setParameter(key, paramaterMap.get(key));
		}

		return typedQuery.getResultList();
	}

}
