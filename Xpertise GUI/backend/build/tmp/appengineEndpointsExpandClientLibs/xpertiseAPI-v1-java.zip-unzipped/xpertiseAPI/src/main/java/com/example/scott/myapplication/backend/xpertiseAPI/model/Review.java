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
 * on 2015-11-11 at 13:09:23 UTC 
 * Modify at your own risk.
 */

package com.example.scott.myapplication.backend.xpertiseAPI.model;

/**
 * Model definition for Review.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the xpertiseAPI. For a detailed explanation see:
 * <a href="http://code.google.com/p/google-http-java-client/wiki/JSON">http://code.google.com/p/google-http-java-client/wiki/JSON</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class Review extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String reviewDesc;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key("reviewee_pid")
  private java.lang.Integer revieweePid;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key("reviewer_pid")
  private java.lang.Integer reviewerPid;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.Integer stars;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getReviewDesc() {
    return reviewDesc;
  }

  /**
   * @param reviewDesc reviewDesc or {@code null} for none
   */
  public Review setReviewDesc(java.lang.String reviewDesc) {
    this.reviewDesc = reviewDesc;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getRevieweePid() {
    return revieweePid;
  }

  /**
   * @param revieweePid revieweePid or {@code null} for none
   */
  public Review setRevieweePid(java.lang.Integer revieweePid) {
    this.revieweePid = revieweePid;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getReviewerPid() {
    return reviewerPid;
  }

  /**
   * @param reviewerPid reviewerPid or {@code null} for none
   */
  public Review setReviewerPid(java.lang.Integer reviewerPid) {
    this.reviewerPid = reviewerPid;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Integer getStars() {
    return stars;
  }

  /**
   * @param stars stars or {@code null} for none
   */
  public Review setStars(java.lang.Integer stars) {
    this.stars = stars;
    return this;
  }

  @Override
  public Review set(String fieldName, Object value) {
    return (Review) super.set(fieldName, value);
  }

  @Override
  public Review clone() {
    return (Review) super.clone();
  }

}
