package com.google.gson;

public final class JsonIOException
  extends JsonParseException
{
  private static final long serialVersionUID = 1L;
  
  public JsonIOException(String paramString)
  {
    super(paramString);
  }
  
  public JsonIOException(String paramString, Throwable paramThrowable)
  {
    super(paramString, paramThrowable);
  }
  
  public JsonIOException(Throwable paramThrowable)
  {
    super(paramThrowable);
  }
}


/* Location:              /prj/BitcoinChecker/tools/dex-tools-2.1-SNAPSHOT/bc-signed-aligned-dex2jar.jar!/com/google/gson/JsonIOException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */