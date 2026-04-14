/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.trino.spi.cache;

import java.io.IOException;

import static java.lang.Math.min;
import static java.util.Objects.checkFromIndexSize;

/**
 * Lazy source of bytes backing a cached blob. Used by {@link BlobCache} to
 * populate a cache entry on miss.
 */
public interface BlobSource
{
    long length()
            throws IOException;

    void readFully(long position, byte[] buffer, int offset, int length)
            throws IOException;

    default int readTail(byte[] buffer, int offset, int length)
            throws IOException
    {
        checkFromIndexSize(offset, length, buffer.length);
        long size = length();
        int readSize = (int) min(size, length);
        readFully(size - readSize, buffer, offset, readSize);
        return readSize;
    }
}
