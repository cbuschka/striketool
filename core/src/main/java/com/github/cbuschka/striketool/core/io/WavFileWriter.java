package com.github.cbuschka.striketool.core.io;

import java.io.*;

public class WavFileWriter implements AutoCloseable {
    private final static int BUFFER_SIZE = 4096;
    private final static int FMT_CHUNK_ID = 0x20746D66;
    private final static int DATA_CHUNK_ID = 0x61746164;
    private final static int RIFF_CHUNK_ID = 0x46464952;
    private final static int RIFF_TYPE_ID = 0x45564157;

    private int bytesPerSample;            // Number of bytes required to store a single sample
    private long numFrames;                    // Number of frames within the data section
    private OutputStream oStream;    // Output stream used for writting data
    private double floatScale;                // Scaling factor used for int <-> float conversion
    private double floatOffset;            // Offset factor used for int <-> float conversion
    private boolean wordAlignAdjust;        // Specify if an extra byte at the end of the data chunk is required for word alignment

    // Wav Header
    private int numChannels;                // 2 bytes unsigned, 0x0001 (1) to 0xFFFF (65,535)
    private long sampleRate;                // 4 bytes unsigned, 0x00000001 (1) to 0xFFFFFFFF (4,294,967,295)
    // Although a java int is 4 bytes, it is signed, so need to use a long
    private int blockAlign;                    // 2 bytes unsigned, 0x0001 (1) to 0xFFFF (65,535)
    private int validBits;                    // 2 bytes unsigned, 0x0002 (2) to 0xFFFF (65,535)

    // Buffering
    private byte[] buffer;                    // Local buffer used for IO
    private int bufferPointer;                // Points to the current position in local buffer
    private long frameCounter;                // Current number of frames read or written

    private WavFileWriter() {
        buffer = new byte[BUFFER_SIZE];
    }

    public static WavFileWriter newWavFile(OutputStream out, int numChannels, long numFrames, int validBits, long sampleRate) throws IOException {
        // Instantiate new Wavfile and initialise
        WavFileWriter wavFile = new WavFileWriter();
        wavFile.numChannels = numChannels;
        wavFile.numFrames = numFrames;
        wavFile.sampleRate = sampleRate;
        wavFile.bytesPerSample = (validBits + 7) / 8;
        wavFile.blockAlign = wavFile.bytesPerSample * numChannels;
        wavFile.validBits = validBits;
        wavFile.oStream = out;

        if (numChannels < 1 || numChannels > 65535)
            throw new IOException("Illegal number of channels, valid range 1 to 65536");
        if (numFrames < 0) throw new IOException("Number of frames must be positive");
        if (validBits < 2 || validBits > 65535)
            throw new IOException("Illegal number of valid bits, valid range 2 to 65536");
        if (sampleRate < 0) throw new IOException("Sample rate must be positive");


        long dataChunkSize = wavFile.blockAlign * numFrames;
        long mainChunkSize = 4 +    // Riff Type
                8 +    // Format ID and size
                16 +    // Format data
                8 +    // Data ID and size
                dataChunkSize;

        if (dataChunkSize % 2 == 1) {
            mainChunkSize += 1;
            wavFile.wordAlignAdjust = true;
        } else {
            wavFile.wordAlignAdjust = false;
        }

        // Set the main chunk size
        putLE(RIFF_CHUNK_ID, wavFile.buffer, 0, 4);
        putLE(mainChunkSize, wavFile.buffer, 4, 4);
        putLE(RIFF_TYPE_ID, wavFile.buffer, 8, 4);

        // Write out the header
        wavFile.oStream.write(wavFile.buffer, 0, 12);

        // Put format data in buffer
        long averageBytesPerSecond = sampleRate * wavFile.blockAlign;

        putLE(FMT_CHUNK_ID, wavFile.buffer, 0, 4);        // Chunk ID
        putLE(16, wavFile.buffer, 4, 4);        // Chunk Data Size
        putLE(1, wavFile.buffer, 8, 2);        // Compression Code (Uncompressed)
        putLE(numChannels, wavFile.buffer, 10, 2);        // Number of channels
        putLE(sampleRate, wavFile.buffer, 12, 4);        // Sample Rate
        putLE(averageBytesPerSecond, wavFile.buffer, 16, 4);        // Average Bytes Per Second
        putLE(wavFile.blockAlign, wavFile.buffer, 20, 2);        // Block Align
        putLE(validBits, wavFile.buffer, 22, 2);        // Valid Bits

        // Write Format Chunk
        wavFile.oStream.write(wavFile.buffer, 0, 24);

        // Start Data Chunk
        putLE(DATA_CHUNK_ID, wavFile.buffer, 0, 4);        // Chunk ID
        putLE(dataChunkSize, wavFile.buffer, 4, 4);        // Chunk Data Size

        // Write Format Chunk
        wavFile.oStream.write(wavFile.buffer, 0, 8);

        // Calculate the scaling factor for converting to a normalised double
        if (wavFile.validBits > 8) {
            // If more than 8 validBits, data is signed
            // Conversion required multiplying by magnitude of max positive value
            wavFile.floatOffset = 0;
            wavFile.floatScale = Long.MAX_VALUE >> (64 - wavFile.validBits);
        } else {
            // Else if 8 or less validBits, data is unsigned
            // Conversion required dividing by max positive value
            wavFile.floatOffset = 1;
            wavFile.floatScale = 0.5 * ((1 << wavFile.validBits) - 1);
        }

        // Finally, set the IO State
        wavFile.bufferPointer = 0;
        wavFile.frameCounter = 0;

        return wavFile;
    }

