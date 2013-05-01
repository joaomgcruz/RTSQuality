--
-- PostgreSQL database dump
--

-- Started on 2010-04-02 18:51:54

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;

--
-- TOC entry 364 (class 2612 OID 16401)
-- Name: plpgsql; Type: PROCEDURAL LANGUAGE; Schema: -; Owner: postgres
--

--CREATE PROCEDURAL LANGUAGE plpgsql;


ALTER PROCEDURAL LANGUAGE plpgsql OWNER TO postgres;

SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 1571 (class 1259 OID 88378)
-- Dependencies: 6
-- Name: arquivo_persistente; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE arquivo_persistente (
    id integer NOT NULL,
    arquivo oid,
    descricao character varying(255),
    nome character varying(255)
);


ALTER TABLE public.arquivo_persistente OWNER TO postgres;

--
-- TOC entry 1551 (class 1259 OID 88231)
-- Dependencies: 6
-- Name: campoextraproduto; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE campoextraproduto (
    id integer NOT NULL,
    conteudo character varying(4096),
    posicao integer NOT NULL,
    id_produto integer,
    id_tipo integer
);


ALTER TABLE public.campoextraproduto OWNER TO postgres;

--
-- TOC entry 1552 (class 1259 OID 88239)
-- Dependencies: 6
-- Name: cidade; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE cidade (
    id integer NOT NULL,
    data_cadastro timestamp without time zone,
    data_inativacao timestamp without time zone,
    inativo boolean,
    denominacao character varying(255),
    id_registro_entrada_cadastro integer,
    id_registro_entrada_inativacao integer,
    estado_id integer
);


ALTER TABLE public.cidade OWNER TO postgres;

--
-- TOC entry 1553 (class 1259 OID 88244)
-- Dependencies: 6
-- Name: creditcard; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE creditcard (
    id integer NOT NULL,
    data_cadastro timestamp without time zone,
    data_inativacao timestamp without time zone,
    inativo boolean,
    data_vencimento timestamp without time zone,
    dv integer NOT NULL,
    nome_titular character varying(512),
    numero_cartao bigint,
    id_registro_entrada_cadastro integer,
    id_registro_entrada_inativacao integer,
    id_pessoa integer
);


ALTER TABLE public.creditcard OWNER TO postgres;

--
-- TOC entry 1574 (class 1259 OID 88572)
-- Dependencies: 6
-- Name: demografico; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE demografico (
    id integer NOT NULL,
    idade integer,
    renda integer,
    id_pessoa integer
);


ALTER TABLE public.demografico OWNER TO postgres;

--
-- TOC entry 1554 (class 1259 OID 88252)
-- Dependencies: 6
-- Name: diasemana; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE diasemana (
    id integer NOT NULL,
    denominacao character varying(512)
);


ALTER TABLE public.diasemana OWNER TO postgres;

--
-- TOC entry 1555 (class 1259 OID 88260)
-- Dependencies: 6
-- Name: disponibilidadeproduto; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE disponibilidadeproduto (
    id integer NOT NULL,
    preco numeric(19,2),
    quantidade bigint NOT NULL,
    id_produto integer
);


ALTER TABLE public.disponibilidadeproduto OWNER TO postgres;

--
-- TOC entry 1556 (class 1259 OID 88265)
-- Dependencies: 6
-- Name: endereco; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE endereco (
    id integer NOT NULL,
    data_cadastro timestamp without time zone,
    data_inativacao timestamp without time zone,
    inativo boolean,
    cep bigint,
    bairro character varying(64),
    complemento character varying(255),
    endereco character varying(2048),
    numero_casa integer,
    id_registro_entrada_cadastro integer,
    id_registro_entrada_inativacao integer,
    id_cidade integer,
    id_pessoa integer
);


ALTER TABLE public.endereco OWNER TO postgres;

--
-- TOC entry 1557 (class 1259 OID 88275)
-- Dependencies: 6
-- Name: estado; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE estado (
    id integer NOT NULL,
    data_cadastro timestamp without time zone,
    data_inativacao timestamp without time zone,
    inativo boolean,
    denominacao character varying(255),
    id_registro_entrada_cadastro integer,
    id_registro_entrada_inativacao integer
);


ALTER TABLE public.estado OWNER TO postgres;

--
-- TOC entry 1558 (class 1259 OID 88280)
-- Dependencies: 6
-- Name: estoque; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE estoque (
    id integer NOT NULL,
    data_cadastro timestamp without time zone,
    data_inativacao timestamp without time zone,
    inativo boolean,
    quantidade integer NOT NULL,
    restantes integer NOT NULL,
    id_registro_entrada_cadastro integer,
    id_registro_entrada_inativacao integer,
    id_produto integer
);


ALTER TABLE public.estoque OWNER TO postgres;

--
-- TOC entry 1559 (class 1259 OID 88285)
-- Dependencies: 6
-- Name: intervaloservico; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE intervaloservico (
    id integer NOT NULL,
    data_cadastro timestamp without time zone,
    data_inativacao timestamp without time zone,
    inativo boolean,
    horafim time without time zone,
    horainicio time without time zone,
    id_registro_entrada_cadastro integer,
    id_registro_entrada_inativacao integer,
    id_dia_semana integer,
    id_produto integer
);


ALTER TABLE public.intervaloservico OWNER TO postgres;

--
-- TOC entry 1560 (class 1259 OID 88290)
-- Dependencies: 6
-- Name: mensagem; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE mensagem (
    id integer NOT NULL,
    data_cadastro timestamp without time zone,
    data_inativacao timestamp without time zone,
    inativo boolean,
    ativado boolean NOT NULL,
    denominacao character varying(512),
    id_registro_entrada_cadastro integer,
    id_registro_entrada_inativacao integer
);


ALTER TABLE public.mensagem OWNER TO postgres;

--
-- TOC entry 1561 (class 1259 OID 88298)
-- Dependencies: 6
-- Name: parametro; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE parametro (
    id integer NOT NULL,
    codigo character varying(255),
    descricao character varying(255),
    nome character varying(255),
    valor character varying(255)
);


ALTER TABLE public.parametro OWNER TO postgres;

--
-- TOC entry 1562 (class 1259 OID 88308)
-- Dependencies: 6
-- Name: pessoa; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE pessoa (
    id integer NOT NULL,
    data_cadastro timestamp without time zone,
    data_inativacao timestamp without time zone,
    inativo boolean,
    cpf bigint,
    rg bigint,
    data_nacimento timestamp without time zone,
    email character varying(128),
    nome character varying(512),
    id_registro_entrada_cadastro integer,
    id_registro_entrada_inativacao integer
);


ALTER TABLE public.pessoa OWNER TO postgres;

--
-- TOC entry 1563 (class 1259 OID 88316)
-- Dependencies: 6
-- Name: planejamentoservico; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE planejamentoservico (
    id integer NOT NULL,
    data_cadastro timestamp without time zone,
    data_inativacao timestamp without time zone,
    inativo boolean,
    equipe character varying(1024),
    id_registro_entrada_cadastro integer,
    id_registro_entrada_inativacao integer,
    id_produto integer
);


ALTER TABLE public.planejamentoservico OWNER TO postgres;

--
-- TOC entry 1573 (class 1259 OID 88564)
-- Dependencies: 6
-- Name: produto; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE produto (
    id integer NOT NULL,
    data_cadastro timestamp without time zone,
    data_inativacao timestamp without time zone,
    inativo boolean,
    weight double precision NOT NULL,
    autorizado boolean,
    caracteristicas character varying(4096),
    nome character varying(1024),
    observacao character varying(2048),
    preco double precision NOT NULL,
    size character varying(128),
    url_imagem character varying(4096),
    warranty character varying(128),
    id_registro_entrada_cadastro integer,
    id_registro_entrada_inativacao integer,
    id_subtipo integer,
    id_tipo integer,
    id_tipo_armazenamento integer
);


ALTER TABLE public.produto OWNER TO postgres;

--
-- TOC entry 1564 (class 1259 OID 88324)
-- Dependencies: 6
-- Name: registroentrada; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE registroentrada (
    id integer NOT NULL
);


ALTER TABLE public.registroentrada OWNER TO postgres;

--
-- TOC entry 1565 (class 1259 OID 88329)
-- Dependencies: 6
-- Name: securitycard; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE securitycard (
    id integer NOT NULL,
    data_cadastro timestamp without time zone,
    data_inativacao timestamp without time zone,
    inativo boolean,
    login character varying(255),
    senha character varying(255),
    id_registro_entrada_cadastro integer,
    id_registro_entrada_inativacao integer
);


ALTER TABLE public.securitycard OWNER TO postgres;

--
-- TOC entry 1572 (class 1259 OID 88561)
-- Dependencies: 6
-- Name: seq_id; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE seq_id
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


ALTER TABLE public.seq_id OWNER TO postgres;

--
-- TOC entry 1977 (class 0 OID 0)
-- Dependencies: 1572
-- Name: seq_id; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('seq_id', 32, true);


--
-- TOC entry 1566 (class 1259 OID 88337)
-- Dependencies: 6
-- Name: subtipoproduto; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE subtipoproduto (
    id integer NOT NULL,
    data_cadastro timestamp without time zone,
    data_inativacao timestamp without time zone,
    inativo boolean,
    denominacao character varying(512),
    descricao character varying(1024),
    id_registro_entrada_cadastro integer,
    id_registro_entrada_inativacao integer,
    id_tipo integer
);


ALTER TABLE public.subtipoproduto OWNER TO postgres;

--
-- TOC entry 1567 (class 1259 OID 88345)
-- Dependencies: 6
-- Name: tipoarmazenamento; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE tipoarmazenamento (
    id integer NOT NULL,
    descricao character varying(512)
);


ALTER TABLE public.tipoarmazenamento OWNER TO postgres;

--
-- TOC entry 1568 (class 1259 OID 88353)
-- Dependencies: 6
-- Name: tipocampoextraproduto; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE tipocampoextraproduto (
    id integer NOT NULL,
    denominacao character varying(1024),
    descricao character varying(1024)
);


ALTER TABLE public.tipocampoextraproduto OWNER TO postgres;

--
-- TOC entry 1569 (class 1259 OID 88361)
-- Dependencies: 6
-- Name: tipoproduto; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE tipoproduto (
    id integer NOT NULL,
    data_cadastro timestamp without time zone,
    data_inativacao timestamp without time zone,
    inativo boolean,
    denominacao character varying(512),
    descricao character varying(1024),
    id_registro_entrada_cadastro integer,
    id_registro_entrada_inativacao integer
);


ALTER TABLE public.tipoproduto OWNER TO postgres;

--
-- TOC entry 1570 (class 1259 OID 88369)
-- Dependencies: 6
-- Name: usuario; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE usuario (
    id integer NOT NULL,
    data_cadastro timestamp without time zone,
    data_inativacao timestamp without time zone,
    inativo boolean,
    id_registro_entrada_cadastro integer,
    id_registro_entrada_inativacao integer,
    id_pessoa integer,
    id_security_card integer NOT NULL
);


ALTER TABLE public.usuario OWNER TO postgres;

--
-- TOC entry 1969 (class 0 OID 88378)
-- Dependencies: 1571
-- Data for Name: arquivo_persistente; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO arquivo_persistente (id, arquivo, descricao, nome) VALUES (13, 65975, 'foto', 'Foto');


--
-- TOC entry 1949 (class 0 OID 88231)
-- Dependencies: 1551
-- Data for Name: campoextraproduto; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 1950 (class 0 OID 88239)
-- Dependencies: 1552
-- Data for Name: cidade; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO cidade (id, data_cadastro, data_inativacao, inativo, denominacao, id_registro_entrada_cadastro, id_registro_entrada_inativacao, estado_id) VALUES (18, NULL, NULL, false, 'Natal', NULL, NULL, 19);
INSERT INTO cidade (id, data_cadastro, data_inativacao, inativo, denominacao, id_registro_entrada_cadastro, id_registro_entrada_inativacao, estado_id) VALUES (5, NULL, NULL, false, 'Natal', NULL, NULL, 6);
INSERT INTO cidade (id, data_cadastro, data_inativacao, inativo, denominacao, id_registro_entrada_cadastro, id_registro_entrada_inativacao, estado_id) VALUES (14, NULL, NULL, false, 'Natal', NULL, NULL, 15);
INSERT INTO cidade (id, data_cadastro, data_inativacao, inativo, denominacao, id_registro_entrada_cadastro, id_registro_entrada_inativacao, estado_id) VALUES (29, NULL, NULL, false, 'a', NULL, NULL, 30);


--
-- TOC entry 1951 (class 0 OID 88244)
-- Dependencies: 1553
-- Data for Name: creditcard; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO creditcard (id, data_cadastro, data_inativacao, inativo, data_vencimento, dv, nome_titular, numero_cartao, id_registro_entrada_cadastro, id_registro_entrada_inativacao, id_pessoa) VALUES (16, NULL, NULL, false, '2090-10-11 00:00:00', 789, 'MARIO TORRES', 1234123412341235, NULL, NULL, 15);
INSERT INTO creditcard (id, data_cadastro, data_inativacao, inativo, data_vencimento, dv, nome_titular, numero_cartao, id_registro_entrada_cadastro, id_registro_entrada_inativacao, id_pessoa) VALUES (3, NULL, NULL, false, '2090-10-11 00:00:00', 123, 'MARIO TORRES', 1234123412341234, NULL, NULL, 2);
INSERT INTO creditcard (id, data_cadastro, data_inativacao, inativo, data_vencimento, dv, nome_titular, numero_cartao, id_registro_entrada_cadastro, id_registro_entrada_inativacao, id_pessoa) VALUES (12, NULL, NULL, false, '2011-10-10 00:00:00', 123, 'MARIO TORRES', 1234123412341234, NULL, NULL, 11);
INSERT INTO creditcard (id, data_cadastro, data_inativacao, inativo, data_vencimento, dv, nome_titular, numero_cartao, id_registro_entrada_cadastro, id_registro_entrada_inativacao, id_pessoa) VALUES (27, NULL, NULL, false, '2030-03-11 00:00:00', 1, 'a', 1, NULL, NULL, 26);


--
-- TOC entry 1971 (class 0 OID 88572)
-- Dependencies: 1574
-- Data for Name: demografico; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO demografico (id, idade, renda, id_pessoa) VALUES (7, 12, 1, 2);
INSERT INTO demografico (id, idade, renda, id_pessoa) VALUES (16, 22, 8500, 11);
INSERT INTO demografico (id, idade, renda, id_pessoa) VALUES (31, 11, 123, 26);


--
-- TOC entry 1952 (class 0 OID 88252)
-- Dependencies: 1554
-- Data for Name: diasemana; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO diasemana (id, denominacao) VALUES (1, 'Domingo');
INSERT INTO diasemana (id, denominacao) VALUES (2, 'Segunda-Feira');
INSERT INTO diasemana (id, denominacao) VALUES (3, 'Terça-Feira');
INSERT INTO diasemana (id, denominacao) VALUES (4, 'Quarta-Feira');
INSERT INTO diasemana (id, denominacao) VALUES (5, 'Quinta-Feira');
INSERT INTO diasemana (id, denominacao) VALUES (6, 'Sexta-Feira');
INSERT INTO diasemana (id, denominacao) VALUES (7, 'Sabado');


--
-- TOC entry 1953 (class 0 OID 88260)
-- Dependencies: 1555
-- Data for Name: disponibilidadeproduto; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 1954 (class 0 OID 88265)
-- Dependencies: 1556
-- Data for Name: endereco; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO endereco (id, data_cadastro, data_inativacao, inativo, cep, bairro, complemento, endereco, numero_casa, id_registro_entrada_cadastro, id_registro_entrada_inativacao, id_cidade, id_pessoa) VALUES (17, NULL, NULL, false, 59094520, 'Ponta Negra', 'Casa D', 'Rua Praia de Jacuma', 9092, NULL, NULL, 18, 15);
INSERT INTO endereco (id, data_cadastro, data_inativacao, inativo, cep, bairro, complemento, endereco, numero_casa, id_registro_entrada_cadastro, id_registro_entrada_inativacao, id_cidade, id_pessoa) VALUES (4, NULL, NULL, false, 1, 'Ponta Negra', 'Casa D', 'Praia de Jacuma', 1, NULL, NULL, 5, 2);
INSERT INTO endereco (id, data_cadastro, data_inativacao, inativo, cep, bairro, complemento, endereco, numero_casa, id_registro_entrada_cadastro, id_registro_entrada_inativacao, id_cidade, id_pessoa) VALUES (13, NULL, NULL, false, 59094520, 'Ponta Negra', 'Casa D', 'Rua Praia de Jacuma', 9092, NULL, NULL, 14, 11);
INSERT INTO endereco (id, data_cadastro, data_inativacao, inativo, cep, bairro, complemento, endereco, numero_casa, id_registro_entrada_cadastro, id_registro_entrada_inativacao, id_cidade, id_pessoa) VALUES (28, NULL, NULL, false, 1, 'a', '1', 'mario1', 1, NULL, NULL, 29, 26);


--
-- TOC entry 1955 (class 0 OID 88275)
-- Dependencies: 1557
-- Data for Name: estado; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO estado (id, data_cadastro, data_inativacao, inativo, denominacao, id_registro_entrada_cadastro, id_registro_entrada_inativacao) VALUES (19, NULL, NULL, false, 'Rio Grande do Norte', NULL, NULL);
INSERT INTO estado (id, data_cadastro, data_inativacao, inativo, denominacao, id_registro_entrada_cadastro, id_registro_entrada_inativacao) VALUES (6, NULL, NULL, false, 'Pará', NULL, NULL);
INSERT INTO estado (id, data_cadastro, data_inativacao, inativo, denominacao, id_registro_entrada_cadastro, id_registro_entrada_inativacao) VALUES (15, NULL, NULL, false, 'Rio Grando do Norte', NULL, NULL);
INSERT INTO estado (id, data_cadastro, data_inativacao, inativo, denominacao, id_registro_entrada_cadastro, id_registro_entrada_inativacao) VALUES (30, NULL, NULL, false, 'a', NULL, NULL);


--
-- TOC entry 1956 (class 0 OID 88280)
-- Dependencies: 1558
-- Data for Name: estoque; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO estoque (id, data_cadastro, data_inativacao, inativo, quantidade, restantes, id_registro_entrada_cadastro, id_registro_entrada_inativacao, id_produto) VALUES (8, '2009-12-07 21:42:40.743', NULL, false, 10, 10, NULL, NULL, 7);
INSERT INTO estoque (id, data_cadastro, data_inativacao, inativo, quantidade, restantes, id_registro_entrada_cadastro, id_registro_entrada_inativacao, id_produto) VALUES (11, '2009-12-11 10:58:07.499', NULL, false, 50, 50, NULL, NULL, 10);
INSERT INTO estoque (id, data_cadastro, data_inativacao, inativo, quantidade, restantes, id_registro_entrada_cadastro, id_registro_entrada_inativacao, id_produto) VALUES (12, '2009-12-11 12:26:23.923', NULL, false, 100, 100, NULL, NULL, 7);
INSERT INTO estoque (id, data_cadastro, data_inativacao, inativo, quantidade, restantes, id_registro_entrada_cadastro, id_registro_entrada_inativacao, id_produto) VALUES (9, '2009-12-12 21:24:39.361', NULL, false, 100, 100, NULL, NULL, 7);
INSERT INTO estoque (id, data_cadastro, data_inativacao, inativo, quantidade, restantes, id_registro_entrada_cadastro, id_registro_entrada_inativacao, id_produto) VALUES (24, '2010-03-03 16:22:50.699', NULL, false, 100, 100, NULL, NULL, 7);


--
-- TOC entry 1957 (class 0 OID 88285)
-- Dependencies: 1559
-- Data for Name: intervaloservico; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 1958 (class 0 OID 88290)
-- Dependencies: 1560
-- Data for Name: mensagem; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO mensagem (id, data_cadastro, data_inativacao, inativo, ativado, denominacao, id_registro_entrada_cadastro, id_registro_entrada_inativacao) VALUES (9, '2009-12-10 21:31:39.713', NULL, false, true, 'Bem vindo, Dezembro é época de confraternizar e claro, dar muitos presentes!!!', NULL, NULL);
INSERT INTO mensagem (id, data_cadastro, data_inativacao, inativo, ativado, denominacao, id_registro_entrada_cadastro, id_registro_entrada_inativacao) VALUES (20, '2009-12-12 23:59:40.833', NULL, false, true, 'Feliz Natal !!!', NULL, NULL);


--
-- TOC entry 1959 (class 0 OID 88298)
-- Dependencies: 1561
-- Data for Name: parametro; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO parametro (id, codigo, descricao, nome, valor) VALUES (1, '0001', 'Representa a feature de Presentation Options referente à quantidade de produto exibidos na página principal.', 'QUANTIDADE_PRODUTOS_EXIBICAO', '20');
INSERT INTO parametro (id, codigo, descricao, nome, valor) VALUES (6, '0006', '', 'QUANTIDADE_PRODUTOS_PAGINACAO', '20');
INSERT INTO parametro (id, codigo, descricao, nome, valor) VALUES (2, '0002', 'Representa a feature de Presentation Options referente à exibição ou não de imagens de produtos.', 'EXIBIR_IMAGENS_PRODUTO', 'true');
INSERT INTO parametro (id, codigo, descricao, nome, valor) VALUES (3, '0003', 'Representa a feature de Content Aproval referente à necessidade de uma terceira pessoa confirmar o cadastro do produto.', 'NECESSARIO_AVAL_LIBERACAO_PRODUTO', 'true');
INSERT INTO parametro (id, codigo, descricao, nome, valor) VALUES (4, '0004', 'Representa a feature Registration referente ao cadastro dos usuários.', 'REGISTRATION', 'true');
INSERT INTO parametro (id, codigo, descricao, nome, valor) VALUES (5, '0005', '', 'WELLCOME_MENSAGE', 'true');


--
-- TOC entry 1960 (class 0 OID 88308)
-- Dependencies: 1562
-- Data for Name: pessoa; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO pessoa (id, data_cadastro, data_inativacao, inativo, cpf, rg, data_nacimento, email, nome, id_registro_entrada_cadastro, id_registro_entrada_inativacao) VALUES (15, NULL, NULL, false, 12345678900, 1234567, '1987-04-06 00:00:00', 'scaramuzzini@gmail.com', 'Mario Torres2', NULL, NULL);
INSERT INTO pessoa (id, data_cadastro, data_inativacao, inativo, cpf, rg, data_nacimento, email, nome, id_registro_entrada_cadastro, id_registro_entrada_inativacao) VALUES (2, NULL, NULL, false, 1, 1, '1997-04-06 00:00:00', 'scaramuzzini@gmail.com', 'Mario', NULL, NULL);
INSERT INTO pessoa (id, data_cadastro, data_inativacao, inativo, cpf, rg, data_nacimento, email, nome, id_registro_entrada_cadastro, id_registro_entrada_inativacao) VALUES (11, NULL, NULL, false, 12345678900, 1234567, '1987-04-06 00:00:00', 'scaramuzzini@gmail.com', 'Mario Torres', NULL, NULL);
INSERT INTO pessoa (id, data_cadastro, data_inativacao, inativo, cpf, rg, data_nacimento, email, nome, id_registro_entrada_cadastro, id_registro_entrada_inativacao) VALUES (26, NULL, NULL, false, 1, 1, '1998-05-16 00:00:00', 's@s.com', 'mario1', NULL, NULL);


--
-- TOC entry 1961 (class 0 OID 88316)
-- Dependencies: 1563
-- Data for Name: planejamentoservico; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 1970 (class 0 OID 88564)
-- Dependencies: 1573
-- Data for Name: produto; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO produto (id, data_cadastro, data_inativacao, inativo, weight, autorizado, caracteristicas, nome, observacao, preco, size, url_imagem, warranty, id_registro_entrada_cadastro, id_registro_entrada_inativacao, id_subtipo, id_tipo, id_tipo_armazenamento) VALUES (7, '2009-12-07 21:15:15.587', NULL, false, 1, true, 'TV', 'TV Plasma 42" LG 3ED42', 'TV', 2799.9499999999998, '1', NULL, '1', NULL, NULL, 3, 1, 1);
INSERT INTO produto (id, data_cadastro, data_inativacao, inativo, weight, autorizado, caracteristicas, nome, observacao, preco, size, url_imagem, warranty, id_registro_entrada_cadastro, id_registro_entrada_inativacao, id_subtipo, id_tipo, id_tipo_armazenamento) VALUES (10, '2009-12-11 00:32:21.112', NULL, false, 10, true, 'Este aqui é o equivalente a DetailedDescription', 'TV Plasma 26" LG 3ED26', 'Não tenho certeza quanto a existência deste campoEste aqui é o equivalente a DetailedDescription', 1399.9000000000001, '135x80x20', NULL, '3 meses.', NULL, NULL, 3, 1, 1);
INSERT INTO produto (id, data_cadastro, data_inativacao, inativo, weight, autorizado, caracteristicas, nome, observacao, preco, size, url_imagem, warranty, id_registro_entrada_cadastro, id_registro_entrada_inativacao, id_subtipo, id_tipo, id_tipo_armazenamento) VALUES (22, '2009-12-11 19:51:56.207', NULL, false, 2, true, '', 'A Cabana', '', 19.899999999999999, '10x20x2', NULL, '3 meses.', NULL, NULL, 4, 2, 1);
INSERT INTO produto (id, data_cadastro, data_inativacao, inativo, weight, autorizado, caracteristicas, nome, observacao, preco, size, url_imagem, warranty, id_registro_entrada_cadastro, id_registro_entrada_inativacao, id_subtipo, id_tipo, id_tipo_armazenamento) VALUES (23, '2009-12-11 20:39:41.469', NULL, true, 2, false, 'Teste...', 'A menina que roubava livros', 'Teste....', 15, '10x20x2', NULL, '3 meses.', NULL, NULL, 4, 2, 1);
INSERT INTO produto (id, data_cadastro, data_inativacao, inativo, weight, autorizado, caracteristicas, nome, observacao, preco, size, url_imagem, warranty, id_registro_entrada_cadastro, id_registro_entrada_inativacao, id_subtipo, id_tipo, id_tipo_armazenamento) VALUES (24, '2009-12-11 20:40:27.458', NULL, false, 2, false, 'Muito bom!', 'A menina que roubava livros', 'Livro de teste', 15, '10x20x2', NULL, '3 meses.', NULL, NULL, 4, 2, 1);
INSERT INTO produto (id, data_cadastro, data_inativacao, inativo, weight, autorizado, caracteristicas, nome, observacao, preco, size, url_imagem, warranty, id_registro_entrada_cadastro, id_registro_entrada_inativacao, id_subtipo, id_tipo, id_tipo_armazenamento) VALUES (26, '2009-12-11 23:24:46.064', NULL, false, 2, true, 'Livro capa dura.', 'O homem que matou Getúlio Vargas', 'Autor:Jô Soares', 40, '10x20x2', NULL, '3 meses.', NULL, NULL, 5, 2, 1);
INSERT INTO produto (id, data_cadastro, data_inativacao, inativo, weight, autorizado, caracteristicas, nome, observacao, preco, size, url_imagem, warranty, id_registro_entrada_cadastro, id_registro_entrada_inativacao, id_subtipo, id_tipo, id_tipo_armazenamento) VALUES (27, '2009-12-11 23:34:51.515', NULL, false, 25.699999999999999, true, '...', 'TV CRT 29" Panasonic P29CRT', '...', 499, '10x20x2', NULL, '12 meses.', NULL, NULL, 3, 1, 1);
INSERT INTO produto (id, data_cadastro, data_inativacao, inativo, weight, autorizado, caracteristicas, nome, observacao, preco, size, url_imagem, warranty, id_registro_entrada_cadastro, id_registro_entrada_inativacao, id_subtipo, id_tipo, id_tipo_armazenamento) VALUES (29, '2009-12-11 23:38:49.521', NULL, false, 10, false, 'Possui TV Digital. Consultar especificações abaixo.', 'TV LCD Sony Bravia 50" SLCD50', 'Melhor TV da loja.', 3500, '135x80x20', NULL, '12 meses.', NULL, NULL, 3, 1, 1);
INSERT INTO produto (id, data_cadastro, data_inativacao, inativo, weight, autorizado, caracteristicas, nome, observacao, preco, size, url_imagem, warranty, id_registro_entrada_cadastro, id_registro_entrada_inativacao, id_subtipo, id_tipo, id_tipo_armazenamento) VALUES (25, '2009-12-11 23:20:24.617', NULL, false, 2, true, 'Melhor livro e agora com a melhor série.', 'Robinson Cruzoe', 'Melhor livro.', 320.55000000000001, '10x20x2', NULL, '12 meses.', NULL, NULL, 5, 2, 1);
INSERT INTO produto (id, data_cadastro, data_inativacao, inativo, weight, autorizado, caracteristicas, nome, observacao, preco, size, url_imagem, warranty, id_registro_entrada_cadastro, id_registro_entrada_inativacao, id_subtipo, id_tipo, id_tipo_armazenamento) VALUES (31, '2009-12-12 10:19:59.026', NULL, false, 4, true, 'Deitel -4ª Edição', 'C++ Como Programar', 'Deitel', 150, '10x20x10', NULL, '3 meses.', NULL, NULL, 5, 2, 1);
INSERT INTO produto (id, data_cadastro, data_inativacao, inativo, weight, autorizado, caracteristicas, nome, observacao, preco, size, url_imagem, warranty, id_registro_entrada_cadastro, id_registro_entrada_inativacao, id_subtipo, id_tipo, id_tipo_armazenamento) VALUES (30, '2009-12-12 10:17:33.987', NULL, false, 4, true, 'Deitel - 6ª Edição', 'Java Como Programar', 'Deitel', 150, '10x20x10', NULL, '3 meses.', NULL, NULL, 5, 2, 1);


--
-- TOC entry 1962 (class 0 OID 88324)
-- Dependencies: 1564
-- Data for Name: registroentrada; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 1963 (class 0 OID 88329)
-- Dependencies: 1565
-- Data for Name: securitycard; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO securitycard (id, data_cadastro, data_inativacao, inativo, login, senha, id_registro_entrada_cadastro, id_registro_entrada_inativacao) VALUES (21, NULL, NULL, false, 'mario2', 'cea4e07a4b9b78554c5f26b20ecc0517', NULL, NULL);
INSERT INTO securitycard (id, data_cadastro, data_inativacao, inativo, login, senha, id_registro_entrada_cadastro, id_registro_entrada_inativacao) VALUES (8, NULL, NULL, false, 'iha', '30bbffd3062e6fc12f248b9eb4fec232', NULL, NULL);
INSERT INTO securitycard (id, data_cadastro, data_inativacao, inativo, login, senha, id_registro_entrada_cadastro, id_registro_entrada_inativacao) VALUES (17, NULL, NULL, false, 'mario', 'de2f15d014d40b93578d255e6221fd60', NULL, NULL);
INSERT INTO securitycard (id, data_cadastro, data_inativacao, inativo, login, senha, id_registro_entrada_cadastro, id_registro_entrada_inativacao) VALUES (32, NULL, NULL, false, 'mario1', '490eb03d394fd69c1eb0a116983cf3f4', NULL, NULL);


--
-- TOC entry 1964 (class 0 OID 88337)
-- Dependencies: 1566
-- Data for Name: subtipoproduto; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO subtipoproduto (id, data_cadastro, data_inativacao, inativo, denominacao, descricao, id_registro_entrada_cadastro, id_registro_entrada_inativacao, id_tipo) VALUES (3, '2009-12-07 21:08:22.345', NULL, false, 'Televisores', 'Televisores', NULL, NULL, 1);
INSERT INTO subtipoproduto (id, data_cadastro, data_inativacao, inativo, denominacao, descricao, id_registro_entrada_cadastro, id_registro_entrada_inativacao, id_tipo) VALUES (4, '2009-12-07 21:08:30.095', NULL, false, 'Ficção', 'Ficção', NULL, NULL, 2);
INSERT INTO subtipoproduto (id, data_cadastro, data_inativacao, inativo, denominacao, descricao, id_registro_entrada_cadastro, id_registro_entrada_inativacao, id_tipo) VALUES (5, '2009-12-07 21:08:40.898', NULL, false, 'Suspense', 'Suspense', NULL, NULL, 2);
INSERT INTO subtipoproduto (id, data_cadastro, data_inativacao, inativo, denominacao, descricao, id_registro_entrada_cadastro, id_registro_entrada_inativacao, id_tipo) VALUES (6, '2009-12-07 21:08:49.273', '2009-12-11 11:40:28.129', true, 'Técnico', 'Técnico', NULL, NULL, 2);
INSERT INTO subtipoproduto (id, data_cadastro, data_inativacao, inativo, denominacao, descricao, id_registro_entrada_cadastro, id_registro_entrada_inativacao, id_tipo) VALUES (21, '2009-12-13 00:03:19.997', NULL, false, 'CDs', 'Somente CDS', NULL, NULL, 18);
INSERT INTO subtipoproduto (id, data_cadastro, data_inativacao, inativo, denominacao, descricao, id_registro_entrada_cadastro, id_registro_entrada_inativacao, id_tipo) VALUES (22, '2009-12-13 00:06:54.672', NULL, false, 'Cama de Casal', 'Cama de Casal', NULL, NULL, 19);


--
-- TOC entry 1965 (class 0 OID 88345)
-- Dependencies: 1567
-- Data for Name: tipoarmazenamento; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO tipoarmazenamento (id, descricao) VALUES (1, 'Físico');
INSERT INTO tipoarmazenamento (id, descricao) VALUES (2, 'Eletrônico');
INSERT INTO tipoarmazenamento (id, descricao) VALUES (3, 'Serviço');


--
-- TOC entry 1966 (class 0 OID 88353)
-- Dependencies: 1568
-- Data for Name: tipocampoextraproduto; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO tipocampoextraproduto (id, denominacao, descricao) VALUES (28, 'TV Digital', 'Este aparelho conta com o Conversor integrado de TV Digital.');


--
-- TOC entry 1967 (class 0 OID 88361)
-- Dependencies: 1569
-- Data for Name: tipoproduto; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO tipoproduto (id, data_cadastro, data_inativacao, inativo, denominacao, descricao, id_registro_entrada_cadastro, id_registro_entrada_inativacao) VALUES (1, '2009-12-07 21:08:03.892', NULL, false, 'Eletrônicos', 'Eletrônicos', NULL, NULL);
INSERT INTO tipoproduto (id, data_cadastro, data_inativacao, inativo, denominacao, descricao, id_registro_entrada_cadastro, id_registro_entrada_inativacao) VALUES (2, '2009-12-07 21:08:10.846', NULL, false, 'Livros', 'Livros', NULL, NULL);
INSERT INTO tipoproduto (id, data_cadastro, data_inativacao, inativo, denominacao, descricao, id_registro_entrada_cadastro, id_registro_entrada_inativacao) VALUES (18, '2009-12-12 23:48:02.727', NULL, false, 'CDs & DVDs', 'Todos os tipos de CDs e DVDs do site.', NULL, NULL);
INSERT INTO tipoproduto (id, data_cadastro, data_inativacao, inativo, denominacao, descricao, id_registro_entrada_cadastro, id_registro_entrada_inativacao) VALUES (19, '2009-12-12 23:49:55.431', NULL, false, 'Casa & Banho', 'Lençois, fronhas, travesseiros, toalhas de banho, etc.', NULL, NULL);


--
-- TOC entry 1968 (class 0 OID 88369)
-- Dependencies: 1570
-- Data for Name: usuario; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO usuario (id, data_cadastro, data_inativacao, inativo, id_registro_entrada_cadastro, id_registro_entrada_inativacao, id_pessoa, id_security_card) VALUES (14, '2009-12-11 19:40:43.367', NULL, false, NULL, NULL, 15, 21);
INSERT INTO usuario (id, data_cadastro, data_inativacao, inativo, id_registro_entrada_cadastro, id_registro_entrada_inativacao, id_pessoa, id_security_card) VALUES (1, '2009-12-12 20:41:55.839', NULL, false, NULL, NULL, 2, 8);
INSERT INTO usuario (id, data_cadastro, data_inativacao, inativo, id_registro_entrada_cadastro, id_registro_entrada_inativacao, id_pessoa, id_security_card) VALUES (10, '2009-12-12 23:36:50.019', NULL, false, NULL, NULL, 11, 17);
INSERT INTO usuario (id, data_cadastro, data_inativacao, inativo, id_registro_entrada_cadastro, id_registro_entrada_inativacao, id_pessoa, id_security_card) VALUES (25, '2010-03-25 17:22:55.458', NULL, false, NULL, NULL, 26, 32);


--
-- TOC entry 1972 (class 0 OID 0)
-- Data for Name: BLOBS; Type: BLOBS; Schema: -; Owner: 
--

SET search_path = pg_catalog;

BEGIN;

SELECT lo_open(lo_create(50130), 131072);
SELECT lowrite(0, 'PK\\003\\004\\024\\000\\000\\000\\010\\000\\242n$;\\012\\256]\\261\\264\\035\\000\\000\\312R\\000\\000\\020\\000\\000\\000allfontsinfo.txt\\235\\\\\\333\\222\\033\\307\\221}\\366D\\314?\\364\\350\\005\\303\\010\\000+\\221\\224%\\331\\033\\341\\245$\\322\\032\\353B\\206H[\\366\\306\\276\\024\\200\\002P\\236F7\\334\\227\\001\\241\\257\\337sNV\\365e\\010P\\232\\221m\\231\\354KuUV^N\\236\\314\\302\\367\\376.\\024\\331\\367\\241\\330d\\263\\354\\225_T\\255\\253\\216\\331\\323O?\\375\\352\\362\\342\\362\\342\\273p\\225\\275\\333\\272\\342\\266\\316\\326e\\225U\\336\\255\\370\\344\\356\\210\\277\\026M\\235\\335\\024\\3532[\\207\\334\\317\\371\\364\\217\\307l\\331V\\225/\\232\\374\\230\\271;\\027r\\267\\310}|\\324\\025\\253\\254\\331\\226\\265\\317\\360\\275\\225\\277\\363y\\271\\337\\341\\321\\314U~\\312ku\\271\\363\\370W\\325d\\345:\\233\\224\\325\\312W\\223?q\\330O?\\233k\\202\\315\\026\\377\\252\\263o\\\\\\236\\207M\\345\\366\\333\\260t\\270\\373\\364\\374\\335\\354\\246qyX\\342\\241g\\037y\\350\\207\\260\\3316x\\346\\371\\350\\231\\177b\\302\\276\\302\\345\\317O\\\\\\316^\\267M\\036\\012\\217\\333\\177\\034\\335\\376k\\211?\\374\\247\\345\\215/F7\\336\\370\\246*\\227\\267\\270\\376\\345\\251\\353\\335$\\276\\032\\335}]m\\\\\\241e~\\366\\351\\350\\306w\\230\\006.~\\366\\301\\3054\\320gc\\261|\\357\\363\\274\\011\\267\\034h,\\012IH\\023\\376l\\274\\376o\\266a\\347|\\3057\\306\\022x\\353\\212\\032\\027\\307\\353\\376e\\033~\\375u\\341\\212\\015\\356\\214\\027\\376*w\\3656\\336\\371\\362\\336\\235\\262\\255B\\275\\365\\034n\\274\\356\\037 [We\\337_^<\\035\\257\\373M\\025\\212\\006\\177\\272\\015X\\343\\323\\361\\362\\337\\356\\303-\\326\\361\\364\\351\\207Ws_\\343#O\\307K\\377\\245*\\033>\\177o\\337\\353&4\\313-\\256\\217\\227\\375\\242(\\374{\\\\\\035\\257\\373M\\271\\337\\273\\274\\304\\276>\\035/\\373\\345nQ\\225\\001\\252r\\304\\255/O~w\\274\\344Wn\\351\\233\\343\\0367\\236\\215\\227\\374\\362}\\003\\243\\010e\\201;\\343\\005\\377\\303W5v\\017+{6^\\363\\017\\355^\\312\\371\\354\\331\\211\\313&\\213g\\367w\\033;\\321\\354\\034\\357\\214\\327\\375K\\310\\363\\362\\200\\313\\177<q9\\216uO\\325\\251O\\305\\316S\\\\\\317\\306K\\177Wa\\205\\207*4\\264\\254gc\\001\\274|\\357u\\371\\371x\\3717\\305\\355\\021N\\347\\026r|~\\317\\021\\344\\016#\\361\\372x\\365/7\\033|\\005\\372\\366|\\274|hBUn\\216T\\351\\347\\343\\325\\177_\\224\\213\\232\\002~>^\\3737e\\321\\026\\253\\252\\335\\341\\316x\\371o}\\025\\326\\\\\\347\\037\\376\\200{_\\234\\276g\\266\\250''\\276<\\363\\304\\337\\363\\246\\302\\026\\312d\\237\\217\\345\\361\\327\\266\\361\\305\\302W\\233\\354\\017\\263\\254(\\233\\354\\350\\233\\336\\253^^|>\\226\\323\\253\\022Su\\246''\\237\\217\\005\\365\\316c\\021\\307\\\\/\\3353\\216&TU\\273\\307\\365\\261\\250\\276\\316\\313\\262\\360W\\270>\\026\\324\\333\\255\\367{\\230,\\374{\\206\\233\\3674\\245\\362\\313\\333\\006\\226\\316\\017]^\\314~\\357?\\227\\027_#\\030\\034\\246\\331\\261l\\263\\003\\364\\012\\021\\005\\216L\\361\\000\\021\\243\\205p,|\\004D\\233j\\2475f\\370o\\212B\\363\\014Jr2|L\\361\\276?2\\302\\374i\\366\\240\\011\\375v\\330\\271\\274\\010u\\266p\\265_q*\\313\\301\\315,\\367\\015\\024\\031/e\\210\\234\\253:;\\340\\3426\\273\\301uW\\025x\\3365Y\\275\\334\\226e\\216\\210\\353\\262E\\320\\214]\\266,\\213u[km\\353l\\025\\326k\\317H\\312\\270\\273\\253\\261\\016\\274\\205!\\340\\313Vs\\374\\241(\\017Y[S>\\230\\206}\\220\\017*J\\357\\312ZCb\\355\\232\\301`v\\3074\\020\\234\\034\\344\\274\\257\\312U\\273\\364\\230\\307\\277[\\274s(\\253\\030\\350w\\274V HWx\\271@\\334\\337\\371\\035TQC\\266\\373\\275\\257\\226Xy6\\371i\\0227h>\\237\\233\\320~[\\270\\361\\037I\\370\\367\\204\\356\\313\\213w\\\\c6\\024\\367\\271\\227\\246X\\331\\252,&\\215\\004\\245\\331\\036\\\\\\\\\\263\\364\\303\\240H\\323V\\020r\\213\\005o}\\221M\\202\\276\\0240\\366$[\\034)\\205UFS\\313\\247x\\011#F\\035L\\242;\\224m\\276\\312v\\016\\037p\\331\\004\\220(\\237d6\\004d\\206\\315l\\312\\362\\026{PyL\\205J\\200\\327\\212\\301(\\241\\261\\021\\346\\264\\240\\207\\213\\3547\\201\\2146\\342\\246\\371]\\362\\242&\\355\\334\\312sR\\013\\277t\\324\\250\\275/\\367\\004m\\336\\013\\352-\\034\\300\\311\\272*w4\\267\\203_\\324\\360\\3352F]\\203\\276\\016\\341\\240\\253\\241\\236R \\227\\311\\251Ae\\240CR\\352\\0311\\336\\256\\205%p[j\\267\\263\\275q\\367 \\020\\364\\015w\\334\\202\\333\\203\\027\\326n\\331\\\\e/rm\\304\\326\\335yz\\302\\260\\364+\\333\\015|yR\\320''`\\017\\016^\\357\\307-\\366\\204\\233%V\\305\\365\\313\\022\\274\\273;f5B2\\246\\202\\341\\364\\265\\253\\307l\\001\\\\"\\005p\\032\\030\\362\\316\\030#jC\\276\\006.\\244\\037\\352\\367\\304A\\322E\\266hCn6\\236\\324kU\\226\\253\\034\\217H7\\017\\016P\\204"~\\007\\374v\\304\\304w\\264\\323\\032r\\243:\\353\\313QV\\220D\\243g\\340Y \\237\\232\\270\\335\\327>\\016g:\\357\\351B!\\224\\323\\263n\\370A\\242\\234\\254n7\\033_7tU\\262\\023\\256\\302\\346\\230\\224\\004\\240\\215\\216\\001\\206&W]\\026v\\211VW\\233x\\267\\360^T\\253\\000\\367\\014m\\334b\\237[~j\\243g\\351Y\\\\\\216\\271"Z\\340\\015h\\321\\322\\333\\004v.\\024X\\305\\012\\261\\0213\\365\\370k.]\\244\\036\\316\\037e1\\247\\001\\272\\231\\311i\\0331\\241L3\\267\\301dd\\260\\316\\360\\337\\244\\246\\313\\334\\355\\233h\\327\\3603^j&7L\\315\\233J\\016\\265\\013+\\314\\365\\223\\177!\\240\\335\\026\\014m7Cq\\271"\\031\\214\\306\\240Z\\320\\215p\\331\\311\\355\\177\\302Q)iy\\343c\\366\\372\\375\\332\\355\\262=<;\\314)\\273v\\271\\271\\023\\331Y\\323\\300^\\345\\357\\343$\\262M\\011\\241\\342V\\211i\\302\\310\\256\\236d\\257B\\241\\017\\334h-\\220\\3761\\203\\225k\\355\\207\\320l\\207\\013\\340\\006a4\\032\\265\\273#Bo|=\\177\\270\\330\\277\\210Vr:\\335\\341\\255\\221\\335\\317\\315\\323Se\\357\\231\\011\\340\\016\\024\\233\\022\\2073\\360X\\301\\232H\\207\\302\\241:\\343\\001\\354\\006\\324\\036H\\020\\372\\324Vp0\\270\\272\\204\\014\\251a\\006*\\3151s\\345za\\025\\352}\\016\\033R\\020\\253\\374\\306U\\346\\352\\360\\231\\214q\\033\\377\\205w~''\\205N.Eo\\257|\\0356\\205m\\271\\002\\300\\211\\225\\311\\363cr\\004iS\\255s\\207O\\321\\035\\265{z;\\006\\003\\213\\026\\010\\024\\235ml]\\004<\\273\\022:\\000\\327,\\220\\223>\\203\\311\\327-b\\014\\263i\\016\\001\\364]\\254\\312C\\334\\355\\005\\314\\277\\362\\015t\\265\\036\\305\\376u\\256\\375K\\177-\\253\\260\\241\\026\\314\\037\\272\\223\\247SRm\\330/\\230M\\032\\227\\352ki\\275a\\234\\027\\200b\\337\\224@\\235\\033\\245\\374\\237}\\365\\345g\\004<\\215&\\323\\204\\235O{Bx\\202W~.\\337;z\\037\\212\\354 \\221\\340\\017\\253\\312\\035\\012\\276\\004\\261\\375\\023NE\\373A\\270$\\247\\367\\034\\003/\\267r9\\212\\272\\007\\357o\\015*\\352OW\\331\\253\\266H\\010\\2453\\001\\013x\\364\\203\\216\\326\\0140cb\\244\\333\\252`\\027\\274\\001E0\\256\\342\\260-\\341B\\017E^\\342\\255\\225\\305\\3115w\\370F[\\326\\204U\\260\\313\\330\\334\\231vB^\\214n<z7\\015f[\\260K\\253\\337\\301\\306\\211\\267\\220b?\\0169\\221\\015\\3402O\\021\\000\\374\\337\\220\\011\\210\\000*\\341\\001\\350d\\2054\\310\\211\\2441\\024\\215\\365\\231\\264\\231\\231a\\240i\\014>\\3667.m\\011=\\252\\025{}=\\035\\200\\231Y\\226mw\\273)\\357\\3472\\311\\354\\216\\321\\007\\352\\270,7E\\220\\022kN\\032?\\015Hqc\\335p\\223\\253\\350\\370\\346\\2646\\271\\332M\\311\\370\\266bP\\232,\\313::\\254\\036X\\364q\\217)\\021\\002\\315\\242\\304\\227\\241W\\311#6\\235\\331&\\3441\\266?\\3367\\310\\220\\020\\303\\014.\\006N\\3421X\\340\\014\\3132\\377@\\352J\\022\\266\\256\\002\\234\\241\\003\\027x\\342L\\276\\246\\332B;\\370\\246i.\\342\\223P\\367 \\241\\020.\\237K\\024E3\\360>.\\332\\177\\372@#\\367i\\331@\\314\\033"\\026c \\220\\311\\311\\021\\322\\3414\\030\\240 \\316wT\\303\\\\\\321v\\343\\005O+I\\205s\\271\\231@e\\327\\025\\243\\231"\\225\\3429\\\\\\321\\342\\337~I\\240\\333\\251xc\\303\\354\\270\\021\\265\\0348\\265>\\341\\302+9\\323\\312<{Qj\\317\\261\\302=Qs\\234\\256<3\\005\\262*\\227-i\\301.\\345\\021\\350[x\\352#\\304 \\365\\201\\360"4\\347\\315i\\334U\\322\\210\\276\\310]\\265\\021:\\204 \\312\\203$Y\\343\\306\\012\\206\\276\\217\\327)\\257L\\230F\\036\\006\\252Z\\271\\306\\356\\355\\244\\210\\351\\032\\224\\247N\\221\\271\\360>\\001J}\\202\\271\\020|\\322\\263?\\356%\\210z\\313\\204l\\377\\3608y\\206\\216\\323@/\\204?\\232\\373z\\344\\306\\332\\2615\\360&a\\371\\2732\\277\\363\\253\\244_\\364`''\\362N&\\314\\003]ZE\\374\\302\\300\\257<0f\\200\\256\\216\\031\\247\\244V\\357\\303\\355\\02125}\\0151y\\252\\231(A%\\312\\234:\\342\\3377\\217Kk\\316r\\217\\321\\214\\244\\347g2\\032\\371\\272\\331hE\\212\\356\\3025\\321D\\270C\\236\\336\\234J\\034\\275\\025\\234LR:&"L\\272\\273m\\267\\030\\022U\\336\\022>J\\251\\354\\236V|\\345\\310\\023H`\\222\\360\\263\\250p\\240\\331_=\\335\\333\\273\\030\\011\\3742\\254l^\\225\\027\\346\\342\\240\\235p\\343w\\266\\016\\372\\211]z\\204\\006\\235\\242g\\201m-\\320\\365\\2400aw\\000(W,\\031\\266\\014\\316\\320\\244\\222\\2308\\023\\003:\\000\\252\\000~P|\\216g\\204\\225\\302\\033\\243\\336:\\254V\\270\\263\\010\\221\\227\\341\\033\\226\\360F\\001\\336y\\252\\330Gw\\014\\017\\374\\313b\\211\\333T\\236\\025\\000=\\276\\367\\3136\\017\\256\\242\\374\\242\\362\\245K\\360"7kqD\\243\\313\\214\\374S\\27139:\\250''\\326pB\\227\\370\\205I=t\\226Q0\\217\\220\\370Y\\356\\373\\362\\342\\324\\365\\354m\\343*\\245S\\202\\177\\376=0G\\260\\332G\\312\\273\\326e\\016\\344\\0225\\030\\242X"\\313\\200g\\204>H\\020r\\202\\014\\215\\215\\345U\\246\\266^b\\374;Y\\261\\246-\\034\\267k\\232\\374{\\275\\007p\\362\\206\\006H\\026\\035\\274\\015\\322\\321\\361\\034X(\\334\\030ldW\\363GH\\342\\014\\327\\337\\007\\300\\217Yn/ \\232\\257/\\376]\\036\\275\\230\\025\\252dPt\\217\\204\\201\\234[\\311\\304\\260\\\\\\302\\241\\303B\\345\\224\\015\\202\\355\\024\\274\\016\\2452\\331\\303\\366(\\027\\376\\212\\337\\375\\006\\316\\273\\2013{\\003\\300S!\\177\\242\\242\\326M\\205\\364V\\371\\304\\314T\\302\\260d\\203]\\340X\\206\\020h\\303F\\313u\\374\\202t\\205\\221,\\272\\343hN\\221\\371\\300\\256\\310\\336\\0002\\350\\036\\213U\\270\\013\\253\\026\\361MV\\022!\\356&?\\356\\267B\\253]8\\224\\303\\205\\3150\\350S\\177\\023z\\337=j;\\316\\027Xz<B\\334\\344b\\256\\027\\021\\270y\\012\\000\\200\\322\\274fG\\350\\014\\323\\2651\\3274\\353\\331\\332\\024\\032)\\015\\227\\305''\\224)K\\350\\262Vs o\\334\\306\\377\\327\\267\\200\\364\\331\\233\\274e\\346\\200+@\\342\\331\\333m\\271\\347&e\\327\\276Y>Ic\\005B\\263rkIT\\007\\2360\\331\\275\\360~\\364\\350\\344V\\327q\\241\\\\\\236]\\231EPp\\214;c\\301\\037\\036\\030c!4&\\320Ab\\275\\343\\014oe''z\\035\\241\\260\\276z\\270\\370O\\027\\261\\214j8\\003\\336b\\366M\\215\\243\\305C\\031\\220\\344\\327\\301\\352\\246\\003\\346\\267G\\330\\345]$l\\354\\017\\306Q\\0246\\3322*\\374\\314\\244^7\\001\\233\\307\\034J\\023\\2124/\\241\\017</\\334\\316\\322p\\020\\334\\255\\210\\300#\\335\\2167\\312\\017\\323\\224\\374\\3514(\\234\\010\\203\\210\\301h\\307F\\236*\\017\\342\\272\\214\\367\\244\\376\\314\\214\\251\\227\\023\\342{pv\\310uh\\373\\314\\266\\274\\330\\215\\245\\302\\343\\017\\360\\325\\320FW7\\014\\247\\302\\356&|l\\242\\313\\021d\\231\\350\\326\\364\\234\\017\\337\\2113UC\\\\kl3\\3509\\210w\\224M/\\001\\200\\011!~\\306,\\253vA\\242;*\\3104\\326\\035n\\373\\320\\010\\214C:\\034\\262h\\351x7\\260s\\271\\222\\327eY\\212\\374Y\\001\\011\\021I/+l$\\202\\326\\213\\342\\210\\305t\\266\\026]\\301\\204N\\352o\\345\\266\\310\\276naC\\335\\014\\337&8q\\003\\027\\316\\020\\261e\\352\\005\\307 \\246U\\2606Mm\\3316\\226N\\341_Tf8\\327\\032V\\262\\317\\032d\\277\\277\\362\\212\\302Ku\\014L\\351jV\\346\\367\\015s\\262\\266f\\240\\210\\200\\237Cr\\201\\345\\332h\\202}\\205\\004\\216u=\\274\\335\\250F\\225\\223v\\021\\371\\260\\314Ic"\\351S\\026&g\\311\\\\\\220\\257\\023{\\302\\203\\356\\271\\016ai\\011^\\332\\035\\232+`\\302Y\\3479\\261\\253;\\246\\007\\333r\\007\\177\\000\\227\\372z\\033W,\\245%\\210(\\350\\245\\227\\221\\003)\\037\\343\\012Y\\033\\346\\240\\247\\252\\301\\2721\\250\\013\\003iW\\006rE\\237\\004RGKc[H9\\215S\\221h\\001Z\\331u\\013\\021\\345\\334\\252\\030\\340U\\332\\275z\\222u\\340\\0139\\377\\200\\037I1\\343\\353v\\275>\\032\\357\\003]c\\372\\035\\031\\210k\\361\\203\\254\\010E`\\352\\027\\235\\302\\360%\\240\\266\\311\\023EK\\215_\\032/\\233X\\334Y\\\\\\036e\\\\X\\036i\\222n\\252\\000\\354&\\027\\017\\267\\343\\026-\\374e\\255\\234e\\336\\313An)Ih\\310bu\\323\\016\\006\\277d\\360\\034j\\331\\264\\367s#=/\\244\\353V\\003m\\225\\006\\332\\310\\254''-\\374\\200\\270\\274\\213u\\245\\246|D\\372t\\272\\244\\217\\015][\\250\\2705\\253\\277\\237,\\230\\243\\021\\034h\\367p\\270\\335\\374c\\2111\\227\\212 \\254\\034\\314\\270eH\\306\\240\\323\\365%t6dl-\\252\\037-\\233r\\210\\255\\212\\240S\\323i8\\200\\203\\253V\\346i\\360\\364\\237\\317&\\363\\306\\274\\016\\331\\222\\017\\002\\000w\\\\L\\214\\024\\001{+`\\265\\364\\211\\232R\\270d\\025\\005XDj:X\\277\\321<\\335\\352\\327\\206\\237\\031(`\\376\\263\\314\\034#\\355\\217\\255\\013\\332L\\204q\\374\\037\\002\\021\\234E\\234O\\314\\025\\005\\272\\252[\\213\\265>m\\2434g\\015H?\\022a\\\\\\0355\\2456QO\\007OG:\\206\\2031\\220H\\017\\007\\334g\\324C\\346\\024\\223],]*\\251\\015\\021i>TiNv|\\3040\\255\\020y6u\\212\\233\\226\\014O\\014\\242\\310\\020\\343\\251\\022E\\0213H\\355\\331\\250J2\\343\\006\\033\\2336\\035\\224=\\246\\331\\255\\225\\274\\333\\27467c$w*\\224XF\\316\\250\\371\\213\\0277\\031\\267\\000\\377\\267\\353\\210\\214\\020S\\311n\\226\\301R\\371\\231\\020N\\237\\311\\377\\214@\\377\\003\\320\\324"\\230\\007\\031\\225p\\177[\\202\\361\\037\\011\\362d\\213\\314\\345\\305\\353\\305\\235E\\231\\223\\260\\277c\\2203{\\003@\\351\\316w\\004\\220]\\342\\202\\230\\234\\232b\\035\\365\\246\\335\\232\\262\\011\\301\\302\\210x\\244\\227\\360\\274\\333\\354Gswl\\240\\301z\\027\\362g[\\325p\\012\\323\\347\\306\\010\\317}\\356d\\225k\\027r\\351\\330\\201\\324\\237}\\356\\000\\225\\302\\260\\315\\264O\\313\\366\\204\\247\\026G\\211\\242j\\3513"\\253\\030\\224\\034\\256K\\016|\\345\\375^\\325\\271zP\\011\\312\\202\\261ox\\340\\021\\202=\\335et\\232W<\\223QYB\\3359\\351\\027\\005V\\344&\\264\\3716\\247\\267\\032zs\\306\\363h\\322\\213\\034\\256c\\232]G\\030gM\\000\\333\\266j\\352''}q+2\\3047\\212!\\230\\305V\\231W\\374\\3664\\233!\\242\\037\\2111\\201\\224\\002[ v\\261@EBj\\201\\320P\\353\\323m\\364\\273\\360,S</\\351o\\312\\031\\346\\226\\026l\\017\\223% \\245\\223\\302\\256\\302\\252d+l\\024\\247r\\030a\\271\\037\\333\\235\\245\\207|\\226U\\037\\230\\315\\262d\\236\\020A\\235\\252-*\\204\\001k\\210\\305\\204W_ P\\033\\355\\027\\0075\\272\\243`\\022\\265z\\004\\014=\\327\\023\\026}\\315=7\\343\\024\\264\\350+\\3442\\213\\214\\324\\266\\357^\\3128\\317\\032\\321&[\\250\\330\\221\\360w\\364\\301M,\\022b\\261]\\325\\253\\257\\314W\\334\\001\\346\\270\\365\\336\\011\\300\\210"L\\337\\352Y\\365\\306\\310\\250\\332(\\327I\\263\\255\\010O\\020\\364&]\\332J\\337\\243\\241\\230\\223\\355\\306oL3\\026x\\223\\331\\245d\\302\\222\\224\\331`\\251[g\\005\\312;\\306\\320\\265\\367\\312\\307c\\241\\212\\314S0\\320\\303pu\\353+l\\252_G\\030\\206%\\255\\334J\\033s\\013<\\333;Q\\216^5\\301\\233\\271\\222\\023\\011\\217)\\\\\\236n\\326\\033\\262\\031=}.\\200S\\262\\204\\335t<\\204\\202V\\022UO^\\223\\313`\\3357\\026%\\230\\206[\\2355\\346<\\261\\344\\265\\337\\033q\\011\\007\\223\\352U\\301\\364x\\304\\036*H5\\035cOL\\222\\200C\\250\\262\\276\\000O+\\333\\224F\\3061\\277c\\356\\2770i\\2559\\374\\240;C\\031\\245-\\311|)e?\\315,\\217\\226(\\350CW\\261:\\240\\2426<H\\210\\016\\242FN?P\\344\\256p.\\320\\302/\\022d\\224\\372\\020=\\306\\035]sz\\363h\\177zT\\000:\\327Cy\\302\\272\\260\\027\\246\\340V\\272\\212\\312V8UE \\234\\205\\352[\\272\\330\\326dn\\304\\363*\\2515\\206H\\243\\030g:\\037\\261\\233\\355~\\354\\205\\243\\371\\031\\016\\023\\332M\\236\\216]TyN\\246B\\272l\\210\\257\\262\\355\\024\\355\\232j\\021\\261:\\242\\252\\317=bl fs\\321\\324\\234\\250L\\234\\374\\177Z$\\375\\336\\364c\\305\\204<\\330\\370\\244\\017\\007\\300\\325-\\352\\262Z`\\330\\205\\261\\331\\021\\310\\210m,\\367\\246:\\372\\262}p\\005\\347\\244\\257>\\334\\013\\236\\357g\\315N\\233\\225\\303\\365QQ\\252\\212u\\272\\033\\270\\212<\\322\\324\\256\\207\\356t\\213pf\\304\\304\\014H"\\367\\331\\232\\341(\\004\\253\\350\\0321\\234r\\216\\310\\002\\011\\270B\\2111\\037\\353\\007\\243@\\367N\\235/,\\372RYE\\222\\024\\245u\\275q\\257\\372\\252\\222\\012\\037\\355r;\\215\\264J\\344\\224\\310\\234\\000~UV\\033\\023Rh\\224"\\207\\324a\\367\\372\\373\\207k\\372\\331\\326\\337\\021\\345\\234.c\\346\\3528\\002\\362a_\\200\\357*\\035\\352\\273\\222\\270Y\\252\\230\\016\\224I\\354j|\\276\\356\\364oD\\243\\335\\313\\225\\354c\\374V\\230c\\235z\\354\\340+\\337\\265f\\000\\247\\267;FT)o\\321\\326\\313*\\354\\033\\203E\\234\\314\\375\\022\\240\\262h\\2528\\275\\222\\257\\255\\007\\3038''\\014\\214\\000\\236\\310\\311Q\\375K\\001Nb\\226\\301/\\324i\\025+R\\334\\320s=Lj\\3111F\\315\\220\\317\\004"\\311''\\003\\017(\\014\\300\\3744\\007\\344yL\\242\\301\\266lN\\341d\\037\\266\\356\\014[\\262\\351\\263\\204)\\327\\200d\\256>v\\005x>8l\\307\\311\\\\\\013/V\\321\\001\\275\\363lYxS\\261O\\014R\\234\\263\\023\\366g\\357\\366J~\\021\\215G\\257\\0216L\\373 \\356\\264[\\2072_\\017\\330\\325\\3106\\330\\274\\246g\\323T\\241\\257\\260K\\241\\026\\317\\257v\\220\\236S\\271B\\244\\272\\025\\235k\\330\\3261\\226^\\234\\242\\223!\\222G\\030\\300\\331\\206\\365\\221\\005t\\327i\\002''\\020rb\\264#\\030\\221\\030R?P\\347T\\007i\\252)s\\347N\\\\\\241\\226\\023\\254\\235\\255\\006\\306A3\\254\\257\\330\\177\\303\\302\\352w\\012\\341\\335,\\256\\262\\353X\\346\\022\\273,9\\374\\255%\\375\\006D\\351\\353\\355\\325\\223\\307\\250\\325\\347Q\\255N\\265\\344\\353\\316\\260;\\277\\013\\205\\203\\255\\033\\264\\375\\245TV\\354\\314\\364\\0045u\\335\\237\\344\\031\\260R\\026\\334\\244+\\221\\230Bz\\220\\275\\035\\366\\313\\306\\320$`t\\217\\260\\342(\\337t:g\\263\\235\\253\\276\\347Rz\\273\\363N()\\302\\322\\222\\001I\\345B(]\\234%G\\271s\\273}\\240ob~P\\321t\\250\\202\\223\\237\\360\\200\\265\\341E\\357;O\\335e=\\036\\226\\247\\350\\234\\342\\314\\274K\\027\\241\\255\\011\\001^\\314f7\\035\\324\\245:\\206\\276\\261BE7\\220\\215\\315q\\206,\\021\\227^0!\\255#\\203\\022\\212\\316\\265\\321\\024G\\015T}\\203\\231B\\226\\222vGeqk\\241E\\333c\\030\\345\\252\\316\\272\\2566\\012\\315:\\311\\211\\263T3S{\\204\\021+A.\\233yP\\243\\002v\\235\\212\\015\\261I\\344\\341\\372w\\376\\274\\307\\310\\026ucRLx\\013\\223}\\221\\332E\\202\\302\\312\\246,\\255"\\232\\251\\031j\\206\\330\\276\\352(+\\263''\\316\\033Y\\271Q*\\353l\\335\\035q\\310\\256!\\334\\240\\304\\245,\\236d\\3315Y*\\250(s\\203c,FSt\\242b\\011\\237\\241\\373m\\301\\266\\313\\000\\377\\2127\\333&\\346\\011#\\242\\214\\212\\247\\272s\\311\\202\\\\\\366\\242\\012\\221\\011|''\\256;j.\\020v\\273\\300\\322Bs\\244\\256u\\204\\216T\\200\\375\\253\\2113\\343B\\343\\216\\0027\\270_\\203\\264\\200\\200\\214\\221''\\022\\205\\217\\300S\\0379S\\243\\236VCQ\\251i\\233Id\\323=b\\340\\211\\276\\250far\\325\\262\\374\\322\\026\\225g\\261\\301E\\377W\\027\\256RF\\246\\226]\\255\\213H\\221\\230\\014;u\\347*\\262;\\246<RJv\\314c\\315K,\\017"R\\336\\307aT\\016:\\222\\332\\021XN\\004bYH\\222_}\\032s~Lp\\252\\307d\\213,Y\\035\\201\\370\\026\\013fI\\033\\355\\227''+\\301>\\364`\\225\\016\\315q\\237\\267\\253\\3151\\201\\207y"dT\\265\\342\\372\\010`)g\\326DD\\235\\261\\033\\327*}\\251\\356\\261\\251\\332\\335\\202\\013\\235\\032\\373\\243\\331!\\275\\256Z\\331L\\372\\\\\\352 |\\370N\\235>\\341t\\262\\032\\030\\013lf\\231\\243\\336\\360T%,\\030n\\252\\260\\256\\325\\361\\244\\034\\305H\\324d\\315\\311\\005\\015\\250\\325\\216S\\2453i\\306-^1R3\\003\\262 \\236\\030\\205<\\334\\371\\256^\\025\\253\\3430\\200\\307 \\240\\363\\207\\271(\\004\\370\\362\\350Ucl\\212\\021e\\360F\\177T\\352\\232\\020p*\\375\\235\\324\\2519g\\322\\025\\324,h\\224"\\205\\255D\\242VB\\366\\373\\263\\234C\\312cf\\211\\351U\\026\\331"\\342\\275\\020\\361\\025\\374A\\354Ml\\005\\201\\271\\003=Re\\254b\\203\\261\\012\\325\\270\\231\\216\\364P\\274T\\255H?\\306\\262\\245\\305\\035\\366\\316\\361\\023\\315\\366\\230}\\347\\221jUl\\037\\370\\266\\364\\252\\213v\\271\\010D\\242\\024\\263\\353\\326\\376\\313c\\304|\\346d\\334\\345\\305\\217D''\\273\\222\\266\\3446\\3454\\271\\303"\\266\\326\\273Z\\342\\337\\251\\270\\023{Z\\373\\004\\271\\333\\234o\\217u\\356\\337\\207\\270K3\\305q\\323\\226\\204\\335ac*\\367\\272:X\\036\\261n\\325l\\2521y\\307\\214\\322\\212hB\\353n_\\346\\345&X]\\251\\351\\032"\\214\\336\\217\\003\\314\\010;\\3502m\\266\\321\\257\\340\\233\\374#\\021\\325\\303q\\344\\271\\303\\202\\227\\027\\177\\345\\311g\\3534\\335\\015&%#(\\2702k\\253,R\\251\\373~me\\010=\\273e\\253*:\\020\\251\\265\\265\\244\\240\\304\\215\\307\\202\\316f\\370\\236\\315''b\\322\\347]\\367\\223\\252m\\300\\253AQ\\245O:\\323\\214B\\223\\232\\251F\\014\\361\\266\\011\\333\\016$\\022\\031\\252\\20529\\311]x/\\203\\267(\\350\\324\\236\\233\\375\\364\\372\\335\\313?\\311m~\\320\\347\\321\\203\\267\\037\\335>\\273~\\035k?D\\036\\013\\353?\\035?\\301\\346\\216\\331\\360L8\\013D=\\234|\\242r\\264[2\\202\\350Z`ix\\3301;\\356\\360=vy>\\346\\305j\\342\\020\\242\\212\\247b\\265\\355\\326\\037\\027%\\376\\200\\340\\360\\012\\206\\354\\337;\\372I\\363\\216\\373`\\207&\\374fcY\\200\\276\\355{\\2069\\373?\\276?U3\\321K\\333{<\\233\\362h$\\237\\265\\336\\223\\377\\335\\2065\\235\\371\\177\\363\\215Gh\\344\\331c\\252\\364\\221v\\200G\\033%\\205\\237\\317o\\372\\363x\\232\\254u>\\001\\272\\007\\032\\024#"\\3020\\355\\275\\311&\\213\\260\\231\\304\\256\\317\\251=\\375c`c\\302\\262\\334\\373H\\204\\313I9\\302]\\006\\365=\\006c\\367|\\207\\356\\273\\351\\314c\\023\\2570k\\215\\253\\314\\375E\\363]\\305q\\212v\\231{\\214\\221@k\\303\\246=\\227j)\\317>\\217\\361^\\335\\326\\331\\265\\232\\271\\2314\\364g\\236NI!\\013\\3545\\354\\017B\\375#v\\230\\376\\266l\\343?\\022\\361\\231\\363\\276\\227\\027\\337\\031+\\250\\354\\244\\373\\346\\224Ia\\327\\313\\030cj:x\\222\\3326\\245\\272\\261\\025t@\\012\\305\\216X\\366\\302\\306&e\\215]\\373\\275#\\265\\3273+\\003\\223%8\\267\\315\\370G\\211\\275\\270\\232v\\355z\\037N\\232\\226y\\015\\347t\\360\\223\\312\\030K\\0251\\226\\360\\235\\353\\026\\233\\374z;U\\026@)\\023T+%\\251\\367$v\\012oE\\271\\215\\207\\353\\340\\355\\244T\\261J\\3752\\367\\373-\\353c\\217\\320\\341\\263\\007\\247\\255\\333\\346.\\036\\212\\212%\\352\\364ayJ\\372\\037E\\237\\324Xp\\375\\266\\201\\245^eo\\227\\360T\\226!\\177\\203\\\\\\341\\233-\\001t\\307s"\\366\\002\\214\\302a\\256\\0064\\205\\313&\\361\\261I\\237\\355\\366UZ!\\376QQvT1%\\210T\\311/\\341\\211\\036|\\271\\356\\363@\\207\\2655\\254\\000v\\225U\\354\\034g\\022\\323\\346B\\320\\273\\210\\362\\325n\\352\\305g\\032\\233i\\245\\212\\033Uj\\031\\226\\323\\210\\303\\363\\2713\\365%\\013\\244[\\266\\246#\\220Yw80\\366b\\255\\210S\\353=r\\2434\\312c\\350\\363s\\207\\3323\\333\\266\\310\\367\\331\\307\\031\\310b)\\221Nw@\\206\\033D&\\303\\331\\227\\221\\244O\\237\\334$T\\273\\245\\266Y\\022\\365\\323\\313_\\262\\237\\313\\235+>\\231\\306\\252\\203\\264\\303bacn\\303\\334\\267\\300\\256\\035\\372\\242\\201\\324>\\277\\363uW\\003\\227x\\233\\004P$\\340\\207\\257\\377\\243\\007\\367\\037\\341f~\\3171\\377\\316\\347\\244\\246\\241\\3561\\3521\\005\\026\\023v\\355\\370\\250\\311F\\012>\\020\\313\\254\\257\\306DY\\206\\035\\300|P+p7\\276i\\262\\304\\210l\\303{\\325\\320Dq\\315\\263\\027\\361`\\364\\276d\\037\\346\\215\\005zu\\021Z:;Y\\25355\\032S:d\\310t2N\\200=\\341\\246\\3519\\234\\370\\221\\267\\002\\364\\271S\\330\\020\\277\\357\\230 \\307:\\012\\336T\\012C\\300\\341-7\\273\\326\\243\\221(l\\016\\2455)"1\\177\\362\\010\\255>\\367;\\012\\263\\354\\247\\017\\177F\\341\\362\\342\\227T?N\\255\\035:\\2569\\362\\022\\024E?\\216d\\321\\2750\\213M\\024\\354\\017\\244\\364\\354D@\\327m\\221\\330\\035\\011m><\\011\\243\\234\\241\\356\\3472\\265^\\016\\015\\022\\373\\331\\315o`\\306\\217\\261\\356\\217\\376D\\3048\\362\\245\\352\\344\\207\\304\\372)\\2354\\354:\\331\\226\\271\\237\\214]*I\\021(\\223\\235G\\266\\364\\231\\210,\\030\\236|\\031]\\310\\361\\364\\230Q\\332\\252\\265\\017\\013Z\\353P\\325\\351\\\\w\\327j\\307\\020\\235\\212\\227\\243\\347^\\344\\210a\\300\\240Y\\177\\360\\205d\\232@i\\314x\\001`\\021*\\025\\032b\\272\\335\\377d\\203\\366\\355?\\255\\332\\253H\\261\\367\\235\\210\\002;\\213\\362=n\\220JQ-]\\335u\\242\\3410-d~\\266\\376\\334oD\\017ba\\366\\004\\273\\311k\\243\\306\\007e.{8m\\262\\231R\\337N\\251\\211\\204G\\020\\345\\037\\373\\371\\017yuoL\\340\\302\\247|CLAi]9\\211\\037\\215\\007\\025\\313\\235\\265\\266\\215^c+)U\\311\\253x\\310\\343\\033:N\\301o\\365\\035\\274]\\177\\337j\\225\\016\\273\\334u''\\027v<A\\022\\033\\354\\257%\\243'':\\301\\313\\000\\354x\\330\\177S\\301\\245Y\\263Nf\\207\\024o\\331\\243\\305S`v\\3645H\\360\\214\\336\\366+\\025%c\\013\\177\\242\\300)\\2066l6\\256=q\\333\\350 \\263\\014\\017\\233\\0120\\020\\356\\312J\\210W_\\211\\007v\\242ij\\016FE\\247\\206\\025\\253\\013,o\\363\\276#\\224O""\\273H\\344X\\373\\205J\\377\\312\\\\H\\030\\030\\2552&\\013^\\313]H\\331423\\237\\330\\256iN\\374&\\035W\\024\\362\\220\\277\\212\\367\\373\\002\\261\\212(x\\362\\305.\\373{\\317\\336]]]e\\3379\\373\\317\\025\\335\\352kR\\251\\016\\311Ai\\177x\\204;=\\367\\2231\\227\\027\\337&\\222V,\\023\\266\\251\\3234\\261\\367\\035j\\351B\\327\\002V\\177\\373g\\313\\026\\250\\022\\342\\177V\\226\\220\\351^\\344\\251\\266\\261TG\\375\\210+\\267\\365\\246\\257+\\273\\025=\\206G\\260\\3233\\363b[\\247\\254&\\352s\\326e\\271\\221\\3122\\203S}<K\\305\\361\\222G\\231\\246\\302\\321\\312^\\265\\024\\333\\204\\210]\\242!^1\\377V\\2214\\364\\247\\265\\342\\247\\330\\015\\363\\030\\017}\\356Ww./^\\276G\\340g\\336#\\232\\213\\224\\322\\261N\\371r\\243^\\232\\242\\264\\217\\027\\245\\326i6*\\356dk\\301\\333\\365%i*\\252\\022Ug\\307\\372\\274\\343\\031\\007\\311\\025cu\\345/i\\366\\365_\\236Pd]\\311[\\204\\313\\340$\\245\\365*\\344\\030\\227\\212\\237\\252d\\273\\360\\336\\257\\036u\\000\\362\\243\\2770\\244\\222\\250N2\\3602\\215\\347\\177\\177\\355\\377\\363(\\221\\237\\375\\315\\242\\364\\275\\205q5\\316\\272\\254\\014\\262\\244V{\\277\\342i\\327v\\025\\312\\376\\010\\357\\027\\237NDO\\250y\\213{`''Ms\\207a\\002\\251\\345\\373\\235(j\\307\\231\\324\\261b\\323\\003\\205"\\026\\033\\3436\\353\\363K\\226\\237S\\377\\016\\333b\\371#G\\273`\\277\\003P\\2243\\276d\\277\\320\\343\\324\\013\\302\\335b\\201<\\236\\273o\\332\\246d\\207\\234\\270\\222z\\333\\362\\\\5\\245\\010\\230\\205\\011\\3205\\363\\334j\\224\\205\\250\\367\\207KTf<h\\261\\034\\366\\300\\016\\016\\001[s$\\323\\0356"\\251\\234?\\350F\\312Y\\\\M\\275Q\\323\\370\\333\\024\\360\\242\\236\\277\\250\\240\\037\\236\\311\\354\\227g\\032\\375$\\340\\341p\\230\\337v\\273h\\035\\000\\313r\\336\\336\\332&\\322\\353\\266\\373\\225KE\\236\\302\\037l\\022\\261\\357\\356^\\203\\313\\375_\\261\\221\\365\\233\\314`\\347\\320\\020\\002jel\\351\\227Hl\\3679B\\377\\363\\021\\273Pk\\234\\236\\264\\232&\\311\\0328L\\275P\\006?\\246v2L\\267,\\255\\353\\\\\\017$\\205<_\\277z\\323\\211b\\300y\\246\\316M\\345M\\343\\037''\\320\\302\\325\\321\\000\\355\\372\\321-#\\023\\311>\\325\\364\\003\\023\\210\\025HZU\\267\\341\\003\\203~\\264&m\\0355\\344\\215}\\266\\347]M\\360\\267\\374\\271H>\\363?\\275\\370\\347$\\363\\220\\225\\334y\\333\\203\\007k\\320;\\376\\302$\\227\\304\\277\\374,\\201\\326\\332\\307\\357\\273\\037\\247\\244Gd\\361d\\232\\275\\204\\255RH\\037\\374Z\\345\\377\\003PK\\003\\004\\024\\000\\000\\000\\010\\0005n$;\\006g\\216j\\202\\001\\000\\000j\\002\\000\\000\\022\\000\\000\\000kingthingsEULA.txt=Q\\275n\\3430\\014\\336\\003\\344\\035\\2761\\001\\022\\243\\270\\355\\306\\014)P\\244\\303\\341\\220>\\200,36\\033Y2()>\\277\\375\\221N\\333E E\\360\\373\\343\\205c_\\006}2\\316\\037\\357''\\034\\261;\\307\\016\\037\\231\\004\\357\\354)f\\302\\251\\027\\242\\221b\\3317\\333\\315vs\\012\\001e \\334R,\\031\\276\\212\\350(,\\230j\\0338\\017\\324!E\\214\\013fj3\\027\\302n\\236\\347\\346\\376\\303\\263\\2565>5\\365\\276\\207\\023\\305Qt\\005\\023,\\251\\242$\\324\\374l]4L\\231\\222\\3666\\232\\025\\274\\001\\376\\004r\\372\\323%\\304T\\340\\007\\027{2\\272$\\334sta\\325\\205\\033\\007\\312\\3305\\327\\353\\353^\\227L\\367u e3\\306\\234F\\302\\344\\270;\\032\\317\\203$s\\212\\031\\351f8O[\\273\\231\\313\\200\\261\\372\\001\\024\\225\\303\\253-\\345\\022\\347\\213&\\223\\251d\\025\\377p\\034\\\\\\033\\314B\\032Mz[\\227gm\\226\\375@n\\232$}\\033\\036\\017\\026\\233J\\367\\372\\3418Z\\307\\2024\\307g\\364Ns7uf\\213\\243\\017\\265\\263(\\005<N\\201\\265\\\\78\\333\\\\\\017\\263\\236\\342+\\013\\212\\237i\\201\\323\\273hN\\002/\\344\\012?\\010s\\222\\273^\\324\\200\\371\\266f\\350\\315y\\235\\260\\272\\323\\200\\327\\223\\254\\223\\300w:\\340MwjP\\035*\\234<;=\\237\\303\\304\\276T\\241\\257\\030\\377R\\357\\244\\313V^\\350\\241\\242.\\012\\261\\335\\234\\377\\221&s\\3009\\366\\301\\010\\217x\\245V\\252\\223\\005\\277^^~o7\\377\\001PK\\003\\004\\024\\000\\000\\000\\010\\000\\371\\221a;\\033\\207&\\004NM\\001\\000TV\\003\\000\\034\\000\\000\\000Kingthings Christmas 2.2.ttf\\334\\275\\007\\274\\035E\\335\\377?\\263\\263\\275\\227\\263\\273\\247\\367v\\317\\355\\355\\234\\224[r\\223\\233\\336\\023R\\201\\220@\\022\\232\\201\\000\\241\\210\\004P\\220\\022PA\\244KU)\\312\\003\\021\\004"EA\\020\\025A\\001;\\202\\002\\242\\240>\\370\\330P\\004r\\316\\357\\273\\347\\336;T\\221G\\237\\204\\377\\353\\177a?\\347}\\366\\354\\316\\314\\356\\316|\\347\\373\\235\\235\\335 \\214\\020\\262@Xd\\316^\\262x\\371O\\254\\337\\267!\\262\\342<X\\373\\340\\342\\345\\235=3W\\363\\347 \\204/\\201\\357\\353W\\216.\\\\}\\304\\332c\\177\\205\\020y\\014!\\356\\231\\203\\266l\\330\\372\\373m\\177|\\036!i''\\254\\333v\\320q\\333\\322\\250\\002["\\375\\263 \\314\\346\\255\\007o\\341\\327|\\266\\206\\220\\226\\2074\\2169x\\3031[\\033\\015D \\375\\263\\340w\\361\\340\\017}xst\\352\\311_\\206\\355\\257Fdh\\343!\\2336l|F+\\337\\011\\333\\306\\341\\367\\352!\\260B;\\222\\235\\015\\337\\367\\201\\357\\371C\\266l;\\341\\263\\227\\375~.|\\177\\005\\226\\237\\036\\276\\351\\350#\\310<RCd\\3213\\360\\373\\265\\037:\\362\\240\\015\\350\\216\\363\\356Cd!\\013\\277?\\265e\\303\\011[\\231\\257kW\\003\\237\\000\\277\\247\\217\\330\\260eS\\362\\216\\023\\037Fd\\361\\351p\\300_\\334z\\3441\\333\\332\\377\\370\\267o#\\262O\\027l\\263b\\353\\321\\233\\266\\356B\\250\\025\\312\\023\\034/\\213\\202s\\003\\3135?Kl8\\300\\030x\\031)bpp\\350\\266\\3779\\261\\371\\371-g\\343\\355\\3653w\\357\\0246\\012\\313\\341\\253\\204\\230\\346\\036\\315}\\204\\241\\372"4Cx\\024~\\277]\\3308\\276\\376\\215\\277\\343\\232k\\216\\023t44\\261\\007\\374\\311p\\322\\020\\177~\\223\\0119\\003\\237\\2078$r\\227q\\275\\260Al\\354\\223<\\21663v\\260\\335\\304\\337\\251\\010\\345\\340c\\372\\304\\3679\\013\\322i4\\015\\245\\2636\\367\\203\\372R\\334+\\014\\341[''r\\020j\\374\\317\\340#\\215\\030\\374W4\\034\\254a]\\370\\251\\007\\322{\\265\\3618a\\033\\257\\262\\300\\244\\216R\\354$\\324\\315\\336\\323\\270\\022_\\336x\\025?\\206j\\370\\021$0KP\\204$\\032\\377\\300\\237n<K\\014$\\341\\023\\033\\317\\340\\247\\032;\\3109\\310\\300\\367#\\235\\\\\\210\\034\\362L\\343g\\254\\205\\322\\344\\313(I\\266\\2418\\351h4\\310>(A\\216G\\011|c\\343\\257\\370^\\310\\373e\\024&a\\304\\343\\227\\020K.E\\002\\367C\\024b\\017A>\\271\\007\\366{\\260\\261\\225%\\300\\2175\\036&&RY\\3248\\221\\274\\322H\\340\\3377~L\\256h\\\\L\\316k\\354"s\\032\\273y\\261q#\\3536\\316''O\\2402\\271\\036\\351l\\256\\376]\\362"\\212\\222]\\2158k\\2422+\\240v\\362\\002\\322\\271+\\020f75\\352\\344\\\\\\204\\311\\255\\015\\203\\3114\\376\\013?\\331\\370\\024\\263\\246\\261\\223\\2134\\266\\220\\247\\032g\\221\\353\\353?!\\253\\032Yr\\036J\\221\\365\\215\\032\\363\\341\\372\\323\\344\\365\\372\\231\\344\\303\\215\\001&\\212F\\360\\375pn\\372\\032\\237\\300/6\\326sw4\\246\\223\\373\\033\\233\\311\\307\\032g\\222\\247\\033\\013\\340x\\353\\314\\315\\215\\213\\310\\354\\306\\371\\314\\256\\306W\\311\\347\\353\\277 \\21776r\\033\\033\\337''\\31766\\221M\\215\\034\\351\\251\\327\\361\\201\\215_\\300\\362\\022\\336\\336\\370!\\371\\016\\362\\230\\203\\033\\277&Y$\\023\\033\\316\\357m\\210\\305\\2276\\376A\\252\\210!\\012\\342\\230\\273\\221Mnh\\374\\221\\334\\323\\3703k4\\376\\304\\035\\215\\212\\302\\243(\\312&Q\\037\\0075\\233\\277\\012\\035)x\\350h\\376\\0054\\302_\\207\\026\\361K\\321|\\376T\\324\\306\\316CN\\363J\\343\\340\\017\\251\\350\\025\\261\\201D$6\\352P7%P\\031\\311\\215\\335Hi\\252\\212\\024P\\015\\251\\240:\\322@\\015\\2447^G&2@-d\\202\\332\\310\\002u\\220\\335x\\015\\205\\220\\003\\352\\242\\020\\250\\327T\\037\\271\\240a\\3445^E\\021\\344\\203FQ\\0304\\206"\\240q\\024\\005M\\240X\\343\\037(\\211\\342\\240)\\004u\\007\\352^\\240\\031\\224\\004\\315\\242T\\343\\025\\250\\275i\\320<\\312\\200\\026P\\026\\264\\210r\\215\\277\\243\\022\\312\\203\\226Q\\001\\264\\005\\025A+\\250\\004\\332\\012\\3727\\324\\206\\312\\240\\355\\250\\005\\264\\003U@;Q+h\\027jk\\274\\214\\272Q;h\\017\\352\\000\\355E\\235\\240}\\250\\253\\361W\\324\\337\\324*\\352\\006\\255\\241\\036\\320I\\250\\027t2\\352k\\374\\005MA\\375\\240SQ\\025t\\000\\325@\\007\\321$\\320!4\\271\\361gh/S@\\2475u\\004M\\005\\235\\216\\006@g\\240\\301\\306\\237\\320(\\032\\002\\235\\211\\206Ag\\241i\\240\\263\\321H\\343\\217h\\016\\232\\016:\\027\\315\\000\\235\\327\\324\\371h\\024t\\001\\232\\331\\370\\037\\264\\020\\315\\002]\\204f\\203.Fs@\\227\\240\\271\\240K\\321\\274\\306\\037\\32024\\037t9Z\\000\\272\\017Z\\010\\272\\002\\364%\\264\\022-\\002]\\205\\026\\203\\256FK@\\327\\240\\245\\240k\\321\\262\\306\\177\\243}\\321r\\320\\375\\320>\\240\\373\\243\\025\\240\\353\\320J\\320\\003\\320\\252\\306\\357\\321\\372\\246n@\\253A\\017Dk@\\017BkA7\\242}\\033\\277C\\233\\320~\\240\\233\\321\\376\\240\\007\\243u\\240\\207\\240\\003\\032\\277E\\207\\242\\365\\240\\2075\\365p\\264\\001\\364C\\350@\\320-\\350\\240\\306\\213\\350\\010\\264\\021\\364H\\264\\011t+\\332\\014z\\024:\\030\\364htH\\343\\005t\\014:\\024t\\033:\\014\\364\\330\\246\\036\\207\\016o\\374\\006\\035\\217>\\004z\\002\\332\\002\\372at\\004\\350\\211\\350H\\320\\217\\240\\255\\215_\\243\\223\\320Q\\240\\333\\321\\321\\240''\\243c@Oi\\352\\251h[\\343y\\364Qt,\\350\\307\\320q\\240\\247\\241\\343AOG''\\200~\\034}\\270\\361+t\\006:\\021\\364L\\364\\021\\320\\263\\320I\\240g\\243\\355\\215\\347\\320\\216\\246\\236\\203N\\006=\\027\\235\\002\\372\\011t*\\350''\\321G\\033\\317\\242O\\241\\217\\201\\236\\207N\\003=\\037\\235\\016\\372i\\364q\\320\\013\\320\\031\\215g\\320g\\232z!:\\023\\364"t\\026\\350\\305\\350l\\320K\\320\\216\\306/\\321\\245\\350\\034\\320\\313\\320\\271\\240\\227\\243O\\200~\\026}\\262\\361\\013t\\005\\372\\024\\350\\225\\350<\\320\\253\\232z5:\\037\\364\\032\\364\\351\\306\\323\\350Zt\\001\\350\\347\\320g@?\\217.\\004\\375\\002\\272\\010\\364:tq\\343)t=\\272\\004\\364\\006t)\\350\\215M\\375"\\272\\254\\361s\\364%t9\\350M\\350\\263\\240\\377\\205\\256\\000\\275\\031]\\011z\\013\\272\\252\\361$\\332\\211\\256\\006\\3752\\272\\006\\364Vt-\\350m\\350s\\240_\\001\\375\\031\\272\\035}\\036\\364\\016\\364\\005\\320;\\321u\\240\\273\\320\\365\\240_E74~\\212\\356B7\\202\\336\\215\\276\\010z\\017\\372\\022\\350\\275\\350\\246\\306O\\320\\327\\320\\177\\201~\\275\\251\\367\\241\\233A\\357G\\267\\200~\\003\\355l\\374\\030=\\200\\276\\014\\372 \\272\\025\\364\\233\\3506\\320\\207\\320W@\\277\\205no\\374\\010}\\033\\335\\001\\372\\235\\246>\\214\\356\\004\\375.\\332\\325\\370!z\\004}\\025\\364Qt\\027\\350\\367\\320\\335\\240\\337G\\367\\200>\\206\\356m\\374\\000=\\216\\276\\006\\372\\004\\372:\\350\\017\\320}\\240?l\\352\\217\\320\\375\\215''\\320\\217\\3217@\\177\\202\\036\\000\\375)z\\020\\364g\\350\\233\\240O\\242\\207\\032\\217\\243\\237\\243o\\201>\\205\\276\\015\\3724\\372\\016\\350/@\\037C\\277D\\017\\203>\\203\\276\\013\\372,z\\004\\3649\\364(\\350\\257\\320\\367\\032\\337G\\317\\243\\357\\203\\376\\032=\\006\\372\\033\\3648\\350\\013\\350\\011\\320\\027\\321\\017\\032\\337C\\277m\\352\\357\\320\\017A\\177\\217~\\004\\372\\337\\350\\307\\215G\\321K\\350''\\240\\177@?\\005\\375\\037\\3643\\320?\\242''A\\377\\204~\\336x\\004\\375\\031=\\005\\372\\227\\246\\376\\025=\\015\\3722\\372\\005\\350\\337\\320/\\033\\337E\\177G\\317\\200\\276\\202\\236\\005\\375\\007z\\016\\364U\\364+\\320\\327\\320\\363\\215\\207\\321\\353\\350\\327\\240\\273\\321o@\\353Mm\\240\\027\\032\\337\\011<\\203\\261\\205\\371\\342\\270\\327\\020\\207o@\\370\\031\\350Q\\037l\\366\\356O\\303\\032\\026|\\007\\036\\011`\\377%\\260\\373\\012\\330{\\015\\354\\274\\001\\366\\335\\002\\273\\356\\200=w\\301\\216\\373`\\277#`\\267c\\220J\\002\\354t\\012\\366\\316\\200]\\316\\201=.\\200\\035.\\201\\375m\\001\\273\\333\\012\\366\\266\\035\\354l''\\330\\327n\\260\\253\\275`O\\373\\301\\216\\326\\300~N\\006\\2739\\025\\354\\345 \\330\\311a\\260\\217\\010\\254\\343\\221`\\033g\\200M\\234\\011\\266p6\\330\\300\\271`\\373\\346\\203\\315[\\010\\266n1\\330\\270\\245`\\333\\226\\203M[\\001\\266l\\025\\330\\2605`\\273\\366\\005\\233\\265?\\330\\252\\367\\363w8\\330\\237-`w\\336\\373\\357\\000X\\326\\277\\351\\373V\\260B\\377w\\177\\033\\300~\\036\\004vs\\323\\277\\332\\360_\\374\\035\\015\\326r\\342\\357\\030\\270n\\017\\303g\\005\\256\\003\\013\\327m\\177(\\377v\\260\\0207@K\\377\\012\\264\\325\\257A\\255\\177\\011\\375\\235y\\210y*m\\246#\\351D:\\233\\276)k\\203W\\035\\\\\\367\\003\\240Lol{\\017l\\373;\\272m8\\035\\237\\330\\026\\354!j<\\333\\270\\247\\361H\\343\\253\\240w\\303\\267\\363`9b\\367M\\273/|\\006=3\\377\\231\\231\\317\\014\\375\\362;\\277\\330\\247Y\\277\\312o)k\\262\\231\\317\\273\\375\\005\\327\\374P\\350-\\016k^\\233#\\340\\352\\374\\377\\346H &h\\233\\277\\013IKV\\177\\031\\343O\\256\\331\\205\\033\\037\\337\\205F\\023_\\205\\226E\\016X\\327\\276\\013\\341\\266tz\\346\\241\\243;\\361z\\370\\302\\264\\301\\212J\\006\\210\\264\\245g\\355$\\205Y\\313V\\347\\326\\244w\\244w\\314\\335\\270#=+}\\310\\206\\215;\\331B\\363\\023~\\330\\264cMgz''Z\\276\\372P\\320}VgvN[\\023\\243\\270i\\315\\232)\\220\\016\\033\\244\\3036\\323\\331\\261\\006R8l<\\205\\303\\232)@\\002\\273a#\\256m~z'').Y\\275t\\365\\316SGc;\\247\\215\\256\\211e2\\351\\231;\\357[\\262z\\347}\\243\\261\\314\\2325\\260\\025OK\\012\\237\\333\\017\\015\\217\\227Y\\2002\\363\\025\\000q,\\225\\345\\253wN\\213\\355Dkv\\354\\030\\373\\226\\313\\354<u\\307\\216\\330\\0168\\216\\361\\357\\273\\320}o[\\201\\321\\333WL\\033_\\001g"H\\221\\024f\\356\\302\\247.i\\376tj.\\023\\013V\\3442\\271\\014\\224s\\315(\\344-\\265\\315_\\276z&\\2244\\263\\246\\0355\\343\\260 \\226d\\2028\\205\\035\\346\\002\\233''\\200\\035\\203\\223\\333\\011?vvu\\367Z\\031\\253\\220\\2612\\303,z=M\\356{}\\032\\007\\2463\\315\\336\\007\\2337\\036\\257_K8~&\\354q\\014X\\303\\3471\\027\\364s\\260\\337\\321\\235w\\201\\015<\\032]o\\331\\223\\357\\002\\3337A1J\\031J\\025J\\275\\224\\006(\\315\\244\\264\\210\\322*J\\353)\\035Ji\\033\\245\\023)\\235F\\351\\014J\\027R\\272\\222\\322NJ_\\245\\364]J?\\244\\364$\\245\\247(\\375b\\234\\272\\272\\013|\\022\\017\\341\\016L\\372{\\373s\\375\\275%\\301\\027\\306\\226Z\\251\\346\\217-Bil\\361k\\2760\\266\\300\\206\\315\\305\\357us\\315\\205nLw\\247\\033\\323\\335\\005\\330\\260\\271\\224\\310\\330\\336\\377\\233\\254b\\370\\177\\223\\225\\337\\314JP\\260\\203\\252\\305<kc{\\031\\276\\365\\242\\326\\312\\027d\\263\\255\\302\\014F\\364h\\207%Y\\213fi\\274U\\211h\\221\\301\\031Q=\\226vDkt\\201\\245XiW\\263G\\331r\\231\\304\\343\\214\\034\\217\\263\\355\\355\\334LGs\\363\\232`\\354\\263\\300\\020\\254LB\\217\\217\\014\\273\\206\\037Sdu\\321\\012K\\266\\3421#6Chie\\\\_\\340?\\274\\026\\257^&,-y\\345\\331Q=z\\337m!)6R\\012\\225\\326\\035\\334\\342\\266t''\\264\\350\\256oF\\255hw&\\024?\\224\\037\\034\\344Z[\\005\\265\\265\\225\\2379S<,\\021JO\\012)\\376\\235_\\012+\\321\\276v\\267m\\343\\206\\214\\237\\257X\\246s\\373}Q3\\326\\326\\342\\267\\034$.Y\\311\\254\\337\\277\\022\\017c~\\366\\254\\372\\037\\352\\177`\\364\\216''\\326\\215\\236\\360\\361\\371\\365\\363,\\337Z\\221M\\313\\231\\210+F\\332\\372\\\\)\\222I\\252\\231\\266Z^\\316G\\243R\\254\\326\\025\\023\\303~ZJ\\265/u\\012\\312\\206\\015J\\301YZ\\216\\252\\011\\307\\021\\235\\316\\251\\256\\340\\372E\\271\\330\\333\\221\\022\\323\\216\\301Z\\223\\273"\\274g\\026\\245b~\\025$[\\251\\260\\302\\352\\330\\251\\355}f\\265%\\253\\225\\267n\\317\\351-\\325\\356Pu\\366\\332\\251\\326\\224J\\305h\\373\\344imZ\\251\\320g\\364\\314;=5`\\343\\266\\372\\017\\355\\201\\324\\351#\\255Ng*\\245\\245\\266\\237\\237U\\262\\205!sp\\371\\274\\036\\255/\\025\\346\\343\\027\\235\\325"\\347bC\\372P\\353\\311\\261\\325\\003\\365\\317\\3415\\365\\031\\314\\322''~{\\3325\\365\\346@\\007\\337x\\225$I\\007\\367u\\350#l\\360\\364\\317\\006\\337{7&\\320^gC{}\\010\\226\\355\\260\\274\\016\\355V\\204\\376cv\\263FG)\\345(\\025)\\265Q\\352\\2474Di\\011\\2455\\224\\016\\244t8\\245m\\224\\316\\244t)\\245k)}\\201\\322\\227(}\\205\\322\\275\\224\\276O\\351\\247\\224\\236\\245\\364;J\\177\\031\\247\\256n\\207\\367=\\201\\367\\2535\\035\\223\\017\\240\\341\\026>\\330\\354?\\246(\\211Q3\\365\\327\\266\\201\\333s{\\253A\\363\\002.| \\331\\2622kN\\211\\305\\265\\350\\375\\373\\212x\\365^h\\346/\\276\\310\\356\\365\\034\\233\\203\\226\\215_\\261.\\267\\010\\372\\333\\024\\304\\341\\340\\265@\\333\\226`q`\\361\\241\\215c\\210L\\270f;`(\\011\\224\\024J*%\\203\\222I\\311\\245\\024\\241\\024\\245\\224\\240\\224\\032\\247\\256n\\\\\\313\\324z\\035\\237\\324`\\311\\010\\301\\1779Rr\\202%S+\\010\\303\\030\\327\\272\\353\\277\\037\\372\\343YK\\217\\272\\346\\254\\305G\\341\\302\\244\\372\\253\\3358>i\\367\\251+\\217\\274\\372\\243+\\217\\254?7\\204?\\335}u\\017\\323}\\367\\001\\014YZ_\\204\\031fi}\\361\\372\\273\\326\\327\\255\\005\\370sM9\\340\\356\\273\\361]\\301\\250r\\252>\\233\\374I\\260\\301N\\365B\\344t\\035\\304\\344c\\347 \\005\\307oA\\231\\244\\361c\\230\\2406J\\275\\224\\246P\\032\\2404\\223\\322"J\\253(\\255\\247t(\\245\\243)}\\204\\322\\351\\224>A\\351"JW\\215\\023\\234\\261b\\2518\\204kU8cV\\325\\367\\2409WK\\305\\222\\305\\372I\\314\\010\\274`\\225\\234b\\251\\003\\017c\\037l\\207\\357\\360`I<\\337\\361\\2060l$@\\007\\337\\237\\253\\005\\313\\204\\261\\360''\\332?5(\\265\\011\\033!\\224`\\303`Qp\\316\\355\\025\\202\\205nLw\\247\\033\\323\\335}\\330\\020\\026|\\307m\\323\\227%\\017[\\337\\327\\306\\\\\\264jd\\277\\356\\253\\374\\364\\231,\\213\\271y\\363\\305\\333WL\\331\\377\\012\\274t\\370\\260\\313\\243\\237\\267\\034\\177\\333\\022\\354\\273\\307\\352\\306i\\227}\\370\\250To\\371\\346U\\254\\221N\\222\\301\\251\\312\\272\\242Wl\\011\\253\\341\\217\\034e*V1\\355\\246\\326\\254+\\205\\312m\\276\\352}\\350xWq\\272SNb\\2034<B\\312\\025\\231/\\225\\310\\244\\032w`\\302Nu\\305\\314\\350\\207\\016\\214\\252\\361\\266\\202\\223_\\277"\\347\\344\\246\\206\\025\\177\\323\\021a-<R\\010\\345V\\220)S\\261\\313\\354\\227\\231\\263\\372\\225\\023\\006b\\016\\023\\333R?\\205\\244\\333\\367\\273\\202\\235\\334\\217\\231\\372\\253SW\\254\\276\\243\\376\\334\\274>\\255>t\\020>\\304L\\367~o\\376\\005~f\\361\\231Q\\262\\235a\\243\\037\\232\\322\\266{\\2318z\\324\\302\\366\\256\\371\\273%\\346\\357^&rbO\\237^\\365\\262Bv\\372\\022_\\364\\243\\355Z\\333\\360\\234>\\275?\\233R\\022\\313g&\\224h\\274\\305(N>&\\322o2/\\357V\\314\\376\\310QC\\025\\2755\\231S\\012\\243K\\363J>\\337\\257\\367-\\036\\3540\\333[\\323Jr\\356`JH\\345\\273\\364\\216\\256\\023"\\031\\017A\\013\\354n\\274\\316\\266p\\363\\232\\355.\\215J\\350\\002\\324\\000\\237Z@\\2730\\003\\2368\\346\\263\\0358\\250\\014\\276\\007\\235E\\021\\256x\\265\\370n\\353\\240\\377(\\355\\255\\376\\003\\357\\305\\276\\0123\\270dc\\274\\363\\340\\365W\\207\\363\\253\\017\\373\\314\\372b|\\360\\221\\265\\336\\340\\256\\352zf\\344]V\\316\\323\\231R\\011O\\011\\353\\2216K2\\346\\217\\032\\242Q\\362u\\177\\322\\264\\210\\032I\\332\\24252\\333Q\\234LH\\015\\315 \\205\\002\\016G\\211\\030\\217\\223\\326\\0127b\\253^Z\\023\\314\\305sMYO\\200\\30794\\325\\325\\335\\260\\306+\\013\\226\\031\\320\\245D\\214\\310\\220X\\256\\340\\220''\\221\\335\\177\\324\\212\\005\\246\\346k\\341\\026H~\\366\\210!\\030EO\\365\\253\\203P\\223\\343\\220\\307\\360L\\350\\204\\222!\\325\\031&\\271<\\023\\211\\260|,JZ\\312\\334\\220\\255\\206R\\252\\240/\\230\\251\\213\\220\\207\\026\\031\\230\\034\\322BaEP\\346.\\206\\204\\242a=< \\024\\313L\\310\\225\\010\\263\\366\\373\\365c\\366\\375\\007^\\304\\374\\346\\230[\\277~\\312\\311\\373\\270_9\\342+\\007\\341\\353\\270;\\237X\\372\\307g^\\273\\367]V\\326/\\322}si*\\005]L\\210w+=!\\321K&\\224T\\245?/\\347"\\0211R\\355\\210\\210\\341PR\\216U\\026X9e\\277\\375\\224\\234\\265\\250\\024Q\\342\\266\\005]\\314\\244\\220\\030r\\363R\\266\\253\\222\\020S\\266\\305\\233\\375ma\\3167\\013R>\\273\\217\\351\\353\\207\\037\\316l\\327]sa*!\\245\\374\\020\\027*u:B(\\226\\220\\223\\345\\236\\214\\224\\365\\341\\332\\365\\265\\2028q)V\\232cf\\345\\325\\253\\345\\2549/\\037\\221c\\216)\\330m\\375\\260\\265\\227\\2252\\035\\345\\270\\230\\260\\014\\316\\350\\255x\\234\\247\\347\\244Lf\\261\\351\\352\\2337\\343\\373\\353w\\277\\336\\327\\211\\257!\\373"$7\\256l\\274\\304\\326\\271a\\210K\\217D?\\307\\263\\361m\\370\\353\\370\\273\\370\\027\\314\\\\\\346v\\2040\\217j\\377\\333\\352\\336\\377\\037TAgoV\\367\\2115\\357#\\257w\\311\\375}\\344\\365\\306\\032G\\250\\321=&6,\\321|&~\\232HHx\\347\\032\\2721\\335\\375\\237n\\214\\223\\270\\006\\035T\\007\\026t\\\\{\\023\\373\\303\\270Z\\363\\204\\032\\037\\374\\357\\363%\\037\\272\\260\\232\\217\\275Z\\265\\3463\\245b\\211\\027:0S+\\332\\371\\240\\353+U;\\260\\216=\\330\\243\\010\\371\\366\\366\\027\\203\\354\\213\\325\\032\\017Z\\363\\307\\376\\257\\326\\004X\\341\\217YG(\\221\\233\\302\\002\\370\\332B.\\020\\310\\242?(hP\\342N\\234-9o?]\\357q\\266\\337yn\\337\\317\\331~cc\\\\\\336\\211g_s\\223\\210=\\017\\2475Q\\017K\\234X)\\202x\\232\\250\\245r\\272\\240[2''\\347K\\012\\2578`\\021\\012\\214\\353j:\\303\\232&\\023\\366\\231\\202"\\250\\216\\310Jm-\\260\\203\\011\\326#\\227QEU\\027\\010_i\\2278\\311\\320%=\\307\\372a\\254j\\034.8\\265~fI\\306\\311\\016x\\252{\\360:Oqki;\\263hu\\316\\312\\265D\\325\\310\\276\\033\\303F\\244-n\\307\\367c{\\373H>\\317K\\245";0EX\\033\\265\\022\\355\\226\\034:bcH\\011U\\240c]\\265<\\341$\\262\\246l\\034z4\\244Q\\310\\205\\262+\\225\\332d\\222Hil}\\230\\210\\254G\\\\#bs\\266\\243\\022-\\025\\225\\261fX\\234\\345W\\004Qp9\\216m\\355`\\005.\\304\\211l\\213\\352\\020\\207\\325\\261n\\204tF\\027}\\342\\233q\\2353\\014\\231\\310\\331\\270BT\\325\\345\\334p\\211\\227x\\223\\360\\244\\253\\227\\3459K\\220\\204\\222\\3422.\\003?\\353\\241\\007LW&\\022\\353\\020\\033\\\\]\\316\\226\\025\\242$#"+H\\006\\247\\273\\005^\\024l\\026\\262kcY\\306\\206\\354\\362r\\210\\204X\\215\\321\\024SeT\\301"\\226\\356k\\234.+\\254\\032qUF\\223m\\316r\\263P\\025\\302\\204eJ-\\260kX\\020\\370\\264\\3520\\016\\313\\023\\3462!\\252)\\262 DUU\\3123*\\3071\\230\\310\\034+t\\311J[\\214O\\017\\205\\255Iv\\246\\273\\246\\246\\346&\\327\\276,\\325o9o\\333H,~\\307\\021\\227\\035\\026\\272\\344''?r~\\265\\335\\305\\372=g\\211\\217\\207\\227\\231\\247\\034\\023\\252\\342\\317\\370''?\\324\\341]\\272"\\252O\\257l\\344\\024eR\\241\\034\\023r\\252\\032&\\206)\\265\\370\\321\\276\\271\\242 Z\\246\\303\\263\\032_\\342;\\004\\236\\267XC\\222\\343\\27409\\205\\343a6\\314\\012\\354\\\\\\236\\334\\305H$\\306D\\245\\026E\\322R"/\\366ws\\254\\230P$\\265\\222\\205\\310\\3002\\210\\236o\\321y\\3356\\005#\\257\\206\\231\\010oa[\\345@\\2308\\023\\347;DI\\016\\363\\220dU\\340\\241\\266IZG\\306\\224l\\035\\352MK\\247\\306i\\246+\\272y#\\302\\304\\210\\312\\350\\216\\302\\234\\377R\\375\\325\\025\\314\\257DMl\\267,\\336\\321\\341\\314\\371q\\020\\323\\342-?\\011\\266_\\327Y=\\031\\325Y8\\305\\234\\351W\\344\\020\\337\\333\\313\\207\\344VO\\347MYf\\345hJe\\0255\\30491\\337d-Ef\\305dX#\\232\\344r!\\247\\023\\222\\035\\030\\330}\\216\\233\\364\\267\\266W\\264\\366lBHL\\032\\215\\311\\211R\\305h\\2332\\267K\\353\\316d\\344\\314\\202\\341\\254\\234\\215\\225\\265\\322\\244C\\374n\\343\\224S\\214n\\377\\360\\276\\274Q\\212\\371rt\\332B\\330:\\335\\255uN\\237RV*\\021O\\360\\346\\015\\246\\305\\224\\333\\253uu\\036\\355''\\335\\013.\\300q\\211QEI\\250\\304TA\\015\\211\\234\\224+\\212\\274\\354+\\242\\232\\266|\\342\\211&1M\\305d\\014>Dl\\245E\\220\\005\\233\\343\\271\\356N\\256Y\\363\\304rZ\\026\\025S\\340\\204b+\\234.\\013\\332e^\\265q\\210\\223\\261\\014m\\001\\353\\304g|\\241]\\020E\\005\\332\\300\\224I\\273\\177\\336\\335I\\004V\\022D\\276\\010\\371i\\012\\264\\322\\\\\\221#\\254\\252prR\\363\\030\\217S\\031E\\027dF&&1\\370<Tn\\023j][\\005\\304\\200\\332\\233K\\311\\274bB)39\\211\\227,h\\3509\\311&\\226\\250\\3039\\0244F%6c\\363E^\\200\\024\\004\\362\\200.\\011|T3$\\201\\213\\037\\300\\362,\\243B{a\\261\\272\\\\6c\\235S\\325h\\205U3\\222\\036\\322\\323le;\\266\\353\\371/K\\351\\241X\\375bu\\316\\247\\264\\207\\364o\\341\\276\\007\\317\\307\\342Sx{\\375\\317\\307\\245\\027\\377|Y\\366\\270Mu!y\\273?%\\362\\372\\250\\354-,\\015\\270)E\\355\\027\\354\\356\\270\\327^M\\224\\247\\224c\\323\\022\\342\\300$\\333\\222E\\201\\2206\\276\\310\\011\\222m;r*\\231\\322Y\\360\\260\\261\\310\\030\\242\\035\\305\\311\\004\\336\\375''\\205\\3219\\231\\353\\327=\\316\\227 B\\265|\\235\\267\\015\\370\\022\\2528\\202\\023\\202\\323X)\\352\\274f[\\202\\231\\357\\346\\024\\326\\205\\232=u\\200\\010\\304\\347T\\276\\327t\\270\\220\\250\\261\\272\\023\\323YC\\216\\261Q?o\\011\\226!\\022\\251\\275\\254\\261\\252\\026\\022B\\251~N\\341u"\\223\\321\\231\\301\\330\\026\\011\\306\\266\\360=tl\\353\\264\\361Q\\255\\355\\037\\330h\\326\\007>\\252t\\332\\033\\343+\\371\\2758\\276\\242\\274i\\264c\\325^\\032\\355\\010\\306;j\\365\\207\\030\\226\\277\\006\\256\\3666\\364\\341\\361\\361\\016\\004W_\\206_\\307\\306 \\034JqJ\\005J\\035\\224j\\224\\246Q\\232Ki\\031\\245\\215\\224\\266\\214\\023\\304g2\\216\\342\\232\\2170\\\\\\226\\177''\\362.\\011\\377N\\344\\335\\366\\351\\203f\\356:M\\253\\237\\201/\\373\\012#\\271\\016.\\027\\205\\251a\\270D\\020\\363,\\232#\\363\\252\\357\\352\\241IS\\341Z\\3075\\311\\2309_\\027\\265\\244\\253\\332Cl\\271\\205D\\243"\\011\\3738\\237''\\303\\216\\352\\244l\\305\\032\\035\\002\\037=\\036V\\375\\201>O\\367\\362\\206\\240O\\233eHf\\213\\257y5\\\\(0z\\375!\\3141;\\360\\337\\361\\351\\217\\267\\275\\376\\360\\353Wl\\336\\264I\\363\\214\\305\\351\\254\\224\\203\\366\\356\\265\\364\\030\\274i%\\304x\\251#-f]h\\357\\375m\\320\\316\\355\\230\\342\\027\\346\\231\\031e\\345J%c\\316)\\305\\304\\230\\343\\011~k\\257/\\206\\303\\0205\\364\\224\\223J,\\036\\022\\234\\316\\262\\313;~RL&\\026\\030\\236\\026\\\\[\\241\\376-\\374\\015\\376ZzmQgp}\\203k\\313!4~m''(N\\251@\\251\\203R\\215\\3224Js)-\\243\\264\\221\\322\\226q\\202\\366\\034\\205\\253+\\224\\020\\256\\375[W\\251\\367\\337\\252\\021\\217\\216]Z|b}\\363=D\\014\\331\\270X\\020&\\301e\\211\\030\\242>oT\\342\\024?\\2449\\375\\223 \\370\\214\\251\\24262[\\027\\264\\244\\243\\230S\\330B\\011BM\\201\\204=\\234\\311\\220\\001[\\261\\023\\320T\\247M5\\005+\\352)\\376\\344\\036Ws\\003/sp\\206\\016!\\261\\247\\271\\275L.\\207U\\376\\332\\372k\\273\\217\\253K\\365\\223\\036o#Ur\\320\\261\\353\\327k\\256>?\\225\\0263\\272\\313\\272\\245N\\2357\\314\\270\\020-\\264&\\305t\\310\\026\\354\\236\\212%\\030VD\\366r\\263\\214\\264\\274l\\231\\2346f\\026#b\\324v\\005\\257\\245\\013\\016\\322O\\213\\351\\316b\\\\\\216\\306B\\202\\335^\\014\\361\\266\\007U#>Gw\\341\\322\\202E\\216\\020\\207Y\\304]\\003\\261\\035\\017\\327SC\\026\\012\\2410\\212\\241$\\312\\240<*\\203U\\356AU4\\005,\\361\\010\\032\\305\\355\\270\\013\\367\\342\\032\\236\\012\\346t:\\177&\\324\\205\\013;wa\\031\\352\\002\\203eta\\363\\212\\021J,%\\216\\222DI\\246\\244PR)\\231\\224,J6%\\207\\222O)L)B)F)N)I)M)C)G)O\\251@\\251H\\251\\205R\\033\\245^J}\\224&S\\232Bi\\220\\322\\020\\245aJ\\323(M\\2474\\203\\322LJ\\263(\\315\\2464\\207\\322BJ\\213(-\\246\\264\\204\\3222J+(\\255\\244\\264\\212\\322jJk(\\355Oi\\035\\245\\003)\\035Di#\\245C)\\035F\\351pJ\\037\\242\\264\\205\\322\\321\\224\\216\\241t\\034\\245\\023)\\235D\\351\\024J\\247R\\372(\\245\\217Q:\\215\\322\\231\\224\\316\\242t.\\245OP\\372$\\245\\013(}\\206\\322E\\224.\\246\\364YJWP\\272\\222\\322\\265\\224\\276@\\351\\006J7R\\372"\\245/Q\\272\\211\\322NJ_\\246t;\\245;(\\335I\\351\\036J\\367R\\372\\032\\245\\257S\\272\\217\\3227)=D\\351[\\224\\276K\\351{\\224\\036\\247\\364\\004\\245\\037P\\372!\\245\\037Qz\\222\\322\\317)=E\\351iJ\\277\\240\\364+J\\317S\\3725\\245\\337Pz\\201\\322\\177Sz\\211\\322\\037(\\375\\231\\322_(\\375\\215\\322\\337)\\275B\\351\\037\\224^\\245T\\237 \\214(aJ\\324Nbj''\\261H\\211ZGLm"\\2466\\021SK\\210\\251%\\304\\324\\022\\342\\020%j\\023\\301\\031\\232 j\\0111\\265\\2048A\\211\\332DL-!\\316R\\242V\\017\\227(\\225)\\265S\\352\\240\\324I\\251\\233R\\017\\245~JUJ\\223(M\\245D\\355$\\246v\\022S;\\211''\\354$x}\\235\\340\\357\\011)\\370\\015\\034\\201\\341\\300\\367\\203\\317\\222\\001\\216`\\340\\341\\003\\327\\014\\354\\203\\226\\014\\246\\026l\\005\\237~\\340\\351\\227R\\014l\\015\\033\\303\\3727\\\\\\000H,\\007\\037\\245\\300a(\\015\\343\\336w\\211\\015 *\\200_\\374\\3767\\3266\\303\\004\\310\\267\\037R\\357\\20755\\027\\312\\343\\006\\245\\351\\005\\354}S\\330\\000%p\\337-\\224\\200\\335!\\372\\304\\265^\\370\\352\\347\\232\\245\\247>\\315\\233\\342\\013\\337\\300\\2457V\\345|\\027\\016\\321\\205\\303\\203\\317\\222\\320\\017\\205\\357o\\376\\222k&:\\226Eg\\3401\\215\\345\\003\\245\\013R\\024\\202\\263\\023\\024\\240\\024\\234\\016\\374Q\\2067\\322\\340\\257\\032|z\\016#j\\216n\\037\\353\\213!\\217dn\\225m\\325\\023g+L\\326O\\251\\266\\346F9\\354&#\\021\\034\\213\\210\\266el\\260\\224l\\007\\303\\353\\363\\030\\245\\003\\213\\227m\\332?"\\206\\223De\\362e\\035''dA^\\271R\\026\\224(V]V\\024\\362\\020:''\\303bd\\377M\\214DRY%]\\300\\230!\\214\\252z\\254_\\352\\321\\211f0P15\\222\\3151\\321\\230 \\305\\343\\244\\220''*\\306\\011\\206\\230:\\253w\\027}\\326\\323\\260\\316`\\\\L+\\351\\014\\221\\230\\215\\373G\\305H\\202($\\247\\340\\230\\216qL\\026\\203\\034E9\\216\\261^\\314\\302\\017\\011\\360\\203\\366\\337\\010\\371eRJ\\252\\010m\\220\\350J\\230\\015\\027\\27246\\210\\331[I\\276\\000Q\\226$\\304\\242L.K*\\230\\347L\\370\\245\\253\\340\\263\\276\\254\\022\\334\\314*\\223&\\322\\332\\264\\233\\261\\324\\213\\344\\014\\267\\217\\256\\205\\210f\\313\\272\\200\\331k\\\\9Y\\266p\\207\\2541\\233\\342jI\\266c\\033L\\31328\\246=\\204\\347a3ifS\\2141=jK\\262\\020\\306+t=n\\350\\033\\342XX\\256\\272\\314TOJ\\250I\\313\\210\\252m]C\\026g\\205J|\\301\\217\\211\\204q]\\236\\367\\\\F\\320E\\316/\\360\\245\\020\\3746\\324\\225\\344\\022\\246\\317\\3736\\234\\0356\\312\\343\\010#0\\013\\347\\204\\370\\220g\\352\\230\\257\\230\\252YT%\\355\\310#5I\\313[\\252\\331\\302a\\015\\256\\255#\\204\\346,bx\\034f\\244\\024\\306\\206\\343\\012\\276\\225`\\223=\\3036g\\207Z\\370rX\\304\\216\\200\\211\\007\\021r(\\304\\260X\\210\\373%\\276%\\004\\277\\016\\367$\\331\\204\\351\\011\\256#1LN\\362\\031\\236Y4\\033\\202\\010\\2175\\234\\026S\\265s\\332Xnj\\021\\262\\256\\270\\026\\361\\306\\363b<!\\007v\\316\\206\\262\\232\\011\\356H\\017\\027E\\274\\237K\\224M>\\017m_r\\223\\222\\217\\330\\306?\\320\\235\\244H\\236G\\0022\\220\\217R\\310\\005\\0372\\326\\274/>v\\327;6\\326\\242\\263P[\\253\\205\\252\\337\\003\\265\\224\\347x?\\004\\365\\267\\352TK}P\\243y\\302\\343\\201\\245\\007\\\\\\240\\3708\\205?\\265~\\333Y^\\250\\376=\\270\\\\W\\032>72\\214\\257\\225\\302\\354\\010s\\301[\\276.\\\\\\271)\\330\\274\\202?\\265\\357\\021\\301\\346?\\303\\301\\230\\305\\263\\015\\023\\177\\016\\177\\371\\377Cc\\026\\376\\007;fq\\350\\0073''d\\357O\\320\\300\\020\\207\\274\\006\\246\\352\\001\\250\\207A\\015d\\340\\272\\363p\\335\\203(\\210i^\\011\\207\\205s\\224\\027J\\237a\\346\\304\\334\\217\\356\\2761#37\\2109\\351Z.k\\004\\343\\035\\215g\\032g\\341k\\241\\356\\004\\363.\\203\\230x\\340Ms.\\007\\232W5L)F)C\\251B\\251\\227\\322LJ\\213(\\255\\242\\264\\236\\322\\241\\343\\024\\214q\\355\\375J\\262\\337\\336\\252\\0312\\301S\\366Bu\\330\\272\\025!\\334\\330Q\\277\\213\\031\\341\\327"\\2569\\363\\226i\\216p\\200\\035j\\336\\357fF\\306\\357\\376\\362}\\365\\364\\253\\233g2\\207\\260\\177A\\260\\245\\321\\370+9\\211\\333\\004Q\\362$h\\327\\307\\004\\017(:\\377\\362,\\027K\\305\\274\\300\\013\\334[Ns\\025\\372\\343w\\234\\350\\334\\333\\356\\252\\206|\\217\\255Uk\\205\\267^W\\336\\307|\\0211\\301-6\\307c\\370\\327\\243f\\254+e''\\016\\344j\\223H\\251\\304\\313\\2252\\031\\031\\2266$\\234T\\267#b\\274\\274\\376\\260\\003}\\333\\271\\345Pi\\335\\232\\224\\233.Z\\212\\031e\\013$\\254\\206[\\212^q\\2352u\\220$\\323\\006\\353\\272x\\352\\024\\262"\\027*\\214\\2045\\037\\367\\341\\201\\016\\215\\324\\227O\\3159\\271\\025\\353\\363N\\241-\\256FS$\\215o\\331\\371\\205\\372K\\345\\301\\213\\357\\330\\375\\271\\031\\335\\007\\223\\225\\005%\\227l\\325+CGE\\372\\315\\263\\3176\\373#\\307L.\\032-\\361\\250H\\310O\\234[b\\361\\375\\373\\365\\2769\\303mZ{\\324\\027}{\\373\\211\\177\\311\\012Y\\257\\252\\367\\365\\234\\030\\311xW]\\345e"''tu\\350]\\371\\224\\220\\304\\370\\341\\313~34\\024mm7;\\006\\027\\367\\351\\375\\371\\274\\222\\217\\234\\372\\311\\372IOa\\274\\017\\336\\362\\362\\237\\256)\\327o\\370A\\375\\272{\\232O\\364\\275\\314\\030\\\\/:\\002\\332\\306q\\350D\\260\\347\\227\\241\\233\\321\\375\\350A\\364-\\364\\010z\\014\\375\\015<t\\003\\257\\030\\357m.n\\332\\370\\030\\272\\270\\331\\236,J\\031J\\025J\\275\\224\\006(\\015S\\232Ii\\021\\245U\\224\\326S:\\224\\322\\221\\224\\266R:\\232\\3226J\\307S:\\225\\322\\331\\224>C\\351&J7S\\272\\205\\322.J\\017P\\372&\\245\\207(}\\207\\322\\223\\224~E\\351\\277''\\0103\\224\\224q\\002+D-H\\316\\205\\232K\\207\\332\\300\\025\\020\\212\\244V\\352\\377\\317\\306\\340\\336<*\\333W\\253\\226\\034\\241\\332\\017\\355\\314\\355\\304\\275\\343\\313;\\307\\003\\203\\233\\321\\330\\347\\003\\317x<\\253\\211D\\213%\\347\\2356\\321\\021\\300s\\206\\302\\271\\271j\\320\\366\\334\\367c\\021\\213A\\253&o\\267\\267\\340h\\205\\276n\\210F\\013\\264\\232\\032S(j\\032\\221\\334\\020\\343\\211\\223\\266\\214\\254''\\340\\301a\\360\\237%\\011\\334f\\015\\307\\242\\\\\\331\\224M[\\341\\345\\376n\\201\\025M]\\322\\213e\\260\\246\\216\\304\\313\\235\\2752''\\271\\272\\250VH,\\316\\3306\\307X&\\016\\207\\231\\010f\\261j\\341{\\005M\\347l\\207x>\\317F"L>\\027\\214\\037\\207\\222`A\\207\\030fI\\006WJ\\314\\265\\226\\030\\214#\\207\\007\\253\\276\\352\\025\\015\\301h\\301\\252\\0241E\\323Q9\\245\\322\\241');
SELECT lowrite(0, '\\012\\252\\253\\211\\232\\315\\206\\303\\232 <IX\\214=\\257\\373_O\\212\\351\\304\\330\\266\\037\\347\\3158\\024\\273\\253M\\346\\344\\210!\\031\\216\\242\\220\\241\\020\\027\\362SR"\\265\\2609\\211Dww\\337\\277z\\316\\342\\271x\\003\\303\\271z\\237lH3gJ\\206\\\\\\205\\022\\207e\\203\\030\\361\\254<~c2\\345\\361\\276\\256qZ>\\011\\005Sm\\301\\214\\364(\\2760u\\252\\340+\\335\\246 ~\\376\\270\\372\\035\\252\\252\\341\\005\\213\\2059\\333\\347M_\\226\\315\\2665''\\264\\314)\\305\\244\\270\\023\\\\\\022Y\\234\\226\\306)\\373\\244#G\\341\\233\\237\\2252=\\345\\244\\234\\210\\205\\004''y\\313\\334\\335\\337r\\003\\227\\324\\344\\314\\\\\\322\\344\\014\\315\\341m\\005\\267h\\326+<\\033\\222\\271\\2229=\\376\\257\\347\\305D\\223\\233\\315\\372I\\254\\013\\001\\002\\364n\\032\\247;\\260\\251\\341\\360\\006\\033\\364\\003N\\343Yr:7\\035\\254\\307-\\320\\312\\036\\2026"@\\273\\373&\\364\\037\\205\\177^\\335=pd\\005\\276D\\202\\311\\203<TK\\273\\312\\332\\002\\256\\276QQi{\\240\\215''[\\002\\207$\\230\\211\\370.\\275r\\356\\377\\302\\003H\\341\\352\\033\\011Ll\\356\\010\\375\\245\\377M"on4\\331R\\2219\\306wq[E\\234\\0265\\243\\011[6\\367Y\\244\\360Z\\3247\\274\\362\\305\\335\\221\\366\\\\\\364\\026U\\217\\034\\370h\\367\\241\\017\\256I\\375\\031\\237\\177\\346\\312zq\\343\\267;|#\\\\\\206\\212:k\\241)YmQ=<\\225)\\227\\031\\303 #+\\353\\017\\254\\350\\300\\017\\037\\306s\\326\\333\\347hi\\377\\33140\\275\\024s\\336^\\347\\3638\\243\\216D\\265h\\312\\021\\355\\031s\\241}e]54\\312\\226J8\\022#b"A\\332[\\331\\321\\220\\346eu\\321\\\\:\\337\\224\\365$x/\\323\\334lIZ\\254\\207\\315\\225\\271\\274\\\\4!\\342l\\257\\232\\274\\345\\244\\305d\\202Y\\250~s\\270\\035\\223\\337f\\223\\307V\\276\\177\\322=[q\\361\\025\\253~\\303\\321\\273wh\\241lZI\\246|\\321\\355m\\365x7\\222\\221\\322\\351\\345fX\\337O\\210\\306z\\230\\205gi\\252\\364\\3269Z\\377\\376\\374\\257\\233\\017Z\\242\\277\\275\\246\\277\\376m\\333\\376\\273T+H\\205hT\\214\\326:!\\224wSr\\242u\\261UP\\326\\255S\\012\\326\\222rD\\211;\\226\\350tMvE\\327\\317\\313\\271n\\235m>\\226\\3116~\\326\\3701y\\212\\253\\201\\347\\364\\015\\364\\032\\2527=\\250N\\350e\\247C\\013p&\\352Q\\256T\\013f\\322\\012\\245a\\234y_\\246\\236\\3269\\316''\\301\\354!\\017\\361y\\246\\020\\314\\262\\015\\346\\337\\276\\207a\\376?n\\024>y\\327\\204\\377\\331\\204\\267\\332\\033%*\\224\\374GL\\321\\212\\373\\212\\337q\\2171\\300\\264\\267\\367,;6\\375\\231\\243\\352/\\267\\206\\265pL\\223\\364\\321y\\206\\240%]\\305\\032dK\\301\\275@\\201D\\302L.G\\206\\232\\367\\002e\\273:\\2179\\307{u\\375\\212\\215\\361h\\275\\376H\\375\\236?\\341L\\372\\250\\357>\\373\\322h46\\374\\257g-\\376\\207-\\342\\253\\206dBC\\015O\\025\\212%\\306\\011\\005\\351\\005\\367\\037\\253\\236\\346\\267\\230\\222>k\\232!\\352\\005O\\363\\372\\364e\\346$(\\204\\257\\362\\312\\354\\372\\307U\\215\\375\\254\\3374\\303\\331Rb\\355\\356/\\316/\\015f\\362_:-\\235\\2262\\301\\315\\310\\276\\326\\361\\233\\221\\371\\240f\\256\\\\\\0115sv\\363f\\244+\\206\\363[I\\241~\\027\\267r\\370\\214_\\327\\277\\364\\271\\265\\370\\344\\227\\242\\227\\177\\347\\214G\\257\\036\\350\\350\\372WS\\026\\377\\375\\346\\200o\\032K)\\233^l\\270\\372\\306\\215\\272k,H$\\305\\244\\357\\360n\\271\\231R\\\\N\\224\\024\\254\\225bB\\302\\206\\036\\244\\353\\037r\\360z\\024\\015\\245\\0337\\221W\\271\\021TB\\267\\242;\\320/\\321s\\350\\025\\364:\\306A\\007\\215M\\354`\\017\\007w\\310f@Kx\\032\\374M\\015=\\215f4\\275)\\217R\\212R\\236R\\231R\\205R\\033\\245\\351\\224\\226PZC\\351@J\\207S\\332Fi;\\2453(}\\212\\322%\\224\\256\\241\\364EJ_\\241t\\007\\245\\373)=L\\351\\007\\224\\236\\242\\364,\\245\\347(\\375\\236\\322?(5&\\010z\\320\\011"\\224DJ&%\\213\\222M\\311\\2454q\\236!\\216+UK5O\\350}Oc\\323[\\2559<\\346|\\332\\200''\\2327+\\224\\020\\354\\356{o4\\355\\367\\325\\373\\375G\\226\\211\\370|\\316/U\\241\\224c?\\374\\023\\017:\\027\\370\\016\\340\\015\\347\\206\\361\\304\\236\\343\\351\\014cH\\027\\262\\252Ah\\331\\013e\\030\\006790w\\274\\017?w\\327\\204\\024.\\341\\377a\\235\\276{\\327\\346t.\\345\\251\\3164\\266\\014\\276fL \\321p0\\037a$\\244A\\237\\3050\\323*\\365\\273\\261\\305\\214;\\223CU_\\363\\012`O\\334\\372\\203\\325<\\336o\\373\\343\\323\\343\\2344\\012\\266*\\355*\\316\\010\\311\\027\\230h\\224\\015\\306T[+\\3744G\\013e4A_<[\\027\\0150V\\321\\241\\251!\\335\\215\\250\\2022\\177)t\\357\\261\\250\\036\\031\\022J-`\\254d0.\\340+L\\362\\265H+\\3742w\\206!\\230%_\\365''O\\203<\\023`\\254.\\346$1\\217\\261 U\\302\\272?\\2519\\017Boz\\326\\345\\2228\\0206\\3021I\\346\\327<\\261\\321\\371\\275\\341\\2522\\257\\206]\\315\\235\\022\\014N$4\\311Pw[G_T\\220$\\341IQV\\371\\334@{\\257j\\017\\316\\264y\\374\\234(\\030\\013\\247\\362]\\323\\360/\\311a''\\177\\364\\254\\327x;&\\207\\213\\363\\255\\234\\274f\\215\\234\\263\\346\\226\\003\\017S\\020\\231\\263\\356\\252\\2279\\034\\026\\303a\\260j\\275-\\340\\\\\\306]\\3211\\244t\\231L\\232}\\267xA\\274\\2655\\014\\216\\177\\\\\\212\\267\\004;\\357\\273/\\354\\274\\240\\010\\006&\\230\\315\\335^\\013\\214\\027\\030\\230\\256\\226\\204\\224\\014\\214W\\177\\233\\317yF^\\312e\\226\\232\\236q\\350\\241\\206g.N\\245\\244t\\330eC\\345\\256\\220\\340&\\022J\\262\\334\\227\\225r\\341\\260\\020~\\375y\\036\\213\\230e\\225pZJ&\\027\\031\\276v\\310!\\232o,\\315d\\245\\274\\241]5\\373\\344E\\230\\017\\213\\226\\301\\233vR\\210\\267td\\244\\234\\033\\022\\035\\211I_\\216uV6yC\\024\\262\\331\\357\\033}\\035\\021\\216s\\025\\301\\356\\350\\330\\225\\315"\\031%\\033?!_\\345V\\241\\271\\020\\235\\036\\201\\316G/\\243Wp7\\204\\372\\223\\360@\\323\\203\\375{0\\002R\\255U\\203\\251\\277\\020R\\011\\303\\314{t\\233\\006&~\\325\\037o\\021.4\\2267\\265\\206`\\266/\\201\\012\\312\\275Gk\\301\\202\\017\\355\\257X+\\021\\217\\205<k\\245\\300;\\016\\002\\271"4\\226"m$)\\334\\237+\\226\\336\\334\\375\\006[\\274\\265O\\257\\326\\336Z</\\205i\\236\\236\\377\\326\\266\\311\\013\\357l\\235A{"\\357\\321D{\\337\\243eg\\271\\222_\\310\\365{\\030\\332.)''R+\\366\\277zt\\011\\213\\017;\\005\\177_\\256\\272\\212\\027\\304_\\223\\006!b\\212Y\\2125\\211\\244\\322\\020t1?\\374\\266\\323\\273\\355\\370vA\\351\\356(\\026\\372\\313-\\236\\0329{{0\\216v4\\306\\312\\315\\307\\\\\\347\\326\\237b\\302>\\311\\347\\330\\232\\251@\\025\\345\\325\\321A\\010\\337\\242\\020\\327\\365\\327\\373p\\226\\343\\346\\344J\\245\\033O\\302];\\347u\\366vE\\206\\327\\034crPm\\011{j\\027\\0337\\342\\247\\034\\033\\323b?\\0151L\\030\\217\\316 \\007\\265\\370\\255\\2631\\346\\330i-^\\371\\240#+^koZONf\\331\\301\\244\\235\\252\\345\\335\\354\\321\\334\\3604V\\221\\311\\374\\271\\312\\326\\254\\2277\\025\\331\\356\\360\\333\\267\\034\\234\\017\\027\\332\\034\\315\\236\\316\\340\\371q-\\326\\331\\026i\\333\\242\\215\\316"\\331\\002V\\016`\\210\\210U5\\233a\\272]\\325-\\350\\2426<E\\027\\264lH\\011\\025G*\\266j\\2732/M\\233\\245\\011\\232\\017-\\263\\237\\317\\346\\031\\333a\\231\\372R\\360)\\346\\265yx\\273\\345`\\034-\\267\\036\\217G\\017i\\323\\273\\272\\255\\374\\335\\214\\320\\016\\275\\260\\353\\012\\241\\216"\\210\\005.in\\232\\221\\224\\362\\370\\205\\372U\\351\\325\\021\\376\\312+\\035\\311\\216\\227K\\341\\244\\232Y\\262>\\253\\346"\\333\\326\\265\\345\\310d|a\\236\\311KIcz\\332\\227\\302&4\\213R\\247\\305\\333\\241\\244\\230h\\251o\\2168,W\\277a\\332~z\\375\\317\\317\\315\\335o\\303\\320\\254\\350H\\272\\243t\\375-\\204`\\314L\\036frbn\\326>9\\255p\\276\\210q\\270\\230\\330Q\\3537ke\\206\\021\\204\\216>\\247\\177\\326\\276\\003\\346`\\245\\242U:\\011)V\\324\\226l\\267\\3315\\347c\\211A\\226\\353K\\234>\\275\\325\\356\\222$Y\\0374\\007\\226\\317\\3555\\372\\222Q)\\332\\311q-e\\261\\034\\0312\\007\\246|"Q\\214\\232,{@\\245R\\277\\262\\242:\\372\\314xT\\214\\273\\026g\\347*P\\274pT\\212%\\022a!b\\352\\234\\326^\\200\\260XKA\\2041Kw\\324\\326\\337*\\242\\255q\\365\\270*\\007\\016\\267\\211\\342\\215\\227\\311\\005\\334R\\344\\242\\010J@\\324Y\\002O\\241\\003\\365\\2409\\3151\\342\\343\\320\\016t5\\266p\\177\\360l\\206\\340\\275\\321\\324&*\\253\\343\\007\\023\\364K!\\370\\346\\201\\273\\012M\\265\\023\\007w\\025\\253\\216P+\\372A\\313}G\\335\\017\\326\\326\\336%\\356\\254A\\274\\370\\326\\316\\256\\3264\\025\\245w4t0\\037\\374\\033\\255n\\302j\\014c\\272\\301D\\377\\346\\375\\307cD\\023\\033\\013t\\177\\272uOp \\214\\216;\\030\\306FU/8j!x\\212\\301\\001\\213\\003e\\207\\037S\\030\\213\\351Tr.\\347HNg\\314H\\214.L\\032\\311\\234/\\273W1"\\367\\333h\\244\\2149F[\\261\\037C\\360\\2278\\362h!M\\012\\013W\\260\\254\\252\\215\\214b\\313\\352\\352`f&\\254d\\237#;\\237O\\245\\017\\357\\341\\310\\\\\\300L\\322N\\316\\227\\272z\\231H\\364\\331\\330\\014\\311\\316\\247\\315\\324\\2029\\0213\\222\\324%\\335\\347x\\206\\233\\277\\226d3lo\\017\\277\\3103"%C\\262n\\364\\302+\\346\\365\\270\\232W\\214\\030\\376b\\266\\255\\235I\\245>\\233T\\004-m+V\\013\\223H`Y\\306\\014\\303\\232\\006N%\\371v[qB*\\257\\016L\\022Y\\3111e\\263\\265\\003\\262ueA\\351\\237\\242\\360J\\330\\224\\264N6\\225fB!\\236ql\\034\\213\\221.\\3109\\254\\211\\232\\300\\011*\\257y\\266dw\\264\\004\\203\\357\\260u\\222|\\254\\2678r\\331!\\307~\\346\\216K\\303K\\261\\364\\330\\267\\330\\355\\355e\\273x\\323a;\\370\\241\\253NY\\027\\212x\\241\\366\\002f6$\\012[\\226\\223\\260\\024\\311\\346\\265b\\357P\\213\\322\\232HH\\211\\372u\\253\\2263\\353t\\213\\3719\\026X\\177\\244JXf\\307\\360\\360\\276~\\214\\251\\277>\\251\\223e5u(\\331bGC\\033Jy\\245\\224\\210\\362\\221\\217\\250\\274s\\244\\254\\226\\342|\\334nSZ*\\033CQ\\273\\272\\337v1\\022kUZ\\246\\364\\344\\344\\274\\347\\360\\316\\255\\222\\314h''H]jkh\\377\\366\\224\\226\\015\\207$\\3774\\314{\\323\\300\\345O\\204\\363j\\266{\\337P\\253\\3727\\313d\\215\\340\\376\\243?U\\265\\344V\\206\\351\\224-u0\\022\\025b\\212E\\254tQ\\205\\353\\344\\363^*\\027\\021\\242`\\027\\214\\226\\254\\306i\\272+\\332\\211\\311ZT\\2341C\\214j\\223R.\\357\\032\\320(9\\216\\2679\\333\\216\\361\\321R\\020\\224{&\\257{\\213\\373\\026\\177\\242\\376\\304\\005G\\013\\207\\334\\217{\\361:\\274f\\372\\322\\371g''\\262\\241\\265|\\361\\267\\365\\327/_3;d\\350\\234\\354\\025r\\210k4\\032\\257\\220(\\327\\215\\272Q_\\360~\\027,\\341\\263\\241GU!\\026h\\207X\\340v\\264\\023\\221\\326\\273\\2405\\267#\\265\\351\\373\\022J<%\\007\\266\\272\\275Ia\\272\\256@\\251Bi\\210\\322lJK(\\255\\241t \\245\\303)m\\243t&\\245\\363(]J\\351\\013\\264,\\327\\323u7R\\272y\\234v\\241\\235\\346.t\\353c\\260\\300\\221\\336\\016|\\033\\360mp\\304w\\322-\\202\\265\\273\\036\\033\\373\\274\\013>\\357\\352\\034\\333\\353\\036\\340{\\306\\371k\\217\\335\\205\\356\\243\\251\\177\\207\\346\\375\\004]\\3673J?\\247\\364<\\245\\227(\\375\\215\\322\\356qjF\\003\\3013K`\\220J\\265\\202\\317\\010\\27416-\\302\\347\\203\\321\\333\\346\\275fa|\\374\\015\\334\\205b\\255\\032\\3035\\227\\274\\263\\257\\177\\257a\\200\\211\\215\\337a\\362\\300{x\\037!\\003\\037\\334H\\252\\276m\\317\\367\\263\\343\\273\\227\\362\\015c\\376\\206\\257\\324\\017\\216\\022\\223X\\330;z\\325q<\\373\\370|\\355\\022f\\253\\363dGR\\222:+\\012\\247\\261\\301\\233\\025\\301\\011\\340\\344\\266.\\231\\227E\\255\\365\\212Ss\\341\\263\\3428\\241\\202eL%\\254\\304l\\261\\275\\223\\204#\\0121M\\334\\326F\\246\\305\\314x7\\230\\235\\345\\013\\300\\345m\\213\\032\\221\\221\\271Q#\\236\\361$g\\356\\322\\020\\204\\014a\\315\\233\\307\\266T\\230d\\022\\3138\\235d\\272\\273\\3049\\256\\026.\\032\\242\\271\\026\\202\\001;\\233\\324\\223\\263g\\204\\015?\\256\\211\\252\\304$\\030A\\3018\\0046\\252\\225\\011G\\300\\266\\022\\326\\266\\230x\\234\\255\\250\\242\\346\\212\\234\\324\\327\\011\\036E\\310\\226\\254\\226\\022x\\033\\301#"\\335\\375PP\\313\\222\\314\\0266\\022\\303\\272\\3013\\222\\024\\2152\\020\\254\\004\\316\\205\\0011K\\241\\305\\022\\300d2\\177\\257\\236r\\231L\\366\\337\\237\\353\\310\\375\\005;\\273_\\371\\370\\352\\371Z"\\007\\2169\\261L\\006\\363\\\\$,j\\234\\232\\216jDg\\347/\\374\\331I\\337\\273\\000/\\252\\337\\0056\\1776\\037\\345bFY.\\027\\366u"\\326\\011''X\\021gm>''\\347b>\\037\\356\\250\\371\\242\\237\\316h\\271\\216\\201\\262R\\216\\305\\304\\370@o\\\\\\214yi9\\335\\271\\322)\\251\\212\\242\\226\\234U\\225\\204\\222\\002\\357\\334\\355\\033v%?\\322"\\227\\252\\235\\0319\\353\\200\\007\\300s\\365\\207\\010\\247\\011\\262\\012\\006/\\332\\255\\370\\374\\300\\000\\357+\\275\\021Kp4\\205S\\023y\\025\\254U\\230\\367Sq\\207\\013\\201{\\246d\\343\\006kHa\\301\\363\\372\\025]\\2369S\\326\\225>7\\304\\273\\012\\307\\311!>\\024\\313z\\034qU\\026\\254\\214\\215\\022\\215g\\310ln>\\364u6\\216\\343\\014.\\342V\\334\\205\\373\\361(^\\216W\\341M\\370h|<\\276\\002_\\203\\277\\205\\037A\\007\\240\\300\\272t7-T\\032>+\\315v[\\201v\\331\\006m\\264\\015\\332h7p\\007p\\3078\\367>6\\366\\331\\017\\237\\375\\235c\\333\\326\\200k\\343<\\031\\332\\363\\350xJ]\\335N\\340c\\274\\321O\\277\\263\\233\\246\\335\\374\\333n(\\275\\325''\\200T\\202\\221@\\210\\007\\336\\323]\\200.\\035\\266\\204h"\\317\\324\\202\\007\\265Y\\210\\343K\\201\\343\\357\\275\\311\\203\\360\\374 \\251w\\333\\375\\375z\\033\\343\\271C4\\022\\274\\000\\300\\355\\205\\324\\337c\\206z\\360,eP\\010\\003\\327\\370Z\\261\\324\\333\\314\\274y\\300N\\360c\\012\\203\\220\\340\\020;1\\034\\000#4\\357Z\\005m\\267:\\326\\352\\373\\2701\\034\\017g\\252>\\204\\034\\376\\204IjfO\\306\\355E\\363\\004\\325\\232\\226\\354}\\214>\\004\\031z\\275\\344}X\\234\\361B\\276.)\\252\\216\\273\\015\\311\\364\\241\\255V\\2735\\350''\\035\\331\\356\\252@\\313L\\252\\234\\332[Sy%\\005kZ\\231DRQ\\230\\240\\375\\245\\022\\\\\\247\\023\\270,\\20224Y\\360\\303\\347\\317Y\\332\\2620\\004\\366\\246\\263]\\232\\031\\267c)G\\261\\366[\\241\\012z,l\\206\\247\\317J\\230\\311\\260\\246\\340\\353#\\211O\\337\\363\\345\\372\\177\\373\\317o\\233t\\334\\231x\\301\\344\\276\\257\\021\\221\\221dQr\\303\\272;\\227\\353\\354fR\\341\\243\\373%Is\\240\\265L\\351\\0278\\3216d\\243\\322n\\203/\\244pr\\357$0\\030\\276.i\\035l2\\205\\235\\020Gl\\360\\205\\242L\\247.BH-\\252\\275\\035\\340\\012\\2716o\\230\\037''\\2332\\011\\027\\\\\\227J\\205\\314\\367u?\\017!\\315\\222\\271\\216\\024\\312\\304\\365\\370\\354\\241\\250\\031\\215+*aX\\211u\\265D4\\363\\202\\242\\270\\255\\311l8nD\\246\\341\\326\\326\\016.\\232\\332\\267M1\\354\\255\\016\\221\\325\\\\;q\\375\\034\\317\\020\\362\\270!\\011\\242\\241\\261\\234\\261z\\251%\\327\\317\\351\\004\\324\\014Q\\014\\303\\321Nfn\\366\\2249\\341\\246-\\225DF\\256&X)\\005\\347W\\024\\344\\355\\212\\300\\261\\235\\2618\\243\\033\\002#\\313\\361\\030.\\233\\222\\235\\002w\\257\\257\\013\\2163nIV\\3166\\367#\\014\\363\\221x\\034\\\\<\\302\\271.I\\247H\\267.\\231a\\231W\\246V\\301\\251\\363]9\\324f\\033ke\\225\\347\\360\\031^\\310\\265\\255\\244+x\\206\\315\\333\\2312\\210\\035\\025\\242\\245tX\\364]\\2233\\012\\251\\300\\215\\012\\303\\205\\031P-e\\361b\\205:Q,l-\\211$|\\305\\232\\366~\\015G\\254u\\305\\262R\\261\\242\\\\\\244k*\\030)7+e:\\253E\\271l\\213">\\362\\310\\217=\\376\\214\\367\\013\\\\\\376\\332\\322\\033\\356\\177){\\355%7\\021\\211\\021E\\216SSj\\254m\\225\\023\\267\\224\\371\\207\\231\\272\\243\\230\\304L\\025\\0258S\\340x%s\\021\\036<3N/\\245\\301\\376j\\216`\\307\\252jD\\034\\031\\021#j\\177"$\\270\\206\\311\\201\\037\\007\\306\\322\\216\\260\\014\\026.\\233\\0322U\\206\\311;+:Sr\\306\\217J\\361\\276\\301\\230\\024\\217\\203\\201\\236\\332\\231\\323\\322.\\317\\023\\206\\223\\270H\\3243\\226\\314\\020\\004\\315v\\364\\274\\234\\313\\256e\\252\\005\\242r)\\337p\\264\\327/\\320Y\\216US\\304\\326TH\\023R\\363x\\226\\223,\\226\\265\\373\\206|\\011\\1773k\\261\\234\\243r\\254\\356\\011\\3414a4\\276\\203\\034\\177\\274\\251r<\\026\\360A\\2337\\253*\\313\\262\\374\\225\\202\\014FW1\\225\\371\\363A&{\\236\\340\\007\\017\\202\\306\\263:o\\272\\256\\350EE5\\317\\340\\327\\377G\\215\\211\\243\\243bL\\235\\022\\207>@\\3278=\\333bpfp\\3723F\\250\\213\\267\\011A:\\330\\352''\\311!\\334 \\262P\\034eQ\\021u\\201\\0179\\011\\315\\005\\273|\\024\\3728\\272\\034\\375\\034/\\200\\330\\356m\\215\\330\\203\\020\\253Tl\\316\\204\\255\\371\\244\\257Vm\\266\\371\\222\\037\\352\\365\\306L\\230\\303\\273>W,\\371\\324\\001\\030[x?\\030(\\034\\263M\\023\\315>\\270OS{[\\323\\017\\366\\034\\313j\\302\\320\\202\\023\\365.\\323V\\2028\\021lV\\020I\\325\\232\\017\\216\\203\\235\\252\\222\\340i\\3577\\014''\\337\\212]>\\330\\011\\312\\353\\0077@\\355!\\234dl\\031\\363\\305a\\014;\\276\\323\\261\\232p[\\336\\\\\\362 \\207\\2367\\212^\\205N\\240Z\\373W\\376\\321\\273\\215\\367d\\361\\271\\226\\325\\333\\303\\314IX\\351Z\\210\\235\\264\\370LEf\\210-9+\\327\\205\\246\\202Y\\352\\253\\263,w9v-\\311\\331o\\223\\275?v\\353\\177\\203\\212umO\\322L\\316Y\\2322\\323\\205\\260\\354f\\362\\363\\211\\247y\\345\\250\\031\\331\\207tt\\222L\\206\\011\\271''\\264\\374(\\237%\\265~aY\\304\\212VLa`\\355\\335N!ke\\226,\\210\\332\\361\\214.\\353\\363\\011\\261\\332\\343!9\\224K\\2052K\\244\\236>\\022\\215\\261\\211\\324\\031\\275\\365\\223j\\371)`\\014\\370Z)\\223\\216Gm\\247''\\326y\\\\$\\302\\204\\303\\002\\361}\\250\\246\\311TF\\221t\\236\\257\\346|\\357\\264MC\\327^\\271\\355\\234K\\373k\\365\\027\\276\\305^-)\\037\\303\\232\\206s9\\246\\327U\\375\\222!j\\320Fdp\\222r`\\307z\\246\\300\\272\\250)\\032\\236$K\\204\\347\\371\\004D\\224SI\\3060\\210 \\340b\\236L\\265\\224P\\\\\\345\\365\\331#\\272\\250\\306\\202\\301\\326\\376\\220\\352x\\012/\\2251n\\323%#\\354k\\336$>\\027\\014\\340\\200\\211\\2749\\024w7\\267\\224\\224r2F\\226Y\\007\\231\\016\\313F\\244\\350P_\\024\\347\\031\\206!\\351a\\214\\027\\336\\201S91<{J\\270\\3763\\214\\325%\\030\\037\\360\\373|I+\\366MoS\\333\\223I)\\031\\337t\\376oRR2\\222W\\362}\\353\\274v\\275\\205`\\274\\261\\252\\267{\\033\\272Sz.\\354\\212+\\235\\233c\\211v\\265}\\260VT\\212\\276+\\204\\252\\034w\\322~\\273\\022B\\334\\351P\\333*\\007\\273\\361P\\013\\307\\232\\013\\353v\\254\\335\\342\\310\\320\\240\\254\\311=\\272\\225h=\\016\\243\\363\\227Y\\212+\\316\\233''\\306\\260(@\\24371\\223\\212\\361\\312\\340\\237\\260\\367\\215C\\205M\\017\\324\\237\\254_W?w\\332\\262\\371\\367\\332\\306\\374\\260\\342\\250#\\021\\010\\017C\\026k\\201\\355\\020,\\301\\366}9\\222\\251\\304\\305\\270\\343\\360!\\213`\\236`\\301\\002\\177/=\\310\\023\\016\\023"\\350\\303iG\\362\\015]0\\362\\355\\301 l\\\\\\210\\2252\\276\\020\\006[\\257E\\031\\3549\\254\\243&\\205D|Tu\\224Hp\\277\\364\\257\\015\\023\\337A\\332\\233s\\007?\\202^@\\257Ad\\270\\363M\\363\\007w\\216\\307|\\023\\024\\243\\224\\241T\\241\\324Ki\\200\\322LJ\\213(\\255\\242\\264\\236\\322\\241\\224N\\243t.\\245OQ\\272\\220\\322\\225\\224\\256\\247\\364UJ\\337\\240\\364]J?\\244\\3644\\245\\337\\214\\323\\0073\\203\\361\\203\\230Z\\273uo\\315\\232\\344\\2055{/\\253\\2752A\\363\\305\\027\\231\\257\\355\\225l\\202\\271<\\014\\264\\315\\007\\241m\\216\\315\\011?\\003\\375\\271\\371F\\325\\273\\3374/\\374\\356f\\015\\216R\\312Q*Rj\\243\\324Oi\\210\\322lJK(\\255\\241t \\245\\303)m\\243\\364IJ\\027S\\272\\214\\322\\325\\224n\\244t+\\245\\007)=J\\351\\307\\224~I\\351EJ\\177\\032\\247\\017|f\\372\\007\\221\\347\\216\\017d6\\374^l\\275{\\177\\342\\375\\336j\\310\\030z\\317\\033\\361\\337\\310\\365\\210o\\316F\\012\\236X''\\301|<\\\\\\303\\340\\323\\343\\215\\365\\257`|O\\246~/\\356\\274\\021o\\317\\340\\365=\\365\\327\\352;\\233\\377(\\015>\\222HDj>\\237\\222Bc3\\301\\365\\346\\223\\001z\\363\\311\\200\\340)\\025\\375m\\317\\010\\370\\343\\237\\347\\323g\\005\\316y\\343\\251\\201?\\214?5\\3207\\361\\364\\000F,\\224\\355\\271w\\224\\315\\201x\\242$\\341\\213\\360\\002\\\\\\037\\315\\340\\031\\365\\307\\311\\365\\365\\3232\\365\\253{0\\207\\227\\004\\317j\\013\\365k\\311\\371<\\201c\\353@\\243h-Z\\327\\274\\333s.\\272\\026]\\207\\276\\210\\356E\\277\\304\\323\\360(\\236\\203\\347\\343\\305x9^\\211\\327\\342u\\370 |0>\\034\\037\\211\\037@\\273\\360\\246\\316]x\\277\\300\\212\\341\\375\\360\\246\\346\\261\\330\\224\\302\\224\\212\\224\\006(\\315\\247\\264\\214\\322\\276\\224\\326Q:\\214\\3221\\224\\216\\243t:\\245OS\\372\\014\\245\\313(}\\216\\322\\027(\\335H\\351VJwR\\272\\227\\322\\367(=C\\351\\267\\224\\376Li\\367\\004aB\\211\\236\\027lQ\\212S\\252P\\252R\\232Ni\\026\\245y\\224\\026PZJi5\\245\\003)m\\244t(\\245-\\224\\216\\030\\247\\340\\355\\203\\002\\317\\370\\265\\261\\027mA\\244\\3274v\\305Z\\325''\\377\\344\\246]\\355]o\\331\\325\\232\\363\\347\\336j\\377jc\\267\\355\\212\\245w\\334\\266+\\271\\357~\\353N\\010\\036\\312z\\207\\365%\\220\\016\\024\\263\\006{X\\274_(\\361%\\253Y\\006\\336\\300\\270jw`\\001\\302\\025\\377-S\\003\\306\\337\\010\\006i\\033\\370=\\006\\246\\306B\\300b\\351\\255\\226}\\374\\316>-q0\\034\\346\\321\\343\\232\\210\\365&,:O\\204\\232\\007\\021''?\\214\\203\\375\\212P\\204b\\012\\027\\301\\370W!\\357\\036R\\033\\233J\\223\\015\\356\\335w\\342\\032\\217\\277}\\312\\261''-=\\361!I\\014;\\031Br\\0115v\\360Q>;\\277\\326{\\254[h-a\\363\\315&\\372\\202B\\266\\026\\265\\337jb?\\313s\\256\\3130o3\\304.+0|\\274\\307\\262\\336b\\260\\217\\223e\\265\\243=\\351\\360o\\265\\353\\237\\216\\343\\010\\363v\\343_e\\260\\214Od9\\365;\\245v<\\200\\331P\\227\\260\\250g~D\\356>F\\301\\331\\3153.\\376\\354\\231<\\031\\254&\\235DH\\361O:\\312W\\303)\\231\\227\\005E"\\254`)\\3463\\214\\347\\015\\017\\2225\\005\\2674\\012\\251\\036s\\250\\257D\\006\\363\\241\\302\\352\\215:\\317r\\012\\317\\3463Nr37y\\012[\\251`\\250qm\\025v\\306tis\\322\\311x\\034g\\244\\030\\254\\227\\017Z\\227\\3662\\031\\002E\\364\\313\\007\\252C\\303l*\\303\\012''\\314\\033%x\\332,\\362)F\\016\\211\\354\\266e\\363X\\203\\301\\374\\320\\014r9\\317q\\217r\\246Mn%<\\207\\343\\335Uf+\\243q\\334\\034q\\321\\362\\372\\217\\217\\277!\\344\\344\\214NIL\\024\\325\\342>\\263\\323\\373\\346"\\306\\253\\223\\023!\\017\\312\\372\\246.b\\0216"%\\371\\315f~\\221\\242\\014\\314\\340\\371\\267u\\005\\227\\3122\\243g}\\201\\225\\336\\324c\\314a N\\303\\020\\232\\263o\\356Uf\\265\\014\\325J\\370\\355=\\017;\\003.Q\\375%I\\262~*\\273\\365\\337\\036R\\333\\331\\326\\276\\016\\343Y\\225R\\216_\\270\\357O\\361G\\006q\\370\\361i\\334q\\341}\\272\\332\\314\\230\\232\\232\\273\\012\\366\\216D\\243:7\\271O\\325X_\\014\\377r\\177/\\027=\\271\\267K\\357.d\\370\\364\\264\\005)5\\323\\336auM[\\036\\3225Qr,\\277\\335h\\233v\\\\\\254j\\315f\\230\\031V5v\\302@\\311l\\325TUi\\221d\\271:\\177z\\207\\326\\351K\\262;\\311\\250\\366\\235\\022\\315y\\313\\370\\327\\037\\257\\364r\\017\\367\\266\\221\\231\\353\\326\\021\\006\\317l\\237\\314G\\011\\276\\266\\243\\310.\\313\\346\\270\\035\\202\\0326\\260p\\367]\\273\\257\\312&\\210\\200\\035\\0315\\377\\335\\2523\\270''\\371\\217@\\217\\026\\274\\317\\243\\014\\336\\347t\\264/:\\000\\235\\215>\\001\\321\\334%\\350rt\\025\\372<\\272\\001\\335\\004\\376\\341\\327\\320\\303x\\020\\317h\\276\\345\\264;\\230\\363\\003\\366\\240Z\\033\\033\\350\\346\\370\\361\\246\\310\\005w\\313\\306[T\\265\\326\\034\\3746\\360\\304\\217B\\3636Ym\\334H\\200-\\202%\\027\\214\\2017\\007\\357\\203\\231<\\023\\343\\333`h|\\241\\367\\215\\261\\354\\034\\275#V\\013\\356\\276\\301\\256\\343\\315\\225\\017\\306\\255\\301\\000xN0Q\\036\\266.\\324|\\256&\\200\\325\\253\\326\\340\\253G\\2404\\220T\\325\\011\\022,\\022\\372\\012\\303$.\\015\\341~\\340,\\357\\206<\\037\\254\\035k3\\034_\\002\\243\\024\\214\\017\\275c\\220\\277\\330?aaj\\266\\357U\\231\\346\\323Y\\360\\237\\215j~\\3604`\\2649\\341\\036V\\024\\301\\230\\201\\364\\005\\217Y\\227\\202W\\033V\\233/5\\004\\355\\015&Q@\\262\\301\\310\\025\\030\\272\\346\\346\\244W\\017\\236V>\\226\\353\\354\\024\\241\\0053\\245\\250\\352N\\315\\325_c\\010\\231\\032\\322\\215|5.\\214\\034\\030\\306\\235\\235\\334\\311\\222e\\226\\375\\312\\354\\270\\344\\366\\271\\226\\222a2\\265v\\267=\\2071\\033\\343\\005.\\342E\\243Q\\326\\261\\226|\\250;4\\034\\267R\\213\\332\\377\\200\\261\\362\\334t\\356 \\236]\\275\\236\\341\\216\\020\\246Og\\022\\314\\202\\005\\362&<g\\036\\306\\233\\325\\331\\363I>\\033\\357R~3s:33\\251\\266\\307\\245\\370\\026\\274\\216\\201Rl\\336\\\\\\177^$\\334\\247\\315h\\236\\210\\216\\213\\377\\010\\227O\\342H\\321\\020[\\215\\020\\306q\\3351\\031&2I\\020\\261\\312I\\254|oW\\377\\177\\341S\\277\\272\\252~\\351\\271}\\033gI\\305\\356\\001IHt\\306\\255\\370y\\247\\333\\272\\333^\\216\\346\\322L\\276\\307\\355\\254\\306\\215hD\\250\\316\\272}\\372\\213\\365;N\\331\\266L\\022V\\205\\360\\371/T\\353\\277}\\270~\\3371\\323\\377z\\312\\354\\025\\212\\242e''\\021B\\274,\\301\\375\\331H\\360\\300\\276\\032\\362l\\2113C\\014;+*\\212\\313b+y\\262\\345CD\\225y^g92\\337\\221f%R\\025w1Y\\277\\304\\203\\006\\226\\354,\\213"sd4\\241V\\213\\025v\\341\\242\\305n\\345\\262\\351\\013\\224\\252\\323Wm\\265r\\305e\\033\\007\\207H{\\373\\2405\\370\\015B\\270\\021U\\211Gd\\222\\334\\242\\010\\205\\355\\265\\321|E\\311W\\252S$\\231_}^\\315q\\206\\226\\270\\241\\005\\347d,+s\\316\\324\\2607\\245\\317\\017\\325.\\214\\027\\301\\230\\346T\\266\\324\\315\\341\\223\\332\\305j\\277\\322\\271\\352\\330}\\366\\271m\\312\\342\\335\\217\\254\\\\B^\\364\\255\\336\\0106\\242\\246\\027c\\213^\\375YE\\351\\221-Ra"\\246\\341a&\\342\\230\\316&F\\345D\\021+"''\\237\\341\\350\\035\\247\\324\\277T\\037\\331\\245\\234\\275\\270\\345\\340\\250\\320bg[\\242E\\251\\274dC\\\\\\210f\\252z\\257\\3375\\224\\0306\\247\\025\\363z>T\\177z\\346\\276\\227\\340\\353\\037VF\\246~L\\327.\\251\\017\\324w\\276\\220\\254\\377\\252\\005\\014\\374lp\\345\\346\\034T+e8\\206m/a\\314$\\035\\302D\\244dQ\\314\\213\\202\\205\\203\\367\\325\\251\\030\\237\\371i"\\314\\235RT\\344\\301\\220mObH\\201%\\010\\271\\310o\\210\\334\\341\\334\\010\\264\\377#\\320\\223\\340\\235\\334\\312$\\231\\035\\314\\337\\310\\201\\344{\\354\\024\\366:\\316\\346N\\346~\\307\\375\\215gx\\203_\\316\\257\\343\\017D\\010\\023\\037\\377\\237M\\276\\241\\273\\323\\215\\351\\356\\3437\\365\\366f^\\205\\275\\230\\327\\336<\\256\\275\\231\\227\\263\\027\\363\\012\\336\\203\\261\\267\\362\\332\\233\\307\\345\\354\\305\\341\\013n,\\257\\376\\275\\221Wm/\\036\\327\\336|\\3379\\267\\027\\363\\332\\233u\\003\\367T\\373\\373\\212\\271\\246\\213Tz\\023co\\210\\361\\374R0%\\262\\204kE\\360\\273\\340;\\012\\346H\\022p\\366\\004\\210\\3258\\360\\256\\232/|\\356\\207h\\261?\\227-\\025\\233\\376P\\277\\037\\370A\\301\\364\\312\\022\\370d\\303\\330\\300\\3159\\036\\301\\264p\\317\\015\\0053\\254\\340\\340\\230\\216\\003\\277Y_\\303p\\252\\202}\\217\\315\\351\\242nH\\234\\324\\336\\302s\\274\\256\\212J&k\\010\\206%\\261b\\271Md\\005G\\021\\344<\\011\\207\\031\\303\\340\\260a0\\020H\\345!\\206\\261$^*\\347\\301;\\011\\336\\005\\233Kk\\202\\346ID(\\264@Ba]\\320\\322\\220\\257\\310\\234\\262\\347\\363\\330\\375\\203=\\237\\007s\\333\\236\\317\\243>o\\317\\347\\321\\277\\347\\263\\330\\262\\347\\263\\330\\274\\347\\263\\230&86\\216*\\274\\022\\022X1\\223\\022\\010$\\306\\313\\261\\244\\302\\253\\272\\310\\212\\311\\214\\310\\211:\\244\\223d,\\013\\253*\\023<\\221\\027r\\230$x\\235:G\\204|F`\\005M\\345\\325D\\014\\342}\\225c\\271lA$\\202\\252\\361J\\234u\\034,\\311,\\336=\\302i\\301\\313\\313E\\330\\201\\013{\\034\\303i\\260\\223\\343\\302\\032\\211''\\301\\024\\001\\226\\227\\005\\226w!},I\\230\\201E\\327\\260\\313\\263\\202\\314\\0226\\352\\263\\014''K\\254\\024r`7\\211\\0206\\034\\205\\204D8\\370PP\\032A \\370`\\3164\\261/q\\262\\305\\023!\\021\\203TM8H?"s\\262\\012\\305\\211&xNPEN\\210\\006gGQ0\\201\\305\\266\\230hpp\\220V:\\001{\\250\\301s\\345aX#\\263,\\227L\\013\\204W\\340\\220"\\301\\374\\030Q\\202\\030\\370i>x\\032\\0366r\\004"&\\343\\260G\\360\\276\\366pT\\031\\313#\\226\\014\\316\\204\\304\\2111\\006\\366\\010\\362\\200\\263e\\333L|,\\017>\\223\\204\\242\\251\\012\\247D\\303p:\\0258\\260T6\\310\\003v\\217\\022\\313\\206\\003gq\\375~\\321\\017^\\016/\\004/\\207\\227Z\\012\\002\\221<\\270j\\351\\261\\227\\303\\263R\\276<\\366\\206h9\\037\\\\b\\303\\300\\034\\034N8L\\202\\213\\015''Gl+\\213\\254dA\\315\\310f\\024Q\\325\\241\\256\\264\\214\\275\\035^\\324s\\254\\347cE\\345\\230\\007\\367|\\026\\365\\343M?\\242[\\216\\333*\\310\\262 \\362"3\\343\\210\\353\\3669`\\335\\332\\344\\332y\\033\\276\\275\\244\\276m\\256\\275x\\260r\\347\\360\\246ix\\331q\\227O?\\341\\264\\215\\017\\315\\233\\363w\\343\\274\\372=\\306\\362\\325=\\013\\017q\\036r\\3550\\347\\232\\002)%\\2239V6\\034\\337`\\211\\004\\361\\253tDG6l\\213\\\\G\\337\\024\\241\\035G\\015;\\251\\252\\344\\334\\372\\263x\\3675S\\247J\\252\\324\\341\\204\\270\\220\\250\\022-\\222\\224\\030Q\\266X3\\034\\2035\\252\\302*\\251\\210\\314\\312\\262\\301\\353n\\253\\354\\004/,w\\344\\326\\260\\311Y\\252\\306\\352\\221\\204\\316\\351\\272\\3039\\011\\337\\342MK\\343\\224\\230\\257\\261\\212fq\\226\\335\\016\\311\\342){2\\365\\015{2\\361\\372\\347\\366d\\352x\\355\\236L\\235\\271dO\\246\\376\\332\\236L\\274\\201\\366d\\352\\370\\333\\202,\\346\\203\\327\\360+\\022\\221m\\260\\352\\222\\006_\\034\\330\\337PUV\\011\\207\\024V\\2264Vsr\\202\\311U*\\234)\\344m\\205\\323$\\221\\210\\241\\260L$\\0312\\362\\034\\225\\350\\242\\300\\010aG&2o\\262\\246Q\\020e\\241\\253+\\313\\011|D\\206u\\002\\374\\250\\231`\\266$\\370\\242Y\\012QDH\\3002DF\\344\\203,\\303\\274B\\222I\\242\\360\\021M$\\022\\037\\004\\3666l-\\250D55\\330\\200\\007S\\017]\\017#\\260\\260\\253\\022\\345\\005.\\233=\\\\\\020\\2054\\034\\243&\\213\\214d\\205`?(\\260j\\272\\032\\321\\203,]\\033J''\\302\\346VR\\320\\331B\\201\\325\\205\\224)\\263\\012\\030y\\301\\366 w)8N(\\212*@v\\256%\\021\\221\\327\\211\\241e \\331J\\345\\265g\\004I\\310h\\315\\324\\011\\244\\016e\\010\\262\\262<\\330\\011RW\\274\\340HE8AvZ\\320\\271b\\221\\323\\205\\014\\030qU\\004\\213\\356\\370A\\352\\006k\\204\\202\\324E\\036\\013^P\\026\\036\\326@\\352\\222\\320\\326\\366G8\\363\\3556\\\\\\003Ma5?\\006\\347\\3232y\\313O\\300u\\322\\341z%":\\253\\251\\026g\\206\\337\\270\\240\\256\\316\\033\\301\\277\\210\\020I)\\315\\027\\217\\204ba\\223\\265\\202COF4\\310\\005\\3268\\035\\220\\354\\324\\251\\370\\023{4\\365\\227]\\323\\214x\\216\\351\\036\\246p\\201Q\\346\\224\\327/,\\257(q\\261YJ\\267&l1\\310=O\\354\\323\\271 \\375\\373\\016\\274\\344\\265\\307cw\\230\\027\\340m\\20737\\344\\261}/n\\237{\\325Y\\202\\237~\\240\\233\\267#%S\\020}1\\227\\226<C6,\\302\\335\\361\\311\\012\\301\\227\\235\\257\\360\\266$\\335|\\203\\003\\275l>nr\\035-\\315g\\306\\322\\215o\\263\\347pK\\320\\341\\350''x\\006\\276\\205\\2111g2\\177!\\007\\220\\357\\2625\\366s\\354N\\366N\\366\\001\\3669\\366\\317\\010\\355\\315!\\005n/\\346\\2657\\217ko\\346U\\332\\213y\\355\\325\\241\\222\\275\\230\\327^\\015\\363\\367\\342q\\355\\315a4\\262\\027\\317!\\307w`\\336\\300\\301\\244\\341\\032&U\\277\\332\\201\\311\\330\\023\\252\\301\\343\\027\\002\\307\\013<\\233/:\\266\\020L\\201\\036\\302\\236-$\\203{\\347B\\260\\215\\300\\227\\252\\265*l\\025<\\246\\321\\274\\205C\\364=\\037\\331\\355\\376\\335\\236\\317\\003\\277\\274\\347\\363\\250\\377\\002\\263\\020\\021Z&\\211\\310AHF\\204L\\022\\242''E\\342\\305p\\004\\342)\\225g\\371D\\232''\\334X\\360eY\\014\\304\\004A\\000\\006\\331D\\005N\\014\\202\\315D\\014\\374\\207 \\300\\213\\372r\\020$2\\034\\204kA\\000\\307\\313>6-\\376\\205=\\177\\030\\352\\236\\317B\\330\\363Y\\340\\205{>|\\\\\\262\\347\\017c\\010\\203\\177\\214\\035\\207\\215+\\274\\252\\202\\367\\227\\317\\260\\204Se^\\212\\305UN\\321\\240n\\244s\\002\\341t\\250d\\011\\306\\011aM\\013\\336\\034\\215\\241r% y\\035b\\372t\\002v\\323\\241\\376%\\242P-\\035\\201\\341\\223\\301X\\210\\243rr\\004[6\\377\\265\\211!\\011\\033\\252^26>\\\\\\340G\\203!\\011\\330.\\232\\010\\206\\013\\336eH"\\250\\261\\034d\\237\\030\\037.\\210\\204\\305\\261\\341\\002H\\236\\010\\212\\302+QbY\\315!\\011\\346\\260%\\337\\336\\360\\377\\270;\\023\\030\\273\\312\\353\\216\\317w\\357\\267\\335}_\\336\\376\\346m\\263\\3313\\036/3\\306\\366x\\301\\216m\\214Y\\214mJ\\025ch\\211\\020\\245\\011%-\\244\\024\\322\\200\\004I(R\\001\\023\\001A\\201\\244AjE"\\0124\\244Y\\010K\\022\\250\\240\\324\\264i\\002T\\021\\211Z\\025\\250R\\0115\\241mP\\224\\214\\237{\\316}\\357\\215\\307\\016U\\001\\351\\276JH\\370\\311\\276\\334w\\316\\375\\226{\\337\\371\\235\\363\\377\\276\\273\\007\\250\\372\\360E\\007\\376\\342\\212\\305\\357\\320\\271[\\026\\326\\337\\357M\\234u`a\\245_\\216\\326\\037\\274\\214\\374}X\\233\\357~\\257{t\\262d\\237e~c{e\\363\\247\\177\\274\\361\\037\\277{\\025\\017\\270T\\351\\305\\327\\237\\263\\347W*\\261\\356{h\\2540N\\325K\\327\\315\\3177\\310t\\236\\340\\362H\\236\\306\\311\\003\\271Z\\377\\346\\324\\024G\\272\\000 \\340\\032\\260K$\\010\\0077\\246\\037\\000\\200 Y\\3058\\322\\002\\310\\313\\255\\001]\\264\\333@\\0275\\017\\340\\006\\206J\\017b \\017\\0350$\\302#&`K\\350\\201\\021d4\\263.4\\276)\\317K\\357>\\225\\253\\365g\\362\\264\\336\\312\\023\\213\\2247\\362\\274\\364\\207ff\\020\\323\\035 k\\016\\360\\031\\246@\\302\\322V\\255 \\206#:\\260i!\\322\\250\\320\\340{~S:lb\\2029\\262\\001\\317\\017[\\003\\376\\015\\023\\360n\\300\\\\K}\\230#\\026<Z\\021^5\\234Bv\\0230}\\361-\\230\\214u\\313\\354\\241\\256\\033h\\247\\2004\\302k\\206\\272^MX8\\031-QG\\220F\\324\\035\\200t\\017u\\021\\244c\\037&#\\240\\256m\\003\\352\\362\\251\\251\\227\\235\\217\\010k\\326\\330Ybc\\007\\307\\351\\015\\335;\\346\\311\\225\\257M-\\376\\346\\276\\261\\311\\2637\\034\\224\\204Z\\263A\\243\\263\\343\\251\\373^$G\\310\\323\\325\\311\\373\\013\\373\\267m\\335\\331=zW\\351''_\\337\\344z\\012\\231\\032o\\231\\177\\354y\\237x-\\274\\344\\255\\013\\271\\330a\\331\\212\\002Lx\\374\\312\\343\\367\\320#\\354\\222\\221p\\344\\206\\2217\\310\\005\\344\\031e\\225r\\257*\\325\\253\\324\\177Q\\337P\\177A\\015z\\210\\376\\016\\375\\330\\310H{\\214\\267\\024\\277\\025C@6\\327iq\\235\\370U27\\314:\\3340kc\\311\\373\\324\\3270\\307k\\230\\276\\206\\010\\0363\\303iW4\\3649\\277\\\\>7\\277\\354\\357I\\234\\214\\305(\\324\\023\\370\\337\\030wT4\\20088\\243d2\\344\\031\\362N_\\371\\313\\007\\357\\374]\\262@\\032c\\011\\2117\\343\\202\\317ib+cm>\\255p\\001\\356\\341\\032\\324;''\\377\\244{\\321\\0157\\222\\311\\013\\233\\265\\257&\\237\\352\\376\\360\\350\\013\\363\\227<|\\354?\\216\\335k\\324\\252d\\245\\257\\205\\015\\223[\\247\\2555\\230Y\\3675\\177\\345l\\250\\207\\011\\3744\\254\\231\\267\\245\\225\\272\\272\\263F\\251TT\\210By\\034g\\233\\3538\\272\\203\\261\\341\\346\\365\\006\\276\\217O\\217VM\\273\\272\\027A\\374\\270a\\213\\305\\315(4\\202U\\274VW\\\\O\\250j\\376a\\365\\235\\371\\273x5\\177\\027\\335\\215\\371\\373P\\272\\371\\227\\023\\037\\310\\277\\031/r\\340\\004\\270f\\374\\202\\310x\\002\\010\\304(\\224\\261`\\011DT\\256\\001C\\331\\222\\3132q]\\204\\033\\210KI\\220\\025,5,X6k\\360\\015\\334\\005\\244T\\304\\202%\\264k\\264\\211\\245],\\017\\253\\236/5\\252<q\\242x\\254\\312\\254\\300\\351#\\237T\\340\\303\\202\\020\\267Z\\307\\016\\321\\031 \\324\\311\\305c\\260\\347\\000\\265\\267\\352\\274W<.\\2274\\241\\233\\340\\241\\321\\206K5\\315e\\305\\343\\017y\\226\\256\\267\\003\\317\\322\\364N\\312YF\\371\\032U\\375\\351\\025\\227\\257\\014\\316\\331\\265\\252\\274?m\\037\\232\\026\\023[<{\\377j2\\321\\340M\\241\\0131\\327\\012\\177\\333v\\232\\355\\317q\\307\\3357\\277qL_\\353\\273m\\226\\304\\326B\\263\\263\\367rS3\\323BQ\\023\\201\\334\\240}@\\227Z\\201\\250\\261cOh\\3329\\263J\\337\\302\\357\\012>y\\354\\360r\\274b\\243g~t\\303\\326m\\013\\341\\346\\325\\333o\\236\\177s\\342\\263o\\222W\\310\\026~\\307j\\367\\356\\015\\177.~\\277\\373D\\367\\226\\356O\\237=pX\\375\\221\\351Y\\233\\323T\\026\\002\\210:\\353\\035\\214V\\341\\361S\\237(\\211R\\030\\362p\\242\\031p\\337MD<\\272\\311*\\311\\335\\273e\\311Z\\250\\2062v,n7'']\\356x%Yn\\217\\306<\\265!\\236\\233h\\370\\3143\\312\\242\\\\\\330by\\346\\376\\375\\307&\\362\\214\\207_\\310\\323\\370ey\\032Wv\\344i\\275\\373j\\236U+e1\\317k\\377-\\241\\311\\006V\\007u,\\025FX\\267\\203K\\367\\022@\\013\\003\\276\\233\\364K\\205\\246?*\\0346>\\316\\034\\321\\360\\340\\012dV*\\204\\263u81\\362\\001\\216$\\\\z\\022\\030\\275R!0\\210&\\246\\247\\027o\\000\\302i\\332YyM\\321\\374X\\243z\\277\\020I]\\003\\232\\233F@ \\36041\\203\\206t\\351\\344$ue3+D\\012\\252E\\005\\355D!\\022\\216("\\015\\373\\326\\235VV\\210$\\273\\264N\\020\\303\\205O\\004\\241\\3655\\325\\223B\\300\\235/\\271\\270e3\\233\\211\\253\\2215\\343\\361\\205\\261\\340\\374\\017\\214''\\366\\323\\324P\\022=\\355\\220\\025\\372\\231N\\375\\312\\2116I&\\253+\\375\\340l\\243\\270{\\262\\276\\355\\354\\311\\215\\0077\\214\\035\\2362.8\\267X\\264L]\\345[\\265y\\2419\\305b\\331"\\323S+#\\252\\326\\230IR\\275\\320!SSd\\343\\261\\357.''\\240\\235\\235s\\333\\254\\260]\\2371\\305\\245v\\266\\267Qr\\374!\\232\\260c#\\336H1[\\261{\\230<\\257\\314)\\367\\253\\266z\\255\\372o\\364<\\372$\\233dwA\\207]\\301\\257\\033\\031\\011\\222\\271D\\255\\022\\272Y\\311\\2665\\355\\214\\021\\336\\031\\0130I\\374~\\0251\\0163\\220\\034l\\350\\370\\216|\\375\\232\\345w\\344k\\351\\344\\366\\020\\3335D\\310\\030*\\024\\016\\323\\3270\\347\\374p\\346F4\\304\\271q\\222\\257w%\\352^\\367\\256}\\015\\177\\274\\206\\343k\\230\\343\\325kW\\260\\214o\\023\\347\\354\\242B\\356''\\245{\\376\\364\\032\\361\\330\\276#\\317\\273\\177@\\376\\341\\322W\\034u\\363\\325\\217\\355\\331\\012LA\\360''\\033(\\207\\227\\013L\\311(''J5\\256\\033\\020\\270\\024JH9\\202\\313\\002\\261m\\334\\374\\025(G\\001R(\\000~X\\034\\230\\246\\304P\\276\\310\\365$\\316(G\\245e\\254\\251\\351:\\323R\\305\\001\\312\\221\\224\\034\\373\\275\\3741g.\\177\\027/\\252&\\033\\245U\\177\\316\\262\\2541\\251\\311\\035[\\031\\227\\035\\3234\\347;eY\\366C\\352\\343[\\033\\374 \\221\\321dT\\245U=Vp\\317i%b\\015\\332\\260\\0274S\\2571!\\316\\330)\\204\\254X\\246\\275\\261\\023\\351\\251\\013\\33433\\0270\\317/\\353\\225\\251\\264\\252\\216B\\344\\343\\325"\\262\\235AwGx\\011L\\021\\305\\024\\206\\005\\313M\\021\\340\\250\\246C\\377&\\005D$Ay\\202;.i\\232\\242\\300\\320\\270\\216\\222\\000K\\231Ta\\025\\024\\307\\0320\\010I,pX\\024ZB\\034\\325\\021`Uh>\\016\\313\\367\\363\\357\\262\\273\\363w\\261]\\000#\\026\\001i\\201>\\005\\320\\247"|\\254\\360V\\014fX\\000\\252\\225\\232\\206\\202\\\\.+\\004\\350\\023\\010\\235Z\\226\\022\\004JEr\\315\\006Tm\\3263\\35123\\313\\300\\267\\272\\311\\025:\\332\\202#\\206\\311\\365\\262\\202\\202\\\\\\224._\\233\\1773\\310=}\\2126\\221\\323\\033\\031EC\\243J\\025\\223\\031=\\011\\266`\\302\\221\\374\\004E\\003\\247\\207>\\251\\302!\\027)z\\024\\206\\327\\201vT\\3122\\243h\\332h\\313%\\212\\016\\261\\031/\\0152\\001(\\317\\253W\\372=U(\\353<\\353\\251r\\015<`\\365\\374\\3552\\001\\340\\241\\221e\\002\\300^\\251\\210\\265HFY\\275\\331\\353)c\\220\\011\\370+\\356\\271)\\026;\\341\\314Z\\031&\\256\\253s-\\305\\354\\027\\326RKUt \\231(\\365\\034Px\\222\\370\\276R\\202\\026,I\\243\\321~\\001\\245\\321:\\313\\244\\321\\264_\\353t{\\265N\\362s\\274(\\017\\207\\014\\376\\177\\034R\\302\\360&\\000\\357\\252\\320\\230\\302\\202\\210R\\246q\\225\\0058\\324R\\022\\005\\376\\340\\200\\203}|6%\\021\\334\\033X\\245\\363=h\\221PU5JPE\\216%[l2\\347*\\351\\276\\205\\022\\357x\\220\\367\\201\\033\\211\\273\\320Oq\\001\\206\\314\\024Y\\336\\207r\\314\\373\\024p\\250\\341\\266\\243\\360\\307;Q\\263\\255\\015\\362>i\\002\\375\\244SJ+\\365\\354\\211\\230\\345}<\\001w\\336\\020\\346\\323\\326\\345Y\\013zc#\\271\\356\\330\\247\\037|~\\364\\266o/\\222o\\277ta\\370\\312\\355\\273\\177u\\353\\316\\253\\277\\251\\234\\277\\327|\\262\\033\\034\\206.@\\021\\265\\251IE\\272\\001|\\230\\300\\261nh\\001\\222\\0010\\206H\\266\\002\\020\\327-\\013\\2136\\233\\324\\022\\025Gc\\006nl\\340E\\002\\310\\326\\242\\266\\357\\352\\252\\201d\\033\\272\\031\\331\\302\\215V\\023\\222ML(2O\\262]\\274%O\\353_\\265\\250\\257Y\\332V\\243F\\253Z\\240\\306n\\342\\213\\304-\\361J8\\037\\351q\\311\\225\\336\\372\\265\\256t\\322P\\017f\\027\\244\\243\\025\\271\\306\\317\\330\\313%/\\353\\266\\276\\305IyQ\\000\\311\\372\\345\\220\\206\\006\\374\\012\\024f|-\\0145jl\\232\\203K\\363\\023#\\231\\330\\006~<j\\261\\375\\347G\\3705\\340e\\003(X:>t\\253\\016$l\\007\\000\\3520\\010Z\\200\\245b,,;%x\\304\\217\\216RS\\224m\\370\\335\\306Ap\\003\\230\\230\\022F\\313wt%\\033\\204\\300\\205Adp\\304\\254\\200\\331NGy5\\317nz(O\\343Os\\314.X\\254\\227\\273\\210%\\312\\234U\\323K\\354,w\\241\\017d\\316f\\320\\200\\276\\236\\230\\240.\\346.\\240\\317D\\226\\273\\240\\232\\206\\331\\005\\277/sN\\002]Y\\312.\\360\\231\\231\\356\\277\\346y\\351\\257\\013]\\264\\300\\214cH\\252\\373\\221\\216\\211\\021\\030\\244\\324aY\\332\\005\\246\\215b\\350\\340 lI\\007\\025\\332\\216ly\\026\\2654\\270\\277\\243"|`b$\\361Mj\\301\\245\\31342\\024\\203\\303\\2458m0;;{\\354j\\256\\213F\\246s~\\233\\244\\216j$\\250s\\036$u\\350\\3708\\305\\244\\016\\352\\234%\\352\\234\\265\\236\\316\\271\\227\\324\\351uLO\\347l7\\205\\316\\247\\247\\225/\\013M4l\\263g\\275\\247\\2426YOE\\215\\335\\036c\\022(\\263^\\227=\\025\\265\\254\\273''[W3\\353\\231\\212z\\320\\355\\216\\323\\314T\\324\\335&\\343,\\325qF\\303\\357\\020<\\303\\024\\256a>\\315\\205\\323\\300\\202pm\\251\\010\\224\\227\\2331\\327\\325RI\\325y\\014\\341\\250D1\\271\\345\\302\\331\\36042\\034\\023\\272\\234\\301o\\200k\\201\\013\\012\\327\\243\\247`\\266VSvc\\246\\016;\\003\\214\\352^\\000=i\\302\\214\\360"\\350j\\034\\270\\320[^\\026o\\265\\230%j\\316\\362\\262\\270t\\324\\023\\231\\272\\254,\\316\\240\\331V=\\313\\324\\221\\237\\3449e~\\371\\300\\362\\234\\023\\276\\347\\316=\\376\\374\\361G\\325\\177f\\373\\376\\027\\015\\365\\303\\364[\\3649\\372\\022\\233\\031\\031\\011\\336\\003\\245\\275{\\302\\350\\321\\3140\\011~\\230\\3444\\034\\202\\037&}\\366\\211p\\210\\276\\304P|EC\\037\\257\\377\\207y\\230\\363\\275|\\322<\\034\\216\\257\\316\\334\\374\\\\oc\\374\\316\\374\\262\\277/\\257\\310/\\327Y\\253\\353\\34255\\202\\027\\200f\\242\\261l\\377\\233\\261\\325\\331\\347\\374\\014Y\\227\\275\\210Kd\\373<\\327\\010|$k8:\\217\\223H\\304\\274\\325\\231\\307M\\277\\032\\035\\260\\015\\007\\247\\025\\233\\000#''1k\\315\\317=\\007\\304\\205/\\236\\301\\000\\02600[\\363j\\224k\\270\\346U\\243\\262\\326\\000\\336vt&k\\304\\367\\021\\270 \\236%Q\\250\\324\\000\\023\\261l\\331A8\\261\\001\\337\\253e\\010\\313-\\006\\010\\326\\321p\\315\\2530+\\031p1\\362\\327\\371\\207\\371?\\317\\337\\205\\362\\361\\374}t\\013\\371\\373\\370\\020b]\\202o\\352\\340(\\305e\\312r)\\256*JKR\\\\\\264\\337_\\271\\013XW\\002\\374\\355Kq\\001\\373N\\225\\342r\\335\\350a]\\266r\\367\\277p\\216\\274\\3235\\316@\\246*\\256q\\266\\225\\377s\\2153\\323"\\305\\262\\201W\\011\\371F\\376\\035\\365`O\\350\\242\\007\\015\\223\\333(t\\261\\360\\215\\260+W\\007z\\220\\200\\2335\\353-\\315.\\270\\232\\375\\353B\\027\\267\\240\\365\\205.i\\244\\207\\263\\323\\256\\341E@\\367\\033\\266\\330\\334\\212"#\\234\\035\\010]\\272?\\200(6\\351%\\035E\\271\\230%\\035!\\244,@\\013 \\312\\343\\205r\\017\\261%J+\\006IG\\030\\213B\\017\\261Y\\255\\234!6\\004\\320I?\\351X\\251\\365\\021;U\\373IG\\262\\217\\373^\\001\\216\\004\\275l\\207\\312\\263lG\\3118%\\333Q!\\331B\\355,\\237\\222e;\\270tN\\312v\\024z\\272\\007\\232e;L\\370z\\211\\372\\275\\274\\020\\231\\024\\272mW\\2444,\\247\\262\\202\\012\\306\\015UQ\\031\\267\\027o\\337t\\347\\356\\365\\333\\343\\355;wm\\371\\370y\\352\\017\\226\\377\\353\\013\\224\\357g\\222\\225X\\251NZ[\\205\\250\\033\\206\\247\\222@\\010\\271N\\254\\344\\016\\023Q\\000\\374\\255\\\\1V\\023\\374\\221\\333\\211\\344\\037\\235\\331\\353V\\036\\275s\\276\\373\\237\\335\\203\\247\\235\\3637\\376kW\\007O=l\\305_r\\376\\350\\363\\327\\377\\355\\221\\257|\\177v#\\271\\212\\334<?\\007|\\243\\313\\216\\343\\364\\226\\203\\006\\330\\273X\\222\\015\\012.s1\\331R\\300*l\\0376\\\\\\266b\\005se\\033\\236xv\\006\\033\\231\\300\\025"\\332$\\204\\257@\\320,\\340l\\025a\\303s:`v\\365\\352\\017\\346\\031\\363v\\267\\345i\\375\\221<\\215w\\277\\222\\247u\\272\\007\\365\\275(9\\320\\264e\\222\\003\\004\\231\\236\\276\\027\\241M\\342\\221\\023b\\363\\223%\\007\\0102\\003\\010\\213\\274\\236\\2767[\\312\\312\\247\\246^b\\202\\027\\365\\214{p\\211/|\\240\\036\\271\\277\\304\\027\\334\\341\\022_\\340,\\270\\333\\227\\226\\370\\232\\332`\\211\\257\\000\\006\\203\\023\\035[\\003\\260c\\204\\371p\\266`\\340I\\357-\\361\\375|\\236\\375\\262\\3703\\323\\2637\\247\\005\\221\\372@\\310\\2656ZN3y\\215,\\242\\274f\\262\\025\\362\\300Me<\\272\\311.j\\273wkE{\\241\\026h\\261\\015\\036ZS.w\\275r&\\257\\021\\011\\312k&\\233\\001\\312kd\\271\\260\\305Fy\\315\\247\\270\\306k&\\222\\257@Q5v\\272\\201:\\217\\214\\036O\\326yX\\264\\325\\242\\226\\250:\\375N\\207\\347\\035\\352<\\240\\323\\335St\\036@\\217`vb\\242{\\010\\000\\270ig\\324\\216\\353\\007$\\325\\021\\341}\\244v\\\\\\235\\234\\204=\\256\\266\\374Q\\370\\031\\031\\037\\007\\256F)\\206)\\341\\351\\035\\244\\022\\323\\031\\324\\215\\002\\213\\236L\\355NK\\240\\024C\\265\\234:\\227\\206\\347T\\204\\324o\\024\\226B9g6|.Jc\\037\\327\\333rm\\250V\\266V\\213\\306>\\246\\015\\376\\241\\374b5\\031m\\220j(}E#j\\252P\\267\\331h\\022#\\212BC\\360)\\261\\232B\\223\\214H\\212\\333?\\313\\025J\\327\\026\\222\\035+~\\243`\\234\\366\\357\\037{$\\\\|<\\221\\337\\213\\257~\\274s`\\257\\363\\365\\211C\\254\\361\\231\\356\\017\\037}\\364G\\257o{\\341\\350s\\310\\256\\372\\210y\\374s\\352\\014;4\\222\\214\\3348\\362S\\362A\\362\\254\\262F\\371\\202j\\250\\177\\250\\336\\224\\355\\373\\270\\210;\\020''\\331V\\262\\363\\235yo\\013\\031k\\212\\200\\315\\217\\215\\212\\265C\\015\\267\\207\\211G\\303\\014\\267\\207\\211b\\303\\354\\303\\241.\\235\\035\\242\\257\\241\\246\\004\\226a\\226\\351\\236\\245\\020\\343\\310\\027/\\240\\257^_\\370\\361\\245\\204\\254|@t\\277x\\375\\207\\275\\374\\003N\\232\\277\\013\\3622w\\035\\334\\266\\307_\\256\\345M\\213KZ\\336^\\360/K\\344\\324\\340\\377\\304:<|''aq)\\370\\257\\367\\267\\355Y\\012\\376\\037\\317\\275\\274V\\317\\277\\237\\272\\237\\311\\337\\307\\020T\\356\\344\\012\\253\\335"\\353b3\\031w\\204\\273}\\263\\315\\355NlD\\3536$fR\\362\\204\\273\\351tWs+\\201\\341/(\\243\\243*\\030\\227\\205\\2022\\326\\246\\233<=\\250\\350\\334\\336}:X+\\246fz\\332\\\\`\\206\\005Mh;\\366\\270\\334JS3\\331 Zm\\342\\005\\232z\\335\\216\\277\\334\\277\\365\\254\\342Y\\373\\316\\333}\\333E\\364\\262\\372\\206k\\026\\357\\275\\273p\\337\\236\\033\\310\\205\\364\\313\\205[\\311E\\344[\\315\\257\\3355\\271\\370\\211<\\003\\241\\356\\223yZW7e\\313\\313,\\254\\013\\366"!9\\310\\243/)^\\007y\\364\\201\\342\\325\\031\\354\\244\\022c\\2542\\310\\243c\\254\\222\\305M\\375<:\\206\\237G\\363,_\\220\\333\\362\\354\\230\\217\\344i\\274\\373Z\\256\\326?\\354\\204\\316\\031\\025\\210vc\\237\\207\\355\\025\\201\\210*EYl\\257\\252k\\265\\004~&f''b\\031\\372p\\244\\263\\323\\256\\353\\007\\016\\350u{W36\\012.D\\315\\343\\263\\201\\010\\302\\2726\\272b\\254(JX\\251Z\\325\\211\\204o\\327e\\275v&\\230\\275\\370\\342\\356y\\366\\245\\334\\234\\326O/\\260\\326\\271\\235\\221\\021\\357\\370\\265\\307\\377N=\\306v\\214\\264Fn\\037Y$\\227\\221\\027\\225\\255\\312\\203j\\252\\336\\244\\376\\214\\036\\242\\317\\261\\265\\354\\317\\270\\311\\257\\341\\237\\034\\031ic\\252\\216\\217\\251\\235\\371\\261\\266\\200\\2200\\350\\214(\\375\\274\\035\\261U\\305\\257*\\357\\327\\\\\\3570\\177\\364\\207\\351\\353\\275\\344\\260\\337\\253jp8\\343\\325W\\362\\015\\216\\274\\003_o\\343\\375\\035\\370:qd\\230\\301\\3470}\\015\\347^\\216\\206=\\347\\207\\264Ts\\350\\276\\006s\\343]\\335\\313\\357\\325\\327PWD,\\253;)/{\\207\\036\\213\\246"a\\255(v\\217\\334R\\250\\274\\256\\374w\\367\\227\\374\\302g^\\033\\215\\377i\\357mO\\336\\361\\311[w\\355\\274\\351KG\\273\\317\\346\\037\\303>\\262\\264\\254N\\322\\023\\313\\352\\012\\345,\\201\\335\\027\\004f\\302\\306\\236 \\020yb lt\\000YZu\\370\\006\\326\\247JEM\\350F\\266''k\\226\\300\\026FY\\365{\\313\\352\\310\\341\\374\\233\\321}"\\177\\037\\344;(<\\324\\271\\356C\\0133E\\362\\211\\322\\316\\322\\246\\254\\231\\3600[\\251I{\\325\\204RF^=\\341!\\267NTv(\\2535\\370)\\302\\303\\313\\271\\337\\033\\014\\254\\314\\014T\\246\\331`\\2306\\252L\\3530\\030\\270\\212\\262\\262\\034\\356\\002\\265,{k\\034\\233\\365\\036\\334\\015VQR\\226\\251L\\007\\203\\201p\\027S\\213\\265\\324\\206?U\\224\\345Q\\217\\373\\353g\\0345,\\227\\264\\322\\344v\\333\\264k\\206\\246o\\337\\245\\353Z\\315\\260\\364m\\326(m\\320TI\\274R\\242\\246z\\207v\\242U\\241L\\212\\016s7\\255\\361 R\\254i\\265\\025\\013\\246m\\241\\032\\355\\314stM/\\331\\216\\263\\2407\\324\\246\\352\\251\\201]x:\\377!9\\230\\277\\213\\356\\036\\324H\\007\\262\\267\\334\\266\\020\\323\\254\\334&\\203H\\243\\232\\301\\025\\026\\245\\214r\\003z?\\356o)\\274Tnc\\034\\227\\231\\026S\\370\\300r[\\024\\366\\312mj\\012S\\207i\\250\\263\\036\\224\\333^\\346NO\\206\\355\\302\\034)\\245\\024e\\330\\360\\005\\224a\\033B\\341i\\021\\314\\033\\222\\362D\\351\\253\\343Q\\206\\355*)\\336\\233`+S\\217\\016d\\330B\\243*-U\\227\\313\\260\\205T\\311\\232\\374{\\352M\\036\\004\\244\\014\\323\\017+\\2210\\027\\025\\031\\340\\\\\\254\\232X\\352VE\\265\\201\\263W\\347Z\\015u\\254\\226\\225i\\244\\303P\\255\\302\\354u\\007\\245n\\307\\342V\\005K\\335&c\\274\\331\\001C\\226)\\314\\252\\032\\204D7\\250r# \\211\\217\\007\\241\\325q\\010C\\201\\333\\033yA\\246\\313UY\\0201\\324\\345R\\036\\236\\254\\313\\015\\341\\220\\256*4\\215\\231Jq\\013\\023\\337G].U\\324\\030\\007G\\202\\301@\\355\\353ro\\306\\347aA?q\\013J\\\\\\310\\\\,g\\002\\351\\377\\341\\356Jc,\\251\\256sW\\325]\\353\\326\\276\\276\\255^\\275\\265_O\\357{\\317\\276\\0033\\303\\000\\003\\303\\216\\201\\230\\210 \\307\\016K\\344\\230\\030l\\022\\233$ L\\022a\\2338r\\034\\373G\\022\\210E\\242\\030"\\023K\\226\\377Xq\\3428\\301\\010\\020\\016$\\226\\345D\\302\\216\\034\\307Yd\\241HL\\277\\311\\271U\\357u\\277\\036\\260eG\\252\\227\\010\\251\\273\\325]]uO\\325\\275U\\365\\316w\\316\\371\\276#\\037AId\\346\\370-\\336\\207\\314\\311\\012\\275\\263\\367!\\2264\\343\\\\\\243\\272\\331\\221\\347+s\\376\\332\\240\\320\\373\\334\\255G\\377\\340\\212\\375\\247J''/\\273\\354\\242\\207n|\\350\\003\\345\\3176\\352\\227(/\\035\\217J\\312k\\341\\232\\337\\177\\314W\\356V:O\\\\\\363\\316\\243\\376''~\\343*\\345\\206\\217\\275\\332\\177\\241\\377\\037O\\177\\270\\362OE"6\\345)\\252\\323\\256-\\353\\356\\030\\022~F\\2574\\361\\220^\\211\\204\\244W\\352Yb\\257\\315\\034) \\343\\260\\266k\\014t^sz%r"\\2311\\330I\\257\\224U\\204\\213\\213\\233\\237/\\364\\334\\357(r\\364\\357dRL\\203\\360\\204\\033\\310T\\312vv\\354\\202\\022E49\\211\\362\\022\\305<;\\026\\303!\\231\\320\\253\\214 \\320\\214\\325\\252\\347\\204\\\\\\253E9\\231\\235U]Y\\274i\\347\\361\\011!\\303\\031:\\334>y\\242&\\347\\314\\016\\246=c\\265\\312\\272\\323A\\242f8\\355|8\\3552\\367&\\247=\\213O8r\\332\\027\\026\\372\\017X\\232\\317,v\\361T$\\342\\272\\305\\275\\325=6w[\\201\\021-6\\333\\270m\\227I\\271Y.k%\\243\\216\\223\\322q\\346\\260\\004\\353\\370\\314e\\360#\\3416?:\\347\\303\\031Y\\314\\3348$\\003UeQY\\257$Z*l\\305M\\243X\\215i\\027w\\235\\2230|\\200,t\\313M\\312\\336"W\\241_+r\\364eBqU\\214\\352\\020\\353\\362\\001\\310r\\232\\262\\220\\177\\220\\244\\324\\255\\012\\021\\250^G\\202T\\362$\\245Fl\\177\\240C\\354ZR\\333\\030+\\304\\263\\251\\302\\020l1j\\230\\222v{\\363o\\010#\\2110\\262\\204\\247\\254\\271\\206O\\016\\231\\352\\203U\\2235\\327zVs\\315`\\235\\235\\0325P\\2535Zs\\235\\025\\2763),\\354\\310\\232k\\030]\\212y\\311\\232k\\313\\250\\303\\260\\275\\336KE\\316\\313\\201A\\266\\337\\026RU*\\326\\221\\220c\\372e\\270\\351d\\212x+\\333\\037v\\341\\36573C\\034\\326\\365\\262\\227\\202,-\\206K\\020.r\\343\\300\\324lYZ\\\\\\016\\341I\\241\\260\\305\\351e\\331~\\345\\025LpYf\\203\\007\\345\\271\\360\\032\\347Yy\\256\\\\\\207Ay.\\205\\2711cx\\177\\327j0\\273\\261`\\203\\362\\\\\\027\\036&"S\\303\\246L7c\\025\\271\\026\\315\\312s\\205^\\206a\\033\\215j^\\025\\275\\375>\\023\\262\\272\\333\\037!\\243k\\303\\007k(\\210\\265\\343\\301\\222\\357\\263\\020N=\\327\\255\\226\\017\\026\\035\\276\\317\\026\\026\\316\\335k\\274\\003\\353\\323|_\\214\\033''Zy\\375lr\\376QT''\\257\\276u\\375,\\266\\361\\375\\370a\\3748~vbb\\234xj\\234\\266\\306\\203\\265\\307\\177]\\377\\233\\344\\346O\\037_\\032{\\014aL\\261\\254\\361\\333\\032\\347}8\\2366T\\203\\304\\367\\205;\\3778[o\\032\\371''\\262\\265\\265\\263?\\3069\\034\\317z\\015\\236\\257\\221dtw$&\\243P\\177\\262G\\301\\253\\311\\262\\003\\223~W\\215\\007\\277e\\233I\\250-\\024\\217\\2406\\177\\251x\\033\\352\\375\\305\\333\\350\\177\\207\\327\\252J\\317\\246^\\242ccy\\036\\334\\206\\252\\303\\235\\336\\214G\\035\\337\\302\\306\\334\\222`"\\264\\2705\\247V*\\032\\300''\\024\\004jZG\\263\\006\\263"@R\\033K\\022\\200\\271\\324\\237\\236\\262t\\333\\005\\250\\274\\272[`\\341{\\334\\231&I]\\261l\\2549\\031u\\031\\260\\225\\223S\\2275l\\003,\\013"\\270\\204-\\3522|\\362\\323\\222\\004\\343\\340\\354\\000*\\007\\310<\\302\\\\V\\0012\\203\\367\\036\\260\\001s\\271F3\\310\\314"\\325\\262\\0011\\253\\3751\\320\\212\\365\\342M\\360\\342M\\250c\\270m\\373w\\213f\\272\\024\\210\\240c2\\343\\340n\\3601[\\360\\307\\302j\\304\\203\\012\\230Y\\337\\017wS\\331\\343\\326\\272\\232\\246\\250TRX\\024\\251\\2356Zw\\204S\\323\\211yd?\\234P%\\342\\321\\352\\222cz!g\\374\\3001\\0006Q(\\202\\025\\332\\3528\\340\\223?\\005N}\\0137\\302\\335\\3024\\246\\031\\247\\227\\034\\303\\224\\356\\022\\246\\330\\323\\255\\323\\272\\027\\242`r.`\\201_\\342\\245\\351R\\212S#\\322\\242\\252\\017?\\010 )\\347\\0203y\\003Qz\\372\\024e4\\025\\206q\\250S\\346eK\\336\\354k\\001\\361\\275\\224\\247\\323I\\003I\\362\\240\\327\\251<7\\206\\011\\273%\\013&\\346\\224\\365\\255\\310\\256\\236E2L\\236E2\\006\\335\\266\\224L\\306<\\013&\\0062\\222\\201G#\\273\\031e} \\230\\326a\\333\\301D\\016\\317\\214b\\355\\320\\204>;{\\327\\372\\256%wyum\\376\\035\\007\\324\\333/''\\326\\332KU,\\332\\327|\\337Y\\233&\\312oj\\226\\233\\375jd\\333U\\365\\333E\\002\\227\\315\\217\\0259\\372T\\221\\203\\253\\233\\302\\026\\353aH#\\307\\302N\\255\\011\\020&\\010IX\\353\\226H\\354y\\304\\355\\244.\\261\\255\\220\\372\\311\\252(\\261\\203\\007YI\\254U<\\352\\313\\250D\\332\\265\\261e\\227h\\271\\231\\00484\\344\\275\\2248\\310\\326\\313\\264\\024m\\300\\260''N<w\\001\\377We\\206>\\312\\377u8b\\031\\377\\267\\206M\\311\\3775q\\002XT"]*)\\333C\\376/ 4\\242b_\\226\\343\\222m\\376\\257r\\274\\310\\211\\351\\177\\255\\320\\321_(r\\364r\\221\\203\\0377=\\363X\\245J\\313p{\\370\\315)\\207\\371\\2452+5\\347\\022\\232\\004\\001\\211f&#\\352{\\025\\026w\\016\\331\\011?}\\232''\\366\\241F\\300c\\307\\306vw\\336\\241\\236\\227\\322d\\252]\\242%\\307\\320\\314\\331n\\200=\\221\\320\\264v\\021\\014{\\303\\015\\3100\\221k\\230\\342\\230HQ\\203\\005Zl\\206>\\211\\255:N\\335\\375\\360\\012N\\034\\335\\331\\277\\307\\345V\\325\\327\\275\\265\\003\\302\\326+\\224\\323S\\2271Fk\\206m\\0341+\\270J\\\\\\344Y\\225@\\013X\\027w\\343\\025\\217\\003\\034''\\374\\310>\\233\\232~$\\242\\271#\\360\\226u\\210I\\256\\276\\256\\177_\\221\\363\\364\\361\\255\\220\\300\\240\\225\\225.+\\244\\275\\222\\215\\336\\242\\225\\225\\014qf\\255\\254\\362\\220@\\336\\312j\\030k\\033\\264\\262\\332\\016\\011,.\\276AF)\\265\\326\\350\\037\\332\\3657)\\257\\\\\\321}h\\341\\212s\\277\\372\\221\\351''g\\354\\273\\224\\007\\224\\315\\037\\254]4\\363\\344\\214u\\247\\366q\\370\\327\\374\\231\\373\\336=\\301\\317\\177\\343\\374\\355Z\\211\\374\\372\\2049\\361\\376\\211\\327\\224+\\225/\\251\\273\\324\\3075E\\263d\\025\\263z\\313\\240\\212Y\\335rM\\307\\011\\253\\336\\256T\\334q\\332z\\273\\316\\3418\\303\\026\\243\\020n\\331Z\\225\\036\\306\\313\\315\\216t+n,\\336\\255Z.\\336D\\267x\\023i\\361&&\\2137\\361\\373zRSv\\271\\334K\\015b\\254.p$j\\2002w\\315\\271\\334\\015Ml\\314\\257\\300\\221\\221\\305\\315\\371\\034e*\\304\\367\\265z\\035\\315\\031\\\\\\236\\224\\276{\\005\\234\\343\\310\\347\\376\\3144\\240L\\217R\\266\\272GP\\303\\367tw\\026\\327\\022\\200\\201DSN\\310| |\\014\\031\\262gmV=\\355g\\262\\303\\306h\\317Z]\\366\\254\\365\\274,\\251\\011^t\\030\\016\\222\\232\\032\\355\\266d\\347\\026\\223\\230\\003\\331\\341\\254g\\255\\024L\\312\\370\\273Y}\\204\\2524w\\250\\350<\\236\\270\\327\\236{\\377\\2433\\237M\\265\\273\\264O\\235\\351\\236+\\025\\371\\221\\370\\375"\\007\\327\\376\\244\\310\\321\\373\\347\\213\\034\\275P%\\033mQ8bO\\014^\\275t\\361\\223\\226E\\234(`Q\\002.~\\311\\363\\250\\007.>vl\\370w}\\303(\\323#Gh\\331\\330\\250\\272\\014\\\\|b6\\246\\300\\274]!\\225v=\\300\\0218\\357b2\\315\\\\|R.\\357\\203a/\\275\\264\\177\\222\\351,O\\345\\0168\\232\\302\\316S\\271X\\246rs\\245\\\\\\335\\3069Gsf\\006;\\254\\343\\032\\322\\317\\311\\2626\\340\\347\\014\\2626\\226L\\345\\226C\\241\\346Y\\033\\311\\321\\\\Zz\\343\\271\\035\\322!\\356\\371O\\234\\377\\034\\272\\027?8!&\\342\\211\\351\\211\\337QT\\345N\\345\\037\\324\\213\\325g\\264\\272\\366\\210\\366:\\272\\015}\\035\\375#\\372\\027|lb\\302\\237\\227\\372\\373q]\\352\\357\\367F~\\247\\204f*\\373\\275X~mD\\251:N\\342\\3158?\\274\\306\\023\\353\\314m\\215s\\016\\343\\261\\304\\301\\307\\357\\330\\214s\\016\\3751\\332\\032\\317=\\277#\\247\\365\\266\\2737~\\224\\226\\210\\262.GY\\237\\314z\\213\\364h\\3765)\\337m\\021\\235W2\\031\\221\\036\\015m%\\226=\\032\\243\\225\\354gL\\327\\244Ai\\371\\220\\262\\274\\021\\322\\272\\022w6$I\\241I\\343u\\254\\301\\221\\352F\\357\\267M\\301XbY:e\\360A\\244\\251\\232 \\230"\\325\\352\\264\\316\\266\\255\\203\\033\\335\\360\\260_\\273\\244C\\322eS\\2507\\027\\357\\200m\\376E\\3616\\324\\253\\213\\267\\321?P\\274\\215g\\012\\247\\006\\276\\247\\370\\213P\\216\\024o#)\\336\\304\\346{s\\257~X\\252(\\211dRr#/U\\224\\252<\\0343\\033\\354\\244\\352\\233Ty\\244W?\\331"\\271W\\237\\324\\244WO4\\334\\351\\311*?\\223\\030\\365\\201*\\217\\362!"\\265Gv\\264uAy\\261\\260\\220\\353]\\243\\271\\274\\347\\226\\366H\\276\\336\\003yO\\334\\250\\301\\242\\017t`0\\323aK\\266\\336\\372p\\275\\301USN\\353\\265\\252\\332s\\230[\\207s\\037I\\262\\001\\374\\011L\\231d\\003\\220\\021\\002\\374\\231\\325*\\025\\211N\\260\\357\\253\\365D\\2333\\271)Ol}I\\346\\006<\\356eI6\\217b\\272\\262a\\300\\375\\351qw\\006\\340\\217\\002\\360G\\355\\333\\033\\017\\037[>\\030\\0348rx\\357=\\227\\242O\\355\\210\\367\\377B\\342\\234^\\270\\007\\033\\346\\341\\351\\271:\\233\\262\\314\\032\\362<}\\241Z\\337w\\2263\\356\\373!\\3056\\231\\243k\\214R_\\321\\\\!\\032\\224\\036\\356(\\215*\\256b\\206\\317\\022\\364\\305\\305\\327\\277\\244\\244\\007\\020_\\2667\\277\\360\\244\\177\\225\\362Q\\005\\273w|\\355W\\366\\230\\352\\3634\\261]\\301H\\303v\\364\\337R-L\\010RM\\212\\311\\273\\027Q\\307\\211\\035\\275k\\242\\305\\272ul-\\365\\304kE:\\352\\375W\\213\\034\\275\\320\\006+/\\0279x\\243H\\312&j\\027y\\352\\205F\\342\\373?,rt\\345\\243T\\347]K\\352z\\016\\313\\361$\\312\\363+.\\316\\313\\361")\\256kI`\\307s\\361\\035');
SELECT lowrite(0, '\\336\\365\\015l2\\300tQE\\006\\260\\335\\241\\370\\016L{%/\\307\\223\\342;\\\\\\247++\\233\\317\\017\\332<\\016\\365y\\321\\250\\236\\245l\\363\\3103\\031\\030XT\\222/*\\311\\333<\\222\\201\\014\\014\\033\\325\\263\\314d`\\266\\026uz\\272\\377]a\\031\\203\\254\\026\\262kM\\013\\333A@\\203j''&%\\307\\301n\\006y\\255\\200\\370\\311\\252\\021\\323C\\207hl\\254U]\\352\\233\\202\\230\\351\\244I,\\033\\260q\\263\\346\\223\\300\\024\\232\\350\\324\\267\\262Z\\206%N\\234\\350\\237\\265~\\216\\032\\013\\372\\2612\\356\\236\\231\\3345\\212P\\325/\\213\\362u\\263\\307Km\\323:\\300\\202\\335\\215\\362\\312\\201\\346\\334\\321\\371\\372\\311&?~(\\010\\004\\334\\301h\\231\\316\\020*\\2020\\024J\\273\\325v\\220Z\\322\\024\\256z,\\250+\\255\\2462\\365_\\317\\3779{\\361+\\352\\267\\357\\235L\\276Y]77\\303\\337m\\3517=\\373\\031U\\326\\375\\371\\347\\277p\\376r\\3644\\376\\341D}\\342\\221\\211\\327\\225\\333\\224\\257\\253{\\325''4O{@\\373\\036\\272\\026}\\031\\317\\343O\\022B~\\221|\\213\\374+\\371\\357\\211\\011%\\302\\332$\\370zQ\\354\\202\\217\\267\\276\\321\\233\\354i\\353~tH\\351\\021\\26571^\\007v\\214\\266\\336\\256\\327\\365\\366\\003m\\271\\255qf*\\306S`\\231\\333\\352^\\270\\363\\217\\263\\365\\246\\221\\177"[[;\\217\\363\\236\\037\\317}8\\3166-\\377\\007\\355L\\306h\\353\\377\\373u\\375\\364\\353\\225\\333\\372QA\\0215|\\250\\377\\214\\305\\257y\\021\\255\\030\\372{\\332\\177\\251{7\\252\\352/\\277|\\370\\356?\\262\\177\\366\\233\\217(\\037\\370\\303\\253X\\024*\\200\\300$8\\341\\223m\\360\\024C\\370#\\001$h\\002t\\342\\255.`2G\\247zK\\015\\002\\311\\203C\\226\\245\\306\\261\\326\\202M\\200\\251\\031 E\\304\\034\\000\\211i]P!\\233\\306LN\\313\\334\\014\\034\\336Da\\004\\270\\017\\251\\375\\375\\305c\\313\\240x\\023J\\\\\\274\\215o\\025ob\\363o\\213\\2671\\206,\\344\\012\\034#\\013Q\\365\\214\\274Y\\306\\243\\344M\\330\\222\\2217\\015@\\327\\203BTM\\327\\325\\267\\340n\\352Q\\336Bg\\213\\273I\\364\\222j\\311\\026:\\232\\252\\336Au\\326$\\215\\332^f\\361\\031\\242\\243KO \\206f\\230E\\0176\\232\\264e\\227P\\334\\234\\016ih\\327h\\271\\223\\326I\\335\\011\\265\\260]\\211\\264Ho\\323v|\\224X\\244\\2058\\272\\362r\\004\\200\\232\\231\\374X\\243\\312jR\\244\\260\\267\\030\\202\\237\\336\\244\\315^\\273AZ\\334!\\336\\\\\\3733\\305\\317\\330\\0238\\027\\260\\005\\244\\242\\222R\\204\\024"\\251\\346\\231\\200\\255.\\031\\2651\\321\\260\\016G\\206\\0272j\\021\\025H\\305Y\\237\\242L\\3006 \\2302$\\005la\\312F\\031\\265_E\\206\\220\\375[d\\251o\\350\\303\\017\\001\\177\\310"O\\312`\\316\\275@\\022p\\267\\373\\267h\\224JKy\\373\\026\\025\\305!\\326\\020\\207\\223q\\363\\366-\\252\\026\\306(o\\337\\342\\251p\\376DU\\036,~\\222\\224\\342M\\234\\314\\270\\377\\000\\313\\375\\254\\031\\020\\316\\351\\346\\225\\232\\221\\323\\315\\353\\315\\214\\353*\\271\\377Y\\024l@\\330U\\353,#*\\323N3\\317mos]en\\233\\231\\202\\032\\311\\220\\373O\\2605\\\\\\352\\001y\\332\\312\\227\\032s\\035\\326,*\\301Q\\331R\\253Y\\203)Xi\\371j\\217\\262\\225\\206G!\\347N\\343\\355\\225.\\217r\\247\\025\\012K\\335_\\332\\221=\\377\\374\\216\\277\\036}\\341\\337\\224WnX\\377\\263\\262u\\353\\271\\277\\273\\375\\347\\225\\017i]\\345\\214r\\237\\275\\371\\357\\037T\\177\\257\\353\\250{g\\225C\\377\\331\\177\\235\\0136\\343J\\001[\\035\\031\\221\\234\\020\\307\\306NT\\365\\260g\\232\\310\\252\\226\\000\\205\\033\\262\\364kJ\\367\\360\\342"\\366\\364\\251 k\\024\\202x\\234\\000p\\027\\000\\356\\313\\261\\2559\\022\\263\\327J\\206jP\\330\\342\\3152\\301766\\213-\\211\\374^\\221\\243+\\337(r\\364\\376\\317\\0249\\272\\366\\336"G/t\\332\\277H\\030\\251\\033ys\\031\\356xR\\031VFz\\262\\326\\265\\360\\210\\004\\031]TvNJ\\206t\\321\\304\\202\\201\\011\\315\\351\\242y\\211\\256#\\006}\\222$]4\\357\\223\\224\\321EW-\\354Y\\246u\\004\\265\\324\\246\\026j%bE\\270\\254\\247\\270i\\034v\\205\\327\\260t\\353\\310\\001\\213[5W\\270{\\017X\\226Y\\341\\214\\237\\274Lg\\274\\346X\\366aZE\\211\\032\\250>\\365"-$=\\265\\347\\356v\\014\\273D\\011\\277\\370\\210\\311D\\024\\030\\301\\312a\\333\\260]j\\261+\\257\\355\\177\\267\\310i\\352?\\225+\\013k\\272$\\227Z\\216le4J\\332\\335\\241,\\234\\223v\\313\\006C;\\225\\205\\235\\001iw[YXT\\011\\305\\355\\266v\\017&8\\346\\003%b\\001\\357/\\232\\365\\216\\261s\\2220\\263M\\232\\365\\216aF\\204u\\255R\\321t\\034\\351`\\025\\017{\\307\\020\\241\\352\\326\\240w\\014r`o\\202d\\037\\036\\331;\\246^W\\356-rf\\336\\010\\212\\034]\\3734\\343[\\345%#\\314\\332\\322P) \\220$\\\\kG\\277\\241a\\031m^^"\\337\\2452\\012\\311\\266\\313Kd\\277!\\030vqq\\363 \\254jF\\305f2\\311"WUH\\221q\\251\\027-W\\303\\035\\241b\\033Z\\232j\\006\\251\\230\\333\\253\\232S\\261\\035{\\213\\212=XU\\243\\006\\253\\332\\351\\364\\357\\037\\015\\015\\036\\274\\240\\011\\216q~\\363\\374c\\332\\315xc"\\232\\370\\360\\304\\017\\224\\033\\225\\277R\\227\\325Ok\\272\\366>\\355\\237\\321\\345\\350\\246\\211\\011?\\212}\\022o\\254\\327\\225\\225\\230\\316+\\2662\\331\\323&\\307\\025\\231\\031\\277\\376m7\\336\\000c\\331\\367p\\304\\270\\267eg8\\330p \\332\\203\\035\\263o\\012\\347\\232}o\\355\\274u\\370\\326\\316[\\207\\307\\331\\205\\205\\332\\330m\\255\\025k+_\\260\\320\\037\\307u\\015l\\215\\347>\\314\\357\\015:\\216\\353\\032\\254\\3278\\357\\303q\\316\\241\\022\\035T\\243\\2707\\257XJ\\357\\305\\333\\205\\252\\372\\177}\\333=\\323K\\376W\\256\\373\\310\\025\\317\\336\\250(d\\335R\\376T\\022\\271\\244\\344\\257t\\323\\263B\\0007\\3571\\201\\205\\005[j\\262\\267\\250T\\205\\332\\222\\374\\005P\\223\\251B\\201\\273\\017;\\347\\222\\277\\360\\376\\255d=&\\020\\332\\3561Q\\033H\\376~\\320Y\\230S\\217V\\355d\\311\\323\\275kN[\\304\\237\\253\\332\\225c''\\023+i\\207\\314;u\\3263\\375vl\\005\\247\\320\\314\\014JSU\\244)ZZ"\\247\\002+\\356\\331\\334\\271\\351\\254\\313\\374vj\\245\\227\\\\\\024\\271\\345\\304\\020\\34657\\373\\272\\237&nr\\202\\315-\\250QI\\240\\376\\243*6\\204\\022G\\250\\015\\020\\304\\006\\237~n\\027\\370\\375\\360\\001)\\232-\\200).Glj\\026\\000\\207/1\\215V*\\001\\246\\301RH\\013\\320\\215\\3044.\\234\\373TG\\366\\362\\000\\000\\324\\226A\\241\\210k\\264\\273K\\026QX\\200\\213\\224(fv\\361&\\270\\202\\030ulM\\312\\200\\001\\200\\241i\\202\\021\\322\\001\\217\\304\\262\\266\\303\\220\\315\\215S@(R\\006\\254,\\025\\202\\340\\023Uf\\367\\001\\237\\225\\011b\\006\\340\\311ZYVr\\300\\230\\345\\030\\360\\0038\\012\\270"\\333\\206\\270\\260%\\266\\035\\302U$t%\\360Q\\335\\220]~\\020\\353u\\010\\306\\206Ny\\222X\\304\\264a\\345\\340\\256\\226\\373\\023\\226\\312\\276(\\200P\\244\\306\\021\\334"\\015\\3313\\010N\\245\\235\\302\\307\\241\\024R\\252K\\0257\\237i\\244\\321\\001\\320\\025\\002RK\\374\\200\\252\\033\\324\\313%\\244\\003\\230\\213f"a_F\\014\\034\\364,\\311T\\306\\244\\344[M\\035\\364\\262\\035J\\034\\345\\304\\300A/\\333\\021\\311\\267\\201\\312\\330\\210\\304\\321\\303\\305\\257D\\377~@\\365\\272\\022\\006Z\\023\\376/C.\\273&a\\2460\\015\\012\\015\\012');
SELECT lo_close(0);

SELECT lo_open(lo_create(65975), 131072);
SELECT lowrite(0, '\\377\\330\\377\\340\\000\\020JFIF\\000\\001\\002\\001\\000H\\000H\\000\\000\\377\\341\\0143Exif\\000\\000MM\\000*\\000\\000\\000\\010\\000\\007\\001\\022\\000\\003\\000\\000\\000\\001\\000\\001\\000\\000\\001\\032\\000\\005\\000\\000\\000\\001\\000\\000\\000b\\001\\033\\000\\005\\000\\000\\000\\001\\000\\000\\000j\\001(\\000\\003\\000\\000\\000\\001\\000\\002\\000\\000\\0011\\000\\002\\000\\000\\000\\034\\000\\000\\000r\\0012\\000\\002\\000\\000\\000\\024\\000\\000\\000\\216\\207i\\000\\004\\000\\000\\000\\001\\000\\000\\000\\244\\000\\000\\000\\320\\000\\012\\374\\200\\000\\000''\\020\\000\\012\\374\\200\\000\\000''\\020Adobe Photoshop CS3 Windows\\0002009:09:23 23:06:54\\000\\000\\000\\000\\003\\240\\001\\000\\003\\000\\000\\000\\001\\377\\377\\000\\000\\240\\002\\000\\004\\000\\000\\000\\001\\000\\000\\000\\356\\240\\003\\000\\004\\000\\000\\000\\001\\000\\000\\001@\\000\\000\\000\\000\\000\\000\\000\\006\\001\\003\\000\\003\\000\\000\\000\\001\\000\\006\\000\\000\\001\\032\\000\\005\\000\\000\\000\\001\\000\\000\\001\\036\\001\\033\\000\\005\\000\\000\\000\\001\\000\\000\\001&\\001(\\000\\003\\000\\000\\000\\001\\000\\002\\000\\000\\002\\001\\000\\004\\000\\000\\000\\001\\000\\000\\001.\\002\\002\\000\\004\\000\\000\\000\\001\\000\\000\\012\\375\\000\\000\\000\\000\\000\\000\\000H\\000\\000\\000\\001\\000\\000\\000H\\000\\000\\000\\001\\377\\330\\377\\340\\000\\020JFIF\\000\\001\\002\\000\\000H\\000H\\000\\000\\377\\355\\000\\014Adobe_CM\\000\\002\\377\\356\\000\\016Adobe\\000d\\200\\000\\000\\000\\001\\377\\333\\000\\204\\000\\014\\010\\010\\010\\011\\010\\014\\011\\011\\014\\021\\013\\012\\013\\021\\025\\017\\014\\014\\017\\025\\030\\023\\023\\025\\023\\023\\030\\021\\014\\014\\014\\014\\014\\014\\021\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\001\\015\\013\\013\\015\\016\\015\\020\\016\\016\\020\\024\\016\\016\\016\\024\\024\\016\\016\\016\\016\\024\\021\\014\\014\\014\\014\\014\\021\\021\\014\\014\\014\\014\\014\\014\\021\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\377\\300\\000\\021\\010\\000\\240\\000w\\003\\001"\\000\\002\\021\\001\\003\\021\\001\\377\\335\\000\\004\\000\\010\\377\\304\\001?\\000\\000\\001\\005\\001\\001\\001\\001\\001\\001\\000\\000\\000\\000\\000\\000\\000\\003\\000\\001\\002\\004\\005\\006\\007\\010\\011\\012\\013\\001\\000\\001\\005\\001\\001\\001\\001\\001\\001\\000\\000\\000\\000\\000\\000\\000\\001\\000\\002\\003\\004\\005\\006\\007\\010\\011\\012\\013\\020\\000\\001\\004\\001\\003\\002\\004\\002\\005\\007\\006\\010\\005\\003\\0143\\001\\000\\002\\021\\003\\004!\\0221\\005AQa\\023"q\\2012\\006\\024\\221\\241\\261B#$\\025R\\301b34r\\202\\321C\\007%\\222S\\360\\341\\361cs5\\026\\242\\262\\203&D\\223TdE\\302\\243t6\\027\\322U\\342e\\362\\263\\204\\303\\323u\\343\\363F''\\224\\244\\205\\264\\225\\304\\324\\344\\364\\245\\265\\305\\325\\345\\365Vfv\\206\\226\\246\\266\\306\\326\\346\\3667GWgw\\207\\227\\247\\267\\307\\327\\347\\367\\021\\000\\002\\002\\001\\002\\004\\004\\003\\004\\005\\006\\007\\007\\006\\0055\\001\\000\\002\\021\\003!1\\022\\004AQaq"\\023\\0052\\201\\221\\024\\241\\261B#\\301R\\321\\3603$b\\341r\\202\\222CS\\025cs4\\361%\\006\\026\\242\\262\\203\\007&5\\302\\322D\\223T\\243\\027dEU6te\\342\\362\\263\\204\\303\\323u\\343\\363F\\224\\244\\205\\264\\225\\304\\324\\344\\364\\245\\265\\305\\325\\345\\365Vfv\\206\\226\\246\\266\\306\\326\\346\\366''7GWgw\\207\\227\\247\\267\\307\\377\\332\\000\\014\\003\\001\\000\\002\\021\\003\\021\\000?\\000\\365T\\222I%)$\\222IJI$\\222S\\013m\\256\\232\\335m\\207k\\033\\251+\\013\\250g3+\\323mMs+\\256`\\030\\000\\314G\\265\\277\\272\\255\\365\\346\\331\\262\\227\\007~\\2148\\2077\\304\\221\\354w\\366a\\313!d|O\\231\\230\\221\\3004\\201\\000\\313\\274\\231\\261DW\\027[uzN~=T}\\236\\327\\012\\313I,&\\003H''\\177\\371\\373\\212\\326\\\\\\231\\341u\\030\\256{\\361\\252s\\347{\\230\\322\\351\\346aM\\360\\336d\\344\\201\\305!\\25608e\\375N\\307\\373\\253r\\306\\215\\216\\251RI%\\242\\306\\244\\222I%)$\\222IO\\377\\320\\365T\\222I%)$\\222IJI$\\222R,\\252\\031\\221C\\352x\\231\\006<A\\374\\327\\005\\314\\271\\226V\\342\\313ZYc~\\223LH?%\\325\\256Z\\362\\343}\\245\\306\\\\^\\351?2\\262\\276/\\030\\3269W\\257X\\337\\365Y\\260\\365\\011\\272~+r\\262\\205o\\326\\266\\202\\347\\216$\\0156\\377\\000\\234V\\245\\375c\\022\\240Ed\\332\\340b\\033\\240\\377\\000<\\373V%v>\\267na\\203\\005\\277''\\015\\245C@<\\000T\\260\\363r\\303\\214\\307\\030\\002r''\\216g]?C\\205|\\241\\304u\\333\\263\\324c^\\334\\212\\031sAhx\\340\\363\\241\\204U[\\2476\\306\\340\\322,\\372[{\\366\\037\\230?\\314VV\\376)\\031c\\204\\245\\363\\030\\304\\313\\247\\250\\216\\315ynk\\272\\222I$\\364)$\\222IO\\377\\321\\365T\\222I%)$\\222IJI%\\033,mU\\272\\307\\230k\\001q>A\\002@\\026t\\001L\\226nOFm\\367\\272\\341if\\363.n\\320u\\343\\333\\364Q:f{\\362\\305\\242\\300\\032\\346\\020@\\034\\006\\273\\350\\211\\375\\357j\\274\\241\\341\\303\\315b\\211#\\212\\004\\361F\\356;zWz\\240OB\\363\\371}/"\\207\\376\\211\\256\\272\\243\\303\\204\\027\\017''5\\260\\254t\\256\\236\\342\\347Y\\225N\\202=0\\361\\254\\216N\\325\\260\\261\\372\\247Q\\313\\307\\315\\025RCX\\312\\333f\\242C\\213\\235c\\\\\\327\\177S\\323o\\321\\375\\365S''''\\203\\227\\221\\346\\017\\021\\214M\\373~\\231G\\325\\351\\375/\\321\\212\\3619Hp\\376.\\302J\\025X-\\251\\226\\016\\036\\320\\357\\274)\\255\\020A\\000\\216\\254JI$\\221R\\222I$\\224\\377\\000\\377\\322\\365T\\222I%)$\\222IJX\\335[\\2502\\326\\375\\232\\223-\\007\\364\\256\\355\\355\\341\\215\\376\\327\\322Z\\035B\\363\\217\\211e\\2150\\370\\332\\317\\213\\275\\241s`@\\205\\231\\361>fP\\003\\014\\177L\\\\\\317\\365?w\\374&\\\\P\\277Q\\351\\263\\177\\243Y\\267;ok\\030G1\\250\\207q\\371\\337\\234\\267\\2275\\323\\303?hc9\\337\\230\\362[\\361,\\262\\277\\375\\030\\272U/\\303%\\305\\313\\327\\356H\\307\\376\\357\\376\\355\\031\\276o\\242\\227=\\325\\311=B\\3113\\015`\\036B\\013\\243\\376\\222\\350W7\\324\\2109\\367\\021\\373\\300}\\315j\\037\\025\\225`\\003\\367\\246\\007\\375)+\\017\\315\\364o\\364+\\011\\256\\352\\211\\366\\260\\2074xn\\235\\337\\365+Usx\\031C\\027%\\266;J\\310\\333g\\300\\376w\\366\\\\\\2720A\\0228\\354\\217\\303r\\211\\340\\021\\277V3\\302\\177\\273\\363AYEJ\\373\\256\\222I+\\314jI$\\222S\\377\\323\\365T\\222I%)2u[\\250e}\\227\\031\\317\\007\\364\\207\\333X\\376Q\\377\\000\\310\\375$\\331\\3160\\214\\247#Q\\210\\262\\220,\\320sz\\316W\\251`\\306i\\226\\324e\\347\\371_\\232\\337\\3545g%\\3612{\\223\\311).k>if\\311,\\222\\375.\\237\\272?F-\\250\\212\\000\\004Y6\\331E\\017\\310\\253W\\321\\266\\346\\200&}76\\3276?\\224\\3069\\253\\261\\\\\\215\\215\\017\\255\\354:\\207\\265\\315#\\311\\303o\\361]][}&m\\372;D|!j| \\376\\257${H\\037\\361\\207\\376\\200\\303\\233pY\\256V\\307\\233-}\\207\\227\\271\\316\\373\\312\\3512\\356\\3641\\255\\264r\\306\\222>?\\232\\271\\201\\240\\205\\037\\305\\347\\256(\\177zG\\376\\3458F\\345u\\257\\3212l{l\\307y\\005\\265\\200k=\\340\\356\\334\\337\\352\\267\\363VB\\263\\323\\262[\\215\\224\\327\\277\\3508\\0268\\370L{\\277\\316\\012\\247%\\233\\332\\317\\022MF^\\231\\371K\\377\\000B_8\\334K\\321\\244\\222K\\242k)$\\222IO\\377\\324\\365T\\222I%)s\\375Z\\363vc\\232\\017\\262\\237`\\370\\377\\000\\204?\\367\\325\\255\\3242_\\215\\212\\353\\032\\006\\362v\\267\\270\\004\\376r\\347u\\344\\352N\\244\\225\\225\\361\\\\\\372G\\010\\353\\353\\237\\227\\350\\305\\233\\014\\177K\\350\\244\\222Id3)t\\270.\\016\\303\\244\\215F\\306\\376\\001sK\\243\\351\\316c\\260\\251\\331\\300`\\007\\3424w\\375%\\247\\360\\211~\\263${\\304\\037\\361O\\376\\206\\305\\233a\\346\\207\\255X[\\205\\264\\011\\365\\034\\032O\\207\\347\\317\\375\\005\\204\\266:\\355\\255\\025WW\\3479\\333\\243\\311\\243\\377\\0002X\\352/\\211\\312\\371\\232\\375\\330\\306?\\367\\177\\367i\\304=>eI\\210\\221\\036)\\322TY\\036\\213\\247e7''\\031\\244\\272m`\\015\\264w\\237\\037\\355\\253K\\013\\242\\335\\263)\\325\\300\\213\\233\\317yd\\271\\277\\365O[\\253\\245\\3452\\373\\270!>\\265R\\376\\374}2j\\314T\\210RI$\\247Z\\377\\000\\377\\325\\365T\\222P\\272\\326\\323S\\355w\\321`.?$\\011\\000\\022t\\003R\\247''\\255\\344n\\261\\230\\315\\341\\236\\367\\374O\\320\\037\\346\\254\\305+,}\\266:\\3335{\\314\\273\\375\\177\\222\\242\\271\\256c1\\313\\226Y\\017\\351\\037O\\367\\177E\\265\\021@\\005$\\222J\\025\\312Z\\375\\0066^G;\\200#\\266\\201d-^\\205k\\003\\255\\250\\237\\322\\030sG\\213F\\232\\177U\\312\\367\\302\\345\\\\\\305~\\364e\\037\\373\\257\\373\\226<\\277)C\\326\\340\\346\\267\\312\\260?\\027*\\012\\337U\\263~u\\232F\\300\\033\\367\\011\\377\\000\\277*\\212\\036r\\\\\\\\\\316S\\375j\\377\\000\\023\\320\\272\\037(\\362RI$\\253\\256Ps\\330C\\353;ln\\254w\\201\\037Eu5Z\\313\\252e\\2542\\327\\200\\340|\\212\\345\\226\\317D\\310\\335K\\261\\3175\\031o\\365]\\257\\375\\027\\255/\\205\\347\\341\\310q\\023\\351\\236\\261\\376\\374\\177\\357\\242\\305\\2266/\\263\\246\\222I-\\246\\007\\377\\326\\365U\\233\\326\\357\\015\\307m\\003\\351Zu\\376\\253}\\307\\376\\226\\325\\243\\300\\\\\\336nO\\332r_l\\313>\\215\\177\\325\\037\\371/\\244\\251|G?\\267\\204\\304|\\331}\\037\\340\\376\\233&(\\334\\257\\262\\004\\222I`6\\024\\222I$\\245!\\335\\325\\277c\\326s\\205\\003%\\355\\033+\\254\\330\\332\\245\\316#Ae\\276\\315\\316kQ\\025|\\3346fWUO0\\332\\357\\252\\362 \\031\\025;\\324\\331\\356\\372;\\277\\177\\351\\251\\271i\\360f\\307>\\322\\374\\375("\\301\\005\\265m\\317\\276\\307^\\361\\265\\326\\035\\305\\247\\226\\310\\372\\007\\372\\252)\\022I$\\362u)(\\345#)JGy\\023#\\376\\022\\224\\222I&\\245H\\230\\326[^Eo\\253\\351\\356\\000\\017\\0310Y\\375\\2444\\304H\\204\\350\\222\\010#\\241\\2757C\\326\\244\\252W\\224\\373:a\\311\\037\\316\\012\\334\\357\\3554\\037\\373\\363R]?\\273\\037o\\335\\375\\016\\037s\\374\\032\\342j\\321\\272\\353t\\377\\000\\377\\327\\364\\276\\243o\\243\\203i\\231%\\273\\033\\361w\\261s\\253\\243\\352\\030\\226e\\343\\372,\\267\\321;\\232\\342\\342\\320\\371\\0152Y\\022\\337\\244\\263\\037\\3212\\307\\321{\\035\\367\\217\\340\\345\\225\\361._>L\\221\\224 e\\001\\032\\364\\376\\365\\372\\231\\261J jh\\333\\236\\222\\264\\356\\227\\234\\000"\\275\\340\\367i\\037\\221\\373\\034\\241f\\006mU\\233\\037C\\313Z$\\206\\015\\356\\376\\315u\\356{\\226i\\345\\363\\215=\\251\\377\\000\\211&N(\\367\\037j\\004\\224\\275\\034\\211\\217\\263\\336#_\\346\\237\\377\\000\\221N\\334|\\227qE\\277:\\336?\\352\\232\\201\\303\\224o\\216c\\374\\031&\\307p\\301\\016\\374\\212q\\332\\307\\\\\\340\\306\\331c)i=\\337a\\330\\300\\216q\\362G\\370\\013\\177\\355\\267\\377\\000\\344V\\246\\017Hg\\246\\367e\\264\\271\\327V\\352\\235T\\373Eo\\333\\275\\256\\333\\371\\356\\331\\364\\224\\274\\267)\\223.A\\023\\023\\030\\357)\\021Z"S\\021\\026\\344$\\256d\\364\\214\\214a\\372"\\354\\212\\306\\200\\363`\\037\\360\\237\\351\\177\\343\\025#\\241\\203\\241\\360:\\025\\026l3\\305#\\031\\212\\256\\277\\243/\\030\\244\\020E\\205\\322Df>M\\237B\\227\\270x\\3551\\370\\2517\\0131\\306\\005\\017\\2371\\003\\357v\\324\\006,\\207hH\\336\\325\\022\\253\\035\\302\\024\\225\\201\\323s\\311\\217E\\3372\\330\\377\\000\\252DoG\\317.\\332Z\\326\\217\\336.\\323\\376\\217\\271<r\\331\\316\\330\\247\\376,\\221\\305\\036\\343\\355M\\323\\257\\007\\003+\\034\\223\\271\\254{\\332\\017\\356\\221\\371\\277\\333IX\\305\\351N\\242\\253\\267<:\\333Xk\\020!\\240\\024\\226\\267\\267\\314\\375\\307\\333\\341\\375ep\\360\\337\\253\\332\\275\\277\\304b\\270\\373\\227\\323\\366\\277\\377\\320\\365T\\222I%)$\\222IJI$\\222R\\222I$\\224\\244\\222I%)$\\222IJI$\\222R\\222I$\\224\\377\\000\\377\\331\\377\\355\\021\\232Photoshop 3.0\\0008BIM\\004\\004\\000\\000\\000\\000\\000\\007\\034\\002\\000\\000\\002\\000\\000\\0008BIM\\004%\\000\\000\\000\\000\\000\\020\\350\\361\\\\\\363/\\301\\030\\241\\242{g\\255\\305d\\325\\2728BIM\\004/\\000\\000\\000\\000\\000J\\353\\003\\001\\000H\\000\\000\\000H\\000\\000\\000\\000\\000\\000\\000\\000\\000\\000\\000\\320\\002\\000\\000@\\002\\000\\000\\000\\000\\000\\000\\000\\000\\000\\000\\030\\003\\000\\000d\\002\\000\\000\\000\\001\\300\\003\\000\\000\\260\\004\\000\\000\\001\\000\\017''\\001\\000llun\\000\\000\\000\\000\\000\\000\\000\\000\\000\\0008BIM\\003\\355\\000\\000\\000\\000\\000\\020\\000H\\000\\000\\000\\001\\000\\002\\000H\\000\\000\\000\\001\\000\\0028BIM\\004&\\000\\000\\000\\000\\000\\016\\000\\000\\000\\000\\000\\000\\000\\000\\000\\000?\\200\\000\\0008BIM\\004\\015\\000\\000\\000\\000\\000\\004\\000\\000\\000\\0368BIM\\004\\031\\000\\000\\000\\000\\000\\004\\000\\000\\000\\0368BIM\\003\\363\\000\\000\\000\\000\\000\\011\\000\\000\\000\\000\\000\\000\\000\\000\\001\\0008BIM\\004\\012\\000\\000\\000\\000\\000\\001\\000\\0008BIM''\\020\\000\\000\\000\\000\\000\\012\\000\\001\\000\\000\\000\\000\\000\\000\\000\\0028BIM\\003\\365\\000\\000\\000\\000\\000H\\000/ff\\000\\001\\000lff\\000\\006\\000\\000\\000\\000\\000\\001\\000/ff\\000\\001\\000\\241\\231\\232\\000\\006\\000\\000\\000\\000\\000\\001\\0002\\000\\000\\000\\001\\000Z\\000\\000\\000\\006\\000\\000\\000\\000\\000\\001\\0005\\000\\000\\000\\001\\000-\\000\\000\\000\\006\\000\\000\\000\\000\\000\\0018BIM\\003\\370\\000\\000\\000\\000\\000p\\000\\000\\377\\377\\377\\377\\377\\377\\377\\377\\377\\377\\377\\377\\377\\377\\377\\377\\377\\377\\377\\377\\377\\377\\003\\350\\000\\000\\000\\000\\377\\377\\377\\377\\377\\377\\377\\377\\377\\377\\377\\377\\377\\377\\377\\377\\377\\377\\377\\377\\377\\377\\003\\350\\000\\000\\000\\000\\377\\377\\377\\377\\377\\377\\377\\377\\377\\377\\377\\377\\377\\377\\377\\377\\377\\377\\377\\377\\377\\377\\003\\350\\000\\000\\000\\000\\377\\377\\377\\377\\377\\377\\377\\377\\377\\377\\377\\377\\377\\377\\377\\377\\377\\377\\377\\377\\377\\377\\003\\350\\000\\0008BIM\\004\\000\\000\\000\\000\\000\\000\\002\\000\\0008BIM\\004\\002\\000\\000\\000\\000\\000\\002\\000\\0008BIM\\0040\\000\\000\\000\\000\\000\\001\\001\\0008BIM\\004-\\000\\000\\000\\000\\000\\006\\000\\001\\000\\000\\000\\0028BIM\\004\\010\\000\\000\\000\\000\\000\\020\\000\\000\\000\\001\\000\\000\\002@\\000\\000\\002@\\000\\000\\000\\0008BIM\\004\\036\\000\\000\\000\\000\\000\\004\\000\\000\\000\\0008BIM\\004\\032\\000\\000\\000\\000\\003]\\000\\000\\000\\006\\000\\000\\000\\000\\000\\000\\000\\000\\000\\000\\001@\\000\\000\\000\\356\\000\\000\\000\\024\\000n\\000o\\000s\\000s\\000a\\000 \\000s\\000e\\000n\\000h\\000o\\000r\\000a\\000 \\000n\\000a\\000z\\000a\\000r\\000e\\000\\000\\000\\001\\000\\000\\000\\000\\000\\000\\000\\000\\000\\000\\000\\000\\000\\000\\000\\000\\000\\000\\000\\001\\000\\000\\000\\000\\000\\000\\000\\000\\000\\000\\000\\356\\000\\000\\001@\\000\\000\\000\\000\\000\\000\\000\\000\\000\\000\\000\\000\\000\\000\\000\\000\\001\\000\\000\\000\\000\\000\\000\\000\\000\\000\\000\\000\\000\\000\\000\\000\\000\\000\\000\\000\\020\\000\\000\\000\\001\\000\\000\\000\\000\\000\\000null\\000\\000\\000\\002\\000\\000\\000\\006boundsObjc\\000\\000\\000\\001\\000\\000\\000\\000\\000\\000Rct1\\000\\000\\000\\004\\000\\000\\000\\000Top long\\000\\000\\000\\000\\000\\000\\000\\000Leftlong\\000\\000\\000\\000\\000\\000\\000\\000Btomlong\\000\\000\\001@\\000\\000\\000\\000Rghtlong\\000\\000\\000\\356\\000\\000\\000\\006slicesVlLs\\000\\000\\000\\001Objc\\000\\000\\000\\001\\000\\000\\000\\000\\000\\005slice\\000\\000\\000\\022\\000\\000\\000\\007sliceIDlong\\000\\000\\000\\000\\000\\000\\000\\007groupIDlong\\000\\000\\000\\000\\000\\000\\000\\006originenum\\000\\000\\000\\014ESliceOrigin\\000\\000\\000\\015autoGenerated\\000\\000\\000\\000Typeenum\\000\\000\\000\\012ESliceType\\000\\000\\000\\000Img \\000\\000\\000\\006boundsObjc\\000\\000\\000\\001\\000\\000\\000\\000\\000\\000Rct1\\000\\000\\000\\004\\000\\000\\000\\000Top long\\000\\000\\000\\000\\000\\000\\000\\000Leftlong\\000\\000\\000\\000\\000\\000\\000\\000Btomlong\\000\\000\\001@\\000\\000\\000\\000Rghtlong\\000\\000\\000\\356\\000\\000\\000\\003urlTEXT\\000\\000\\000\\001\\000\\000\\000\\000\\000\\000nullTEXT\\000\\000\\000\\001\\000\\000\\000\\000\\000\\000MsgeTEXT\\000\\000\\000\\001\\000\\000\\000\\000\\000\\006altTagTEXT\\000\\000\\000\\001\\000\\000\\000\\000\\000\\016cellTextIsHTMLbool\\001\\000\\000\\000\\010cellTextTEXT\\000\\000\\000\\001\\000\\000\\000\\000\\000\\011horzAlignenum\\000\\000\\000\\017ESliceHorzAlign\\000\\000\\000\\007default\\000\\000\\000\\011vertAlignenum\\000\\000\\000\\017ESliceVertAlign\\000\\000\\000\\007default\\000\\000\\000\\013bgColorTypeenum\\000\\000\\000\\021ESliceBGColorType\\000\\000\\000\\000None\\000\\000\\000\\011topOutsetlong\\000\\000\\000\\000\\000\\000\\000\\012leftOutsetlong\\000\\000\\000\\000\\000\\000\\000\\014bottomOutsetlong\\000\\000\\000\\000\\000\\000\\000\\013rightOutsetlong\\000\\000\\000\\000\\0008BIM\\004(\\000\\000\\000\\000\\000\\014\\000\\000\\000\\001?\\360\\000\\000\\000\\000\\000\\0008BIM\\004\\021\\000\\000\\000\\000\\000\\001\\001\\0008BIM\\004\\024\\000\\000\\000\\000\\000\\004\\000\\000\\000\\0028BIM\\004\\014\\000\\000\\000\\000\\013\\031\\000\\000\\000\\001\\000\\000\\000w\\000\\000\\000\\240\\000\\000\\001h\\000\\000\\341\\000\\000\\000\\012\\375\\000\\030\\000\\001\\377\\330\\377\\340\\000\\020JFIF\\000\\001\\002\\000\\000H\\000H\\000\\000\\377\\355\\000\\014Adobe_CM\\000\\002\\377\\356\\000\\016Adobe\\000d\\200\\000\\000\\000\\001\\377\\333\\000\\204\\000\\014\\010\\010\\010\\011\\010\\014\\011\\011\\014\\021\\013\\012\\013\\021\\025\\017\\014\\014\\017\\025\\030\\023\\023\\025\\023\\023\\030\\021\\014\\014\\014\\014\\014\\014\\021\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\001\\015\\013\\013\\015\\016\\015\\020\\016\\016\\020\\024\\016\\016\\016\\024\\024\\016\\016\\016\\016\\024\\021\\014\\014\\014\\014\\014\\021\\021\\014\\014\\014\\014\\014\\014\\021\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\377\\300\\000\\021\\010\\000\\240\\000w\\003\\001"\\000\\002\\021\\001\\003\\021\\001\\377\\335\\000\\004\\000\\010\\377\\304\\001?\\000\\000\\001\\005\\001\\001\\001\\001\\001\\001\\000\\000\\000\\000\\000\\000\\000\\003\\000\\001\\002\\004\\005\\006\\007\\010\\011\\012\\013\\001\\000\\001\\005\\001\\001\\001\\001\\001\\001\\000\\000\\000\\000\\000\\000\\000\\001\\000\\002\\003\\004\\005\\006\\007\\010\\011\\012\\013\\020\\000\\001\\004\\001\\003\\002\\004\\002\\005\\007\\006\\010\\005\\003\\0143\\001\\000\\002\\021\\003\\004!\\0221\\005AQa\\023"q\\2012\\006\\024\\221\\241\\261B#$\\025R\\301b34r\\202\\321C\\007%\\222S\\360\\341\\361cs5\\026\\242\\262\\203&D\\223TdE\\302\\243t6\\027\\322U\\342e\\362\\263\\204\\303\\323u\\343\\363F''\\224\\244\\205\\264\\225\\304\\324\\344\\364\\245\\265\\305\\325\\345\\365Vfv\\206\\226\\246\\266\\306\\326\\346\\3667GWgw\\207\\227\\247\\267\\307\\327\\347\\367\\021\\000\\002\\002\\001\\002\\004\\004\\003\\004\\005\\006\\007\\007\\006\\0055\\001\\000\\002\\021\\003!1\\022\\004AQaq"\\023\\0052\\201\\221\\024\\241\\261B#\\301R\\321\\3603$b\\341r\\202\\222CS\\025cs4\\361%\\006\\026\\242\\262\\203\\007&5\\302\\322D\\223T\\243\\027dEU6te\\342\\362\\263\\204\\303\\323u\\343\\363F\\224\\244\\205\\264\\225\\304\\324\\344\\364\\245\\265\\305\\325\\345\\365Vfv\\206\\226\\246\\266\\306\\326\\346\\366''7GWgw\\207\\227\\247\\267\\307\\377\\332\\000\\014\\003\\001\\000\\002\\021\\003\\021\\000?\\000\\365T\\222I%)$\\222IJI$\\222S\\013m\\256\\232\\335m\\207k\\033\\251+\\013\\250g3+\\323mMs+\\256`\\030\\000\\314G\\265\\277\\272\\255\\365\\346\\331\\262\\227\\007~\\2148\\2077\\304\\221\\354w\\366a\\313!d|O\\231\\230\\221\\3004\\201\\000\\313\\274\\231\\261DW\\027[uzN~=T}\\236\\327\\012\\313I,&\\003H''\\177\\371\\373\\212\\326\\\\\\231\\341u\\030\\256{\\361\\252s\\347{\\230\\322\\351\\346aM\\360\\336d\\344\\201\\305!\\25608e\\375N\\307\\373\\253r\\306\\215\\216\\251RI%\\242\\306\\244\\222I%)$\\222IO\\377\\320\\365T\\222I%)$\\222IJI$\\222R,\\252\\031\\221C\\352x\\231\\006<A\\374\\327\\005\\314\\271\\226V\\342\\313ZYc~\\223LH?%\\325\\256Z\\362\\343}\\245\\306\\\\^\\351?2\\262\\276/\\030\\3269W\\257X\\337\\365Y\\260\\365\\011\\272~+r\\262\\205o\\326\\266\\202\\347\\216$\\0156\\377\\000\\234V\\245\\375c\\022\\240Ed\\332\\340b\\033\\240\\377\\000<\\373V%v>\\267na\\203\\005\\277''\\015\\245C@<\\000T\\260\\363r\\303\\214\\307\\030\\002r''\\216g]?C\\205|\\241\\304u\\333\\263\\324c^\\334\\212\\031sAhx\\340\\363\\241\\204U[\\2476\\306\\340\\322,\\372[{\\366\\037\\230?\\314VV\\376)\\031c\\204\\245\\363\\030\\304\\313\\247\\250\\216\\315ynk\\272\\222I$\\364)$\\222IO\\377\\321\\365T\\222I%)$\\222IJI%\\033,mU\\272\\307\\230k\\001q>A\\002@\\026t\\001L\\226nOFm\\367\\272\\341if\\363.n\\320u\\343\\333\\364Q:f{\\362\\305\\242\\300\\032\\346\\020@\\034\\006\\273\\350\\211\\375\\357j\\274\\241\\341\\303\\315b\\211#\\212\\004\\361F\\356;zWz\\240OB\\363\\371}/"\\207\\376\\211\\256\\272\\243\\303\\204\\027\\017''5\\260\\254t\\256\\236\\342\\347Y\\225N\\202=0\\361\\254\\216N\\325\\260\\261\\372\\247Q\\313\\307\\315\\025RCX\\312\\333f\\242C\\213\\235c\\\\\\327\\177S\\323o\\321\\375\\365S''''\\203\\227\\221\\346\\017\\021\\214M\\373~\\231G\\325\\351\\375/\\321\\212\\3619Hp\\376.\\302J\\025X-\\251\\226\\016\\036\\320\\357\\274)\\255\\020A\\000\\216\\254JI$\\221R\\222I$\\224\\377\\000\\377\\322\\365T\\222I%)$\\222IJX\\335[\\2502\\326\\375\\232\\223-\\007\\364\\256\\355\\355\\341\\215\\376\\327\\322Z\\035B\\363\\217\\211e\\2150\\370\\332\\317\\213\\275\\241s`@\\205\\231\\361>fP\\003\\014\\177L\\\\\\317\\365?w\\374&\\\\P\\277Q\\351\\263\\177\\243Y\\267;ok\\030G1\\250\\207q\\371\\337\\234\\267\\2275\\323\\303?hc9\\337\\230\\362[\\361,\\262\\277\\375\\030\\272U/\\303%\\305\\313\\327\\356H\\307\\376\\357\\376\\355\\031\\276o\\242\\227=\\325\\311=B\\3113\\015`\\036B\\013\\243\\376\\222\\350W7\\324\\2109\\367\\021\\373\\300}\\315j\\037\\025\\225`\\003\\367\\246\\007\\375)+\\017\\315\\364o\\364+\\011\\256\\352\\211\\366\\260\\2074xn\\235\\337\\365+Usx\\031C\\027%\\266;J\\310\\333g\\300\\376w\\366\\\\\\2720A\\0228\\354\\217\\303r\\211\\340\\021\\277V3\\302\\177\\273\\363AYEJ\\373\\256\\222I+\\314jI$\\222S\\377\\323\\365T\\222I%)2u[\\250e}\\227\\031\\317\\007\\364\\207\\333X\\376Q\\377\\000\\310\\375$\\331\\3160\\214\\247#Q\\210\\262\\220,\\320sz\\316W\\251`\\306i\\226\\324e\\347\\371_\\232\\337\\3545g%\\3612{\\223\\311).k>if\\311,\\222\\375.\\237\\272?F-\\250\\212\\000\\004Y6\\331E\\017\\310\\253W\\321\\266\\346\\200&}76\\3276?\\224\\3069\\253\\261\\\\\\215\\215\\017\\255\\354:\\207\\265\\315#\\311\\303o\\361]][}&m\\372;D|!j| \\376\\257${H\\037\\361\\207\\376\\200\\303\\233pY\\256V\\307\\233-}\\207\\227\\271\\316\\373\\312\\3512\\356\\3641\\255\\264r\\306\\222>?\\232\\271\\201\\240\\205\\037\\305\\347\\256(\\177zG\\376\\3458F\\345u\\257\\3212l{l\\307y\\005\\265\\200k=\\340\\356\\334\\337\\352\\267\\363VB\\263\\323\\262[\\215\\224\\327\\277\\3508\\0268\\370L{\\277\\316\\012\\247%\\233\\332\\317\\022MF^\\231\\371K\\377\\000B_8\\334K\\321\\244\\222K\\242k)$\\222IO\\377\\324\\365T\\222I%)s\\375Z\\363vc\\232\\017\\262\\237`\\370\\377\\000\\204?\\367\\325\\255\\3242_\\215\\212\\353\\032\\006\\362v\\267\\270\\004\\376r\\347u\\344\\352N\\244\\225\\225\\361\\\\\\372G\\010\\353\\353\\237\\227\\350\\305\\233\\014\\177K\\350\\244\\222Id3)t\\270.\\016\\303\\244\\215F\\306\\376\\001sK\\243\\351\\316c\\260\\251\\331\\300`\\007\\3424w\\375%\\247\\360\\211~\\263${\\304\\037\\361O\\376\\206\\305\\233a\\346\\207\\255X[\\205\\264\\011\\365\\034\\032O\\207\\347\\317\\375\\005\\204\\266:\\355\\255\\025WW\\3479\\333\\243\\311\\243\\377\\0002X\\352/\\211\\312\\371\\232\\375\\330\\306?\\367\\177\\367i\\304=>eI\\210\\221\\036)\\322TY\\036\\213\\247e7''\\031\\244\\272m`\\015\\264w\\237\\037\\355\\253K\\013\\242\\335\\263)\\325\\300\\213\\233\\317yd\\271\\277\\365O[\\253\\245\\3452\\373\\270!>\\265R\\376\\374}2j\\314T\\210RI$\\247Z\\377\\000\\377\\325\\365T\\222P\\272\\326\\323S\\355w\\321`.?$\\011\\000\\022t\\003R\\247''\\255\\344n\\261\\230\\315\\341\\236\\367\\374O\\320\\037\\346\\254\\305+,}\\266:\\3335{\\314\\273\\375\\177\\222\\242\\271\\256c1\\313\\226Y\\017\\351\\037O\\367\\177E\\265\\021@\\005$\\222J\\025\\312Z\\375\\0066^G;\\200#\\266\\201d-^\\205k\\003\\255\\250\\237\\322\\030sG\\213F\\232\\177U\\312\\367\\302\\345\\\\\\305~\\364e\\037\\373\\257\\373\\226<\\277)C\\326\\340\\346\\267\\312\\260?\\027*\\012\\337U\\263~u\\232F\\300\\033\\367\\011\\377\\000\\277*\\212\\036r\\\\\\\\\\316S\\375j\\377\\000\\023\\320\\272\\037(\\362RI$\\253\\256Ps\\330C\\353;ln\\254w\\201\\037Eu5Z\\313\\252e\\2542\\327\\200\\340|\\212\\345\\226\\317D\\310\\335K\\261\\3175\\031o\\365]\\257\\375\\027\\255/\\205\\347\\341\\310q\\023\\351\\236\\261\\376\\374\\177\\357\\242\\305\\2266/\\263\\246\\222I-\\246\\007\\377\\326\\365U\\233\\326\\357\\015\\307m\\003\\351Zu\\376\\253}\\307\\376\\226\\325\\243\\300\\\\\\336nO\\332r_l\\313>\\215\\177\\325\\037\\371/\\244\\251|G?\\267\\204\\304|\\331}\\037\\340\\376\\233&(\\334\\257\\262\\004\\222I`6\\024\\222I$\\245!\\335\\325\\277c\\326s\\205\\003%\\355\\033+\\254\\330\\332\\245\\316#Ae\\276\\315\\316kQ\\025|\\3346fWUO0\\332\\357\\252\\362 \\031\\025;\\324\\331\\356\\372;\\277\\177\\351\\251\\271i\\360f\\307>\\322\\374\\375("\\301\\005\\265m\\317\\276\\307^\\361\\265\\326\\035\\305\\247\\226\\310\\372\\007\\372\\252)\\022I$\\362u)(\\345#)JGy\\023#\\376\\022\\224\\222I&\\245H\\230\\326[^Eo\\253\\351\\356\\000\\017\\0310Y\\375\\2444\\304H\\204\\350\\222\\010#\\241\\2757C\\326\\244\\252W\\224\\373:a\\311\\037\\316\\012\\334\\357\\3554\\037\\373\\363R]?\\273\\037o\\335\\375\\016\\037s\\374\\032\\342j\\321\\272\\353t\\377\\000\\377\\327\\364\\276\\243o\\243\\203i\\231%\\273\\033\\361w\\261s\\253\\243\\352\\030\\226e\\343\\372,\\267\\321;\\232\\342\\342\\320\\371\\0152Y\\022\\337\\244\\263\\037\\3212\\307\\321{\\035\\367\\217\\340\\345\\225\\361._>L\\221\\224 e\\001\\032\\364\\376\\365\\372\\231\\261J jh\\333\\236\\222\\264\\356\\227\\234\\000"\\275\\340\\367i\\037\\221\\373\\034\\241f\\006mU\\233\\037C\\313Z$\\206\\015\\356\\376\\315u\\356{\\226i\\345\\363\\215=\\251\\377\\000\\211&N(\\367\\037j\\004\\224\\275\\034\\211\\217\\263\\336#_\\346\\237\\377\\000\\221N\\334|\\227qE\\277:\\336?\\352\\232\\201\\303\\224o\\216c\\374\\031&\\307p\\301\\016\\374\\212q\\332\\307\\\\\\340\\306\\331c)i=\\337a\\330\\300\\216q\\362G\\370\\013\\177\\355\\267\\377\\000\\344V\\246\\017Hg\\246\\367e\\264\\271\\327V\\352\\235T\\373Eo\\333\\275\\256\\333\\371\\356\\331\\364\\224\\274\\267)\\223.A\\023\\023\\030\\357)\\021Z"S\\021\\026\\344$\\256d\\364\\214\\214a\\372"\\354\\212\\306\\200\\363`\\037\\360\\237\\351\\177\\343\\025#\\241\\203\\241\\360:\\025\\026l3\\305#\\031\\212\\256\\277\\243/\\030\\244\\020E\\205\\322Df>M\\237B\\227\\270x\\3551\\370\\2517\\0131\\306\\005\\017\\2371\\003\\357v\\324\\006,\\207hH\\336\\325\\022\\253\\035\\302\\024\\225\\201\\323s\\311\\217E\\3372\\330\\377\\000\\252DoG\\317.\\332Z\\326\\217\\336.\\323\\376\\217\\271<r\\331\\316\\330\\247\\376,\\221\\305\\036\\343\\355M\\323\\257\\007\\003+\\034\\223\\271\\254{\\332\\017\\356\\221\\371\\277\\333IX\\305\\351N\\242\\253\\267<:\\333Xk\\020!\\240\\024\\226\\267\\267\\314\\375\\307\\333\\341\\375ep\\360\\337\\253\\332\\275\\277\\304b\\270\\373\\227\\323\\366\\277\\377\\320\\365T\\222I%)$\\222IJI$\\222R\\222I$\\224\\244\\222I%)$\\222IJI$\\222R\\222I$\\224\\377\\000\\377\\331\\0008BIM\\004!\\000\\000\\000\\000\\000U\\000\\000\\000\\001\\001\\000\\000\\000\\017\\000A\\000d\\000o\\000b\\000e\\000 \\000P\\000h\\000o\\000t\\000o\\000s\\000h\\000o\\000p\\000\\000\\000\\023\\000A\\000d\\000o\\000b\\000e\\000 \\000P\\000h\\000o\\000t\\000o\\000s\\000h\\000o\\000p\\000 \\000C\\000S\\0003\\000\\000\\000\\001\\0008BIM\\004\\006\\000\\000\\000\\000\\000\\007\\000\\001\\000\\000\\000\\001\\001\\000\\377\\341\\017\\020http://ns.adobe.com/xap/1.0/\\000<?xpacket begin="\\357\\273\\277" id="W5M0MpCehiHzreSzNTczkc9d"?> <x:xmpmeta xmlns:x="adobe:ns:meta/" x:xmptk="Adobe XMP Core 4.1-c036 46.276720, Mon Feb 19 2007 22:40:08        "> <rdf:RDF xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"> <rdf:Description rdf:about="" xmlns:xap="http://ns.adobe.com/xap/1.0/" xmlns:dc="http://purl.org/dc/elements/1.1/" xmlns:photoshop="http://ns.adobe.com/photoshop/1.0/" xmlns:xapMM="http://ns.adobe.com/xap/1.0/mm/" xmlns:tiff="http://ns.adobe.com/tiff/1.0/" xmlns:exif="http://ns.adobe.com/exif/1.0/" xap:CreateDate="2009-09-23T23:06:54-03:00" xap:ModifyDate="2009-09-23T23:06:54-03:00" xap:MetadataDate="2009-09-23T23:06:54-03:00" xap:CreatorTool="Adobe Photoshop CS3 Windows" dc:format="image/jpeg" photoshop:ColorMode="3" photoshop:History="" xapMM:InstanceID="uuid:3F097BA2ACA8DE118B36FA367EF84999" xapMM:DocumentID="uuid:3E097BA2ACA8DE118B36FA367EF84999" tiff:Orientation="1" tiff:XResolution="720000/10000" tiff:YResolution="720000/10000" tiff:ResolutionUnit="2" tiff:NativeDigest="256,257,258,259,262,274,277,284,530,531,282,283,296,301,318,319,529,532,306,270,271,272,305,315,33432;5E3B226A228B18DDEBC1EEFBC4137D1A" exif:PixelXDimension="238" exif:PixelYDimension="320" exif:ColorSpace="-1" exif:NativeDigest="36864,40960,40961,37121,37122,40962,40963,37510,40964,36867,36868,33434,33437,34850,34852,34855,34856,37377,37378,37379,37380,37381,37382,37383,37384,37385,37386,37396,41483,41484,41486,41487,41488,41492,41493,41495,41728,41729,41730,41985,41986,41987,41988,41989,41990,41991,41992,41993,41994,41995,41996,42016,0,2,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,20,22,23,24,25,26,27,28,30;602A7316202AD2AA65D89ADD22FB92B7"> <xapMM:DerivedFrom rdf:parseType="Resource"/> </rdf:Description> </rdf:RDF> </x:xmpmeta>                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 <?xpacket end="w"?>\\377\\356\\000\\016Adobe\\000d\\200\\000\\000\\000\\001\\377\\333\\000\\204\\000\\014\\010\\010\\010\\011\\010\\014\\011\\011\\014\\021\\013\\012\\013\\021\\025\\017\\014\\014\\017\\025\\030\\023\\023\\025\\023\\023\\030\\021\\014\\014\\014\\014\\014\\014\\021\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\001\\015\\013\\013\\015\\016\\015\\020\\016\\016\\020\\024\\016\\016\\016\\024\\024\\016\\016\\016\\016\\024\\021\\014\\014\\014\\014\\014\\021\\021\\014\\014\\014\\014\\014\\014\\021\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\014\\377\\300\\000\\021\\010\\001@\\000\\356\\003\\001"\\000\\002\\021\\001\\003\\021\\001\\377\\335\\000\\004\\000\\017\\377\\304\\001?\\000\\000\\001\\005\\001\\001\\001\\001\\001\\001\\000\\000\\000\\000\\000\\000\\000\\003\\000\\001\\002\\004\\005\\006\\007\\010\\011\\012\\013\\001\\000\\001\\005\\001\\001\\001\\001\\001\\001\\000\\000\\000\\000\\000\\000\\000\\001\\000\\002\\003\\004\\005\\006\\007\\010\\011\\012\\013\\020\\000\\001\\004\\001\\003\\002\\004\\002\\005\\007\\006\\010\\005\\003\\0143\\001\\000\\002\\021\\003\\004!\\0221\\005AQa\\023"q\\2012\\006\\024\\221\\241\\261B#$\\025R\\301b34r\\202\\321C\\007%\\222S\\360\\341\\361cs5\\026\\242\\262\\203&D\\223TdE\\302\\243t6\\027\\322U\\342e\\362\\263\\204\\303\\323u\\343\\363F''\\224\\244\\205\\264\\225\\304\\324\\344\\364\\245\\265\\305\\325\\345\\365Vfv\\206\\226\\246\\266\\306\\326\\346\\3667GWgw\\207\\227\\247\\267\\307\\327\\347\\367\\021\\000\\002\\002\\001\\002\\004\\004\\003\\004\\005\\006\\007\\007\\006\\0055\\001\\000\\002\\021\\003!1\\022\\004AQaq"\\023\\0052\\201\\221\\024\\241\\261B#\\301R\\321\\3603$b\\341r\\202\\222CS\\025cs4\\361%\\006\\026\\242\\262\\203\\007&5\\302\\322D\\223T\\243\\027dEU6te\\342\\362\\263\\204\\303\\323u\\343\\363F\\224\\244\\205\\264\\225\\304\\324\\344\\364\\245\\265\\305\\325\\345\\365Vfv\\206\\226\\246\\266\\306\\326\\346\\366''7GWgw\\207\\227\\247\\267\\307\\377\\332\\000\\014\\003\\001\\000\\002\\021\\003\\021\\000?\\000\\365T\\222I%)$\\222IJI$\\222R\\222I$\\224\\244\\222I%)$\\222IJI%S?9\\270\\214\\0207X\\357\\242\\0232d\\2168\\231\\314\\324B@$\\320lmeU\\270V\\320\\320\\001!\\255\\020''\\344\\271\\213^\\353,s\\336e\\3162J-\\271\\371v\\223\\272\\302\\001\\374\\321\\240@X\\234\\3679\\034\\346"\\000\\210\\306\\367\\375&|p\\341\\273tz\\021c/\\265\\255h\\006\\320\\034\\347\\001\\251\\333\\343\\367\\255\\265\\312\\325k\\352x\\262\\263\\265\\315\\340\\255\\234~\\261C\\353>\\240"\\3204`\\345\\304~m\\177\\313\\177\\3461[\\370o5\\023\\021\\206G\\326\\017\\242\\377\\000H~\\352\\314\\261\\253\\227N\\256\\212I\\206\\242xN\\264\\330\\224\\222I$\\245$\\222I)I$\\222JRI$\\222\\224\\222I$\\247\\377\\320\\365T\\222I%)$\\222IJI$\\222R\\222I$\\224\\244\\222I%)$\\222IJ\\\\\\265\\271\\026d\\275\\326\\331"K\\266\\264\\366\\022a\\253\\251\\\\\\326uB\\254\\233\\000\\020\\013\\211\\0372\\250|V\\376\\356+n1\\305\\366I\\223\\027\\317\\364(\\022I%\\204\\330R@\\220A\\034\\203#\\342\\022I\\020H64!OK\\207s\\257\\306\\256\\327@s\\207\\2728\\224uO\\2452\\306a\\263\\177\\007V\\217"\\256.\\237\\014\\245,P\\224\\276c\\020KRB\\211\\001I$\\222\\221\\012I$\\222R\\222I$\\224\\244\\222I%)$\\222IO\\377\\321\\365T\\222I%)$\\222IJI$\\222R\\222I$\\224\\244\\222I%)$\\222IJY\\335[\\015\\257\\250\\336\\301\\357i\\227y\\210\\205\\242\\243c7\\326\\346Ln\\004O\\204\\246e\\3062\\343\\2269m!I\\006\\210=\\236U$|\\274\\0330\\336\\030\\367\\207\\207}\\007pL\\177%\\001s9q\\313\\034\\345\\011o\\022\\332\\004\\021aJ\\316\\016\\023\\262\\354\\211\\332\\306\\352\\347*\\313w\\243\\326\\326bo\\356\\342K\\217\\301O\\311`\\031\\263\\010\\313\\345\\003\\212_E\\263\\227\\014l7\\232\\320\\326\\206\\216\\000\\200\\235e\\344u\\246\\261\\305\\2643|~q\\343\\344\\250du\\014\\213\\337\\273q`\\354\\326\\230\\013S/\\304pc\\322>\\2624\\250\\355\\3763\\020\\305#\\276\\217F\\222\\316\\350\\371\\027[[\\305\\204\\2704\\350\\342\\264U\\2549F\\\\q\\310\\001\\002]\\326HQ\\245$\\222JD)$\\222IJI$\\222R\\222I$\\224\\377\\000\\377\\322\\365T\\222I%)$\\222IJI$\\222R\\222I$\\224\\244\\222I%)$\\222IJI$\\222S\\316u\\033]ne\\204\\231\\015;Z<\\000U\\221\\363\\333\\267.\\315\\013d\\314\\024\\005\\314g$\\346\\310e\\251\\342-\\250\\354<\\224\\254\\321\\230\\372\\261m\\246~\\220\\033~\\177IVI6\\031%\\003q4h\\307\\374d\\221{\\251$\\222LK\\263\\321-.\\251\\365\\037\\3142>kMQ\\351\\030\\306\\234}\\356\\020\\3535\\371vW\\227I\\311\\211\\016_\\030\\226\\365\\370~\\213Vu\\304iI$\\222\\260\\265I$\\222JRI$\\222\\224\\222I$\\247\\377\\323\\365T\\222I%)$\\222IJI$\\222R\\222I$\\224\\244\\222I%)$\\222IJI$\\222S\\217\\326q\\355}\\315\\262\\266\\02743\\334\\341\\333U\\232+\\261\\3344\\237\\200+\\252M\\001g\\347\\370lr\\3449\\004\\314x\\276a\\\\Z\\262G)\\002\\251\\345\\354\\242\\372H\\026\\260\\262ul\\367\\012\\013\\245\\313\\304\\257*\\275\\217\\320\\217\\242\\341\\310Y\\026t|\\266\\272\\030\\003\\307b\\014*<\\317\\303\\362c\\227\\352\\342rC\\303Y\\177\\204\\311\\034\\200\\215t-\\025\\267\\207\\322\\361\\200e\\316\\227\\222\\001\\000\\360\\252\\321\\321\\262\\034\\361\\353C[\\337Y+i\\255\\015hh\\340\\010\\012\\307\\303\\3712\\014\\245\\233\\037n\\016=\\357\\373\\253rO\\244O\\330\\256\\023\\244\\222\\326aRI$\\222\\224\\222I$\\245$\\222I)I$\\222J\\177\\377\\324\\365T\\222I%)$\\222IJI$\\222R\\222I$\\224\\244\\222I%)$\\225l\\373\\305\\030\\257q\\324\\237kG\\231M\\234\\304#)\\035\\242- Y\\245\\014\\374s\\2201\\301\\227\\036\\010\\342|\\025\\225\\312\\262\\307\\326\\361c>\\233u\\037\\025\\324\\265\\333\\232\\034;\\211Uy\\036jY\\3433 \\001\\214\\264\\257\\335+\\262C\\206\\251t\\222I\\\\X\\244\\222I%"\\310\\310\\257\\036\\243m\\246\\032 |\\316\\215\\013>\\216\\266\\322\\350\\275\\233Gg7_\\301O\\353\\013\\\\\\376\\225c[!\\333\\253 \\215b\\036\\307\\377\\000\\337V \\324\\003\\342%g|C\\230\\313\\206P8\\315\\016\\242\\276f\\\\p\\004\\033z\\300C\\200#Pu\\005:\\253\\323\\254k\\360\\353\\203%\\242\\035\\345\\012\\322\\277\\216|p\\214\\277x\\003\\366\\261\\221D\\205$\\222I\\310RI$\\222\\224\\222I$\\245$\\222I)\\377\\325\\365T\\222I%)$\\222IJI$\\222R\\222I$\\224\\244\\222I%)`\\365|\\241u\\342\\266\\031ezyn\\356\\254g\\365W5\\316\\246\\210\\323G?\\377\\000"\\262VG\\304y\\310\\310\\0348\\315\\353\\353\\227\\367\\177F,\\330\\340G\\250\\375\\024\\027E\\323^\\347\\340P]\\364\\2664;\\342\\002\\347V\\317B{N=\\214\\016\\2275\\362G\\206\\340!\\017\\204\\344\\251\\317\\035n8\\257\\373\\251\\314=6\\351\\244\\222Ka\\201I$\\222Js:\\363\\343\\021\\225\\314z\\266\\006\\2370\\003\\237\\037\\364\\0262\\331\\353\\245\\276\\215M<\\356\\334>B?\\357\\313\\031b|VW\\230F\\366\\217\\346\\330\\305\\362\\266zvC\\250\\311d\\037c\\316\\327\\016\\332\\256\\215r\\2150\\340|\\012\\352X\\340X\\035:\\020\\014\\253\\037\\011\\2310\\234\\011\\371H#\\374%\\231\\206\\240\\262I0 \\360\\235i\\261)$\\222IJI$\\222R\\222I$\\224\\377\\000\\377\\326\\365T\\222I%)$\\222IJI$\\222R\\222I$\\224\\245_>\\377\\000C\\031\\357\\037H\\210o\\304\\253\\013\\037\\256]/\\256\\221\\300\\033\\217\\317\\205_\\234\\313\\355`\\234\\206\\365\\303\\037\\357It\\005\\310\\007/\\235JI$\\271\\266\\322\\226\\207\\325\\352\\313r\\262\\337\\371\\2666\\257\\275\\276\\250Y\\353K\\241\\330\\006C\\353\\375\\366\\310\\376\\311\\377\\000\\314\\225\\337\\206\\312\\271\\230\\217\\336\\022\\0132|\\245\\332I$\\226\\373YI$\\222Jq\\272\\347\\363\\225\\374\\016\\2131h\\365\\267\\003\\220\\306\\367ku\\371\\254\\345\\316\\363\\346\\371\\234\\236\\004\\017\\2606q\\374\\241Kw\\244dz\\230\\336\\231\\372U\\351\\362\\354\\260\\221\\360\\262\\235\\213p\\260j\\323\\243\\207\\222\\\\\\216q\\20702\\371%\\351\\227\\361VH\\361G\\305\\351RQc\\332\\366\\007\\264\\313\\\\$\\025%\\321\\003z\\206\\262\\222I$\\224\\244\\222I%)$\\222IO\\377\\327\\365T\\222I%)$\\222IJI$\\222R\\222I1\\235#\\346\\222\\226.\\002g@\\321$\\366\\\\\\336]\\346\\374\\207\\331\\330\\230o\\300->\\261\\231\\261\\237ga\\367;\\351\\371\\005\\214\\261\\276)\\314\\011Hb\\211\\322\\032\\313\\373\\377\\000\\372\\013>(\\320\\263\\325I$\\222\\314eRTe\\014^\\245\\202\\343\\364m\\273\\321?\\365\\306Y\\267\\377\\000\\004k\\022Uz\\220x\\307e\\265\\211}\\027Sh\\376\\305\\215s\\317\\375\\267\\275X\\344\\215s\\030\\377\\000\\274\\266b\\342^\\331$\\300\\202\\001\\034\\024\\353\\244j\\251$\\223\\023\\002RS\\316\\365\\033=L\\313O\\201\\332>Z*\\312W;u\\257w\\213\\211Q\\\\\\266Yqd\\234\\277zD\\266\\300\\240\\002\\222I$\\304\\272\\035/\\250\\012^)\\271\\321[\\310kI\\341\\256\\374\\337\\363\\226\\342\\344\\\\\\320\\366\\226\\273\\202\\272\\254w\\213)c\\346d\\015V\\347\\3033\\234\\230\\3169o\\217o\\356tk\\345\\215\\033\\356\\221$\\222Z\\014jI$\\222R\\222I$\\224\\377\\000\\377\\320\\365T\\222I%)$\\222IJI$\\222R\\220r\\362\\006=\\016\\264\\362\\007\\264x\\224e\\207\\3262\\305\\266\\212Xe\\265\\362|J\\257\\315\\347\\366p\\312W\\352>\\230\\177yt#\\304i\\241c\\335c\\313\\336e\\3162Jd\\222\\\\\\341$\\233=[JI$\\220R\\220\\262\\253\\026\\342\\335Q\\341\\354p?r*@\\002`\\360tRa\\227\\016X\\036\\322\\010:\\202\\365\\030\\315-\\307\\251\\244\\316\\3264O\\300" \\342?~5N\\361`\\237\\214#.\\241\\250\\244,\\233\\005T=\\347\\260(\\253?\\255Y\\267\\0302u{\\277\\000\\242\\317>\\014S\\237\\356\\304\\327\\232b,\\200\\341\\223&RI%\\3146\\324\\222I$\\245-\\356\\221`v\\030l\\352\\302AX*\\327O\\314v-\\3365\\277G\\017\\373\\362\\267\\310g\\030\\263\\003/\\226C\\200\\375Vd\\215\\305\\350\\222L\\235t-e$\\222I)I$\\222J\\177\\377\\321\\365T\\222I%)$\\222IJI$\\222S[\\250d\\375\\237\\031\\316\\037M\\336\\326\\374J\\347\\011$\\311\\344\\255\\036\\265~\\373\\233P:0I\\370\\225\\234\\260>#\\233\\334\\316b\\017\\247\\037\\244\\177{\\364\\233\\030\\243Q\\363RI$\\2512)$\\222IJH\\030 \\370$\\222*z\\016\\226I\\303h=\\211\\003\\341*\\342\\241\\321\\237\\273\\020\\217\\335q\\037\\202\\276\\272\\214R\\023\\307\\011\\017\\322\\210-I\\012\\221\\363R\\310\\353\\216n\\352\\333>\\341$\\217%\\256\\271\\356\\252\\010\\316\\260\\2233\\004y\\010\\001V\\370\\214\\270yi\\017\\336"?\\212\\354C\\324\\324I$\\227>\\331RI$\\222\\224\\222I$\\247\\244\\300\\277\\327\\305c\\373\\217k\\276!XX}+9\\230\\356\\364\\255;k\\260\\350\\362@\\0151\\371\\323\\373\\337En.\\233\\225\\311\\356a\\204\\373\\215\\177\\275\\035\\332\\263\\215H\\251$\\222S-RI$\\222\\237\\377\\322\\365T\\222I%)$\\222IJ@\\313\\312f5E\\356\\347\\363G\\211F\\015\\000\\222?;R\\261:\\323\\334rC\\017\\015\\032\\017\\212\\257\\316g8p\\231\\217\\233\\345\\217\\231]\\010\\361J\\2326=\\326=\\317q\\2278\\311QI%\\316\\022I\\263\\325\\264\\244\\222I\\005)$\\222IJI$\\222S\\275\\321\\331\\267\\014\\037\\336$\\253\\3133\\243d4\\324h&\\034\\323 y\\025\\246\\272^L\\203\\313\\342\\257\\335\\003\\354\\335\\253?\\230\\251s\\335U\\301\\331\\257\\216\\320>\\340\\267\\354{kc\\236\\355\\003D\\225\\314]a\\266\\327Xyq\\225S\\342\\323\\254P\\217\\357J\\377\\000\\305\\377\\000\\321\\227\\341\\032\\222\\301$\\222X\\254\\352I$\\222R\\222I$\\224\\240\\001\\321\\334\\025\\324Qcm\\245\\2267P\\346\\202\\271u\\263\\321-\\232\\237Q?D\\356h\\362?K\\376\\232\\326\\370No\\237\\021\\376\\374\\177\\356\\230s\\015\\213\\246\\222I-f\\025$\\222I)\\377\\323\\365T\\222I%)$\\222IK\\022\\032\\011:\\001\\251\\\\\\326e\\346\\374\\207\\331\\330\\230o\\300-~\\257\\177\\245\\215\\260\\037u\\232|\\273\\254%\\217\\361\\\\\\367(\\341\\037\\243\\352\\227\\367\\277E\\233\\014t\\265$\\222K-\\231I$\\222JRI$\\222\\224\\222I$\\244\\270\\323\\366\\212v\\222\\017\\255Y1\\373\\263\\252\\351\\327/\\210\\362\\334\\246\\021\\247\\007wa\\264\\356?\\347B\\352\\027A\\360\\323|\\274GbX3\\017W\\230i\\365Y\\373\\015\\200w\\200~\\022%s\\353\\177\\253\\2221\\010\\035\\317\\344\\005\\337\\301`*_\\026?\\254\\200\\355\\037\\332\\273\\017\\312|\\324\\222I,\\306U$\\222I)I$\\222*R.6[\\261,\\027\\200\\\\\\033\\364\\3329-\\374\\361\\377\\000~BI?\\016Yb\\311\\034\\221\\336''\\360\\352\\202\\001\\024^\\254\\020@#Px)\\325^\\235\\223\\366\\234V\\270\\375&\\373\\037\\361\\035\\377\\000\\264\\255.\\232\\022\\023\\210\\220\\332B\\307\\325\\252E\\032RI$\\234\\207\\377\\324\\365T\\222I%)1\\323T\\352\\237T\\277\\321\\305t\\030s\\375\\243\\346\\231\\223 \\307\\011L\\355\\021i\\002\\310\\016OQ\\312\\373FA#\\3503\\332\\325U$\\2273\\223$\\262NS\\226\\3626\\332\\002\\205\\005$\\222I\\211RI$\\222\\224\\222I$\\245$\\222I)&4\\233\\230\\337\\022#\\343+\\250\\\\\\233^\\352\\310{~\\223u\\037%\\322\\341\\3453*\\221c~\\007B5\\376\\322\\332\\370VK\\307(\\023\\362\\235<\\213\\016`l\\026\\267Yp\\030\\300N\\244\\350<V\\032\\323\\353\\216>\\255m\\354\\033\\370\\312\\314T\\376''>.`\\217\\334\\002?\\367K\\361\\012\\210RI$\\250\\257RI$\\222\\224\\222I$\\245$\\222I)\\321\\350\\267\\206\\\\\\352\\217\\026j>!m\\256V\\273\\015v6\\306\\362\\322\\010\\371.\\236\\233[um\\261\\206Z\\341+o\\341y\\270\\261\\234d\\353\\015\\277\\271&\\014\\261\\243}\\331\\244\\222KE\\211\\377\\325\\365T\\222I%)b\\365\\313f\\326T\\016\\215\\022~%l=\\341\\214/v\\201\\242J\\346r.7\\\\\\373]\\313\\214\\374\\273,\\377\\000\\211\\346\\021\\303\\355\\365\\310\\177\\346\\305\\223\\024l\\337di$\\222\\303l)$\\222IJI$\\222R\\222I$\\224\\244\\222I%)mtsUX\\333}BK\\311y\\336\\351\\211\\354\\331\\372-X\\253\\234\\372\\300\\343e\\325\\342\\214\\347ScN\\352\\352\\207\\031$\\2274C>\\237\\376\\253Z\\037\\014\\230\\216I\\377\\000v\\376\\305\\263\\217\\020\\253\\247\\260\\352\\367\\262\\334\\220\\030C\\203\\004H2\\251!\\343\\266\\306\\322\\321k\\213\\254\\217{\\235\\314\\374?5\\021V\\346\\246''\\236r\\035O\\345\\351LE\\000\\024\\222I(\\022\\244\\222I%)$\\222IJI$\\222R\\225\\376\\225\\232\\352m\\024\\273\\371\\273\\014\\017"U\\004\\\\J\\315\\2315\\264i.\\032\\251\\271y\\316\\031`a\\363X\\037\\336\\277\\321[ \\0106\\364\\351$\\222\\351\\232\\257\\377\\326\\365D\\351&s\\203Z\\\\t\\000IINoX\\312\\015\\250P\\303\\356\\177\\322\\362\\013\\031\\0333 \\344^\\353\\017\\0344y\\004\\025\\315\\363\\231\\375\\354\\322\\227\\350\\217L\\177\\272\\033P\\217\\014@RI$\\253\\256RI$\\222\\224\\222I$\\245$\\222I)I$\\222JR\\301\\315\\305\\365\\276\\264a\\223H!\\265\\233\\015\\322t\\331\\273\\331\\265o(\\355\\367\\207\\370\\002>\\370Sr\\3718&O\\365d?\\346\\240\\262I$\\224)RI$\\222\\224\\222I$\\245$\\222I)I$\\222JR@\\220A\\006\\010\\324\\021\\342\\222H\\202A\\261\\241\\012zl[\\305\\3646\\301\\334k\\361FY}\\016\\311\\256\\312\\347\\350\\231\\003\\342\\265\\027C\\014\\346\\\\\\241\\313\\372C\\034\\217\\370P\\015c\\032\\235x\\277\\377\\327\\3656\\222G\\273\\231?t\\350\\253uK\\0130\\337\\032\\027C~\\365mdu\\313\\214\\327H\\343\\351\\037\\310\\025~w''\\007/\\220\\367\\034#\\316^\\225\\320\\027 \\345$\\222K\\233m)$\\222IJI$\\222R\\222I$\\224\\244\\222I%)$\\222IJI%B\\336\\242\\332\\372\\2558|\\266\\306\\2709\\336\\017>\\352\\333\\377\\000E?\\034%2D{\\023\\376(S}$\\222LR\\222I$\\224\\244\\222I%)$\\222IJI$\\222R\\222I$\\224\\335\\350\\3669\\231\\201\\273\\200c\\301\\016\\004rG\\321[\\353\\225\\255\\356\\256\\306\\275\\274\\264\\310]\\027\\332\\333\\366?\\265F\\233f<\\326\\247''\\230\\036S>#\\274!9\\017\\356\\312,3\\217\\256''\\271\\017\\377\\320\\365U\\317\\365\\334\\200\\334\\372\\352s\\034\\001\\256E\\261\\355&O\\263w\\357.\\201%\\0271\\204f\\306q\\223W\\325te\\302m\\344\\322]+\\360q\\036e\\3254\\237\\202\\005\\235''\\015\\332\\206\\021\\344\\323\\375\\353&_\\012\\314>YF_\\203(\\315\\036\\305\\301Il\\216\\215C\\331>\\372\\334\\1775\\305\\244\\217\\363e\\250.\\350vO\\266\\301\\036aE/\\207s1\\375\\016/\\356\\225\\303${\\271\\211-1\\320\\254\\357h\\373\\2202\\372>uLi\\305kr\\036\\\\\\003\\232]\\260\\006\\301\\367\\356ro\\3349\\237\\363g\\355\\212\\275\\310\\367i\\244\\216zWYh\\237B\\247\\037\\006\\333\\257\\375:\\332\\227\\354\\316\\253\\266N8\\237\\001c\\177\\330\\201\\344y\\221\\376L\\376\\011\\343\\217t\\011#\\216\\235\\324\\3178\\256\\037\\333\\257\\377\\000J)\\216\\227\\236F\\264\\221\\345\\271\\277\\371$\\323\\312s\\003\\374\\224\\276\\305q\\307\\270j\\244\\254\\376\\314\\317\\377\\000B\\177\\316o\\376I7\\354\\336\\2410(?\\022\\346G\\375R\\037u\\317\\376j\\177\\342\\225qG\\270C]vZ\\361]cs\\335\\240\\013Tt6\\277\\244}\\206\\3677\\327\\367<^\\321\\364l$\\275\\2267w\\356+\\030\\0359\\270\\336\\367\\235\\326\\237\\301]Z\\374\\207&qD\\313 \\365\\317J\\375\\330\\261d\\311f\\206\\301\\344\\337NN3\\306>X\\002\\350\\220\\346\\375\\027\\201\\371\\365\\377\\000\\337\\331\\371\\211.\\242\\352j\\271\\233-hs|\\377\\000(X\\371=\\036\\3668\\232=\\354\\354''US\\234\\370|\\343#<C\\212\\007^\\021\\363C\\377\\000A]\\014\\200\\350t.zJO\\252\\332\\314=\\205\\247\\314B\\210k\\211\\200\\011>K8\\202\\015\\020m\\225I#7\\017-\\361\\266\\227\\231\\362\\201\\367\\271\\035\\275\\0375\\302KZ\\337"u\\374\\024\\261\\345\\363K\\345\\307#\\376\\012\\323(\\216\\241\\244\\222\\267\\373'':c`\\373\\302-}\\023%\\337M\\315g\\342\\214yN`\\232\\030\\245\\365\\034?\\364\\225\\307\\036\\341\\317Ih\\236\\207\\221\\331\\355\\374T\\331\\320\\354\\374\\373\\000\\370\\004\\361\\310\\363$\\327\\266\\177\\004{\\221\\356\\345\\244\\266\\035\\320\\352\\201\\266\\302\\017y\\010\\264\\364|V\\17796\\037=\\007\\340\\244\\037\\014\\346I\\242\\000\\3612\\321\\036\\354\\\\61\\317pk\\001s\\217\\000-\\377\\000\\261\\273');
SELECT lowrite(0, '\\366i\\306\\374\\362\\337\\307\\351+\\025c\\321O\\363l\\015\\370\\004E{\\007\\303\\375\\274y\\001\\2203\\311\\011c\\376\\254x\\230\\345\\222\\310\\323@m\\377\\321\\365T\\222I%)$\\222IJI$\\222R\\222I$\\224\\244\\222I%)$\\222IJI$\\222R\\222I$\\224\\244\\222I%,@<\\244\\032\\321\\300\\001:I)I$\\222JRI$\\222\\224\\222I$\\245$\\222I)I$\\222J\\177\\377\\322\\365T\\222I%)$\\222IJI$\\222R\\222I$\\224\\244\\222I%)$\\222IJI$\\222R\\222I$\\224\\244\\222I%)$\\222IJI$\\222R\\222I$\\224\\244\\222I%)$\\222IJI$\\222S\\377\\331');
SELECT lo_close(0);

COMMIT;

--
-- TOC entry 1973 (class 0 OID 0)
-- Dependencies: 1972
-- Data for Name: BLOB COMMENTS; Type: BLOB COMMENTS; Schema: -; Owner: 
--


SET search_path = public, pg_catalog;

--
-- TOC entry 1901 (class 2606 OID 88385)
-- Dependencies: 1571 1571
-- Name: arquivo_persistente_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY arquivo_persistente
    ADD CONSTRAINT arquivo_persistente_pkey PRIMARY KEY (id);


--
-- TOC entry 1853 (class 2606 OID 88238)
-- Dependencies: 1551 1551
-- Name: campoextraproduto_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY campoextraproduto
    ADD CONSTRAINT campoextraproduto_pkey PRIMARY KEY (id);


--
-- TOC entry 1855 (class 2606 OID 88243)
-- Dependencies: 1552 1552
-- Name: cidade_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY cidade
    ADD CONSTRAINT cidade_pkey PRIMARY KEY (id);


--
-- TOC entry 1857 (class 2606 OID 88251)
-- Dependencies: 1553 1553
-- Name: creditcard_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY creditcard
    ADD CONSTRAINT creditcard_pkey PRIMARY KEY (id);


--
-- TOC entry 1905 (class 2606 OID 88578)
-- Dependencies: 1574 1574
-- Name: demografico_id_pessoa_key; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY demografico
    ADD CONSTRAINT demografico_id_pessoa_key UNIQUE (id_pessoa);


--
-- TOC entry 1907 (class 2606 OID 88576)
-- Dependencies: 1574 1574
-- Name: demografico_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY demografico
    ADD CONSTRAINT demografico_pkey PRIMARY KEY (id);


--
-- TOC entry 1859 (class 2606 OID 88259)
-- Dependencies: 1554 1554
-- Name: diasemana_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY diasemana
    ADD CONSTRAINT diasemana_pkey PRIMARY KEY (id);


--
-- TOC entry 1861 (class 2606 OID 88264)
-- Dependencies: 1555 1555
-- Name: disponibilidadeproduto_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY disponibilidadeproduto
    ADD CONSTRAINT disponibilidadeproduto_pkey PRIMARY KEY (id);


--
-- TOC entry 1863 (class 2606 OID 88274)
-- Dependencies: 1556 1556
-- Name: endereco_id_cidade_key; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY endereco
    ADD CONSTRAINT endereco_id_cidade_key UNIQUE (id_cidade);


--
-- TOC entry 1865 (class 2606 OID 88272)
-- Dependencies: 1556 1556
-- Name: endereco_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY endereco
    ADD CONSTRAINT endereco_pkey PRIMARY KEY (id);


--
-- TOC entry 1867 (class 2606 OID 88279)
-- Dependencies: 1557 1557
-- Name: estado_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY estado
    ADD CONSTRAINT estado_pkey PRIMARY KEY (id);


--
-- TOC entry 1869 (class 2606 OID 88284)
-- Dependencies: 1558 1558
-- Name: estoque_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY estoque
    ADD CONSTRAINT estoque_pkey PRIMARY KEY (id);


--
-- TOC entry 1871 (class 2606 OID 88289)
-- Dependencies: 1559 1559
-- Name: intervaloservico_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY intervaloservico
    ADD CONSTRAINT intervaloservico_pkey PRIMARY KEY (id);


--
-- TOC entry 1873 (class 2606 OID 88297)
-- Dependencies: 1560 1560
-- Name: mensagem_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY mensagem
    ADD CONSTRAINT mensagem_pkey PRIMARY KEY (id);


--
-- TOC entry 1875 (class 2606 OID 88307)
-- Dependencies: 1561 1561
-- Name: parametro_codigo_key; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY parametro
    ADD CONSTRAINT parametro_codigo_key UNIQUE (codigo);


--
-- TOC entry 1877 (class 2606 OID 88305)
-- Dependencies: 1561 1561
-- Name: parametro_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY parametro
    ADD CONSTRAINT parametro_pkey PRIMARY KEY (id);


--
-- TOC entry 1879 (class 2606 OID 88315)
-- Dependencies: 1562 1562
-- Name: pessoa_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY pessoa
    ADD CONSTRAINT pessoa_pkey PRIMARY KEY (id);


--
-- TOC entry 1881 (class 2606 OID 88323)
-- Dependencies: 1563 1563
-- Name: planejamentoservico_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY planejamentoservico
    ADD CONSTRAINT planejamentoservico_pkey PRIMARY KEY (id);


--
-- TOC entry 1903 (class 2606 OID 88571)
-- Dependencies: 1573 1573
-- Name: produto_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY produto
    ADD CONSTRAINT produto_pkey PRIMARY KEY (id);


--
-- TOC entry 1883 (class 2606 OID 88328)
-- Dependencies: 1564 1564
-- Name: registroentrada_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY registroentrada
    ADD CONSTRAINT registroentrada_pkey PRIMARY KEY (id);


--
-- TOC entry 1885 (class 2606 OID 88336)
-- Dependencies: 1565 1565
-- Name: securitycard_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY securitycard
    ADD CONSTRAINT securitycard_pkey PRIMARY KEY (id);


--
-- TOC entry 1887 (class 2606 OID 88344)
-- Dependencies: 1566 1566
-- Name: subtipoproduto_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY subtipoproduto
    ADD CONSTRAINT subtipoproduto_pkey PRIMARY KEY (id);


--
-- TOC entry 1889 (class 2606 OID 88352)
-- Dependencies: 1567 1567
-- Name: tipoarmazenamento_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY tipoarmazenamento
    ADD CONSTRAINT tipoarmazenamento_pkey PRIMARY KEY (id);


--
-- TOC entry 1891 (class 2606 OID 88360)
-- Dependencies: 1568 1568
-- Name: tipocampoextraproduto_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY tipocampoextraproduto
    ADD CONSTRAINT tipocampoextraproduto_pkey PRIMARY KEY (id);


--
-- TOC entry 1893 (class 2606 OID 88368)
-- Dependencies: 1569 1569
-- Name: tipoproduto_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY tipoproduto
    ADD CONSTRAINT tipoproduto_pkey PRIMARY KEY (id);


--
-- TOC entry 1895 (class 2606 OID 88375)
-- Dependencies: 1570 1570
-- Name: usuario_id_pessoa_key; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY usuario
    ADD CONSTRAINT usuario_id_pessoa_key UNIQUE (id_pessoa);


--
-- TOC entry 1897 (class 2606 OID 88377)
-- Dependencies: 1570 1570
-- Name: usuario_id_security_card_key; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY usuario
    ADD CONSTRAINT usuario_id_security_card_key UNIQUE (id_security_card);


--
-- TOC entry 1899 (class 2606 OID 88373)
-- Dependencies: 1570 1570
-- Name: usuario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY usuario
    ADD CONSTRAINT usuario_pkey PRIMARY KEY (id);


--
-- TOC entry 1930 (class 2606 OID 88496)
-- Dependencies: 1563 1564 1882
-- Name: fk186deea156867665; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY planejamentoservico
    ADD CONSTRAINT fk186deea156867665 FOREIGN KEY (id_registro_entrada_cadastro) REFERENCES registroentrada(id);


--
-- TOC entry 1931 (class 2606 OID 88501)
-- Dependencies: 1564 1563 1882
-- Name: fk186deea1a0f53ec1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY planejamentoservico
    ADD CONSTRAINT fk186deea1a0f53ec1 FOREIGN KEY (id_registro_entrada_inativacao) REFERENCES registroentrada(id);


--
-- TOC entry 1948 (class 2606 OID 88579)
-- Dependencies: 1562 1574 1878
-- Name: fk2a2ffee2d5cf21ac; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY demografico
    ADD CONSTRAINT fk2a2ffee2d5cf21ac FOREIGN KEY (id_pessoa) REFERENCES pessoa(id);


--
-- TOC entry 1932 (class 2606 OID 88506)
-- Dependencies: 1564 1565 1882
-- Name: fk39f01a9056867665; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY securitycard
    ADD CONSTRAINT fk39f01a9056867665 FOREIGN KEY (id_registro_entrada_cadastro) REFERENCES registroentrada(id);


--
-- TOC entry 1933 (class 2606 OID 88511)
-- Dependencies: 1564 1565 1882
-- Name: fk39f01a90a0f53ec1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY securitycard
    ADD CONSTRAINT fk39f01a90a0f53ec1 FOREIGN KEY (id_registro_entrada_inativacao) REFERENCES registroentrada(id);


--
-- TOC entry 1937 (class 2606 OID 88531)
-- Dependencies: 1569 1882 1564
-- Name: fk4683790556867665; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tipoproduto
    ADD CONSTRAINT fk4683790556867665 FOREIGN KEY (id_registro_entrada_cadastro) REFERENCES registroentrada(id);


--
-- TOC entry 1938 (class 2606 OID 88536)
-- Dependencies: 1569 1882 1564
-- Name: fk46837905a0f53ec1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY tipoproduto
    ADD CONSTRAINT fk46837905a0f53ec1 FOREIGN KEY (id_registro_entrada_inativacao) REFERENCES registroentrada(id);


--
-- TOC entry 1945 (class 2606 OID 88594)
-- Dependencies: 1564 1882 1573
-- Name: fk50c666d956867665; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY produto
    ADD CONSTRAINT fk50c666d956867665 FOREIGN KEY (id_registro_entrada_cadastro) REFERENCES registroentrada(id);


--
-- TOC entry 1944 (class 2606 OID 88589)
-- Dependencies: 1892 1569 1573
-- Name: fk50c666d97feb79ce; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY produto
    ADD CONSTRAINT fk50c666d97feb79ce FOREIGN KEY (id_tipo) REFERENCES tipoproduto(id);


--
-- TOC entry 1946 (class 2606 OID 88599)
-- Dependencies: 1882 1573 1564
-- Name: fk50c666d9a0f53ec1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY produto
    ADD CONSTRAINT fk50c666d9a0f53ec1 FOREIGN KEY (id_registro_entrada_inativacao) REFERENCES registroentrada(id);


--
-- TOC entry 1943 (class 2606 OID 88584)
-- Dependencies: 1573 1886 1566
-- Name: fk50c666d9c214fea4; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY produto
    ADD CONSTRAINT fk50c666d9c214fea4 FOREIGN KEY (id_subtipo) REFERENCES subtipoproduto(id);


--
-- TOC entry 1947 (class 2606 OID 88604)
-- Dependencies: 1567 1888 1573
-- Name: fk50c666d9e562d1e7; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY produto
    ADD CONSTRAINT fk50c666d9e562d1e7 FOREIGN KEY (id_tipo_armazenamento) REFERENCES tipoarmazenamento(id);


--
-- TOC entry 1912 (class 2606 OID 88406)
-- Dependencies: 1882 1564 1553
-- Name: fk552751c956867665; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY creditcard
    ADD CONSTRAINT fk552751c956867665 FOREIGN KEY (id_registro_entrada_cadastro) REFERENCES registroentrada(id);


--
-- TOC entry 1913 (class 2606 OID 88411)
-- Dependencies: 1882 1564 1553
-- Name: fk552751c9a0f53ec1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY creditcard
    ADD CONSTRAINT fk552751c9a0f53ec1 FOREIGN KEY (id_registro_entrada_inativacao) REFERENCES registroentrada(id);


--
-- TOC entry 1914 (class 2606 OID 88416)
-- Dependencies: 1878 1553 1562
-- Name: fk552751c9d5cf21ac; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY creditcard
    ADD CONSTRAINT fk552751c9d5cf21ac FOREIGN KEY (id_pessoa) REFERENCES pessoa(id);


--
-- TOC entry 1908 (class 2606 OID 88386)
-- Dependencies: 1890 1568 1551
-- Name: fk55ece5d7ec94788c; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY campoextraproduto
    ADD CONSTRAINT fk55ece5d7ec94788c FOREIGN KEY (id_tipo) REFERENCES tipocampoextraproduto(id);


--
-- TOC entry 1941 (class 2606 OID 88551)
-- Dependencies: 1884 1565 1570
-- Name: fk5b4d8b0e181e235; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY usuario
    ADD CONSTRAINT fk5b4d8b0e181e235 FOREIGN KEY (id_security_card) REFERENCES securitycard(id);


--
-- TOC entry 1939 (class 2606 OID 88541)
-- Dependencies: 1570 1882 1564
-- Name: fk5b4d8b0e56867665; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY usuario
    ADD CONSTRAINT fk5b4d8b0e56867665 FOREIGN KEY (id_registro_entrada_cadastro) REFERENCES registroentrada(id);


--
-- TOC entry 1940 (class 2606 OID 88546)
-- Dependencies: 1570 1882 1564
-- Name: fk5b4d8b0ea0f53ec1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY usuario
    ADD CONSTRAINT fk5b4d8b0ea0f53ec1 FOREIGN KEY (id_registro_entrada_inativacao) REFERENCES registroentrada(id);


--
-- TOC entry 1942 (class 2606 OID 88556)
-- Dependencies: 1562 1878 1570
-- Name: fk5b4d8b0ed5cf21ac; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY usuario
    ADD CONSTRAINT fk5b4d8b0ed5cf21ac FOREIGN KEY (id_pessoa) REFERENCES pessoa(id);


--
-- TOC entry 1915 (class 2606 OID 88421)
-- Dependencies: 1556 1564 1882
-- Name: fk6b07cbe956867665; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY endereco
    ADD CONSTRAINT fk6b07cbe956867665 FOREIGN KEY (id_registro_entrada_cadastro) REFERENCES registroentrada(id);


--
-- TOC entry 1917 (class 2606 OID 88431)
-- Dependencies: 1564 1882 1556
-- Name: fk6b07cbe9a0f53ec1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY endereco
    ADD CONSTRAINT fk6b07cbe9a0f53ec1 FOREIGN KEY (id_registro_entrada_inativacao) REFERENCES registroentrada(id);


--
-- TOC entry 1916 (class 2606 OID 88426)
-- Dependencies: 1854 1552 1556
-- Name: fk6b07cbe9a9d3b0a6; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY endereco
    ADD CONSTRAINT fk6b07cbe9a9d3b0a6 FOREIGN KEY (id_cidade) REFERENCES cidade(id);


--
-- TOC entry 1918 (class 2606 OID 88436)
-- Dependencies: 1878 1556 1562
-- Name: fk6b07cbe9d5cf21ac; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY endereco
    ADD CONSTRAINT fk6b07cbe9d5cf21ac FOREIGN KEY (id_pessoa) REFERENCES pessoa(id);


--
-- TOC entry 1924 (class 2606 OID 88466)
-- Dependencies: 1882 1559 1564
-- Name: fk7068673556867665; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY intervaloservico
    ADD CONSTRAINT fk7068673556867665 FOREIGN KEY (id_registro_entrada_cadastro) REFERENCES registroentrada(id);


--
-- TOC entry 1923 (class 2606 OID 88461)
-- Dependencies: 1858 1554 1559
-- Name: fk706867356e34215b; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY intervaloservico
    ADD CONSTRAINT fk706867356e34215b FOREIGN KEY (id_dia_semana) REFERENCES diasemana(id);


--
-- TOC entry 1925 (class 2606 OID 88471)
-- Dependencies: 1882 1559 1564
-- Name: fk70686735a0f53ec1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY intervaloservico
    ADD CONSTRAINT fk70686735a0f53ec1 FOREIGN KEY (id_registro_entrada_inativacao) REFERENCES registroentrada(id);


--
-- TOC entry 1935 (class 2606 OID 88521)
-- Dependencies: 1564 1882 1566
-- Name: fk754f1ee556867665; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY subtipoproduto
    ADD CONSTRAINT fk754f1ee556867665 FOREIGN KEY (id_registro_entrada_cadastro) REFERENCES registroentrada(id);


--
-- TOC entry 1934 (class 2606 OID 88516)
-- Dependencies: 1569 1566 1892
-- Name: fk754f1ee57feb79ce; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY subtipoproduto
    ADD CONSTRAINT fk754f1ee57feb79ce FOREIGN KEY (id_tipo) REFERENCES tipoproduto(id);


--
-- TOC entry 1936 (class 2606 OID 88526)
-- Dependencies: 1564 1882 1566
-- Name: fk754f1ee5a0f53ec1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY subtipoproduto
    ADD CONSTRAINT fk754f1ee5a0f53ec1 FOREIGN KEY (id_registro_entrada_inativacao) REFERENCES registroentrada(id);


--
-- TOC entry 1910 (class 2606 OID 88396)
-- Dependencies: 1882 1564 1552
-- Name: fk784b434456867665; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cidade
    ADD CONSTRAINT fk784b434456867665 FOREIGN KEY (id_registro_entrada_cadastro) REFERENCES registroentrada(id);


--
-- TOC entry 1911 (class 2606 OID 88401)
-- Dependencies: 1552 1564 1882
-- Name: fk784b4344a0f53ec1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cidade
    ADD CONSTRAINT fk784b4344a0f53ec1 FOREIGN KEY (id_registro_entrada_inativacao) REFERENCES registroentrada(id);


--
-- TOC entry 1909 (class 2606 OID 88391)
-- Dependencies: 1552 1866 1557
-- Name: fk784b4344c62926b4; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY cidade
    ADD CONSTRAINT fk784b4344c62926b4 FOREIGN KEY (estado_id) REFERENCES estado(id);


--
-- TOC entry 1919 (class 2606 OID 88441)
-- Dependencies: 1882 1557 1564
-- Name: fk7c49258656867665; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY estado
    ADD CONSTRAINT fk7c49258656867665 FOREIGN KEY (id_registro_entrada_cadastro) REFERENCES registroentrada(id);


--
-- TOC entry 1920 (class 2606 OID 88446)
-- Dependencies: 1882 1557 1564
-- Name: fk7c492586a0f53ec1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY estado
    ADD CONSTRAINT fk7c492586a0f53ec1 FOREIGN KEY (id_registro_entrada_inativacao) REFERENCES registroentrada(id);


--
-- TOC entry 1928 (class 2606 OID 88486)
-- Dependencies: 1564 1882 1562
-- Name: fk8e48fbc756867665; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY pessoa
    ADD CONSTRAINT fk8e48fbc756867665 FOREIGN KEY (id_registro_entrada_cadastro) REFERENCES registroentrada(id);


--
-- TOC entry 1929 (class 2606 OID 88491)
-- Dependencies: 1564 1882 1562
-- Name: fk8e48fbc7a0f53ec1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY pessoa
    ADD CONSTRAINT fk8e48fbc7a0f53ec1 FOREIGN KEY (id_registro_entrada_inativacao) REFERENCES registroentrada(id);


--
-- TOC entry 1921 (class 2606 OID 88451)
-- Dependencies: 1558 1564 1882
-- Name: fkce21a5856867665; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY estoque
    ADD CONSTRAINT fkce21a5856867665 FOREIGN KEY (id_registro_entrada_cadastro) REFERENCES registroentrada(id);


--
-- TOC entry 1922 (class 2606 OID 88456)
-- Dependencies: 1558 1564 1882
-- Name: fkce21a58a0f53ec1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY estoque
    ADD CONSTRAINT fkce21a58a0f53ec1 FOREIGN KEY (id_registro_entrada_inativacao) REFERENCES registroentrada(id);


--
-- TOC entry 1926 (class 2606 OID 88476)
-- Dependencies: 1560 1564 1882
-- Name: fkdfc72a4b56867665; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY mensagem
    ADD CONSTRAINT fkdfc72a4b56867665 FOREIGN KEY (id_registro_entrada_cadastro) REFERENCES registroentrada(id);


--
-- TOC entry 1927 (class 2606 OID 88481)
-- Dependencies: 1560 1564 1882
-- Name: fkdfc72a4ba0f53ec1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY mensagem
    ADD CONSTRAINT fkdfc72a4ba0f53ec1 FOREIGN KEY (id_registro_entrada_inativacao) REFERENCES registroentrada(id);


-- Completed on 2010-04-02 18:51:56

--
-- PostgreSQL database dump complete
--

--
-- TOC entry 192 (class 1259 OID 17612)
-- Name: performanceannotation; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE performanceannotation (
    id integer NOT NULL PRIMARY KEY,
    annotation_limit bigint NOT NULL,
    annotation_name character varying(255),
    class_name character varying(255),
    execution_date timestamp without time zone,
    execution_time bigint NOT NULL,
    method_signature character varying(255)
);


ALTER TABLE public.performanceannotation OWNER TO postgres;

--
-- TOC entry 193 (class 1259 OID 17620)
-- Name: securityannotation; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE securityannotation (
    id integer NOT NULL PRIMARY KEY,
    annotation_name character varying(255),
    class_name character varying(255),
    execution_date timestamp without time zone,
    method_signature character varying(255)
);


ALTER TABLE public.securityannotation OWNER TO postgres;

--
-- TOC entry 194 (class 1259 OID 17646)
-- Name: reliabilityannotation; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE reliabilityannotation (
    id integer NOT NULL PRIMARY KEY,
    annotation_name character varying(255),
    fail boolean,
    class_name character varying(255),
    execution_date timestamp without time zone,
    method_signature character varying(255)
);
