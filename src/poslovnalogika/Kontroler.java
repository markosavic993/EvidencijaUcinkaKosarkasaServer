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
import domen.UcinakKosarkasa;
import domen.Utakmica;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import so.OpstaSO;
import so.korisnik.UlogujSeSO;
import so.korisnik.DodajKorisnikaSo;
import so.korisnik.VratiSveKorisnikeSO;
import so.kosarkas.AzurirajKosarkaseNakonBrisanjaTimaSO;
import so.kosarkas.SacuvajKosarkasaSO;
import so.kosarkas.IzmeniKosarkasaSO;
import so.kosarkas.ObrisiKosarkasaSO;
import so.kosarkas.PretraziKosarkaseSO;
import so.kosarkas.VratiListuKosarkasaSO;
import so.tim.SacuvajTimSO;
import so.tim.IzmeniTimSO;
import so.tim.ObrisiTimSO;
import so.tim.VratiIDTimaSO;
import so.tim.VratiListuTimovaSO;
import so.tim.PretraziTimoveSO;
import so.tip_ucinka.DodajTipUcinkaSO;
import so.tip_ucinka.ObrisiTipUcinkaSO;
import so.tip_ucinka.VratiListuTipovaUcinakaSO;
import so.ucinak.SacuvajUcinakNaUtakmiciSO;
import so.ucinak.VratiUcinkeSaUtakmiceSO;
import so.utakmica.AzurirajUtakmicuSO;
import so.utakmica.DodajUtakmicuSO;
import so.utakmica.VratiIDUtakmiceSO;
import so.utakmica.VratiPoeneDomacinaIGostaSO;
import so.utakmica.VratiListuUtakmicaSO;

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

        OpstaSO so = new VratiListuTimovaSO();
        so.izvrsiOperaciju(new Tim());
        listaTimova = (List<Tim>) so.getObjekat();

        return listaTimova;
    }

    public List<Tim> vratiListuTimovaPremaKriterijumu(String kriterijum) throws Exception {
        List<Tim> listaTimova = null;

        OpstaSO so = new PretraziTimoveSO();
        so.izvrsiOperaciju(new Tim(kriterijum));
        listaTimova = (List<Tim>) so.getObjekat();

        return listaTimova;
    }
    
    public int vratiBrojTimova() throws Exception {
        int brojTimova = 0;
        OpstaSO so = new VratiIDTimaSO();
        so.izvrsiOperaciju(new Tim());
        brojTimova = (int) so.getObjekat();

        return brojTimova;
    }

    public void sacuvajTim(Tim t) throws Exception {
        OpstaSO so = new SacuvajTimSO();
        so.izvrsiOperaciju(t);
    }

    public void sacuvajKosarkasa(Kosarkas k) throws Exception {
        OpstaSO so = new SacuvajKosarkasaSO();
        so.izvrsiOperaciju(k);
    }

    public List<Kosarkas> vratiListuKosarkasa() throws Exception {
        List<Kosarkas> listaKosarkasa = null;
        OpstaSO so = new VratiListuKosarkasaSO();
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
        
        OpstaSO so = new VratiListuUtakmicaSO();
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
        OpstaSO so = new VratiListuTipovaUcinakaSO();
        so.izvrsiOperaciju(new TipUcinka());
        listaTipova = (List<TipUcinka>) so.getObjekat();
        
        return listaTipova;
    }
    
    public List<Kosarkas> vratiKosarkaseIzJednogTima(Tim t) throws Exception {
        List<Kosarkas> lista = new ArrayList<>();
        OpstaSO so = new PretraziKosarkaseSO();
        so.izvrsiOperaciju(t); 
        lista = (List<Kosarkas>) so.getObjekat();
        
        return lista;
    }
    
   public void sacuvajListuUcinaka(List<UcinakKosarkasa> lista) throws Exception {
       Utakmica u = lista.get(0).getUtakmica();
        try {
            //broker.uspostaviKonekciju();
            OpstaSO so = new VratiPoeneDomacinaIGostaSO();
            so.izvrsiOperaciju(u);
            Utakmica utakmica =  (Utakmica) so.getObjekat();
            int brojPoenaDomacin = utakmica.getPoeniDomacin();
            int brojPoenaGost = utakmica.getPoeniGost();
            for (UcinakKosarkasa ucinak : lista) {
                if(ucinak.getTipUcinka().getNaziv().equals("Poeni") && 
                        ucinak.getKosarkas().getTimZaKojiNastupa().equals(ucinak.getUtakmica().getDomacin())) {
                    brojPoenaDomacin+=ucinak.getVrednost();
                }
                
                if(ucinak.getTipUcinka().getNaziv().equals("Poeni") && 
                        ucinak.getKosarkas().getTimZaKojiNastupa().equals(ucinak.getUtakmica().getGost())) {
                    brojPoenaGost+=ucinak.getVrednost();
                }
                u = ucinak.getUtakmica();
//                broker.ubaciNoviUcinak(ucinak);
//                broker.potvrdiTransakciju();
                OpstaSO sisOp = new SacuvajUcinakNaUtakmiciSO();
                sisOp.izvrsiOperaciju(ucinak);
            }
            u.setPoeniDomacin(brojPoenaDomacin);
            u.setPoeniGost(brojPoenaGost);
            OpstaSO operacija = new AzurirajUtakmicuSO();
            operacija.izvrsiOperaciju(u);
//            broker.azurirajRezultatUtakmice(u, brojPoenaDomacin, brojPoenaGost);
//            broker.potvrdiTransakciju();
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
   }
   
   public List<UcinakKosarkasa> vratiUcinkeOdredjenogIgracaNaUtakmici(Kosarkas k, Utakmica u) throws Exception { 
        
       List<UcinakKosarkasa> listaSvih = new ArrayList<>();
       List<UcinakKosarkasa> listaJednogIgraca = new ArrayList<>();
       
        try {
            //broker.uspostaviKonekciju();
            OpstaSO so = new VratiUcinkeSaUtakmiceSO();
            so.izvrsiOperaciju(u);
            listaSvih = (List<UcinakKosarkasa>) so.getObjekat();
            for (UcinakKosarkasa ucinak : listaSvih) {
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
        OpstaSO so = new UlogujSeSO();
        so.izvrsiOperaciju(k);
   }
    
}
