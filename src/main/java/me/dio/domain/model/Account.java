package me.dio.domain.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "tb_account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(example = "0")
    private Long id;

    @Column(nullable = false,unique = true)
    @Schema(example = "00000000-0")
    private String number;

    @Schema(example = "0000")
    private String agency;

    @Column(precision = 13,  scale = 2)
    @Schema(example = "1324.64")
    private BigDecimal balance;

    @Column(name = "additional_limit", precision = 13,  scale = 2)
    @Schema(example = "1000.00")
    private BigDecimal limit;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getLimit() {
        return limit;
    }

    public void setLimit(BigDecimal limit) {
        this.limit = limit;
    }

}
