package Domeinklasse;


import factory.DAOFactory;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ov_chipkaart")
public class OVChipkaart {
    @Id
    @Column(name="kaart_nummer")
    private int kaart_nummer;
    private Date geldig_tot;
    private int klasse;
    private int saldo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="reiziger_id", nullable=false)
    private Reiziger reiziger;

    @ManyToMany
    @JoinTable(name = "ov_chipkaart_product",
            joinColumns = { @JoinColumn(name = "kaart_nummer") },
            inverseJoinColumns = { @JoinColumn(name = "product_nummer") })
    private List<Product> alleProducten = new ArrayList<>();

    @Transient
    DAOFactory df = DAOFactory.newInstance();

    public OVChipkaart(int kaart_nummer, Date geldig_tot, int klasse, int saldo, Reiziger reiziger) {
        this.kaart_nummer = kaart_nummer;
        this.geldig_tot = geldig_tot;
        this.klasse = klasse;
        this.saldo = saldo;
        this.reiziger = reiziger;
    }

    public OVChipkaart() {

    }

    public Product createNewProductAndAdd(int product_nummer, String naam, String beschrijving, double prijs) {
        Product product = new Product(product_nummer, naam, beschrijving, prijs);
        df.getPdao().save(product);
        alleProducten.add(product);
        return product;
    }

    public void addProduct(Product product) {
        alleProducten.add(product);
        product.voegOVChipkaartToe(this);
    }

    public void deleteProduct(int product_nummer) {
        for (int i = 0; i < alleProducten.size(); i++) {
            Product product = alleProducten.get(i);
            if (product.getProduct_nummer() == product_nummer) {
                alleProducten.remove(product);
                df.getPdao().delete(product);
            }
        }
    }

    public Reiziger getReiziger() {
        return reiziger;
    }

    public void setReiziger(Reiziger reiziger) {
        this.reiziger = reiziger;
    }

    public int getKaart_nummer() {
        return kaart_nummer;
    }

    public void setKaart_nummer(int kaart_nummer) {
        this.kaart_nummer = kaart_nummer;
    }

    public Date getGeldig_tot() {
        return geldig_tot;
    }

    public void setGeldig_tot(Date geldig_tot) {
        this.geldig_tot = geldig_tot;
    }

    public int getKlasse() {
        return klasse;
    }

    public void setKlasse(int klasse) {
        this.klasse = klasse;
    }

    public int getSaldo() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }

    public List<Product> getAlleProducten() {
        return alleProducten;
    }

    public void setAlleProducten(List<Product> alleProducten) {
        this.alleProducten = alleProducten;
    }

    @Override
    public String toString() {
        return " Kaartnummer: " + getKaart_nummer() + " Klasse: " + getKlasse() + "Saldo: " + getSaldo() + " Geldig tot: " + getGeldig_tot() + " Producten: " + alleProducten ;
    }
}
