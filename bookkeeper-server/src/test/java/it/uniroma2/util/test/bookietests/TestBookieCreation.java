package it.uniroma2.util.test.bookietests;

import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.bookkeeper.bookie.Bookie;
import org.apache.bookkeeper.conf.ServerConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;



@RunWith(value = Parameterized.class)
public class TestBookieCreation {
	private ServerConfiguration conf;
	
	private Bookie bookie;
	
	
	@Parameters
	public static Iterable<Object[]> getParams(){
		return Arrays.asList(new Object[][] { 
            { 8000, true},
            {8001, false},
            {0, true},
            {-1, true},
            { 9000, true},
            { 9001, true},
      });
	}
	
	
	public TestBookieCreation(int bookiePort, boolean isLoopBack) {
		this.conf = new ServerConfiguration();
		this.conf.setAllowLoopback(isLoopBack);
		this.conf.setBookiePort(bookiePort);
	}
	
	@Test
	public void testCreation() throws Exception{
		try {
			this.bookie = new Bookie(conf);
			assertNotNull(this.bookie);
		}catch(RuntimeException e) {
			Logger.getLogger("BT").log(Level.SEVERE, "Cannot listen on 127.0.0.1 \n");
		}
	}
}
