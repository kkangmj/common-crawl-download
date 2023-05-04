package org.mangoo.commoncrawl;

import org.apache.commons.io.HexDump;
import org.mangoo.poi.util.LittleEndian;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class DocumentLocationTest {
    @Test
    public void testGetLong() throws IOException {
        byte[] array = new byte[] { (byte)0x91, (byte)0xE9, 0x1D, (byte)0x98, 0x39, 0x01, 0x00, 0x00 };
        HexDump.dump(array, 0, System.out, 0);

        int offset = 0;
//        System.out.println(((long) (array[offset + 7] & 0xff) << 56));
//        System.out.println(((long) (array[offset + 6] & 0xff) << 48));
//        System.out.println(((long) (array[offset + 5] & 0xff) << 40));
//        System.out.println(((long) (array[offset + 4] & 0xff) << 32));
//        System.out.println(((long) (array[offset + 3] & 0xff) << 24));
//        System.out.println(((long) (array[offset + 2] & 0xff) << 16));
//        System.out.println(((long) (array[offset + 1] & 0xff) << 8));
//        System.out.println(((long) (array[offset + 0] & 0xff) << 0));

//        System.out.println(((long) (array[offset + 5] & 0xff) << 40) |
//                ((long) (array[offset + 4] & 0xff) << 32));

//        System.out.println(BlockHeader.getLong(array, offset));

        assertEquals(1346876860817L, LittleEndian.getLong(array, offset));
    }
}
