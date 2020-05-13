package com.comp2211.dashboard.io;

import com.comp2211.dashboard.io.DatabaseManager.Table;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MySQLManagerTest {

    @BeforeEach
    void setUp() {}

    @AfterEach
    void tearDown() {}

    @Test
    void hoursToCase() {
        Table table = Table.click_table;
        String dateTitle = "Date";
        String string6 = "WHEN TIME("+dateTitle+") BETWEEN '00:00:00' AND '05:59:59' THEN CONCAT(DATE("+dateTitle+"), ' 00:00:00') " +
                "WHEN TIME("+dateTitle+") BETWEEN '06:00:00' AND '11:59:59' THEN CONCAT(DATE("+dateTitle+"), ' 06:00:00') " +
                "WHEN TIME("+dateTitle+") BETWEEN '12:00:00' AND '17:59:59' THEN CONCAT(DATE("+dateTitle+"), ' 12:00:00') " +
                "WHEN TIME("+dateTitle+") BETWEEN '18:00:00' AND '23:59:59' THEN CONCAT(DATE("+dateTitle+"), ' 18:00:00')";
        String string12 = "WHEN TIME("+dateTitle+") BETWEEN '00:00:00' AND '11:59:59' THEN CONCAT(DATE("+dateTitle+"), ' 00:00:00') " +
                "WHEN TIME("+dateTitle+") BETWEEN '12:00:00' AND '23:59:59' THEN CONCAT(DATE("+dateTitle+"), ' 12:00:00')";
        String string24 = "WHEN TIME("+dateTitle+") BETWEEN '00:00:00' AND '23:59:59' THEN CONCAT(DATE("+dateTitle+"), ' 00:00:00')";

        assertEquals(
                string6,
                MySQLManager.hoursToCase((byte) 6, table)
        );
        assertEquals(
                string12,
                MySQLManager.hoursToCase((byte) 12, table)
        );
        assertEquals(
                string24,
                MySQLManager.hoursToCase((byte) 24, table)
        );
    }
}
