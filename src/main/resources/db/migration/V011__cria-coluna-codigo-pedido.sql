update pedido set codigo = uuid();
alter table pedido add constraint uk_pedido_codigo unique (codigo);