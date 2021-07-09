package it.uniroma2.util.test.avofentriestests;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.BitSet;
import java.util.Collection;
import java.util.List;

import org.apache.bookkeeper.util.AvailabilityOfEntriesOfLedger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;



@RunWith(value = Parameterized.class)
public class TestAvailabilityOfEntriesOfLedgerUnavEntries {
	private AvailabilityOfEntriesOfLedger avOfEntries;
	private long[] entries = {1L, 2L, 3L, 4L, 5L, 6L};
	
	private long startId;
	private long lastId;
	private BitSet availOfEntries;
	
	
	@Parameters
	public static Collection<Object[]> getParams(){
		BitSet bs1 = new BitSet(6);
		for(int i = 0; i < 5; i++)
			bs1.set(i, true);
		
		// added trying to increase coverage
		BitSet bs2 = new BitSet(6);
		for(int i = 0; i <= 2; i++)
			bs2.set(i, false);
		for(int i = 3; i < 6; i++)
			bs2.set(i, true);
		
		BitSet bs3 = new BitSet(6);
		for(int i = 0; i <= 3; i++)
			bs2.set(i, true);
		for(int i = 3; i < 6; i++)
			bs2.set(i, false);
		
		
		return Arrays.asList(new Object[][] {
			{1L, 2L, bs1},
			{2L, 1L, bs1},
			//{-1L, 0L, new BitSet(10)},
			{1L, 2L, bs1},
			{0L, 0L, bs1},
			{7L, 8L, bs1},
			
			//added to increase coverage
			{1L, 2L, bs2},
			{1L, 2L, bs3},
			//{1L, 10L, bs3},
		});
	}
	
	
	public TestAvailabilityOfEntriesOfLedgerUnavEntries(long startId, long lastId, BitSet avail) {
		this.configure(startId, lastId, avail);
	}
	
	
	private void configure(long startId, long lastId, BitSet avail) {
		this.startId = startId;
		this.lastId = lastId;
		this.availOfEntries = avail;
		
		this.avOfEntries = new AvailabilityOfEntriesOfLedger(this.entries);
	}
	
	
	@Test
	public void testUnavEntries() {
		List<Long> unavEntries = this.avOfEntries.getUnavailableEntries(startId, lastId, 
				availOfEntries);
		
		boolean found = false;
		
		for(long l : this.entries) {
			if(l == this.lastId) {
				assertEquals(0, unavEntries.size());
				found = true;
				break;
			}
		}
		
		if(!found)
			assertEquals(unavEntries.size(), (this.lastId - this.startId)+1);
	}
}
