CREATE TABLE IF NOT EXISTS tb_sos_grupo_usuario (
    id BIGSERIAL PRIMARY KEY,
    id_grupo SMALLINT NOT NULL
) 
@@

CREATE TABLE IF NOT EXISTS tb_sos_usuario (
    id BIGSERIAL PRIMARY KEY,
    tb_sos_grupo_usuario_id BIGINT UNIQUE NOT NULL,
    username VARCHAR(100) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    senha VARCHAR(100) NOT NULL,
    numero_crp INTEGER
)
@@

CREATE TABLE IF NOT EXISTS tb_sos_chat (
    id BIGSERIAL PRIMARY KEY,
    status SMALLINT DEFAULT 1 NOT NULL CHECK (status IN (0, 1, 2)),
    criado_em TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    tb_sos_usuario_id_criador BIGINT NOT NULL
)
@@

CREATE TABLE IF NOT EXISTS tb_sos_mensagem (
    id BIGSERIAL PRIMARY KEY,
    conteudo TEXT NOT NULL,
    enviada_em TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    tb_sos_chat_id BIGINT NOT NULL,
    remetente_usuario_id BIGINT NOT NULL,
    tb_sos_usuario_id_paciente BIGINT NULL,
    tb_sos_usuario_id_psicologo BIGINT NULL
)
@@

CREATE TABLE IF NOT EXISTS tb_sos_ong (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    contato NUMERIC,
    tb_sos_usuario_id BIGINT NOT NULL
)
@@

ALTER TABLE tb_sos_usuario
    DROP CONSTRAINT IF EXISTS tb_sos_usuario_grupousuario_FK,
    ADD CONSTRAINT tb_sos_usuario_grupousuario_FK
        FOREIGN KEY (tb_sos_grupo_usuario_id) REFERENCES tb_sos_grupo_usuario (id)
        ON DELETE CASCADE
@@

ALTER TABLE tb_sos_chat
    DROP CONSTRAINT IF EXISTS tb_sos_chat_usuario_criador_FK,
    ADD CONSTRAINT tb_sos_chat_usuario_criador_FK
        FOREIGN KEY (tb_sos_usuario_id_criador) REFERENCES tb_sos_usuario (id)
        ON DELETE CASCADE
@@

ALTER TABLE tb_sos_mensagem
    DROP CONSTRAINT IF EXISTS tb_sos_mensagem_chat_FK,
    ADD CONSTRAINT tb_sos_mensagem_chat_FK
        FOREIGN KEY (tb_sos_chat_id) REFERENCES tb_sos_chat (id)
        ON DELETE CASCADE
@@

ALTER TABLE tb_sos_mensagem
    DROP CONSTRAINT IF EXISTS tb_sos_mensagem_remetente_FK,
    ADD CONSTRAINT tb_sos_mensagem_remetente_FK
        FOREIGN KEY (remetente_usuario_id) REFERENCES tb_sos_usuario (id)
        ON DELETE CASCADE
@@

ALTER TABLE tb_sos_mensagem
    DROP CONSTRAINT IF EXISTS tb_sos_mensagem_paciente_FK,
    ADD CONSTRAINT tb_sos_mensagem_paciente_FK
        FOREIGN KEY (tb_sos_usuario_id_paciente) REFERENCES tb_sos_usuario (id)
        ON DELETE SET NULL
@@

ALTER TABLE tb_sos_mensagem
    DROP CONSTRAINT IF EXISTS tb_sos_mensagem_psicologo_FK,
    ADD CONSTRAINT tb_sos_mensagem_psicologo_FK
        FOREIGN KEY (tb_sos_usuario_id_psicologo) REFERENCES tb_sos_usuario (id)
        ON DELETE SET NULL
@@

ALTER TABLE tb_sos_ong
    DROP CONSTRAINT IF EXISTS tb_sos_ong_usuario_FK,
    ADD CONSTRAINT tb_sos_ong_usuario_FK
        FOREIGN KEY (tb_sos_usuario_id) REFERENCES tb_sos_usuario (id)
        ON DELETE CASCADE
@@

CREATE OR REPLACE PROCEDURE proc_update_usuario (
    p_usuario_id IN BIGINT,
    p_username IN VARCHAR DEFAULT NULL,
    p_email IN VARCHAR DEFAULT NULL,
    p_senha IN VARCHAR DEFAULT NULL,
    p_numero_crp IN INTEGER DEFAULT NULL
) LANGUAGE plpgsql
AS $$
BEGIN
    UPDATE tb_sos_usuario
    SET username = COALESCE(p_username, username),
        email = COALESCE(p_email, email),
        senha = COALESCE(p_senha, senha),
        numero_crp = COALESCE(p_numero_crp, numero_crp)
    WHERE id = p_usuario_id;
EXCEPTION
    WHEN OTHERS THEN
        RAISE WARNING 'Erro proc_update_usuario: % - %', SQLSTATE, SQLERRM;
END;
$$
@@

CREATE OR REPLACE FUNCTION func_contar_usuarios_por_tipo_grupo (
    p_id_grupo_conceitual IN SMALLINT
) RETURNS INTEGER LANGUAGE plpgsql
AS $$
DECLARE
    v_total_usuarios INTEGER;
BEGIN
    SELECT COUNT(u.id) INTO v_total_usuarios
    FROM tb_sos_usuario u
    JOIN tb_sos_grupo_usuario gu ON u.tb_sos_grupo_usuario_id = gu.id
    WHERE gu.id_grupo = p_id_grupo_conceitual;
    RETURN v_total_usuarios;
EXCEPTION
    WHEN NO_DATA_FOUND THEN RETURN 0;
    WHEN OTHERS THEN
        RAISE WARNING 'Erro func_contar_usuarios_por_tipo_grupo: % - %', SQLSTATE, SQLERRM;
        RETURN -1;
END;
$$
@@

CREATE OR REPLACE FUNCTION func_get_data_ultima_mensagem_chat (
    p_chat_id IN BIGINT
) RETURNS TIMESTAMPTZ LANGUAGE plpgsql
AS $$
DECLARE
    v_data_ultima_msg TIMESTAMPTZ;
BEGIN
    SELECT MAX(m.enviada_em) INTO v_data_ultima_msg
    FROM tb_sos_mensagem m
    WHERE m.tb_sos_chat_id = p_chat_id;
    RETURN v_data_ultima_msg;
EXCEPTION
    WHEN NO_DATA_FOUND THEN RETURN NULL;
    WHEN OTHERS THEN
        RAISE WARNING 'Erro func_get_data_ultima_mensagem_chat: % - %', SQLSTATE, SQLERRM;
        RETURN NULL;
END;
$$
@@

CREATE OR REPLACE PROCEDURE proc_listar_ongs_e_admins ()
LANGUAGE plpgsql
AS $$
DECLARE
    r RECORD;
BEGIN
    FOR r IN
        SELECT o.nome AS ong_nome, o.contato AS ong_contato, u.username AS admin_username
        FROM tb_sos_ong o
        JOIN tb_sos_usuario u ON o.tb_sos_usuario_id = u.id
        ORDER BY o.nome
    LOOP
        RAISE NOTICE 'ONG: % | Contato: % | Admin: %', r.ong_nome, COALESCE(r.ong_contato::TEXT, 'N/A'), r.admin_username;
    END LOOP;
EXCEPTION
    WHEN OTHERS THEN
        RAISE WARNING 'Erro proc_listar_ongs_e_admins: % - %', SQLSTATE, SQLERRM;
END;
$$
@@