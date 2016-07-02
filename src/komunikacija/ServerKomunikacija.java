/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package komunikacija;

import com.sun.xml.internal.bind.v2.schemagen.episode.Klass;
import domen.Korisnik;
import domen.Kosarkas;
import domen.Tim;
import domen.TipUcinka;
import domen.Ucinak;
import domen.Utakmica;
import forme.komponente.TableModelAktivniKorisnici;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import niti.NitKlijent;
import poslovnalogika.Kontroler;
import transfer.TransferObjekatOdgovor;
import transfer.TransferObjekatZahtev;
import util.Konstante;

/**
 *
 * @author Marko
 */
public class ServerKomunikacija extends Thread {

    private boolean kraj = false;
    private List<Korisnik> aktivniKorisnici;
    private List<NitKlijent> klijenti;
    private ServerSocket ss;

    public ServerKomunikacija(List<Korisnik> aktivniKorisnici, List<NitKlijent> klijenti) {
        this.aktivniKorisnici = aktivniKorisnici;
        this.klijenti = klijenti;
    }

    public boolean isKraj() {
        return kraj;
    }

    public void setKraj(boolean kraj) {
        this.kraj = kraj;
    }

    @Override
    public void run() {
        pokreniServer();
    }

    public void pokreniServer() {
        try {

            ss = new ServerSocket(9000);
            while (!kraj) {
                try {
                    Socket socket = ss.accept();
                    NitKlijent nit = new NitKlijent(socket, aktivniKorisnici, klijenti);
                    klijenti.add(nit);
                    nit.start();
                } catch (Exception e) {
                }

            }

        } catch (IOException ex) {
            Logger.getLogger(ServerKomunikacija.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void zaustavi() throws IOException {
        kraj = true;
        ss.close();
        //this.interrupt();
    }

}
