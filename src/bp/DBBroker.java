/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bp;

import domen.DomenskiObjekat;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import util.Konstante;

/**
 *
 * @author Marko
 */
public class DBBroker {
    private static DBBroker objekat;
    private Connection konekcija;

    private DBBroker() throws SQLException, ClassNotFoundException{
        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/evidencija_ucinka_kosarkasa";
        String user = "root";
        String password = "";
        konekcija = DriverManager.getConnection(url, user, password);
        konekcija.setAutoCommit(false);
    }

    public static DBBroker vratiObjekat() throws SQLException, ClassNotFoundException{
        if (objekat == null)
            objekat = new DBBroker();
        return objekat;
    }

    public void pokreniTransakciju() throws SQLException {
        konekcija.setAutoCommit(false);
    }

    public void potvrdiTransakciju() throws SQLException {
        konekcija.commit();
    }

    public void ponistiTransakciju() throws SQLException {
        konekcija.rollback();
    }



    public void sacuvajObjekat(DomenskiObjekat dom) throws Exception{
        try {
            String upit = "INSERT INTO "+dom.vratiDeoZaINSERT(0)+" VALUES "+dom.vratiVrednostiZaINSERT(0);
            System.out.println(upit);
            Statement st = konekcija.createStatement();
            st.executeUpdate(upit);

//            if(dom.imaSlabeObjekte()){
//                if(dom.primarniKljucJeAUTONUMBER()){
//                    dom.postaviUslov(Konstante.VRATI_POSLEDNJI_OBJEKAT);
//                    dom = (DomenskiObjekat) vratiObjekat(dom);
//                }
//                dom.postaviUslov(Konstante.SLABI_OBJEKTI);
//                for(int i = 0; i<dom.brojSlabihObjekata(); i++){
//                    upit = "INSERT INTO "+dom.vratiDeoZaINSERT(0)+" VALUES "+dom.vratiVrednostiZaINSERT(i);
//                    st.executeUpdate(upit);
//                }
//            }

        } catch (SQLException e) {
            throw new Exception("Objekat nije sacuvan: "+e.getMessage());
        }
    }

    public void izmeniObjekat(DomenskiObjekat dom) throws Exception{
        try {
            String upit = "UPDATE "+dom.vratiImeTabele()+" SET "+dom.vratiVrednostiZaSET()+" WHERE "
                    +dom.vratiVrednostiZaWHERE();
            System.out.println(upit);
            Statement st = konekcija.createStatement();
            st.executeUpdate(upit);

//            if(dom.imaSlabeObjekte()){
//                dom.postaviUslov(Konstante.SLABI_OBJEKTI_BRISANJE);
//                obrisiObjekat(dom);
//                dom.postaviUslov(Konstante.SLABI_OBJEKTI);
//                for(int i = 0; i<dom.brojSlabihObjekata(); i++){
//                    upit = "INSERT INTO "+dom.vratiDeoZaINSERT(0)+" VALUES "+dom.vratiVrednostiZaINSERT(i);
//                    st.executeUpdate(upit);
//                }
//            }
        } catch (SQLException e) {
            throw  new Exception("Objekat nije izmenjen: "+e.getMessage());
        }
    }

    public void obrisiObjekat(DomenskiObjekat dom) throws Exception{
        try {
            String upit = "DELETE FROM "+dom.vratiImeTabele()+" WHERE "+dom.vratiVrednostiZaWHERE();
            System.out.println(upit);
            Statement st = konekcija.createStatement();
            st.executeUpdate(upit);
        } catch (SQLException e) {
            throw  new Exception("Objekat nije obrisan: "+e.getMessage());
        }
    }

    public Object vratiObjekat(DomenskiObjekat dom) throws Exception{
        try {
            String upit = "";
            Statement st = konekcija.createStatement();
//            if(dom.vratiUslov()==Konstante.VRATI_POSLEDNJI_OBJEKAT){
//                upit = "SELECT "+dom.vratiDeoZaSELECT()+" FROM "+dom.vratiDeoZaFROM();
//                ResultSet rez = st.executeQuery(upit);
//                return dom.vratiObjekat(rez);
//            }

            upit = "SELECT " +dom.vratiDeoZaSELECT()+
                    " FROM " +dom.vratiDeoZaFROM()+ " WHERE " +dom.vratiVrednostiZaWHERE();
            System.out.println(upit);
            ResultSet tabela = st.executeQuery(upit);
            dom = (DomenskiObjekat) dom.vratiObjekat(tabela);
            if(dom == null)
                return null;

//            if(dom.imaSlabeObjekte()){
//                dom.postaviUslov(Konstante.SLABI_OBJEKTI);
//                upit = "SELECT " +dom.vratiDeoZaSELECT()+
//                    " FROM " +dom.vratiDeoZaFROM()+ " WHERE " +dom.vratiVrednostiZaWHERE();
//                tabela = st.executeQuery(upit);
//                dom.napuniObjekatSaSlabimObjektima(tabela);
//            }
            return dom;
        } catch (SQLException e) {
            throw new Exception("Greska u metodi vratiObjekat");
        }
    }

