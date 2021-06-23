package it.uniroma2.tests;

import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.bookkeeper.stats.codahale.FastTimer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;


@RunWith(value = Parameterized.class)
public class TestFastTimerCreation {
	private Logger ftLog;
	private int windowVal;
	private FastTimer.Buckets buckets;
	
	
	@Parameters
	public static Iterable<Object[]> getParams(){
		return Arrays.asList(new Object[][] { 
            { FastTimer.Buckets.coarse, -10}, //this will trigger exception
            { FastTimer.Buckets.fine, 0}, 
            { FastTimer.Buckets.none, Integer.MAX_VALUE}, //this will trigger exception
            { FastTimer.Buckets.none, 100}
      });
	}
	
	
	public TestFastTimerCreation(FastTimer.Buckets buckets, int windowVal) {
		this.buckets = buckets;
		this.windowVal = windowVal;
		this.ftLog = Logger.getLogger("FTCR");
	}
	
	/* Test cases */
	
	@Test
	public void testFastTimer() {
		try {
			FastTimer ft = new FastTimer(this.windowVal, this.buckets);
			assertNotNull(ft); //simple assert, just to check that everything is fine
		}catch(NegativeArraySizeException e) {
			this.ftLog.log(Level.WARNING, "Error creating FastTimer. Window size must be >= 0 \n");
		}
	}
}
