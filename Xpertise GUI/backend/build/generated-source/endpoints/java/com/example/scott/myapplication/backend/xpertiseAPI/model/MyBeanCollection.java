/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
/*
 * This code was generated by https://github.com/google/apis-client-generator/
 * (build: 2015-11-16 19:10:01 UTC)
 * on 2015-11-30 at 03:05:27 UTC 
 * Modify at your own risk.
 */

package com.example.scott.myapplication.backend.xpertiseAPI.model;

/**
 * Model definition for MyBeanCollection.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the xpertiseAPI. For a detailed explanation see:
 * <a href="https://developers.google.com/api-client-library/java/google-http-java-client/json">https://developers.google.com/api-client-library/java/google-http-java-client/json</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class MyBeanCollection extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.util.List<MyBean> items;

  /**
   * @return value or {@code null} for none
   */
  public java.util.List<MyBean> getItems() {
    return items;
  }

  /**
   * @param items items or {@code null} for none
   */
  public MyBeanCollection setItems(java.util.List<MyBean> items) {
    this.items = items;
    return this;
  }

  @Override
  public MyBeanCollection set(String fieldName, Object value) {
    return (MyBeanCollection) super.set(fieldName, value);
  }

  @Override
  public MyBeanCollection clone() {
    return (MyBeanCollection) super.clone();
  }

}
