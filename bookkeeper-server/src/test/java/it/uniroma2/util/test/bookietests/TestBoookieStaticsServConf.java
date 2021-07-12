package it.uniroma2.util.test.bookietests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.bookkeeper.bookie.Bookie;
import org.apache.bookkeeper.conf.ServerConfiguration;
import org.apache.bookkeeper.net.BookieId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/* Test some statics methods that deals with ServerConfiguration  */


@RunWith(value = Parameterized.class)
public class TestBoookieStaticsServConf {
	private ServerConfiguration conf;
	private Logger logger;
	
	
	@Parameters
	public static Collection<ServerConfiguration> getParams(){
		ServerConfiguration validConf = new ServerConfiguration();
		validConf.setBookieId("test-bookieid");
		validConf.setAdvertisedAddress("10.10.10.1");
		validConf.setBookiePort(8000);
		
		ServerConfiguration iFaceConf = new ServerConfiguration();
		iFaceConf.setListeningInterface("10.10.10.2");
		
		ServerConfiguration useHostName = new ServerConfiguration();
		useHostName.setUseHostNameAsBookieID(true);
		useHostName.setUseShortHostName(true);
		useHostName.setAllowLoopback(true);
		
		ServerConfiguration useHostName2 = new ServerConfiguration();
		useHostName2.setUseHostNameAsBookieID(true);
		useHostName2.setUseShortHostName(false);
		useHostName2.setAllowLoopback(true);
		
		ServerConfiguration emptyConf = new ServerConfiguration();
		emptyConf.setAllowLoopback(true);
		
		ServerConfiguration loop = new ServerConfiguration();
		loop.setAllowLoopback(true);
		loop.setListeningInterface("127.0.0.1");
		
		
		//these will raise exceptions
		ServerConfiguration noLoop = new ServerConfiguration();
		noLoop.setAllowLoopback(false);
		noLoop.setListeningInterface("127.0.0.1");
		
		ServerConfiguration invalidInet = new ServerConfiguration();
		noLoop.setAllowLoopback(false);
		noLoop.setListeningInterface("256.255.255.255");
		
		
		return Arrays.asList(new ServerConfiguration[] {
				validConf,
				iFaceConf,
				useHostName,
				useHostName2,
				emptyConf,
				loop,
				noLoop,
				invalidInet
		});
	}
	
	
	public TestBoookieStaticsServConf(ServerConfiguration conf) {
		this.conf = conf;
		this.logger = Logger.getLogger("BSM");
	}
	
	
	@Test
	public void testBookieStatics() {
		BookieId bkId;
		try {
			if(this.conf.getBookieId() != null) {
				bkId = Bookie.getBookieId(this.conf);
				
				assertEquals(this.conf.getBookieId(), bkId.getId());
				assertEquals(this.conf.getAdvertisedAddress(), 
						Bookie.getBookieAddress(this.conf).getHostName());
				assertEquals(this.conf.getBookiePort(), 
						Bookie.getBookieAddress(this.conf).getPort());
			}
			else {
				assertEquals(3181, Bookie.getBookieAddress(this.conf).getPort());
			}
			assertNotNull(Bookie.getBookieId(this.conf));
		} catch (UnknownHostException e) {
			this.logger.log(Level.SEVERE, "Errors in the ServerConfiguration\\n");
		}
	}
}
