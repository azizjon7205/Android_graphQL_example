package com.example.android_graphql_example

import com.example.android_graphql_example.network.GraphQL
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.test.runTest
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun checkContinentsNotNull() = runTest{
        assertNotNull(GraphQL.get().query(GetContinentsQuery()))
        assertNotNull(GraphQL.get().query(GetContinentsQuery()).execute().data)
        assertNotNull(GraphQL.get().query(GetContinentsQuery()).execute().data!!.continents)
    }

    @Test
    fun checkCountriesNotNull() = runTest{
        assertNotNull(GraphQL.get().query(GetContinentsQuery()))
        assertNotNull(GraphQL.get().query(GetContinentsQuery()).execute().data)
        assertNotNull(GraphQL.get().query(FindCountriesOfAContinentQuery("AF")).execute().data!!.continent)
        assertNotNull(GraphQL.get().query(FindCountriesOfAContinentQuery("AF")).execute().data!!.continent?.countries)
    }


    @Test
    fun checkCountriesNotEmpty() = runTest{
        assertNotNull(GraphQL.get().query(GetContinentsQuery()))
        assertNotNull(GraphQL.get().query(GetContinentsQuery()).execute().data)
        assertNotNull(GraphQL.get().query(FindCountriesOfAContinentQuery("AF")).execute().data!!.continent)
        assertNotNull(GraphQL.get().query(FindCountriesOfAContinentQuery("AF")).execute().data!!.continent?.countries)
        assertTrue(GraphQL.get().query(FindCountriesOfAContinentQuery("AF")).execute().data!!.continent?.countries?.size != 0)
    }


}