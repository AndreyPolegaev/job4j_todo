package ru.job4j.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.entity.Category;
import ru.job4j.entity.Item;
import ru.job4j.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

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

    private <T> T tx(Function<Session, T> command) {
        Session session = sf.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            T rsl = command.apply(session);
            tx.commit();
            return rsl;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public Item save(Item item, String[] ids) {
        Session session = sf.openSession();
        if (ids != null) {
            for (String temp : ids) {
                Category category = session.load(Category.class, Integer.parseInt(temp));
                item.addCategory(category);
            }
        }
        return tx(ses -> {
            ses.save(item);
            return item;
        });
    }

    @Override
    public User save(User user) {
        return tx(session -> {
            session.save(user);
            return user;
        });
    }

    @Override
    public List<Category> getAllCategories() {
        return tx(session -> session.createQuery("from Category").getResultList());
    }

    @Override
    public User findUserByEmail(String email) {
        User user = (User) tx(session -> session.createQuery("from User where email = :param")
                .setParameter("param", email)
                .uniqueResult());
        return user;
    }

    public static void main(String[] args) {
        User user = DaoImpl.instOf().findUserByEmail("1");
        System.out.println(user);
    }

    @Override
    public List<Item> findAll() {
        return tx(session -> session.createQuery("from Item").getResultList());
    }

    @Override
    public List<Item> findUncompleted() {
        return tx(session -> session.createQuery("from Item i where i.done = false ").getResultList());
    }

    @Override
    public void delete(int id) {
        tx(session -> session.createQuery("delete from Item i where i.id = :param")
                .setParameter("param", id)
                .executeUpdate());
    }

    @Override
    public void deleteAll() {
        tx(session -> session.createQuery("delete from Item").executeUpdate());
    }

    @Override
    public void update(int id) {
        tx(session -> {
            Item item = session.get(Item.class, id);
            item.setDone(true);
            session.update(item);
            return null;
        });
    }

    @Override
    public void close() {
        StandardServiceRegistryBuilder.destroy(registry);
    }
}

