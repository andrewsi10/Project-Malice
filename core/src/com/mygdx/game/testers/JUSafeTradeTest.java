package com.mygdx.game.testers;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.*;

import org.junit.*;

import static org.junit.Assert.*;
import junit.framework.JUnit4TestAdapter;

/**
 * SafeTrade tests:
 *   TradeOrder
 *   PriceComparator
 *   Trader
 *   Brokerage
 *   StockExchange
 *   Stock
 *
 * @author This class was split according to who made which classes:
 *          TradeOrder: David Huang (JunitTest was given)
 *          PriceComparator: George Peck (Given)
 *          Trader: David Huang
 *          Brokerage: Nathan Lui
 *          StockExchange: David Huang
 *          Stock: Nathan Lui
 * 
 * @author Nathan Lui
 * @author David Huang
 * @version March 27, 2015
 * @author Assignment: JM Chapter 19 - SafeTrade
 * 
 * @author Sources: sources
 *
 */
public class JUSafeTradeTest
{
    // --Test TradeOrder
    /**
     * TradeOrder tests:
     *   TradeOrderConstructor - constructs TradeOrder and then compare toString
     *   TradeOrderGetTrader - compares value returned to constructed value
     *   TradeOrderGetSymbol - compares value returned to constructed value
     *   TradeOrderIsBuy - compares value returned to constructed value
     *   TradeOrderIsSell - compares value returned to constructed value
     *   TradeOrderIsMarket - compares value returned to constructed value
     *   TradeOrderIsLimit - compares value returned to constructed value
     *   TradeOrderGetShares - compares value returned to constructed value
     *   TradeOrderGetPrice - compares value returned to constructed value
     *   TradeOrderSubtractShares - subtracts known value & compares result
     *     returned by getShares to expected value
     */
    private String symbol = "GGGL";
    private boolean buyOrder = true;
    private boolean marketOrder = true;
    private int numShares = 123;
    private int numToSubtract = 24;
    private double price = 123.45;

    @Test
    public void tradeOrderConstructor()
    {
        TradeOrder to = new TradeOrder( null, symbol, buyOrder, marketOrder,
            numShares, price );
        String toStr = to.toString();

        assertTrue( "<< Invalid TradeOrder Constructor >>",
                    toStr.contains( "TradeOrder[Trader trader:null" )
                        && toStr.contains( "java.lang.String symbol:" + symbol )
                        && toStr.contains( "boolean buyOrder:" + buyOrder )
                        && toStr.contains( "boolean marketOrder:" + marketOrder )
                        && toStr.contains( "int numShares:" + numShares )
                        && toStr.contains( "double price:" + price ) );
    }
    
    @Test
    public void TradeOrderToString()
    {
        TradeOrder to = new TradeOrder( null, symbol, buyOrder, marketOrder,
            numShares, price );
        assertNotNull( to.toString() );
    }

    @Test
    public void tradeOrderGetTrader()
    {
        TradeOrder to = new TradeOrder( null, symbol, buyOrder, marketOrder,
            numShares, price );
        assertNull( "<< TradeOrder: " + to.getTrader() + " should be null >>",
                    to.getTrader() );
    }

    @Test
    public void tradeOrderGetSymbol()
    {
        TradeOrder to = new TradeOrder( null, symbol, buyOrder, marketOrder,
            numShares, price );
        assertEquals( "<< TradeOrder: " + to.getTrader() + " should be "
            + symbol + " >>", symbol, to.getSymbol() );
    }

    @Test
    public void tradeOrderIsBuy()
    {
        TradeOrder to = new TradeOrder( null, symbol, buyOrder, marketOrder,
            numShares, price );

        assertTrue( "<< TradeOrder: " + to.isBuy() + " should be " + buyOrder
            + " >>", to.isBuy() );
    }

    @Test
    public void tradeOrderIsSell()
    {
        TradeOrder to = new TradeOrder( null, symbol, buyOrder, marketOrder,
            numShares, price );
        assertFalse( "<< TradeOrder: " + to.isSell() + " should be "
            + !buyOrder + " >>", to.isSell() );
    }

    @Test
    public void tradeOrderIsMarket()
    {
        TradeOrder to = new TradeOrder( null, symbol, buyOrder, marketOrder,
            numShares, price );
        assertTrue( "<< TradeOrder: " + to.isMarket() + " should be "
            + marketOrder + " >>", to.isMarket() );
    }

