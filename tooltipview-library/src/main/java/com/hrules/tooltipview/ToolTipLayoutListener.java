package com.hrules.tooltipview;

interface ToolTipLayoutListener {

  void onHideOnTouch(String tag);

  void onShowedOnTouch(String tag);

  void onRemoveOnTouch(String tag);
}
