package project.main.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import project.main.dao.UrlFilterDao;
import project.main.model.UrlFilter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository("urlFilterDao")
public class UrlFilterDaoImpl implements UrlFilterDao {
    @Autowired
    JdbcTemplate jdbcTemplate;

    static class UrlFilterRowMapper implements RowMapper<UrlFilter> {
        @Override
        public UrlFilter mapRow(ResultSet rs, int rowNum) throws SQLException {
            UrlFilter urlFilter = new UrlFilter(rs.getString("url"), rs.getInt("type"), rs.getInt("auth"));
            return urlFilter;
        }
    }

    @Override
    public List<UrlFilter> getFilter() {
        return jdbcTemplate.query("select * from url_filter", new UrlFilterRowMapper());
    }
}
