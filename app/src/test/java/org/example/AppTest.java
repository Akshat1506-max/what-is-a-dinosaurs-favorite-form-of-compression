package org.example;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class AppTest {
    @Test
    void testCompressionAndDecompression() {
        String text = "Hello World";
        App.main(new String[]{});
        String compressed = App.compress(text);
        String decompressed = App.decompress(compressed);
        assertEquals(text, decompressed);
    }
}