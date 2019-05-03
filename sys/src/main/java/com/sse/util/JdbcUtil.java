package com.sse.util;

import lombok.extern.slf4j.Slf4j;

import java.sql.*;

/**
 * author pczhao
 * date  2019-04-17 15:51
 */

@Slf4j
public class JdbcUtil {

    public static Connection getConnection(String jdbcUrl, String username, String password) throws ClassNotFoundException {
        Connection con;
        Class.forName("com.mysql.jdbc.Driver");
        int tryCount = 0;
        final int MAX_TRY_COUNT = 3;
        while (tryCount < MAX_TRY_COUNT) {
            try {
                con = DriverManager.getConnection(jdbcUrl, username, password);
                return con;
            } catch (SQLException e) {
                e.printStackTrace();
                tryCount++;
                log.error("get mysql connection failed, retry to get connection,jdbcUrl: {}, tryCount: {}", jdbcUrl, tryCount);
            }
            try {
                Thread.sleep(1000 * 20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        throw new RuntimeException("MYSQL_CONNECTION_EXCEPTION");
    }

    public static void release(PreparedStatement ps, Connection conn) {
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void release(ResultSet rs, PreparedStatement ps, Connection conn) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 增删改 sql
     */
    public static int update(PreparedStatement ps, Object... param) throws SQLException {
        if (param != null) {
            for (int i = 0; i < param.length; i++) {
                ps.setObject(i + 1, param[i]);
            }
        }
        return ps.executeUpdate();
    }

    /**
     * 查询数据
     */
    public static ResultSet query(PreparedStatement ps, Object... param) throws SQLException {
        ResultSet rs;
        if (param != null) {
            for (int i = 0; i < param.length; i++) {
                ps.setObject(i + 1, param[i]);
            }
        }
        rs = ps.executeQuery();
        return rs;
    }

    private static boolean schemaExist(Connection conn, String schemaSql) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(schemaSql);
            rs = query(ps);
            if (rs.next()) {
                return rs.getInt(1) == 1;
            }
        } finally {
            release(rs, ps, null);
        }
        return false;
    }

    public static boolean databaseExist(Connection conn, String dbname) throws SQLException {
        final String sql = "SELECT COUNT(0) FROM information_schema.`SCHEMATA` WHERE schema_name='" + dbname + "'";
        return schemaExist(conn, sql);
    }

    public static boolean tableExist(Connection conn, String dbName, String tbName) throws SQLException {
        final String sql = "SELECT COUNT(0) FROM information_schema.`TABLES` t, information_schema.`SCHEMATA` d WHERE t.TABLE_NAME='" + tbName + "' AND d.SCHEMA_NAME ='" + dbName + "'";
        return schemaExist(conn, sql);
    }

    public static int createDatabase(Connection conn, String dbname) throws SQLException {
        final String sql = "CREATE DATABASE " + dbname;
        PreparedStatement ps = conn.prepareStatement(sql);
        try {
            return update(ps);
        } finally {
            release(ps, null);
        }
    }

    public static void changeDb(Connection conn, String dbName) throws SQLException {
        final String sql = "USE " + dbName;
        PreparedStatement ps = conn.prepareStatement(sql);
        try {
            ps.execute();
        } finally {
            release(ps, null);
        }
    }

    public static boolean copyTableStructure(Connection conn, String dbNameSrc, String tbNameSrc, String dbNameTarget, String tbNameTarget) throws SQLException {
        final String sql = "CREATE TABLE " + dbNameTarget + "." + tbNameTarget + " LIKE " + dbNameSrc + "." + tbNameSrc;
        PreparedStatement ps = conn.prepareStatement(sql);
        try {
            return ps.execute();
        } finally {
            release(ps, null);
        }
    }

}
