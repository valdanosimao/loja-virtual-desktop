package br.com.vs.controller;

import java.util.ArrayList;
import java.util.List;

import br.com.vs.model.Categoria;

public class CategoriaController {

	public List<Categoria> listar() {
		List<Categoria> categorias = new ArrayList<Categoria>();
		categorias.add(new Categoria(1, "Categoria de teste"));
		return categorias;
	}

}
