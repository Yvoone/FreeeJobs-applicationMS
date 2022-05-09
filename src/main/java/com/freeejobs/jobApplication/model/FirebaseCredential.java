package com.freeejobs.jobApplication.model;

public class FirebaseCredential {
    private String type;
    private String project_id;
    private String private_key_id;
    private String private_key;
    private String client_email;
    private String client_id;
    private String auth_uri;
    private String token_uri;
    private String auth_provider_x509_cert_url;
    private String client_x509_cert_url;


    public String getType() {
        return System.getenv("TYPE");
    }

    public String getProject_id() {
        return System.getenv("PROJECT_ID");
    }

    public String getPrivate_key_id() {
        return System.getenv("PRIVATE_KEY_ID");
    }

    public String getPrivate_key() {
        String private_key = System.getenv("PRIVATE_KEY").replace("\\n", "\n");
        return private_key;
    }

    public String getClient_email() {
        return System.getenv("CLIENT_EMAIL");
    }

    public String getClient_id() {
        return System.getenv("CLIENT_ID");
    }

    public String getAuth_uri() {
        return System.getenv("AUTH_URI");
    }

    public String getToken_uri() {
        return System.getenv("TOKEN_URI");
    }

    public String getAuth_provider_x509_cert_url() {
        return System.getenv("AUTH_PROVIDER_X509_CERT_URL");
    }

    public String getClient_x509_cert_url() {
        return System.getenv("CLIENT_X509_CERT_URL");
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }

    public void setPrivate_key_id(String private_key_id) {
        this.private_key_id = private_key_id;
    }

    public void setPrivate_key(String private_key) {
        this.private_key = private_key;
    }

    public void setClient_email(String client_email) {
        this.client_email = client_email;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public void setAuth_uri(String auth_uri) {
        this.auth_uri = auth_uri;
    }

    public void setToken_uri(String token_uri) {
        this.token_uri = token_uri;
    }

    public void setAuth_provider_x509_cert_url(String auth_provider_x509_cert_url) {
        this.auth_provider_x509_cert_url = auth_provider_x509_cert_url;
    }

    public void setClient_x509_cert_url(String client_x509_cert_url) {
        this.client_x509_cert_url = client_x509_cert_url;
    }
}