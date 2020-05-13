package com.comp2211.dashboard.util;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.ResultSet;
import jdk.jfr.Description;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SecurityTest {

  @BeforeEach
  void setUp() {}

  @AfterEach
  void tearDown() {}

  @Test
  @Description("34 Validation: Test password encoding and matching")
  void encodePasswordMatched() {
    String testPassword = "hilbwqeq8432h;3pf23j94p";
    byte[] testSalt = "giueofuiwe45687y38hxftxb".getBytes();

    byte[] hash = Security.encodePassword(testPassword, testSalt);

    Boolean passwordMatched = Security.matchPassword(testPassword, hash, testSalt);

    assertTrue(passwordMatched);
  }

  @Test
  @Description("35 Defect test: Test different passwords")
  void encodePasswordNotMatched() {
    String inputPassword = "hilbwqeq8432h;3pf23j94p";
    String comparePassword = "ioyfiblewnowrewrpnive";
    byte[] testSalt = "giueofuiwe45687y38hxftxb".getBytes();

    byte[] hash = Security.encodePassword(inputPassword, testSalt);

    Boolean passwordMatched = Security.matchPassword(comparePassword, hash, testSalt);

    assertFalse(passwordMatched);
  }

  @Test
  @Description("36 Defect: Test password encoding and matching must use same salt")
  void encodePasswordSaltMustMatch() {
    String testPassword = "hilbwqeq8432h;3pf23j94p";
    byte[] inputSalt = "giueofuiwe45687y38hxftxb".getBytes();
    byte[] compareSalt = "iof3w4".getBytes();

    byte[] hash = Security.encodePassword(testPassword, inputSalt);

    Boolean passwordMatched = Security.matchPassword(testPassword, hash, compareSalt);

    assertFalse(passwordMatched);
  }

  @Test
  @Description("37 Boundary test: Min number of username characters")
  void validateTextBoundaryMinNumChars() {
    String testUsername = "hded";
    Boolean passed = Security.validateText(testUsername);

    assertTrue(passed);
  }

  @Test
  @Description("38 Boundary test: Max number of username characters")
  void validateTextBoundaryMaxNumChars() {
    String testUsername = "12345678901234567890";
    Boolean passed = Security.validateText(testUsername);

    assertTrue(passed);
  }

  @Test
  @Description("39 Boundary test: One too few characters in username")
  void validateTextBoundaryTooFewChars() {
    String testUsername = "hde";
    Boolean passed = Security.validateText(testUsername);

    assertFalse(passed);
  }

  @Test
  @Description("40 Boundary test: One too many characters in username")
  void validateTextBoundaryTooManyChars() {
    String testUsername = "123456789012345678901";
    Boolean passed = Security.validateText(testUsername);

    assertFalse(passed);
  }
}