package com.mobnetic.coinguardian.model.market;

import com.mobnetic.coinguardian.model.CheckerInfo;
import com.mobnetic.coinguardian.model.Market;
import com.mobnetic.coinguardian.model.Ticker;
import java.util.HashMap;
import java.util.LinkedHashMap;
import org.json.JSONObject;

public class ETHEXIndia
  extends Market
{
  private static final HashMap<String, CharSequence[]> CURRENCY_PAIRS = new LinkedHashMap();
  private static final String NAME = "ETHEX India";
  private static final String TTS_NAME = "ETHEX India";
  private static final String URL = "https://api.ethexindia.com/ticker/";
  
  static
  {
    CURRENCY_PAIRS.put("ETH", new String[] { "INR" });
  }
  
  public ETHEXIndia()
  {
    super("ETHEX India", "ETHEX India", CURRENCY_PAIRS);
  }
  
  public String getUrl(int paramInt, CheckerInfo paramCheckerInfo)
  {
    return "https://api.ethexindia.com/ticker/";
  }
  
  protected void parseTickerFromJsonObject(int paramInt, JSONObject paramJSONObject, Ticker paramTicker, CheckerInfo paramCheckerInfo)
    throws Exception
  {
    paramTicker.bid = paramJSONObject.getDouble("bid");
    paramTicker.ask = paramJSONObject.getDouble("ask");
    paramTicker.vol = paramJSONObject.getDouble("current_volume");
    paramTicker.high = paramJSONObject.getDouble("high");
    paramTicker.low = paramJSONObject.getDouble("low");
    paramTicker.last = paramJSONObject.getDouble("last_traded_price");
    paramTicker.timestamp = paramJSONObject.getLong("last_traded_time");
  }
}


/* Location:              /prj/BitcoinChecker/tools/dex-tools-2.1-SNAPSHOT/bc-signed-aligned-dex2jar.jar!/com/mobnetic/coinguardian/model/market/ETHEXIndia.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */