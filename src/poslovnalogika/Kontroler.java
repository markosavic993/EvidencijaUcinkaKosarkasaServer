/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poslovnalogika;

import bp.BrokerBazePodataka;
import bp.DBBroker;
import domen.DomenskiObjekat;
import domen.Korisnik;
import domen.Kosarkas;
import domen.Tim;
import domen.TipUcinka;
import domen.Ucinak;
import domen.Utakmica;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import so.OpstaSO;
import so.korisnik.AzurirajKorisnikaSO;
import so.korisnik.DodajKorisnikaSo;
import so.korisnik.VratiSveKorisnikeSO;
import so.kosarkas.AzurirajKosarkaseNakonBrisanjaTimaSO;
import so.kosarkas.DodajKosarkasaSO;
import so.kosarkas.IzmeniKosarkasaSO;
import so.kosarkas.ObrisiKosarkasaSO;
import so.kosarkas.VratiKosarkasePremaKriterijumuSO;
import so.kosarkas.VratiKosarkaseSO;
import so.tim.DodajTimSO;
import so.tim.IzmeniTimSO;
import so.tim.ObrisiTimSO;
import so.tim.VratiIDTimaSO;
import so.tim.VratiSveTimoveSO;
import so.tim.VratiTimovePremaKriterijumuSO;
import so.tip_ucinka.DodajTipUcinkaSO;
import so.tip_ucinka.ObrisiTipUcinkaSO;
import so.tip_ucinka.VratiTipoveUcinakaSO;
import so.ucinak.VratiUcinkeSaUtakmiceSO;
import so.utakmica.DodajUtakmicuSO;
import so.utakmica.VratiIDUtakmiceSO;
import so.utakmica.VratiUtakmiceSO;

/**
 *
 * @author Marko
 */
public class Kontroler {

    private BrokerBazePodataka broker;
    private static Kontroler instanca;
    //FrmKosarkasUnos formaZaUnosKosarkasa;

    private Kontroler() {
        broker = new BrokerBazePodataka();
    }

    public static Kontroler getInstance() {
        if (instanca == null) {
            instanca = new Kontroler();
        }

        return instanca;
    }

    public List<Korisnik> vratiSveKorisnike() throws Exception {
        List<Korisnik> lk = null;
        OpstaSO so = new VratiSveKorisnikeSO();
        so.izvrsiOperaciju(new Korisnik());
        lk = (List<Korisnik>) so.getObjekat();
        return lk;
    }

    public boolean autorizujKorisnika(String username, String password) throws Exception {
        Korisnik k = new Korisnik(username, password);
        List<Korisnik> listaKorisnika = vratiSveKorisnike();
        for (Korisnik korisnik : listaKorisnika) {
            if (k.equals(korisnik)) {
                return true;
            }
        }
        return false;
    }
    
    public Korisnik vratiKorisnika(String username, String password) throws Exception {
        Korisnik k = new Korisnik(username, password);
        List<Korisnik> listaKorisnika = vratiSveKorisnike();
        for (Korisnik korisnik : listaKorisnika) {
            if (k.equals(korisnik)) {
                return korisnik;
            }
        }
        return null;
    }

    public List<Tim> vratiListuTimova() throws Exception {
        List<Tim> listaTimova = null;

        OpstaSO so = new VratiSveTimoveSO();
        so.izvrsiOperaciju(new Tim());
        listaTimova = (List<Tim>) so.getObjekat();

        return listaTimova;
    }

    public List<Tim> vratiListuTimovaPremaKriterijumu(String kriterijum) throws Exception {
        List<Tim> listaTimova = null;

        OpstaSO so = new VratiTimovePremaKriterijumuSO();
        so.izvrsiOperaciju(new Tim(kriterijum));
        listaTimova = (List<Tim>) so.getObjekat();

        return listaTimova;
    }
    
    public int vratiBrojTimova() throws Exception {
        int brojTimova = 0;
        OpstaSO so = new VratiIDTimaSO();
        so.izvrsiOperaciju(new Tim());
        System.out.println("*************" + (int)so.getObjekat());
        brojTimova = (int) so.getObjekat();

        return brojTimova;
    }

    public void sacuvajTim(Tim t) throws Exception {
        OpstaSO so = new DodajTimSO();
        so.izvrsiOperaciju(t);
    }

    public void sacuvajKosarkasa(Kosarkas k) throws Exception {
        OpstaSO so = new DodajKosarkasaSO();
        so.izvrsiOperaciju(k);
    }

    public List<Kosarkas> vratiListuKosarkasa() throws Exception {
        List<Kosarkas> listaKosarkasa = null;
        OpstaSO so = new VratiKosarkaseSO();
        so.izvrsiOperaciju(new Kosarkas());
        listaKosarkasa = (List<Kosarkas>) so.getObjekat();
        return listaKosarkasa;
    }

    public void izbrisiKosarkasa(Kosarkas k) throws Exception {
        OpstaSO so = new ObrisiKosarkasaSO();
        so.izvrsiOperaciju(k);
    }

