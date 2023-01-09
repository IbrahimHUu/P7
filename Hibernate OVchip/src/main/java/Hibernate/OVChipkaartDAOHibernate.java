package Hibernate;

import DAO.OVChipkaartDAO;
import Domeinklasse.OVChipkaart;
import Domeinklasse.Reiziger;
import factory.sessionFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class OVChipkaartDAOHibernate implements OVChipkaartDAO {
    @Override
    public boolean save(OVChipkaart ovchipkaart) {
        Session session = sessionFactory.getSession();

        Transaction tx = session.beginTransaction();
        session.save(ovchipkaart);
        tx.commit();

        return true;
    }

    @Override
    public List<OVChipkaart> findByReiziger(Reiziger reiziger) {
        Session session = sessionFactory.getSession();

        Transaction tx = null;
        try {
            String hql = "from OVChipkaart ov where ov.reiziger.reiziger_id =:reiziger";
            tx = session.beginTransaction();
            Query query = session.createQuery(hql);
            query.setParameter("reiziger", reiziger.getReiziger_id());

            tx.commit();

            return query.list();
        } catch (HibernateException e) {
            tx.rollback();
        }
        return null;
    }

    @Override
    public boolean update(OVChipkaart ovchipkaart) {
        Session session = sessionFactory.getSession();

        Transaction tx = session.beginTransaction();
        session.update(ovchipkaart);
        tx.commit();

        return true;
    }

    @Override
    public boolean delete(OVChipkaart ovchipkaart) {
        Session session = sessionFactory.getSession();

        Transaction tx = session.beginTransaction();
        session.delete(ovchipkaart);
        tx.commit();

        return true;
    }

    @Override
    public List<OVChipkaart> findAll() {
        Session session = sessionFactory.getSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            List<OVChipkaart> alleOvchipkaarten = session.createQuery("from OVChipkaart ").getResultList();
            tx.commit();
            return alleOvchipkaarten;
        } catch (HibernateException e) {
            tx.rollback();
        }
        return null;
    }
}
