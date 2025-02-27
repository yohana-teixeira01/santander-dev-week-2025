package me.dio.service;



import me.dio.dto.UserDTO;

public interface UserService {

    UserDTO findById(Long id);

    UserDTO create(UserDTO userToCreate);

    UserDTO update(Long id, UserDTO updatedUser);

    void delete(Long id);

    void deleteCard(Long id);
}