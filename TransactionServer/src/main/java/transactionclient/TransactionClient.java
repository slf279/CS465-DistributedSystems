package transactionclient;

import java.io.*;
import java.net.*;
import java.util.*;
import utils.*;


public class TransactionClient
{
    public static SystemLog log = new SystemLog("server.log");
    
    public static void main(String[] args) throws IOException
    {
        String configFile = "TransactionClient.properties"; // default property file name

        // if there is one arg passed when the program is loaded
        if ( args.length == 1 )
        {
            configFile = args[0]; // get client properties file name as arg
        }
        
        // read and store config data from server properties file
        PropertyHandler configData = new PropertyHandler(configFile);
        
        // get number of transactions to do
        int numTransactions = Integer.parseInt(configData.getProperty("NUMBER_TRANSACTIONS"));

        Proxy ops;
        Thread[] working = new Thread[numTransactions];
        
        // create new thread for every transaction
        for (int transNum = 0; transNum < numTransactions; transNum++){
            ops = new Proxy(configData);
            Thread thread = new Thread(ops);
            thread.start();
            
            working[transNum] = thread;
        }
        while (Thread.activeCount() > 1){
            // wait
        }
        log.close();
        
    }
    
}
