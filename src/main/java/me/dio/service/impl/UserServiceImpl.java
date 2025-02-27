package me.dio.service.impl;

import me.dio.domain.model.User;
import me.dio.domain.repository.UserRepository;
import me.dio.dto.UserDTO;
import me.dio.service.UserService;
import org.springdoc.api.OpenApiResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDTO findById(Long id) {
        User entity = userRepository.findById(id)
                .orElseThrow(() -> new OpenApiResourceNotFoundException("User not found with ID: " + id));
        return new UserDTO(entity);
    }

    public UserDTO create(UserDTO userToCreate) {
        boolean accountExists = userRepository.existsByAccountNumber(userToCreate.getAccount().getNumber());
        boolean nameExists = userRepository.existsByName(userToCreate.getName());
        boolean cardExists = userRepository.existsByCardNumber(userToCreate.getCard().getNumber());

        if (accountExists || nameExists || cardExists) {
            String message = "Error: ";
            if (accountExists) message += "This Account number already exists. ";
            if (nameExists) message += "This User name already exists. ";
            if (cardExists) message += "This Card number already exists. ";

            throw new IllegalArgumentException(message.trim());
        }
        // Você deve ter um método de conversão toEntity() no UserDTO
        User entity = userRepository.save(userToCreate.toEntity());
        return new UserDTO(entity);
    }

    public UserDTO update(Long id, UserDTO updatedUser) {
        // Busca o usuário pelo ID, se não existir lança uma exceção
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found with ID: " + id));

        // Verifica se o número da conta já existe em outro usuário
        boolean accountExists = userRepository.existsByAccountNumberAndIdNot(updatedUser.getAccount().getNumber(), id);
        // Verifica se o nome do usuário já existe em outro usuário
        boolean nameExists = userRepository.existsByNameAndIdNot(updatedUser.getName(), id);
        // Verifica se o número do cartão já existe em outro usuário
        boolean cardExists = userRepository.existsByCardNumberAndIdNot(updatedUser.getCard().getNumber(), id);

        // Se houver conflitos, gera uma mensagem personalizada e lança uma exceção
        if (accountExists || nameExists || cardExists) {
            String message = "Error:  ";
            if (accountExists) message += "This Account number already exists. ";
            if (nameExists) message += "This User name already exists. ";
            if (cardExists) message += "This Card number already exists. ";

            throw new IllegalArgumentException(message.trim());
        }

        // Atualiza os dados do usuário encontrado com os novos valores recebidos
        existingUser.setName(updatedUser.getName());
        existingUser.getAccount().setNumber(updatedUser.getAccount().getNumber());
        existingUser.getCard().setNumber(updatedUser.getCard().getNumber());
        existingUser.getCard().setLimit(updatedUser.getCard().getLimit());

        // Salva e retorna o usuário atualizado
        User entity = userRepository.save(existingUser);
        return new UserDTO(entity);
    }

    public void delete(Long id) {
        // Verifica se o usuário existe antes de deletar
        if (!userRepository.existsById(id)) {
            throw new NoSuchElementException("User not found with ID: " + id);
        }

        // Se existir, deleta o usuário do banco de dados
        userRepository.deleteById(id);
    }

    public void deleteCard(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found with ID: " + userId));

        // Verifica se o usuário possui um cartão
        if (user.getCard() != null) {
            user.setCard(null);  // Remove o cartão associado ao usuário
            userRepository.save(user);  // Salva a alteração
        } else {
            throw new IllegalArgumentException("No card found to delete for this user.");
        }
    }

}