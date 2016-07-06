/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so.tip_ucinka;

import bp.DBBroker;
import domen.TipUcinka;
import so.OpstaSO;

/**
 *
 * @author Marko
 */
public class VratiTipoveUcinakaSO extends OpstaSO{

    @Override
    protected void proveriPreduslov(Object objekat) throws Exception {

    }

    @Override
    protected void izvrsiKonkretnuOperaciju(Object objekat) throws Exception {
        TipUcinka tu = (TipUcinka) objekat;
        super.setObjekat(DBBroker.vratiObjekat().vratiSveObjekte(tu));
    }
    
}
