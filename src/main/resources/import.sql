insert into cozinha (id, nome) values (1, 'Tailandesa');
insert into cozinha (id, nome) values (2, 'Italiana');

insert into estado (id, nome) values (1, 'São Paulo');
insert into estado (id, nome) values (2, 'Minas Gerais');
insert into estado (id, nome) values (3, 'Acre');

insert into cidade (nome, estado_id) values ('Santos', 1);
insert into cidade (nome, estado_id) values ('Belo Horizonte', 2);
insert into cidade (nome, estado_id) values ('Rio Branco', 3);

insert into restaurante (nome, taxa_frete, cozinha_id, endereco_cidade_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_bairro) values ('Tailandez', 10, 1, 1, '05887-300', 'Rua Gabriel', '1000', 'Centro');
insert into restaurante (nome, taxa_frete, cozinha_id) values ('Thai Tailandez', 10.50, 1);
insert into restaurante (nome, taxa_frete, cozinha_id) values ('Italiano', 15, 2);


insert into forma_pagamento (id, descricao) values (1, 'Cartão de crédito');
insert into forma_pagamento (id, descricao) values (2, 'Cartão de débito');
insert into forma_pagamento (id, descricao) values (3, 'Dinheiro');

insert into permissao (id, nome, descricao) values (1, 'CONSULTA_COZINHAS', 'Permite consultar cozinhas');
insert into permissao (id, nome, descricao) values (2, 'EDITAR_COZINHAS', 'Permite editar cozinhas');

insert into restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) values (1, 1), (1, 2), (1, 3), (2, 3), (3, 2), (3, 3);