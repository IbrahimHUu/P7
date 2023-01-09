package Domeinklasse;


import factory.DAOFactory;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "reiziger")
public class Reiziger {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "reiziger_id", nullable = false)
    private int reiziger_id;


    private String voorletters;
    private String tussenvoegsel;
    private String achternaam;
    private Date geboortedatum;

    @JoinColumn(name = "reiziger_id")
    @OneToOne()
    private Adres adres;

    @OneToMany(mappedBy= "reiziger")
    private List<OVChipkaart> alleOVChipkaarten = new ArrayList<>();

    @Transient
    DAOFactory df = DAOFactory.newInstance();

    public Reiziger(String voorletters, String tussenvoegsel, String achternaam, Date geboortedatum) {
        this.voorletters = voorletters;
        this.tussenvoegsel = tussenvoegsel;
        this.achternaam = achternaam;
        this.geboortedatum = geboortedatum;
    }

    public Reiziger() {
    }

    public OVChipkaart createNewOvchipkaart(int kaartnummer, Date datum, int klasse, int saldo) {
        OVChipkaart ov = new OVChipkaart(kaartnummer, datum, klasse, saldo, this);
        alleOVChipkaarten.add(ov);
        df.getOvdao().save(ov);

        return ov;
    }

    public boolean deleteOvChipkaart(int kaart_nummer) {
        for (OVChipkaart ov : alleOVChipkaarten) {
            if (ov.getKaart_nummer() == kaart_nummer) {
                df.getOvdao().delete(ov);
                alleOVChipkaarten.remove(ov);
                return true;
            }
        }
        return false;
    }

    public OVChipkaart findByKaartnummer(int kaart_nummer) {
        for (OVChipkaart ov : alleOVChipkaarten) {
            if (ov.getKaart_nummer() == kaart_nummer) {
                return ov;
            }
        }
        return null;
    }

    public Adres createNewAdres(String postcode, String huisnummer, String straat, String woonplaats) {
        Adres adres = new Adres(this, this.getId(), postcode, huisnummer, straat, woonplaats);
        this.adres = adres;
        df.getAdao().save(adres);
        return adres;
    }

    public void deleteAdres() {
        if (adres != null) {
            df.getAdao().delete(getAdres());
            this.adres = null;
        }

    }

    public void addOvchipkaart(OVChipkaart ovChipkaart) {
        alleOVChipkaarten.add(ovChipkaart);
    }

    public Adres getAdres() {
        return adres;
    }

    public void setAdres(Adres adres) {
        this.adres = adres;
        df.getRdao().update(this);
    }

    public int getId() {
        return reiziger_id;
    }

    public String getVoorletters() {
        return voorletters;
    }

    public void setVoorletters(String voorletters) {
        this.voorletters = voorletters;
        df.getRdao().update(this);
    }

    public String getTussenvoegsel() {
        return tussenvoegsel;
    }

    public void setTussenvoegsel(String tussenvoegsel) {
        this.tussenvoegsel = tussenvoegsel;
        df.getRdao().update(this);
    }

    public String getAchternaam() {
        return achternaam;
    }

    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
        df.getRdao().update(this);
    }

    public Date getGeboortedatum() {
        return geboortedatum;
    }

    public void setGeboortedatum(Date geboortedatum) {
        this.geboortedatum = geboortedatum;
        df.getRdao().update(this);
    }

    public List<OVChipkaart> getAlleOVChipkaarten() {
        return alleOVChipkaarten;
    }

    public void setAlleOVChipkaarten(ArrayList<OVChipkaart> alleOVChipkaarten) {
        this.alleOVChipkaarten = alleOVChipkaarten;
    }

    public int getReiziger_id() {
        return reiziger_id;
    }

    public void setReiziger_id(int reiziger_id) {
        this.reiziger_id = reiziger_id;
    }

    @Override
    public String toString() {
//        return "reiziger_id" + rei
//                ", voorletters='" + voorletters + '\'' +
//                ", tussenvoegsel='" + tussenvoegsel + '\'' +
//                ", achternaam='" + achternaam + '\'' +
//                ", geboortedatum=" + geboortedatum +
//                ", adres=" + adres +
//                ", alleOVChipkaarten=" + alleOVChipkaarten +
//                '}';

        String s = "Voorletters:" + " " + voorletters + " " + "Tussenvoegsel:" + " " + tussenvoegsel +
                " " + "Achternaam:" + " " + achternaam + " " + "Geboortedatum:" + " " + geboortedatum;
        return s;
    }
}
