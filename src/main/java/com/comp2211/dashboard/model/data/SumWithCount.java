package com.comp2211.dashboard.model.data;

import java.math.BigDecimal;

public class SumWithCount {
  private BigDecimal sum;
  private long count;
  
  public SumWithCount() {
    sum = BigDecimal.ZERO;
    count = 0L;
  }
  
  public BigDecimal getSum() {
    return sum;
  }
  
  public long getCount() {
    return count;
  }
  
  public void addSum(BigDecimal add) {
    sum.add(add);
  }
  
  public void incrementCount() {
    count++;
  }
} 
