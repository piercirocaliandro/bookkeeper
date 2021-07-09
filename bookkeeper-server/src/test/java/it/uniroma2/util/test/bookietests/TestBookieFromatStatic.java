package it.uniroma2.util.test.bookietests;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;

import org.apache.bookkeeper.bookie.Bookie;
import org.apache.bookkeeper.conf.ServerConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/* Another test on a static Bookie method
 * 
 * N.B: added to increment coverage 
 * */


@RunWith(value = Parameterized.class)
public class TestBookieFromatStatic {
	private ServerConfiguration conf;
	private boolean isInteractive;
	private boolean force;
	
	
	@Parameters
	public static Collection<Object[]> getParams(){
		ServerConfiguration fullConf = new ServerConfiguration();
		File journDir1 = new File("jour1");
		File journDir2 = new File("jour2");
		journDir1.mkdir();
		journDir2.mkdir();
		String[] jourDirs = {"jour1", "jour2"};
		fullConf.setJournalDirsName(jourDirs);
		
		/* the true value requires user interaction therefore can't be tested, 
		cause maven will stuck */
		return Arrays.asList(new Object[][] {
			{fullConf, false, true},
			{new ServerConfiguration(), false, true},
			{new ServerConfiguration(), false, true},
			{new ServerConfiguration(), false, false},
			//{null, true, true},
		});
	}
	
	
	public TestBookieFromatStatic(ServerConfiguration conf, boolean isInteractive, boolean force) {
		this.conf = conf;
		this.isInteractive = isInteractive;
		this.force = force;
	}
	
	
	@Test
	public void testFormatConf() {
		assertTrue(Bookie.format(this.conf, this.isInteractive, this.force));
	}
}
