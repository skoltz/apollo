/*
 * -\-\-
 * Spotify Apollo Jetty HTTP Server Module
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
package com.spotify.apollo.http.server;

import com.typesafe.config.ConfigFactory;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HttpServerConfigTest {

  private static final String SERVICE_NAME = "http-test-service-name";

  @Test
  public void canConfigureAddressAndPort() {
    String addr = "someaddr";
    int port = 1234;
    String json =
        String.format("{\"http\":{\"server\":{\"address\":\"%s\", \"port\":%d}}}", addr, port);

    HttpServerConfig http = conf(json);
    assertEquals(addr, http.address());
    assertEquals(port, http.port().intValue());
  }

  @Test
  public void canConfigureTtlMillis() {
    long ttlMillis = 123L;
    String json = "{\"http\":{\"server\":{\"ttlMillis\": 123}}}";

    HttpServerConfig http = conf(json);
    assertEquals(ttlMillis, http.ttlMillis());
  }

  @Test
  public void canConfigureWorkerThreads() {
    long workerThreads = 123L;
    String json = "{\"http\":{\"server\":{\"workerThreads\": 123}}}";

    HttpServerConfig http = conf(json);
    assertEquals(workerThreads, http.workerThreads());
  }

  @Test
  public void canConfigureMaxHttpChunkLength() {
    long maxHttpChunkLength = 123L;
    String json = "{\"http\":{\"server\":{\"maxHttpChunkLength\": 123}}}";

    HttpServerConfig http = conf(json);
    assertEquals(maxHttpChunkLength, http.maxHttpChunkLength());
  }

  @Test
  public void canConfigureKeepAliveTimeout() {
    long keepAliveTimeout = 123L;
    String json = "{\"http\":{\"server\":{\"keepAliveTimeout\": 123}}}";

    HttpServerConfig http = conf(json);
    assertEquals(keepAliveTimeout, http.keepAliveTimeout());
  }

  @Test
  public void hasNoRegistrationNameByDefault() {
    String json = "{\"http\":{\"server\":{}}}";

    HttpServerConfig http = conf(json);
    assertEquals(SERVICE_NAME, http.registrationName());
  }

  @Test
  public void canConfigureRegistrationName() {
    String json = "{\"http\":{\"server\":{\"registrationName\": \"foobar\"}}}";

    HttpServerConfig http = conf(json);
    assertEquals("foobar", http.registrationName());
  }

  private static HttpServerConfig conf(String json) {
    return new HttpServerConfig(SERVICE_NAME, ConfigFactory.parseString(json));
  }
}
