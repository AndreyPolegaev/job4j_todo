package ru.job4j.dao;

import ru.job4j.entity.Category;
import ru.job4j.entity.Item;
import ru.job4j.entity.User;

import java.util.List;

public interface Store {

    Item save(Item item, String[] ids);

    User save(User user);

    List<Item> findAll();

    List<Item> findUncompleted();

    User findUserByEmail(String email);

    List<Category> getAllCategories();

    void delete(int id);

    void deleteAll();

    void update(int id);

    void close();
}
