/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so.utakmica;

import bp.DBBroker;
import domen.Utakmica;
import so.OpstaSO;

/**
 *
 * @author Marko
 */
public class AzurirajUtakmicuSO extends OpstaSO{

    @Override
    protected void proveriPreduslov(Object objekat) throws Exception {

    }

    @Override
    protected void izvrsiKonkretnuOperaciju(Object objekat) throws Exception {
        Utakmica u = (Utakmica) objekat;
        DBBroker.vratiObjekat().izmeniObjekat(u);
    }
    
}
