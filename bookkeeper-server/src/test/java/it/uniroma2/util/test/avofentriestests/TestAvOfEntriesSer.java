package it.uniroma2.util.test.avofentriestests;

import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.Collection;

import org.apache.bookkeeper.util.AvailabilityOfEntriesOfLedger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;


@RunWith(value = Parameterized.class)
public class TestAvOfEntriesSer {
	private long[] entries;
	private AvailabilityOfEntriesOfLedger avOfLedg;
	
	
	@Parameters
	public static Collection<long[]> getParams(){
		return Arrays.asList(new long[][] {
			{1L, 2L, 3L, 4L, 5L, 6L},
			{-1L, -2L, -3L, -4L, -5L, -6L},
			{},
		});
	}
	
	
	public TestAvOfEntriesSer(long[] entries) {
		this.configure(entries);
	}
	
	
	private void configure(long[] entries) {
		this.entries = entries;
		this.avOfLedg = new AvailabilityOfEntriesOfLedger(this.entries);
	}
	
	
	@Test
	public void testAvOfEntrSer() {
		assertNotNull(this.avOfLedg.serializeStateOfEntriesOfLedger());
	}
}
