package org.loopring.looprwalletnetwork

import android.util.Log
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

@RunWith(MockitoJUnitRunner::class)
class LoopringServiceTest {

    private lateinit var serviceMock: LoopringServiceInternal
    private lateinit var serviceLive: LoopringService

    val dispatcher: Dispatcher = object : Dispatcher() {


        @Throws(InterruptedException::class)
        override fun dispatch(request: RecordedRequest): MockResponse {
            when {

                request.path == "/api?module=stats&action=ethsupply&apikey=537ZMY3HV44B111IV29WNPTEAE3ZNSBU5M" ->
                    return MockResponse().setResponseCode(200).setBody("{\"status\":\"1\",\"message\":\"OK\",\"result\":\"98272428967800000000000000\"}")
                request.path == "/api?module=stats&action=ethprice&apikey=537ZMY3HV44B111IV29WNPTEAE3ZNSBU5M" ->
                    return MockResponse().setResponseCode(200).setBody("{\"status\":\"1\",\"message\":\"OK\",\"result\":{\"ethbtc\":\"0.06465\",\"ethbtc_timestamp\":\"1521425340\",\"ethusd\":\"526.8\",\"ethusd_timestamp\":\"1521425342\"}}")
                request.path == "/api?module=contract&action=getabi&apikey=537ZMY3HV44B111IV29WNPTEAE3ZNSBU5M&address=0xef68e7c694f40c8202821edf525de3782458639f" ->
                    return MockResponse().setResponseCode(200).setBody("{\"status\":\"1\",\"message\":\"OK\",\"result\":\"[{\\\"constant\\\":false,\\\"inputs\\\":[{\\\"name\\\":\\\"_spender\\\",\\\"type\\\":\\\"address\\\"},{\\\"name\\\":\\\"_value\\\",\\\"type\\\":\\\"uint256\\\"}],\\\"name\\\":\\\"approve\\\",\\\"outputs\\\":[],\\\"payable\\\":false,\\\"type\\\":\\\"function\\\"},{\\\"constant\\\":true,\\\"inputs\\\":[],\\\"name\\\":\\\"totalSupply\\\",\\\"outputs\\\":[{\\\"name\\\":\\\"\\\",\\\"type\\\":\\\"uint256\\\"}],\\\"payable\\\":false,\\\"type\\\":\\\"function\\\"},{\\\"constant\\\":true,\\\"inputs\\\":[{\\\"name\\\":\\\"\\\",\\\"type\\\":\\\"uint256\\\"}],\\\"name\\\":\\\"bonusPercentages\\\",\\\"outputs\\\":[{\\\"name\\\":\\\"\\\",\\\"type\\\":\\\"uint8\\\"}],\\\"payable\\\":false,\\\"type\\\":\\\"function\\\"},{\\\"constant\\\":false,\\\"inputs\\\":[{\\\"name\\\":\\\"_from\\\",\\\"type\\\":\\\"address\\\"},{\\\"name\\\":\\\"_to\\\",\\\"type\\\":\\\"address\\\"},{\\\"name\\\":\\\"_value\\\",\\\"type\\\":\\\"uint256\\\"}],\\\"name\\\":\\\"transferFrom\\\",\\\"outputs\\\":[],\\\"payable\\\":false,\\\"type\\\":\\\"function\\\"},{\\\"constant\\\":true,\\\"inputs\\\":[],\\\"name\\\":\\\"DECIMALS\\\",\\\"outputs\\\":[{\\\"name\\\":\\\"\\\",\\\"type\\\":\\\"uint256\\\"}],\\\"payable\\\":false,\\\"type\\\":\\\"function\\\"},{\\\"constant\\\":true,\\\"inputs\\\":[],\\\"name\\\":\\\"BLOCKS_PER_PHASE\\\",\\\"outputs\\\":[{\\\"name\\\":\\\"\\\",\\\"type\\\":\\\"uint16\\\"}],\\\"payable\\\":false,\\\"type\\\":\\\"function\\\"},{\\\"constant\\\":true,\\\"inputs\\\":[],\\\"name\\\":\\\"MAX_UNSOLD_RATIO\\\",\\\"outputs\\\":[{\\\"name\\\":\\\"\\\",\\\"type\\\":\\\"uint256\\\"}],\\\"payable\\\":false,\\\"type\\\":\\\"function\\\"},{\\\"constant\\\":true,\\\"inputs\\\":[],\\\"name\\\":\\\"HARD_CAP\\\",\\\"outputs\\\":[{\\\"name\\\":\\\"\\\",\\\"type\\\":\\\"uint256\\\"}],\\\"payable\\\":false,\\\"type\\\":\\\"function\\\"},{\\\"constant\\\":true,\\\"inputs\\\":[],\\\"name\\\":\\\"BASE_RATE\\\",\\\"outputs\\\":[{\\\"name\\\":\\\"\\\",\\\"type\\\":\\\"uint256\\\"}],\\\"payable\\\":false,\\\"type\\\":\\\"function\\\"},{\\\"constant\\\":false,\\\"inputs\\\":[],\\\"name\\\":\\\"close\\\",\\\"outputs\\\":[],\\\"payable\\\":false,\\\"type\\\":\\\"function\\\"},{\\\"constant\\\":true,\\\"inputs\\\":[],\\\"name\\\":\\\"saleStarted\\\",\\\"outputs\\\":[{\\\"name\\\":\\\"\\\",\\\"type\\\":\\\"bool\\\"}],\\\"payable\\\":false,\\\"type\\\":\\\"function\\\"},{\\\"constant\\\":true,\\\"inputs\\\":[],\\\"name\\\":\\\"issueIndex\\\",\\\"outputs\\\":[{\\\"name\\\":\\\"\\\",\\\"type\\\":\\\"uint256\\\"}],\\\"payable\\\":false,\\\"type\\\":\\\"function\\\"},{\\\"constant\\\":true,\\\"inputs\\\":[{\\\"name\\\":\\\"_owner\\\",\\\"type\\\":\\\"address\\\"}],\\\"name\\\":\\\"balanceOf\\\",\\\"outputs\\\":[{\\\"name\\\":\\\"balance\\\",\\\"type\\\":\\\"uint256\\\"}],\\\"payable\\\":false,\\\"type\\\":\\\"function\\\"},{\\\"constant\\\":false,\\\"inputs\\\":[{\\\"name\\\":\\\"recipient\\\",\\\"type\\\":\\\"address\\\"}],\\\"name\\\":\\\"issueToken\\\",\\\"outputs\\\":[],\\\"payable\\\":true,\\\"type\\\":\\\"function\\\"},{\\\"constant\\\":false,\\\"inputs\\\":[{\\\"name\\\":\\\"_firstblock\\\",\\\"type\\\":\\\"uint256\\\"}],\\\"name\\\":\\\"start\\\",\\\"outputs\\\":[],\\\"payable\\\":false,\\\"type\\\":\\\"function\\\"},{\\\"constant\\\":true,\\\"inputs\\\":[],\\\"name\\\":\\\"hardCapReached\\\",\\\"outputs\\\":[{\\\"name\\\":\\\"\\\",\\\"type\\\":\\\"bool\\\"}],\\\"payable\\\":false,\\\"type\\\":\\\"function\\\"},{\\\"constant\\\":true,\\\"inputs\\\":[],\\\"name\\\":\\\"saleEnded\\\",\\\"outputs\\\":[{\\\"name\\\":\\\"\\\",\\\"type\\\":\\\"bool\\\"}],\\\"payable\\\":false,\\\"type\\\":\\\"function\\\"},{\\\"constant\\\":true,\\\"inputs\\\":[],\\\"name\\\":\\\"unsoldTokenIssued\\\",\\\"outputs\\\":[{\\\"name\\\":\\\"\\\",\\\"type\\\":\\\"bool\\\"}],\\\"payable\\\":false,\\\"type\\\":\\\"function\\\"},{\\\"constant\\\":true,\\\"inputs\\\":[],\\\"name\\\":\\\"price\\\",\\\"outputs\\\":[{\\\"name\\\":\\\"tokens\\\",\\\"type\\\":\\\"uint256\\\"}],\\\"payable\\\":false,\\\"type\\\":\\\"function\\\"},{\\\"constant\\\":true,\\\"inputs\\\":[],\\\"name\\\":\\\"GOAL\\\",\\\"outputs\\\":[{\\\"name\\\":\\\"\\\",\\\"type\\\":\\\"uint256\\\"}],\\\"payable\\\":false,\\\"type\\\":\\\"function\\\"},{\\\"constant\\\":true,\\\"inputs\\\":[],\\\"name\\\":\\\"NAME\\\",\\\"outputs\\\":[{\\\"name\\\":\\\"\\\",\\\"type\\\":\\\"string\\\"}],\\\"payable\\\":false,\\\"type\\\":\\\"function\\\"},{\\\"constant\\\":false,\\\"inputs\\\":[{\\\"name\\\":\\\"_to\\\",\\\"type\\\":\\\"address\\\"},{\\\"name\\\":\\\"_value\\\",\\\"type\\\":\\\"uint256\\\"}],\\\"name\\\":\\\"transfer\\\",\\\"outputs\\\":[],\\\"payable\\\":false,\\\"type\\\":\\\"function\\\"},{\\\"constant\\\":true,\\\"inputs\\\":[],\\\"name\\\":\\\"totalEthReceived\\\",\\\"outputs\\\":[{\\\"name\\\":\\\"\\\",\\\"type\\\":\\\"uint256\\\"}],\\\"payable\\\":false,\\\"type\\\":\\\"function\\\"},{\\\"constant\\\":true,\\\"inputs\\\":[],\\\"name\\\":\\\"saleDue\\\",\\\"outputs\\\":[{\\\"name\\\":\\\"\\\",\\\"type\\\":\\\"bool\\\"}],\\\"payable\\\":false,\\\"type\\\":\\\"function\\\"},{\\\"constant\\\":true,\\\"inputs\\\":[],\\\"name\\\":\\\"target\\\",\\\"outputs\\\":[{\\\"name\\\":\\\"\\\",\\\"type\\\":\\\"address\\\"}],\\\"payable\\\":false,\\\"type\\\":\\\"function\\\"},{\\\"constant\\\":true,\\\"inputs\\\":[],\\\"name\\\":\\\"NUM_OF_PHASE\\\",\\\"outputs\\\":[{\\\"name\\\":\\\"\\\",\\\"type\\\":\\\"uint256\\\"}],\\\"payable\\\":false,\\\"type\\\":\\\"function\\\"},{\\\"constant\\\":true,\\\"inputs\\\":[{\\\"name\\\":\\\"_owner\\\",\\\"type\\\":\\\"address\\\"},{\\\"name\\\":\\\"_spender\\\",\\\"type\\\":\\\"address\\\"}],\\\"name\\\":\\\"allowance\\\",\\\"outputs\\\":[{\\\"name\\\":\\\"remaining\\\",\\\"type\\\":\\\"uint256\\\"}],\\\"payable\\\":false,\\\"type\\\":\\\"function\\\"},{\\\"constant\\\":true,\\\"inputs\\\":[],\\\"name\\\":\\\"firstblock\\\",\\\"outputs\\\":[{\\\"name\\\":\\\"\\\",\\\"type\\\":\\\"uint256\\\"}],\\\"payable\\\":false,\\\"type\\\":\\\"function\\\"},{\\\"constant\\\":true,\\\"inputs\\\":[],\\\"name\\\":\\\"SYMBOL\\\",\\\"outputs\\\":[{\\\"name\\\":\\\"\\\",\\\"type\\\":\\\"string\\\"}],\\\"payable\\\":false,\\\"type\\\":\\\"function\\\"},{\\\"inputs\\\":[{\\\"name\\\":\\\"_target\\\",\\\"type\\\":\\\"address\\\"}],\\\"payable\\\":false,\\\"type\\\":\\\"constructor\\\"},{\\\"payable\\\":true,\\\"type\\\":\\\"fallback\\\"},{\\\"anonymous\\\":false,\\\"inputs\\\":[],\\\"name\\\":\\\"SaleStarted\\\",\\\"type\\\":\\\"event\\\"},{\\\"anonymous\\\":false,\\\"inputs\\\":[],\\\"name\\\":\\\"SaleEnded\\\",\\\"type\\\":\\\"event\\\"},{\\\"anonymous\\\":false,\\\"inputs\\\":[{\\\"indexed\\\":false,\\\"name\\\":\\\"caller\\\",\\\"type\\\":\\\"address\\\"}],\\\"name\\\":\\\"InvalidCaller\\\",\\\"type\\\":\\\"event\\\"},{\\\"anonymous\\\":false,\\\"inputs\\\":[{\\\"indexed\\\":false,\\\"name\\\":\\\"msg\\\",\\\"type\\\":\\\"bytes\\\"}],\\\"name\\\":\\\"InvalidState\\\",\\\"type\\\":\\\"event\\\"},{\\\"anonymous\\\":false,\\\"inputs\\\":[{\\\"indexed\\\":false,\\\"name\\\":\\\"issueIndex\\\",\\\"type\\\":\\\"uint256\\\"},{\\\"indexed\\\":false,\\\"name\\\":\\\"addr\\\",\\\"type\\\":\\\"address\\\"},{\\\"indexed\\\":false,\\\"name\\\":\\\"ethAmount\\\",\\\"type\\\":\\\"uint256\\\"},{\\\"indexed\\\":false,\\\"name\\\":\\\"tokenAmount\\\",\\\"type\\\":\\\"uint256\\\"}],\\\"name\\\":\\\"Issue\\\",\\\"type\\\":\\\"event\\\"},{\\\"anonymous\\\":false,\\\"inputs\\\":[],\\\"name\\\":\\\"SaleSucceeded\\\",\\\"type\\\":\\\"event\\\"},{\\\"anonymous\\\":false,\\\"inputs\\\":[],\\\"name\\\":\\\"SaleFailed\\\",\\\"type\\\":\\\"event\\\"},{\\\"anonymous\\\":false,\\\"inputs\\\":[{\\\"indexed\\\":true,\\\"name\\\":\\\"owner\\\",\\\"type\\\":\\\"address\\\"},{\\\"indexed\\\":true,\\\"name\\\":\\\"spender\\\",\\\"type\\\":\\\"address\\\"},{\\\"indexed\\\":false,\\\"name\\\":\\\"value\\\",\\\"type\\\":\\\"uint256\\\"}],\\\"name\\\":\\\"Approval\\\",\\\"type\\\":\\\"event\\\"},{\\\"anonymous\\\":false,\\\"inputs\\\":[{\\\"indexed\\\":true,\\\"name\\\":\\\"from\\\",\\\"type\\\":\\\"address\\\"},{\\\"indexed\\\":true,\\\"name\\\":\\\"to\\\",\\\"type\\\":\\\"address\\\"},{\\\"indexed\\\":false,\\\"name\\\":\\\"value\\\",\\\"type\\\":\\\"uint256\\\"}],\\\"name\\\":\\\"Transfer\\\",\\\"type\\\":\\\"event\\\"}]\"}")
                request.path == "/api?module=account&action=balance&tag=latest&apikey=537ZMY3HV44B111IV29WNPTEAE3ZNSBU5M&address=0x05ee546c1a62f90d7acbffd6d846c9c54c7cf94c" ->
                    return MockResponse().setResponseCode(200).setBody("{\"status\":\"1\",\"message\":\"OK\",\"result\":\"94304755860870236935545\"}")
                request.path == "/api?module=account&action=balancemulti&tag=latest&apikey=537ZMY3HV44B111IV29WNPTEAE3ZNSBU5M&address=0x05ee546c1a62f90d7acbffd6d846c9c54c7cf94c%2C0x6fe1d5ae5ea6ba334bd4c1cc2dd8b6695314d82d" ->
                    return MockResponse().setResponseCode(200).setBody("{\"status\":\"1\",\"message\":\"OK\",\"result\":[{\"account\":\"0x05ee546c1a62f90d7acbffd6d846c9c54c7cf94c\",\"balance\":\"34601796320353675276601\"},{\"account\":\"0x6fe1d5ae5ea6ba334bd4c1cc2dd8b6695314d82d\",\"balance\":\"9328084852000000\"}]}")
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
        val retrofitMock = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()

        serviceMock = retrofitMock.create<LoopringServiceInternal>(LoopringServiceInternal::class.java)


        serviceLive = LoopringService()
    }

    /**
     * Testing best case, all proper data is returned
     * Mock server
     */
    @Test
    fun getBalances_shouldWorkMock() = runBlocking {
        val deferred = serviceLive.getBalances("0x5496379c453c4e6Bf8DDf9794Ae8783D2B4Cb07d")

        val result = deferred.await()
        Assert.assertEquals(result.id,64)
    }
}