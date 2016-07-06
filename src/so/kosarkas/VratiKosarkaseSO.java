/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so.kosarkas;

import bp.DBBroker;
import domen.Kosarkas;
import poslovnalogika.Kontroler;
import so.OpstaSO;
import util.Konstante;

/**
 *
 * @author Marko
 */
public class VratiKosarkaseSO extends OpstaSO{

    @Override
    protected void proveriPreduslov(Object objekat) throws Exception {

    }

    @Override
    protected void izvrsiKonkretnuOperaciju(Object objekat) throws Exception {
        Kosarkas kosarkas = (Kosarkas) objekat;
        super.setObjekat(DBBroker.vratiObjekat().vratiSveObjekte(kosarkas));
    }
    
}
