package android.support.v4.app;

import android.os.Bundle;
import android.support.v4.content.Loader;
import android.support.v4.content.Loader.OnLoadCompleteListener;
import android.support.v4.util.DebugUtils;
import android.support.v4.util.SparseArrayCompat;
import android.util.Log;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.reflect.Modifier;

class LoaderManagerImpl
  extends LoaderManager
{
  static boolean DEBUG = false;
  static final String TAG = "LoaderManager";
  FragmentActivity mActivity;
  boolean mCreatingLoader;
  final SparseArrayCompat<LoaderInfo> mInactiveLoaders = new SparseArrayCompat();
  final SparseArrayCompat<LoaderInfo> mLoaders = new SparseArrayCompat();
  boolean mRetaining;
  boolean mRetainingStarted;
  boolean mStarted;
  final String mWho;
  
  LoaderManagerImpl(String paramString, FragmentActivity paramFragmentActivity, boolean paramBoolean)
  {
    this.mWho = paramString;
    this.mActivity = paramFragmentActivity;
    this.mStarted = paramBoolean;
  }
  
  private LoaderInfo createAndInstallLoader(int paramInt, Bundle paramBundle, LoaderManager.LoaderCallbacks<Object> paramLoaderCallbacks)
  {
    try
    {
      this.mCreatingLoader = true;
      paramBundle = createLoader(paramInt, paramBundle, paramLoaderCallbacks);
      installLoader(paramBundle);
      return paramBundle;
    }
    finally
    {
      this.mCreatingLoader = false;
    }
  }
  
  private LoaderInfo createLoader(int paramInt, Bundle paramBundle, LoaderManager.LoaderCallbacks<Object> paramLoaderCallbacks)
  {
    LoaderInfo localLoaderInfo = new LoaderInfo(paramInt, paramBundle, paramLoaderCallbacks);
    localLoaderInfo.mLoader = paramLoaderCallbacks.onCreateLoader(paramInt, paramBundle);
    return localLoaderInfo;
  }
  
  public void destroyLoader(int paramInt)
  {
    if (this.mCreatingLoader) {
      throw new IllegalStateException("Called while creating a loader");
    }
    if (DEBUG) {
      Log.v("LoaderManager", "destroyLoader in " + this + " of " + paramInt);
    }
    int i = this.mLoaders.indexOfKey(paramInt);
    LoaderInfo localLoaderInfo;
    if (i >= 0)
    {
      localLoaderInfo = (LoaderInfo)this.mLoaders.valueAt(i);
      this.mLoaders.removeAt(i);
      localLoaderInfo.destroy();
    }
    paramInt = this.mInactiveLoaders.indexOfKey(paramInt);
    if (paramInt >= 0)
    {
      localLoaderInfo = (LoaderInfo)this.mInactiveLoaders.valueAt(paramInt);
      this.mInactiveLoaders.removeAt(paramInt);
      localLoaderInfo.destroy();
    }
    if ((this.mActivity != null) && (!hasRunningLoaders())) {
      this.mActivity.mFragments.startPendingDeferredFragments();
    }
  }
  
  void doDestroy()
  {
    if (!this.mRetaining)
    {
      if (DEBUG) {
        Log.v("LoaderManager", "Destroying Active in " + this);
      }
      for (i = this.mLoaders.size() - 1; i >= 0; i--) {
        ((LoaderInfo)this.mLoaders.valueAt(i)).destroy();
      }
      this.mLoaders.clear();
    }
    if (DEBUG) {
      Log.v("LoaderManager", "Destroying Inactive in " + this);
    }
    for (int i = this.mInactiveLoaders.size() - 1; i >= 0; i--) {
      ((LoaderInfo)this.mInactiveLoaders.valueAt(i)).destroy();
    }
    this.mInactiveLoaders.clear();
  }
  
  void doReportNextStart()
  {
    for (int i = this.mLoaders.size() - 1; i >= 0; i--) {
      ((LoaderInfo)this.mLoaders.valueAt(i)).mReportNextStart = true;
    }
  }
  
  void doReportStart()
  {
    for (int i = this.mLoaders.size() - 1; i >= 0; i--) {
      ((LoaderInfo)this.mLoaders.valueAt(i)).reportStart();
    }
  }
  
  void doRetain()
  {
    if (DEBUG) {
      Log.v("LoaderManager", "Retaining in " + this);
    }
    if (!this.mStarted)
    {
      RuntimeException localRuntimeException = new RuntimeException("here");
      localRuntimeException.fillInStackTrace();
      Log.w("LoaderManager", "Called doRetain when not started: " + this, localRuntimeException);
    }
    for (;;)
    {
      return;
      this.mRetaining = true;
      this.mStarted = false;
      for (int i = this.mLoaders.size() - 1; i >= 0; i--) {
        ((LoaderInfo)this.mLoaders.valueAt(i)).retain();
      }
    }
  }
  
  void doStart()
  {
    if (DEBUG) {
      Log.v("LoaderManager", "Starting in " + this);
    }
    if (this.mStarted)
    {
      RuntimeException localRuntimeException = new RuntimeException("here");
      localRuntimeException.fillInStackTrace();
      Log.w("LoaderManager", "Called doStart when already started: " + this, localRuntimeException);
    }
    for (;;)
    {
      return;
      this.mStarted = true;
      for (int i = this.mLoaders.size() - 1; i >= 0; i--) {
        ((LoaderInfo)this.mLoaders.valueAt(i)).start();
      }
    }
  }
  
  void doStop()
  {
    if (DEBUG) {
      Log.v("LoaderManager", "Stopping in " + this);
    }
    if (!this.mStarted)
    {
      RuntimeException localRuntimeException = new RuntimeException("here");
      localRuntimeException.fillInStackTrace();
      Log.w("LoaderManager", "Called doStop when not started: " + this, localRuntimeException);
    }
    for (;;)
    {
      return;
      for (int i = this.mLoaders.size() - 1; i >= 0; i--) {
        ((LoaderInfo)this.mLoaders.valueAt(i)).stop();
      }
      this.mStarted = false;
    }
  }
  
  public void dump(String paramString, FileDescriptor paramFileDescriptor, PrintWriter paramPrintWriter, String[] paramArrayOfString)
  {
    Object localObject1;
    int i;
    Object localObject2;
    if (this.mLoaders.size() > 0)
    {
      paramPrintWriter.print(paramString);
      paramPrintWriter.println("Active Loaders:");
      localObject1 = paramString + "    ";
      for (i = 0; i < this.mLoaders.size(); i++)
      {
        localObject2 = (LoaderInfo)this.mLoaders.valueAt(i);
        paramPrintWriter.print(paramString);
        paramPrintWriter.print("  #");
        paramPrintWriter.print(this.mLoaders.keyAt(i));
        paramPrintWriter.print(": ");
        paramPrintWriter.println(((LoaderInfo)localObject2).toString());
        ((LoaderInfo)localObject2).dump((String)localObject1, paramFileDescriptor, paramPrintWriter, paramArrayOfString);
      }
    }
    if (this.mInactiveLoaders.size() > 0)
    {
      paramPrintWriter.print(paramString);
      paramPrintWriter.println("Inactive Loaders:");
      localObject2 = paramString + "    ";
      for (i = 0; i < this.mInactiveLoaders.size(); i++)
      {
        localObject1 = (LoaderInfo)this.mInactiveLoaders.valueAt(i);
        paramPrintWriter.print(paramString);
        paramPrintWriter.print("  #");
        paramPrintWriter.print(this.mInactiveLoaders.keyAt(i));
        paramPrintWriter.print(": ");
        paramPrintWriter.println(((LoaderInfo)localObject1).toString());
        ((LoaderInfo)localObject1).dump((String)localObject2, paramFileDescriptor, paramPrintWriter, paramArrayOfString);
      }
    }
  }
  
  void finishRetain()
  {
    if (this.mRetaining)
    {
      if (DEBUG) {
        Log.v("LoaderManager", "Finished Retaining in " + this);
      }
      this.mRetaining = false;
      for (int i = this.mLoaders.size() - 1; i >= 0; i--) {
        ((LoaderInfo)this.mLoaders.valueAt(i)).finishRetain();
      }
    }
  }
  
  public <D> Loader<D> getLoader(int paramInt)
  {
    if (this.mCreatingLoader) {
      throw new IllegalStateException("Called while creating a loader");
    }
    Object localObject = (LoaderInfo)this.mLoaders.get(paramInt);
    if (localObject != null) {
      if (((LoaderInfo)localObject).mPendingLoader != null) {
        localObject = ((LoaderInfo)localObject).mPendingLoader.mLoader;
      }
    }
    for (;;)
    {
      return (Loader<D>)localObject;
      localObject = ((LoaderInfo)localObject).mLoader;
      continue;
      localObject = null;
    }
  }
  
  public boolean hasRunningLoaders()
  {
    boolean bool1 = false;
    int i = this.mLoaders.size();
    int j = 0;
    if (j < i)
    {
      LoaderInfo localLoaderInfo = (LoaderInfo)this.mLoaders.valueAt(j);
      if ((localLoaderInfo.mStarted) && (!localLoaderInfo.mDeliveredData)) {}
      for (boolean bool2 = true;; bool2 = false)
      {
        bool1 |= bool2;
        j++;
        break;
      }
    }
    return bool1;
  }
  
  public <D> Loader<D> initLoader(int paramInt, Bundle paramBundle, LoaderManager.LoaderCallbacks<D> paramLoaderCallbacks)
  {
    if (this.mCreatingLoader) {
      throw new IllegalStateException("Called while creating a loader");
    }
    LoaderInfo localLoaderInfo = (LoaderInfo)this.mLoaders.get(paramInt);
    if (DEBUG) {
      Log.v("LoaderManager", "initLoader in " + this + ": args=" + paramBundle);
    }
    if (localLoaderInfo == null)
    {
      paramLoaderCallbacks = createAndInstallLoader(paramInt, paramBundle, paramLoaderCallbacks);
      paramBundle = paramLoaderCallbacks;
      if (DEBUG) {
        Log.v("LoaderManager", "  Created new loader " + paramLoaderCallbacks);
      }
    }
    for (paramBundle = paramLoaderCallbacks;; paramBundle = localLoaderInfo)
    {
      if ((paramBundle.mHaveData) && (this.mStarted)) {
        paramBundle.callOnLoadFinished(paramBundle.mLoader, paramBundle.mData);
      }
      return paramBundle.mLoader;
      if (DEBUG) {
        Log.v("LoaderManager", "  Re-using existing loader " + localLoaderInfo);
      }
      localLoaderInfo.mCallbacks = paramLoaderCallbacks;
    }
  }
  
  void installLoader(LoaderInfo paramLoaderInfo)
  {
    this.mLoaders.put(paramLoaderInfo.mId, paramLoaderInfo);
    if (this.mStarted) {
      paramLoaderInfo.start();
    }
  }
  
  public <D> Loader<D> restartLoader(int paramInt, Bundle paramBundle, LoaderManager.LoaderCallbacks<D> paramLoaderCallbacks)
  {
    if (this.mCreatingLoader) {
      throw new IllegalStateException("Called while creating a loader");
    }
    LoaderInfo localLoaderInfo1 = (LoaderInfo)this.mLoaders.get(paramInt);
    if (DEBUG) {
      Log.v("LoaderManager", "restartLoader in " + this + ": args=" + paramBundle);
    }
    if (localLoaderInfo1 != null)
    {
      LoaderInfo localLoaderInfo2 = (LoaderInfo)this.mInactiveLoaders.get(paramInt);
      if (localLoaderInfo2 == null) {
        break label314;
      }
      if (!localLoaderInfo1.mHaveData) {
        break label177;
      }
      if (DEBUG) {
        Log.v("LoaderManager", "  Removing last inactive loader: " + localLoaderInfo1);
      }
      localLoaderInfo2.mDeliveredData = false;
      localLoaderInfo2.destroy();
      localLoaderInfo1.mLoader.abandon();
      this.mInactiveLoaders.put(paramInt, localLoaderInfo1);
    }
    for (;;)
    {
      for (paramBundle = createAndInstallLoader(paramInt, paramBundle, paramLoaderCallbacks).mLoader;; paramBundle = localLoaderInfo1.mPendingLoader.mLoader)
      {
        return paramBundle;
        label177:
        if (!localLoaderInfo1.mStarted)
        {
          if (DEBUG) {
            Log.v("LoaderManager", "  Current loader is stopped; replacing");
          }
          this.mLoaders.put(paramInt, null);
          localLoaderInfo1.destroy();
          break;
        }
        if (localLoaderInfo1.mPendingLoader != null)
        {
          if (DEBUG) {
            Log.v("LoaderManager", "  Removing pending loader: " + localLoaderInfo1.mPendingLoader);
          }
          localLoaderInfo1.mPendingLoader.destroy();
          localLoaderInfo1.mPendingLoader = null;
        }
        if (DEBUG) {
          Log.v("LoaderManager", "  Enqueuing as new pending loader");
        }
        localLoaderInfo1.mPendingLoader = createLoader(paramInt, paramBundle, paramLoaderCallbacks);
      }
      label314:
      if (DEBUG) {
        Log.v("LoaderManager", "  Making last loader inactive: " + localLoaderInfo1);
      }
      localLoaderInfo1.mLoader.abandon();
      this.mInactiveLoaders.put(paramInt, localLoaderInfo1);
    }
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder(128);
    localStringBuilder.append("LoaderManager{");
    localStringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
    localStringBuilder.append(" in ");
    DebugUtils.buildShortClassTag(this.mActivity, localStringBuilder);
    localStringBuilder.append("}}");
    return localStringBuilder.toString();
  }
  
  void updateActivity(FragmentActivity paramFragmentActivity)
  {
    this.mActivity = paramFragmentActivity;
  }
  
  final class LoaderInfo
    implements Loader.OnLoadCompleteListener<Object>
  {
    final Bundle mArgs;
    LoaderManager.LoaderCallbacks<Object> mCallbacks;
    Object mData;
    boolean mDeliveredData;
    boolean mDestroyed;
    boolean mHaveData;
    final int mId;
    boolean mListenerRegistered;
    Loader<Object> mLoader;
    LoaderInfo mPendingLoader;
    boolean mReportNextStart;
    boolean mRetaining;
    boolean mRetainingStarted;
    boolean mStarted;
    
    public LoaderInfo(Bundle paramBundle, LoaderManager.LoaderCallbacks<Object> paramLoaderCallbacks)
    {
      this.mId = paramBundle;
      this.mArgs = paramLoaderCallbacks;
      LoaderManager.LoaderCallbacks localLoaderCallbacks;
      this.mCallbacks = localLoaderCallbacks;
    }
    
    void callOnLoadFinished(Loader<Object> paramLoader, Object paramObject)
    {
      String str;
      if (this.mCallbacks != null)
      {
        str = null;
        if (LoaderManagerImpl.this.mActivity != null)
        {
          str = LoaderManagerImpl.this.mActivity.mFragments.mNoTransactionsBecause;
          LoaderManagerImpl.this.mActivity.mFragments.mNoTransactionsBecause = "onLoadFinished";
        }
      }
      try
      {
        if (LoaderManagerImpl.DEBUG)
        {
          StringBuilder localStringBuilder = new java/lang/StringBuilder;
          localStringBuilder.<init>();
          Log.v("LoaderManager", "  onLoadFinished in " + paramLoader + ": " + paramLoader.dataToString(paramObject));
        }
        this.mCallbacks.onLoadFinished(paramLoader, paramObject);
        if (LoaderManagerImpl.this.mActivity != null) {
          LoaderManagerImpl.this.mActivity.mFragments.mNoTransactionsBecause = str;
        }
        this.mDeliveredData = true;
        return;
      }
      finally
      {
        if (LoaderManagerImpl.this.mActivity != null) {
          LoaderManagerImpl.this.mActivity.mFragments.mNoTransactionsBecause = str;
        }
      }
    }
    
    void destroy()
    {
      if (LoaderManagerImpl.DEBUG) {
        Log.v("LoaderManager", "  Destroying: " + this);
      }
      this.mDestroyed = true;
      boolean bool = this.mDeliveredData;
      this.mDeliveredData = false;
      String str;
      if ((this.mCallbacks != null) && (this.mLoader != null) && (this.mHaveData) && (bool))
      {
        if (LoaderManagerImpl.DEBUG) {
          Log.v("LoaderManager", "  Reseting: " + this);
        }
        str = null;
        if (LoaderManagerImpl.this.mActivity != null)
        {
          str = LoaderManagerImpl.this.mActivity.mFragments.mNoTransactionsBecause;
          LoaderManagerImpl.this.mActivity.mFragments.mNoTransactionsBecause = "onLoaderReset";
        }
      }
      try
      {
        this.mCallbacks.onLoaderReset(this.mLoader);
        if (LoaderManagerImpl.this.mActivity != null) {
          LoaderManagerImpl.this.mActivity.mFragments.mNoTransactionsBecause = str;
        }
        this.mCallbacks = null;
        this.mData = null;
        this.mHaveData = false;
        if (this.mLoader != null)
        {
          if (this.mListenerRegistered)
          {
            this.mListenerRegistered = false;
            this.mLoader.unregisterListener(this);
          }
          this.mLoader.reset();
        }
        if (this.mPendingLoader != null) {
          this.mPendingLoader.destroy();
        }
        return;
      }
      finally
      {
        if (LoaderManagerImpl.this.mActivity != null) {
          LoaderManagerImpl.this.mActivity.mFragments.mNoTransactionsBecause = str;
        }
      }
    }
    
    public void dump(String paramString, FileDescriptor paramFileDescriptor, PrintWriter paramPrintWriter, String[] paramArrayOfString)
    {
      paramPrintWriter.print(paramString);
      paramPrintWriter.print("mId=");
      paramPrintWriter.print(this.mId);
      paramPrintWriter.print(" mArgs=");
      paramPrintWriter.println(this.mArgs);
      paramPrintWriter.print(paramString);
      paramPrintWriter.print("mCallbacks=");
      paramPrintWriter.println(this.mCallbacks);
      paramPrintWriter.print(paramString);
      paramPrintWriter.print("mLoader=");
      paramPrintWriter.println(this.mLoader);
      if (this.mLoader != null) {
        this.mLoader.dump(paramString + "  ", paramFileDescriptor, paramPrintWriter, paramArrayOfString);
      }
      if ((this.mHaveData) || (this.mDeliveredData))
      {
        paramPrintWriter.print(paramString);
        paramPrintWriter.print("mHaveData=");
        paramPrintWriter.print(this.mHaveData);
        paramPrintWriter.print("  mDeliveredData=");
        paramPrintWriter.println(this.mDeliveredData);
        paramPrintWriter.print(paramString);
        paramPrintWriter.print("mData=");
        paramPrintWriter.println(this.mData);
      }
      paramPrintWriter.print(paramString);
      paramPrintWriter.print("mStarted=");
      paramPrintWriter.print(this.mStarted);
      paramPrintWriter.print(" mReportNextStart=");
      paramPrintWriter.print(this.mReportNextStart);
      paramPrintWriter.print(" mDestroyed=");
      paramPrintWriter.println(this.mDestroyed);
      paramPrintWriter.print(paramString);
      paramPrintWriter.print("mRetaining=");
      paramPrintWriter.print(this.mRetaining);
      paramPrintWriter.print(" mRetainingStarted=");
      paramPrintWriter.print(this.mRetainingStarted);
      paramPrintWriter.print(" mListenerRegistered=");
      paramPrintWriter.println(this.mListenerRegistered);
      if (this.mPendingLoader != null)
      {
        paramPrintWriter.print(paramString);
        paramPrintWriter.println("Pending Loader ");
        paramPrintWriter.print(this.mPendingLoader);
        paramPrintWriter.println(":");
        this.mPendingLoader.dump(paramString + "  ", paramFileDescriptor, paramPrintWriter, paramArrayOfString);
      }
    }
    
    void finishRetain()
    {
      if (this.mRetaining)
      {
        if (LoaderManagerImpl.DEBUG) {
          Log.v("LoaderManager", "  Finished Retaining: " + this);
        }
        this.mRetaining = false;
        if ((this.mStarted != this.mRetainingStarted) && (!this.mStarted)) {
          stop();
        }
      }
      if ((this.mStarted) && (this.mHaveData) && (!this.mReportNextStart)) {
        callOnLoadFinished(this.mLoader, this.mData);
      }
    }
    
    public void onLoadComplete(Loader<Object> paramLoader, Object paramObject)
    {
      if (LoaderManagerImpl.DEBUG) {
        Log.v("LoaderManager", "onLoadComplete: " + this);
      }
      if (this.mDestroyed) {
        if (LoaderManagerImpl.DEBUG) {
          Log.v("LoaderManager", "  Ignoring load complete -- destroyed");
        }
      }
      for (;;)
      {
        return;
        if (LoaderManagerImpl.this.mLoaders.get(this.mId) != this)
        {
          if (LoaderManagerImpl.DEBUG) {
            Log.v("LoaderManager", "  Ignoring load complete -- not active");
          }
        }
        else
        {
          LoaderInfo localLoaderInfo = this.mPendingLoader;
          if (localLoaderInfo != null)
          {
            if (LoaderManagerImpl.DEBUG) {
              Log.v("LoaderManager", "  Switching to pending loader: " + localLoaderInfo);
            }
            this.mPendingLoader = null;
            LoaderManagerImpl.this.mLoaders.put(this.mId, null);
            destroy();
            LoaderManagerImpl.this.installLoader(localLoaderInfo);
          }
          else
          {
            if ((this.mData != paramObject) || (!this.mHaveData))
            {
              this.mData = paramObject;
              this.mHaveData = true;
              if (this.mStarted) {
                callOnLoadFinished(paramLoader, paramObject);
              }
            }
            paramLoader = (LoaderInfo)LoaderManagerImpl.this.mInactiveLoaders.get(this.mId);
            if ((paramLoader != null) && (paramLoader != this))
            {
              paramLoader.mDeliveredData = false;
              paramLoader.destroy();
              LoaderManagerImpl.this.mInactiveLoaders.remove(this.mId);
            }
            if ((LoaderManagerImpl.this.mActivity != null) && (!LoaderManagerImpl.this.hasRunningLoaders())) {
              LoaderManagerImpl.this.mActivity.mFragments.startPendingDeferredFragments();
            }
          }
        }
      }
    }
    
    void reportStart()
    {
      if ((this.mStarted) && (this.mReportNextStart))
      {
        this.mReportNextStart = false;
        if (this.mHaveData) {
          callOnLoadFinished(this.mLoader, this.mData);
        }
      }
    }
    
    void retain()
    {
      if (LoaderManagerImpl.DEBUG) {
        Log.v("LoaderManager", "  Retaining: " + this);
      }
      this.mRetaining = true;
      this.mRetainingStarted = this.mStarted;
      this.mStarted = false;
      this.mCallbacks = null;
    }
    
    void start()
    {
      if ((this.mRetaining) && (this.mRetainingStarted)) {
        this.mStarted = true;
      }
      for (;;)
      {
        return;
        if (!this.mStarted)
        {
          this.mStarted = true;
          if (LoaderManagerImpl.DEBUG) {
            Log.v("LoaderManager", "  Starting: " + this);
          }
          if ((this.mLoader == null) && (this.mCallbacks != null)) {
            this.mLoader = this.mCallbacks.onCreateLoader(this.mId, this.mArgs);
          }
          if (this.mLoader != null)
          {
            if ((this.mLoader.getClass().isMemberClass()) && (!Modifier.isStatic(this.mLoader.getClass().getModifiers()))) {
              throw new IllegalArgumentException("Object returned from onCreateLoader must not be a non-static inner member class: " + this.mLoader);
            }
            if (!this.mListenerRegistered)
            {
              this.mLoader.registerListener(this.mId, this);
              this.mListenerRegistered = true;
            }
            this.mLoader.startLoading();
          }
        }
      }
    }
    
    void stop()
    {
      if (LoaderManagerImpl.DEBUG) {
        Log.v("LoaderManager", "  Stopping: " + this);
      }
      this.mStarted = false;
      if ((!this.mRetaining) && (this.mLoader != null) && (this.mListenerRegistered))
      {
        this.mListenerRegistered = false;
        this.mLoader.unregisterListener(this);
        this.mLoader.stopLoading();
      }
    }
    
    public String toString()
    {
      StringBuilder localStringBuilder = new StringBuilder(64);
      localStringBuilder.append("LoaderInfo{");
      localStringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
      localStringBuilder.append(" #");
      localStringBuilder.append(this.mId);
      localStringBuilder.append(" : ");
      DebugUtils.buildShortClassTag(this.mLoader, localStringBuilder);
      localStringBuilder.append("}}");
      return localStringBuilder.toString();
    }
  }
}


/* Location:              /prj/BitcoinChecker/tools/dex-tools-2.1-SNAPSHOT/bc-signed-aligned-dex2jar.jar!/android/support/v4/app/LoaderManagerImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */