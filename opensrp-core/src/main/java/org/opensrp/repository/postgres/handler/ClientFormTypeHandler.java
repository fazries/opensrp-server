package org.opensrp.repository.postgres.handler;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.opensrp.domain.Client;
import org.opensrp.domain.ClientForm;
import org.postgresql.util.PGobject;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientFormTypeHandler extends BaseTypeHandler implements TypeHandler<ClientForm> {
	@Override
	public void setParameter(PreparedStatement preparedStatement, int i, ClientForm clientForm, JdbcType jdbcType) throws SQLException {
		/*try {
			if (clientForm != null) {
				String jsonString = mapper.writeValueAsString(clientForm);
				PGobject jsonObject = new PGobject();
				jsonObject.setType("jsonb");
				jsonObject.setValue(jsonString);
				preparedStatement.setObject(i, jsonObject);
			}
		}
		catch (Exception e) {
			throw new SQLException(e);
		}*/
	}

	@Override
	public ClientForm getResult(ResultSet resultSet, String s) throws SQLException {
		return null;
	}

	@Override
	public ClientForm getResult(ResultSet resultSet, int i) throws SQLException {
		return null;
	}

	@Override
	public ClientForm getResult(CallableStatement callableStatement, int i) throws SQLException {
		return null;
	}
	
	/*@Override
	public void setParameter(PreparedStatement ps, int i, Client parameter, JdbcType jdbcType) throws SQLException {
		try {
			if (parameter != null) {
				String jsonString = mapper.writeValueAsString(parameter);
				PGobject jsonObject = new PGobject();
				jsonObject.setType("jsonb");
				jsonObject.setValue(jsonString);
				ps.setObject(i, jsonObject);
			}
		}
		catch (Exception e) {
			throw new SQLException(e);
		}
	}
	
	@Override
	public Client getResult(ResultSet rs, String columnName) throws SQLException {
		try {
			String jsonString = rs.getString(columnName);
			if (StringUtils.isBlank(jsonString)) {
				return null;
			}
			return mapper.readValue(jsonString, Client.class);
		}
		catch (Exception e) {
			throw new SQLException(e);
		}
	}
	
	@Override
	public Client getResult(ResultSet rs, int columnIndex) throws SQLException {
		try {
			String jsonString = rs.getString(columnIndex);
			if (StringUtils.isBlank(jsonString)) {
				return null;
			}
			return mapper.readValue(jsonString, Client.class);
		}
		catch (Exception e) {
			throw new SQLException(e);
		}
	}
	
	@Override
	public Client getResult(CallableStatement cs, int columnIndex) throws SQLException {
		try {
			String jsonString = cs.getString(columnIndex);
			if (StringUtils.isBlank(jsonString)) {
				return null;
			}
			return mapper.readValue(jsonString, Client.class);
		}
		catch (Exception e) {
			throw new SQLException(e);
		}
	}*/

	
}