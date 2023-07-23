package com.estudo.gmfood.core.modelmapper;

import com.estudo.gmfood.api.model.EnderecoRequest;
import com.estudo.gmfood.api.model.input.ItemPedidoInput;
import com.estudo.gmfood.domain.model.Endereco;
import com.estudo.gmfood.domain.model.ItemPedido;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.createTypeMap(ItemPedidoInput.class, ItemPedido.class)
                .addMappings(mapper -> mapper.skip(ItemPedido::setId));

        TypeMap<Endereco, EnderecoRequest> enderecoToEnderecoRequestTypeMap = modelMapper.createTypeMap(Endereco.class, EnderecoRequest.class);

        enderecoToEnderecoRequestTypeMap.<String>addMapping(
                enderecoSrc -> enderecoSrc.getCidade().getEstado().getNome(), (destinoRequestDest, value) -> destinoRequestDest.getCidade().setEstado(value));

        return modelMapper;

    }
}
