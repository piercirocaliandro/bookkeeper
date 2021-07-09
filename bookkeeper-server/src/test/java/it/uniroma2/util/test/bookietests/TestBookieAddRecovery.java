package it.uniroma2.util.test.bookietests;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.bookkeeper.bookie.Bookie;
import org.apache.bookkeeper.bookie.BookieException;
import org.apache.bookkeeper.proto.BookkeeperInternalCallbacks.WriteCallback;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import it.uniroma2.util.test.bookietests.utils.BookieUtils;

/* Test the adding of an entry to a previously fenced ledger */


@RunWith(value = Parameterized.class)
public class TestBookieAddRecovery {
	private ByteBuf entry;
	private WriteCallback cb;
	private Object ctx;
	private byte[] masterKey;
	
	private Bookie bookie;
	
	
	@Parameters
	public static Collection<Object[]> getParams(){
		
		//create a valid instance of the ByteBuf
		ByteBuf entry = Unpooled.buffer(128);
		entry.writeLong(1L);
		entry.writeLong(1L);
		entry.writeBytes(("entry-" + 1L).getBytes());
		
		return Arrays.asList(new Object[][] {
			{entry, mock(WriteCallback.class), new Object(), "masterkey".getBytes()},
			{entry, null, new Object(), "masterkey".getBytes()},
			{entry, mock(WriteCallback.class), null, "masterkey".getBytes()},
			{Unpooled.buffer(128), mock(WriteCallback.class), new Object(), "masterkey".getBytes()},
			{entry, mock(WriteCallback.class), new Object(), "masterkey".getBytes()},
			
			//added to increment coverage
			{entry, mock(WriteCallback.class), new Object(), "".getBytes()},
		});
	}
	
	
	public TestBookieAddRecovery(ByteBuf entry, WriteCallback cb, Object ctx, byte[] masterKey) 
			throws IOException, BookieException {
		this.configure(entry, cb, ctx, masterKey);
	}
	
	
	private void configure(ByteBuf entry, WriteCallback cb, Object ctx, byte[] masterKey) 
			throws IOException, BookieException {
		this.entry = entry;
		this.cb = cb;
		this.ctx = ctx;
		this.masterKey = masterKey;
		
		this.bookie = BookieUtils.getBookieInstance();
		this.bookie.fenceLedger(1L, this.masterKey);
	}
	
	
	@Test
	public void testAddRecovery() {
		try {
			this.bookie.recoveryAddEntry(this.entry, this.cb, this.ctx, this.masterKey);
			assertEquals(this.entry, this.bookie.readEntry(1L, 1L));
		} catch (IOException | BookieException | InterruptedException e) {
			Logger.getLogger("BAR").log(Level.WARNING, "Failed to add recovery\n");
		}
	}
}
