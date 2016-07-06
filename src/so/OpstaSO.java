/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package so;

import bp.DBBroker;
import java.sql.SQLException;

/**
 *
 * @author Ivan
 */
public abstract class OpstaSO {
    private Object objekat;

    public Object getObjekat() {
        return objekat;
    }

    protected void setObjekat(Object objekat) {
        this.objekat = objekat;
    }

    public void izvrsiOperaciju(Object objekat) throws Exception{
        try {
            pokreniTransakciju();
            proveriPreduslov(objekat);
            izvrsiKonkretnuOperaciju(objekat);
            potvrdiTransakciju();
        } catch (Exception e) {
            ponistiTransakciju();
            throw new Exception(e.getMessage());
        }
    }

    private void pokreniTransakciju() throws SQLException, ClassNotFoundException {
        DBBroker.vratiObjekat().pokreniTransakciju();
    }

    protected abstract void proveriPreduslov(Object objekat) throws Exception;

    protected abstract void izvrsiKonkretnuOperaciju(Object objekat) throws Exception;

    private void potvrdiTransakciju() throws SQLException, ClassNotFoundException {
        DBBroker.vratiObjekat().potvrdiTransakciju();
    }

    private void ponistiTransakciju() throws SQLException, ClassNotFoundException {
        DBBroker.vratiObjekat().ponistiTransakciju();
    }

}
