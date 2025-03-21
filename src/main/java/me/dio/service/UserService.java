package me.dio.service;

import me.dio.dto.UserDTO;

import java.util.List;

public interface UserService {

    UserDTO findById(Long id);

    UserDTO create(UserDTO userToCreate);

    UserDTO update(Long id, UserDTO updatedUser);

    void delete(Long id);

    List<UserDTO> findAll();
}