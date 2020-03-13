package com.comp2211.dashboard.model.data;

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
  private static final BigDecimal TEST_IMPRESSION_COST = new BigDecimal("1.37272387");

  private static final byte TEST_AGE = 1;
  private static final byte TEST_GENDER = 1;
  private static final byte TEST_INCOME = 1;
  private static final byte TEST_CONTEXT = 1;

  private ImpressionData impressionData;

  @BeforeEach
  void setUp() {
    this.impressionData = new ImpressionData(TEST_ID, TEST_IMPRESSION_DATE, TEST_IMPRESSION_COST, new Demographics(TEST_GENDER, TEST_AGE, TEST_INCOME, TEST_CONTEXT));
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
  @DisplayName("Test ImpressionData.getImpressionCost()")
  void getImpressionCost() {
    assertEquals(TEST_IMPRESSION_COST, this.impressionData.getImpressionCost());
  }

  @Test
  @DisplayName("Test ImpressionData.getGender()")
  void getGender() {
    assertEquals(Demographics.GenderTypes[TEST_GENDER], this.impressionData.getDemographics().getGender());
  }

  @Test
  @DisplayName("Test ImpressionData.getAge()")
  void getAge() {
    assertEquals(Demographics.AgeTypes[TEST_AGE], this.impressionData.getDemographics().getAge());
  }  

  @Test
  @DisplayName("Test ImpressionData.getIncome()")
  void getIncome() {
    assertEquals(Demographics.IncomeTypes[TEST_INCOME], this.impressionData.getDemographics().getIncome());
  }

  @Test
  @DisplayName("Test ImpressionData.getContext()")
  void getContext() {
    assertEquals(Demographics.ContextTypes[TEST_CONTEXT], this.impressionData.getDemographics().getContext());
  }
}