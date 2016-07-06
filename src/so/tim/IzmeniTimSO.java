/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so.tim;

import bp.DBBroker;
import domen.Tim;
import so.OpstaSO;
import util.Konstante;

/**
 *
 * @author Marko
 */
public class IzmeniTimSO extends OpstaSO{

    @Override
    protected void proveriPreduslov(Object objekat) throws Exception {

    }

    @Override
    protected void izvrsiKonkretnuOperaciju(Object objekat) throws Exception {
        Tim tim = (Tim) objekat;
        tim.setUslov(Konstante.AZURIRAJ_TIM);
        DBBroker.vratiObjekat().izmeniObjekat(tim);
    }
    
}
