/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so.korisnik;

import bp.DBBroker;
import domen.Korisnik;
import so.OpstaSO;

/**
 *
 * @author Marko
 */
public class DodajKorisnikaSo extends OpstaSO{

    @Override
    protected void proveriPreduslov(Object objekat) throws Exception {

    }

    @Override
    protected void izvrsiKonkretnuOperaciju(Object objekat) throws Exception {
        Korisnik korisnik = (Korisnik) objekat;
        DBBroker.vratiObjekat().sacuvajObjekat(korisnik);
    }
    
}
