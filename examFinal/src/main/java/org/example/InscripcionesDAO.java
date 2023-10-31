package org.example;

import org.hibernate.Cache;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class InscripcionesDAO {
    private Cache HibernateUtil;

    public void save(InscripcionesClass inscripcion) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(inscripcion);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public InscripcionesClass getInscripcionById(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        InscripcionesClass inscripcion = session.get(InscripcionesClass.class, id);
        session.close();
        return inscripcion;
    }

    public List<InscripcionesClass> getAllInscripciones() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<InscripcionesClass> inscripciones = session.createQuery("FROM InscripcionesClass", InscripcionesClass.class).list();
        session.close();
        return inscripciones;
    }

    public void update(InscripcionesClass inscripcion) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(inscripcion);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void delete(InscripcionesClass inscripcion) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.delete(inscripcion);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}