package com.mobeta.android.dslv;

import android.content.Context;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView.LayoutParams;

public class DragSortItemView
  extends ViewGroup
{
  private int mGravity = 48;
  
  public DragSortItemView(Context paramContext)
  {
    super(paramContext);
    setLayoutParams(new AbsListView.LayoutParams(-1, -2));
  }
  
  public int getGravity()
  {
    return this.mGravity;
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    View localView = getChildAt(0);
    if (localView == null) {}
    for (;;)
    {
      return;
      if (this.mGravity == 48) {
        localView.layout(0, 0, getMeasuredWidth(), localView.getMeasuredHeight());
      } else {
        localView.layout(0, getMeasuredHeight() - localView.getMeasuredHeight(), getMeasuredWidth(), getMeasuredHeight());
      }
    }
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt2);
    int j = View.MeasureSpec.getSize(paramInt1);
    paramInt2 = View.MeasureSpec.getMode(paramInt2);
    View localView = getChildAt(0);
    if (localView == null)
    {
      setMeasuredDimension(0, j);
      return;
    }
    if (localView.isLayoutRequested()) {
      measureChild(localView, paramInt1, View.MeasureSpec.makeMeasureSpec(0, 0));
    }
    paramInt1 = i;
    ViewGroup.LayoutParams localLayoutParams;
    if (paramInt2 == 0)
    {
      localLayoutParams = getLayoutParams();
      if (localLayoutParams.height <= 0) {
        break label92;
      }
    }
    label92:
    for (paramInt1 = localLayoutParams.height;; paramInt1 = localView.getMeasuredHeight())
    {
      setMeasuredDimension(j, paramInt1);
      break;
    }
  }
  
  public void setGravity(int paramInt)
  {
    this.mGravity = paramInt;
  }
}


/* Location:              /prj/BitcoinChecker/tools/dex-tools-2.1-SNAPSHOT/bc-signed-aligned-dex2jar.jar!/com/mobeta/android/dslv/DragSortItemView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */