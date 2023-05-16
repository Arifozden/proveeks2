package com.example.proveeks2;

import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BrukerController {
    @Autowired
    private JdbcTemplate db;
    private Logger logger = LoggerFactory.
            getLogger(BrukerController.class);
    @Autowired
    private HttpSession session;
    if(etPassord >0){
        session.setAttribute("Logget inn",bruker);
        return true;}

    @PostMapping("/lagre")
    public void lagreBruker(Bruker bruker) {
        String regex1 = "[a-zøåA-ZÆØÅ0-9 .\\-]{2,30}";
        String regex2 = "[a-øæååA-ZÆØÅ0-9]{6,10}";
        boolean OK1 = bruker.getBrukernavn().matches(regex1);
        boolean OK2 =bruker.getPassord().matches(regex2);
        if(OK1 && OK2){
            String sql = "INSERT INTO Bruker(brukernavn,passord)" +
                    " VALUES(?,?)";
            try{
                db.update(sql,bruker.getBrukernavn(),bruker.getPassord());
            }
            catch(Exception e){
                logger.error("Feil i lagre brukeren "+e);
            }
        }
        else{
            logger.error("Feil i regEx i meldingen:" +
                    bruker.getBrukernavn()+" : "+bruker.getPassord());
        }
    }

    @GetMapping("/hentAlleBrukernavn")
    public List<String> hentAlleBrukernavn() {
        String sql = "SELECT brukernavn FROM Bruker";
        try{
            List<String> retur = db.queryForList(sql,String.class);
            return retur;
        }
        catch(Exception e){
            logger.error("Feil i hent alle brukernavn "+e);
            return null;
        }
    }

    @GetMapping("/logginn")
    public boolean logginn(Bruker bruker) {
        String sql = "SELECT count(*) FROM Bruker WHERE passord = ?";
        int etPassord = db.queryForObject(sql,Integer.class,bruker.getPassord());
        if(etPassord >0){
            session.setAttribute("Logget inn",bruker);
            return true;
        }
        return false;
    }


}
