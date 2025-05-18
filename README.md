# Solari - Product Service

Este microsserviço é responsável por gerenciar os produtos do sistema, incluindo operações de criação, consulta, atualização e exclusão de produtos. Ele faz parte do sistema de gerenciamento de pedidos do projeto **Solari**, desenvolvido no **Tech Challenge - Fase 4** da pós-graduação em Arquitetura e Desenvolvimento Java - FIAP.

---

## 🧩 Tecnologias Utilizadas

- **Java 21**: Linguagem principal do projeto.
- **Spring Boot**: Framework para criação de aplicações Java.
- **Maven**: Gerenciador de dependências e build.
- **Flyway**: Controle de versão do banco de dados.
- **JPA / Hibernate**: Persistência de dados.
- **Docker**: Containerização da aplicação.
- **JaCoCo**: Ferramenta para análise de cobertura de testes.
- **JUnit 5 + Mockito**: Frameworks para testes unitários e mocks.

---

## 🧱 Estrutura do Projeto

O projeto segue os princípios da Clean Architecture, organizando o código em camadas bem definidas:

- **domain**: Contém as entidades e regras de negócio.
- **application**: Casos de uso e orquestração da lógica.
- **infrastructure**: Implementações técnicas (controllers, gateways, persistência, configurações).
- **tests**: Testes unitários e de integração.

---

## 🚀 Como executar localmente

### Pré-requisitos
- Java 21+
- Maven
- Docker

### Passos
1. Clonar o repositório:
   git clone https://github.com/BrunaCasagrande/solari-product-service.git  
   cd solari-product-service

2. Executar o projeto com Maven:
   mvn spring-boot:run

---

## 📌 Endpoints Principais

### Produto

- **POST** `/solari/v1/products`  
  Cria um novo produto.

- **GET** `/solari/v1/products/{sku}`  
  Busca um produto pelo SKU.

- **PUT** `/solari/v1/products/{sku}`  
  Atualiza os dados de um produto.

- **DELETE** `/solari/v1/products/{sku}`  
  Remove um produto pelo SKU.

---

## ✅ Testes

Para executar os testes e gerar o relatório de cobertura com JaCoCo:

1. Executar os testes:
   mvn test

2. Gerar o relatório de cobertura:
   mvn jacoco:report

3. Acessar o relatório no navegador:  
   file:///C:/solari/solari-product-service/target/site/jacoco/index.html

---

## 🐳 Executando com Docker

### Build da imagem Docker:
docker build -t solari-product-service .

### Executar o container:
docker run -p 8083:8080 solari-product-service

### Acessar a aplicação:
http://localhost:8083/solari/v1/products

---

## 👩‍💻 Autor

Desenvolvido por **Bruna Casagrande RM: 359536** como parte do projeto **Solari**.
