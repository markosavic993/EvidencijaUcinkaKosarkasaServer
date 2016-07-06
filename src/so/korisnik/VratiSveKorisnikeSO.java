/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so.korisnik;

import bp.DBBroker;
import domen.DomenskiObjekat;
import domen.Korisnik;
import domen.Kosarkas;
import so.OpstaSO;

/**
 *
 * @author Marko
 */
public class VratiSveKorisnikeSO extends OpstaSO{

    @Override
    protected void proveriPreduslov(Object objekat) throws Exception {

    }

    @Override
    protected void izvrsiKonkretnuOperaciju(Object objekat) throws Exception {
        Korisnik korisnik = (Korisnik) objekat;
        super.setObjekat(DBBroker.vratiObjekat().vratiSveObjekte(korisnik));
    }
    
}
