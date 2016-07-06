/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so.kosarkas;

import bp.DBBroker;
import domen.Kosarkas;
import so.OpstaSO;
import util.Konstante;

/**
 *
 * @author Marko
 */
public class IzmeniKosarkasaSO extends OpstaSO{

    @Override
    protected void proveriPreduslov(Object objekat) throws Exception {

    }

    @Override
    protected void izvrsiKonkretnuOperaciju(Object objekat) throws Exception {
        Kosarkas kosarkas = (Kosarkas) objekat;
        kosarkas.setUslov(Konstante.AZURIRAJ_KOSARKASA);
        DBBroker.vratiObjekat().izmeniObjekat(kosarkas);
    }
    
}
