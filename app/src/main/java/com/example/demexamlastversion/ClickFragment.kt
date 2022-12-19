package com.example.demexamlastversion

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.demexamlastversion.databinding.FragmentClickBinding
import okhttp3.*
import okio.IOException
import org.json.JSONObject

class ClickFragment : Fragment() {
    private lateinit var binding: FragmentClickBinding
    private val client = OkHttpClient()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentClickBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val request = Request.Builder()
            .url("http://mad2019.hakta.pro/api/wanted/${Global.id_wanted}")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                TODO("Not yet implemented")
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.code == 200) {
                    val jsonObject = JSONObject(response.body.string()).getJSONObject("data")
                    binding.nicknames.text = jsonObject.getString("nicknames")
                    binding.description.text = jsonObject.getString("description")
                }

            }

        })
    }
}