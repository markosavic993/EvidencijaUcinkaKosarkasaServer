/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package komunikacija;

import domen.Korisnik;
import domen.Kosarkas;
import domen.Tim;
import domen.TipUcinka;
import domen.Ucinak;
import domen.Utakmica;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import poslovnalogika.Kontroler;
import transfer.TransferObjekatOdgovor;
import transfer.TransferObjekatZahtev;
import util.Konstante;

/**
 *
 * @author Marko
 */
public class ServerKomunikacija {

    private Socket socket;
    private boolean kraj = false;

    public void pokreniServer() {
        try {
            ServerSocket ss = new ServerSocket(9000);
            System.out.println("Server je pokrenut.");
            socket = ss.accept();
            System.out.println("Korisnik se povezao.");
            try {
                izvrsenjeOperacija();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ServerKomunikacija.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (IOException ex) {
            Logger.getLogger(ServerKomunikacija.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void izvrsenjeOperacija() throws IOException, ClassNotFoundException {
        while (!kraj) {
            ObjectInputStream inSocket = inSocket = new ObjectInputStream(socket.getInputStream());
            TransferObjekatZahtev toZahtev = (TransferObjekatZahtev) inSocket.readObject();
            TransferObjekatOdgovor toOdgovor = new TransferObjekatOdgovor();

            try {

                System.out.println("O: " + toZahtev.getOperacija());
                switch (toZahtev.getOperacija()) {
                    case Konstante.AUTORIZUJ_KORISNIKA:
                        String username = ((String) toZahtev.getParametar()).split("@")[0];
                        String password = ((String) toZahtev.getParametar()).split("@")[1];
                        boolean izlaz = Kontroler.getInstance().autorizujKorisnika(username, password);
                        toOdgovor.setRezultat(izlaz);
                        toOdgovor.setPoruka("Autorizacija korisnika uspešno izvršena!");
                        break;

                    case Konstante.VRATI_SVE_KORISNIKE:
                        List<Korisnik> lk = Kontroler.getInstance().vratiSveKorisnike();
                        toOdgovor.setRezultat(lk);
                        toOdgovor.setPoruka("Vraćeni svi korisnici!");
                        break;

                    case Konstante.DODAJ_KORISNIKA:
                        Korisnik k = (Korisnik) toZahtev.getParametar();
                        Kontroler.getInstance().dodajKorisnika(k);
                        toOdgovor.setPoruka("Korisnik uspešno sačuvan!");
                        break;

                    case Konstante.VRATI_BROJ_TIMOVA:
                        toOdgovor.setRezultat(Kontroler.getInstance().vratiBrojTimova());
                        toOdgovor.setPoruka("Prebrojani su svi timovi!");
                        break;

                    case Konstante.AZURIRAJ_TIM:
                        Tim t = (Tim) toZahtev.getParametar();
                        Kontroler.getInstance().azurirajTim(t);
                        toOdgovor.setRezultat(null);
                        toOdgovor.setPoruka("Tim uspešno ažuriran!");
                        break;

                    case Konstante.SACUVAJ_TIM:
                        t = (Tim) toZahtev.getParametar();
                        Kontroler.getInstance().sacuvajTim(t);
                        toOdgovor.setRezultat(null);
                        toOdgovor.setPoruka("Tim uspešno sačuvan!");
                        break;
                        
                    case Konstante.VRATI_TIMOVE:
                        List<Tim> lt = Kontroler.getInstance().vratiListuTimova();
                        toOdgovor.setRezultat(lt);
                        toOdgovor.setPoruka("Vraćeni svi timovi!");
                        break;
                        
                    case Konstante.DODAJ_NOVU_UTAKMICU:
                        Utakmica u = (Utakmica) toZahtev.getParametar();
                        Kontroler.getInstance().dodajNovuUtakmicu(u);
                        toOdgovor.setRezultat(null);
                        toOdgovor.setPoruka("Utakmica uspešno sačuvana!");
                        break;
                        
                    case Konstante.VRATI_BROJ_UTAKMICA:
                        toOdgovor.setRezultat(Kontroler.getInstance().vratiIDUtakmice());
                        toOdgovor.setPoruka("Vraćen broj utakmica!");
                        break;
                        
                    case Konstante.SACUVAJ_LISTU_UCINAKA:
                        Kontroler.getInstance().sacuvajListuUcinaka((List<Ucinak>) toZahtev.getParametar());
                        toOdgovor.setPoruka("Lista učinaka sačuvana");
                        toOdgovor.setRezultat(null);
                        break;
                        
                    case Konstante.VRATI_KOSARKASE_IZ_JEDNOG_TIMA:
                        List<Kosarkas> kosarkasiIzJednogTima = Kontroler.getInstance().vratiKosarkaseIzJednogTima((Tim) toZahtev.getParametar());
                        toOdgovor.setRezultat(kosarkasiIzJednogTima);
                        toOdgovor.setPoruka("Vraćeni košarkaši iz jednog tima!");
                        break;
                        
                    case Konstante.VRATI_TIPOVE_UCINAKA:
                        List<TipUcinka> ltu = Kontroler.getInstance().vratiTipoveUcinaka();
                        toOdgovor.setRezultat(ltu);
                        toOdgovor.setPoruka("Vraćeni svi tipovi učinaka!");
                        break;
                        
                    case Konstante.DODAJ_NOVI_TIP_UCINKA:
                        Kontroler.getInstance().dodajNoviTipUcinka((TipUcinka) toZahtev.getParametar());
                        toOdgovor.setPoruka("Novi tip učinka uspešno sačuvan!");
                        toOdgovor.setRezultat(null);
                        break;
                        
                    case Konstante.IZBRISI_TIP_UCINKA:
                        Kontroler.getInstance().izbrisiTipUcinka((TipUcinka) toZahtev.getParametar());
                        toOdgovor.setRezultat(null);
                        toOdgovor.setPoruka("Tip učinka uspešno izbrisan!");
                        break;
                        
                    case Konstante.IZBRISI_KOSARKASA:
                        Kontroler.getInstance().izbrisiKosarkasa((Kosarkas) toZahtev.getParametar());
                        toOdgovor.setRezultat(null);
                        toOdgovor.setPoruka("Košarkaš uspešno obrisan!");
                        break;
                        
                    case Konstante.VRATI_LISTU_KOSARKASA:
                        toOdgovor.setRezultat(Kontroler.getInstance().vratiListuKosarkasa());
                        toOdgovor.setPoruka("Vraćena lista košarkaša!");
                        break;
                        
                    case Konstante.SACUVAJ_KOSARKASA:
                        Kontroler.getInstance().sacuvajKosarkasa((Kosarkas) toZahtev.getParametar());
                        toOdgovor.setRezultat(null);
                        toOdgovor.setPoruka("Košarkaš uspešno sačuvan!");
                        break;
                        
                    case Konstante.AZURIRAJ_KOSARKASA:
                        Kontroler.getInstance().azurirajKosarkasa((Kosarkas) toZahtev.getParametar());
                        toOdgovor.setRezultat(null);
                        toOdgovor.setPoruka("Košarkaš uspešno ažuriran!");
                        break;
                        
                    case Konstante.VRATI_UTAKMICE:
                        toOdgovor.setRezultat(Kontroler.getInstance().vratiUtakmice());
                        toOdgovor.setPoruka("Vraćena lista utakmica!");
                        break;
                        
                    case Konstante.IZBRISI_TIM:
                        Kontroler.getInstance().izbrisiTim((Tim) toZahtev.getParametar());
                        toOdgovor.setRezultat(null);
                        toOdgovor.setPoruka("Tim uspešno izbrisan!");
                        break;
                        
                    case Konstante.VRATI_LISTU_TIMOVA_PREMA_KRITERIJUMU:
                        toOdgovor.setRezultat(Kontroler.getInstance().vratiListuTimovaPremaKriterijumu((String) toZahtev.getParametar()));
                        toOdgovor.setPoruka("Uspešno vraćena lista timova prema kriterijumu!");
                        break;
                        
                    case Konstante.PROMENI_LOZINKU_KORISNIKA:
                        Kontroler.getInstance().promeniLozinkuKorisnika((Korisnik) toZahtev.getParametar());
                        toOdgovor.setRezultat(null);
                        toOdgovor.setPoruka("Lozinka uspešno promenjena!");
                        break;
                        
                    case Konstante.VRATI_UCINKE_ODREDJENOG_IGRACA_NA_UTAKMICI:
                        Object[] niz = (Object[]) toZahtev.getParametar();
                        toOdgovor.setRezultat(Kontroler.getInstance().vratiUcinkeOdredjenogIgracaNaUtakmici((Kosarkas)niz[0], (Utakmica)niz[1]));
                        toOdgovor.setPoruka("Uspešno vraćeni učinci igrača na utakmici!");

                }

            } catch (Exception ex) {
                toOdgovor.setPoruka(ex.getMessage());
                toOdgovor.setIzuzetak(ex);
            }

            posaljiOdgovor(toOdgovor);

        }
    }

    private void posaljiOdgovor(TransferObjekatOdgovor toOdgovor) throws IOException {
        ObjectOutputStream outSocket = new ObjectOutputStream(socket.getOutputStream());
        outSocket.writeObject(toOdgovor);
    }

    public static void main(String[] args) {
        new ServerKomunikacija().pokreniServer();
    }
}
