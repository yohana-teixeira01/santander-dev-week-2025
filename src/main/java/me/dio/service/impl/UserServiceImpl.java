package me.dio.service.impl;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import me.dio.domain.model.User;
import me.dio.domain.repository.UserRepository;
import me.dio.dto.UserDTO;
import me.dio.service.UserService;
import org.springdoc.api.OpenApiResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content(mediaType = "application/json"))
    })
    public UserDTO findById(Long id) {
        User entity = userRepository.findById(id)
                .orElseThrow(() -> new OpenApiResourceNotFoundException("Usuário não encontrado com ID: " + id));
        return new UserDTO(entity);
    }

    @Override
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuários", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "204", description = "Nenhum usuário encontrado", content = @Content(mediaType = "application/json"))
    })
    public List<UserDTO> findAll() {
        List<User> users = userRepository.findAll();
        return users.stream().map(UserDTO::new).collect(Collectors.toList());
    }

    @Override
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou informações duplicadas", content = @Content(mediaType = "application/json"))
    })
    public UserDTO create(UserDTO userToCreate) {

        if (userToCreate.getAccount() == null || userToCreate.getAccount().getNumber() == null || userToCreate.getAccount().getNumber().isEmpty()) {
            throw new IllegalArgumentException("Número de conta não pode ser nulo ou vazio.");
        }
        if (userToCreate.getName() == null || userToCreate.getName().isEmpty()) {
            throw new IllegalArgumentException("Nome de usuário não pode ser nulo ou vazio.");
        }
        if (userToCreate.getCard() == null || userToCreate.getCard().getNumber() == null || userToCreate.getCard().getNumber().isEmpty()) {
            throw new IllegalArgumentException("Número de cartão não pode ser nulo ou vazio.");
        }

        boolean accountExists = userRepository.existsByAccountNumber(userToCreate.getAccount().getNumber());
        boolean nameExists = userRepository.existsByName(userToCreate.getName());
        boolean cardExists = userRepository.existsByCardNumber(userToCreate.getCard().getNumber());

        if (accountExists || nameExists || cardExists) {
            String message = "Erro: ";
            if (accountExists) message += "Este número de conta já existe. ";
            if (nameExists) message += "Este nome de usuário já existe. ";
            if (cardExists) message += "Este número de cartão já existe. ";

            throw new IllegalArgumentException(message.trim());
        }

        User entity = userRepository.save(userToCreate.toEntity());
        return new UserDTO(entity);
    }

    @Override
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou informações duplicadas", content = @Content(mediaType = "application/json"))
    })
    public UserDTO update(Long id, UserDTO updatedUser) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Usuário não encontrado com ID: " + id));

        boolean accountExists = userRepository.existsByAccountNumberAndIdNot(updatedUser.getAccount().getNumber(), id);
        boolean nameExists = userRepository.existsByNameAndIdNot(updatedUser.getName(), id);
        boolean cardExists = userRepository.existsByCardNumberAndIdNot(updatedUser.getCard().getNumber(), id);

        if (accountExists || nameExists || cardExists) {
            String message = "Erro:  ";
            if (accountExists) message += "Este número de conta já existe. ";
            if (nameExists) message += "Este nome de usuário já existe. ";
            if (cardExists) message += "Este número de cartão já existe. ";

            throw new IllegalArgumentException(message.trim());
        }

        existingUser.setName(updatedUser.getName());
        existingUser.getAccount().setNumber(updatedUser.getAccount().getNumber());
        existingUser.getCard().setNumber(updatedUser.getCard().getNumber());
        existingUser.getCard().setLimit(updatedUser.getCard().getLimit());

        if (existingUser.getAccount().getNumber() == null) {
            throw new IllegalArgumentException("Número da conta não pode ser nulo.");
        }

        if (existingUser.getName() == null || existingUser.getName().isEmpty()) {
            throw new IllegalArgumentException("Nome do usuário não pode ser nulo ou vazio.");
        }

        if (existingUser.getCard().getNumber() == null) {
            throw new IllegalArgumentException("Número do cartão não pode ser nulo.");
        }

        User entity = userRepository.save(existingUser);
        return new UserDTO(entity);
    }

    @Override
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content(mediaType = "application/json"))
    })
    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new NoSuchElementException("Usuário não encontrado com ID: " + id);
        }

        userRepository.deleteById(id);
    }
}
