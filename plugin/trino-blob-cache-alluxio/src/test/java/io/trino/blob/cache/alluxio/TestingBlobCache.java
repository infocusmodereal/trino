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
package io.trino.blob.cache.alluxio;

import io.opentelemetry.api.trace.Tracer;
import io.trino.spi.cache.BlobCache;
import io.trino.spi.cache.BlobSource;
import io.trino.spi.cache.CacheKey;

import java.io.IOException;
import java.util.Collection;

final class TestingBlobCache
{
    private TestingBlobCache() {}

    static BlobCache adapt(AlluxioBlobCache delegate, Tracer tracer, AlluxioCacheStats stats)
    {
        return new BlobCache()
        {
            @Override
            public BlobSource get(CacheKey key, BlobSource source)
                    throws IOException
            {
                return delegate.get(key, source, tracer, stats);
            }

            @Override
            public void invalidate(CacheKey key)
            {
                delegate.invalidate(key);
            }

            @Override
            public void invalidate(Collection<CacheKey> keys)
            {
                delegate.invalidate(keys);
            }
        };
    }
}
