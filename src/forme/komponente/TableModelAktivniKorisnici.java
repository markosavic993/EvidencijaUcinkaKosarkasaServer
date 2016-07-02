/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forme.komponente;

import domen.Korisnik;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Marko
 */
public class TableModelAktivniKorisnici extends AbstractTableModel{

    List<Korisnik> lk;
    private static TableModelAktivniKorisnici instance;

    private TableModelAktivniKorisnici() {
        this.lk = new ArrayList<>();
    }
    
    public static TableModelAktivniKorisnici getInstance() {
        if(instance == null) {
            instance = new TableModelAktivniKorisnici();
        }
        return instance;
    }

    public void setLk(List<Korisnik> lk) {
        this.lk = lk;
    }
    
    
    
    @Override
    public int getRowCount() {
        return lk.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Korisnik k = lk.get(rowIndex);
        switch(columnIndex) {
            case 0: return k.getUsername();
            case 1: return k.getMail();
                default: return "n/a";
        }
    }

    @Override
    public String getColumnName(int column) {
        switch(column) {
            case 0: return "Korisničko ime";
            case 1: return "E-Mail";
                default: return "n/a";
        }
    }

    public void osvezi() {
        fireTableDataChanged();
    }
    
    
    
}
