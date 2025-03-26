# 🚀 Santander Dev Week 2025 - Java RESTful API  

## 🎯 Objetivo do Projeto 
Criar uma API REST utilizando **Java 17** e **Spring Boot 3**, seguindo boas práticas de desenvolvimento e deploy na nuvem com **Railway**.  

## 🛠️ Tecnologias Utilizadas  

As principais tecnologias e ferramentas utilizadas neste projeto são:  

<p align="center">
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/java/java-original.svg" alt="Java" width="60"/>  
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/spring/spring-original.svg" alt="Spring" width="60"/>  
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/postgresql/postgresql-original.svg" alt="PostgreSQL" width="60"/>  
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/docker/docker-original.svg" alt="Docker" width="60"/>  
  <img src="https://railway.app/brand/logo-light.svg" alt="Railway" width="60"/>  
</p>  

## Diagrama de Classes

```mermaid
classDiagram
    class User {
        +String name
        +Account account
        +List~Feature~ features
        +List~Card~ card
        +List~News~ news
    }

    class Account {
        +String number
        +String agency
        +double balance
        +double limit
    }

    class Feature {
        +String icon
        +String description
    }

    class Card {
        +String number
        +double limit
    }

    class News {
        +String icon
        +String description
    }

    User "1" *-- "1" Account
    User "1" *-- "N" Feature : has many
    User "1" *-- "1" Card : has many
    User "1" *-- "N" News : has many
```

## 📋 Estrutura do Projeto  

### 🔹 **Camadas do Backend**  

- **Model:** Define as entidades do banco de dados, incluindo **User**, **Account**, **Card**, **Feature** e **News**.  
- **DTO (Data Transfer Object):** Organiza e encapsula os dados enviados e recebidos.  
- **Repository:** Interface JPA para operações com o banco de dados.  
- **Service:** Contém a lógica de negócios e métodos CRUD.  
- **Controller:** Define os endpoints REST para manipulação dos dados.  

### 🔹 **Banco de Dados**  

O sistema gerencia as seguintes entidades:  

- **User**: Representa um usuário e suas informações associadas.  
- **Account**: Contém dados da conta bancária, como número, agência, saldo e limite.  
- **Card**: Representa um cartão de crédito com número e limite disponível.  
- **Feature**: Funcionalidades disponíveis para o usuário.  
- **News**: Notícias associadas ao usuário.  

### 🔹 **Operações CRUD**  

- **Create:** Criar usuários e os outros.  
- **Read:** Busca usuários por ID e lista.  
- **Update:** Atualiza informações do usuário.  
- **Delete:** Exclui um usuário e/ou o número do cartão do sistema.  

### 🔹 **Outras Implementações Presentes*  

✅ **Tratamento de Exceções:**  
  - Implementação de um **GlobalExceptionHandler** para capturar erros comuns como `NoSuchElementException` e `IllegalArgumentException`, retornando respostas amigáveis.  


✅ **Docker e Railway:**  
  - **Docker:** Configuração via `Dockerfile` para facilitar a execução do projeto em qualquer ambiente.  
  - **Railway:** Deploy automatizado, tornando a API acessível online.  

---
## ⚠️ Problema Conhecido com Swagger  

Durante o desenvolvimento, foi identificado um problema com a versão **2.1.0 do Swagger** ao utilizar @RestControllerAdvice para tratar os erros mostrando **Failed to load API definition**.

**Solução:**
A migração para a versão **2.8.4 do Springfox** resolveu esse problema. Além disso, foi necessário forçar o exemplo de campos do tipo `Long` utilizando a anotação `@Schema(example = "0")`, porque ao gerar exemplos para campos do tipo **Long**. O Swagger UI exibia um ID absurdo (9007199254740991), que é o limite máximo de segurança do JavaScript, devido a um bug conhecido nas versões antigas do Springfox. E essa solução corrigiu a exibição errada do ID.
Se você enfrentar o mesmo problema ao utilizar o @RestControllerAdvice, confira a solução completa no link do StackOverflow abaixo:

[Problema com Versão do Swagger 2.1.0 e solução](https://stackoverflow.com/questions/79274106/how-to-use-both-restcontrolleradvice-and-swagger-ui-in-spring-boot)


## 📂 Imagens do Projeto  

As imagens do funcionamento do projeto estão disponíveis abaixo:  

<p align="center">
  <img src="./assets/swagger.png" alt="Execução no Swagger" width="300"/> 
  <img src="./assets/tabelasRailway.png" alt="Tabelas no Railway" width="300"/>
  <img src="./assets/railway.png" alt="Salvos na tabela" width="300"/>  
  <img src="./assets/execRailway.png" alt="Projeto sendo executado no Railway" width="300"/>  
    <img src="./assets/execProjeto.png" alt="Execução do projeto na máquina local" width="300"/>  
  <img src="./assets/postman.png" alt="Execução no Postman" width="300"/>  
    <img src="./assets/docker.png" alt="Container criado no Docker" width="300"/>  
</p>
