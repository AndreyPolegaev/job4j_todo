package ru.job4j.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.entity.Item;

import javax.persistence.Query;
import java.util.List;

public class DaoImpl implements Store, AutoCloseable {

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    private DaoImpl() {

    }

    private static final class Lazy {
        private static final Store INST = new DaoImpl();
    }

    public static Store instOf() {
        return Lazy.INST;
    }

    @Override
    public Item save(Item item) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.save(item);
        session.getTransaction().commit();
        session.close();
        return item;
    }

    @Override
    public List<Item> findAll() {
        Session session = sf.openSession();
        session.beginTransaction();
        List<Item>  items = session.createQuery("from Item").getResultList();
        session.getTransaction().commit();
        session.close();
        return items;
    }

    @Override
    public List<Item> findUncompleted() {
        Session session = sf.openSession();
        session.beginTransaction();
        List<Item>  items = session.createQuery("from Item i where i.done = false ")
                .getResultList();
        session.getTransaction().commit();
        session.close();
        return items;
    }

    @Override
    public void delete(int id) {
        Session session = sf.openSession();
        session.beginTransaction();
        Item item = new Item();
        item.setId(id);
        session.delete(item);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void deleteAll() {
        Session session = sf.openSession();
        session.beginTransaction();
        session.createQuery("delete from Item").executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void update(int id) {
        Session session = sf.openSession();
        session.beginTransaction();
        Item item = session.get(Item.class, id);
        item.setDone(true);
        session.update(item);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void close() {
        StandardServiceRegistryBuilder.destroy(registry);
    }
}

