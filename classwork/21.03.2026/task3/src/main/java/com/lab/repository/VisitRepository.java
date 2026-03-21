package com.lab.repository;

import com.lab.model.Visit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class VisitRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void init() {
        jdbcTemplate.execute(
            "CREATE TABLE IF NOT EXISTS visits (" +
            "  id SERIAL PRIMARY KEY," +
            "  visit_time TIMESTAMP NOT NULL DEFAULT NOW()," +
            "  user_agent TEXT" +
            ")"
        );
    }

    public void save(String userAgent) {
        jdbcTemplate.update(
            "INSERT INTO visits (visit_time, user_agent) VALUES (NOW(), ?)",
            userAgent
        );
    }

    public List<Visit> findAll() {
        return jdbcTemplate.query(
            "SELECT id, visit_time, user_agent FROM visits ORDER BY visit_time DESC",
            new VisitRowMapper()
        );
    }

    private static class VisitRowMapper implements RowMapper<Visit> {
        @Override
        public Visit mapRow(ResultSet rs, int rowNum) throws SQLException {
            Visit v = new Visit();
            v.setId(rs.getLong("id"));
            v.setVisitTime(rs.getTimestamp("visit_time").toLocalDateTime());
            v.setUserAgent(rs.getString("user_agent"));
            return v;
        }
    }
}
