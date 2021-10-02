insert into cozinha (id, nome) values (1, 'Tailandesa');
insert into cozinha (id, nome) values (2, 'Italiana');

insert into restaurante (nome, taxa_frete, cozinha_id) values ('Tailandez', 10, 1);
insert into restaurante (nome, taxa_frete, cozinha_id) values ('Thai Tailandez', 10.50, 1);
insert into restaurante (nome, taxa_frete, cozinha_id) values ('Italiano', 15, 2);

insert into estado (id, nome) values (1, 'São Paulo');
insert into estado (id, nome) values (2, 'Minas Gerais');
insert into estado (id, nome) values (3, 'Acre');

insert into cidade (nome, estado_id) values ('Santos', 1);
insert into cidade (nome, estado_id) values ('Belo Horizonte', 2);
insert into cidade (nome, estado_id) values ('Rio Branco', 3);