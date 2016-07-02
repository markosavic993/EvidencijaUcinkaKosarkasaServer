/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bp;

import domen.Korisnik;
import domen.Kosarkas;
import domen.Tim;
import domen.TipUcinka;
import domen.Ucinak;
import domen.Utakmica;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Marko
 */
public class BrokerBazePodataka {

    private Connection konekcija;

    public BrokerBazePodataka() {
    }

    public void uspostaviKonekciju() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/evidencija_ucinka_kosarkasa";
        String user = "root";
        String password = "";
        konekcija = DriverManager.getConnection(url, user, password);
        konekcija.setAutoCommit(false);
        // System.out.println("Uspesno uspostavljanje konekcije!");
    }

    public void raskiniKonekciju() throws SQLException {
        konekcija.close();
        // System.out.println("Uspesno raskidanje konekcije!");
    }

    public void potvrdiTransakciju() throws SQLException {
        konekcija.commit();
    }

    public void ponistiTransakciju() throws SQLException {
        konekcija.rollback();
    }

    public List<Korisnik> vratiSveKorisnike() {
        List<Korisnik> lk = new ArrayList<>();
        String upit = "SELECT * FROM korisnik";
        try {

            Statement s = konekcija.createStatement();
            ResultSet rs = s.executeQuery(upit);

            while (rs.next()) {
                lk.add(new Korisnik(rs.getString("username"), rs.getString("password"), rs.getString("mail")));
            }

            rs.close();
            s.close();

        } catch (SQLException ex) {
            Logger.getLogger(BrokerBazePodataka.class.getName()).log(Level.SEVERE, null, ex);
        }

        return lk;
    }

    public List<Tim> vratiSveTimove() {
        List<Tim> lt = new ArrayList<>();
        String upit = "SELECT * FROM tim ORDER BY naziv";

        try {
            Statement s = konekcija.createStatement();
            ResultSet rs = s.executeQuery(upit);

            while (rs.next()) {
                if (rs.getInt("sifratima") != 1) {
                    lt.add(new Tim(rs.getInt("sifratima"), rs.getString("naziv"), rs.getInt("godinaosnivanja"), rs.getString("grad"), rs.getString("hala")));
                }
            }

            rs.close();
            s.close();

        } catch (SQLException ex) {
            Logger.getLogger(BrokerBazePodataka.class.getName()).log(Level.SEVERE, null, ex);
        }

        return lt;
    }

    public List<Tim> vratiTimovePremaKriterijumu(String kriterijum) {
        List<Tim> lt = new ArrayList<>();
        String upit = "SELECT * FROM tim WHERE naziv = ?";

        try {
            PreparedStatement ps = konekcija.prepareStatement(upit);
            ps.setString(1, kriterijum);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                if (rs.getInt("sifratima") != 0) {
                    lt.add(new Tim(rs.getInt("sifratima"), rs.getString("naziv"), rs.getInt("godinaosnivanja"), rs.getString("grad"), rs.getString("hala")));
                }
            }

            rs.close();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(BrokerBazePodataka.class.getName()).log(Level.SEVERE, null, ex);
        }

