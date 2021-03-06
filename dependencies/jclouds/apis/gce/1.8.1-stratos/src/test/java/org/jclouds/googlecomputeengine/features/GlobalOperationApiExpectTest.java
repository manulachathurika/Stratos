/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jclouds.googlecomputeengine.features;

import static org.jclouds.googlecomputeengine.GoogleComputeEngineConstants.COMPUTE_READONLY_SCOPE;
import static org.jclouds.googlecomputeengine.GoogleComputeEngineConstants.COMPUTE_SCOPE;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import org.jclouds.googlecomputeengine.internal.BaseGoogleComputeEngineApiExpectTest;
import org.jclouds.googlecomputeengine.options.ListOptions;
import org.jclouds.googlecomputeengine.parse.ParseOperationListTest;
import org.jclouds.googlecomputeengine.parse.ParseOperationTest;
import org.jclouds.http.HttpRequest;
import org.jclouds.http.HttpResponse;
import org.testng.annotations.Test;

@Test(groups = "unit")
public class GlobalOperationApiExpectTest extends BaseGoogleComputeEngineApiExpectTest {

   private static final String OPERATIONS_URL_PREFIX = "https://www.googleapis" +
           ".com/compute/v1/projects/myproject/global/operations";

   public static final HttpRequest GET_GLOBAL_OPERATION_REQUEST = HttpRequest
           .builder()
           .method("GET")
           .endpoint(OPERATIONS_URL_PREFIX + "/operation-1354084865060-4cf88735faeb8-bbbb12cb")
           .addHeader("Accept", "application/json")
           .addHeader("Authorization", "Bearer " + TOKEN).build();

   public static final HttpResponse GET_GLOBAL_OPERATION_RESPONSE = HttpResponse.builder().statusCode(200)
           .payload(staticPayloadFromResource("/global_operation.json")).build();

   public void testGetOperationResponseIs2xx() throws Exception {

      GlobalOperationApi operationApi = requestsSendResponses(requestForScopes(COMPUTE_READONLY_SCOPE),
              TOKEN_RESPONSE, GET_GLOBAL_OPERATION_REQUEST, GET_GLOBAL_OPERATION_RESPONSE).getGlobalOperationApiForProject("myproject");

      assertEquals(operationApi.get("operation-1354084865060-4cf88735faeb8-bbbb12cb"),
              new ParseOperationTest().expected());
   }

   public void testGetOperationResponseIs4xx() throws Exception {

      HttpResponse operationResponse = HttpResponse.builder().statusCode(404).build();

      GlobalOperationApi globalOperationApi = requestsSendResponses(requestForScopes(COMPUTE_READONLY_SCOPE),
              TOKEN_RESPONSE, GET_GLOBAL_OPERATION_REQUEST, operationResponse).getGlobalOperationApiForProject("myproject");

      assertNull(globalOperationApi.get("operation-1354084865060-4cf88735faeb8-bbbb12cb"));
   }

   public void testDeleteOperationResponseIs2xx() throws Exception {
      HttpRequest delete = HttpRequest
              .builder()
              .method("DELETE")
              .endpoint(OPERATIONS_URL_PREFIX + "/operation-1352178598164-4cdcc9d031510-4aa46279")
              .addHeader("Authorization", "Bearer " + TOKEN).build();

      HttpResponse operationResponse = HttpResponse.builder().statusCode(204).build();

      GlobalOperationApi globalOperationApi = requestsSendResponses(requestForScopes(COMPUTE_SCOPE),
              TOKEN_RESPONSE, delete, operationResponse).getGlobalOperationApiForProject("myproject");

      globalOperationApi.delete("operation-1352178598164-4cdcc9d031510-4aa46279");
   }

   public void testDeleteOperationResponseIs4xx() throws Exception {
      HttpRequest delete = HttpRequest
              .builder()
              .method("DELETE")
              .endpoint(OPERATIONS_URL_PREFIX + "/operation-1352178598164-4cdcc9d031510-4aa46279")
              .addHeader("Authorization", "Bearer " + TOKEN).build();

      HttpResponse operationResponse = HttpResponse.builder().statusCode(404).build();

      GlobalOperationApi globalOperationApi = requestsSendResponses(requestForScopes(COMPUTE_SCOPE),
              TOKEN_RESPONSE, delete, operationResponse).getGlobalOperationApiForProject("myproject");

      globalOperationApi.delete("operation-1352178598164-4cdcc9d031510-4aa46279");
   }

   public void testLisOperationWithNoOptionsResponseIs2xx() {
      HttpRequest get = HttpRequest
              .builder()
              .method("GET")
              .endpoint(OPERATIONS_URL_PREFIX)
              .addHeader("Accept", "application/json")
              .addHeader("Authorization", "Bearer " + TOKEN).build();

      HttpResponse operationResponse = HttpResponse.builder().statusCode(200)
              .payload(payloadFromResource("/global_operation_list.json")).build();

      GlobalOperationApi globalOperationApi = requestsSendResponses(requestForScopes(COMPUTE_READONLY_SCOPE),
              TOKEN_RESPONSE, get, operationResponse).getGlobalOperationApiForProject("myproject");

      assertEquals(globalOperationApi.listFirstPage().toString(),
              new ParseOperationListTest().expected().toString());
   }

   public void testListOperationWithPaginationOptionsResponseIs2xx() {
      HttpRequest get = HttpRequest
              .builder()
              .method("GET")
              .endpoint(OPERATIONS_URL_PREFIX +
                      "?pageToken=CglPUEVSQVRJT04SOzU5MDQyMTQ4Nzg1Mi5vcG" +
                      "VyYXRpb24tMTM1MjI0NDI1ODAzMC00Y2RkYmU2YTJkNmIwLWVkMzIyMzQz&" +
                      "filter=" +
                      "status%20eq%20done&" +
                      "maxResults=3")
              .addHeader("Accept", "application/json")
              .addHeader("Authorization", "Bearer " + TOKEN).build();

      HttpResponse operationResponse = HttpResponse.builder().statusCode(200)
              .payload(payloadFromResource("/global_operation_list.json")).build();

      GlobalOperationApi globalOperationApi = requestsSendResponses(requestForScopes(COMPUTE_READONLY_SCOPE),
              TOKEN_RESPONSE, get, operationResponse).getGlobalOperationApiForProject("myproject");

      assertEquals(globalOperationApi.listAtMarker("CglPUEVSQVRJT04SOzU5MDQyMTQ4Nzg1Mi5vcGVyYXRpb24tMTM1Mj" +
              "I0NDI1ODAzMC00Y2RkYmU2YTJkNmIwLWVkMzIyMzQz",
              new ListOptions.Builder().filter("status eq done").maxResults(3)).toString(),
              new ParseOperationListTest().expected().toString());
   }

   public void testListOperationWithPaginationOptionsResponseIs4xx() {
      HttpRequest get = HttpRequest
              .builder()
              .method("GET")
              .endpoint(OPERATIONS_URL_PREFIX)
              .addHeader("Accept", "application/json")
              .addHeader("Authorization", "Bearer " + TOKEN).build();

      HttpResponse operationResponse = HttpResponse.builder().statusCode(404).build();

      GlobalOperationApi globalOperationApi = requestsSendResponses(requestForScopes(COMPUTE_READONLY_SCOPE),
              TOKEN_RESPONSE, get, operationResponse).getGlobalOperationApiForProject("myproject");

      assertTrue(globalOperationApi.list().concat().isEmpty());
   }


}
