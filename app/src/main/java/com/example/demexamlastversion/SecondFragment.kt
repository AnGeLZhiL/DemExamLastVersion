package com.example.demexamlastversion

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.demexamlastversion.databinding.FragmentSecondBinding
import okhttp3.*
import okio.IOException
import org.json.JSONObject

class SecondFragment : Fragment(), WantedAdapter.Listner {
    private lateinit var binding: FragmentSecondBinding
    private val client = OkHttpClient()
    private val adapter = WantedAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val request = Request.Builder()
            .url("http://mad2019.hakta.pro/api/wanted")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                TODO("Not yet implemented")
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.code == 200){
                    val jsonArray = JSONObject(response.body.string()).getJSONArray("data")
                    for (i in 0 until jsonArray.length()){
                        Global.wantedList.add(
                            WantedModel(
                                id = jsonArray.getJSONObject(i).getInt("id"),
                                nicknames = jsonArray.getJSONObject(i).getString("nicknames"),
                                description = jsonArray.getJSONObject(i).getString("description")
                            )
                        )
                    }
                    Handler(Looper.getMainLooper()).post {
                        binding.recycler.adapter = adapter
                    }
                }
            }

        })
    }

    override fun onClickItem(wantedModel: WantedModel) {
        findNavController().navigate(R.id.action_secondFragment_to_clickFragment)
        Global.id_wanted = wantedModel.id
    }

}