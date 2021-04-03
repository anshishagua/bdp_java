package com.anshishagua.mybatis.typehandler;

import com.anshishagua.constants.WorkflowStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * WorkflowStatusTypeHandler.java
 *
 * @author lixiao
 * @date 2021-03-28
 */

public class WorkflowStatusTypeHandler extends BaseTypeHandler<WorkflowStatus> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, WorkflowStatus parameter, JdbcType jdbcType) throws SQLException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            ps.setString(i, mapper.writeValueAsString(parameter));
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public  WorkflowStatus getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String value = rs.getString(columnName);

        if (value == null) {
            return null;
        }

        return WorkflowStatus.parse(value);
    }

    @Override
    public  WorkflowStatus getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String value = rs.getString(columnIndex);

        if (value == null) {
            return null;
        }

        return WorkflowStatus.parse(value);
    }

    @Override
    public WorkflowStatus getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String value = cs.getString(columnIndex);

        if (value == null) {
            return null;
        }

        return WorkflowStatus.parse(value);
    }
}
