/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so.ucinak;

import bp.DBBroker;
import domen.UcinakKosarkasa;
import so.OpstaSO;

/**
 *
 * @author Marko
 */
public class SacuvajUcinakNaUtakmiciSO extends OpstaSO{

    @Override
    protected void proveriPreduslov(Object objekat) throws Exception {

    }

    @Override
    protected void izvrsiKonkretnuOperaciju(Object objekat) throws Exception {
        UcinakKosarkasa u = (UcinakKosarkasa) objekat;
        DBBroker.vratiObjekat().sacuvajObjekat(u);
    }
    
}
