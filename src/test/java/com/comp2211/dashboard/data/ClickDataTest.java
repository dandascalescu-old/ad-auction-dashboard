package com.comp2211.dashboard.data;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.junit.jupiter.api.*;

class ClickDataTest {

  private static final String TEST_ID = "test-id";
  private static final BigDecimal CLICK_COST = new BigDecimal("11.794442");
  private static final Timestamp CLICK_DATE = new Timestamp(System.currentTimeMillis());

  private ClickData clickData;

  @BeforeEach
  void setUp() {
    this.clickData = new ClickData(TEST_ID, CLICK_DATE, CLICK_COST);
  }

  @AfterEach
  void tearDown() {
    this.clickData = null;
    assertNull(this.clickData);
  }

  @Test
  @DisplayName("Test ClickData.getClickDate()")
  void getClickDate() {
    assertEquals(this.clickData.getClickDate(), CLICK_DATE);
  }

  @Test
  @DisplayName("Test ClickData.setClickDate()")
  void setClickDate() {
    Timestamp newClickDate = new Timestamp(System.currentTimeMillis() + 5000);
    this.clickData.setClickDate(newClickDate);
    assertEquals(this.clickData.getClickDate(), newClickDate);
  }

  @Test
  @DisplayName("Test ClickData.getClickCost()")
  void getClickCost() {
    assertEquals(this.clickData.getClickCost(), CLICK_COST);
  }

  @Test
  @DisplayName("Test ClickData.setClickCost()")
  void setClickCost() {
    BigDecimal newClickCost = new BigDecimal("5.93232");
    this.clickData.setClickCost(newClickCost);
    assertEquals(this.clickData.getClickCost(), newClickCost);
  }
}