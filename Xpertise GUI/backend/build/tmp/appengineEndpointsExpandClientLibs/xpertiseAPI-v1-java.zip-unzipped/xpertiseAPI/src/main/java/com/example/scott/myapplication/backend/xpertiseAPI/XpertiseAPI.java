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
 * on 2015-10-16 at 00:25:25 UTC 
 * Modify at your own risk.
 */

package com.example.scott.myapplication.backend.xpertiseAPI;

/**
 * Service definition for XpertiseAPI (v1).
 *
 * <p>
 * This is an API
 * </p>
 *
 * <p>
 * For more information about this service, see the
 * <a href="" target="_blank">API Documentation</a>
 * </p>
 *
 * <p>
 * This service uses {@link XpertiseAPIRequestInitializer} to initialize global parameters via its
 * {@link Builder}.
 * </p>
 *
 * @since 1.3
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public class XpertiseAPI extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient {

  // Note: Leave this static initializer at the top of the file.
  static {
    com.google.api.client.util.Preconditions.checkState(
        com.google.api.client.googleapis.GoogleUtils.MAJOR_VERSION == 1 &&
        com.google.api.client.googleapis.GoogleUtils.MINOR_VERSION >= 15,
        "You are currently running with version %s of google-api-client. " +
        "You need at least version 1.15 of google-api-client to run version " +
        "1.20.0 of the xpertiseAPI library.", com.google.api.client.googleapis.GoogleUtils.VERSION);
  }

  /**
   * The default encoded root URL of the service. This is determined when the library is generated
   * and normally should not be changed.
   *
   * @since 1.7
   */
  public static final String DEFAULT_ROOT_URL = "https://myApplicationId.appspot.com/_ah/api/";

  /**
   * The default encoded service path of the service. This is determined when the library is
   * generated and normally should not be changed.
   *
   * @since 1.7
   */
  public static final String DEFAULT_SERVICE_PATH = "xpertiseAPI/v1/";

  /**
   * The default encoded base URL of the service. This is determined when the library is generated
   * and normally should not be changed.
   */
  public static final String DEFAULT_BASE_URL = DEFAULT_ROOT_URL + DEFAULT_SERVICE_PATH;

  /**
   * Constructor.
   *
   * <p>
   * Use {@link Builder} if you need to specify any of the optional parameters.
   * </p>
   *
   * @param transport HTTP transport, which should normally be:
   *        <ul>
   *        <li>Google App Engine:
   *        {@code com.google.api.client.extensions.appengine.http.UrlFetchTransport}</li>
   *        <li>Android: {@code newCompatibleTransport} from
   *        {@code com.google.api.client.extensions.android.http.AndroidHttp}</li>
   *        <li>Java: {@link com.google.api.client.googleapis.javanet.GoogleNetHttpTransport#newTrustedTransport()}
   *        </li>
   *        </ul>
   * @param jsonFactory JSON factory, which may be:
   *        <ul>
   *        <li>Jackson: {@code com.google.api.client.json.jackson2.JacksonFactory}</li>
   *        <li>Google GSON: {@code com.google.api.client.json.gson.GsonFactory}</li>
   *        <li>Android Honeycomb or higher:
   *        {@code com.google.api.client.extensions.android.json.AndroidJsonFactory}</li>
   *        </ul>
   * @param httpRequestInitializer HTTP request initializer or {@code null} for none
   * @since 1.7
   */
  public XpertiseAPI(com.google.api.client.http.HttpTransport transport, com.google.api.client.json.JsonFactory jsonFactory,
      com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
    this(new Builder(transport, jsonFactory, httpRequestInitializer));
  }

  /**
   * @param builder builder
   */
  XpertiseAPI(Builder builder) {
    super(builder);
  }

  @Override
  protected void initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest<?> httpClientRequest) throws java.io.IOException {
    super.initialize(httpClientRequest);
  }

  /**
   * Create a request for the method "profile_auth".
   *
   * This request holds the parameters needed by the xpertiseAPI server.  After setting any optional
   * parameters, call the {@link ProfileAuth#execute()} method to invoke the remote operation.
   *
   * @param email
   * @param password
   * @return the request
   */
  public ProfileAuth profileAuth(java.lang.String email, java.lang.String password) throws java.io.IOException {
    ProfileAuth result = new ProfileAuth(email, password);
    initialize(result);
    return result;
  }

  public class ProfileAuth extends XpertiseAPIRequest<com.example.scott.myapplication.backend.xpertiseAPI.model.Profile> {

    private static final String REST_PATH = "authProfile/{email}/{password}";

    /**
     * Create a request for the method "profile_auth".
     *
     * This request holds the parameters needed by the the xpertiseAPI server.  After setting any
     * optional parameters, call the {@link ProfileAuth#execute()} method to invoke the remote
     * operation. <p> {@link
     * ProfileAuth#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param email
     * @param password
     * @since 1.13
     */
    protected ProfileAuth(java.lang.String email, java.lang.String password) {
      super(XpertiseAPI.this, "GET", REST_PATH, null, com.example.scott.myapplication.backend.xpertiseAPI.model.Profile.class);
      this.email = com.google.api.client.util.Preconditions.checkNotNull(email, "Required parameter email must be specified.");
      this.password = com.google.api.client.util.Preconditions.checkNotNull(password, "Required parameter password must be specified.");
    }

    @Override
    public com.google.api.client.http.HttpResponse executeUsingHead() throws java.io.IOException {
      return super.executeUsingHead();
    }

    @Override
    public com.google.api.client.http.HttpRequest buildHttpRequestUsingHead() throws java.io.IOException {
      return super.buildHttpRequestUsingHead();
    }

    @Override
    public ProfileAuth setAlt(java.lang.String alt) {
      return (ProfileAuth) super.setAlt(alt);
    }

    @Override
    public ProfileAuth setFields(java.lang.String fields) {
      return (ProfileAuth) super.setFields(fields);
    }

    @Override
    public ProfileAuth setKey(java.lang.String key) {
      return (ProfileAuth) super.setKey(key);
    }

    @Override
    public ProfileAuth setOauthToken(java.lang.String oauthToken) {
      return (ProfileAuth) super.setOauthToken(oauthToken);
    }

    @Override
    public ProfileAuth setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (ProfileAuth) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public ProfileAuth setQuotaUser(java.lang.String quotaUser) {
      return (ProfileAuth) super.setQuotaUser(quotaUser);
    }

    @Override
    public ProfileAuth setUserIp(java.lang.String userIp) {
      return (ProfileAuth) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String email;

    /**

     */
    public java.lang.String getEmail() {
      return email;
    }

    public ProfileAuth setEmail(java.lang.String email) {
      this.email = email;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.String password;

    /**

     */
    public java.lang.String getPassword() {
      return password;
    }

    public ProfileAuth setPassword(java.lang.String password) {
      this.password = password;
      return this;
    }

    @Override
    public ProfileAuth set(String parameterName, Object value) {
      return (ProfileAuth) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "profile_edit".
   *
   * This request holds the parameters needed by the xpertiseAPI server.  After setting any optional
   * parameters, call the {@link ProfileEdit#execute()} method to invoke the remote operation.
   *
   * @param firstName
   * @param lastName
   * @param password
   * @param email
   * @param city
   * @param lat
   * @param lng
   * @param description
   * @param pid
   * @return the request
   */
  public ProfileEdit profileEdit(java.lang.String firstName, java.lang.String lastName, java.lang.String password, java.lang.String email, java.lang.String city, java.lang.Double lat, java.lang.Double lng, java.lang.String description, java.lang.Integer pid) throws java.io.IOException {
    ProfileEdit result = new ProfileEdit(firstName, lastName, password, email, city, lat, lng, description, pid);
    initialize(result);
    return result;
  }

  public class ProfileEdit extends XpertiseAPIRequest<com.example.scott.myapplication.backend.xpertiseAPI.model.MyBean> {

    private static final String REST_PATH = "editProfile/{firstName}/{lastName}/{password}/{email}/{city}/{lat}/{lng}/{description}/{pid}";

    /**
     * Create a request for the method "profile_edit".
     *
     * This request holds the parameters needed by the the xpertiseAPI server.  After setting any
     * optional parameters, call the {@link ProfileEdit#execute()} method to invoke the remote
     * operation. <p> {@link
     * ProfileEdit#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param firstName
     * @param lastName
     * @param password
     * @param email
     * @param city
     * @param lat
     * @param lng
     * @param description
     * @param pid
     * @since 1.13
     */
    protected ProfileEdit(java.lang.String firstName, java.lang.String lastName, java.lang.String password, java.lang.String email, java.lang.String city, java.lang.Double lat, java.lang.Double lng, java.lang.String description, java.lang.Integer pid) {
      super(XpertiseAPI.this, "POST", REST_PATH, null, com.example.scott.myapplication.backend.xpertiseAPI.model.MyBean.class);
      this.firstName = com.google.api.client.util.Preconditions.checkNotNull(firstName, "Required parameter firstName must be specified.");
      this.lastName = com.google.api.client.util.Preconditions.checkNotNull(lastName, "Required parameter lastName must be specified.");
      this.password = com.google.api.client.util.Preconditions.checkNotNull(password, "Required parameter password must be specified.");
      this.email = com.google.api.client.util.Preconditions.checkNotNull(email, "Required parameter email must be specified.");
      this.city = com.google.api.client.util.Preconditions.checkNotNull(city, "Required parameter city must be specified.");
      this.lat = com.google.api.client.util.Preconditions.checkNotNull(lat, "Required parameter lat must be specified.");
      this.lng = com.google.api.client.util.Preconditions.checkNotNull(lng, "Required parameter lng must be specified.");
      this.description = com.google.api.client.util.Preconditions.checkNotNull(description, "Required parameter description must be specified.");
      this.pid = com.google.api.client.util.Preconditions.checkNotNull(pid, "Required parameter pid must be specified.");
    }

    @Override
    public ProfileEdit setAlt(java.lang.String alt) {
      return (ProfileEdit) super.setAlt(alt);
    }

    @Override
    public ProfileEdit setFields(java.lang.String fields) {
      return (ProfileEdit) super.setFields(fields);
    }

    @Override
    public ProfileEdit setKey(java.lang.String key) {
      return (ProfileEdit) super.setKey(key);
    }

    @Override
    public ProfileEdit setOauthToken(java.lang.String oauthToken) {
      return (ProfileEdit) super.setOauthToken(oauthToken);
    }

    @Override
    public ProfileEdit setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (ProfileEdit) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public ProfileEdit setQuotaUser(java.lang.String quotaUser) {
      return (ProfileEdit) super.setQuotaUser(quotaUser);
    }

    @Override
    public ProfileEdit setUserIp(java.lang.String userIp) {
      return (ProfileEdit) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String firstName;

    /**

     */
    public java.lang.String getFirstName() {
      return firstName;
    }

    public ProfileEdit setFirstName(java.lang.String firstName) {
      this.firstName = firstName;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.String lastName;

    /**

     */
    public java.lang.String getLastName() {
      return lastName;
    }

    public ProfileEdit setLastName(java.lang.String lastName) {
      this.lastName = lastName;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.String password;

    /**

     */
    public java.lang.String getPassword() {
      return password;
    }

    public ProfileEdit setPassword(java.lang.String password) {
      this.password = password;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.String email;

    /**

     */
    public java.lang.String getEmail() {
      return email;
    }

    public ProfileEdit setEmail(java.lang.String email) {
      this.email = email;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.String city;

    /**

     */
    public java.lang.String getCity() {
      return city;
    }

    public ProfileEdit setCity(java.lang.String city) {
      this.city = city;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.Double lat;

    /**

     */
    public java.lang.Double getLat() {
      return lat;
    }

    public ProfileEdit setLat(java.lang.Double lat) {
      this.lat = lat;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.Double lng;

    /**

     */
    public java.lang.Double getLng() {
      return lng;
    }

    public ProfileEdit setLng(java.lang.Double lng) {
      this.lng = lng;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.String description;

    /**

     */
    public java.lang.String getDescription() {
      return description;
    }

    public ProfileEdit setDescription(java.lang.String description) {
      this.description = description;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.Integer pid;

    /**

     */
    public java.lang.Integer getPid() {
      return pid;
    }

    public ProfileEdit setPid(java.lang.Integer pid) {
      this.pid = pid;
      return this;
    }

    @Override
    public ProfileEdit set(String parameterName, Object value) {
      return (ProfileEdit) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "profile_get".
   *
   * This request holds the parameters needed by the xpertiseAPI server.  After setting any optional
   * parameters, call the {@link ProfileGet#execute()} method to invoke the remote operation.
   *
   * @param pid
   * @return the request
   */
  public ProfileGet profileGet(java.lang.Integer pid) throws java.io.IOException {
    ProfileGet result = new ProfileGet(pid);
    initialize(result);
    return result;
  }

  public class ProfileGet extends XpertiseAPIRequest<com.example.scott.myapplication.backend.xpertiseAPI.model.Profile> {

    private static final String REST_PATH = "profile/{pid}";

    /**
     * Create a request for the method "profile_get".
     *
     * This request holds the parameters needed by the the xpertiseAPI server.  After setting any
     * optional parameters, call the {@link ProfileGet#execute()} method to invoke the remote
     * operation. <p> {@link
     * ProfileGet#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param pid
     * @since 1.13
     */
    protected ProfileGet(java.lang.Integer pid) {
      super(XpertiseAPI.this, "GET", REST_PATH, null, com.example.scott.myapplication.backend.xpertiseAPI.model.Profile.class);
      this.pid = com.google.api.client.util.Preconditions.checkNotNull(pid, "Required parameter pid must be specified.");
    }

    @Override
    public com.google.api.client.http.HttpResponse executeUsingHead() throws java.io.IOException {
      return super.executeUsingHead();
    }

    @Override
    public com.google.api.client.http.HttpRequest buildHttpRequestUsingHead() throws java.io.IOException {
      return super.buildHttpRequestUsingHead();
    }

    @Override
    public ProfileGet setAlt(java.lang.String alt) {
      return (ProfileGet) super.setAlt(alt);
    }

    @Override
    public ProfileGet setFields(java.lang.String fields) {
      return (ProfileGet) super.setFields(fields);
    }

    @Override
    public ProfileGet setKey(java.lang.String key) {
      return (ProfileGet) super.setKey(key);
    }

    @Override
    public ProfileGet setOauthToken(java.lang.String oauthToken) {
      return (ProfileGet) super.setOauthToken(oauthToken);
    }

    @Override
    public ProfileGet setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (ProfileGet) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public ProfileGet setQuotaUser(java.lang.String quotaUser) {
      return (ProfileGet) super.setQuotaUser(quotaUser);
    }

    @Override
    public ProfileGet setUserIp(java.lang.String userIp) {
      return (ProfileGet) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.Integer pid;

    /**

     */
    public java.lang.Integer getPid() {
      return pid;
    }

    public ProfileGet setPid(java.lang.Integer pid) {
      this.pid = pid;
      return this;
    }

    @Override
    public ProfileGet set(String parameterName, Object value) {
      return (ProfileGet) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "profile_listAll".
   *
   * This request holds the parameters needed by the xpertiseAPI server.  After setting any optional
   * parameters, call the {@link ProfileListAll#execute()} method to invoke the remote operation.
   *
   * @return the request
   */
  public ProfileListAll profileListAll() throws java.io.IOException {
    ProfileListAll result = new ProfileListAll();
    initialize(result);
    return result;
  }

  public class ProfileListAll extends XpertiseAPIRequest<com.example.scott.myapplication.backend.xpertiseAPI.model.ProfileCollection> {

    private static final String REST_PATH = "profile";

    /**
     * Create a request for the method "profile_listAll".
     *
     * This request holds the parameters needed by the the xpertiseAPI server.  After setting any
     * optional parameters, call the {@link ProfileListAll#execute()} method to invoke the remote
     * operation. <p> {@link ProfileListAll#initialize(com.google.api.client.googleapis.services.Abstr
     * actGoogleClientRequest)} must be called to initialize this instance immediately after invoking
     * the constructor. </p>
     *
     * @since 1.13
     */
    protected ProfileListAll() {
      super(XpertiseAPI.this, "GET", REST_PATH, null, com.example.scott.myapplication.backend.xpertiseAPI.model.ProfileCollection.class);
    }

    @Override
    public com.google.api.client.http.HttpResponse executeUsingHead() throws java.io.IOException {
      return super.executeUsingHead();
    }

    @Override
    public com.google.api.client.http.HttpRequest buildHttpRequestUsingHead() throws java.io.IOException {
      return super.buildHttpRequestUsingHead();
    }

    @Override
    public ProfileListAll setAlt(java.lang.String alt) {
      return (ProfileListAll) super.setAlt(alt);
    }

    @Override
    public ProfileListAll setFields(java.lang.String fields) {
      return (ProfileListAll) super.setFields(fields);
    }

    @Override
    public ProfileListAll setKey(java.lang.String key) {
      return (ProfileListAll) super.setKey(key);
    }

    @Override
    public ProfileListAll setOauthToken(java.lang.String oauthToken) {
      return (ProfileListAll) super.setOauthToken(oauthToken);
    }

    @Override
    public ProfileListAll setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (ProfileListAll) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public ProfileListAll setQuotaUser(java.lang.String quotaUser) {
      return (ProfileListAll) super.setQuotaUser(quotaUser);
    }

    @Override
    public ProfileListAll setUserIp(java.lang.String userIp) {
      return (ProfileListAll) super.setUserIp(userIp);
    }

    @Override
    public ProfileListAll set(String parameterName, Object value) {
      return (ProfileListAll) super.set(parameterName, value);
    }
  }

  /**
   * Create a request for the method "profile_post".
   *
   * This request holds the parameters needed by the xpertiseAPI server.  After setting any optional
   * parameters, call the {@link ProfilePost#execute()} method to invoke the remote operation.
   *
   * @param firstName
   * @param lastName
   * @param password
   * @param email
   * @param city
   * @param lat
   * @param lng
   * @param description
   * @return the request
   */
  public ProfilePost profilePost(java.lang.String firstName, java.lang.String lastName, java.lang.String password, java.lang.String email, java.lang.String city, java.lang.Double lat, java.lang.Double lng, java.lang.String description) throws java.io.IOException {
    ProfilePost result = new ProfilePost(firstName, lastName, password, email, city, lat, lng, description);
    initialize(result);
    return result;
  }

  public class ProfilePost extends XpertiseAPIRequest<com.example.scott.myapplication.backend.xpertiseAPI.model.MyBean> {

    private static final String REST_PATH = "mybean/{firstName}/{lastName}/{password}/{email}/{city}/{lat}/{lng}/{description}";

    /**
     * Create a request for the method "profile_post".
     *
     * This request holds the parameters needed by the the xpertiseAPI server.  After setting any
     * optional parameters, call the {@link ProfilePost#execute()} method to invoke the remote
     * operation. <p> {@link
     * ProfilePost#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
     * must be called to initialize this instance immediately after invoking the constructor. </p>
     *
     * @param firstName
     * @param lastName
     * @param password
     * @param email
     * @param city
     * @param lat
     * @param lng
     * @param description
     * @since 1.13
     */
    protected ProfilePost(java.lang.String firstName, java.lang.String lastName, java.lang.String password, java.lang.String email, java.lang.String city, java.lang.Double lat, java.lang.Double lng, java.lang.String description) {
      super(XpertiseAPI.this, "POST", REST_PATH, null, com.example.scott.myapplication.backend.xpertiseAPI.model.MyBean.class);
      this.firstName = com.google.api.client.util.Preconditions.checkNotNull(firstName, "Required parameter firstName must be specified.");
      this.lastName = com.google.api.client.util.Preconditions.checkNotNull(lastName, "Required parameter lastName must be specified.");
      this.password = com.google.api.client.util.Preconditions.checkNotNull(password, "Required parameter password must be specified.");
      this.email = com.google.api.client.util.Preconditions.checkNotNull(email, "Required parameter email must be specified.");
      this.city = com.google.api.client.util.Preconditions.checkNotNull(city, "Required parameter city must be specified.");
      this.lat = com.google.api.client.util.Preconditions.checkNotNull(lat, "Required parameter lat must be specified.");
      this.lng = com.google.api.client.util.Preconditions.checkNotNull(lng, "Required parameter lng must be specified.");
      this.description = com.google.api.client.util.Preconditions.checkNotNull(description, "Required parameter description must be specified.");
    }

    @Override
    public ProfilePost setAlt(java.lang.String alt) {
      return (ProfilePost) super.setAlt(alt);
    }

    @Override
    public ProfilePost setFields(java.lang.String fields) {
      return (ProfilePost) super.setFields(fields);
    }

    @Override
    public ProfilePost setKey(java.lang.String key) {
      return (ProfilePost) super.setKey(key);
    }

    @Override
    public ProfilePost setOauthToken(java.lang.String oauthToken) {
      return (ProfilePost) super.setOauthToken(oauthToken);
    }

    @Override
    public ProfilePost setPrettyPrint(java.lang.Boolean prettyPrint) {
      return (ProfilePost) super.setPrettyPrint(prettyPrint);
    }

    @Override
    public ProfilePost setQuotaUser(java.lang.String quotaUser) {
      return (ProfilePost) super.setQuotaUser(quotaUser);
    }

    @Override
    public ProfilePost setUserIp(java.lang.String userIp) {
      return (ProfilePost) super.setUserIp(userIp);
    }

    @com.google.api.client.util.Key
    private java.lang.String firstName;

    /**

     */
    public java.lang.String getFirstName() {
      return firstName;
    }

    public ProfilePost setFirstName(java.lang.String firstName) {
      this.firstName = firstName;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.String lastName;

    /**

     */
    public java.lang.String getLastName() {
      return lastName;
    }

    public ProfilePost setLastName(java.lang.String lastName) {
      this.lastName = lastName;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.String password;

    /**

     */
    public java.lang.String getPassword() {
      return password;
    }

    public ProfilePost setPassword(java.lang.String password) {
      this.password = password;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.String email;

    /**

     */
    public java.lang.String getEmail() {
      return email;
    }

    public ProfilePost setEmail(java.lang.String email) {
      this.email = email;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.String city;

    /**

     */
    public java.lang.String getCity() {
      return city;
    }

    public ProfilePost setCity(java.lang.String city) {
      this.city = city;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.Double lat;

    /**

     */
    public java.lang.Double getLat() {
      return lat;
    }

    public ProfilePost setLat(java.lang.Double lat) {
      this.lat = lat;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.Double lng;

    /**

     */
    public java.lang.Double getLng() {
      return lng;
    }

    public ProfilePost setLng(java.lang.Double lng) {
      this.lng = lng;
      return this;
    }

    @com.google.api.client.util.Key
    private java.lang.String description;

    /**

     */
    public java.lang.String getDescription() {
      return description;
    }

    public ProfilePost setDescription(java.lang.String description) {
      this.description = description;
      return this;
    }

    @Override
    public ProfilePost set(String parameterName, Object value) {
      return (ProfilePost) super.set(parameterName, value);
    }
  }

  /**
   * Builder for {@link XpertiseAPI}.
   *
   * <p>
   * Implementation is not thread-safe.
   * </p>
   *
   * @since 1.3.0
   */
  public static final class Builder extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient.Builder {

    /**
     * Returns an instance of a new builder.
     *
     * @param transport HTTP transport, which should normally be:
     *        <ul>
     *        <li>Google App Engine:
     *        {@code com.google.api.client.extensions.appengine.http.UrlFetchTransport}</li>
     *        <li>Android: {@code newCompatibleTransport} from
     *        {@code com.google.api.client.extensions.android.http.AndroidHttp}</li>
     *        <li>Java: {@link com.google.api.client.googleapis.javanet.GoogleNetHttpTransport#newTrustedTransport()}
     *        </li>
     *        </ul>
     * @param jsonFactory JSON factory, which may be:
     *        <ul>
     *        <li>Jackson: {@code com.google.api.client.json.jackson2.JacksonFactory}</li>
     *        <li>Google GSON: {@code com.google.api.client.json.gson.GsonFactory}</li>
     *        <li>Android Honeycomb or higher:
     *        {@code com.google.api.client.extensions.android.json.AndroidJsonFactory}</li>
     *        </ul>
     * @param httpRequestInitializer HTTP request initializer or {@code null} for none
     * @since 1.7
     */
    public Builder(com.google.api.client.http.HttpTransport transport, com.google.api.client.json.JsonFactory jsonFactory,
        com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
      super(
          transport,
          jsonFactory,
          DEFAULT_ROOT_URL,
          DEFAULT_SERVICE_PATH,
          httpRequestInitializer,
          false);
    }

    /** Builds a new instance of {@link XpertiseAPI}. */
    @Override
    public XpertiseAPI build() {
      return new XpertiseAPI(this);
    }

    @Override
    public Builder setRootUrl(String rootUrl) {
      return (Builder) super.setRootUrl(rootUrl);
    }

    @Override
    public Builder setServicePath(String servicePath) {
      return (Builder) super.setServicePath(servicePath);
    }

    @Override
    public Builder setHttpRequestInitializer(com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
      return (Builder) super.setHttpRequestInitializer(httpRequestInitializer);
    }

    @Override
    public Builder setApplicationName(String applicationName) {
      return (Builder) super.setApplicationName(applicationName);
    }

    @Override
    public Builder setSuppressPatternChecks(boolean suppressPatternChecks) {
      return (Builder) super.setSuppressPatternChecks(suppressPatternChecks);
    }

    @Override
    public Builder setSuppressRequiredParameterChecks(boolean suppressRequiredParameterChecks) {
      return (Builder) super.setSuppressRequiredParameterChecks(suppressRequiredParameterChecks);
    }

    @Override
    public Builder setSuppressAllChecks(boolean suppressAllChecks) {
      return (Builder) super.setSuppressAllChecks(suppressAllChecks);
    }

    /**
     * Set the {@link XpertiseAPIRequestInitializer}.
     *
     * @since 1.12
     */
    public Builder setXpertiseAPIRequestInitializer(
        XpertiseAPIRequestInitializer xpertiseapiRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(xpertiseapiRequestInitializer);
    }

    @Override
    public Builder setGoogleClientRequestInitializer(
        com.google.api.client.googleapis.services.GoogleClientRequestInitializer googleClientRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(googleClientRequestInitializer);
    }
  }
}
