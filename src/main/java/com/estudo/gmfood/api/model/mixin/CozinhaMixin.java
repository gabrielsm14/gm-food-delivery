package com.estudo.gmfood.api.model.mixin;

import com.estudo.gmfood.domain.model.Restaurante;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

public abstract class CozinhaMixin {

	@JsonIgnore // nao serealiza restaurantes
	private List<Restaurante> restaurantes;
}
