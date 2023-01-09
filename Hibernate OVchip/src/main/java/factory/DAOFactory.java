package factory;


import DAO.AdresDAO;
import DAO.OVChipkaartDAO;
import DAO.ProductDAO;
import DAO.ReizigerDAO;
import Hibernate.AdresDAOHibernate;
import Hibernate.ProductDAOHibernate;
import Hibernate.ReizigerDAOHibernate;
import Hibernate.OVChipkaartDAOHibernate;
import org.hibernate.Session;

public class DAOFactory {
    protected static Session session = sessionFactory.getSession();

    protected OVChipkaartDAO ovdao = new OVChipkaartDAOHibernate();
    protected AdresDAO adao = new AdresDAOHibernate();
    protected ReizigerDAO rdao = new ReizigerDAOHibernate(this);
    protected ProductDAO pdao = new ProductDAOHibernate();

    public static DAOFactory newInstance() {
        return new DAOFactory();
    }

    public OVChipkaartDAO getOvdao() {
        return ovdao;
    }

    public AdresDAO getAdao() {
        return adao;
    }

    public ReizigerDAO getRdao() {
        return rdao;
    }

    public ProductDAO getPdao() {
        return pdao;
    }
}