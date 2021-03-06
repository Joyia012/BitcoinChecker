package com.nineoldandroids.view;

import android.view.View;
import android.view.animation.Interpolator;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.Animator.AnimatorListener;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.animation.ValueAnimator.AnimatorUpdateListener;
import com.nineoldandroids.view.animation.AnimatorProxy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

class ViewPropertyAnimatorPreHC
  extends ViewPropertyAnimator
{
  private static final int ALPHA = 512;
  private static final int NONE = 0;
  private static final int ROTATION = 16;
  private static final int ROTATION_X = 32;
  private static final int ROTATION_Y = 64;
  private static final int SCALE_X = 4;
  private static final int SCALE_Y = 8;
  private static final int TRANSFORM_MASK = 511;
  private static final int TRANSLATION_X = 1;
  private static final int TRANSLATION_Y = 2;
  private static final int X = 128;
  private static final int Y = 256;
  private Runnable mAnimationStarter = new Runnable()
  {
    public void run()
    {
      ViewPropertyAnimatorPreHC.this.startAnimation();
    }
  };
  private AnimatorEventListener mAnimatorEventListener = new AnimatorEventListener(null);
  private HashMap<Animator, PropertyBundle> mAnimatorMap = new HashMap();
  private long mDuration;
  private boolean mDurationSet = false;
  private Interpolator mInterpolator;
  private boolean mInterpolatorSet = false;
  private Animator.AnimatorListener mListener = null;
  ArrayList<NameValuesHolder> mPendingAnimations = new ArrayList();
  private final AnimatorProxy mProxy;
  private long mStartDelay = 0L;
  private boolean mStartDelaySet = false;
  private final WeakReference<View> mView;
  
  ViewPropertyAnimatorPreHC(View paramView)
  {
    this.mView = new WeakReference(paramView);
    this.mProxy = AnimatorProxy.wrap(paramView);
  }
  
  private void animateProperty(int paramInt, float paramFloat)
  {
    float f = getValue(paramInt);
    animatePropertyBy(paramInt, f, paramFloat - f);
  }
  
  private void animatePropertyBy(int paramInt, float paramFloat)
  {
    animatePropertyBy(paramInt, getValue(paramInt), paramFloat);
  }
  
  private void animatePropertyBy(int paramInt, float paramFloat1, float paramFloat2)
  {
    if (this.mAnimatorMap.size() > 0)
    {
      Object localObject1 = null;
      Iterator localIterator = this.mAnimatorMap.keySet().iterator();
      PropertyBundle localPropertyBundle;
      do
      {
        localObject2 = localObject1;
        if (!localIterator.hasNext()) {
          break;
        }
        localObject2 = (Animator)localIterator.next();
        localPropertyBundle = (PropertyBundle)this.mAnimatorMap.get(localObject2);
      } while ((!localPropertyBundle.cancel(paramInt)) || (localPropertyBundle.mPropertyMask != 0));
      if (localObject2 != null) {
        ((Animator)localObject2).cancel();
      }
    }
    Object localObject2 = new NameValuesHolder(paramInt, paramFloat1, paramFloat2);
    this.mPendingAnimations.add(localObject2);
    localObject2 = (View)this.mView.get();
    if (localObject2 != null)
    {
      ((View)localObject2).removeCallbacks(this.mAnimationStarter);
      ((View)localObject2).post(this.mAnimationStarter);
    }
  }
  
  private float getValue(int paramInt)
  {
    float f;
    switch (paramInt)
    {
    default: 
      f = 0.0F;
    }
    for (;;)
    {
      return f;
      f = this.mProxy.getTranslationX();
      continue;
      f = this.mProxy.getTranslationY();
      continue;
      f = this.mProxy.getRotation();
      continue;
      f = this.mProxy.getRotationX();
      continue;
      f = this.mProxy.getRotationY();
      continue;
      f = this.mProxy.getScaleX();
      continue;
      f = this.mProxy.getScaleY();
      continue;
      f = this.mProxy.getX();
      continue;
      f = this.mProxy.getY();
      continue;
      f = this.mProxy.getAlpha();
    }
  }
  
  private void setValue(int paramInt, float paramFloat)
  {
    switch (paramInt)
    {
    }
    for (;;)
    {
      return;
      this.mProxy.setTranslationX(paramFloat);
      continue;
      this.mProxy.setTranslationY(paramFloat);
      continue;
      this.mProxy.setRotation(paramFloat);
      continue;
      this.mProxy.setRotationX(paramFloat);
      continue;
      this.mProxy.setRotationY(paramFloat);
      continue;
      this.mProxy.setScaleX(paramFloat);
      continue;
      this.mProxy.setScaleY(paramFloat);
      continue;
      this.mProxy.setX(paramFloat);
      continue;
      this.mProxy.setY(paramFloat);
      continue;
      this.mProxy.setAlpha(paramFloat);
    }
  }
  
  private void startAnimation()
  {
    ValueAnimator localValueAnimator = ValueAnimator.ofFloat(new float[] { 1.0F });
    ArrayList localArrayList = (ArrayList)this.mPendingAnimations.clone();
    this.mPendingAnimations.clear();
    int i = 0;
    int j = localArrayList.size();
    for (int k = 0; k < j; k++) {
      i |= ((NameValuesHolder)localArrayList.get(k)).mNameConstant;
    }
    this.mAnimatorMap.put(localValueAnimator, new PropertyBundle(i, localArrayList));
    localValueAnimator.addUpdateListener(this.mAnimatorEventListener);
    localValueAnimator.addListener(this.mAnimatorEventListener);
    if (this.mStartDelaySet) {
      localValueAnimator.setStartDelay(this.mStartDelay);
    }
    if (this.mDurationSet) {
      localValueAnimator.setDuration(this.mDuration);
    }
    if (this.mInterpolatorSet) {
      localValueAnimator.setInterpolator(this.mInterpolator);
    }
    localValueAnimator.start();
  }
  
  public ViewPropertyAnimator alpha(float paramFloat)
  {
    animateProperty(512, paramFloat);
    return this;
  }
  
  public ViewPropertyAnimator alphaBy(float paramFloat)
  {
    animatePropertyBy(512, paramFloat);
    return this;
  }
  
  public void cancel()
  {
    if (this.mAnimatorMap.size() > 0)
    {
      localObject = ((HashMap)this.mAnimatorMap.clone()).keySet().iterator();
      while (((Iterator)localObject).hasNext()) {
        ((Animator)((Iterator)localObject).next()).cancel();
      }
    }
    this.mPendingAnimations.clear();
    Object localObject = (View)this.mView.get();
    if (localObject != null) {
      ((View)localObject).removeCallbacks(this.mAnimationStarter);
    }
  }
  
  public long getDuration()
  {
    if (this.mDurationSet) {}
    for (long l = this.mDuration;; l = new ValueAnimator().getDuration()) {
      return l;
    }
  }
  
  public long getStartDelay()
  {
    if (this.mStartDelaySet) {}
    for (long l = this.mStartDelay;; l = 0L) {
      return l;
    }
  }
  
  public ViewPropertyAnimator rotation(float paramFloat)
  {
    animateProperty(16, paramFloat);
    return this;
  }
  
  public ViewPropertyAnimator rotationBy(float paramFloat)
  {
    animatePropertyBy(16, paramFloat);
    return this;
  }
  
  public ViewPropertyAnimator rotationX(float paramFloat)
  {
    animateProperty(32, paramFloat);
    return this;
  }
  
  public ViewPropertyAnimator rotationXBy(float paramFloat)
  {
    animatePropertyBy(32, paramFloat);
    return this;
  }
  
  public ViewPropertyAnimator rotationY(float paramFloat)
  {
    animateProperty(64, paramFloat);
    return this;
  }
  
  public ViewPropertyAnimator rotationYBy(float paramFloat)
  {
    animatePropertyBy(64, paramFloat);
    return this;
  }
  
  public ViewPropertyAnimator scaleX(float paramFloat)
  {
    animateProperty(4, paramFloat);
    return this;
  }
  
  public ViewPropertyAnimator scaleXBy(float paramFloat)
  {
    animatePropertyBy(4, paramFloat);
    return this;
  }
  
  public ViewPropertyAnimator scaleY(float paramFloat)
  {
    animateProperty(8, paramFloat);
    return this;
  }
  
  public ViewPropertyAnimator scaleYBy(float paramFloat)
  {
    animatePropertyBy(8, paramFloat);
    return this;
  }
  
  public ViewPropertyAnimator setDuration(long paramLong)
  {
    if (paramLong < 0L) {
      throw new IllegalArgumentException("Animators cannot have negative duration: " + paramLong);
    }
    this.mDurationSet = true;
    this.mDuration = paramLong;
    return this;
  }
  
  public ViewPropertyAnimator setInterpolator(Interpolator paramInterpolator)
  {
    this.mInterpolatorSet = true;
    this.mInterpolator = paramInterpolator;
    return this;
  }
  
  public ViewPropertyAnimator setListener(Animator.AnimatorListener paramAnimatorListener)
  {
    this.mListener = paramAnimatorListener;
    return this;
  }
  
  public ViewPropertyAnimator setStartDelay(long paramLong)
  {
    if (paramLong < 0L) {
      throw new IllegalArgumentException("Animators cannot have negative duration: " + paramLong);
    }
    this.mStartDelaySet = true;
    this.mStartDelay = paramLong;
    return this;
  }
  
  public void start()
  {
    startAnimation();
  }
  
  public ViewPropertyAnimator translationX(float paramFloat)
  {
    animateProperty(1, paramFloat);
    return this;
  }
  
  public ViewPropertyAnimator translationXBy(float paramFloat)
  {
    animatePropertyBy(1, paramFloat);
    return this;
  }
  
  public ViewPropertyAnimator translationY(float paramFloat)
  {
    animateProperty(2, paramFloat);
    return this;
  }
  
  public ViewPropertyAnimator translationYBy(float paramFloat)
  {
    animatePropertyBy(2, paramFloat);
    return this;
  }
  
  public ViewPropertyAnimator x(float paramFloat)
  {
    animateProperty(128, paramFloat);
    return this;
  }
  
  public ViewPropertyAnimator xBy(float paramFloat)
  {
    animatePropertyBy(128, paramFloat);
    return this;
  }
  
  public ViewPropertyAnimator y(float paramFloat)
  {
    animateProperty(256, paramFloat);
    return this;
  }
  
  public ViewPropertyAnimator yBy(float paramFloat)
  {
    animatePropertyBy(256, paramFloat);
    return this;
  }
  
  private class AnimatorEventListener
    implements Animator.AnimatorListener, ValueAnimator.AnimatorUpdateListener
  {
    private AnimatorEventListener() {}
    
    public void onAnimationCancel(Animator paramAnimator)
    {
      if (ViewPropertyAnimatorPreHC.this.mListener != null) {
        ViewPropertyAnimatorPreHC.this.mListener.onAnimationCancel(paramAnimator);
      }
    }
    
    public void onAnimationEnd(Animator paramAnimator)
    {
      if (ViewPropertyAnimatorPreHC.this.mListener != null) {
        ViewPropertyAnimatorPreHC.this.mListener.onAnimationEnd(paramAnimator);
      }
      ViewPropertyAnimatorPreHC.this.mAnimatorMap.remove(paramAnimator);
      if (ViewPropertyAnimatorPreHC.this.mAnimatorMap.isEmpty()) {
        ViewPropertyAnimatorPreHC.access$202(ViewPropertyAnimatorPreHC.this, null);
      }
    }
    
    public void onAnimationRepeat(Animator paramAnimator)
    {
      if (ViewPropertyAnimatorPreHC.this.mListener != null) {
        ViewPropertyAnimatorPreHC.this.mListener.onAnimationRepeat(paramAnimator);
      }
    }
    
    public void onAnimationStart(Animator paramAnimator)
    {
      if (ViewPropertyAnimatorPreHC.this.mListener != null) {
        ViewPropertyAnimatorPreHC.this.mListener.onAnimationStart(paramAnimator);
      }
    }
    
    public void onAnimationUpdate(ValueAnimator paramValueAnimator)
    {
      float f1 = paramValueAnimator.getAnimatedFraction();
      paramValueAnimator = (ViewPropertyAnimatorPreHC.PropertyBundle)ViewPropertyAnimatorPreHC.this.mAnimatorMap.get(paramValueAnimator);
      if ((paramValueAnimator.mPropertyMask & 0x1FF) != 0)
      {
        localObject = (View)ViewPropertyAnimatorPreHC.this.mView.get();
        if (localObject != null) {
          ((View)localObject).invalidate();
        }
      }
      Object localObject = paramValueAnimator.mNameValuesHolder;
      if (localObject != null)
      {
        int i = ((ArrayList)localObject).size();
        for (int j = 0; j < i; j++)
        {
          paramValueAnimator = (ViewPropertyAnimatorPreHC.NameValuesHolder)((ArrayList)localObject).get(j);
          float f2 = paramValueAnimator.mFromValue;
          float f3 = paramValueAnimator.mDeltaValue;
          ViewPropertyAnimatorPreHC.this.setValue(paramValueAnimator.mNameConstant, f2 + f3 * f1);
        }
      }
      paramValueAnimator = (View)ViewPropertyAnimatorPreHC.this.mView.get();
      if (paramValueAnimator != null) {
        paramValueAnimator.invalidate();
      }
    }
  }
  
  private static class NameValuesHolder
  {
    float mDeltaValue;
    float mFromValue;
    int mNameConstant;
    
    NameValuesHolder(int paramInt, float paramFloat1, float paramFloat2)
    {
      this.mNameConstant = paramInt;
      this.mFromValue = paramFloat1;
      this.mDeltaValue = paramFloat2;
    }
  }
  
  private static class PropertyBundle
  {
    ArrayList<ViewPropertyAnimatorPreHC.NameValuesHolder> mNameValuesHolder;
    int mPropertyMask;
    
    PropertyBundle(int paramInt, ArrayList<ViewPropertyAnimatorPreHC.NameValuesHolder> paramArrayList)
    {
      this.mPropertyMask = paramInt;
      this.mNameValuesHolder = paramArrayList;
    }
    
    boolean cancel(int paramInt)
    {
      int j;
      if (((this.mPropertyMask & paramInt) != 0) && (this.mNameValuesHolder != null))
      {
        int i = this.mNameValuesHolder.size();
        j = 0;
        if (j < i) {
          if (((ViewPropertyAnimatorPreHC.NameValuesHolder)this.mNameValuesHolder.get(j)).mNameConstant == paramInt)
          {
            this.mNameValuesHolder.remove(j);
            this.mPropertyMask &= (paramInt ^ 0xFFFFFFFF);
          }
        }
      }
      for (boolean bool = true;; bool = false)
      {
        return bool;
        j++;
        break;
      }
    }
  }
}


/* Location:              /prj/BitcoinChecker/tools/dex-tools-2.1-SNAPSHOT/bc-signed-aligned-dex2jar.jar!/com/nineoldandroids/view/ViewPropertyAnimatorPreHC.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */