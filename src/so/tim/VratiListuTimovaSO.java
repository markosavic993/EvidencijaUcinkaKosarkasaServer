/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so.tim;

import bp.DBBroker;
import domen.Tim;
import so.OpstaSO;

/**
 *
 * @author Marko
 */
public class VratiListuTimovaSO extends OpstaSO{

    @Override
    protected void proveriPreduslov(Object objekat) throws Exception {

    }

    @Override
    protected void izvrsiKonkretnuOperaciju(Object objekat) throws Exception {
        Tim tim = (Tim) objekat;
        super.setObjekat(DBBroker.vratiObjekat().vratiSveObjekte(tim));
    }
    
}
