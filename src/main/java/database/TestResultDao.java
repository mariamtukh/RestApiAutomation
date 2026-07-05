package database;

import java.sql.*;

public class TestResultDao {
    private static final String DB_URL = "jdbc:sqlite:test_results.db";
    public TestResultDao() {
        createTableIfNotExists();
    }

    private void createTableIfNotExists() {
        String sql = """
            CREATE TABLE IF NOT EXISTS test_results (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                test_name TEXT UNIQUE,
                status TEXT,
                execution_time DATETIME
            )""";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create table", e);
        }
    }

    public void saveResult(String testName, String status) {
        String upsert = """
            INSERT INTO test_results (test_name, status, execution_time)
            VALUES (?, ?, CURRENT_TIMESTAMP)
            ON CONFLICT(test_name) DO UPDATE SET
                status = excluded.status,
                execution_time = excluded.execution_time
            """;
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement ps = conn.prepareStatement(upsert)) {
            ps.setString(1, testName);
            ps.setString(2, status);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save result", e);
        }
    }
}
