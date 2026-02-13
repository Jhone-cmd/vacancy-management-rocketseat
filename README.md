# Vacancy Management

Este projeto é uma aplicação Java/Spring Boot desenvolvida enquanto assistia às aulas da plataforma **RocketSeat**. A aplicação foi criada com o objetivo de demonstrar conceitos de desenvolvimento web, segurança, autenticação e gerenciamento de vagas de emprego.

---

## 📁 Estrutura de Pastas

```
src/
 ├── main/
 │   ├── java/br/com/jhonecmd/vacancy_management/
 │   │   ├── exceptions/            # Classes de exceção e handler global
 │   │   ├── modules/               # Módulos do domínio: candidate, company, job, etc.
 │   │   │   ├── candidate/         # Funcionalidades relacionadas a candidatos
 │   │   │   ├── company/           # Funcionalidades relacionadas a empresas
 │   │   │   └── ...
 │   │   ├── providers/             # Implementações de provedores (JWT, etc.)
 │   │   └── security/              # Configuração e filtros de segurança
 │   └── resources/
 │       ├── application.properties # Configurações da aplicação
 │       ├── static/               # Arquivos estáticos
 │       └── templates/            # Templates caso use MVC
 └── test/                         # Testes de unidade e integração

```

---

## 🔒 Segurança

A aplicação utiliza JWT para autenticação e possui filtros de segurança diferenciados para candidatos e empresas:

- `JWTProvider` e `JWTCandidateProvider` gerenciam a geração e validação de tokens.
- `SecurityConfig` define as regras de acesso às rotas.
- `SecurityFilter` e `SecurityCandidateFilter` interceptam requisições e extraem credenciais.

A autenticação é necessária para a maioria das rotas, e o sistema distingue privilégios entre tipos de usuário (empresa vs. candidato).

---

## 🚀 Funcionalidades Principais

1. **Cadastro e Login**
   - Empresas e candidatos podem se cadastrar e efetuar login.
   - Credenciais são validadas e tokens JWT são retornados.
2. **Gerenciamento de Vagas**
   - Empresas podem criar, atualizar, listar e remover vagas.
   - Candidatos podem visualizar vagas disponíveis.
3. **Rotas Protegidas**
   - Endpoints que requerem autenticação estão protegidos pelos filtros de segurança.
4. **Tratamento de Erros**
   - Exceções customizadas (`ResourceAlreadyExists`, `InvalidCredentials`, etc.) e um `ExceptionHandlerController` para respostas padronizadas.

---

## 🛣️ Rotas Disponíveis

O projeto segue uma organização de controller por módulo. Exemplos de rotas (HTTP):

- **/api/candidates/auth** - registro/login de candidatos
- **/api/companies/auth** - registro/login de empresas
- **/api/candidates/** - operações de candidatos (perfil, etc.)
- **/api/companies/** - operações de empresas
- **/api/companies/{companyId}/jobs/** - gerenciamento de vagas para uma empresa

> Consulte os controllers no código para detalhes completos das rotas.

---

## 🧩 Dependências e Build

O projeto utiliza Maven para gerenciar dependências e compilar:

```bash
./mvnw clean install
./mvnw spring-boot:run
```

Também existe um `docker-compose.yml` para orquestrar serviços adicionais, se necessário.

---

## 📚 Créditos

Este projeto foi realizado com base nas aulas da plataforma **RocketSeat** como parte de um treinamento em desenvolvimento Java e Spring Boot.

---

## ✅ Observações Finais

- Código organizado em módulos para facilitar manutenção.
- Segurança tratada com JWT e filtros específicos.
- Estrutura pensada para escalabilidade e aprendizado.

Qualquer dúvida ou sugestão pode ser enviada via issues no repositório.
