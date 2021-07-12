package it.uniroma2.util.test.bookietests;

import static org.junit.Assert.assertEquals;
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

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import it.uniroma2.util.test.bookietests.utils.BookieUtils;


@RunWith(value = Parameterized.class)
public class TestBookieGetListOfEntries {
	private long ledgerId;
	
	private Bookie bookie;
	private Logger logger;
	
	
	@Parameters
	public static Collection<Long> getParams(){
		return Arrays.asList(new Long[] {
			1L,
			0L,
		});
	}
	
	
	public TestBookieGetListOfEntries(long ledgerId) 
			throws IOException, BookieException, InterruptedException {
		this.configure(ledgerId);
	}
	
	
	private void configure(long ledgerId) throws 
	IOException, BookieException, InterruptedException {
		this.ledgerId = ledgerId;
		
		//create the entry
		ByteBuf entry = Unpooled.buffer(128);
		entry.writeLong(1L);
		entry.writeLong(1L);
		entry.writeBytes(("entry-" + 1L).getBytes());
		
		this.logger = Logger.getLogger("BGE");
		this.bookie = BookieUtils.getBookieInstance();
		this.bookie.addEntry(entry, false, null, bookie, "key".getBytes());
	}
	
	
	@Test
	public void testEntryCreation() {
		 
		try {
			if(this.bookie.getListOfEntriesOfLedger(this.ledgerId) != null)
				assertTrue(this.bookie.getListOfEntriesOfLedger(this.ledgerId).hasNext());
			else
				assertEquals(null, this.bookie.getListOfEntriesOfLedger(this.ledgerId));
		} catch (IOException e) {
			this.logger.log(Level.SEVERE, "An error occurred during the test \n");
		}
	}
}
