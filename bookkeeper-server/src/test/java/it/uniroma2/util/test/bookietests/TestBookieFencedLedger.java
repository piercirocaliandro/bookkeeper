package it.uniroma2.util.test.bookietests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.bookkeeper.bookie.Bookie;
import org.apache.bookkeeper.bookie.BookieException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.google.common.util.concurrent.SettableFuture;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import it.uniroma2.util.test.bookietests.utils.BookieUtils;

/* Test the fencing mode of a Bookie (maybe founded from the documentation, check that later) 
 * and that the method recoveryAddEntry works 
 * */


@RunWith(value=Parameterized.class)
public class TestBookieFencedLedger {
	private Bookie bookie;
	
	private long ledgerId;
	private String masterKey;
	
	
	@Parameters
	public static Collection<Object[]> getParams(){
		return Arrays.asList(new Object[][] {
			{3L,"masterkey"},
			//{1L,"nokey"},
			{1L,""},
			//{1L,null},
			{-1L,"masterkey"},
		});
	}
	
	
	public TestBookieFencedLedger(long ledgerId, String masterKey) {
		this.configure(ledgerId, masterKey);
	}
	
	
	private void configure(long ledgerId, String masterKey) {
		this.ledgerId = ledgerId;
		this.masterKey = masterKey;
		this.bookie = BookieUtils.getBookieInstance();
		
		BookieUtils.addEntries(3, bookie);
	}
	
	
	@Test
	public void testFenceLedger() {
		try {
			// fence the ledger, so from now on it will not be writable
			SettableFuture<Boolean> feat = bookie.fenceLedger(this.ledgerId, 
					this.masterKey.getBytes());
			assertNotNull(feat);		
			// add a new entry
			/*ByteBuf entry = Unpooled.buffer(128);
			entry.writeLong(this.ledgerId);
			entry.writeLong(1L);
			entry.writeBytes(("entry-" + 1L).getBytes());
			
        
			this.bookie.recoveryAddEntry(entry, null, new Object(), "masterkey".getBytes());
			assertEquals(entry, this.bookie.readEntry(this.ledgerId, 1L));*/
        }catch(IOException | BookieException e) {
        	Logger.getLogger("BKF").log(Level.WARNING, "Invalid input parameters");
        } 
	}
}
