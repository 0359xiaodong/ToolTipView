package com.hrules.tooltipview;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.IntDef;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class ToolTip {
  public static final int DIRECTION_X_AXIS = 0;
  public static final int DIRECTION_Y_AXIS = 1;
  private static final int DEFAULT_MOVE_DIRECTION = DIRECTION_Y_AXIS;

  private View anchorView;
  private int gravity;

  private boolean hideOnTouch;
  private boolean showedOnTouch;
  private boolean removeOnTouch;

  private float correctionX;
  private float correctionY;

  private Drawable drawable;
  private int colorFilter;

  private String text;
  private float textSize;
  private int textColor;
  private int textPaddingLeft;
  private int textPaddingTop;
  private int textPaddingRight;
  private int textPaddingBottom;

  private boolean fadeIn;
  private int fadeInDelay;
  private int fadeInDuration;

  private boolean move;
  private int moveDirection;
  private int moveDelay;
  private int moveDuration;
  private int moveDistance;

  private Interpolator fadeInInterpolator;
  private Interpolator moveInterpolator;

  private ToolTipListener listener;

  @Retention(RetentionPolicy.SOURCE) @IntDef({ DIRECTION_X_AXIS, DIRECTION_Y_AXIS })
  public @interface Direction {
  }

  public ToolTip(Context context) {
    Resources res = context.getResources();

    drawable = new ToolTipDrawable(context);

    text = context.getString(R.string.default_text);
    textSize = res.getDimension(R.dimen.default_text_size);
    textColor = res.getColor(R.color.default_text_color);
    textPaddingLeft = res.getDimensionPixelSize(R.dimen.default_text_padding_left);
    textPaddingTop = res.getDimensionPixelSize(R.dimen.default_text_padding_top);
    textPaddingRight = res.getDimensionPixelSize(R.dimen.default_text_padding_right);
    textPaddingBottom = res.getDimensionPixelSize(R.dimen.default_text_padding_bottom);

    fadeIn = res.getBoolean(R.bool.default_fadein);
    fadeInDuration = res.getInteger(R.integer.default_fadein_duration);
    fadeInDelay = res.getInteger(R.integer.default_fadein_delay);

    move = res.getBoolean(R.bool.default_move);
    moveDirection = DEFAULT_MOVE_DIRECTION;
    moveDelay = res.getInteger(R.integer.default_move_delay);
    moveDuration = res.getInteger(R.integer.default_move_duration);
    moveDistance = res.getInteger(R.integer.default_move_distance);

    fadeInInterpolator = new AccelerateDecelerateInterpolator();
    moveInterpolator = new AccelerateDecelerateInterpolator();
  }

  private ToolTip(Builder builder) {
    this.anchorView = builder.anchorView;
    this.gravity = builder.gravity;

    this.hideOnTouch = builder.hideOnTouch;
    this.showedOnTouch = builder.showedOnTouch;
    this.removeOnTouch = builder.removeOnTouch;

    this.correctionX = builder.correctionX;
    this.correctionY = builder.correctionY;

    this.drawable = builder.drawable;
    this.colorFilter = builder.colorFilter;

    this.text = builder.text;
    this.textSize = builder.textSize;
    this.textColor = builder.textColor;
    this.textPaddingLeft = builder.textPaddingLeft;
    this.textPaddingTop = builder.textPaddingTop;
    this.textPaddingRight = builder.textPaddingRight;
    this.textPaddingBottom = builder.textPaddingBottom;

    this.fadeIn = builder.fadeIn;
    this.fadeInDelay = builder.fadeInDelay;
    this.fadeInDuration = builder.fadeInDuration;

    this.move = builder.move;
    this.moveDirection = builder.moveDirection;
    this.moveDelay = builder.moveDelay;
    this.moveDuration = builder.moveDuration;
    this.moveDistance = builder.moveDistance;

    this.fadeInInterpolator = builder.fadeInInterpolator;
    this.moveInterpolator = builder.moveInterpolator;

    this.listener = builder.listener;
  }

  ToolTipListener getListener() {
    return listener;
  }

  public void setListener(ToolTipListener listener) {
    this.listener = listener;
  }

  public View getAnchorView() {
    return anchorView != null ? anchorView : null;
  }

  public void setAnchorView(View anchorView) {
    this.anchorView = anchorView;
  }

  public int getGravity() {
    return gravity;
  }

  public void setGravity(int gravity) {
    this.gravity = gravity;
  }

  public boolean isHideOnTouch() {
    return hideOnTouch;
  }

  public void setHideOnTouch(boolean hideOnTouch) {
    this.hideOnTouch = hideOnTouch;
  }

  public boolean isShowedOnTouch() {
    return showedOnTouch;
  }

  public void setShowedOnTouch(boolean showedOnTouch) {
    this.showedOnTouch = showedOnTouch;
  }

  public boolean isRemoveOnTouch() {
    return removeOnTouch;
  }

  public void setRemoveOnTouch(boolean removeOnTouch) {
    this.removeOnTouch = removeOnTouch;
  }

  public float getCorrectionX() {
    return correctionX;
  }

  public void setCorrectionX(float correctionX) {
    this.correctionX = correctionX;
  }

  public float getCorrectionY() {
    return correctionY;
  }

  public void setCorrectionY(float correctionY) {
    this.correctionY = correctionY;
  }

  public Drawable getDrawable() {
    return drawable;
  }

  public void setDrawable(Drawable drawable) {
    this.drawable = drawable;
  }

  public int getColorFilter() {
    return colorFilter;
  }

  public void setColorFilter(@ColorInt int colorFilter) {
    this.colorFilter = colorFilter;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public float getTextSize() {
    return textSize;
  }

  public void setTextSize(float textSize) {
    this.textSize = textSize;
  }

  public int getTextColor() {
    return textColor;
  }

  public void setTextColor(@ColorInt int textColor) {
    this.textColor = textColor;
  }

  public int getTextPaddingLeft() {
    return textPaddingLeft;
  }

  public void setTextPaddingLeft(int textPaddingLeft) {
    this.textPaddingLeft = textPaddingLeft;
  }

  public int getTextPaddingTop() {
    return textPaddingTop;
  }

  public void setTextPaddingTop(int textPaddingTop) {
    this.textPaddingTop = textPaddingTop;
  }

  public int getTextPaddingRight() {
    return textPaddingRight;
  }

  public void setTextPaddingRight(int textPaddingRight) {
    this.textPaddingRight = textPaddingRight;
  }

  public int getTextPaddingBottom() {
    return textPaddingBottom;
  }

  public void setTextPaddingBottom(int textPaddingBottom) {
    this.textPaddingBottom = textPaddingBottom;
  }

  public void setTextPadding(int textPaddingLeft, int textPaddingTop, int textPaddingRight,
      int textPaddingBottom) {
    this.textPaddingLeft = textPaddingLeft;
    this.textPaddingTop = textPaddingTop;
    this.textPaddingRight = textPaddingRight;
    this.textPaddingBottom = textPaddingBottom;
  }

  public boolean isFadeIn() {
    return fadeIn;
  }

  public void setFadeIn(boolean fadeIn) {
    this.fadeIn = fadeIn;
  }

  public int getFadeInDelay() {
    return fadeInDelay;
  }

  public void setFadeInDelay(int fadeInDelay) {
    this.fadeInDelay = fadeInDelay;
  }

  public int getFadeInDuration() {
    return fadeInDuration;
  }

  public void setFadeInDuration(int fadeInDuration) {
    this.fadeInDuration = fadeInDuration;
  }

  public boolean isMove() {
    return move;
  }

  public void setMove(boolean move) {
    this.move = move;
  }

  public int getMoveDirection() {
    return moveDirection;
  }

  public void setMoveDirection(@Direction int moveDirection) {
    this.moveDirection = moveDirection;
  }

  public int getMoveDelay() {
    return moveDelay;
  }

  public void setMoveDelay(int moveDelay) {
    this.moveDelay = moveDelay;
  }

  public int getMoveDuration() {
    return moveDuration;
  }

  public void setMoveDuration(int moveDuration) {
    this.moveDuration = moveDuration;
  }

  public int getMoveDistance() {
    return moveDistance;
  }

  public void setMoveDistance(int moveDistance) {
    this.moveDistance = moveDistance;
  }

  public Interpolator getFadeInInterpolator() {
    return fadeInInterpolator;
  }

  public void setFadeInInterpolator(Interpolator fadeInInterpolator) {
    this.fadeInInterpolator = fadeInInterpolator;
  }

  public Interpolator getMoveInterpolator() {
    return moveInterpolator;
  }

  public void setMoveInterpolator(Interpolator moveInterpolator) {
    this.moveInterpolator = moveInterpolator;
  }

  public static class Builder {
    private View anchorView;
    private int gravity;

    private boolean hideOnTouch;
    private boolean showedOnTouch;
    private boolean removeOnTouch;

    private float correctionX;
    private float correctionY;

    private Drawable drawable;
    private int colorFilter;

    private String text;
    private float textSize;
    private int textColor;
    private int textPaddingLeft;
    private int textPaddingTop;
    private int textPaddingRight;
    private int textPaddingBottom;

    private boolean fadeIn;
    private int fadeInDelay;
    private int fadeInDuration;

    private boolean move;
    private int moveDirection;
    private int moveDelay;
    private int moveDuration;
    private int moveDistance;

    private Interpolator fadeInInterpolator;
    private Interpolator moveInterpolator;

    private ToolTipListener listener;

    public Builder(Context context) {
      Resources res = context.getResources();

      drawable = new ToolTipDrawable(context);

      text = context.getString(R.string.default_text);
      textSize = res.getDimension(R.dimen.default_text_size);
      textColor = res.getColor(R.color.default_text_color);
      textPaddingLeft = res.getDimensionPixelSize(R.dimen.default_text_padding_left);
      textPaddingTop = res.getDimensionPixelSize(R.dimen.default_text_padding_top);
      textPaddingRight = res.getDimensionPixelSize(R.dimen.default_text_padding_right);
      textPaddingBottom = res.getDimensionPixelSize(R.dimen.default_text_padding_bottom);

      fadeIn = res.getBoolean(R.bool.default_fadein);
      fadeInDuration = res.getInteger(R.integer.default_fadein_duration);
      fadeInDelay = res.getInteger(R.integer.default_fadein_delay);

      move = res.getBoolean(R.bool.default_move);
      moveDirection = DEFAULT_MOVE_DIRECTION;
      moveDelay = res.getInteger(R.integer.default_move_delay);
      moveDuration = res.getInteger(R.integer.default_move_duration);
      moveDistance = res.getInteger(R.integer.default_move_distance);

      fadeInInterpolator = new AccelerateDecelerateInterpolator();
      moveInterpolator = new AccelerateDecelerateInterpolator();
    }

    public Builder anchorView(View anchorView) {
      this.anchorView = anchorView;
      return this;
    }

    public Builder gravity(int gravity) {
      this.gravity = gravity;
      return this;
    }

    public Builder hideOnTouch(boolean hideOnTouch) {
      this.hideOnTouch = hideOnTouch;
      return this;
    }

    public Builder showedOnTouch(boolean showedOnTouch) {
      this.showedOnTouch = showedOnTouch;
      return this;
    }

    public Builder removeOnTouch(boolean removeOnTouch) {
      this.removeOnTouch = removeOnTouch;
      return this;
    }

    public Builder correctionX(float correctionX) {
      this.correctionX = correctionX;
      return this;
    }

    public Builder correctionY(float correctionY) {
      this.correctionY = correctionY;
      return this;
    }

    public Builder drawable(Drawable drawable) {
      this.drawable = drawable;
      return this;
    }

    public Builder colorFilter(@ColorInt int colorFilter) {
      this.colorFilter = colorFilter;
      return this;
    }

    public Builder text(String text) {
      this.text = text;
      return this;
    }

    public Builder textSize(float textSize) {
      this.textSize = textSize;
      return this;
    }

    public Builder textColor(@ColorInt int textColor) {
      this.textColor = textColor;
      return this;
    }

    public Builder textPaddingLeft(int textPaddingLeft) {
      this.textPaddingLeft = textPaddingLeft;
      return this;
    }

    public Builder textPaddingTop(int textPaddingTop) {
      this.textPaddingTop = textPaddingTop;
      return this;
    }

    public Builder textPaddingRight(int textPaddingRight) {
      this.textPaddingRight = textPaddingRight;
      return this;
    }

    public Builder textPaddingBottom(int textPaddingBottom) {
      this.textPaddingBottom = textPaddingBottom;
      return this;
    }

    public Builder textPadding(int textPaddingLeft, int textPaddingTop, int textPaddingRight,
        int textPaddingBottom) {
      this.textPaddingLeft = textPaddingLeft;
      this.textPaddingTop = textPaddingTop;
      this.textPaddingRight = textPaddingRight;
      this.textPaddingBottom = textPaddingBottom;
      return this;
    }

    public Builder fadeIn(boolean fadeIn) {
      this.fadeIn = fadeIn;
      return this;
    }

    public Builder fadeInDuration(int fadeInDuration) {
      this.fadeInDuration = fadeInDuration;
      return this;
    }

    public Builder fadeInDelay(int fadeInDelay) {
      this.fadeInDelay = fadeInDelay;
      return this;
    }

    public Builder fadeInInterpolator(Interpolator fadeInInterpolator) {
      this.fadeInInterpolator = fadeInInterpolator;
      return this;
    }

    public Builder move(boolean move) {
      this.move = move;
      return this;
    }

    public Builder moveDirection(@Direction int moveDirection) {
      this.moveDirection = moveDirection;
      return this;
    }

    public Builder moveDelay(int moveDelay) {
      this.moveDelay = moveDelay;
      return this;
    }

    public Builder moveDuration(int moveDuration) {
      this.moveDuration = moveDuration;
      return this;
    }

    public Builder moveDistance(int moveDistance) {
      this.moveDistance = moveDistance;
      return this;
    }

    public Builder moveInterpolator(Interpolator moveInterpolator) {
      this.moveInterpolator = moveInterpolator;
      return this;
    }

    public Builder listener(ToolTipListener listener) {
      this.listener = listener;
      return this;
    }

    public ToolTip build() {
      return new ToolTip(this);
    }
  }
}
