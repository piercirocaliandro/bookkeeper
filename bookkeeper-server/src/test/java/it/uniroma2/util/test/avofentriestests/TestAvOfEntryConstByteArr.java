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

/* Test the other constructors for */


@RunWith(value = Parameterized.class)
public class TestAvOfEntryConstByteArr {
	private byte[] serStateOfEntr;
	
	private AvailabilityOfEntriesOfLedger avOfEntr;
	
	
	@Parameters
	public static Collection<byte[]> getParams(){
		
		// added to increment coverage
		
		byte[] ill = new byte[256];
		for(int i = 0; i < 256; i++)
			ill[i] = 1;
		
		/*byte[] valid = new byte[128];
		for(int i = 0; i <= 57; i++)
			valid[i] = 0;
		
		for(int j = 58; j < 128; j++)
			valid[j] = 1;*/
			
		return Arrays.asList(new byte[][] {
			ill,
			//valid,
			new byte[256],
			null
		});
	}
	
	
	public TestAvOfEntryConstByteArr(byte[] serState) {
		this.serStateOfEntr = serState;
	}
	
	
	@Test
	public void testConstrWithByteArr() {
		try {
			this.avOfEntr = new AvailabilityOfEntriesOfLedger(this.serStateOfEntr);
			assertNotNull(this.avOfEntr);
		}catch(NullPointerException | ArrayIndexOutOfBoundsException | IllegalArgumentException e) {
			Logger.getLogger("AOE").log(Level.WARNING, "Invalid value for byte[] array");
		}
	}
}