    public List vratiSveObjekte(DomenskiObjekat dom) throws Exception{
        try {
            String upit = "SELECT " +dom.vratiDeoZaSELECT()+
                    " FROM " +dom.vratiDeoZaFROM();
            System.out.println(upit);
            Statement st = konekcija.createStatement();
            ResultSet tabela = st.executeQuery(upit);
            List lista = dom.napuniListuObjekata(tabela);

//            if(dom.imaSlabeObjekte()){
//                for (Object object : lista) {
//                    dom = (DomenskiObjekat) object;
//                    dom.postaviUslov(Konstante.SLABI_OBJEKTI);
//                    upit = "SELECT " +dom.vratiDeoZaSELECT()+
//                        " FROM " +dom.vratiDeoZaFROM()+ " WHERE " +dom.vratiVrednostiZaWHERE();
//                    tabela = st.executeQuery(upit);
//                    dom.napuniObjekatSaSlabimObjektima(tabela);
//                }
//            }
            return lista;
        } catch (SQLException e) {
            throw new Exception("Greska u metodi vratiSveObjekte");
        }
    }

    public List vratiObjektePoUslovu(DomenskiObjekat dom) throws Exception{
        try {
            String upit = "SELECT " +dom.vratiDeoZaSELECT()+
                    " FROM " +dom.vratiDeoZaFROM()+ " WHERE " +dom.vratiVrednostiZaWHERE();
            System.out.println(upit);
            Statement st = konekcija.createStatement();
            ResultSet tabela = st.executeQuery(upit);
            List lista = dom.napuniListuObjekata(tabela);

//            if(dom.imaSlabeObjekte()){
//                for (Object object : lista) {
//                    dom = (DomenskiObjekat) object;
//                    dom.postaviUslov(Konstante.SLABI_OBJEKTI);
//                    upit = "SELECT " +dom.vratiDeoZaSELECT()+
//                        " FROM " +dom.vratiDeoZaFROM()+ " WHERE " +dom.vratiVrednostiZaWHERE();
//                    tabela = st.executeQuery(upit);
//                    dom.napuniObjekatSaSlabimObjektima(tabela);
//                }
//            }
            return lista;
        } catch (SQLException e) {
            throw new Exception("Greska u metodi vratiObjektePoUslovu");
        }
    }
    
    public void izmeniObjekte(List lista) throws Exception{
        try {
            Statement st = konekcija.createStatement();
            for (Object object : lista) {
                DomenskiObjekat dom = (DomenskiObjekat) object;
                String upit = "UPDATE "+dom.vratiImeTabele()+" SET "+dom.vratiVrednostiZaSET()+" WHERE "
                    +dom.vratiVrednostiZaWHERE();
                st.executeUpdate(upit);

//                if(dom.imaSlabeObjekte()){
//                    dom.postaviUslov(Konstante.SLABI_OBJEKTI_BRISANJE);
//                    obrisiObjekat(dom);
//                    dom.postaviUslov(Konstante.SLABI_OBJEKTI);
//                    for(int i = 0; i<dom.brojSlabihObjekata(); i++){
//                        upit = "INSERT INTO "+dom.vratiDeoZaINSERT(0)+" VALUES "+dom.vratiVrednostiZaINSERT(i);
//                        st.executeUpdate(upit);
//                    }
//                }
            }

        } catch (SQLException e) {
            throw  new Exception("Objekti nisu izmenjeni: "+e.getMessage());
        }
    }
    
    public int vratiIDObjekta(DomenskiObjekat dom) throws Exception{
        try {
            String upit = "";
            Statement st = konekcija.createStatement();
//            if(dom.vratiUslov()==Konstante.VRATI_POSLEDNJI_OBJEKAT){
//                upit = "SELECT "+dom.vratiDeoZaSELECT()+" FROM "+dom.vratiDeoZaFROM();
//                ResultSet rez = st.executeQuery(upit);
//                return dom.vratiObjekat(rez);
//            }

            upit = "SELECT " +dom.vratiDeoZaSELECT()+
                    " FROM " +dom.vratiDeoZaFROM();
            System.out.println(upit);
            ResultSet tabela = st.executeQuery(upit);
            int id = dom.vratiID(tabela);
            if(dom == null)
                return -1;

//            if(dom.imaSlabeObjekte()){
//                dom.postaviUslov(Konstante.SLABI_OBJEKTI);
//                upit = "SELECT " +dom.vratiDeoZaSELECT()+
//                    " FROM " +dom.vratiDeoZaFROM()+ " WHERE " +dom.vratiVrednostiZaWHERE();
//                tabela = st.executeQuery(upit);
//                dom.napuniObjekatSaSlabimObjektima(tabela);
//            }
            return id;
        } catch (SQLException e) {
            throw new Exception("Greska u metodi vratiObjekat");
        }
    }
}
