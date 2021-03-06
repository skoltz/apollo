/*
 * -\-\-
 * Spotify Apollo API Interfaces
 * --
 * Copyright (C) 2013 - 2015 Spotify AB
 * --
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * -/-/-
 */
package com.spotify.apollo;

import com.spotify.apollo.route.Middleware;

import java.util.Optional;

import okio.ByteString;

/**
 * Specifies a way to serialize a response Object for some incoming {@link Request},
 * optionally providing a content type that should be set in the response headers.
 *
 * @deprecated This interface is deprecated; the new preferred way to do response serialization is
 * via a {@link Middleware} that converts the response object into a {@link Response<ByteString>}.
 * The reasons for preferring a Middleware are:
 *
 *   - This interface is not typesafe, whereas Middlewares can be. This means the compiler can help
 *     you spot mistakes. Simply define your Middleware as a function from something like {@code
 *     AsyncHandler<MyDomainObject>} to {@code AsyncHandler<Response<ByteString>>}.
 *   - Middlewares are more powerful; they can directly set headers and can also modify other things
 *     on the Response, such as status codes and reason phrases.
 */
@Deprecated
public interface Serializer {

  /**
   * Produce a {@link Payload} from the incoming request {@link Request}
   * and the endpoint returned object.
   *
   * @param request  The incoming {@link Request}
   * @param t        The object that the endpoint handler returned
   * @return  A {@link Payload} instance
   */
  Payload serialize(Request request, Object t);

  interface Payload {
    /**
     * The bytes of the payload.
     */
    ByteString byteString();

    /**
     * An optional content-type
     */
    Optional<String> contentType();

    /**
     * Create a new {@link Payload} based on the content of this instance
     * but with the given contentType if no contentType is present already.
     *
     * @param contentType  The contentType to use if no contentType is present
     */
    Payload withContentTypeIfAbsent(String contentType);
  }
}