    private static void putLE(long val, byte[] buffer, int pos, int numBytes) {
        for (int b = 0; b < numBytes; b++) {
            buffer[pos] = (byte) (val & 0xFF);
            val >>= 8;
            pos++;
        }
    }

    private void writeSample(long val) throws IOException {
        for (int b = 0; b < bytesPerSample; b++) {
            if (bufferPointer == BUFFER_SIZE) {
                oStream.write(buffer, 0, BUFFER_SIZE);
                bufferPointer = 0;
            }

            buffer[bufferPointer] = (byte) (val & 0xFF);
            val >>= 8;
            bufferPointer++;
        }
    }

    public int writeFrames(int[] sampleBuffer, int numFramesToWrite) throws IOException {
        return writeFrames(sampleBuffer, 0, numFramesToWrite);
    }

    public int writeFrames(int[] sampleBuffer, int offset, int numFramesToWrite) throws IOException {

        for (int f = 0; f < numFramesToWrite; f++) {
            if (frameCounter == numFrames) return f;

            for (int c = 0; c < numChannels; c++) {
                writeSample(sampleBuffer[offset]);
                offset++;
            }

            frameCounter++;
        }

        return numFramesToWrite;
    }

    public int writeFrames(int[][] sampleBuffer, int numFramesToWrite) throws IOException {
        return writeFrames(sampleBuffer, 0, numFramesToWrite);
    }

    public int writeFrames(int[][] sampleBuffer, int offset, int numFramesToWrite) throws IOException {
        for (int f = 0; f < numFramesToWrite; f++) {
            if (frameCounter == numFrames) return f;

            for (int c = 0; c < numChannels; c++) writeSample(sampleBuffer[c][offset]);

            offset++;
            frameCounter++;
        }

        return numFramesToWrite;
    }

    public int writeFrames(long[] sampleBuffer, int numFramesToWrite) throws IOException {
        return writeFrames(sampleBuffer, 0, numFramesToWrite);
    }

    public int writeFrames(long[] sampleBuffer, int offset, int numFramesToWrite) throws IOException {

        for (int f = 0; f < numFramesToWrite; f++) {
            if (frameCounter == numFrames) return f;

            for (int c = 0; c < numChannels; c++) {
                writeSample(sampleBuffer[offset]);
                offset++;
            }

            frameCounter++;
        }

        return numFramesToWrite;
    }

    public int writeFrames(long[][] sampleBuffer, int numFramesToWrite) throws IOException {
        return writeFrames(sampleBuffer, 0, numFramesToWrite);
    }

    public int writeFrames(long[][] sampleBuffer, int offset, int numFramesToWrite) throws IOException {

        for (int f = 0; f < numFramesToWrite; f++) {
            if (frameCounter == numFrames) return f;

            for (int c = 0; c < numChannels; c++) writeSample(sampleBuffer[c][offset]);

            offset++;
            frameCounter++;
        }

        return numFramesToWrite;
    }

    public int writeFrames(double[] sampleBuffer, int numFramesToWrite) throws IOException {
        return writeFrames(sampleBuffer, 0, numFramesToWrite);
    }

    public int writeFrames(double[] sampleBuffer, int offset, int numFramesToWrite) throws IOException {

        for (int f = 0; f < numFramesToWrite; f++) {
            if (frameCounter == numFrames) return f;

            for (int c = 0; c < numChannels; c++) {
                writeSample((long) (floatScale * (floatOffset + sampleBuffer[offset])));
                offset++;
            }

            frameCounter++;
        }

        return numFramesToWrite;
    }

    public int writeFrames(double[][] sampleBuffer, int numFramesToWrite) throws IOException {
        return writeFrames(sampleBuffer, 0, numFramesToWrite);
    }

    public int writeFrames(double[][] sampleBuffer, int offset, int numFramesToWrite) throws IOException {

        for (int f = 0; f < numFramesToWrite; f++) {
            if (frameCounter == numFrames) return f;

            for (int c = 0; c < numChannels; c++)
                writeSample((long) (floatScale * (floatOffset + sampleBuffer[c][offset])));

            offset++;
            frameCounter++;
        }

        return numFramesToWrite;
    }

    public void close() throws IOException {

        if (oStream != null) {
            // Write out anything still in the local buffer
            if (bufferPointer > 0) oStream.write(buffer, 0, bufferPointer);

            // If an extra byte is required for word alignment, add it to the end
            if (wordAlignAdjust) oStream.write(0);

            // Close the stream and set to null
            oStream.close();
            oStream = null;
        }
    }
}