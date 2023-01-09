

import Domeinklasse.Adres;
import Domeinklasse.OVChipkaart;
import Domeinklasse.Product;
import Domeinklasse.Reiziger;
import factory.DAOFactory;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

/**
 * Testklasse - deze klasse test alle andere klassen in deze package.
 *
 * System.out.println() is alleen in deze klasse toegestaan (behalve voor exceptions).
 *
 * @author tijmen.muller@hu.nl
 */
public class Main {
    private static DAOFactory df = DAOFactory.newInstance();

    public static void main(String[] args) throws SQLException {
        testReizigerDAOHibernate();
        testAdresDAOHibernate();
        testOVChipkaartHibernate();

        testProductHibernate();
    }



    /**
     * P6. Haal alle (geannoteerde) entiteiten uit de database.
     */
//    private static void testFetchAll() {
//        Session session = ConnectionFactory.;
//        try {
//            Metamodel metamodel = session.getSessionFactory().getMetamodel();
//            for (EntityType<?> entityType : metamodel.getEntities()) {
//                Query query = session.createQuery("from " + entityType.getName());
//
//                System.out.println("[Test] Alle objecten van type " + entityType.getName() + " uit database:");
//                for (Object o : query.list()) {
//                    System.out.println("  " + o);
//                }
//                System.out.println();
//            }

//    }


    public static void testReizigerDAOHibernate() {
        System.out.println("==============testReizigerDAOHibernate==============");
        List<Reiziger> reizigers = df.getRdao().findAll();
        for (Reiziger reiziger : reizigers) {
            System.out.println(reiziger);
        }

        Reiziger reiziger = new Reiziger("Ibrahim", "", "Errahoui", Date.valueOf("2003-08-05"));


        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
        df.getRdao().save(reiziger);
        reiziger.setVoorletters("I.");
//        System.out.println(reiziger);
        System.out.println(reizigers.size() + " reizigers\n");


        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.delete() ");
        df.getRdao().delete(reiziger);
        df.getRdao().findAll();
        System.out.println(reizigers.size() + " reizigers\n");
    }




    public static void testAdresDAOHibernate() {
        System.out.println("==============testAdresDAOHibernate==============");
        List<Adres> alleAdressen = df.getAdao().findAll();

        for (Adres adres : alleAdressen) {
            System.out.println(adres);
        }

        System.out.println();

        Reiziger reiziger = new Reiziger("Ibrahim", "", "Errahoui", Date.valueOf("2003-08-05"));
        df.getRdao().save(reiziger);
        reiziger.createNewAdres("3554BV", "1", "C.Roobolstraat", "Utrecht");

        reiziger.getAdres().setWoonplaats("Utrecht");

        List<Adres> gezochtAdres = df.getAdao().findByReiziger(reiziger);
        System.out.println(gezochtAdres);

        reiziger.deleteAdres();
        gezochtAdres = df.getAdao().findByReiziger(reiziger);
        System.out.println(gezochtAdres);

        df.getRdao().delete(reiziger);
        System.out.println();


    }

    public static void testOVChipkaartHibernate() {
        System.out.println("==============testOVChipkaartHibernate==============");
        List<OVChipkaart> alleOv = df.getOvdao().findAll();

        for (OVChipkaart ov : alleOv) {
            System.out.println(ov);
        }
        System.out.println();

        Reiziger reiziger = new Reiziger("Ibrahim", "", "Errahoui", Date.valueOf("2003-08-05"));
        df.getRdao().save(reiziger);
        reiziger.createNewOvchipkaart(200000, Date.valueOf("2023-01-01"), 1, 20);

        OVChipkaart ovchipkaart = reiziger.getAlleOVChipkaarten().get(0);
        ovchipkaart.setGeldig_tot(Date.valueOf("2030-03-03"));

        List<OVChipkaart> ovchipkaartVanReiziger = df.getOvdao().findByReiziger(reiziger);
        System.out.println(ovchipkaartVanReiziger);

        alleOv = df.getOvdao().findAll();
        System.out.println("Voor het verwijderen van een OVchipkaart waren er: " + alleOv.size());
        reiziger.deleteOvChipkaart(200000);
        alleOv = df.getOvdao().findAll();
        System.out.println("Aantal overgebleven Ovchipkaarten" + alleOv.size());

        df.getRdao().delete(reiziger);
        System.out.println();
    }

    public static void testProductHibernate() {
        System.out.println("==============testProductHibernate==============");
        OVChipkaart ov = df.getOvdao().findAll().get(0);
        System.out.println(ov);


        Product newProduct = ov.createNewProductAndAdd(10, "text", "", 200);
        
        List<Product> alleProducten = df.getPdao().findAll();
        for (Product product : alleProducten) {
            System.out.println(product);
        }
        System.out.println();

        System.out.println("[Test] Aanpassing beschrijving");
        newProduct.setBeschrijving("nieuwe aangepaste beschrijving");


        alleProducten = df.getPdao().findAll();
        for (Product product : alleProducten) {
            System.out.println(product);
        }

        System.out.println();
        System.out.println("[Test] aantal producten voordat een verwijderd wordt " + alleProducten.size());
        ov.deleteProduct(10);
        alleProducten = df.getPdao().findAll();
        System.out.println("Aantal ovchipkaarten over" + alleProducten.size());

    }


}