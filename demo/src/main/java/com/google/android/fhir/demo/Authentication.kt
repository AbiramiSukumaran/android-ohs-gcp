package com.google.android.fhir.demo


import android.os.AsyncTask
import com.google.auth.oauth2.GoogleCredentials
import java.io.ByteArrayInputStream
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStream


 class Authentication  {
    // makeGetRequest makes a GET request to the specified Cloud Run or
    // Cloud Functions endpoint `serviceUrl` (must be a complete URL), by
    // authenticating with an ID token retrieved from Application Default
    // Credentials using the specified `audience`.
    //
    // For Cloud Functions, endpoint (`serviceUrl`) and `audience` are the same.
    // Example `audience` value (Cloud Functions): https://project-region-projectid.cloudfunctions.net/myFunction

     @Throws(IOException::class)
    fun makeGetRequest(
        serviceUrl: String?,
        audience: String?, param: String?
    ): com.google.api.client.http.HttpResponse {
        //val credentials: com.google.auth.oauth2.GoogleCredentials = com.google.auth.oauth2.GoogleCredentials.getApplicationDefault()

        val filePath: String = "com/google/android/fhir/demo/application_default_credentials.json"
        //val filePath = "application_default_credentials.json"

        val str: String = "<<YOUR_CLIENT_CREDENTIAL>>"
        val inputstr: InputStream = ByteArrayInputStream(str.toByteArray())
        val credentials: GoogleCredentials = GoogleCredentials.fromStream(inputstr)
            //GoogleCredentials.getApplicationDefault().createScoped(setOf(CloudHealthcareScopes.CLOUD_PLATFORM))
           // GoogleCredentials.fromStream( FileInputStream(filePath))
        require(credentials is com.google.auth.oauth2.IdTokenProvider) { "Credentials are not an instance of IdTokenProvider." }
        val tokenCredential: com.google.auth.oauth2.IdTokenCredentials =
            com.google.auth.oauth2.IdTokenCredentials.newBuilder()
                .setIdTokenProvider(credentials as com.google.auth.oauth2.IdTokenProvider)
                .setTargetAudience(audience + param)
                .build()
        val genericUrl: com.google.api.client.http.GenericUrl =
            com.google.api.client.http.GenericUrl(serviceUrl + param)
        val adapter: com.google.auth.http.HttpCredentialsAdapter =
            com.google.auth.http.HttpCredentialsAdapter(tokenCredential)
        val transport: com.google.api.client.http.HttpTransport =
            com.google.api.client.http.javanet.NetHttpTransport()
        val request: com.google.api.client.http.HttpRequest =
            transport.createRequestFactory(adapter).buildGetRequest(genericUrl)
        return request.execute()
    }


    fun main(args: Array<String>) {
        //final HttpRequest request_argfinal HttpResponse response_arg
        //final BufferedWriter writer = response_arg.getWriter();
        // writer.write("Hello world!");
/*        Log.d("apache http client version", version)
        Log.d("apache http client version", version);
        Log.d("*****Inside MAINNNNNNN", " call") */
        makeGetRequest("https://fhir-gcp-123-uxu5wi2jpa-uc.a.run.app?fhir_data=",
            "https://fhir-gcp-123-uxu5wi2jpa-uc.a.run.app?fhir_data=","")
    }
}
