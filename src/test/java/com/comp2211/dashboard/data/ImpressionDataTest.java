package com.comp2211.dashboard.data;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ImpressionDataTest {

  private static final String TEST_ID = "test-id";
  private static final Timestamp TEST_IMPRESSION_DATE = new Timestamp(System.currentTimeMillis());
  private static final String TEST_AGE = "25";
  private static final byte TEST_INCOME = 1;
  private static final byte TEST_CONTEXT = 1;
  private static final BigDecimal TEST_IMPRESSION_COST = new BigDecimal("1.37272387");
  private static final boolean TEST_GENDER = true;

  private ImpressionData impressionData;

  @BeforeEach
  void setUp() {
    this.impressionData = new ImpressionData(
        TEST_ID,
        TEST_IMPRESSION_DATE,
        TEST_AGE,
        TEST_INCOME,
        TEST_CONTEXT,
        TEST_IMPRESSION_COST,
        TEST_GENDER
    );
    assertNotNull(this.impressionData);
  }

  @AfterEach
  void tearDown() {
    this.impressionData = null;
    assertNull(this.impressionData);
  }

  @Test
  @DisplayName("Test ImpressionData.getImpressionDate()")
  void getImpressionDate() {
    assertEquals(TEST_IMPRESSION_DATE, this.impressionData.getImpressionDate());
  }

  @Test
  @DisplayName("Test ImpressionData.setImpressionDate()")
  void setImpressionDate() {
    Timestamp newImpressionDate = new Timestamp(System.currentTimeMillis() - 5000);
    this.impressionData.setImpressionDate(newImpressionDate);
    assertEquals(newImpressionDate, this.impressionData.getImpressionDate());
  }

  @Test
  @DisplayName("Test ImpressionData.getAge()")
  void getAge() {
    assertEquals(TEST_AGE, this.impressionData.getAge());
  }

  @Test
  @DisplayName("Test ImpressionData.setAge()")
  void setAge() {
    String newAge = "76";
    this.impressionData.setAge(newAge);
    assertEquals(newAge, this.impressionData.getAge());
  }

  @Test
  @DisplayName("Test ImpressionData.getIncome()")
  void getIncome() {
    assertEquals(TEST_INCOME, this.impressionData.getIncome());
  }

  @Test
  @DisplayName("Test ImpressionData.setIncome()")
  void setIncome() {
    byte newIncome = 2;
    this.impressionData.setIncome(newIncome);
    assertEquals(newIncome, this.impressionData.getIncome());
  }

  @Test
  @DisplayName("Test ImpressionData.getContext()")
  void getContext() {
    assertEquals(TEST_CONTEXT, this.impressionData.getContext());
  }

  @Test
  @DisplayName("Test ImpressionData.setContext()")
  void setContext() {
    byte newContext = 2;
    this.impressionData.setContext(newContext);
    assertEquals(newContext, this.impressionData.getContext());
  }

  @Test
  @DisplayName("Test ImpressionData.getImpressionCost()")
  void getImpressionCost() {
    assertEquals(TEST_IMPRESSION_COST, this.impressionData.getImpressionCost());
  }

  @Test
  @DisplayName("Test ImpressionData.setImpressionCost()")
  void setImpressionCost() {
    BigDecimal newImpressionCost = new BigDecimal("6.23232");
    this.impressionData.setImpressionCost(newImpressionCost);
    assertEquals(newImpressionCost, this.impressionData.getImpressionCost());
  }

  @Test
  @DisplayName("Test ImpressionData.getGender()")
  void getGender() {
    assertEquals(TEST_GENDER, this.impressionData.getGender());
  }

  @Test
  @DisplayName("Test ImpressionData.setGender()")
  void setGender() {
    boolean newGender = false;
    this.impressionData.setGender(newGender);
    assertEquals(newGender, this.impressionData.getGender());
  }
}