package org.wbfd.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.wbfd.entity.Collocation;
import org.wbfd.enums.LEVEL;

public interface CollocationRepository
{
    public Collocation save(Collocation collocation) throws SQLException;

    public Collocation get(Collocation collocation) throws SQLException;

    public void delete(Collocation collocation) throws SQLException;

    public List<Collocation> getList(Integer maxResults, LEVEL level) throws SQLException;

    public void clearHits() throws SQLException;

    public List<Collocation> dumpDataBase(Connection connection) throws SQLException;
}