    @Test
    public void tradeOrderIsLimit()
    {
        TradeOrder to = new TradeOrder( null, symbol, buyOrder, marketOrder,
            numShares, price );

        assertFalse( "<< TradeOrder: " + to.isLimit() + " should be "
            + !marketOrder + ">>", to.isLimit() );
    }

    @Test
    public void tradeOrderGetShares()
    {
        TradeOrder to = new TradeOrder( null, symbol, buyOrder, marketOrder,
            numShares, price );
        assertTrue( "<< TradeOrder: " + to.getShares() + " should be "
            + numShares + ">>", numShares == to.getShares()
            || ( numShares - numToSubtract ) == to.getShares() );
    }

    @Test
    public void tradeOrderGetPrice()
    {
        TradeOrder to = new TradeOrder( null, symbol, buyOrder, marketOrder,
            numShares, price );
        assertEquals( "<< TradeOrder: " + to.getPrice() + " should be " + price
            + ">>", price, to.getPrice(), 0.0 );
    }

    @Test
    public void tradeOrderSubtractShares()
    {
        TradeOrder to = new TradeOrder( null, symbol, buyOrder, marketOrder,
            numShares, price );
        to.subtractShares( numToSubtract );
        assertEquals( "<< TradeOrder: subtractShares(" + numToSubtract
            + ") should be " + ( numShares - numToSubtract ) + ">>", numShares
            - numToSubtract, to.getShares() );
    }
    
    // --Test TraderWindow Stub
    @Test
    public void traderWindowConstructor()
    {
        TraderWindow tw = new TraderWindow( null );
        assertNotNull( tw );
    }

    @Test
    public void traderWindowShowMessage()
    {
        TraderWindow tw = new TraderWindow( null );
        assertNotNull( tw );
        tw.showMessage( null );
    }

    // --Test PriceComparator
    @Test
    public void priceComparatorMarket1Market2()
    {
        TradeOrder order1 = new TradeOrder( null,
            "COMP",
            true,
            true,
            111,
            111.44 );
        TradeOrder order2 = new TradeOrder( null,
            "COMP",
            true,
            true,
            222,
            222.99 );

        PriceComparator pc = new PriceComparator();
        assertEquals( 0, pc.compare( order1, order2 ) );
    }


    @Test
    public void priceComparatorMarket1Limit2()
    {
        TradeOrder order1 = new TradeOrder( null,
            "COMP",
            true,
            true,
            111,
            111.44 );
        TradeOrder order2 = new TradeOrder( null,
            "COMP",
            true,
            false,
            222,
            222.99 );

        PriceComparator pc = new PriceComparator();
        assertEquals( -1, pc.compare( order1, order2 ) );
    }


    @Test
    public void priceComparatorLimit1Market2()
    {
        TradeOrder order1 = new TradeOrder( null,
            "COMP",
            true,
            false,
            111,
            111.44 );
        TradeOrder order2 = new TradeOrder( null,
            "COMP",
            true,
            true,
            222,
            222.99 );

        PriceComparator pc = new PriceComparator();
        assertEquals( 1, pc.compare( order1, order2 ) );
    }


    @Test
    public void priceComparatorAscending()
    {
        TradeOrder order1 = new TradeOrder( null,
            "COMP",
            true,
            false,
            111,
            111.44 );
        TradeOrder order2 = new TradeOrder( null,
            "COMP",
            true,
            false,
            222,
            222.99 );

        int cents1 = (int)( 100.0 * order1.getPrice() + 0.5 );
        int cents2 = (int)( 100.0 * order2.getPrice() + 0.5 );

        PriceComparator pc = new PriceComparator();
        assertEquals( cents1 - cents2, pc.compare( order1, order2 ) );
    }


    @Test
    public void priceComparatorDescending()
    {
        TradeOrder order1 = new TradeOrder( null,
            "COMP",
            true,
            false,
            111,
            111.44 );
        TradeOrder order2 = new TradeOrder( null,
            "COMP",
            true,
            false,
            222,
            222.99 );

        int cents1 = (int)( 100.0 * order1.getPrice() + 0.5 );
        int cents2 = (int)( 100.0 * order2.getPrice() + 0.5 );

        PriceComparator pc = new PriceComparator( false );
        assertEquals( cents2 - cents1, pc.compare( order1, order2 ) );
    }
    
    // --Test Trader

    @Test
    public void traderConstructor()
    {
        Trader sTrader = new Trader( null, "somename", "password" );
        String str = sTrader.toString();

        assertTrue( "<< Invalid TradeOrder Constructor >>",
            str.contains( "Trader[Brokerage brokerage" )
                && str.contains( "java.lang.String screenName:" + "somename" )
                && str.contains( "java.lang.String password:" + "password" )
                && str.contains( "TraderWindow myWindow:null" )
                && str.contains( "java.util.Queue mailbox:[]" ) );
    }


    @Test
    public void traderCompare()
    {
        Trader trader1 = new Trader( null, "a", "password" );
        Trader trader2 = new Trader( null, "a", "password" );
        Trader trader3 = new Trader( null, "b", "password" );

        assertEquals( 0, trader1.compareTo( trader2 ) );
        assertFalse( 0 == trader1.compareTo( trader3 ) );
    }


    @Test
    public void traderEquals()
    {
        Trader trader1 = new Trader( null, "a", "password" );
        Trader trader2 = new Trader( null, "a", "password" );
        Trader trader3 = new Trader( null, "b", "password" );
        Map<Integer, String> map = new TreeMap<Integer, String>();

        assertTrue( trader1.equals( trader2 ) );
        assertFalse( trader1.equals( trader3 ) );
        assertFalse( trader1.equals( map ) );
    }


    @Test
    public void traderGetName()
    {
        Trader trader1 = new Trader( null, "a", "password" );
        assertEquals( trader1.getName(), "a" );
    }


    @Test
    public void traderGetPassword()
    {
        Trader trader1 = new Trader( null, "a", "password" );
        assertEquals( trader1.getPassword(), "password" );
    }


    @Test
    public void traderGetQuote()
    {
        Trader trader1 = new Trader( null, "a", "password" );
        boolean someBool = false;
        //brokerage is null so i test that this method, which is supposed to 
        //call brokerage, returns a null pointer exception.
        
        try
        {
            trader1.getQuote( "someSymbol" );
            fail();
        }
        catch ( NullPointerException ex )
        {
            someBool = true;
        }
        assertTrue( someBool );
        
        StockExchange stkEx = new StockExchange();
        stkEx.listStock( symbol, marketName, price );
        Brokerage someBrok = new Brokerage( stkEx );
        Trader trader2 = new Trader( someBrok, "a", "password" );
        
        trader2.getQuote( symbol );
        assertFalse( trader2.mailbox().isEmpty() );
    }


    @Test
    public void traderOpenWindow()
    {
        Trader trader1 = new Trader( null, "a", "password" );
        trader1.openWindow();
        String str = trader1.toString();
        assertTrue( str.contains( "TraderWindow myWindow:TraderWindow" ) );
    }

    @Test
    public void traderHasMessages()
    {
        Trader trader1 = new Trader( null, "a", "password" );
        assertFalse( trader1.hasMessages() );
    }
    
    @Test
    public void traderReceiveMessage()
    {
        String msg = "someMessage";
        Trader trader1 = new Trader( null, "a", "password" );
        trader1.receiveMessage( msg );
        assertTrue( trader1.hasMessages() );
        trader1.openWindow();
        assertFalse( trader1.hasMessages() ); 
        trader1.receiveMessage( msg );
        assertFalse( trader1.hasMessages() );
    }

    @Test
    public void traderPlaceOrder()
    {
        Trader trader1 = new Trader( null, "a", "password" );
        boolean someBool = false;
        // brokerage is null so i test that this method, which is supposed to
        // call brokerage, returns a null pointer exception.

        try
        {
            trader1.placeOrder( null );
            fail();
        }
        catch ( NullPointerException ex )
        {
            someBool = true;
        }
        assertTrue( someBool );
        
        StockExchange stkEx = new StockExchange();
        Brokerage someBrok = new Brokerage( stkEx );
        Trader trader2 = new Trader( someBrok, "a", "password" );

        // checks to see that this method functions correctly: correctly creates
        // a Stock object to call the placeOrder method from the Stock class
        TradeOrder t = new TradeOrder( trader2,
            symbol,
            true,
            true,
            numShares,
            price );

        someBool = true;
        try
        {
            trader2.placeOrder( t );
        }
        catch ( NullPointerException ex )
        {
            someBool = false;
        }
        assertTrue( someBool );
        assertFalse( trader2.mailbox().isEmpty() );
        trader2.mailbox().remove();
        assertTrue( trader2.mailbox().isEmpty() );
        stkEx.listStock( symbol, "Google", price );
        trader2.placeOrder( t );
        assertFalse( trader2.mailbox().isEmpty() );
    }

    @Test
    public void traderQuit()
    {
        Brokerage someBrok = new Brokerage( null );
        Trader trader1 = new Trader( someBrok, "a", "password" );
        someBrok.addUser( "a", "password" );
        someBrok.login( "a", "password" );
        trader1.quit();
        assertFalse( someBrok.getLoggedTraders().contains( trader1 ) );
        String str = trader1.toString();
        assertTrue( str.contains( "TraderWindow myWindow:null" ) );
    }
    
    @Test
    public void traderToStringTest()
    {
        assertNotNull( new Trader( null, goodName1, goodPass ).toString() );
    }
    
    
    // --Test Brokerage
    
    private String goodName1 = "David";
    private String goodName2 = "Nathan";
    private String goodName3 = "Peck";
    private String goodPass = "password";
    private String badName1 = "Bob";
    private String badName2 = "VeryLongName";
    private String badPass1 = "badPassword";
    private String badPass2 = "K";
    
    @Test
    public void BrokerageConstructorTest()
    {
        Brokerage br = new Brokerage( null );
        assertNotNull( br );
        assertNotNull( br.getLoggedTraders() );
        assertTrue( br.getLoggedTraders().isEmpty() );
        assertNotNull( br.getTraders() );
        assertTrue( br.getTraders().isEmpty() );
        assertNull( br.getExchange() );
    }
    
    @Test
    public void brokerageAddUserTest()
    {
        Brokerage br = new Brokerage( null );
        assertNotNull( br );
        int check = br.addUser( goodName1, goodPass );
        assertEquals( "<< Brokerage: " + check + " should return 0 >>", 
            0, check );
        check = br.addUser( badName1, goodPass );
        assertEquals( "<< Brokerage: " + check + " should return -1 >>", 
            -1, check );
        check = br.addUser( badName2, goodPass );
        assertEquals( "<< Brokerage: " + check + " should return -1 >>", 
            -1, check );
        check = br.addUser( goodName2, badPass1 );
        assertEquals( "<< Brokerage: " + check + " should return -2 >>", 
            -2, check );
        check = br.addUser( goodName3, badPass2 );
        assertEquals( "<< Brokerage: " + check + " should return -2 >>", 
            -2, check );
        check = br.addUser( goodName1, goodPass );
        assertEquals( "<< Brokerage: " + check + " should return -3 >>", 
            -3, check );
    }
    
    @Test
    public void brokerageLoginTest()
    {
        Brokerage br = new Brokerage( null );
        assertNotNull( br );
        assertEquals( 
            "<< Brokerage: addUser() is Incorrect can't test login() >>", 
            0, br.addUser( goodName1, goodPass ) );
        
        int check = br.login( goodName2, goodPass );
        assertEquals( "<< Brokerage: " + check + " should return -1 >>", 
            -1, check );
        assertTrue( "<< Brokerage: loggedTraders should be empty >>", 
                    br.getLoggedTraders().isEmpty() );
        check = br.login( goodName1, badPass1 );
        assertEquals( "<< Brokerage: " + check + " should return -2 >>", 
            -2, check );
        assertTrue( "<< Brokerage: loggedTraders should be empty >>", 
                    br.getLoggedTraders().isEmpty() );
        check = br.login( goodName1, goodPass );
        assertEquals( "<< Brokerage: " + check + " should return 0 >>", 
            0, check );
        assertFalse( "<< Brokerage: loggedTraders should not be empty >>", 
                     br.getLoggedTraders().isEmpty() );
        check = br.login( goodName1, goodPass );
        assertEquals( "<< Brokerage: " + check + " should return -3 >>", 
            -3, check );
        assertFalse( "<< Brokerage: loggedTraders should not be empty >>", 
                     br.getLoggedTraders().isEmpty() );
    }
    
    @Test
    public void brokerageLogoutTest()
    {
        Brokerage br = new Brokerage( null );
        assertNotNull( br );
        assertEquals( 
            "<< Brokerage: addUser() is Incorrect can't test logout() >>", 
            0, br.addUser( goodName1, goodPass ) );
        assertEquals( 
            "<< Brokerage: login() is Incorrect can't test logout() >>", 
            0, br.login( goodName1, goodPass ) );
        assertFalse( "<< Brokerage: loggedTraders should not be empty >>", 
                     br.getLoggedTraders().isEmpty() );
        
        br.logout( new Trader( br, badName1, badPass1 ) );
        assertFalse( "<< Brokerage: loggedTraders should not be empty >>", 
                     br.getLoggedTraders().isEmpty() );
        br.logout( new Trader( br, goodName1, goodPass ) );
        assertTrue( "<< Brokerage: loggedTraders should be empty >>", 
                    br.getLoggedTraders().isEmpty() );
    }
    
    @Test
    public void brokerageGetQuoteTest()
    {
        StockExchange ste = new StockExchange();
        Brokerage br = new Brokerage( ste );
        Trader t = new Trader( br, goodName1, goodPass );
        ste.listStock( symbol, goodName1, price );
        br.getQuote( symbol, t );
        assertTrue( t.hasMessages() );
    }
    
    @Test
    public void brokeragePlaceOrder()
    {
        StockExchange ste = new StockExchange();
        ste.listStock( symbol, goodName1, price );
        Brokerage br = new Brokerage( ste );
        TradeOrder t = new TradeOrder( new Trader(br, goodName1, goodPass), 
                                    symbol, true, true, numShares, price );
        br.placeOrder( t );
        assertFalse( ste.getListedStocks().get( symbol ).getBuyOrders().isEmpty() );
        
    }
    
    @Test
    public void brokerageToStringTest()
    {
        assertNotNull( new Brokerage( null ).toString() );
    }
    
    
    // --Test StockExchange

    @Test
    public void stockExchangeConstructor()
    {
        StockExchange stkEx = new StockExchange();
        String str = stkEx.toString();
        assertTrue( str.contains( "java.util.Map listedStocks:{}" ) );
    }


    @Test
    public void stockExchangeGetQuote()
    {
        StockExchange stkEx = new StockExchange();
        assertTrue( stkEx.getQuote( symbol ).contains( "not found" ) );
        
        stkEx.listStock( symbol, "Google", price );
        assertNotNull( stkEx.getQuote( symbol ) );
    }


    @Test
    public void stockExchangeListStock()
    {
        StockExchange stkEx = new StockExchange();
        stkEx.listStock( "GOOG", "someStock", 100 );
        assertTrue( !stkEx.getListedStocks().isEmpty() );
        String str = stkEx.toString();
        assertTrue( str.contains( "java.util.Map listedStocks:{GOOG=Stock" ) );
    }

    @Test
    public void stockExchangePlaceOrder()
    {
        StockExchange stkEx = new StockExchange();
        boolean someBool = false;
        // in this case, giving the placeOrder method a null TraderOrder will
        // cause a NullPointerException, showing that the method works to some
        // extent

        try
        {
            stkEx.placeOrder( null );
        }
        catch ( NullPointerException ex )
        {
            someBool = true;
        }
        assertTrue( someBool );
        
        
        Brokerage someBrok = new Brokerage( stkEx );
        Trader trader1 = new Trader( someBrok, "a", "password" );

        // checks to see that this method functions correctly: correctly creates
        // a Stock object to call the placeOrder method from the Stock class
        TradeOrder t = new TradeOrder( trader1,
            symbol,
            true,
            true,
            numShares,
            price );

        someBool = true;
        try
        {
            stkEx.placeOrder( t );
        }
        catch ( NullPointerException ex )
        {
            someBool = false;
        }
        assertTrue( someBool );
        assertFalse( trader1.mailbox().isEmpty() );
        trader1.mailbox().remove();
        assertTrue( trader1.mailbox().isEmpty() );
        stkEx.listStock( symbol, "Google", price );
        stkEx.placeOrder( t );
        assertFalse( trader1.mailbox().isEmpty() );
    }

    @Test
    public void stockExchangeToStringTest()
    {
        assertNotNull( new StockExchange().toString() );
    }
    
    
    // --Test Stock
    
//    private String symbol = "GGGL";
//    private boolean buyOrder = true;
//    private boolean marketOrder = true;
//    private int numShares = 123;
//    private int numToSubtract = 24;
//    private double price = 123.45;
    private String marketName = "Giggle";
    
    
    @Test
    public void stockConstructorTest()
    {
        Stock st = new Stock( symbol, marketName, price );
        assertEquals( 
            "<< Stock: " + st.getStockSymbol() + " should be " + symbol + " >>", 
            symbol, st.getStockSymbol() );
        assertEquals( 
            "<< Stock: " + st.getCompanyName() + " should be " + marketName + " >>", 
            marketName, st.getCompanyName() );
        assertEquals( 
            "<< Stock: " + st.getLoPrice() + " should be " + price + " >>", 
            price, st.getLastPrice(), 0.0 );
        assertEquals( 
            "<< Stock: " + st.getStockSymbol() + " should be " + price + " >>", 
            symbol, st.getStockSymbol() );
        assertEquals( 
            "<< Stock: " + st.getStockSymbol() + " should be " + price + " >>", 
            symbol, st.getStockSymbol() );
        assertEquals( 
            "<< Stock: " + st.getStockSymbol() + " should be " + price + " >>", 
            symbol, st.getStockSymbol() );
        assertEquals( 
            "<< Stock: " + st.getVolume() + " should be 0 >>", 
            symbol, st.getStockSymbol() );
        assertNotNull( st.getBuyOrders() );
        assertTrue( st.getBuyOrders().isEmpty() );
        assertNotNull( st.getSellOrders() );
        assertTrue( st.getSellOrders().isEmpty() );
        
    }

    @Test
    public void stockGetQuoteTest()
    {
        Stock st = new Stock( symbol, marketName, price );
        String stockQuote = st.getQuote();
        String quote = marketName + " (" + symbol + ")\n"
                        + "Price: " + price + " hi: " + price + " lo: " + price 
                        + " vol: 0\nAsk: none  Bid: none";
        assertEquals( quote, stockQuote );
    }

    @Test
    public void stockExecuteOrdersTest()
    {
        Stock st = new Stock( symbol, marketName, price );
        Trader t1 = new Trader( null, goodName1, goodPass );
        Trader t2 = new Trader( null, goodName2, goodPass );
        TradeOrder buyOrder1 = new TradeOrder( t1, symbol, buyOrder, !marketOrder, numShares, price + 2);
        TradeOrder buyOrder2 = new TradeOrder( t1, symbol, buyOrder, marketOrder, numShares + 1, price - 1 );
        TradeOrder buyOrder3 = new TradeOrder( t1, symbol, buyOrder, !marketOrder, numShares, price - 1);
        TradeOrder sellOrder1 = new TradeOrder( t2, symbol, !buyOrder, !marketOrder, numShares, price + 2);
        TradeOrder sellOrder2 = new TradeOrder( t2, symbol, !buyOrder, marketOrder, numShares + 1, price - 2 );
        TradeOrder sellOrder3 = new TradeOrder( t2, symbol, !buyOrder, !marketOrder, numShares, price - 2 );
        PriorityQueue<TradeOrder> buyOrders = st.getBuyOrders();
        PriorityQueue<TradeOrder> sellOrders = st.getSellOrders();
        
        // if orders have same amount of stocks and same price and are limit
        buyOrders.add( buyOrder1 );
        st.executeOrders();
        assertEquals( buyOrder1, buyOrders.peek() ); // check queues
        assertTrue( sellOrders.isEmpty() );
        sellOrders.add( sellOrder1 );
        st.executeOrders();
        assertTrue( buyOrders.isEmpty() ); // check queues
        assertTrue( sellOrders.isEmpty() );
        assertEquals( 0, buyOrder1.getShares() ); // check # of shares
        assertEquals( 0, sellOrder1.getShares() );
        assertEquals( numShares, st.getVolume() ); // check volume
        assertEquals( price, st.getLoPrice(), 0.0 ); // check prices
        assertEquals( price + 2, st.getLastPrice(), 0.0 );
        assertEquals( price + 2, st.getHiPrice(), 0.0 );
        assertFalse( t1.mailbox().isEmpty() ); // check mailboxes
        assertFalse( t2.mailbox().isEmpty() );
        t1.mailbox().remove(); // clear mailboxes
        t2.mailbox().remove();
        assertTrue( t1.mailbox().isEmpty() ); // should only have one object
        assertTrue( t2.mailbox().isEmpty() );
        buyOrder1.subtractShares( -numShares ); // reset testing Objects
        sellOrder1.subtractShares( -numShares );
        
        // if orders are both marketOrders and have same #of shares and price
        buyOrders.add( buyOrder2 );
        sellOrders.add( sellOrder2 );
        st.executeOrders();
        assertTrue( buyOrders.isEmpty() ); // check queues
        assertTrue( sellOrders.isEmpty() );
        assertEquals( 0, buyOrder2.getShares() ); // check # of shares
        assertEquals( 0, sellOrder2.getShares() );
        assertEquals( 2 * numShares + 1, st.getVolume() ); // check volume
        assertEquals( price, st.getLoPrice(), 0.0 ); // check prices
        assertEquals( price + 2, st.getLastPrice(), 0.0 );
        assertEquals( price + 2, st.getHiPrice(), 0.0 );
        assertFalse( t1.mailbox().isEmpty() ); // check mailboxes
        assertFalse( t2.mailbox().isEmpty() );
        t1.mailbox().remove(); // clear mailboxes
        t2.mailbox().remove();
        assertTrue( t1.mailbox().isEmpty() ); // should only have one object
        assertTrue( t2.mailbox().isEmpty() );
        buyOrder2.subtractShares( -numShares - 1 ); // reset testing Objects
        sellOrder2.subtractShares( -numShares - 1 );

        // if buy is a limit and sell is a market at different stock #'s
        buyOrders.add( buyOrder3 );
        sellOrders.add( sellOrder2 );
        st.executeOrders();
        assertTrue( buyOrders.isEmpty() ); // check queues
        assertFalse( sellOrders.isEmpty() );
        assertEquals( 0, buyOrder3.getShares() ); // check # of shares
        assertEquals( 1, sellOrder2.getShares() );
        assertEquals( 3 * numShares + 1, st.getVolume() ); // check volume
        assertEquals( price - 1, st.getLoPrice(), 0.0 ); // check prices
        assertEquals( price - 1, st.getLastPrice(), 0.0 );
        assertEquals( price + 2, st.getHiPrice(), 0.0 );
        assertFalse( t1.mailbox().isEmpty() ); // check mailboxes
        assertFalse( t2.mailbox().isEmpty() );
        t1.mailbox().remove(); // clear mailboxes
        t2.mailbox().remove();
        assertTrue( t1.mailbox().isEmpty() ); // should only have one object
        assertTrue( t2.mailbox().isEmpty() );
        buyOrder3.subtractShares( -numShares ); // reset testing Objects
        sellOrder2.subtractShares( -numShares );
        sellOrders.remove();

        // if sell is a limit and buy is a market at different stock #'s
        buyOrders.add( buyOrder2 );
        sellOrders.add( sellOrder3 );
        st.executeOrders();
        assertFalse( buyOrders.isEmpty() ); // check queues
        assertTrue( sellOrders.isEmpty() );
        assertEquals( 1, buyOrder2.getShares() ); // check # of shares
        assertEquals( 0, sellOrder3.getShares() );
        assertEquals( price - 2, st.getLoPrice(), 0.0 ); // check prices
        assertEquals( price - 2, st.getLastPrice(), 0.0 );
        assertEquals( price + 2, st.getHiPrice(), 0.0 );
        assertFalse( t1.mailbox().isEmpty() ); // check mailboxes
        assertFalse( t2.mailbox().isEmpty() );
        t1.mailbox().remove(); // clear mailboxes
        t2.mailbox().remove();
        assertTrue( t1.mailbox().isEmpty() ); // should only have one object
        assertTrue( t2.mailbox().isEmpty() );
        buyOrder2.subtractShares( -numShares ); // reset testing Objects
        sellOrder3.subtractShares( -numShares );
        buyOrders.remove();
        assertTrue( buyOrders.isEmpty() );
        assertTrue( sellOrders.isEmpty() );

        // if there is more than one buy and one sell order
        buyOrders.add( buyOrder2 );
        buyOrders.add( buyOrder3 );
        sellOrders.add( sellOrder2 );
        sellOrders.add( sellOrder3 );
        st.executeOrders();
        assertFalse( buyOrders.isEmpty() ); // check queues
        assertFalse( sellOrders.isEmpty() );
        assertEquals( 0, buyOrder2.getShares() ); // check # of shares
        assertEquals( 0, sellOrder2.getShares() );
        assertEquals( numShares, buyOrder3.getShares() );
        assertEquals( numShares, sellOrder3.getShares() );
        assertEquals( price - 2, st.getLoPrice(), 0.0 ); // check prices
        assertEquals( price - 2, st.getLastPrice(), 0.0 );
        assertEquals( price + 2, st.getHiPrice(), 0.0 );
        assertFalse( t1.mailbox().isEmpty() ); // check mailboxes
        assertFalse( t2.mailbox().isEmpty() );
        t1.mailbox().remove(); // clear mailboxes
        t2.mailbox().remove();
        assertTrue( t1.mailbox().isEmpty() ); // should only have one object
        assertTrue( t2.mailbox().isEmpty() );
        buyOrder2.subtractShares( -numShares ); // reset testing Objects
        sellOrder2.subtractShares( -numShares );

        TradeOrder buyOrder4 = new TradeOrder( t1, symbol, buyOrder, marketOrder, numShares, price - 1 );
        TradeOrder sellOrder4 = new TradeOrder( t2, symbol, !buyOrder, marketOrder, numShares, price - 2 );
        // continue simulation of orders:
        // add one sellOrder
        sellOrders.add( sellOrder4 );
        st.executeOrders();
        assertTrue( buyOrders.isEmpty() );
        assertFalse( sellOrders.isEmpty() );
        assertEquals( "Buy shares left: " + buyOrder3.getShares(), 
                    0, buyOrder3.getShares() ); // check # of shares
        assertEquals( 0, sellOrder4.getShares() );
        assertEquals( numShares, sellOrder3.getShares() );
        assertEquals( price - 2, st.getLoPrice(), 0.0 ); // check prices
        assertEquals( price - 1, st.getLastPrice(), 0.0 );
        assertEquals( price + 2, st.getHiPrice(), 0.0 );
        assertFalse( t1.mailbox().isEmpty() ); // check mailboxes
        assertFalse( t2.mailbox().isEmpty() );
        t1.mailbox().remove(); // clear mailboxes
        t2.mailbox().remove();
        assertTrue( t1.mailbox().isEmpty() ); // should only have one object
        assertTrue( t2.mailbox().isEmpty() );
        buyOrder3.subtractShares( -numShares ); // reset testing Objects
        sellOrder2.subtractShares( -numShares );
        // add one buy order
        buyOrders.add( buyOrder4 );
        st.executeOrders();
        assertTrue( buyOrders.isEmpty() );
        assertTrue( sellOrders.isEmpty() );
        assertEquals( 0, buyOrder4.getShares() ); // check # of shares
        assertEquals( 0, sellOrder3.getShares() );
        assertEquals( price - 2, st.getLoPrice(), 0.0 ); // check prices
        assertEquals( price - 2, st.getLastPrice(), 0.0 );
        assertEquals( price + 2, st.getHiPrice(), 0.0 );
        assertFalse( t1.mailbox().isEmpty() ); // check mailboxes
        assertFalse( t2.mailbox().isEmpty() );
        t1.mailbox().remove(); // clear mailboxes
        t2.mailbox().remove();
        assertTrue( t1.mailbox().isEmpty() ); // should only have one object
        assertTrue( t2.mailbox().isEmpty() );
        
    }

    @Test
    public void stockPlaceOrderTest()
    {
        Stock st = new Stock( symbol, marketName, price );
        Trader t1 = new Trader( null, goodName1, goodPass );
        Trader t2 = new Trader( null, goodName2, goodPass );
        TradeOrder buyOrder1 = new TradeOrder( t1, symbol, buyOrder, !marketOrder, numShares, price );
        TradeOrder sellOrder1 = new TradeOrder( t2, symbol, !buyOrder, !marketOrder, numShares, price );
        String buyMsg = "New order:  Buy " + symbol + " (" + marketName + ")\n"
                       + numShares + " shares at $" + price;
        String sellMsg = "New order:  Sell " + symbol + " (" + marketName + ")\n"
                        + numShares + " shares at $" + price;
        
        // test placing a buyOrder
        st.placeOrder( buyOrder1 );
        assertFalse( st.getBuyOrders().isEmpty() ); // check queues
        assertTrue( st.getSellOrders().isEmpty() );
        assertFalse( t1.mailbox().isEmpty() ); // check mailboxes
        assertTrue( t2.mailbox().isEmpty() );
        st.getBuyOrders().remove(); // reset variables
        String msg = t1.mailbox().remove();
        assertEquals( buyMsg, msg );
        
        // test placing a sellOrder
        st.placeOrder( sellOrder1 );
        assertFalse( st.getSellOrders().isEmpty() ); // check queues
        assertTrue( st.getBuyOrders().isEmpty() );
        assertFalse( t2.mailbox().isEmpty() ); // check mailboxes
        assertTrue( t1.mailbox().isEmpty() );
        st.getSellOrders().remove();
        msg = t2.mailbox().remove();
        assertEquals( sellMsg, msg );
    }
    
    @Test
    public void stockToStringTest()
    {
        assertNotNull( new Stock( symbol, marketName, price ).toString() );
    }
    // Remove block comment below to run JUnit test in console

//    public static junit.framework.Test suite()
//    {
//        return new JUnit4TestAdapter( JUSafeTradeTest.class );
//    }
//    
//    public static void main( String args[] )
//    {
//        org.junit.runner.JUnitCore.main( "JUSafeTradeTest" );
//    }
}

