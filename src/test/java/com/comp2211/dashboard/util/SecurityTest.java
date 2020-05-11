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
  @Description("Validation: Test password encoding and matching")
  void encodePasswordMatched() {
    String testPassword = "hilbwqeq8432h;3pf23j94p";
    byte[] testSalt = "giueofuiwe45687y38hxftxb".getBytes();

    ResultSet rs = null;
    byte[] hash = Security.encodePassword(testPassword, testSalt);

    Boolean passwordMatched = Security.matchPassword(testPassword, hash, testSalt);

    assertTrue(passwordMatched);
  }

  @Test
  @Description("Defect test: Test different passwords")
  void encodePasswordNotMatched() {
    String inputPassword = "hilbwqeq8432h;3pf23j94p";
    String comparePassword = "ioyfiblewnowrewrpnive";
    byte[] testSalt = "giueofuiwe45687y38hxftxb".getBytes();

    ResultSet rs = null;
    byte[] hash = Security.encodePassword(inputPassword, testSalt);

    Boolean passwordMatched = Security.matchPassword(comparePassword, hash, testSalt);

    assertFalse(passwordMatched);
  }

  @Test
  @Description("Validation: Test password encoding and matching must use same salt")
  void encodePasswordSaltMustMatch() {
    String testPassword = "hilbwqeq8432h;3pf23j94p";
    byte[] inputSalt = "giueofuiwe45687y38hxftxb".getBytes();
    byte[] compareSalt = "iof3w4".getBytes();

    ResultSet rs = null;
    byte[] hash = Security.encodePassword(testPassword, inputSalt);

    Boolean passwordMatched = Security.matchPassword(testPassword, hash, compareSalt);

    assertFalse(passwordMatched);
  }

  @Test
  void validateText() {}
}