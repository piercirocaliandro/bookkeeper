package it.uniroma2.util.test.avofentriestests;


import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.bookkeeper.util.AvailabilityOfEntriesOfLedger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;


@RunWith(value = Parameterized.class)
public class TestAvOfEntryConstByteBuf {
	private ByteBuf bytebuf;
	private AvailabilityOfEntriesOfLedger avOfEntries;
	private Logger logger;
	
	
	@Parameters
	public static Collection<ByteBuf> getParams(){
		
		// these two were added to increment coverage
		ByteBuf full = Unpooled.buffer(128);
		full.writeInt(0);
		full.writeInt(1);
		
		ByteBuf ill = Unpooled.buffer(128);
		ill.writeInt(10);
		
		return Arrays.asList(new ByteBuf[] {
			full,
			ill,
			Unpooled.buffer(128),
			null,
		});
	}
	
	
	public TestAvOfEntryConstByteBuf(ByteBuf byteBuf) {
		this.configure(byteBuf);
	}
	
	
	private void configure(ByteBuf byteBuf) {
		this.bytebuf = byteBuf;
		this.logger = Logger.getLogger("AOE");
	}
	
	
	@Test
	public void testConstrByteBuf() {
		try {
			this.avOfEntries = new AvailabilityOfEntriesOfLedger(this.bytebuf);
			assertNotNull(this.avOfEntries);
		}catch(NullPointerException | IllegalArgumentException e ) {
			this.logger.log(Level.WARNING, "Invalid value for ByteBuf");
		}
	}
}
