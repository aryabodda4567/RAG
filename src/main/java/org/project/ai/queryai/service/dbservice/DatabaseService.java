package org.project.ai.queryai.service.dbservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DatabaseService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    //  Execute a query
    public List<Map<String, Object>> executeDQL(String sql) {
        return jdbcTemplate.queryForList(sql);
    }

    public Boolean isValidSafe(String sql) {
        return !sql.toLowerCase().contains("update") && !sql.toLowerCase().contains("delete")
                && !sql.toLowerCase().contains("insert") && !sql.toLowerCase().contains("create");
    }


}
