package com.hrules.tooltipview;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import java.util.HashMap;
import java.util.Map;

public class ToolTipRelativeLayout extends RelativeLayout
    implements ToolTipLayout, ToolTipLayoutListener {
  private static final String PREFS_FILENAME = "PrefsToolTipView";
  private static final String PREFS_PREFIX = "PrefsToolTipView_";

  private Context context;
  private Map<String, ToolTipView> toolTipViews;
  private SharedPreferences preferences;

  public ToolTipRelativeLayout(Context context) {
    this(context, null);
  }

  public ToolTipRelativeLayout(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public ToolTipRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(context);
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  public ToolTipRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr,
      int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    init(context);
  }

  private void init(Context context) {
    this.context = context.getApplicationContext();
    toolTipViews = new HashMap<>();
    preferences = context.getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE);
  }

  public void addToolTipView(ToolTip toolTip, String tag, boolean show) {
    if (toolTip == null) {
      throw new IllegalArgumentException("ToolTip must not be null");
    }
    if (tag == null || tag.trim().equals("")) {
      throw new IllegalArgumentException("Tag must not be null or empty");
    }
    if (toolTipViews.containsKey(tag)) {
      throw new IllegalArgumentException("Tag is already in use");
    }

    ToolTipView toolTipView = new ToolTipView(context);
    toolTipView.create(toolTip, tag, this);
    if (show) {
      toolTipView.setVisibility(View.VISIBLE);
    }
    addView(toolTipView);
    toolTipViews.put(tag, toolTipView);
  }

  public boolean removeToolTipView(String tag) {
    if (toolTipViews.containsKey(tag)) {
      removeView(toolTipViews.get(tag));
      return true;
    }
    return false;
  }

  public boolean showToolTipView(String tag) {
    if (toolTipViews.containsKey(tag)) {
      toolTipViews.get(tag).setVisibility(View.VISIBLE);
      return true;
    }
    return false;
  }

  public boolean hideToolTipView(String tag) {
    if (toolTipViews.containsKey(tag)) {
      toolTipViews.get(tag).setVisibility(View.GONE);
      return true;
    }
    return false;
  }

  @Override public boolean isToolTipViewShowed(String tag) {
    return preferences.getBoolean(PREFS_PREFIX + tag, false);
  }

  @Override public String setToolTipViewShowed(String tag, boolean showed) {
    String prefs = PREFS_PREFIX + tag;
    preferences.edit().putBoolean(prefs, showed).apply();
    return prefs;
  }

  @Override public void onHideOnTouch(String tag) {
    hideToolTipView(tag);
  }

  @Override public void onShowedOnTouch(String tag) {
    setToolTipViewShowed(tag, true);
  }

  @Override public void onRemoveOnTouch(String tag) {
    removeToolTipView(tag);
  }
}
