package com.google.gson;

import com.google.gson.internal..Gson.Preconditions;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;

public final class FieldAttributes
{
  private final Field field;
  
  public FieldAttributes(Field paramField)
  {
    .Gson.Preconditions.checkNotNull(paramField);
    this.field = paramField;
  }
  
  Object get(Object paramObject)
    throws IllegalAccessException
  {
    return this.field.get(paramObject);
  }
  
  public <T extends Annotation> T getAnnotation(Class<T> paramClass)
  {
    return this.field.getAnnotation(paramClass);
  }
  
  public Collection<Annotation> getAnnotations()
  {
    return Arrays.asList(this.field.getAnnotations());
  }
  
  public Class<?> getDeclaredClass()
  {
    return this.field.getType();
  }
  
  public Type getDeclaredType()
  {
    return this.field.getGenericType();
  }
  
  public Class<?> getDeclaringClass()
  {
    return this.field.getDeclaringClass();
  }
  
  public String getName()
  {
    return this.field.getName();
  }
  
  public boolean hasModifier(int paramInt)
  {
    if ((this.field.getModifiers() & paramInt) != 0) {}
    for (boolean bool = true;; bool = false) {
      return bool;
    }
  }
  
  boolean isSynthetic()
  {
    return this.field.isSynthetic();
  }
}


/* Location:              /prj/BitcoinChecker/tools/dex-tools-2.1-SNAPSHOT/bc-signed-aligned-dex2jar.jar!/com/google/gson/FieldAttributes.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */