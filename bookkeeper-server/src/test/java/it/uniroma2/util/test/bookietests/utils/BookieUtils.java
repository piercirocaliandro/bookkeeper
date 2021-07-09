package it.uniroma2.util.test.bookietests.utils;

import java.io.IOException;

import org.apache.bookkeeper.bookie.Bookie;
import org.apache.bookkeeper.bookie.BookieException;
import org.apache.bookkeeper.conf.ServerConfiguration;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/* Various utilities to test Bookie class */


public class BookieUtils {
	
	public static Bookie getBookieInstance() {
		ServerConfiguration conf = new ServerConfiguration();
		conf.setAllowLoopback(true);
		conf.setBookiePort(8000);
				
		try {
			return new Bookie(conf);
		} catch (IOException | InterruptedException | BookieException e) {
			Logger.getLogger("TBE").log(Level.WARN, "failed to create Bookie \n");
		}
		return null;
	}
	
	
	public static void addEntries(long cap, Bookie bookie) {
		for(long ledgerId = 0; ledgerId < cap; ledgerId++) {
			for(long entryId = 0; entryId < cap; entryId++) {
				ByteBuf entry = Unpooled.buffer(128);
				entry.writeLong(ledgerId);
				entry.writeLong(entryId);
		        entry.writeBytes(("entry-" + entryId).getBytes());
		        
		        try {
					bookie.addEntry(entry, false, null, null, "masterkey".getBytes());
				} catch (IOException e) {
					Logger.getLogger("TBE").log(Level.WARN, "IOExc while adding entry \n");
				} catch (BookieException e) {
					Logger.getLogger("TBE").log(Level.WARN, "BookieExc while adding entry \\n");
				} catch (InterruptedException e) {
					Logger.getLogger("TBE").log(Level.WARN, "InterrExc while adding entry \\n \n");
				}
			}
		}
	}
}
