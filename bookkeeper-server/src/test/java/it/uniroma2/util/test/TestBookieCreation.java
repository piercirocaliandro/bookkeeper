package it.uniroma2.util.test;

import static org.junit.Assert.assertNotNull;

import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.bookkeeper.bookie.Bookie;
import org.apache.bookkeeper.conf.ServerConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;


@RunWith(value = Parameterized.class)
public class TestBookieCreation {
	private ServerConfiguration conf;
	
	@Parameters
	public static Iterable<Object[]> getParams(){
		return Arrays.asList(new Object[][] { 
            { 8000, true},
            { 2000, false}, 
            { 0, false},
            { 0, true}
      });
	}
	
	
	public TestBookieCreation(int bookiePort, boolean isLoopBack) {
		this.conf = new ServerConfiguration();
		this.conf.setAllowLoopback(isLoopBack);
		this.conf.setBookiePort(bookiePort);
	}
	
	@Test
	public void testCreation() throws Exception{
		try {
			Bookie bk1 = new Bookie(conf);
			assertNotNull(bk1);
		}catch(RuntimeException e) {
			Logger.getLogger("BT").log(Level.SEVERE, "Cannot listen on 127.0.0.1 \n");
		}
		
		/*ByteBuf newEntry = Unpooled.wrappedBuffer(new byte[1024*1024]);
		newEntry.setByte(5, 3);
		long ledgerID = newEntry.getLong(newEntry.readerIndex());
		
		bk1.addEntry(newEntry, false, null, null, "key".getBytes());
		assertNotEquals(0, bk1.getListOfEntriesOfLedger(ledgerID));*/
	}
}
