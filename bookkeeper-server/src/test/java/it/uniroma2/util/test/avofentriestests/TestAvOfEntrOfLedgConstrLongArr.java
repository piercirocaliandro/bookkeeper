package it.uniroma2.util.test.avofentriestests;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.apache.bookkeeper.util.AvailabilityOfEntriesOfLedger;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/* Test the constructors methods that deals with long[] arrays */


@RunWith(value = Parameterized.class)
public class TestAvOfEntrOfLedgConstrLongArr {
	private long[] entriesOfLedger;
	
	private AvailabilityOfEntriesOfLedger aOfEntriesLedg;
	
	
	@Parameters
	public static Collection<long[]> getParams(){
		return Arrays.asList(new long[][] {
			{1L, 2L, 3L}, 
			{3L, 2L, -3L}, 
			{10L, 20L, Long.MAX_VALUE},
			{},
			null
		});
	}
	
	
	public TestAvOfEntrOfLedgConstrLongArr(long[] entries) {
		this.configure(entries);
	}
	
	
	private void configure(long[] entries) {
		this.entriesOfLedger = entries;
	}
	
	
	@Test
	public void testAofEntriesConstr(){
		try {
			this.aOfEntriesLedg = new AvailabilityOfEntriesOfLedger(this.entriesOfLedger);
			assertEquals(this.entriesOfLedger.length, 
					this.aOfEntriesLedg.getTotalNumOfAvailableEntries());
		}catch(NullPointerException e) {
			Logger.getLogger("AOE").log(Level.WARN, "List of long[] must be != null");
		}
	}
}
