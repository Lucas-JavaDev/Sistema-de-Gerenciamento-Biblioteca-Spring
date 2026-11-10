# 📚 Sistema de Gerenciamento Biblioteca — Spring Boot

API REST completa para gerenciamento de biblioteca, desenvolvida em Java com Spring Boot,
aplicando arquitetura em camadas, DTOs, tratamento de exceções customizado e regras de negócio reais.

Projeto construído durante estudo aprofundado de Spring Boot, Spring Data JPA e boas práticas
de desenvolvimento de APIs REST.

---

## 🚀 Funcionalidades

- **Gestão de Autores, Livros e Membros** — CRUD completo
- **Sistema de empréstimo e devolução** de livros
- **Controle automático de estoque** — desconta ao emprestar, devolve ao retornar
- **Limite máximo de empréstimos ativos** por membro
- **Bloqueio de empréstimo duplicado** — um membro não pode ter dois empréstimos do mesmo livro simultaneamente
- **Cálculo automático de atraso** na devolução
- **Tratamento de erros centralizado** — exceções customizadas com respostas HTTP semânticas (404 para recurso não encontrado, 400 para violação de regra de negócio)

---

## 🛠️ Tecnologias

- **Java 17**
- **Spring Boot 4.1.0** (Spring Web MVC, Spring Data JPA, Spring Security)
- **PostgreSQL**
- **Maven**
- **Lombok**
- **JWT** (OAuth2 Resource Server e assinatura HS256)
- **Postman** (testes da API)

---

## 🏗️ Arquitetura

O projeto segue uma estrutura em camadas, separando responsabilidades:
```
src/main/java/br/study/SpringStudy_3/
├── Config/       → Configuração de segurança e JWT
├── Controller/   → Endpoints REST
├── DTO/          → Objetos de entrada e saída da API
├── Entity/       → Entidades mapeadas com JPA
├── Exception/    → Exceções e tratamento global de erros
├── Repository/   → Acesso a dados com Spring Data JPA
└── Service/      → Regras de negócio
```

**Por que DTOs?** As entidades JPA nunca são expostas diretamente na API — cada endpoint
usa DTOs específicos de entrada e saída, evitando exposição de dados sensíveis, prevenindo
mass assignment e desacoplando o modelo de banco de dados do contrato da API.

---

## 🔐 Autenticação e autorização

O login é feito em `POST /auth`, enviando email e senha. A API retorna um token JWT
Bearer, assinado com HS256, válido por uma hora. Para acessar rotas protegidas, envie:

```http
Authorization: Bearer SEU_TOKEN
```

O token inclui as autoridades do membro, como `ROLE_USER` e `ROLE_ADMIN`. O cadastro
público (`POST /membro`) atribui `ROLE_USER`; ele não permite escolher uma role.
O endpoint de promoção exige `ROLE_ADMIN`. Para configurar o primeiro administrador,
atribua essa role diretamente no banco de dados por um procedimento administrativo
controlado. Após receber uma nova role, o membro deve fazer login novamente para obter
um token atualizado.

| Acesso | Rotas |
|--------|-------|
| Público | `POST /auth`, `POST /membro`, `GET /livro`, `GET /livro/{id}`, `GET /autor`, `GET /autor/{id}` |
| `ROLE_ADMIN` | Todas as rotas `/emprestimo/**`; operações de escrita em `/livro` e `/autor`; consulta, atualização, exclusão e promoção em `/membro` |
| `ROLE_USER` ou `ROLE_ADMIN` | Demais rotas protegidas |

As regras gerais são configuradas em `SecurityConfig`; regras específicas por método
usam `@PreAuthorize`, habilitado por `@EnableMethodSecurity`.

## 📋 Endpoints

| Recurso | Rotas principais |
|---------|------------------|
| Autenticação | `POST /auth` |
| Membros | `/membro` — cadastro, consulta, atualização, exclusão e promoção |
| Autores | `/autor` — CRUD |
| Livros | `/livro` — CRUD |
| Empréstimos | `/emprestimo` — registro, consulta, devolução e atualização de status |

As rotas de leitura de livros e autores são públicas. Cadastro, autenticação e permissões
por operação estão detalhados na seção de autenticação e autorização acima.

---

## ⚙️ Como executar localmente

**Pré-requisitos:** Java 17, PostgreSQL. O repositório inclui o Maven Wrapper, então não é necessário instalar o Maven.

1. Clone o repositório:
```bash
   git clone https://github.com/Lucas-JavaDev/Sistema-de-Gerenciamento-Biblioteca-Spring.git
   cd Sistema-de-Gerenciamento-Biblioteca-Spring
```

2. Crie um banco PostgreSQL e configure `src/main/resources/application.properties`
   (esse arquivo é local e não é versionado). Exemplo:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/biblioteca
spring.datasource.username=SEU_USUARIO
spring.datasource.password=SUA_SENHA
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.open-in-view=false
```

3. Configure a variável de ambiente `JWT_SECRET` com uma chave Base64 de 32 bytes.
   Para gerar uma chave localmente:

```bash
openssl rand -base64 32
```

No macOS/Linux, exporte a chave no terminal que iniciará a aplicação:

```bash
export JWT_SECRET='COLE_A_CHAVE_GERADA_AQUI'
```

No PowerShell:

```powershell
$env:JWT_SECRET = "COLE_A_CHAVE_GERADA_AQUI"
```

Não compartilhe nem versione a chave.

4. Execute a aplicação:

```bash
./mvnw spring-boot:run
```

No Windows, use `mvnw.cmd spring-boot:run`. A API estará disponível em
`http://localhost:8080`.

---

## 📮 Testando com Postman

O repositório inclui uma collection e um ambiente local do Postman.

1. Abra o Postman e importe `postman/exports/Sistema Gerenciamento Biblioteca API.json`.
2. Importe o ambiente `postman/environments/Local.environment.yaml` e selecione-o.
3. Configure `base_url` como `http://localhost:8080`.
4. Registre um membro com `POST /membro` e autentique-o em `POST /auth`.
5. Use o token retornado como Bearer Token nas requisições protegidas.

Exemplo de corpo para cadastro:

```json
{
  "nome": "Nome do Membro",
  "cpf": "12345678900",
  "email": "membro@example.com",
  "password": "senha-com-8-ou-mais"
}
```

Exemplo de corpo para login:

```json
{
  "email": "membro@example.com",
  "password": "senha-com-8-ou-mais"
}
```

O login retorna `token` e `tokenType` (`Bearer`). No Postman, selecione **Authorization
→ Bearer Token** e informe o valor de `token`. Para promover um membro, um administrador
pode chamar `PATCH /membro/promote/{membroId}` com `{"roleName":"ROLE_ADMIN"}`.

Um membro comum deve receber `403 Forbidden` ao acessar endpoints exclusivos de
administrador. Para testar essas rotas, configure um primeiro administrador no banco,
faça login com esse membro e use o novo token.

---

## 🧠 O que este projeto me ensinou

- Modelagem de relacionamentos JPA (`@ManyToOne`, `@OneToMany`) e prevenção de referência circular
- Separação de responsabilidades entre Controller, Service e Repository
- Diferença entre validação de existência (404) e validação de regra de negócio (400)
- Uso de transações (`@Transactional`) para garantir consistência em operações compostas
- Query methods do Spring Data JPA (derivação automática de queries por nome de método)
- Emissão e validação de JWT com autorização baseada em roles (`ROLE_USER` e `ROLE_ADMIN`)