package it.uniroma2.util.test.bookietests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import org.apache.bookkeeper.bookie.Bookie;
import org.apache.bookkeeper.bookie.BookieException;
import org.apache.bookkeeper.conf.ServerConfiguration;
import org.apache.bookkeeper.proto.BookkeeperInternalCallbacks.WriteCallback;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import it.uniroma2.util.test.bookietests.utils.BookieUtils;

@RunWith(value=Parameterized.class)
public class TestBookieAddEntry {
	private Bookie bookie;
	private ServerConfiguration conf;
	
	// parameters for the method
	private ByteBuf entry;
	private boolean ackBeforeSync;
	private WriteCallback cb;
	private Object ctx;
	private byte[] masterKey;
	
	//useful for testing
	private long ledgerId;
	private long entryId;
	
	
	@Parameters
	public static Collection<Object[]> getParams(){
		
		/* the creation of the ByteBuf is made in the configure() method, because
		 * it needs some values to be set. In this case, the first long parameter is the ledger id
		 * and the second one is the entry id
		 */
		return Arrays.asList(new Object[][] {
			{1L, 1L, false, mock(WriteCallback.class), new Object(), "key".getBytes()},
			{1L, 1L, false, null, new Object(), "key".getBytes()},
			{1L, 1L, true, mock(WriteCallback.class), new Object(), "key".getBytes()},
			{0L, 0L, false, 
				mock(WriteCallback.class), new Object(), "key".getBytes()},
			{Long.MAX_VALUE, 1L, false, mock(WriteCallback.class), null, "key".getBytes()},
			{1L, Long.MAX_VALUE, false, mock(WriteCallback.class), new Object(), "".getBytes()},
		});
	}
	
	
	public TestBookieAddEntry(long ledgerId, long entryId, boolean ackBeforeSync, 
			WriteCallback cb, Object ctx, byte[] masterKey) {
		this.configure(ledgerId, entryId, ackBeforeSync, cb, ctx, masterKey);
	}
	
	
	private void configure(long ledgerId, long entryId, boolean ackBeforeSync, 
			WriteCallback cb, Object ctx, byte[] masterKey) {
		
		//create the entry
		this.entry = Unpooled.buffer(128);
		this.entry.writeLong(ledgerId);
		this.entry.writeLong(entryId);
        this.entry.writeBytes(("entry-" + entryId).getBytes());
		
		this.ackBeforeSync = ackBeforeSync;
		this.cb = cb;
		this.ctx = ctx;
		this.masterKey = masterKey;
		
		this.ledgerId = ledgerId;
		this.entryId = entryId;
		
		// create the instance of Bookie
		this.conf = new ServerConfiguration();
		this.conf.setAllowLoopback(true);
		this.conf.setBookiePort(8000);
		
		this.bookie = BookieUtils.getBookieInstance();
	}
	
	
	@Test
	public void testEntryCreation() 
			throws IOException, BookieException, InterruptedException {
		
		this.bookie.addEntry(this.entry, this.ackBeforeSync, this.cb, this.ctx, this.masterKey);
		assertEquals(this.entry, this.bookie.readEntry(this.ledgerId, this.entryId));
		assertTrue(this.bookie.readLastAddConfirmed(this.ledgerId) > 0);
	}
}
