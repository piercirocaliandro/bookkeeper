package it.uniroma2.util.test.avofentriestests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;

import org.apache.bookkeeper.util.AvailabilityOfEntriesOfLedger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(value = Parameterized.class)
public class TestAvOfEntriesHasEntry {
	private long isAvEntries;
	
	private long[] entries = {1,2,3,4,5,6};
	private AvailabilityOfEntriesOfLedger avOfLedg;
	
	
	@Parameters
	public static Collection<Long> getParams(){
		return Arrays.asList(new Long[]{
			1L,
			-1L,
			3L,
			Long.MAX_VALUE
		});
	}
	
	
	public TestAvOfEntriesHasEntry(long entry) {
		this.configure(entry);
	}
	
	
	private void configure(long entry) {
		this.isAvEntries = entry;
		this.avOfLedg = new AvailabilityOfEntriesOfLedger(this.entries);
	}
	
	
	@Test
	public void testIfAvail() {
		boolean found = false;
		for(long l : this.entries) {
			if(l == this.isAvEntries) {
				assertTrue(this.avOfLedg.isEntryAvailable(this.isAvEntries));
				found = true;
				break;
			}
		}
		
		if(!found)
		assertFalse(this.avOfLedg.isEntryAvailable(this.isAvEntries));
	}
}
