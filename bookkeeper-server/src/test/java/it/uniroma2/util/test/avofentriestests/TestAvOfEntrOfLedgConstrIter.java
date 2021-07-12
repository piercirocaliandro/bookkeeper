package it.uniroma2.util.test.avofentriestests;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;
import java.util.PrimitiveIterator;

import org.apache.bookkeeper.util.AvailabilityOfEntriesOfLedger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/* Test the constructors methods that deals with long[] arrays */


@RunWith(value = Parameterized.class)
public class TestAvOfEntrOfLedgConstrIter {
	private PrimitiveIterator.OfLong primitiveIterator;
	private long[] entriesOfLedger;
	
	private AvailabilityOfEntriesOfLedger aOfEntriesLedgWithIt;
	
	
	@Parameters
	public static Collection<long[]> getParams(){
		return Arrays.asList(new long[][] {
			{1L, 2L, 3L}, 
			{3L, 2L, -3L}, 
			{10L, 20L, Long.MAX_VALUE},
			{},
		});
	}
	
	
	public TestAvOfEntrOfLedgConstrIter(long[] entries) {
		this.configure(entries);
	}
	
	
	private void configure(long[] entries) {
		this.entriesOfLedger = entries;
		this.primitiveIterator = Arrays.stream(entries).iterator();
	}
	
	
	@Test
	public void testAofEntriesConstrWithIter() {
		this.aOfEntriesLedgWithIt = new AvailabilityOfEntriesOfLedger(this.primitiveIterator);
		assertEquals(this.entriesOfLedger.length, 
				this.aOfEntriesLedgWithIt.getTotalNumOfAvailableEntries());
	}
}
