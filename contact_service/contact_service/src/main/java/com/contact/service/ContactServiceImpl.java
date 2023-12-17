package com.contact.service;

import com.contact.entity.Contact;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContactServiceImpl implements ContactService {

    //fake list of contacts

    List<Contact> list = List.of(
            new Contact(1L, "nitin@gmail.com", "Amit", 1L),
            new Contact(2L, "sahil@gmail.com", "Anil", 2L),
            new Contact(3L, "mukul@gmail.com", "Rohan", 3L),
            new Contact(4L, "mukul02@gmail.com", "Sameer", 3L)
    );


    @Override
    public List<Contact> getContactsOfUser(Long userId) {
        return list.stream().filter(contact -> contact.getUserId().equals(userId)).collect(Collectors.toList());
    }
}