    public void dodajNovuUtakmicu(Utakmica u) throws Exception {
        OpstaSO so = new DodajUtakmicuSO();
        so.izvrsiOperaciju(u);
    }
    
    public void azurirajKosarkasa(Kosarkas k) throws Exception {
       OpstaSO so = new IzmeniKosarkasaSO();
       so.izvrsiOperaciju(k);
    }
    
    public int vratiIDUtakmice() throws Exception {
        int id = 0;
        OpstaSO so = new VratiIDUtakmiceSO();
        so.izvrsiOperaciju(new Utakmica());
        id = (int) so.getObjekat();
        return id;
    }
    
    public List<Utakmica> vratiUtakmice() throws Exception {
        List<Utakmica> listaUtakmica = new ArrayList<>();
        
        OpstaSO so = new VratiUtakmiceSO();
        so.izvrsiOperaciju(new Utakmica());
        listaUtakmica = (List<Utakmica>) so.getObjekat();
        
        return listaUtakmica;
    }
    
    public void izbrisiTim(Tim t) throws Exception {
        OpstaSO sop = new AzurirajKosarkaseNakonBrisanjaTimaSO();
        sop.izvrsiOperaciju(t);
        OpstaSO so = new ObrisiTimSO();
        so.izvrsiOperaciju(t);
        
    } 
    
    public void azurirajTim(Tim t) throws Exception {
        OpstaSO so = new IzmeniTimSO();
        so.izvrsiOperaciju(t); 
    }
    
    public List<TipUcinka> vratiTipoveUcinaka() throws Exception {
        List<TipUcinka> listaTipova = new ArrayList<>();
        OpstaSO so = new VratiTipoveUcinakaSO();
        so.izvrsiOperaciju(new TipUcinka());
        listaTipova = (List<TipUcinka>) so.getObjekat();
        
        return listaTipova;
    }
    
    public List<Kosarkas> vratiKosarkaseIzJednogTima(Tim t) throws Exception {
        List<Kosarkas> lista = new ArrayList<>();
        OpstaSO so = new VratiKosarkasePremaKriterijumuSO();
        so.izvrsiOperaciju(t); 
        lista = (List<Kosarkas>) so.getObjekat();
        
        return lista;
    }
    
   public void sacuvajListuUcinaka(List<Ucinak> lista) {
       Utakmica u = lista.get(0).getUtakmica();
        try {
            broker.uspostaviKonekciju();
            int brojPoenaDomacin = broker.vratiBrojPoenaDomacinaIGosta(u)[0];
            int brojPoenaGost = broker.vratiBrojPoenaDomacinaIGosta(u)[1];
            for (Ucinak ucinak : lista) {
                if(ucinak.getTipUcinka().getNaziv().equals("Poeni") && 
                        ucinak.getKosarkas().getTimZaKojiNastupa().equals(ucinak.getUtakmica().getDomacin())) {
                    brojPoenaDomacin+=ucinak.getVrednost();
                }
                
                if(ucinak.getTipUcinka().getNaziv().equals("Poeni") && 
                        ucinak.getKosarkas().getTimZaKojiNastupa().equals(ucinak.getUtakmica().getGost())) {
                    brojPoenaGost+=ucinak.getVrednost();
                }
                u = ucinak.getUtakmica();
                broker.ubaciNoviUcinak(ucinak);
                broker.potvrdiTransakciju();
            }
            
            broker.azurirajRezultatUtakmice(u, brojPoenaDomacin, brojPoenaGost);
            broker.potvrdiTransakciju();
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
   }
   
   public List<Ucinak> vratiUcinkeOdredjenogIgracaNaUtakmici(Kosarkas k, Utakmica u) throws Exception { 
        
       List<Ucinak> listaSvih = new ArrayList<>();
       List<Ucinak> listaJednogIgraca = new ArrayList<>();
       
        try {
            //broker.uspostaviKonekciju();
            OpstaSO so = new VratiUcinkeSaUtakmiceSO();
            so.izvrsiOperaciju(u);
            listaSvih = (List<Ucinak>) so.getObjekat();
            for (Ucinak ucinak : listaSvih) {
                if(ucinak.getKosarkas().equals(k)) {
                    listaJednogIgraca.add(ucinak);
                }
            }
            //broker.raskiniKonekciju();
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        }
           
        return listaJednogIgraca;
    }
   
   public void dodajNoviTipUcinka(TipUcinka t) throws Exception {
        OpstaSO so = new DodajTipUcinkaSO();
        so.izvrsiOperaciju(t);
   }
   
   public void izbrisiTipUcinka(TipUcinka tip) throws Exception {
        OpstaSO so = new ObrisiTipUcinkaSO();
        so.izvrsiOperaciju(tip);
    }
   
   public void dodajKorisnika(Korisnik k) throws Exception {
        OpstaSO so = new DodajKorisnikaSo();
        so.izvrsiOperaciju(k);
   }
   
   public void promeniLozinkuKorisnika(Korisnik k) throws Exception {
        OpstaSO so = new AzurirajKorisnikaSO();
        so.izvrsiOperaciju(k);
   }
    
}
