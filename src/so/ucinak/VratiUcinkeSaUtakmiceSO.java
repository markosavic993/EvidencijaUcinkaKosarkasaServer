/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so.ucinak;

import bp.DBBroker;
import domen.Ucinak;
import domen.Utakmica;
import so.OpstaSO;

/**
 *
 * @author Marko
 */
public class VratiUcinkeSaUtakmiceSO extends OpstaSO{

    @Override
    protected void proveriPreduslov(Object objekat) throws Exception {

    }

    @Override
    protected void izvrsiKonkretnuOperaciju(Object objekat) throws Exception {
        Utakmica ut = (Utakmica) objekat;
        Ucinak u = new Ucinak();
        u.setUtakmica(ut);
        super.setObjekat(DBBroker.vratiObjekat().vratiSveObjekte(u));
    }
    
}
