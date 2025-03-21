package me.dio.domain.repository;

import me.dio.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Boolean existsByAccountNumber(String accountNumber);

    boolean existsByName(String name);

    boolean existsByCardNumber(String cardNumber);

    boolean existsByAccountNumberAndIdNot(String number, Long id);

    boolean existsByNameAndIdNot(String name, Long id);

    boolean existsByCardNumberAndIdNot(String number, Long id);


}
