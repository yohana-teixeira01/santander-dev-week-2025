package me.dio.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import me.dio.dto.UserDTO;
import me.dio.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Busca por id", description = "Método que retorna um registro", tags = "Users")
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable Long id) {
        UserDTO user = userService.findById(id);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(user);
    }
    @Operation(summary = "Listar", description = "Método que retorna todos os dados", tags = "Users")
    @GetMapping
    public ResponseEntity<List<UserDTO>> findAll() {
        List<UserDTO> users = userService.findAll();
        return ResponseEntity.ok(users);
    }
    @Operation(summary = "Salvar", description = "Método que salva um registro",tags = "Users")
    @PostMapping
    public ResponseEntity<UserDTO> create(@Valid @RequestBody UserDTO userToCreate) {
        // O serviço agora recebe e retorna UserDTO
        UserDTO userCreated = userService.create(userToCreate);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(userCreated.getId())
                .toUri();
        return ResponseEntity.created(location).body(userCreated);
    }
    @Operation(summary = "Atualizar", description = "Método que atualiza os dados do registro", tags = "Users")
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO updatedUser) {
        // Atualiza o usuário e retorna UserDTO
        UserDTO user = userService.update(id, updatedUser);
        return ResponseEntity.ok(user);
    }
    @Operation(summary = "Deletar", description = "Método que exclui um registro", tags = "Users")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.ok("User deleted successfully with ID: " + id);
    }

}
