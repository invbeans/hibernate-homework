package ru.rassafel.hibernate.dao;

import java.util.List;
import java.util.Optional;

/**
 * @author rassafel
 */
public interface GenericDao<Item, Id> {
    Optional<Item> findById(Id key);

    List<Item> findAll();

    Item save(Item item);

    Optional<Item> update(Id key, Item item);

    Optional<Item> deleteById(Id key);
}
