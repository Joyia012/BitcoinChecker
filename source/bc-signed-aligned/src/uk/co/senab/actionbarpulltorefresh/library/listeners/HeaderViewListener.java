package uk.co.senab.actionbarpulltorefresh.library.listeners;

import android.view.View;

public abstract interface HeaderViewListener
{
  public static final int STATE_HIDDEN = 2;
  public static final int STATE_MINIMIZED = 1;
  public static final int STATE_VISIBLE = 0;
  
  public abstract void onStateChanged(View paramView, int paramInt);
}


/* Location:              /prj/BitcoinChecker/tools/dex-tools-2.1-SNAPSHOT/bc-signed-aligned-dex2jar.jar!/uk/co/senab/actionbarpulltorefresh/library/listeners/HeaderViewListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */