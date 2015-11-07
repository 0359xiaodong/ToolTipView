package com.hrules.tooltipview.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;
import com.hrules.tooltipview.ToolTip;
import com.hrules.tooltipview.ToolTipDrawable;
import com.hrules.tooltipview.ToolTipFrameLayout;
import com.hrules.tooltipview.ToolTipListener;
import com.hrules.tooltipview.ToolTipRelativeLayout;

public class MainActivity extends AppCompatActivity implements ToolTipListener {
  private static final String TAG_TOOLTIPVIEW_AWESOME_1 = "awesome_feature_1";
  private static final String TAG_TOOLTIPVIEW_AWESOME_2 = "awesome_feature_2";
  private static final String TAG_TOOLTIPVIEW_AWESOME_3 = "awesome_feature_3";

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    ToolTip toolTipText1 = new ToolTip.Builder(this).
        text(String.format(getString(R.string.tool_tip), 1)).
        anchorView(findViewById(R.id.awesome_1)).
        gravity(Gravity.TOP).
        correctionY(5).
        listener(this).
        hideOnTouch(true).
        build();

    ToolTip toolTipText2 = new ToolTip.Builder(this).
        text(String.format(getString(R.string.tool_tip), 2)).
        anchorView(findViewById(R.id.awesome_2)).
        gravity(Gravity.LEFT | Gravity.TOP).
        correctionX(120).
        correctionY(5).
        listener(this).
        hideOnTouch(true).
        moveDirection(ToolTip.DIRECTION_X_AXIS).
        drawable(
            new ToolTipDrawable.Builder(this).color(getResources().getColor(R.color.colorPrimary))
                .build()).
        build();

    ToolTip toolTipText3 = new ToolTip.Builder(this).
        text(String.format(getString(R.string.tool_tip), 3)).
        textColor(getResources().getColor(R.color.colorAccent)).
        anchorView(findViewById(R.id.awesome_3)).
        gravity(Gravity.TOP).
        correctionY(5).
        listener(this).
        hideOnTouch(true).
        move(true).
        moveDirection(ToolTip.DIRECTION_Y_AXIS).
        build();

    ToolTipRelativeLayout toolTipRelativeLayout =
        (ToolTipRelativeLayout) findViewById(R.id.toolTipRelativeLayout);
    toolTipRelativeLayout.addToolTipView(toolTipText1, TAG_TOOLTIPVIEW_AWESOME_1, true);
    toolTipRelativeLayout.addToolTipView(toolTipText2, TAG_TOOLTIPVIEW_AWESOME_2, true);

    ToolTipFrameLayout toolTipFrameLayout =
        (ToolTipFrameLayout) findViewById(R.id.toolTipFrameLayout);
    toolTipFrameLayout.addToolTipView(toolTipText3, TAG_TOOLTIPVIEW_AWESOME_3, true);
  }

  @Override public void onClick(String tag) {
    Toast.makeText(MainActivity.this, tag, Toast.LENGTH_SHORT).show();
    Log.d(getString(R.string.app_name), tag);
  }
}
