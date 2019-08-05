package com.ilknur.task.service;

import com.ilknur.task.dao.ItemRepository;
import com.ilknur.task.dao.TaskRepository;
import com.ilknur.task.entities.Item;
import com.ilknur.task.entities.Status;
import com.ilknur.task.entities.Task;
import com.ilknur.task.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ItemService {
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    AccountService accountService;
    @Autowired
    ItemRepository itemRepository;
    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm");
    public Item addItemToTask(Authentication authentication, Item item, long id) {
        User user = accountService.findUserByUserName(authentication.getName());
        Task task = taskRepository.getOne(id);

        if (task.getUser() == user) {
            item.setCreatedDate(new Date(System.currentTimeMillis()));
            List<Item> itemList = task.getItemList();
            itemList.add(item);
            task.setItemList(itemList);
            itemRepository.save(item);
            taskRepository.save(task);
            return item;
        } else
            return null;
    }

    public Item addDependencyToItem(Authentication authentication, Item item, long id) {
            Item dependentItem = itemRepository.getOne(id);
            Set<Item> dependencyItemSet = dependentItem.getDependencyItems();
            dependencyItemSet.add(item);
            dependentItem.setDependencyItems(dependencyItemSet);
            return itemRepository.save(dependentItem);

    }

    public Item markAsComplete(Authentication authentication, long id) {
        User user = accountService.findUserByUserName(authentication.getName());
        Item item = itemRepository.getOne(id);
            Boolean canBeCompleted = true;
            Set<Item> itemDependency = item.getDependencyItems();
            for (Item _item : itemDependency) {
                if (_item.getStatus() != Status.COMPLETE) {
                    canBeCompleted = false;
                    break;
                }
            }
            if (canBeCompleted) {
                item.setStatus(Status.COMPLETE);
                return itemRepository.save(item);
            } else
                return item;
    }

    public List<Map<String, Object>> getItems(long taskId,Authentication authentication) {
        User user = accountService.findUserByUserName(authentication.getName());
        Task task = taskRepository.getOne(taskId);
        List<Item> itemList = task.getItemList();
        List<Map<String,Object>> responseBody = new ArrayList<>();
        for (Item item:itemList){
            Map<String,Object> responseMap = new HashMap<>();
            if(item.getStatus()==Status.NOT_COMPLETE){
            if(item.getDeadline().before(new Date(System.currentTimeMillis()))) {
                item.setStatus(Status.EXPIRED);
                itemRepository.save(item);
            }
            }
            responseMap.put("id",item.getId());
            responseMap.put("name",item.getName());
            responseMap.put("description",item.getDescription());
            responseMap.put("status",item.getStatus());
            responseMap.put("deadline",sdf.format(item.getDeadline()));
            responseMap.put("dependencyItems",item.getDependencyItems());
            responseBody.add(responseMap);

        }
        return responseBody;
    }

    public void deleteItem(long id) {
        itemRepository.delete(itemRepository.getOne(id));
    }
}
