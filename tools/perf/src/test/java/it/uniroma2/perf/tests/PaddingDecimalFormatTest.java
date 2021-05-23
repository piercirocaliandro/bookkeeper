package it.uniroma2.perf.tests;
import static org.junit.Assert.assertFalse;

import org.apache.bookkeeper.tools.perf.utils.PaddingDecimalFormat;
import org.junit.*;

public class PaddingDecimalFormatTest {
	
	@Test
	public void testPaddingBranch1() {
		PaddingDecimalFormat pdf = new PaddingDecimalFormat("%s", 10);
		Object temp = "1234";
		assertFalse(pdf.equals(temp));
	}
}
