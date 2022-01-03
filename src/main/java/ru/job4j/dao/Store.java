package ru.job4j.dao;

import ru.job4j.entity.Item;

import java.util.List;

public interface Store {

    Item save(Item item);

    List<Item> findAll();

    List<Item> findUncompleted();

    void delete(int id);

    void deleteAll();

    void update(int id);

    void close();
}
