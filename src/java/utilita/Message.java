package utilita;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author moira
 */
public class Message {

    private final String code;
    private final boolean error;
    private final static Map<String, String> map;
    private final String msg;

    public String getMsg() {
        return this.msg;
    }

    public String getCode() {
        return this.code;
    }

    public Boolean isError() {
        return this.error;
    }

    public Message(String code, boolean error) {
        this.code = code;
        this.error = error;
        this.msg = map.get(this.code);
    }

    public String toJSON() {
        String error_string = this.error ? "true" : "false";
        return "{\"message\":\"" + this.msg + "\", \"error\":\"" + error_string + "\"}";
    }

    static {

        map = new HashMap<>();
        //errore generico
        map.put("err", "Si è rilevato un errore");
        map.put("null", "nessun utente trovato");
        //errore User

        map.put("usr_0", "Inserisci tutti i campi");
        map.put("usr_1", "Utente già esistente");
        map.put("usr_2", "Utente non esistente");
        map.put("usr_3", "Username o password errata");
        map.put("usr_4", "Utente loggato");
        //errore Nome
        map.put("name_1", "Il nome deve essere alfanumerico");
        map.put("name_2", "Il nome è corto");
        map.put("name_3", "Il nome è lungo");
        //errore Cognome
        map.put("surname_1", "Il nome deve essere alfanumerico");
        map.put("surname_2", "Il nome è corto");
        map.put("surname_3", "Il nome è lungo");
        //errore Data di Nascita
        map.put("date_1", "Formato non valido");
        map.put("date_2", "Data non valida");
        map.put("dt_ok", "Data modificata");
        //errore Città
        map.put("plc", "La città deve essere alfanumerica");
        //errore Sesso
        map.put("gnd", "Puoi scegliere solo maschio o femmina");
        //errore Email
        map.put("eml_1", "L'e-mail corrente non è valida");
        map.put("eml_2", "L'e-mail confermata non è valida");
        map.put("eml_3", "L'e-mail non è valida");
        map.put("eml_ok", "L'e-mail è stata modificata");
        //errore Password
        map.put("psw", "Password errata");
        map.put("psd_1", "La password non è valida");
        map.put("psd_2", "La password confermata non è valida");
        map.put("psd_3", "La password deve contenere almeno 4 caratteri");
        map.put("psd_4", "La password deve essere alfanumerica");
        map.put("psd_ok", "La password è stata modificata");
        map.put("psd_5", "inserisci la password");
        /* FOTO */
        map.put("pho_slt", "Per favore, Seleziona una foto");
        map.put("pho_ok", "Foto caricata con successo");
        map.put("pho_err", "Caricamento foto fallito");
        /* REQUEST */
        map.put("snd", "Richiesta inviata");
        map.put("acc", "Richiesta accettata");
        map.put("dec", "Richiest cancellata");
        /* OTHER */
        map.put("srv", "Errore, per favore riprovare");
        map.put("tmp", "File corrottii");
        map.put("alp", "Per favore, non inserire numeri");
        map.put("log", "Per favore, effettua il login per vedere questa pagina");
        map.put("fld", "Tutti i campi sono richiesti");
        map.put("inv", "Utente invitato");
        map.put("basic_add", "Parentela aggiunta al tuo albero");
        /* NotAllowedException */
        map.put("yourself", "Non Autorizzato:  Non puoi aggiungere te stesso");
        /* SPOUSE */
        map.put("sp_alr", "Non Autorizzato:  Hai gia uno/a sposo/a");
        map.put("sp_gen", "Non Autorizzato:  Non puoi avere lo stesso sesso del tuo partener");
        map.put("sp_your", "Non Autorizzato:  Questo utente è gia il tuo/a sposo/a");
        map.put("sp_sib", "Non Autorizzato:  Il/la tuo/a sposo/a non puo essere tuo fratello/ tua sorella");
        map.put("sp_anc", "Non Autorizzato:  Il/la tuo/a sposo/a non puo essere un tuo antenato");
        map.put("sp_off", "Non Autorizzato:  Il/la tuo/a sposo/a non puo essere tuo discendente");
        /* MOTHER */
        map.put("mot_alr", "Non Autorizzato:  Hai gia una madre");
        map.put("mot_your", "Non Autorizzato:  Questo utente è gia tua madre");
        map.put("mot_sib", "Non Autorizzato:  Tua Madre non può essere tuo fratello/ tua sorella");
        map.put("mot_anc", "Non Autorizzato:  Tua madre non puo essere un tuo antenato");
        map.put("mot_off", "Non Autorizzato:  Tua madre non puo essere un tuo discendete");
        /* FATHER */
        map.put("fat_alr", "Non Autorizzato:  Hai gia un padre");
        map.put("fat_your", "Non Autorizzato:  Questo utente è gia tuo padre");
        map.put("fat_sib", "Non Autorizzato:  Tuo padre non puo essere tuo fratello/ tua sorella");
        map.put("fat_anc", "Non Autorizzato:  Tuo padre non puo essere un tuo antenato");
        map.put("fat_off", "Non Autorizzato:  Tuo padre non puo essere un tuo discendente");
        /* CHILD */
        map.put("ch_mot_alr", "Non Autorizzato:  Questo utente ha gia una madre");
        map.put("ch_mot_your", "Non Autorizzato:  Questo utente è gia tuo figlio");
        map.put("ch_mot_sib", "Non Autorizzato:  Tuo/a figlio/a non puo essere tuo fratello/ tua sorella");
        map.put("ch_mot_anc", "Non Autorizzato:  Tuo/a figlio/a non puo essere un tuo discendente");
        map.put("ch_mot_off", "Non Autorizzato:  Tuo/a figlio/a non puo essere un tuo antenato");
        map.put("ch_fat_alr", "Non Autorizzato:  Questo utente ha gia un padre");
        map.put("ch_fat_your", "Non Autorizzato:  Questo utente è gia tuo figlio");
        map.put("ch_fat_sib", "Non Autorizzato:  Tuo/a figlio/a non puo essere tuo fratello/ tua sorella");
        map.put("ch_fat_anc", "Non Autorizzato:  Tuo/a figlio/a non puo essere un tuo antenato");
        map.put("ch_fat_off", "Non Autorizzato:  Tuo/a figlio/a non puo essere un tuo discendente");
        /* SIBLING */

        map.put("sib_your", "Not allowed: this user already is your sibling");
        map.put("sib_1", "Not allowed: this user can't be add as sibling, because this relationship is not verificable");
        map.put("sib_2", "Not allowed: this user can't be your sibling, because you have different parents");
        map.put("sib_3", "Not allowed: this user can't be your sibling, beacause you can't have the same parents");
        map.put("sib_alt", "hai già un legame di parentela con tuo fratello");

        map.put("sib_your", "Non Autorizzato:  Questo utente è gia tuo/tua fratello/sorella");
        map.put("sib_1", "Non Autorizzato:  Questo utente non puo essere tuo/tua fratello/sorella, perche questa relazione non è verificabile");
        map.put("sib_2", "Non Autorizzato:  Questo utente non puo essere tuo/tua fratello/sorella, perche ha gia due genitori");
        map.put("sib_3", "Non Autorizzato:  Questo utente non puo essere tuo/tua fratello/sorella, perche non avete gli stessi genitori");

    }

}
