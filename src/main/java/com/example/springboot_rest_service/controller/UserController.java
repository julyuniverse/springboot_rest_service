package com.example.springboot_rest_service.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import com.example.springboot_rest_service.model.User;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private int sequence;
    private Map<Integer, User> userMap = new HashMap<>();

    @PostConstruct // 초기화를 수행하기 위해 종속성 주입이 완료된 후, 클래스가 서비스되기 전에 호출됨.
    public void init() { // 임시 users data 생성
        sequence++;
        userMap.put(sequence, new User(sequence, "Taesung", "Lee", 34));
        sequence++;
        userMap.put(sequence, new User(sequence, "Jungmin", "Lee", 34));
    }

    @GetMapping("/users")
    public List<User> getUsers() { // 모든 유저 출력
        return new ArrayList<User>(userMap.values());
    }

    @PostMapping("/users")
    public String createUser(@RequestBody User user) { // 유저 생성
        sequence++;
        user.setId(sequence);
        userMap.put(sequence, user);
        return "User with the username " + user.getFirstName() + " added to the Database!";
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable("id") int id) { // 유저 출력
        Iterator<Integer> keys = userMap.keySet().iterator(); // Iterator 메서드로 해당 id를 찾는다.
        while (keys.hasNext()) {
            int key = keys.next();
            if (userMap.get(key).getId() == id) {
                return userMap.get(key);
            }
        }
        return null;
    }

    @PutMapping("/users/{id}")
    public String updateUser(@PathVariable("id") int id, @RequestBody User user) { // 유저 수정
        Iterator<Integer> keys = userMap.keySet().iterator(); // Iterator 메서드로 해당 id를 찾는다.
        while (keys.hasNext()) {
            int key = keys.next();
            if (userMap.get(key).getId() == id) {
                user.setId(id);
                userMap.put(key, user);

                return "User with the id " + id + " has been updated!";
            }
        }
        return null;
    }

    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable("id") int id) { // 유저 삭제
        Iterator<Integer> keys = userMap.keySet().iterator(); // Iterator 메서드로 해당 id를 찾는다.
        while (keys.hasNext()) {
            int key = keys.next();
            if (userMap.get(key).getId() == id) {
                userMap.remove(key);

                return "User with the id " + id + " deleted from the Database!";
            }
        }
        return null;
    }
}
