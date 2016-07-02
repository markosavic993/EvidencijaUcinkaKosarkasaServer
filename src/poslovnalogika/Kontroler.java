/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poslovnalogika;

import bp.BrokerBazePodataka;
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

    public List<Korisnik> vratiSveKorisnike() {
        List<Korisnik> listaKorisnika = null;
        try {
            broker.uspostaviKonekciju();
            listaKorisnika = broker.vratiSveKorisnike();
            broker.raskiniKonekciju();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listaKorisnika;
    }

    public boolean autorizujKorisnika(String username, String password) {
        Korisnik k = new Korisnik(username, password);
        List<Korisnik> listaKorisnika = vratiSveKorisnike();
        for (Korisnik korisnik : listaKorisnika) {
            if (k.equals(korisnik)) {
                return true;
            }
        }
        return false;
    }
    
    public Korisnik vratiKorisnika(String username, String password) {
        Korisnik k = new Korisnik(username, password);
        List<Korisnik> listaKorisnika = vratiSveKorisnike();
        for (Korisnik korisnik : listaKorisnika) {
            if (k.equals(korisnik)) {
                return korisnik;
            }
        }
        return null;
    }

    public List<Tim> vratiListuTimova() {
        List<Tim> listaTimova = null;

        try {
            broker.uspostaviKonekciju();
            listaTimova = broker.vratiSveTimove();
            broker.raskiniKonekciju();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listaTimova;
    }

    public List<Tim> vratiListuTimovaPremaKriterijumu(String kriterijum) {
        List<Tim> listaTimova = null;

        try {
            broker.uspostaviKonekciju();
            listaTimova = broker.vratiTimovePremaKriterijumu(kriterijum);
            broker.raskiniKonekciju();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listaTimova;
    }
    
    public int vratiBrojTimova() {
        int brojTimova = 0;
        try {
            broker.uspostaviKonekciju();
            brojTimova = broker.vratiBrojTimova();
            broker.raskiniKonekciju();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        }

        return brojTimova;
    }

    public void sacuvajTim(Tim t) {
        try {
            broker.uspostaviKonekciju();
            broker.sacuvajTim(t);
            broker.potvrdiTransakciju();
            broker.raskiniKonekciju();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sacuvajKosarkasa(Kosarkas k) {
        try {
            broker.uspostaviKonekciju();
            broker.sacuvajKosarkasa(k);
            broker.potvrdiTransakciju();
            broker.raskiniKonekciju();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
           
        }
    }

    public List<Kosarkas> vratiListuKosarkasa() {
        List<Kosarkas> listaKosarkasa = null;
        try {
            broker.uspostaviKonekciju();
            listaKosarkasa = broker.vratiSveKosarkase();
            broker.raskiniKonekciju();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listaKosarkasa;
    }

    public void izbrisiKosarkasa(Kosarkas k) {
        try {
            broker.uspostaviKonekciju();
            broker.obrisiIzabranogKosarkasa(k);
            broker.potvrdiTransakciju();
            broker.raskiniKonekciju();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void dodajNovuUtakmicu(Utakmica u) {
        try {
            broker.uspostaviKonekciju();
            broker.dodajUtakmicu(u);
            broker.potvrdiTransakciju();
            broker.raskiniKonekciju();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public void azurirajKosarkasa(Kosarkas k) {
        try {
            broker.uspostaviKonekciju();
            broker.azurirajKosarkasa(k);
            broker.potvrdiTransakciju();
            broker.raskiniKonekciju();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public int vratiIDUtakmice() {
        int id = 0;
        try {
            broker.uspostaviKonekciju();
            id = broker.vratiBrojUtakmica();
            broker.raskiniKonekciju();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }
    
    public List<Utakmica> vratiUtakmice() {
        List<Utakmica> listaUtakmica = new ArrayList<>();
        
        try {
            broker.uspostaviKonekciju();
            listaUtakmica = broker.vratiUtakmice();
            broker.raskiniKonekciju();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return listaUtakmica;
    }
    
    public void izbrisiTim(Tim t) {
        try {
            broker.uspostaviKonekciju();
            broker.azurirajIgraceNakonBrisanjaTima(t);
            broker.obrisiIzabraniTim(t);
            broker.potvrdiTransakciju();
            broker.raskiniKonekciju();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
    
    public void azurirajTim(Tim t) {
        try {
            broker.uspostaviKonekciju();
            broker.azurirajTim(t);
            broker.potvrdiTransakciju();
            broker.raskiniKonekciju();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List<TipUcinka> vratiTipoveUcinaka() {
        List<TipUcinka> listaTipova = new ArrayList<>();
        
        try {
            broker.uspostaviKonekciju();
            listaTipova = broker.vratiTipoveUcinaka();
            broker.raskiniKonekciju();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return listaTipova;
    }
    
    public List<Kosarkas> vratiKosarkaseIzJednogTima(Tim t) {
        List<Kosarkas> lista = new ArrayList<>();
        
        try {
            broker.uspostaviKonekciju();
            lista = broker.vratiKosarkaseIzOdredjenogTima(t);
            broker.raskiniKonekciju();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
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
   
   public List<Ucinak> vratiUcinkeOdredjenogIgracaNaUtakmici(Kosarkas k, Utakmica u) { 
        
       List<Ucinak> listaSvih = new ArrayList<>();
       List<Ucinak> listaJednogIgraca = new ArrayList<>();
       
        try {
            broker.uspostaviKonekciju();
            listaSvih = broker.vratiSveUcinkeSaUtakmice(u);
            for (Ucinak ucinak : listaSvih) {
                if(ucinak.getKosarkas().equals(k)) {
                    listaJednogIgraca.add(ucinak);
                }
            }
            broker.raskiniKonekciju();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        }
           
        return listaJednogIgraca;
    }
   
   public void dodajNoviTipUcinka(TipUcinka t) {
        try {
            broker.uspostaviKonekciju();
            broker.dodajNoviTipUcinaka(t);
            broker.potvrdiTransakciju();
            broker.raskiniKonekciju();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        }
   }
   
   public void izbrisiTipUcinka(TipUcinka tip) {
        try {
            broker.uspostaviKonekciju();
            broker.obrisiTipUcinka(tip);
            broker.potvrdiTransakciju();
            broker.raskiniKonekciju();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
   public void dodajKorisnika(Korisnik k) {
        try {
            broker.uspostaviKonekciju();
            broker.dodajKorisnika(k);
            broker.potvrdiTransakciju();
            broker.raskiniKonekciju();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        }
   }
   
   public void promeniLozinkuKorisnika(Korisnik k) {
        try {
            broker.uspostaviKonekciju();
            broker.azurirajLozinku(k);
            broker.potvrdiTransakciju();
            broker.raskiniKonekciju();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        }
   }
    
}
