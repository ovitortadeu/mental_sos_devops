# Mental SOS - API de Saúde Mental com Spring Boot e Docker Compose

Este projeto é a API de back-end do Mental SOS, desenvolvida com Spring Boot 3.2.5 (Java 21). Para garantir um ambiente consistente, facilitar o desenvolvimento e o deploy, a aplicação foi containerizada e orquestrada com Docker Compose.

## 1. Visão Geral da Arquitetura

### 1.1. Arquitetura Atual

A versão anterior do Mental SOS operava com uma arquitetura monolítica, onde a aplicação e o banco de dados eram instalados diretamente no servidor, resultando em desafios de escalabilidade e inconsistências de ambiente.

### 1.2. Arquitetura Futura (com Docker Compose)

Com o Docker Compose, a arquitetura foi modernizada para um modelo baseado em containers. Cada componente (aplicação e banco de dados) roda em seu próprio container isolado, orquestrados para funcionarem de forma coesa.

![Arquitetura Futura com Docker Compose]

## 2. Pré-requisitos

*   **Docker Engine:** [Guia de Instalação](https://docs.docker.com/engine/install/)
*   **Docker Compose:** [Guia de Instalação](https://docs.docker.com/compose/install/)
*   **Maven:** Para construir o projeto Java localmente.
*   **Java 21 JDK:** Para compilar o projeto.

## 3. Como Rodar o Projeto

1.  **Clone o repositório:**
    ```bash
    git clone https://github.com/ovitortadeu/mental_sos_devops.git
    cd mental_sos
    ```

2.  **Compile o projeto Spring Boot para gerar o arquivo .jar:**
    ```bash
    mvn clean install -DskipTests
    ```

3.  **Inicie os serviços com Docker Compose:**
    ```bash
    docker compose up --build -d
    ```
    *   `up`: Inicia os serviços definidos no `docker-compose.yml`.
    *   `--build`: Garante que a imagem da aplicação seja construída a partir do `Dockerfile`.
    *   `-d`: Roda os containers em segundo plano.

4.  **Verifique o status dos containers:**
    ```bash
    docker compose ps
    ```
    Ambos os containers (`mental-sos-app` e `mental-sos-db`) devem estar com o status `Up (healthy)`.

5.  **Acesse a aplicação:**
    *   **API RESTful:** A aplicação estará disponível em `http://localhost:8080/`.
    *   **Swagger UI (Documentação da API):** Acesse a documentação interativa em `http://localhost:8080/swagger-ui.html`.

## 4. Comandos Essenciais do Docker Compose

*   **Iniciar os serviços (em segundo plano):**
    ```bash
    docker compose up -d
    ```
*   **Parar os serviços:**
    ```bash
    docker compose stop
    ```
*   **Parar e remover os containers, redes e volumes (apaga os dados do banco):**
    ```bash
    docker compose down -v
    ```
*   **Reconstruir as imagens e reiniciar os serviços:**
    ```bash
    docker compose up --build -d
    ```
*   **Visualizar logs de todos os serviços:**
    ```bash
    docker compose logs -f
    ```
*   **Executar um comando em um container (ex: shell no container da aplicação):**
    ```bash
    docker compose exec app bash
    ```

## 5. Processo de Deploy Passo a Passo

1.  **Pré-requisitos no Servidor:** Instale Docker e Docker Compose no servidor de destino.
2.  **Obter o Código:** Clone o repositório do projeto no servidor.
3.  **Compilar o Projeto:** Gere o arquivo `.jar` com o comando `mvn clean install -DskipTests`.
4.  **Configurar Segurança:** **IMPORTANTE:** Antes de subir em produção, edite o `docker-compose.yml` e altere as senhas (`SPRING_DATASOURCE_PASSWORD`, `POSTGRES_PASSWORD`) e o segredo do JWT (`JWT_SECRET`) para valores fortes e seguros.
5.  **Iniciar a Aplicação:** Utilize `docker compose up --build -d` para iniciar os serviços.
6.  **Configurar Firewall/Proxy Reverso:** Configure o firewall e/ou um proxy reverso (como Nginx) para gerenciar o acesso público à aplicação.

## 6. Troubleshooting Básico

*   **Serviços não iniciam:** Verifique os logs com `docker compose logs -f`. A causa mais comum são portas (8080 ou 5432) já em uso na sua máquina.
*   **Aplicação não conecta ao banco:** Verifique se as credenciais (`user`, `password`, `db_name`) no `docker-compose.yml` são exatamente as mesmas para o serviço `app` e `db`.
*   **Erro de compilação:** Certifique-se de que o Maven e o JDK 21 estão corretamente instalados e configurados.
