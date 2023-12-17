package com.user.service;

import com.user.entity.Contact;
import com.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private RestTemplate restTemplate;


    List<User> list = List.of(
            new User(1L, "Nitin", "23525625"),
            new User(2L, "Sahil", "99999"),
            new User(3L, "Mukul", "888")
    );

    @Override
    public User getUser(Long id) {

        User filterUser = list.stream().filter(user -> user.getUserId().equals(id)).findAny().orElse(null);
        if (filterUser != null) {
            List<Contact> contacts = restTemplate.exchange(
                            "http://contact-service/contact/user/" + filterUser.getUserId(),
                            HttpMethod.GET,
                            null,
                            new ParameterizedTypeReference<List<Contact>>() {
                            })
                    .getBody();
            filterUser.setContacts(contacts);
        }
        return filterUser;
    }

    @Override
    public List<User> getAllUser() {
        return list.parallelStream().map(user -> {
            List<Contact> contacts = restTemplate.exchange(
                            "http://contact-service/contact/user/" + user.getUserId(),
                            HttpMethod.GET,
                            null,
                            new ParameterizedTypeReference<List<Contact>>() {})
                    .getBody();
            user.setContacts(contacts);
            return user;
        }).collect(Collectors.toList());
    }
}
