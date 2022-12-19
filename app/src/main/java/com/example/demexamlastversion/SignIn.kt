package com.example.demexamlastversion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.example.demexamlastversion.databinding.ActivitySignInBinding
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okio.IOException
import org.json.JSONObject

class SignIn : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    private val emailRegex = "[a-z0-9]+@[a-z]+\\.+[a-z]{2,3}"
    private val client = OkHttpClient()
    private lateinit var alertDialog: AlertDialog.Builder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signUp.setOnClickListener {
            startActivity(Intent(this, SignUp::class.java))
            finish()
        }

        alertDialog = AlertDialog.Builder(this)

        binding.signIn.setOnClickListener {
            if (binding.email.text.isNotEmpty() and binding.password.text.isNotEmpty()){
                if (binding.email.text.toString().trim().matches(emailRegex.toRegex())){
                    val requestBody = RequestBody.create(
                        "application/json".toMediaTypeOrNull(),
                        JSONObject()
                            .put("email", binding.email.text.toString())
                            .put("password", binding.password.text.toString())
                            .toString()
                    )
                    val request = Request.Builder()
                        .url("${Global.base_url}/user/login")
                        .post(requestBody)
                        .build()
                    client.newCall(request).enqueue(object : Callback{
                        override fun onFailure(call: Call, e: IOException) {
                            this@SignIn.runOnUiThread(java.lang.Runnable {
                                alertDialog
                                    .setTitle("Error")
                                    .setMessage("Kakaya-to erynda y vas")
                                    .setCancelable(true)
                                    .setPositiveButton("Ok"){dialog, it ->
                                        dialog.cancel()
                                    }
                                    .show()
                            })
                        }
                        override fun onResponse(call: Call, response: Response) {
                            if (response.code == 200){
                                Global.token = JSONObject(response.body.string()).getString("token")
                                startActivity(Intent(this@SignIn, MenuActivity::class.java))
                            } else if (response.code == 469){
                                this@SignIn.runOnUiThread(java.lang.Runnable {
                                    alertDialog
                                        .setTitle("Error")
                                        .setMessage("Email not found")
                                        .setCancelable(true)
                                        .setPositiveButton("Ok"){dialog, it ->
                                            dialog.cancel()
                                        }
                                        .show()
                                })
                            }
                        }

                    })
                } else binding.email.error = "некорректное заполнение"
            } else {
                if (binding.email.text.isEmpty()) binding.email.error = "Поле пустое"
                if (binding.password.text.isEmpty()) binding.password.error = "Поле пустое"
            }
        }
    }
}