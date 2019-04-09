package com.josbertlonnee;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseManager
{
	protected java.sql.Connection _sqlConnection = null;

	private ArrayList<PreparedStatement> _sqlStatement = new ArrayList<PreparedStatement>(30);

	public DatabaseManager(String databaseName, String userName, String password) throws SQLException
	{
		_sqlConnection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/" + databaseName, userName, password);
	}

	protected void createPreparedStatement(int index, String query) throws IndexOutOfBoundsException, SQLException
	{
		PreparedStatement ps = _sqlConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		
		if (_sqlStatement.size() > index) {
			if (_sqlStatement.get(index) != null)
				throw new RuntimeException("Duplicate prepared statement with index " + index);
		
			_sqlStatement.set( index, ps );
		} else {
			while(_sqlStatement.size() < index)
				_sqlStatement.add( null );
			
			_sqlStatement.add( ps );
		}
	}

	protected PreparedStatement getPStmt(int index) {
		return _sqlStatement.get(index);
	}

	public void finalizer() {
		try {
			for (PreparedStatement ps : _sqlStatement)
				if (ps != null)
					ps.close();

			if (_sqlConnection != null)
				_sqlConnection.close();

		} catch (SQLException e) {
		}
	}
}
