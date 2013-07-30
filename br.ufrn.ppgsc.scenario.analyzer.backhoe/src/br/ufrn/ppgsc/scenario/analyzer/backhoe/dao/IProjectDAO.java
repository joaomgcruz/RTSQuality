package br.ufrn.ppgsc.scenario.analyzer.backhoe.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IProjectDAO {
	
	private Connection connection;
	
	public IProjectDAO() {
		try {
			connection = null;//apagado aqui
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public long getTaskNumberByRevision(long revision) {
		long task_number = 0;
		
		try {
			
			// TODO: testar
			/* SELECT id_tarefa FROM log_tarefa WHERE revision = '123' OR EXISTS
			 * (SELECT regexp_matches(log, '(^([ ]|[\n])*(Revisão|revisão|Revisao|revisao)[:| ]+123)'));
			 */
			
			PreparedStatement stmt = connection.prepareStatement(
					"SELECT tarefa.numtarefa"
					+ " FROM iproject.log_tarefa INNER JOIN iproject.tarefa"
					+ " ON tarefa.id_tarefa = log_tarefa.id_tarefa AND revision = ?");
			
			stmt.setString(1, String.valueOf(revision));
			
			ResultSet rs = stmt.executeQuery();
			
			/* Coloquei em um While, mas não vejo como iria retornar
			 * mais de uma buscando pela revisão
			 */
			while (rs.next())
				task_number = Long.valueOf(rs.getString(1));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return task_number;
	}
	
	public static void main(String[] args) {
		IProjectDAO dao = new IProjectDAO();
		System.out.println(dao.getTaskNumberByRevision(74982));
	}
	
}
