package com.example.android_graphql_example

import android.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.apollographql.apollo3.exception.ApolloException
import com.example.android_graphql_example.adapter.CountryAdapter
import com.example.android_graphql_example.databinding.ActivityMainBinding
import com.example.android_graphql_example.network.GraphQL
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var continents = ArrayList<GetContinentsQuery.Continent>()

    private val adapter by lazy { CountryAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
    }

    private fun initViews() {
        getUserList()
        with(binding){
            rvCountries.layoutManager = LinearLayoutManager(this@MainActivity)


        }

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                getCountries(continents[position].code)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }


        }
    }

    private fun getUserList() {
        lifecycleScope.launch launchWhenResumed@{
            val response = try {
                GraphQL.get().query(GetContinentsQuery()).execute()
            } catch (e: ApolloException){
                Log.d("@@@", e.toString())
                return@launchWhenResumed
            }
            continents = response.data!!.continents as ArrayList<GetContinentsQuery.Continent>
            getCountries(continents[0].code)

            binding.spinner.adapter = ArrayAdapter(this@MainActivity,
                R.layout.simple_spinner_item, getContinentsName())
            Log.d("@@@", continents.size.toString())
        }
    }

    private fun getCountries(id: String){
        lifecycleScope.launch launchWhenResumed@{
            val response = try {
                GraphQL.get().query(FindCountriesOfAContinentQuery(id)).execute()
            } catch (e: ApolloException){
                Log.d("@@@", e.toString())
                return@launchWhenResumed
            }

            adapter.submitData(response.data!!.continent!!.countries)
            binding.rvCountries.adapter = adapter
            Log.d("@@@", response.data!!.continent!!.countries.toString())
        }
    }

    private fun getContinentsName(): Array<out Any> {
        val items = ArrayList<String>()
        for (i in continents){
            items.add(i.name)
        }
        return items.toArray()
    }

}