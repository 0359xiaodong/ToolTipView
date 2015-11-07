package com.hrules.tooltipview;

interface ToolTipLayout {
  void addToolTipView(ToolTip toolTip, String tag, boolean show);

  boolean removeToolTipView(String tag);

  boolean showToolTipView(String tag);

  boolean hideToolTipView(String tag);

  boolean isToolTipViewShowed(String tag);

  String setToolTipViewShowed(String tag, boolean showed);
}
