package com.ilknur.task.web;

import com.ilknur.task.entities.Item;
import com.ilknur.task.entities.Status;
import com.ilknur.task.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

@RestController
public class ItemRestController {

    @Autowired
    ItemService itemService;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
    @PostMapping("items/addItem/{id}")
    public ResponseEntity addItemToTask(@RequestBody Map<String,Object> itemMap, @PathVariable long id) {
        if (itemMap.get("name") != null && !itemMap.get("name").equals("")) {
            if (itemMap.get("description") != null && !itemMap.get("description").equals("")) {
                if (itemMap.get("status") != null) {
                    if (itemMap.get("deadline") != null) {
                        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                        Item item = new Item();
                        item.setName(String.valueOf(itemMap.get("name")));
                        item.setDescription(String.valueOf(itemMap.get("description")));
                        item.setStatus(Status.valueOf((String) itemMap.get("status")));
                            try {
                                item.setDeadline(sdf.parse((String) itemMap.get("deadline")));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        Item response = itemService.addItemToTask(authentication, item, id);
                        if(response!=null) {
                            return new ResponseEntity(HttpStatus.OK);
                        }else{
                            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
                        }
                    } else return new ResponseEntity("{\"result\":\"Deadline cannot be null.\"}", HttpStatus.BAD_REQUEST);
                } else return new ResponseEntity("{\"result\":\"Status Area cannot be null.\"}", HttpStatus.BAD_REQUEST);
            } else return new ResponseEntity("{\"result\":\"Description cannot be null.\"}", HttpStatus.BAD_REQUEST);
        } else return new ResponseEntity("{\"result\":\"Name Area cannot be null.\"}", HttpStatus.BAD_REQUEST);
    }
    @PutMapping("items/addDependency/{id}")
    public ResponseEntity addDependencyToItem(@RequestBody Map<String,Object> item, @PathVariable long id){
        if(item.get(Status.valueOf((String) item.get("status")))== Status.COMPLETE){
            return new ResponseEntity("{\"result\":\"Cannot add dependency to completed task.\"}", HttpStatus.BAD_REQUEST);
        }
        else{
            Item itemObj = new Item();
            itemObj.setId(Long.valueOf((Integer) item.get("id")));
            itemObj.setName(String.valueOf(item.get("name")));
            itemObj.setDescription(String.valueOf(item.get("description")));
            itemObj.setStatus(Status.valueOf((String) item.get("status")));
            try {
                itemObj.setDeadline(simpleDateFormat.parse((String) item.get("deadline")));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Item response = itemService.addDependencyToItem(authentication,itemObj,id);
            if(response !=null) {
                return new ResponseEntity(response,HttpStatus.OK);
            }
            else return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }
    @PutMapping("items/markComplete/{id}")
    public ResponseEntity markAsComplete (@PathVariable long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Item item = itemService.markAsComplete(authentication,id);
        if(item == null){
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        else if(item.getStatus()==Status.NOT_COMPLETE){
            return new ResponseEntity("{\"result\":\"Cannot mark as complete before dependecy items completed.\"}",HttpStatus.OK);
        }
        else {
            return new ResponseEntity(item,HttpStatus.OK);
        }
    }

    @GetMapping("items/{taskId}")
    public ResponseEntity getItems(@PathVariable long taskId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return new ResponseEntity(itemService.getItems(taskId,authentication), HttpStatus.OK);
    }

    @DeleteMapping("items/{id}")
    public ResponseEntity deleteItem(@PathVariable long id){
        itemService.deleteItem(id);
        return new ResponseEntity(HttpStatus.OK);
    }

}
