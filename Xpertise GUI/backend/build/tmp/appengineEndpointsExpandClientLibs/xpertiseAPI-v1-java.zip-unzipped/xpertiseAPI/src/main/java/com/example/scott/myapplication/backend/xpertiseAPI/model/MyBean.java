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
 * This code was generated by https://code.google.com/p/google-apis-client-generator/
 * (build: 2015-08-03 17:34:38 UTC)
 * on 2015-11-14 at 23:04:06 UTC 
 * Modify at your own risk.
 */

package com.example.scott.myapplication.backend.xpertiseAPI.model;

/**
 * Model definition for MyBean.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the xpertiseAPI. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class MyBean extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Boolean bool;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String data;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Double hiLat;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Double hiLng;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Double loLat;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Double loLng;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer pid;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Boolean getBool() {
    return bool;
  }

  /**
   * @param bool bool or {@code null} for none
   */
  public MyBean setBool(java.lang.Boolean bool) {
    this.bool = bool;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getData() {
    return data;
  }

  /**
   * @param data data or {@code null} for none
   */
  public MyBean setData(java.lang.String data) {
    this.data = data;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Double getHiLat() {
    return hiLat;
  }

  /**
   * @param hiLat hiLat or {@code null} for none
   */
  public MyBean setHiLat(java.lang.Double hiLat) {
    this.hiLat = hiLat;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Double getHiLng() {
    return hiLng;
  }

  /**
   * @param hiLng hiLng or {@code null} for none
   */
  public MyBean setHiLng(java.lang.Double hiLng) {
    this.hiLng = hiLng;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Double getLoLat() {
    return loLat;
  }

  /**
   * @param loLat loLat or {@code null} for none
   */
  public MyBean setLoLat(java.lang.Double loLat) {
    this.loLat = loLat;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Double getLoLng() {
    return loLng;
  }

  /**
   * @param loLng loLng or {@code null} for none
   */
  public MyBean setLoLng(java.lang.Double loLng) {
    this.loLng = loLng;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getPid() {
    return pid;
  }

  /**
   * @param pid pid or {@code null} for none
   */
  public MyBean setPid(java.lang.Integer pid) {
    this.pid = pid;
    return this;
  }

  @Override
  public MyBean set(String fieldName, Object value) {
    return (MyBean) super.set(fieldName, value);
  }

  @Override
  public MyBean clone() {
    return (MyBean) super.clone();
  }

}
