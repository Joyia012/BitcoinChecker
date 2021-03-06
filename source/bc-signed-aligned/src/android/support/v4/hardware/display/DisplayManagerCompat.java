package android.support.v4.hardware.display;

import android.content.Context;
import android.os.Build.VERSION;
import android.view.Display;
import android.view.WindowManager;
import java.util.WeakHashMap;

public abstract class DisplayManagerCompat
{
  public static final String DISPLAY_CATEGORY_PRESENTATION = "android.hardware.display.category.PRESENTATION";
  private static final WeakHashMap<Context, DisplayManagerCompat> sInstances = new WeakHashMap();
  
  public static DisplayManagerCompat getInstance(Context paramContext)
  {
    synchronized (sInstances)
    {
      DisplayManagerCompat localDisplayManagerCompat = (DisplayManagerCompat)sInstances.get(paramContext);
      Object localObject = localDisplayManagerCompat;
      if (localDisplayManagerCompat == null)
      {
        if (Build.VERSION.SDK_INT >= 17)
        {
          localObject = new android/support/v4/hardware/display/DisplayManagerCompat$JellybeanMr1Impl;
          ((JellybeanMr1Impl)localObject).<init>(paramContext);
          sInstances.put(paramContext, localObject);
        }
      }
      else {
        return (DisplayManagerCompat)localObject;
      }
      localObject = new LegacyImpl(paramContext);
    }
  }
  
  public abstract Display getDisplay(int paramInt);
  
  public abstract Display[] getDisplays();
  
  public abstract Display[] getDisplays(String paramString);
  
  private static class JellybeanMr1Impl
    extends DisplayManagerCompat
  {
    private final Object mDisplayManagerObj;
    
    public JellybeanMr1Impl(Context paramContext)
    {
      this.mDisplayManagerObj = DisplayManagerJellybeanMr1.getDisplayManager(paramContext);
    }
    
    public Display getDisplay(int paramInt)
    {
      return DisplayManagerJellybeanMr1.getDisplay(this.mDisplayManagerObj, paramInt);
    }
    
    public Display[] getDisplays()
    {
      return DisplayManagerJellybeanMr1.getDisplays(this.mDisplayManagerObj);
    }
    
    public Display[] getDisplays(String paramString)
    {
      return DisplayManagerJellybeanMr1.getDisplays(this.mDisplayManagerObj, paramString);
    }
  }
  
  private static class LegacyImpl
    extends DisplayManagerCompat
  {
    private final WindowManager mWindowManager;
    
    public LegacyImpl(Context paramContext)
    {
      this.mWindowManager = ((WindowManager)paramContext.getSystemService("window"));
    }
    
    public Display getDisplay(int paramInt)
    {
      Display localDisplay = this.mWindowManager.getDefaultDisplay();
      if (localDisplay.getDisplayId() == paramInt) {}
      for (;;)
      {
        return localDisplay;
        localDisplay = null;
      }
    }
    
    public Display[] getDisplays()
    {
      return new Display[] { this.mWindowManager.getDefaultDisplay() };
    }
    
    public Display[] getDisplays(String paramString)
    {
      if (paramString == null) {}
      for (paramString = getDisplays();; paramString = new Display[0]) {
        return paramString;
      }
    }
  }
}


/* Location:              /prj/BitcoinChecker/tools/dex-tools-2.1-SNAPSHOT/bc-signed-aligned-dex2jar.jar!/android/support/v4/hardware/display/DisplayManagerCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */