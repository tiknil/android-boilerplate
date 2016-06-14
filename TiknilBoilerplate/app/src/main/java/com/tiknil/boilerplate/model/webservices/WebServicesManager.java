package com.tiknil.boilerplate.model.webservices;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tiknil.boilerplate.BuildConfig;
import com.tiknil.boilerplate.model.webservices.webservices_utils.BooleanAdapter;
import com.tiknil.boilerplate.model.webservices.webservices_utils.DateAdapter;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManagerFactory;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Gestisce la comunicazione con i web services
 *
 * @TiKnil
 */
public class WebServicesManager {

    //region Inner enums
    //endregion


    //region Constants
    private static final String TAG = WebServicesManager.class.getSimpleName();
    //endregion


    //region Instance Fields
    //endregion


    //region Class methods
    public static String actualEndPoint() {
        return BuildConfig.DEBUG ? DEV_SERVER_URL : PROD_SERVER_URL;
    }
    //endregion


    //region Constructors / Lifecycle

    public WebServicesManager(Context context) {

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new DateAdapter("yyyy'-'MM'-'dd' 'HH':'mm':'ss", Locale.getDefault(), TimeZone.getTimeZone("UTC")))
                .registerTypeAdapter(Boolean.class, new BooleanAdapter())
                .create();

        SSLContext sslContext = null;
        try {
            TrustManagerFactory trustManagerFactory = null;

            //Recuperare il certificato pubblico dal server tramite il codice (sostituendo ${MY_SERVER} con il dominio - es: dev.shellyapp.co)
            //echo | openssl s_client -connect ${MY_SERVER}:443 2>&1 | \sed -ne '/-BEGIN CERTIFICATE-/,/-END CERTIFICATE-/p' > mycert.pem

            //Quindi convertire il file .pem in file .crt con il comando
            //openssl x509 -outform der -in mycert.pem -out mycert.crt

            //Salvare il file risultante nella cartella "raw" dentro a "res" del progetto

            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            InputStream cert = context.getResources().openRawResource(0 /* TODO: BuildConfig.DEBUG ? R.raw.dev : R.raw.prod*/);
            Certificate ca;
            try {
                ca = cf.generateCertificate(cert);
                Log.d(TAG, "ca=" + ((X509Certificate) ca).getSubjectDN());
            } finally {
                cert.close();
            }

            // Create a KeyStore containing our trusted CAs
            String keyStoreType = KeyStore.getDefaultType();
            KeyStore keyStore = KeyStore.getInstance(keyStoreType);
            keyStore.load(null, null);
            keyStore.setCertificateEntry("ca", ca);

            sslContext = SSLContext.getInstance("TLS");
            trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            keyManagerFactory.init(keyStore, "keystore_pass".toCharArray());
            sslContext.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), new SecureRandom());

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request originalRequest = chain.request();

                            Request newRequest = null;
                            if (TextUtils.isEmpty(accessToken)) {
                                newRequest = originalRequest.newBuilder()
                                        .addHeader("Accept", "application/json")
                                        .build();
                            } else {
                                newRequest = originalRequest.newBuilder()
                                        .addHeader("Accept", "application/json")
                                        .addHeader("Authorization", "bearer " + accessToken)
                                        .build();
                            }

                            return chain.proceed(newRequest);
                        }
                    })
                    .sslSocketFactory(sslContext.getSocketFactory())
                    .hostnameVerifier(new HostnameVerifier() {
                        @Override
                        public boolean verify(String hostname, SSLSession session) {
                            return ((BuildConfig.DEBUG ? DEV_SERVER_URL : PROD_SERVER_URL).contains(hostname));
                        }
                    })
                    .build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.DEBUG ? DEV_SERVER_URL : PROD_SERVER_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();

            webServices = retrofit.create(WebServices.class);

        } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            Log.w(TAG, "Errore durante l'impostazione del client http per la connessione con SSL", noSuchAlgorithmException);
        } catch (KeyStoreException keyStoreException) {
            Log.w(TAG, "Errore durante l'impostazione del client http per la connessione con SSL", keyStoreException);
        } catch (UnrecoverableKeyException unrecoverableKeyException) {
            Log.w(TAG, "Errore durante l'impostazione del client http per la connessione con SSL", unrecoverableKeyException);
        } catch (KeyManagementException keyManagementException) {
            Log.w(TAG, "Errore durante l'impostazione del client http per la connessione con SSL", keyManagementException);
        } catch (CertificateException certificateException) {
            Log.w(TAG, "Errore durante l'impostazione del client http per la connessione con SSL", certificateException);
        } catch (IOException ioException) {
            Log.w(TAG, "Errore durante l'impostazione del client http per la connessione con SSL", ioException);
        }

        instance = this;
    }

    //endregion


    //region Custom accessors
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public WebServices getWebServices() {
        return webServices;
    }

    //endregion


    //region Public
    //endregion

    //region Protected, without modifier
    //endregion

    //region Private
    //endregion


    //region Override methods and callbacks
    //endregion

    //region Inner classes or interfaces
    //endregion

    private static final String PROD_SERVER_URL = "https://api.boilerplate.com";
    private static final String DEV_SERVER_URL = "https://dev.boilerplate.com";

    private WebServices webServices = null;
    private String accessToken = "";
    private static WebServicesManager instance = null;

    public static WebServicesManager actualInstance() {
        return instance;
    }

}
