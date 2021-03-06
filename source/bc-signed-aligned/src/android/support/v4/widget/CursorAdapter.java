package android.support.v4.widget;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.FilterQueryProvider;
import android.widget.Filterable;

public abstract class CursorAdapter
  extends BaseAdapter
  implements Filterable, CursorFilter.CursorFilterClient
{
  @Deprecated
  public static final int FLAG_AUTO_REQUERY = 1;
  public static final int FLAG_REGISTER_CONTENT_OBSERVER = 2;
  protected boolean mAutoRequery;
  protected ChangeObserver mChangeObserver;
  protected Context mContext;
  protected Cursor mCursor;
  protected CursorFilter mCursorFilter;
  protected DataSetObserver mDataSetObserver;
  protected boolean mDataValid;
  protected FilterQueryProvider mFilterQueryProvider;
  protected int mRowIDColumn;
  
  @Deprecated
  public CursorAdapter(Context paramContext, Cursor paramCursor)
  {
    init(paramContext, paramCursor, 1);
  }
  
  public CursorAdapter(Context paramContext, Cursor paramCursor, int paramInt)
  {
    init(paramContext, paramCursor, paramInt);
  }
  
  public CursorAdapter(Context paramContext, Cursor paramCursor, boolean paramBoolean)
  {
    if (paramBoolean) {}
    for (int i = 1;; i = 2)
    {
      init(paramContext, paramCursor, i);
      return;
    }
  }
  
  public abstract void bindView(View paramView, Context paramContext, Cursor paramCursor);
  
  public void changeCursor(Cursor paramCursor)
  {
    paramCursor = swapCursor(paramCursor);
    if (paramCursor != null) {
      paramCursor.close();
    }
  }
  
  public CharSequence convertToString(Cursor paramCursor)
  {
    if (paramCursor == null) {}
    for (paramCursor = "";; paramCursor = paramCursor.toString()) {
      return paramCursor;
    }
  }
  
  public int getCount()
  {
    if ((this.mDataValid) && (this.mCursor != null)) {}
    for (int i = this.mCursor.getCount();; i = 0) {
      return i;
    }
  }
  
  public Cursor getCursor()
  {
    return this.mCursor;
  }
  
  public View getDropDownView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    if (this.mDataValid)
    {
      this.mCursor.moveToPosition(paramInt);
      if (paramView == null)
      {
        paramView = newDropDownView(this.mContext, this.mCursor, paramViewGroup);
        bindView(paramView, this.mContext, this.mCursor);
      }
    }
    for (;;)
    {
      return paramView;
      break;
      paramView = null;
    }
  }
  
  public Filter getFilter()
  {
    if (this.mCursorFilter == null) {
      this.mCursorFilter = new CursorFilter(this);
    }
    return this.mCursorFilter;
  }
  
  public FilterQueryProvider getFilterQueryProvider()
  {
    return this.mFilterQueryProvider;
  }
  
  public Object getItem(int paramInt)
  {
    if ((this.mDataValid) && (this.mCursor != null)) {
      this.mCursor.moveToPosition(paramInt);
    }
    for (Cursor localCursor = this.mCursor;; localCursor = null) {
      return localCursor;
    }
  }
  
  public long getItemId(int paramInt)
  {
    long l1 = 0L;
    long l2 = l1;
    if (this.mDataValid)
    {
      l2 = l1;
      if (this.mCursor != null)
      {
        l2 = l1;
        if (this.mCursor.moveToPosition(paramInt)) {
          l2 = this.mCursor.getLong(this.mRowIDColumn);
        }
      }
    }
    return l2;
  }
  
  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    if (!this.mDataValid) {
      throw new IllegalStateException("this should only be called when the cursor is valid");
    }
    if (!this.mCursor.moveToPosition(paramInt)) {
      throw new IllegalStateException("couldn't move cursor to position " + paramInt);
    }
    if (paramView == null) {
      paramView = newView(this.mContext, this.mCursor, paramViewGroup);
    }
    for (;;)
    {
      bindView(paramView, this.mContext, this.mCursor);
      return paramView;
    }
  }
  
  public boolean hasStableIds()
  {
    return true;
  }
  
  void init(Context paramContext, Cursor paramCursor, int paramInt)
  {
    boolean bool = true;
    label23:
    int i;
    if ((paramInt & 0x1) == 1)
    {
      paramInt |= 0x2;
      this.mAutoRequery = true;
      if (paramCursor == null) {
        break label140;
      }
      this.mCursor = paramCursor;
      this.mDataValid = bool;
      this.mContext = paramContext;
      if (!bool) {
        break label146;
      }
      i = paramCursor.getColumnIndexOrThrow("_id");
      label54:
      this.mRowIDColumn = i;
      if ((paramInt & 0x2) != 2) {
        break label152;
      }
      this.mChangeObserver = new ChangeObserver();
    }
    for (this.mDataSetObserver = new MyDataSetObserver(null);; this.mDataSetObserver = null)
    {
      if (bool)
      {
        if (this.mChangeObserver != null) {
          paramCursor.registerContentObserver(this.mChangeObserver);
        }
        if (this.mDataSetObserver != null) {
          paramCursor.registerDataSetObserver(this.mDataSetObserver);
        }
      }
      return;
      this.mAutoRequery = false;
      break;
      label140:
      bool = false;
      break label23;
      label146:
      i = -1;
      break label54;
      label152:
      this.mChangeObserver = null;
    }
  }
  
  @Deprecated
  protected void init(Context paramContext, Cursor paramCursor, boolean paramBoolean)
  {
    if (paramBoolean) {}
    for (int i = 1;; i = 2)
    {
      init(paramContext, paramCursor, i);
      return;
    }
  }
  
  public View newDropDownView(Context paramContext, Cursor paramCursor, ViewGroup paramViewGroup)
  {
    return newView(paramContext, paramCursor, paramViewGroup);
  }
  
  public abstract View newView(Context paramContext, Cursor paramCursor, ViewGroup paramViewGroup);
  
  protected void onContentChanged()
  {
    if ((this.mAutoRequery) && (this.mCursor != null) && (!this.mCursor.isClosed())) {
      this.mDataValid = this.mCursor.requery();
    }
  }
  
  public Cursor runQueryOnBackgroundThread(CharSequence paramCharSequence)
  {
    if (this.mFilterQueryProvider != null) {}
    for (paramCharSequence = this.mFilterQueryProvider.runQuery(paramCharSequence);; paramCharSequence = this.mCursor) {
      return paramCharSequence;
    }
  }
  
  public void setFilterQueryProvider(FilterQueryProvider paramFilterQueryProvider)
  {
    this.mFilterQueryProvider = paramFilterQueryProvider;
  }
  
  public Cursor swapCursor(Cursor paramCursor)
  {
    if (paramCursor == this.mCursor) {
      paramCursor = null;
    }
    for (;;)
    {
      return paramCursor;
      Cursor localCursor = this.mCursor;
      if (localCursor != null)
      {
        if (this.mChangeObserver != null) {
          localCursor.unregisterContentObserver(this.mChangeObserver);
        }
        if (this.mDataSetObserver != null) {
          localCursor.unregisterDataSetObserver(this.mDataSetObserver);
        }
      }
      this.mCursor = paramCursor;
      if (paramCursor != null)
      {
        if (this.mChangeObserver != null) {
          paramCursor.registerContentObserver(this.mChangeObserver);
        }
        if (this.mDataSetObserver != null) {
          paramCursor.registerDataSetObserver(this.mDataSetObserver);
        }
        this.mRowIDColumn = paramCursor.getColumnIndexOrThrow("_id");
        this.mDataValid = true;
        notifyDataSetChanged();
        paramCursor = localCursor;
      }
      else
      {
        this.mRowIDColumn = -1;
        this.mDataValid = false;
        notifyDataSetInvalidated();
        paramCursor = localCursor;
      }
    }
  }
  
  private class ChangeObserver
    extends ContentObserver
  {
    public ChangeObserver()
    {
      super();
    }
    
    public boolean deliverSelfNotifications()
    {
      return true;
    }
    
    public void onChange(boolean paramBoolean)
    {
      CursorAdapter.this.onContentChanged();
    }
  }
  
  private class MyDataSetObserver
    extends DataSetObserver
  {
    private MyDataSetObserver() {}
    
    public void onChanged()
    {
      CursorAdapter.this.mDataValid = true;
      CursorAdapter.this.notifyDataSetChanged();
    }
    
    public void onInvalidated()
    {
      CursorAdapter.this.mDataValid = false;
      CursorAdapter.this.notifyDataSetInvalidated();
    }
  }
}


/* Location:              /prj/BitcoinChecker/tools/dex-tools-2.1-SNAPSHOT/bc-signed-aligned-dex2jar.jar!/android/support/v4/widget/CursorAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */