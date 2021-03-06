package com.gamesbykevin.cryptobot.util;

import lombok.extern.log4j.Log4j;

import java.io.FileInputStream;

import static com.gamesbykevin.cryptobot.broker.BrokerManager.FUNDS;
import static com.gamesbykevin.cryptobot.broker.BrokerManager.SLEEP;
import static com.gamesbykevin.cryptobot.order.Order.ATTEMPTS_LIMIT;
import static com.gamesbykevin.cryptobot.util.Email.EMAIL_NOTIFICATION_ADDRESS;

@Log4j
public class Properties {

    //object used to access our properties
    private static java.util.Properties PROPERTIES;

    //location of property file
    public static final String PROPERTY_FILE = "./config.properties";

    /**
     * Are we paper trading? (aka not using real money)
     */
    public static final boolean PAPER_TRADING = Boolean.parseBoolean(getProperty("paperTrading"));

    /**
     * Are we submitting limit orders or market orders?
     */
    public static final boolean LIMIT_ORDERS = Boolean.parseBoolean(getProperty("limitOrders"));

    public static final String[] STRATEGIES = getProperty("strategies").split(",");
    public static final String[] DATA_FEED_URL = getProperty("dataFeedUrl").split(",");
    public static final String[] TICKER_PRICE_URL = getProperty("tickerPriceUrl").split(",");

    public static String getProperty(String key) {

        try {

            //if null, instantiate and load
            if (PROPERTIES == null) {

                //loading...
                log.info("Loading properties: " + PROPERTY_FILE);

                //create our object
                PROPERTIES = new java.util.Properties();

                try {

                    //load file from same directory
                    PROPERTIES.load(new FileInputStream(PROPERTY_FILE));

                } catch (Exception e) {

                    //if we couldn't find the property file we must be testing in our IDE
                    PROPERTIES.load(Properties.class.getClassLoader().getResourceAsStream(PROPERTY_FILE));

                }
            }

            //return our property
            return PROPERTIES.getProperty(key);

        } catch (Exception ex) {

            //display error
            log.error(ex.getMessage(), ex);

            //exit with error
            System.exit(1);
        }

        return null;
    }

    public static void display() {

        //now that we just loaded the properties print values
        log.info("Thread sleep: " + SLEEP);
        log.info("Funds $" + FUNDS);
        log.info("Paper trading: " + PAPER_TRADING);
        log.info("Limit orders: " + LIMIT_ORDERS);
        log.info("Check order status attempts: " + ATTEMPTS_LIMIT);
        log.info("Email notification: " + EMAIL_NOTIFICATION_ADDRESS);
    }
}