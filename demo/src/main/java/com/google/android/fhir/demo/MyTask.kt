package com.google.android.fhir.demo

import android.os.AsyncTask
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MyTask  {
   fun onLoadHere(pass_param: String) {
      //super.onCreate(savedInstanceState)
      MyTask.MyTask1(pass_param).execute()
   }

     class MyTask1(param: String) : AsyncTask<Void?, Void?, Void?>() {
      var result: String? = null
         var param_in: String? = param


      override fun onPostExecute(aVoid: Void?) {

        // super.onPostExecute(aVoid)
      }

        override fun doInBackground(vararg p0: Void?): Void? {
            val auth = Authentication()
            auth.makeGetRequest("https://fhir-gcp-123-uxu5wi2jpa-uc.a.run.app?fhir_data=",
                "https://fhir-gcp-123-uxu5wi2jpa-uc.a.run.app?fhir_data=",
                param_in);
            return null
        }
     }
}