//        upit = "SELECT * FROM tim WHERE grad = ?";
//        try {
//            PreparedStatement ps = konekcija.prepareStatement(upit);
//            ps.setString(1, kriterijum);
//            ResultSet rs = ps.executeQuery();
//
//            while (rs.next()) {
//                if (rs.getInt("sifratima") != 0) {
//                    Tim tim = new Tim(rs.getInt("sifratima"), rs.getString("naziv"), rs.getInt("godinaosnivanja"), rs.getString("grad"), rs.getString("hala"));
//                    if(!lt.contains(tim)) {
//                       lt.add(tim);
//                    }
//                }
//            }
//
//            rs.close();
//            ps.close();
//
//        } catch (SQLException ex) {
//            Logger.getLogger(BrokerBazePodataka.class.getName()).log(Level.SEVERE, null, ex);
//        }

        return lt;
    }

    public int vratiBrojUtakmica() {
        String upit = "SELECT MAX(id) FROM utakmica";
        int max = 0;

        try {
            Statement s = konekcija.createStatement();
            ResultSet rs = s.executeQuery(upit);
            while (rs.next()) {
                max = rs.getInt(1);
            }
            rs.close();
            s.close();
        } catch (SQLException ex) {
            Logger.getLogger(BrokerBazePodataka.class.getName()).log(Level.SEVERE, null, ex);
        }
        return max;
    }

    public int vratiBrojTimova() {
        String upit = "SELECT MAX(sifratima) FROM tim";
        int max = 0;
        try {
            Statement s = konekcija.createStatement();
            ResultSet rs = s.executeQuery(upit);

            while (rs.next()) {
                max = rs.getInt(1);
            }
            rs.close();
            s.close();

        } catch (SQLException ex) {
            Logger.getLogger(BrokerBazePodataka.class.getName()).log(Level.SEVERE, null, ex);
        }

        return max;
    }

    public void sacuvajTim(Tim t) {
        String upit = "INSERT INTO tim(sifratima, naziv, godinaosnivanja, grad, hala) VALUES(?, ?, ?, ?, ?)";

        try {
            PreparedStatement ps = konekcija.prepareStatement(upit);
            ps.setInt(1, t.getSifraTima());
            ps.setString(2, t.getNaziv());
            ps.setInt(3, t.getGodinaOsnivanja());
            ps.setString(4, t.getGrad());
            ps.setString(5, t.getNazivHale());

            ps.executeUpdate();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(BrokerBazePodataka.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Kosarkas> vratiSveKosarkase() {
        List<Kosarkas> lk = new ArrayList<>();
        String upit = "SELECT * FROM kosarkas k JOIN tim t ON k.sifratima = t.sifratima ORDER BY k.prezime";

        try {
            Statement s = konekcija.createStatement();
            ResultSet rs = s.executeQuery(upit);

            while (rs.next()) {
                Tim t = new Tim(rs.getInt("sifratima"), rs.getString("naziv"), rs.getInt("godinaosnivanja"), rs.getString("grad"), rs.getString("hala"));
                Kosarkas k = new Kosarkas(rs.getString("ime"), rs.getString("prezime"), rs.getString("jmbg"), rs.getDate("datumrodjenja"), rs.getString("pozicija"), rs.getInt("broj"), t, rs.getInt("visina"), rs.getInt("tezina"));
                lk.add(k);
            }

            rs.close();
            s.close();
        } catch (SQLException ex) {
            Logger.getLogger(BrokerBazePodataka.class.getName()).log(Level.SEVERE, null, ex);
        }

        return lk;
    }

    public void sacuvajKosarkasa(Kosarkas k) {
        String upit = "INSERT INTO kosarkas(ime, prezime, datumrodjenja, jmbg, pozicija, broj, visina, tezina, sifratima) VALUES (?,?,?,?,?,?,?,?,?)";

        try {
            PreparedStatement ps = konekcija.prepareStatement(upit);
            ps.setString(1, k.getIme());
            ps.setString(2, k.getPrezime());
            ps.setDate(3, new Date(k.getDatumRodjenja().getTime()));
            ps.setString(4, k.getJmbg());
            ps.setString(5, k.getPozicija());
            ps.setInt(6, k.getBrojNaDresu());
            ps.setInt(7, k.getVisina());
            ps.setInt(8, k.getTezina());
            if (k.getTimZaKojiNastupa() != null) {
                ps.setInt(9, k.getTimZaKojiNastupa().getSifraTima());
            } else {
                ps.setInt(9, 1);
            }

            ps.executeUpdate();
            ps.close();
        } catch (SQLException ex) {
            
        }

    }

    public void obrisiIzabranogKosarkasa(Kosarkas k) {
        String upit = "DELETE FROM kosarkas WHERE jmbg = ?";

        try {
            PreparedStatement ps = konekcija.prepareStatement(upit);
            ps.setString(1, k.getJmbg());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(BrokerBazePodataka.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void obrisiIzabraniTim(Tim t) {
        String upit = "DELETE FROM tim WHERE sifratima = ?";

        try {
            PreparedStatement ps = konekcija.prepareStatement(upit);
            ps.setInt(1, t.getSifraTima());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(BrokerBazePodataka.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void azurirajIgraceNakonBrisanjaTima(Tim t) {
        String upit = "UPDATE kosarkas SET sifratima = 1 WHERE sifratima = ?";
        try {
            PreparedStatement ps = konekcija.prepareStatement(upit);
            ps.setInt(1, t.getSifraTima());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(BrokerBazePodataka.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void dodajUtakmicu(Utakmica u) {
        String upit = "INSERT INTO utakmica(id, datum, domacin, gost, poeni_domacin, poeni_gost) VALUES(?, ?, ?, ?, 0, 0)";

        try {
            PreparedStatement ps = konekcija.prepareStatement(upit);
            ps.setInt(1, u.getIdUtakmice());
            ps.setDate(2, new Date(u.getDatumOdigravanja().getTime()));
            ps.setInt(3, u.getDomacin().getSifraTima());
            ps.setInt(4, u.getGost().getSifraTima());

            ps.executeUpdate();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(BrokerBazePodataka.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void azurirajTim(Tim t) {
        String upit = "UPDATE tim SET naziv = ?, godinaosnivanja = ?, grad = ?, hala = ? WHERE sifratima = ?";

        try {
            PreparedStatement ps = konekcija.prepareStatement(upit);
            ps.setString(1, t.getNaziv());
            ps.setInt(2, t.getGodinaOsnivanja());
            ps.setString(3, t.getGrad());
            ps.setString(4, t.getNazivHale());
            ps.setInt(5, t.getSifraTima());

            ps.executeUpdate();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(BrokerBazePodataka.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void azurirajKosarkasa(Kosarkas k) {
        String upit = "UPDATE kosarkas SET ime= ?, prezime = ?, datumrodjenja = ?, pozicija = ?, broj = ?, visina = ?, tezina = ?, sifratima = ? WHERE jmbg = ?";

        try {
            PreparedStatement ps = konekcija.prepareStatement(upit);
            ps.setString(1, k.getIme());
            ps.setString(2, k.getPrezime());
            ps.setDate(3, new Date(k.getDatumRodjenja().getTime()));

            ps.setString(4, k.getPozicija());
            ps.setInt(5, k.getBrojNaDresu());
            ps.setInt(6, k.getVisina());
            ps.setInt(7, k.getTezina());
            if (k.getTimZaKojiNastupa() != null) {
                ps.setInt(8, k.getTimZaKojiNastupa().getSifraTima());
            } else {
                ps.setInt(8, 1);
            }
            ps.setString(9, k.getJmbg());

            ps.executeUpdate();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(BrokerBazePodataka.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public List<Utakmica> vratiUtakmice() {
        String upit = "SELECT u.id, u.datum, t.sifratima, t.naziv, t.godinaosnivanja, t.grad, t.hala, t2.sifratima AS sifra, t2.naziv AS n, t2.godinaosnivanja AS godina, t2.grad AS g, t2.hala AS h, u.poeni_domacin, u.poeni_gost FROM utakmica u JOIN tim t ON (u.domacin = t.sifratima) JOIN tim t2 ON (u.gost = t2.sifratima) ORDER BY datum DESC";
        List<Utakmica> lu = new ArrayList<>();

        try {
            Statement s = konekcija.createStatement();
            ResultSet rs = s.executeQuery(upit);

            while (rs.next()) {
                Tim d = new Tim(rs.getInt("sifratima"), rs.getString("naziv"), rs.getInt("godinaosnivanja"), rs.getString("grad"), rs.getString("hala"));
                Tim g = new Tim(rs.getInt("sifra"), rs.getString("n"), rs.getInt("godina"), rs.getString("g"), rs.getString("h"));
                lu.add(new Utakmica(rs.getInt("id"), rs.getDate("datum"), d, g, rs.getInt("poeni_domacin"), rs.getInt("poeni_gost")));

            }
        } catch (SQLException ex) {
            Logger.getLogger(BrokerBazePodataka.class.getName()).log(Level.SEVERE, null, ex);
        }

        return lu;
    }

    public List<TipUcinka> vratiTipoveUcinaka() {
        String upit = "SELECT * FROM tipucinka";
        List<TipUcinka> ltu = new ArrayList<>();

        try {
            Statement s = konekcija.createStatement();
            ResultSet rs = s.executeQuery(upit);

            while (rs.next()) {
                ltu.add(new TipUcinka(rs.getString("naziv"), rs.getString("opis")));
            }
        } catch (SQLException ex) {
            Logger.getLogger(BrokerBazePodataka.class.getName()).log(Level.SEVERE, null, ex);
        }

        return ltu;
    }

    public List<Kosarkas> vratiKosarkaseIzOdredjenogTima(Tim t) {
        List<Kosarkas> lista = new ArrayList<>();
        String upit = "SELECT * FROM kosarkas WHERE sifratima = ?";

        try {
            PreparedStatement ps = konekcija.prepareStatement(upit);
            ps.setInt(1, t.getSifraTima());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Kosarkas k = new Kosarkas(rs.getString("ime"), rs.getString("prezime"), rs.getString("jmbg"), rs.getDate("datumrodjenja"), rs.getString("pozicija"), rs.getInt("broj"), t, rs.getInt("visina"), rs.getInt("tezina"));
                lista.add(k);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BrokerBazePodataka.class.getName()).log(Level.SEVERE, null, ex);
        }

        return lista;
    }

    public List<Ucinak> vratiSveUcinkeSaUtakmice(Utakmica u) {
        List<Ucinak> lu = new ArrayList<>();
        String upit = "SELECT * FROM ucinak u JOIN utakmica ut ON (u.utakmica = ut.id) JOIN kosarkas k ON (u.kosarkas = k.jmbg) JOIN tipucinka t ON (u.tip = t.naziv) JOIN tim tm ON (k.sifratima = tm.sifratima) HAVING u.utakmica = ?";

        try {
            PreparedStatement ps = konekcija.prepareStatement(upit);
            ps.setInt(1, u.getIdUtakmice());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                TipUcinka tip = new TipUcinka(rs.getString("naziv"), rs.getString("opis"));
                Tim t = new Tim(rs.getInt("sifratima"), rs.getString("naziv"), rs.getInt("godinaosnivanja"), rs.getString("grad"), rs.getString("hala"));
                Kosarkas k = new Kosarkas(rs.getString("ime"), rs.getString("prezime"), rs.getString("jmbg"), rs.getDate("datumrodjenja"), rs.getString("pozicija"), rs.getInt("broj"), t, rs.getInt("visina"), rs.getInt("tezina"));
                int vrednost = rs.getInt("vrednost");
                lu.add(new Ucinak(k, u, tip, vrednost));

            }
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(BrokerBazePodataka.class.getName()).log(Level.SEVERE, null, ex);
        }

        return lu;

    }

    public void ubaciNoviUcinak(Ucinak u) {
        String upit = "INSERT INTO ucinak(kosarkas, utakmica, tip, vrednost) VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement ps = konekcija.prepareStatement(upit);
            ps.setString(1, u.getKosarkas().getJmbg());
            ps.setInt(2, u.getUtakmica().getIdUtakmice());
            ps.setString(3, u.getTipUcinka().getNaziv());
            ps.setInt(4, u.getVrednost());

            ps.executeUpdate();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(BrokerBazePodataka.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void azurirajRezultatUtakmice(Utakmica u, int poeniD, int poeniG) {
        String upit = "UPDATE utakmica SET poeni_domacin = ?, poeni_gost = ? WHERE id = ?";

        try {
            PreparedStatement ps = konekcija.prepareStatement(upit);
            ps.setInt(1, poeniD);
            ps.setInt(2, poeniG);
            ps.setInt(3, u.getIdUtakmice());

            ps.executeUpdate();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(BrokerBazePodataka.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void dodajNoviTipUcinaka(TipUcinka tip) {
        String upit = "INSERT INTO tipucinka(naziv, opis) VALUES(?, ?)";

        try {
            PreparedStatement ps = konekcija.prepareStatement(upit);
            ps.setString(1, tip.getNaziv());
            ps.setString(2, tip.getOpis());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(BrokerBazePodataka.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public int[] vratiBrojPoenaDomacinaIGosta(Utakmica u) {
        int[] niz = new int[2];
        String upit = "SELECT poeni_domacin, poeni_gost FROM utakmica WHERE id = ?";
        System.out.println(u == null);
        try {
            PreparedStatement ps = konekcija.prepareStatement(upit);
            ps.setInt(1, u.getIdUtakmice());

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                niz[0] = rs.getInt("poeni_domacin");
                niz[1] = rs.getInt("poeni_gost");
            }
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(BrokerBazePodataka.class.getName()).log(Level.SEVERE, null, ex);
        }

        return niz;

    }
    
    public void obrisiTipUcinka(TipUcinka tip) {
        String upit = "DELETE FROM tipucinka WHERE naziv = ?";

        try {
            PreparedStatement ps = konekcija.prepareStatement(upit);
            ps.setString(1, tip.getNaziv());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(BrokerBazePodataka.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void dodajKorisnika(Korisnik k) {
        String upit = "INSERT INTO korisnik(mail, username, password) VALUES(?, ?, ?)";

        try {
            PreparedStatement ps = konekcija.prepareStatement(upit);
            ps.setString(1, k.getMail());
            ps.setString(2, k.getUsername());
            ps.setString(3, k.getPassword());

            ps.executeUpdate();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(BrokerBazePodataka.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void azurirajLozinku(Korisnik k) {
        String upit = "UPDATE korisnik SET password = ? WHERE mail = ?";
        try {
            PreparedStatement ps = konekcija.prepareStatement(upit);
            ps.setString(1, k.getPassword());
            ps.setString(2, k.getMail());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(BrokerBazePodataka.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
}
