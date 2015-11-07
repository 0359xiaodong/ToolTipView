package com.hrules.tooltipview;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.TextView;
import java.util.ArrayList;

class ToolTipView extends TextView
    implements View.OnClickListener, ViewTreeObserver.OnPreDrawListener {
  private ToolTip toolTip;
  private String tag;
  private ToolTipLayoutListener toolTipLayoutListener;

  private int parentsPaddingLeft;
  private int parentsPaddingTop;
  private int parentsPaddingRight;
  private int parentsPaddingBottom;

  private float left;
  private float top;

  private Animator animator;

  public ToolTipView(Context context) {
    this(context, null);
  }

  public ToolTipView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public ToolTipView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  public ToolTipView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    init();
  }

  private void init() {
    setAlpha(0f);
  }

  @SuppressLint("RtlHardcoded") @SuppressWarnings("deprecation") private void createView() {
    // init
    if (getParent() instanceof ToolTipRelativeLayout) {
      setLayoutParams(
          new ToolTipRelativeLayout.LayoutParams(ToolTipRelativeLayout.LayoutParams.WRAP_CONTENT,
              ToolTipRelativeLayout.LayoutParams.WRAP_CONTENT));
    } else if (getParent() instanceof ToolTipFrameLayout) {
      setLayoutParams(
          new ToolTipFrameLayout.LayoutParams(ToolTipFrameLayout.LayoutParams.WRAP_CONTENT,
              ToolTipFrameLayout.LayoutParams.WRAP_CONTENT));
      measure(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
    } else {
      throw new IllegalArgumentException("Parent view is not valid");
    }

    float width = getMeasuredWidth();
    float height = getMeasuredHeight();

    setBackgroundDrawable(toolTip.getDrawable());
    if (toolTip.getColorFilter() != 0) {
      getBackground().setColorFilter(toolTip.getColorFilter(), PorterDuff.Mode.MULTIPLY);
    }

    // position
    float left;
    float top;

    if (toolTip.getAnchorView() != null) {
      getPaddings(true);
      left =
          toolTip.getAnchorView().getLeft() + (toolTip.getAnchorView().getWidth() / 2) - (width / 2)
              + (toolTip.isMove()
              && toolTip.getMoveDistance() > 0
              && toolTip.getMoveDirection() == ToolTip.DIRECTION_X_AXIS ? Math.abs(
              toolTip.getMoveDistance()) / 2 : 0) - parentsPaddingLeft;
      switch (toolTip.getGravity() & Gravity.HORIZONTAL_GRAVITY_MASK) {
        case Gravity.START:
        case Gravity.LEFT:
          left = toolTip.getAnchorView().getLeft() + (toolTip.isMove()
              && toolTip.getMoveDistance() > 0
              && toolTip.getMoveDirection() == ToolTip.DIRECTION_X_AXIS ? Math.abs(
              toolTip.getMoveDistance()) : 0) - parentsPaddingLeft;
          break;

        case Gravity.END:
        case Gravity.RIGHT:
          left = toolTip.getAnchorView().getRight() - width - parentsPaddingLeft;
          break;
      }

      top = toolTip.getAnchorView().getTop() + (toolTip.getAnchorView().getHeight() / 2) - (height
          / 2) + (toolTip.isMove()
          && toolTip.getMoveDistance() > 0
          && toolTip.getMoveDirection() == ToolTip.DIRECTION_Y_AXIS ? Math.abs(
          toolTip.getMoveDistance()) / 2 : 0) - parentsPaddingTop;
      switch (toolTip.getGravity() & Gravity.VERTICAL_GRAVITY_MASK) {
        case Gravity.TOP:
          top = toolTip.getAnchorView().getTop() + (toolTip.isMove()
              && toolTip.getMoveDistance() > 0
              && toolTip.getMoveDirection() == ToolTip.DIRECTION_Y_AXIS ? Math.abs(
              toolTip.getMoveDistance()) : 0) - parentsPaddingTop;
          break;

        case Gravity.BOTTOM:
          top = toolTip.getAnchorView().getBottom() - height - parentsPaddingTop;
          break;
      }
    } else {
      getPaddings(false);
      left = ((ViewGroup) getParent()).getLeft() + (((ViewGroup) getParent()).getWidth() / 2) - (
          width
              / 2) + (toolTip.isMove()
          && toolTip.getMoveDistance() > 0
          && toolTip.getMoveDirection() == ToolTip.DIRECTION_X_AXIS ? Math.abs(
          toolTip.getMoveDistance()) / 2 : 0) - parentsPaddingLeft;
      switch (toolTip.getGravity() & Gravity.HORIZONTAL_GRAVITY_MASK) {
        case Gravity.START:
        case Gravity.LEFT:
          left = ((ViewGroup) getParent()).getLeft() + (toolTip.isMove()
              && toolTip.getMoveDistance() > 0
              && toolTip.getMoveDirection() == ToolTip.DIRECTION_X_AXIS ? Math.abs(
              toolTip.getMoveDistance()) : 0);
          break;

        case Gravity.END:
        case Gravity.RIGHT:
          left = ((ViewGroup) getParent()).getRight()
              - width
              - parentsPaddingLeft
              - parentsPaddingRight;
          break;
      }

      top = (((ViewGroup) getParent()).getHeight() / 2) - (height / 2);
      switch (toolTip.getGravity() & Gravity.VERTICAL_GRAVITY_MASK) {
        case Gravity.TOP:
          top = ((ViewGroup) getParent()).getTop() + (toolTip.isMove()
              && toolTip.getMoveDistance() > 0
              && toolTip.getMoveDirection() == ToolTip.DIRECTION_Y_AXIS ? Math.abs(
              toolTip.getMoveDistance()) : 0);
          break;

        case Gravity.BOTTOM:
          top = ((ViewGroup) getParent()).getBottom()
              - height
              - parentsPaddingTop
              - parentsPaddingBottom;
          break;
      }
    }

    this.left = left + toolTip.getCorrectionX();
    this.top = top + toolTip.getCorrectionY();
    ObjectAnimator.ofFloat(this, "translationX", this.left).setDuration(0).start();
    ObjectAnimator.ofFloat(this, "translationY", this.top).setDuration(0).start();

    // animations
    startAnimation();

    // listeners
    setOnClickListener(this);
    getViewTreeObserver().removeOnPreDrawListener(this);
  }

  private void getPaddings(boolean anchored) {
    parentsPaddingLeft = 0;
    parentsPaddingTop = 0;
    parentsPaddingRight = 0;
    parentsPaddingBottom = 0;

    ViewParent parent = getParent();
    while (parent instanceof ViewGroup) {
      parentsPaddingLeft += ((ViewGroup) parent).getPaddingLeft();
      parentsPaddingTop += ((ViewGroup) parent).getPaddingTop();
      parentsPaddingRight += ((ViewGroup) parent).getPaddingRight();
      parentsPaddingBottom += ((ViewGroup) parent).getPaddingBottom();
      if (anchored) {
        break;
      }
      parent = parent.getParent();
    }

    if (!anchored) {
      final Rect viewDisplayFrame = new Rect();
      getWindowVisibleDisplayFrame(viewDisplayFrame);
      parentsPaddingLeft -= viewDisplayFrame.left;
      parentsPaddingTop -= viewDisplayFrame.top;
    }
  }

  void create(ToolTip toolTip, String tag, ToolTipLayoutListener toolTipLayoutListener) {
    this.toolTip = toolTip;
    this.tag = tag;
    this.toolTipLayoutListener = toolTipLayoutListener;

    // pre-init
    setText(toolTip.getText());
    setTextSize(TypedValue.COMPLEX_UNIT_PX, toolTip.getTextSize());
    setTextColor(toolTip.getTextColor());
    setPadding(toolTip.getTextPaddingLeft(), toolTip.getTextPaddingTop(),
        toolTip.getTextPaddingRight(), toolTip.getTextPaddingBottom());

    getViewTreeObserver().addOnPreDrawListener(this);
  }

  @Override public void onClick(View v) {
    if (toolTip.isHideOnTouch()) {
      toolTipLayoutListener.onHideOnTouch(tag);
    }
    if (toolTip.isShowedOnTouch()) {
      toolTipLayoutListener.onShowedOnTouch(tag);
    }
    if (toolTip.getListener() != null) {
      toolTip.getListener().onClick(tag);
    }
  }

  @Override public boolean onPreDraw() {
    createView();
    return true;
  }

  private boolean isAnimationRunning() {
    return animator != null && animator.isRunning();
  }

  private void stopAnimation() {
    if (isAnimationRunning()) {
      animator.end();
    }
  }

  private void startAnimation() {
    stopAnimation();

    animator = generateAnimation();
    animator.start();
  }

  private Animator generateAnimation() {
    ArrayList<Animator> animatorList = new ArrayList<>();

    AnimatorSet animatorSet = new AnimatorSet();

    if (toolTip.isFadeIn()) {
      ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(this, "Alpha", 0f, 1.0f);
      alphaAnimator.setStartDelay(toolTip.getFadeInDelay());
      alphaAnimator.setDuration(toolTip.getFadeInDuration());
      alphaAnimator.setInterpolator(toolTip.getFadeInInterpolator());
      animatorList.add(alphaAnimator);
    }

    if (toolTip.isMove() && toolTip.getMoveDistance() > 0) {
      ObjectAnimator moveAnimator;
      if (toolTip.getMoveDirection() == ToolTip.DIRECTION_X_AXIS) {
        moveAnimator = ObjectAnimator.ofFloat(this, "translationX", this.left,
            this.left - toolTip.getMoveDistance());
      } else {
        moveAnimator = ObjectAnimator.ofFloat(this, "translationY", this.top,
            this.top - toolTip.getMoveDistance());
      }
      moveAnimator.setRepeatCount(ObjectAnimator.INFINITE);
      moveAnimator.setRepeatMode(ObjectAnimator.REVERSE);
      moveAnimator.setStartDelay(toolTip.getMoveDelay());
      moveAnimator.setDuration(toolTip.getMoveDuration());
      moveAnimator.setInterpolator(toolTip.getMoveInterpolator());
      animatorList.add(moveAnimator);
    }

    animatorSet.playTogether(animatorList);
    return animatorSet;
  }
}
