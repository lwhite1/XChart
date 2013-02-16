/**
 * Copyright (C) 2013 Xeiam LLC http://xeiam.com
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.xeiam.xchart.internal.chartpart.gridstep;

import java.math.BigDecimal;

import com.xeiam.xchart.internal.chartpart.Axis.Direction;

/**
 * @author timmolter
 */
public class DecimalGridStep {

  /** the default tick mark step hint for x axis */
  private static final int DEFAULT_TICK_MARK_STEP_HINT_X = 74;

  /** the default tick mark step hint for y axis */
  private static final int DEFAULT_TICK_MARK_STEP_HINT_Y = 44;

  /**
   * Determine the grid step for the data set given the space in pixels allocated for the axis
   * 
   * @param tickSpace in plot space
   * @return
   */
  public BigDecimal getGridStepForDecimal(Direction axisDirection, double span, int tickSpace) {

    int tickMarkSpaceHint = (axisDirection == Direction.X ? DEFAULT_TICK_MARK_STEP_HINT_X : DEFAULT_TICK_MARK_STEP_HINT_Y);

    // for very short plots, squeeze some more ticks in than normal
    if (axisDirection == Direction.Y && tickSpace < 160) {
      tickMarkSpaceHint = 25;
    }

    double gridStepHint = span / tickSpace * tickMarkSpaceHint;

    // gridStepHint --> significand * 10 ** exponent
    // e.g. 724.1 --> 7.241 * 10 ** 2
    double significand = gridStepHint;
    int exponent = 0;
    if (significand == 0) {
      exponent = 1;
    } else if (significand < 1) {
      while (significand < 1) {
        significand *= 10.0;
        exponent--;
      }
    } else {
      while (significand >= 10) {
        significand /= 10.0;
        exponent++;
      }
    }

    // calculate the grid step with hint.
    BigDecimal gridStep;
    if (significand > 7.5) {
      // gridStep = 10.0 * 10 ** exponent
      gridStep = BigDecimal.TEN.multiply(pow(10, exponent));
    } else if (significand > 3.5) {
      // gridStep = 5.0 * 10 ** exponent
      gridStep = new BigDecimal(new Double(5).toString()).multiply(pow(10, exponent));
    } else if (significand > 1.5) {
      // gridStep = 2.0 * 10 ** exponent
      gridStep = new BigDecimal(new Double(2).toString()).multiply(pow(10, exponent));
    } else {
      // gridStep = 1.0 * 10 ** exponent
      gridStep = pow(10, exponent);
    }
    return gridStep;
  }

  /**
   * Calculates the value of the first argument raised to the power of the second argument.
   * 
   * @param base the base
   * @param exponent the exponent
   * @return the value <tt>a<sup>b</sup></tt> in <tt>BigDecimal</tt>
   */
  private BigDecimal pow(double base, int exponent) {

    BigDecimal value;
    if (exponent > 0) {
      value = new BigDecimal(new Double(base).toString()).pow(exponent);
    } else {
      value = BigDecimal.ONE.divide(new BigDecimal(new Double(base).toString()).pow(-exponent));
    }
    return value;
  }

  public BigDecimal getFirstPosition(final BigDecimal min, BigDecimal gridStep) {

    BigDecimal firstPosition;
    if (min.remainder(gridStep).doubleValue() <= 0.0) {
      firstPosition = min.subtract(min.remainder(gridStep));
    } else {
      firstPosition = min.subtract(min.remainder(gridStep)).add(gridStep);
    }
    return firstPosition;
  }

}
