package it.prova.pokeronline;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import it.prova.pokeronline.model.Ruolo;
import it.prova.pokeronline.model.StatoUtente;
import it.prova.pokeronline.model.Tavolo;
import it.prova.pokeronline.model.Utente;
import it.prova.pokeronline.service.RuoloService;
import it.prova.pokeronline.service.TavoloService;
import it.prova.pokeronline.service.UtenteService;

@SpringBootApplication
public class PokeronlineApplication implements CommandLineRunner {

	@Autowired
	private RuoloService ruoloServiceInstance;
	@Autowired
	private UtenteService utenteServiceInstance;
	@Autowired
	private TavoloService tavoloServiceInstance;

	public static void main(String[] args) {
		SpringApplication.run(PokeronlineApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		if (ruoloServiceInstance.cercaPerDescrizioneECodice("Administrator", "ROLE_ADMIN") == null) {
			ruoloServiceInstance.inserisciNuovo(new Ruolo("Administrator", "ROLE_ADMIN"));
		}
		if (ruoloServiceInstance.cercaPerDescrizioneECodice("Classic User", "ROLE_PLAYER") == null) {
			ruoloServiceInstance.inserisciNuovo(new Ruolo("Classic User", "ROLE_PLAYER"));
		}
		if (ruoloServiceInstance.cercaPerDescrizioneECodice("Special User", "ROLE_SPECIAL_PLAYER") == null) {
			ruoloServiceInstance.inserisciNuovo(new Ruolo("Special User", "ROLE_SPECIAL_PLAYER"));
		}
		if (tavoloServiceInstance.cercaPerDenominazione("Tavolo iniziale") == null) {
			Tavolo tavolo = new Tavolo("Tavolo iniziale", new Date(), 2, 2, "peppe");
			tavoloServiceInstance.inserisciNuovo(tavolo);

			// A DIFFERENZA DEGLI ALTRI PROGETTI CERCO SOLO PER USERNAME PERCHE' SE VADO
			// ANCHE PER
			// PASSWORD OGNI VOLTA NE INSERISCE UNO NUOVO
			if (utenteServiceInstance.findByUsername("admin") == null) {
				Utente admin = new Utente("admin", "admin", "Mario", "Rossi", new Date(), 1, 2);
				admin.setStato(StatoUtente.ATTIVO);
				admin.setTavolo(tavolo);
				admin.getRuoli().add(ruoloServiceInstance.cercaPerDescrizioneECodice("Administrator", "ROLE_ADMIN"));
				utenteServiceInstance.inserisciNuovo(admin);
			}

			if (utenteServiceInstance.findByUsername("user") == null) {
				Utente classicUser = new Utente("user", "user", "Antonio", "Verdi", new Date(), 1, 1);
				classicUser.setStato(StatoUtente.ATTIVO);
				classicUser.setTavolo(tavolo);
				classicUser.getRuoli()
						.add(ruoloServiceInstance.cercaPerDescrizioneECodice("Classic User", "ROLE_PLAYER"));
				utenteServiceInstance.inserisciNuovo(classicUser);
			}

			if (utenteServiceInstance.findByUsername("user1") == null) {
				Utente classicUser1 = new Utente("user1", "user1", "Antonioo", "Verdii", new Date(), 1, 1);
				classicUser1.setStato(StatoUtente.ATTIVO);
				classicUser1.setTavolo(tavolo);
				classicUser1.getRuoli()
						.add(ruoloServiceInstance.cercaPerDescrizioneECodice("Classic User", "ROLE_PLAYER"));
				utenteServiceInstance.inserisciNuovo(classicUser1);
			}

			if (utenteServiceInstance.findByUsername("user2") == null) {
				Utente specialUser = new Utente("special", "special", "Antoniooo", "Verdiii", new Date(), 1, 2);
				specialUser.setStato(StatoUtente.ATTIVO);
				specialUser.setTavolo(tavolo);
				specialUser.getRuoli()
						.add(ruoloServiceInstance.cercaPerDescrizioneECodice("Special User", "ROLE_SPECIAL_PLAYER"));
				utenteServiceInstance.inserisciNuovo(specialUser);
			}

		}
	}

}
