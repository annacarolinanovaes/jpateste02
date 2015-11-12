create database artistas;

use artistas;

CREATE TABLE tb_artistas (
    codArtista INT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(60) NOT NULL,
	nacionalidade VARCHAR(60) NOT NULL,
    PRIMARY KEY (codArtista)
);

CREATE TABLE tb_albuns (
    codAlbum INT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(60) NOT NULL,
    ano INT NOT NULL,
    artista INT NOT NULL,
    PRIMARY KEY (codAlbum),
    FOREIGN KEY (artista)
      REFERENCES tb_artistas (codArtista)
      ON DELETE RESTRICT
      ON UPDATE CASCADE
);

CREATE TABLE tb_musicas (
    codMusica INT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(60) NOT NULL,
    duracao INT NOT NULL,
    album INT NOT NULL,
    PRIMARY KEY (codMusica),
    FOREIGN KEY (album)
      REFERENCES tb_albuns (codAlbum)
      ON DELETE RESTRICT
      ON UPDATE CASCADE
);

LOCK TABLES tb_artistas WRITE;
ALTER TABLE tb_artistas DISABLE KEYS;
INSERT INTO tb_artistas(codArtista, nome, nacionalidade) VALUES 
(1,'Caetano','BR'),
(2,'Bon Jovi','EUA');
ALTER TABLE tb_artistas ENABLE KEYS;
UNLOCK TABLES;

LOCK TABLES tb_albuns WRITE;
ALTER TABLE tb_albuns DISABLE KEYS;
INSERT INTO tb_albuns(codAlbum, nome, ano, artista) VALUES 
(1,'Noites do Norte',2001,1),
(2,'Prenda Minha',1999,1),
(3,'One Wild Night Live',2001,2),
(4,'Crush',2000,2);
ALTER TABLE tb_albuns ENABLE KEYS;
UNLOCK TABLES;

LOCK TABLES tb_musicas WRITE;
ALTER TABLE tb_musicas DISABLE KEYS;
INSERT INTO tb_musicas(codMusica, nome, duracao, album) VALUES 
(1,'Cantiga de Boi',150, 1),
(2,'Meu Rio',140, 1),
(3,'Meditação',190, 2),
(4,'Sozinho',200,2),
(5,'Its My Life',210,3),
(6,'Living on a Prayer',180,3),
(7,'Just Older',180,4),
(8,'Mystery Train',150,4);
ALTER TABLE tb_musicas ENABLE KEYS;
UNLOCK TABLES;

select * from tb_albuns;
select * from tb_artistas;
select * from tb_musicas;