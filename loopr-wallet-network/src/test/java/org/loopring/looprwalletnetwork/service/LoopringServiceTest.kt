package org.loopring.looprwalletnetwork

import android.util.Log
import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import org.loopring.looprwalletnetwork.services.LoopringService
import kotlinx.coroutines.experimental.runBlocking
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.loopring.looprwalletnetwork.services.LoopringServiceInternal
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.math.BigInteger
import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import com.google.gson.JsonObject


@RunWith(MockitoJUnitRunner::class)
class LoopringServiceTest {

    private lateinit var serviceMock: LoopringService
    private lateinit var serviceLive: LoopringService

    val dispatcher: Dispatcher = object : Dispatcher() {


        @Throws(InterruptedException::class)
        override fun dispatch(request: RecordedRequest): MockResponse {
            //throw(Throwable(request.body.readUtf8()))
            val gson = GsonBuilder().create()
            val requestBody = gson.fromJson(request.body.readUtf8(), JsonObject::class.java)
            val method = requestBody.get("method").asString
            val jsonrpc = requestBody.get("jsonrpc").asString
            val params = requestBody.get("params").asJsonArray.get(0).asJsonObject
            val id = requestBody.get("id").asString
            when {
                method == "loopring_getBalance" ->
                    return MockResponse().setResponseCode(200).setBody("{\"jsonrpc\":\"2.0\",\"id\":64,\"result\":{\"delegateAddress\":\"0x5567ee920f7E62274284985D793344351A00142B\",\"owner\":\"0x5496379c453c4e6Bf8DDf9794Ae8783D2B4Cb07d\",\"tokens\":[{\"symbol\":\"VITE\",\"balance\":\"0\",\"allowance\":\"0\"},{\"symbol\":\"DAI\",\"balance\":\"0\",\"allowance\":\"0\"},{\"symbol\":\"FOO\",\"balance\":\"0\",\"allowance\":\"0\"},{\"symbol\":\"WETH\",\"balance\":\"0\",\"allowance\":\"0\"},{\"symbol\":\"RDN\",\"balance\":\"0\",\"allowance\":\"0\"},{\"symbol\":\"LRC\",\"balance\":\"0\",\"allowance\":\"0\"},{\"symbol\":\"BNT\",\"balance\":\"0\",\"allowance\":\"0\"},{\"symbol\":\"ZRX\",\"balance\":\"0\",\"allowance\":\"0\"},{\"symbol\":\"BAR\",\"balance\":\"0\",\"allowance\":\"0\"},{\"symbol\":\"RHOC\",\"balance\":\"0\",\"allowance\":\"0\"},{\"symbol\":\"BAT\",\"balance\":\"0\",\"allowance\":\"0\"},{\"symbol\":\"IOST\",\"balance\":\"0\",\"allowance\":\"0\"},{\"symbol\":\"OMG\",\"balance\":\"0\",\"allowance\":\"0\"},{\"symbol\":\"KNC\",\"balance\":\"0\",\"allowance\":\"0\"},{\"symbol\":\"ETH\",\"balance\":\"0\",\"allowance\":\"0\"},{\"symbol\":\"SNT\",\"balance\":\"0\",\"allowance\":\"0\"},{\"symbol\":\"ARP\",\"balance\":\"0\",\"allowance\":\"0\"},{\"symbol\":\"EOS\",\"balance\":\"0\",\"allowance\":\"0\"},{\"symbol\":\"REQ\",\"balance\":\"0\",\"allowance\":\"0\"}]}}")
                //request.path == "/api?module=stats&action=ethsupply&apikey=537ZMY3HV44B111IV29WNPTEAE3ZNSBU5M" ->
                //    return MockResponse().setResponseCode(200).setBody("{\"status\":\"1\",\"message\":\"OK\",\"result\":\"98272428967800000000000000\"}")
                else -> return MockResponse().setResponseCode(404)
            }
        }
    }

    @Before
    fun setup() {
        // Create a MockWebServer. These are lean enough that you can create a new
        // instance for every unit test.
        val server = MockWebServer()
        server.setDispatcher(dispatcher)

        // Start the server.
        server.start()

        // Ask the server for its URL. You'll need this to make HTTP requests.
        val baseUrl = server.url("")

        //Log.e("TESTING","BaseUrl: $baseUrl")

        //TODO - register new type adapters
        /*val retrofitMock = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()*/

        serviceMock = LoopringService()
        serviceMock.setMock(baseUrl)


        serviceLive = LoopringService()
    }

    /**
     * Testing best case, all proper data is returned
     * Mock server
     */
    @Test
    fun getBalances_shouldWorkMock() = runBlocking {
        val deferred = serviceMock.getBalances("0x5496379c453c4e6Bf8DDf9794Ae8783D2B4Cb07d")

        val result = deferred.await()
        Assert.assertEquals(serviceMock.id, result.id)
        Assert.assertEquals(serviceMock.jsonRpcVersion, result.jsonrpc)
        Assert.assertEquals(serviceMock.delegateAddress, result.delegateAddress)
        Assert.assertTrue(result.tokens?.get(0)?.token is String && result.tokens?.get(0)?.token != "")
        Assert.assertTrue(result.tokens?.get(0)?.balance?.compareTo(BigInteger("0"))!! >= 0)
        Assert.assertTrue(result.tokens?.get(0)?.allowance?.compareTo(BigInteger("0"))!! >= 0)
    }

    /**
     * Testing best case, all proper data is returned
     * Live server
     */
    @Test
    fun getBalances_shouldWorkLive() = runBlocking {
        val deferred = serviceLive.getBalances("0x5496379c453c4e6Bf8DDf9794Ae8783D2B4Cb07d")

        val result = deferred.await()
        Assert.assertEquals(serviceLive.id, result.id)
        Assert.assertEquals(serviceLive.jsonRpcVersion, result.jsonrpc)
        Assert.assertEquals(serviceLive.delegateAddress, result.delegateAddress)
        Assert.assertTrue(result.tokens?.get(0)?.token is String && result.tokens?.get(0)?.token != "")
        Assert.assertTrue(result.tokens?.get(0)?.balance?.compareTo(BigInteger("0"))!! >= 0)
        Assert.assertTrue(result.tokens?.get(0)?.allowance?.compareTo(BigInteger("0"))!! >= 0)
    }
}