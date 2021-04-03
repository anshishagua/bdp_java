package com.anshishagua.mybatis.typehandler;

import com.anshishagua.object.Workflow;
import com.anshishagua.object.WorkflowParams;
import com.anshishagua.object.tasks.Task;
import com.anshishagua.object.tasks.TaskUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * WorkflowParamsTypeHandler.java
 *
 * @author lixiao
 * @date 2021-03-28
 */

public class WorkflowParamsTypeHandler extends BaseTypeHandler<Map<String, Object>> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Map<String, Object> parameter, JdbcType jdbcType) throws SQLException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            ps.setString(i, mapper.writeValueAsString(parameter));
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public  Map<String, Object> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String json = rs.getString(columnName);

        if (json == null) {
            return new HashMap<>();
        }

        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(json, Map.class);
        } catch (Exception ex) {
            throw new SQLException(ex);
        }
    }

    @Override
    public  Map<String, Object> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String json = rs.getString(columnIndex);

        if (json == null) {
            return new HashMap<>();
        }

        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.readValue(json, Map.class);
        } catch (Exception ex) {
            throw new SQLException(ex);
        }
    }

    @Override
    public Map<String, Object> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String json = cs.getString(columnIndex);

        if (json == null) {
            return new HashMap<>();
        }

        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.readValue(json, Map.class);
        } catch (Exception ex) {
            throw new SQLException(ex);
        }
    }
}
