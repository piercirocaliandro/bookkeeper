package it.uniroma2.tests;

import static org.junit.Assert.assertNotEquals;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.apache.bookkeeper.stats.codahale.FastTimer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(value = Parameterized.class)
public class TestFastTimerEventCount {
	private long duration;
	private TimeUnit unit;
	
	
	@Parameters
	public static Iterable<Object[]> getParams(){
		return Arrays.asList(new Object[][] { 
            { -1L, TimeUnit.DAYS},
            { 10L, TimeUnit.HOURS}, 
            { Long.MAX_VALUE, TimeUnit.DAYS},
            { 0L, TimeUnit.MICROSECONDS},
            { 100L, TimeUnit.MILLISECONDS},
            { 100L, TimeUnit.MINUTES},
            { 100L, TimeUnit.NANOSECONDS},
            { 100L, TimeUnit.SECONDS},
      });
	}
	
	
	public TestFastTimerEventCount(long duration, TimeUnit unit) {
		this.duration = duration;
		this.unit = unit;
	}
	
	
	@Test
	public void testTimeEventCount() {
		FastTimer ft = new FastTimer();
		long countBefore = ft.getCount();
		
		// add a new event
		ft.update(this.duration, this.unit);
		long countAfter = ft.getCount();
		
		assertNotEquals(countBefore, countAfter);
	}
}
