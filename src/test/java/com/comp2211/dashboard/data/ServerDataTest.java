package com.comp2211.dashboard.data;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Timestamp;
import org.junit.jupiter.api.*;

class ServerDataTest {

  private static final String TEST_ID = "test-id";
  private static final Timestamp TEST_ENTRY_DATE = new Timestamp(System.currentTimeMillis() - 5000);
  private static final Timestamp TEST_EXIT_DATE = new Timestamp(System.currentTimeMillis());
  private static final byte TEST_PAGES_VIEWED = 5;
  private static final boolean TEST_CONVERTED = false;

  private ServerData serverData;

  @BeforeEach
  void setUp() {
    this.serverData = new ServerData(
        TEST_ID,
        TEST_ENTRY_DATE,
        TEST_EXIT_DATE,
        TEST_PAGES_VIEWED,
        TEST_CONVERTED
    );
    assertNotNull(this.serverData);
  }

  @AfterEach
  void tearDown() {
    this.serverData = null;
    assertNull(this.serverData);
  }

  @Test
  void getEntryDate() {
    assertEquals(TEST_ENTRY_DATE, this.serverData.getEntryDate());
  }

  @Test
  void setEntryDate() {
    Timestamp newEntryDate = new Timestamp(System.currentTimeMillis());
    this.serverData.setEntryDate(newEntryDate);
    assertEquals(newEntryDate, this.serverData.getEntryDate());
  }

  @Test
  void getExitDate() {
    assertEquals(TEST_EXIT_DATE, this.serverData.getExitDate());
  }

  @Test
  void setExitDate() {
    Timestamp newExitDate = new Timestamp(System.currentTimeMillis() + 10000);
    this.serverData.setExitDate(newExitDate);
    assertEquals(newExitDate, this.serverData.getExitDate());
  }

  @Test
  void getPagesViewed() {
    assertEquals(TEST_PAGES_VIEWED, this.serverData.getPagesViewed());
  }

  @Test
  void setPagesViewed() {
    byte newPagesViewed = 10;
    this.serverData.setPagesViewed(newPagesViewed);
    assertEquals(newPagesViewed, this.serverData.getPagesViewed());
  }

  @Test
  void hasConverted() {
    assertEquals(TEST_CONVERTED, this.serverData.hasConverted());
  }

  @Test
  void setConversion() {
    boolean newConverted = true;
    this.serverData.setConversion(newConverted);
    assertEquals(newConverted, this.serverData.hasConverted());
  }
}