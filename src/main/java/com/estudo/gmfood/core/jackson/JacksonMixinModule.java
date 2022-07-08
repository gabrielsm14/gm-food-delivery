//package com.estudo.gmfood.core.jackson;
//
//import com.estudo.gmfood.api.model.mixin.CidadeMixin;
//import com.estudo.gmfood.api.model.mixin.CozinhaMixin;
//import com.estudo.gmfood.domain.model.Cidade;
//import com.estudo.gmfood.domain.model.Cozinha;
//import com.fasterxml.jackson.databind.module.SimpleModule;
//import org.springframework.stereotype.Component;
//
//@Component
//public class JacksonMixinModule extends SimpleModule {
//
//    public JacksonMixinModule() {
//        setMixInAnnotation(Cidade.class, CidadeMixin.class);
//        setMixInAnnotation(Cozinha.class, CozinhaMixin.class);
//    }
//}
