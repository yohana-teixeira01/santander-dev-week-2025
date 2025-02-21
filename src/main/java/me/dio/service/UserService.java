package me.dio.service;


import me.dio.domain.model.User;

public interface UserService {

    User findById(Long id);

    User create(User userToCreate);

    User update(Long id, User updatedUser);

    void delete(Long id);

    void deleteCard(Long id);
}
