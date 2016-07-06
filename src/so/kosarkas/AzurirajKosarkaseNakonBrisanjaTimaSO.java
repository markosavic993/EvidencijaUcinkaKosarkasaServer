/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so.kosarkas;

import bp.DBBroker;
import domen.Kosarkas;
import domen.Tim;
import so.OpstaSO;
import util.Konstante;

/**
 *
 * @author Marko
 */
public class AzurirajKosarkaseNakonBrisanjaTimaSO extends OpstaSO{

    @Override
    protected void proveriPreduslov(Object objekat) throws Exception {

    }

    @Override
    protected void izvrsiKonkretnuOperaciju(Object objekat) throws Exception {
        Tim tim = (Tim) objekat;
        Kosarkas kosarkas = new Kosarkas();
        kosarkas.setTimZaKojiNastupa(tim);
        kosarkas.setUslov(Konstante.AZURIRAJ_KOSARKASE_NAKON_BRISNJA_TIMA);
        DBBroker.vratiObjekat().izmeniObjekat(kosarkas);
    }
    
}
