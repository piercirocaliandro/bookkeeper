package it.uniroma2.util.test;
import static org.junit.Assert.assertNotNull;

import java.nio.ByteBuffer;

import org.apache.bookkeeper.util.ZeroBuffer;
import org.junit.*;

public class ZeroBufferTest {
	
	@Test
	public void testZeroBuffer(){
		ByteBuffer dst = ByteBuffer.allocate(20);
		ZeroBuffer.put(dst);
		assertNotNull(dst);
	}
}
