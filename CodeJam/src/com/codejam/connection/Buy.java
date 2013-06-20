package com.codejam.connection;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import com.codejam.listener.ListenerManager;
import com.codejam.listener.StrategyType;
import com.codejam.trade.Trade;
import com.codejam.trade.TradeType;

public class Buy extends Thread
{
	
	private int reqTime;
	private StrategyType TYPE;
	private String MANAGER;
	
	public Buy(int pTime, StrategyType pType, String pManager)
	{
		super();
		this.reqTime = pTime;
		this.TYPE = pType;
		this.MANAGER = pManager;
	}
	
	public void run()
	{
		if(Connections.MARKET_CLOSED)
		{
			return;
		}
		
		Connections.getTradeBookWriter().print("B\r\n");
		Connections.getTradeBookWriter().flush();
		char[] buffer = new char[10];
		String price = new String();
		try
		{
			Connections.getTradeBookReader().read(buffer);
		}catch (IOException e) {}
		for(char c: buffer)
		{
			if(((int) c >= 48 && (int) c <= 90) || (int) c == 46)
			{
				price = price + c;
			}
		}
		if(Connections.getTime() != 0)
		{
			if(price.contains("E")) price = "E";
			if(price.equals("E")) Connections.MARKET_CLOSED = true;
			ListenerManager.sendTrade(new Trade(Connections.getTime(), price, TradeType.BUY, this.TYPE, this.MANAGER));
			System.out.print("Stock bought at time " + Connections.getTime() + " when requested buy at " + this.reqTime + " for price " + price + "\n");
		}
	}
}
