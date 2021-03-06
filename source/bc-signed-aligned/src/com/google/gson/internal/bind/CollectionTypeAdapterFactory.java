package com.google.gson.internal.bind;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal..Gson.Types;
import com.google.gson.internal.ConstructorConstructor;
import com.google.gson.internal.ObjectConstructor;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Iterator;

public final class CollectionTypeAdapterFactory
  implements TypeAdapterFactory
{
  private final ConstructorConstructor constructorConstructor;
  
  public CollectionTypeAdapterFactory(ConstructorConstructor paramConstructorConstructor)
  {
    this.constructorConstructor = paramConstructorConstructor;
  }
  
  public <T> TypeAdapter<T> create(Gson paramGson, TypeToken<T> paramTypeToken)
  {
    Type localType = paramTypeToken.getType();
    Class localClass = paramTypeToken.getRawType();
    if (!Collection.class.isAssignableFrom(localClass)) {}
    for (paramGson = null;; paramGson = new Adapter(paramGson, localType, paramGson.getAdapter(TypeToken.get(localType)), this.constructorConstructor.get(paramTypeToken)))
    {
      return paramGson;
      localType = .Gson.Types.getCollectionElementType(localType, localClass);
    }
  }
  
  private static final class Adapter<E>
    extends TypeAdapter<Collection<E>>
  {
    private final ObjectConstructor<? extends Collection<E>> constructor;
    private final TypeAdapter<E> elementTypeAdapter;
    
    public Adapter(Gson paramGson, Type paramType, TypeAdapter<E> paramTypeAdapter, ObjectConstructor<? extends Collection<E>> paramObjectConstructor)
    {
      this.elementTypeAdapter = new TypeAdapterRuntimeTypeWrapper(paramGson, paramTypeAdapter, paramType);
      this.constructor = paramObjectConstructor;
    }
    
    public Collection<E> read(JsonReader paramJsonReader)
      throws IOException
    {
      if (paramJsonReader.peek() == JsonToken.NULL) {
        paramJsonReader.nextNull();
      }
      Collection localCollection;
      for (paramJsonReader = null;; paramJsonReader = localCollection)
      {
        return paramJsonReader;
        localCollection = (Collection)this.constructor.construct();
        paramJsonReader.beginArray();
        while (paramJsonReader.hasNext()) {
          localCollection.add(this.elementTypeAdapter.read(paramJsonReader));
        }
        paramJsonReader.endArray();
      }
    }
    
    public void write(JsonWriter paramJsonWriter, Collection<E> paramCollection)
      throws IOException
    {
      if (paramCollection == null) {
        paramJsonWriter.nullValue();
      }
      for (;;)
      {
        return;
        paramJsonWriter.beginArray();
        Iterator localIterator = paramCollection.iterator();
        while (localIterator.hasNext())
        {
          paramCollection = localIterator.next();
          this.elementTypeAdapter.write(paramJsonWriter, paramCollection);
        }
        paramJsonWriter.endArray();
      }
    }
  }
}


/* Location:              /prj/BitcoinChecker/tools/dex-tools-2.1-SNAPSHOT/bc-signed-aligned-dex2jar.jar!/com/google/gson/internal/bind/CollectionTypeAdapterFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */