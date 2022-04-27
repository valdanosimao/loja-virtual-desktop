package com.alura.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.alura.model.Categoria;
import com.alura.model.Produto;

public class CategoriaDAO {
	
	private Connection connection;

	public CategoriaDAO(Connection connection) {
		this.connection = connection;
	}

	/**
	 * método que lista as categorias
	 * @return
	 * @throws SQLException
	 */
	public List<Categoria> listar() throws SQLException {
		List<Categoria> categorias = new ArrayList<>();
		String sql = "SELECT ID, NOME FROM CATEGORIA";

		try (PreparedStatement pstm = connection.prepareStatement(sql)) {
			pstm.execute();

			try (ResultSet rst = pstm.getResultSet()) {
				while (rst.next()) {
					Categoria categoria = new Categoria(rst.getInt(1), rst.getString(2));

					categorias.add(categoria);
				}
			}
		}
		return categorias;
	}

	/**
	 * Métod que lista uma categoria vinculado ao Produto
	 * @return
	 * @throws SQLException
	 */
	public List<Categoria> listarComProduto() throws SQLException {
		Categoria ultima = null;
		List<Categoria> categorias = new ArrayList<>();

		String sql = "SELECT C.ID, C.NOME, P.ID, P.NOME, P.DESCRICAO " + "FROM CATEGORIA C "
				+ "INNER JOIN PRODUTO P ON C.ID = P.CATEGORIA_ID";

		try (PreparedStatement pstm = connection.prepareStatement(sql)) {
			pstm.execute();

			try (ResultSet rst = pstm.getResultSet()) {
				while (rst.next()) {
					if (ultima == null || !ultima.getNome().equals(rst.getString(2))) {
						Categoria categoria = new Categoria(rst.getInt(1), rst.getString(2));

						categorias.add(categoria);
						ultima = categoria;
					}
					Produto produto = new Produto(rst.getInt(3), rst.getString(4), rst.getString(5));
					ultima.adicionar(produto);
				}
			}
			return categorias;
		}
	}

}
