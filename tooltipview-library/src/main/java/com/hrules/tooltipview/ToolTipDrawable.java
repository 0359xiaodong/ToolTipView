package com.hrules.tooltipview;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.ColorInt;

public class ToolTipDrawable extends GradientDrawable {

  public ToolTipDrawable(Context context) {
    setColor(context.getResources().getColor(R.color.default_background_color));
    setCornerRadius(context.getResources().getDimension(R.dimen.default_background_corner_radius));
  }

  private ToolTipDrawable(Builder builder) {
    setColor(builder.color);
    setCornerRadius(builder.cornerRadius);
  }

  public static class Builder {
    private int color;
    private float cornerRadius;

    public Builder(Context context) {
      color = context.getResources().getColor(R.color.default_background_color);
      cornerRadius = context.getResources().getDimension(R.dimen.default_background_corner_radius);
    }

    public Builder color(@ColorInt int color) {
      this.color = color;
      return this;
    }

    public Builder cornerRadius(float cornerRadius) {
      this.cornerRadius = cornerRadius;
      return this;
    }

    public ToolTipDrawable build() {
      return new ToolTipDrawable(this);
    }
  }
}