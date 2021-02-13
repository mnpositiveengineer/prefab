package com.prefab.sales.database;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

class DbManagerTestSuite {

    @Test
    public void testConnect() throws SQLException {
        DbManager dbManager = DbManager.getInstance();
        Assertions.assertNotNull(dbManager.getConnection());
    }
}