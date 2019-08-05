package com.ilknur.task.dao;

import com.ilknur.task.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item,Long> {
    @Query( value ="SELECT item.id,created_date,deadline,description,item.name,status,task_id FROM todo.item inner join todo.task ON item.task_id = task.id inner join todo.user ON task.user_id = ?1;", nativeQuery = true)
    public List<Item> findItemsByUser(long userId);
}
