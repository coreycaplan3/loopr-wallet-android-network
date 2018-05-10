package org.loopring.looprwalletnetwork

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
import org.mockito.junit.MockitoJUnitRunner
import java.math.BigInteger
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import java.util.*


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
            when { //TODO - externalize these responses
                method == "loopring_getBalance" -> //
                    return MockResponse().setResponseCode(200).setBody("{\"jsonrpc\":\"2.0\",\"id\":64,\"result\":{\"delegateAddress\":\""+serviceMock.delegateAddress+"\",\"owner\":\"0x5496379c453c4e6Bf8DDf9794Ae8783D2B4Cb07d\",\"tokens\":[{\"symbol\":\"VITE\",\"balance\":\"0\",\"allowance\":\"0\"},{\"symbol\":\"DAI\",\"balance\":\"0\",\"allowance\":\"0\"},{\"symbol\":\"FOO\",\"balance\":\"0\",\"allowance\":\"0\"},{\"symbol\":\"WETH\",\"balance\":\"0\",\"allowance\":\"0\"},{\"symbol\":\"RDN\",\"balance\":\"0\",\"allowance\":\"0\"},{\"symbol\":\"LRC\",\"balance\":\"0\",\"allowance\":\"0\"},{\"symbol\":\"BNT\",\"balance\":\"0\",\"allowance\":\"0\"},{\"symbol\":\"ZRX\",\"balance\":\"0\",\"allowance\":\"0\"},{\"symbol\":\"BAR\",\"balance\":\"0\",\"allowance\":\"0\"},{\"symbol\":\"RHOC\",\"balance\":\"0\",\"allowance\":\"0\"},{\"symbol\":\"BAT\",\"balance\":\"0\",\"allowance\":\"0\"},{\"symbol\":\"IOST\",\"balance\":\"0\",\"allowance\":\"0\"},{\"symbol\":\"OMG\",\"balance\":\"0\",\"allowance\":\"0\"},{\"symbol\":\"KNC\",\"balance\":\"0\",\"allowance\":\"0\"},{\"symbol\":\"ETH\",\"balance\":\"0\",\"allowance\":\"0\"},{\"symbol\":\"SNT\",\"balance\":\"0\",\"allowance\":\"0\"},{\"symbol\":\"ARP\",\"balance\":\"0\",\"allowance\":\"0\"},{\"symbol\":\"EOS\",\"balance\":\"0\",\"allowance\":\"0\"},{\"symbol\":\"REQ\",\"balance\":\"0\",\"allowance\":\"0\"}]}}")
                method == "loopring_getOrders" -> //
                    return MockResponse().setResponseCode(200).setBody("{\"jsonrpc\":\"2.0\",\"id\":64,\"result\":{\"data\":[],\"pageIndex\":2,\"pageSize\":40,\"total\":0}}")
                method == "loopring_getDepth" ->
                    return MockResponse().setResponseCode(200).setBody("{\"jsonrpc\":\"2.0\",\"id\":64,\"result\":{\"delegateAddress\":\""+serviceMock.delegateAddress+"\",\"market\":\"LRC-WETH\",\"depth\":{\"buy\":[[\"1.0000000000\",\"4.0000000000\",\"4.0000000000\"],[\"0.0014416000\",\"150.0000000000\",\"0.2162400000\"],[\"0.0014355300\",\"500.0000000000\",\"0.7177650000\"],[\"0.0014220800\",\"450.0000000000\",\"0.6399360000\"],[\"0.0011000000\",\"10000.0000000000\",\"11.0000000000\"],[\"0.0001000000\",\"2468.0000000000\",\"0.2468000000\"]],\"sell\":[[\"1.0100000000\",\"500.0000000000\",\"505.0000000000\"],[\"1.0000000000\",\"18.0000000000\",\"18.0000000000\"],[\"0.8888888800\",\"1000.0000000000\",\"888.8888800000\"],[\"0.0019000000\",\"200.0000000000\",\"0.3800000000\"],[\"0.0017000000\",\"200.0000000000\",\"0.3400000000\"],[\"0.0015000000\",\"90.0000000000\",\"0.1350000000\"],[\"0.0014700000\",\"450.0000000000\",\"0.6615000000\"],[\"0.0014416000\",\"150.0000000000\",\"0.2162400000\"],[\"0.0014355300\",\"213.0000000000\",\"0.3057678900\"],[\"0.0014000000\",\"30.0000000000\",\"0.0420000000\"]]}}}")
                method == "loopring_getTicker" ->
                    return MockResponse().setResponseCode(200).setBody("{\"jsonrpc\":\"2.0\",\"id\":64,\"result\":[{\"market\":\"ARP-BAR\",\"exchange\":\"\",\"interval\":\"\",\"amount\":0,\"vol\":0,\"open\":0,\"close\":0,\"high\":0,\"low\":0,\"last\":0,\"buy\":0,\"sell\":0,\"change\":\"\"},{\"market\":\"ARP-LRC\",\"exchange\":\"\",\"interval\":\"\",\"amount\":1541180,\"vol\":105096.76776,\"open\":0.13942902,\"close\":0.11942902,\"high\":0.13942902,\"low\":0.00070903,\"last\":0.11942902,\"buy\":0.11942902,\"sell\":0.11942902,\"change\":\"-14.34%\"},{\"market\":\"ARP-WETH\",\"exchange\":\"\",\"interval\":\"\",\"amount\":7590168.968,\"vol\":948.8474535,\"open\":0.00012502,\"close\":0.000125,\"high\":0.00012525,\"low\":0.000125,\"last\":0.000125,\"buy\":0.000125,\"sell\":0.000125,\"change\":\"-0.02%\"},{\"market\":\"BAR-LRC\",\"exchange\":\"\",\"interval\":\"\",\"amount\":0,\"vol\":0,\"open\":0,\"close\":0,\"high\":0,\"low\":0,\"last\":0,\"buy\":0,\"sell\":0,\"change\":\"\"},{\"market\":\"BAR-WETH\",\"exchange\":\"\",\"interval\":\"\",\"amount\":0,\"vol\":0,\"open\":0,\"close\":0,\"high\":0,\"low\":0,\"last\":0,\"buy\":0,\"sell\":0,\"change\":\"\"},{\"market\":\"BAT-BAR\",\"exchange\":\"\",\"interval\":\"\",\"amount\":0,\"vol\":0,\"open\":0,\"close\":0,\"high\":0,\"low\":0,\"last\":0,\"buy\":0,\"sell\":0,\"change\":\"\"},{\"market\":\"BAT-LRC\",\"exchange\":\"\",\"interval\":\"\",\"amount\":0,\"vol\":0,\"open\":0,\"close\":0,\"high\":0,\"low\":0,\"last\":0,\"buy\":0,\"sell\":0,\"change\":\"\"},{\"market\":\"BAT-WETH\",\"exchange\":\"\",\"interval\":\"\",\"amount\":0,\"vol\":0,\"open\":0,\"close\":0,\"high\":0,\"low\":0,\"last\":0,\"buy\":0,\"sell\":0,\"change\":\"\"},{\"market\":\"BNT-BAR\",\"exchange\":\"\",\"interval\":\"\",\"amount\":0,\"vol\":0,\"open\":0,\"close\":0,\"high\":0,\"low\":0,\"last\":0,\"buy\":0,\"sell\":0,\"change\":\"\"},{\"market\":\"BNT-LRC\",\"exchange\":\"\",\"interval\":\"\",\"amount\":0,\"vol\":0,\"open\":0,\"close\":0,\"high\":0,\"low\":0,\"last\":0,\"buy\":0,\"sell\":0,\"change\":\"\"},{\"market\":\"BNT-WETH\",\"exchange\":\"\",\"interval\":\"\",\"amount\":0,\"vol\":0,\"open\":0,\"close\":0,\"high\":0,\"low\":0,\"last\":0,\"buy\":0,\"sell\":0,\"change\":\"\"},{\"market\":\"EOS-BAR\",\"exchange\":\"\",\"interval\":\"\",\"amount\":0,\"vol\":0,\"open\":0,\"close\":0,\"high\":0,\"low\":0,\"last\":0,\"buy\":0,\"sell\":0,\"change\":\"\"},{\"market\":\"EOS-LRC\",\"exchange\":\"\",\"interval\":\"\",\"amount\":0,\"vol\":0,\"open\":0,\"close\":0,\"high\":0,\"low\":0,\"last\":0,\"buy\":0,\"sell\":0,\"change\":\"\"},{\"market\":\"EOS-WETH\",\"exchange\":\"\",\"interval\":\"\",\"amount\":0,\"vol\":0,\"open\":0,\"close\":0,\"high\":0,\"low\":0,\"last\":0,\"buy\":0,\"sell\":0,\"change\":\"\"},{\"market\":\"FOO-BAR\",\"exchange\":\"\",\"interval\":\"\",\"amount\":0,\"vol\":0,\"open\":0.01732051,\"close\":0.01732051,\"high\":0.01732051,\"low\":0.01732051,\"last\":0.01732051,\"buy\":0.01732051,\"sell\":0.01732051,\"change\":\"0.00%\"},{\"market\":\"FOO-LRC\",\"exchange\":\"\",\"interval\":\"\",\"amount\":0,\"vol\":0,\"open\":0,\"close\":0,\"high\":0,\"low\":0,\"last\":0,\"buy\":0,\"sell\":0,\"change\":\"\"},{\"market\":\"FOO-WETH\",\"exchange\":\"\",\"interval\":\"\",\"amount\":0,\"vol\":0,\"open\":0,\"close\":0,\"high\":0,\"low\":0,\"last\":0,\"buy\":0,\"sell\":0,\"change\":\"\"},{\"market\":\"IOST-BAR\",\"exchange\":\"\",\"interval\":\"\",\"amount\":0,\"vol\":0,\"open\":0,\"close\":0,\"high\":0,\"low\":0,\"last\":0,\"buy\":0,\"sell\":0,\"change\":\"\"},{\"market\":\"IOST-LRC\",\"exchange\":\"\",\"interval\":\"\",\"amount\":0,\"vol\":0,\"open\":0,\"close\":0,\"high\":0,\"low\":0,\"last\":0,\"buy\":0,\"sell\":0,\"change\":\"\"},{\"market\":\"IOST-WETH\",\"exchange\":\"\",\"interval\":\"\",\"amount\":0,\"vol\":0,\"open\":0,\"close\":0,\"high\":0,\"low\":0,\"last\":0,\"buy\":0,\"sell\":0,\"change\":\"\"},{\"market\":\"KNC-BAR\",\"exchange\":\"\",\"interval\":\"\",\"amount\":0,\"vol\":0,\"open\":0,\"close\":0,\"high\":0,\"low\":0,\"last\":0,\"buy\":0,\"sell\":0,\"change\":\"\"},{\"market\":\"KNC-LRC\",\"exchange\":\"\",\"interval\":\"\",\"amount\":0,\"vol\":0,\"open\":0,\"close\":0,\"high\":0,\"low\":0,\"last\":0,\"buy\":0,\"sell\":0,\"change\":\"\"},{\"market\":\"KNC-WETH\",\"exchange\":\"\",\"interval\":\"\",\"amount\":0,\"vol\":0,\"open\":0,\"close\":0,\"high\":0,\"low\":0,\"last\":0,\"buy\":0,\"sell\":0,\"change\":\"\"},{\"market\":\"LRC-WETH\",\"exchange\":\"\",\"interval\":\"\",\"amount\":0,\"vol\":0,\"open\":0.00143553,\"close\":0.00143553,\"high\":0.00143553,\"low\":0.00143553,\"last\":0.00143553,\"buy\":0.00143553,\"sell\":0.00143553,\"change\":\"0.00%\"},{\"market\":\"OMG-BAR\",\"exchange\":\"\",\"interval\":\"\",\"amount\":0,\"vol\":0,\"open\":0,\"close\":0,\"high\":0,\"low\":0,\"last\":0,\"buy\":0,\"sell\":0,\"change\":\"\"},{\"market\":\"OMG-LRC\",\"exchange\":\"\",\"interval\":\"\",\"amount\":0,\"vol\":0,\"open\":0,\"close\":0,\"high\":0,\"low\":0,\"last\":0,\"buy\":0,\"sell\":0,\"change\":\"\"},{\"market\":\"OMG-WETH\",\"exchange\":\"\",\"interval\":\"\",\"amount\":0,\"vol\":0,\"open\":0,\"close\":0,\"high\":0,\"low\":0,\"last\":0,\"buy\":0,\"sell\":0,\"change\":\"\"},{\"market\":\"RDN-BAR\",\"exchange\":\"\",\"interval\":\"\",\"amount\":0,\"vol\":0,\"open\":0,\"close\":0,\"high\":0,\"low\":0,\"last\":0,\"buy\":0,\"sell\":0,\"change\":\"\"},{\"market\":\"RDN-LRC\",\"exchange\":\"\",\"interval\":\"\",\"amount\":0,\"vol\":0,\"open\":0,\"close\":0,\"high\":0,\"low\":0,\"last\":0,\"buy\":0,\"sell\":0,\"change\":\"\"},{\"market\":\"RDN-WETH\",\"exchange\":\"\",\"interval\":\"\",\"amount\":0,\"vol\":0,\"open\":0,\"close\":0,\"high\":0,\"low\":0,\"last\":0,\"buy\":0,\"sell\":0,\"change\":\"\"},{\"market\":\"REQ-BAR\",\"exchange\":\"\",\"interval\":\"\",\"amount\":0,\"vol\":0,\"open\":0,\"close\":0,\"high\":0,\"low\":0,\"last\":0,\"buy\":0,\"sell\":0,\"change\":\"\"},{\"market\":\"REQ-LRC\",\"exchange\":\"\",\"interval\":\"\",\"amount\":0,\"vol\":0,\"open\":0,\"close\":0,\"high\":0,\"low\":0,\"last\":0,\"buy\":0,\"sell\":0,\"change\":\"\"},{\"market\":\"REQ-WETH\",\"exchange\":\"\",\"interval\":\"\",\"amount\":0,\"vol\":0,\"open\":0,\"close\":0,\"high\":0,\"low\":0,\"last\":0,\"buy\":0,\"sell\":0,\"change\":\"\"},{\"market\":\"RHOC-BAR\",\"exchange\":\"\",\"interval\":\"\",\"amount\":0,\"vol\":0,\"open\":0,\"close\":0,\"high\":0,\"low\":0,\"last\":0,\"buy\":0,\"sell\":0,\"change\":\"\"},{\"market\":\"RHOC-LRC\",\"exchange\":\"\",\"interval\":\"\",\"amount\":0,\"vol\":0,\"open\":0,\"close\":0,\"high\":0,\"low\":0,\"last\":0,\"buy\":0,\"sell\":0,\"change\":\"\"},{\"market\":\"RHOC-WETH\",\"exchange\":\"\",\"interval\":\"\",\"amount\":0,\"vol\":0,\"open\":0,\"close\":0,\"high\":0,\"low\":0,\"last\":0,\"buy\":0,\"sell\":0,\"change\":\"\"},{\"market\":\"SNT-BAR\",\"exchange\":\"\",\"interval\":\"\",\"amount\":0,\"vol\":0,\"open\":0,\"close\":0,\"high\":0,\"low\":0,\"last\":0,\"buy\":0,\"sell\":0,\"change\":\"\"},{\"market\":\"SNT-LRC\",\"exchange\":\"\",\"interval\":\"\",\"amount\":0,\"vol\":0,\"open\":0,\"close\":0,\"high\":0,\"low\":0,\"last\":0,\"buy\":0,\"sell\":0,\"change\":\"\"},{\"market\":\"SNT-WETH\",\"exchange\":\"\",\"interval\":\"\",\"amount\":0,\"vol\":0,\"open\":0,\"close\":0,\"high\":0,\"low\":0,\"last\":0,\"buy\":0,\"sell\":0,\"change\":\"\"},{\"market\":\"VITE-BAR\",\"exchange\":\"\",\"interval\":\"\",\"amount\":0,\"vol\":0,\"open\":0,\"close\":0,\"high\":0,\"low\":0,\"last\":0,\"buy\":0,\"sell\":0,\"change\":\"\"},{\"market\":\"VITE-LRC\",\"exchange\":\"\",\"interval\":\"\",\"amount\":367686,\"vol\":47947.686,\"open\":0.17964002,\"close\":0.1282,\"high\":0.17964002,\"low\":0.12794365,\"last\":0.1282,\"buy\":0.1282,\"sell\":0.1282,\"change\":\"-28.64%\"},{\"market\":\"VITE-WETH\",\"exchange\":\"\",\"interval\":\"\",\"amount\":0,\"vol\":0,\"open\":0.0003,\"close\":0.0003,\"high\":0.0003,\"low\":0.0003,\"last\":0.0003,\"buy\":0.0003,\"sell\":0.0003,\"change\":\"0.00%\"},{\"market\":\"ZRX-BAR\",\"exchange\":\"\",\"interval\":\"\",\"amount\":0,\"vol\":0,\"open\":0,\"close\":0,\"high\":0,\"low\":0,\"last\":0,\"buy\":0,\"sell\":0,\"change\":\"\"},{\"market\":\"ZRX-LRC\",\"exchange\":\"\",\"interval\":\"\",\"amount\":0,\"vol\":0,\"open\":0,\"close\":0,\"high\":0,\"low\":0,\"last\":0,\"buy\":0,\"sell\":0,\"change\":\"\"},{\"market\":\"ZRX-WETH\",\"exchange\":\"\",\"interval\":\"\",\"amount\":0,\"vol\":0,\"open\":0,\"close\":0,\"high\":0,\"low\":0,\"last\":0,\"buy\":0,\"sell\":0,\"change\":\"\"}]}")
                method == "loopring_getTickers" ->
                    return MockResponse().setResponseCode(200).setBody("{\"jsonrpc\":\"2.0\",\"id\":64,\"result\":{\"binance\":{\"market\":\"LRC-WETH\",\"exchange\":\"binance\",\"interval\":\"\",\"amount\":3846710,\"vol\":5548.0646791,\"open\":0.00129907,\"close\":0.0013,\"high\":0.001599,\"low\":0.00127,\"last\":0.0015129,\"buy\":0,\"sell\":0,\"change\":\"+16.46%\"},\"loopr\":{\"market\":\"LRC-WETH\",\"exchange\":\"\",\"interval\":\"\",\"amount\":0,\"vol\":0,\"open\":0.0014355328,\"close\":0.0014355328,\"high\":0.0014355328,\"low\":0.0014355328,\"last\":0.0014355328,\"buy\":0.00143553,\"sell\":0.00143553,\"change\":\"0.00%\"},\"okex\":{\"market\":\"LRC-WETH\",\"exchange\":\"okex\",\"interval\":\"\",\"amount\":1228740.74355074,\"vol\":1887.210620612146,\"open\":0,\"close\":0,\"high\":0.0016,\"low\":0.00127,\"last\":0.00153589,\"buy\":0,\"sell\":0,\"change\":\"+17.62%\"}}}")
                method == "loopring_getTrend" ->
                    return MockResponse().setResponseCode(200).setBody("{\"jsonrpc\":\"2.0\",\"id\":64,\"result\":[{\"intervals\":\"1Day\",\"market\":\"LRC-WETH\",\"vol\":1.0694368,\"amount\":899.28375,\"createTime\":1524614471,\"open\":0.0010667891,\"close\":0.0012489996,\"high\":0.0012505002,\"low\":0.0010667891,\"start\":1524528001,\"end\":1524614400}]}")
                method == "loopring_getRingMined" ->
                    return MockResponse().setResponseCode(200).setBody("{\"jsonrpc\":\"2.0\",\"id\":64,\"result\":{\"data\":[{\"id\":1,\"protocol\":\"0xb1170dE31c7f72aB62535862C97F5209E356991b\",\"delegateAddress\":\""+serviceMock.delegateAddress+"\",\"ringIndex\":\"0\",\"ringHash\":\"0x3d58a550136668074648778c3ac6757df582c0df7c7af12c76f39d4a43be5054\",\"txHash\":\"0x30940beef7acb033d10882861a469c19bb6c1aab9a15e6897d6bb0020d70ffa7\",\"miner\":\"0x3ACDF3e3D8eC52a768083f718e763727b0210650\",\"feeRecipient\":\"0x3ACDF3e3D8eC52a768083f718e763727b0210650\",\"isRinghashReserved\":false,\"blockNumber\":5469397,\"totalLrcFee\":\"323200000000000000\",\"tradeAmount\":2,\"timestamp\":1524156383,\"Fork\":false}],\"pageIndex\":1,\"pageSize\":20,\"total\":1}}")
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

    /**
     * Testing best case, all proper data is returned
     * Mock server
     * TODO - find a set of orders for this to be tested on
     */
    @Test
    fun getOrders_shouldWorkMock() = runBlocking {
        val deferred = serviceMock.getOrders("0x847983c3a34afa192cfee860698584c030f4c9db1",
                "0xf0b75ed18109403b88713cd7a1a8423352b9ed9260e39cb1ea0f423e2b6664f0",
                "ORDER_CANCEL",
                "cross-weth",
                "buy",
                2,
                40
                )

        val result = deferred.await()
        Assert.assertEquals(serviceMock.id, result.id)
        Assert.assertEquals(serviceMock.jsonRpcVersion, result.jsonrpc)

    }

    /**
     * Testing best case, all proper data is returned
     * Live server
     * TODO - find a set of orders for this to be tested on
     */
    @Test
    fun getOrders_shouldWorkLive() = runBlocking {
        val deferred = serviceLive.getOrders("0x847983c3a34afa192cfee860698584c030f4c9db1",
                "0xf0b75ed18109403b88713cd7a1a8423352b9ed9260e39cb1ea0f423e2b6664f0",
                "ORDER_CANCEL",
                "cross-weth",
                "buy",
                2,
                40
        )

        val result = deferred.await()
        Assert.assertEquals(serviceLive.id, result.id)
        Assert.assertEquals(serviceLive.jsonRpcVersion, result.jsonrpc)
    }

    /**
     * Testing best case, all proper data is returned
     * Mock server
     */
    @Test
    fun getDepth_shouldWorkMock() = runBlocking {
        val deferred = serviceMock.getDepth( "LRC-WETH",10)

        val result = deferred.await()
        Assert.assertEquals(serviceMock.id, result.id)
        Assert.assertEquals(serviceMock.jsonRpcVersion, result.jsonrpc)
        Assert.assertEquals("LRC-WETH", result.market)
        Assert.assertEquals("1.0000000000",result.buyDepth?.get(0)?.depthPrice.toString())
    }

    /**
     * Testing best case, all proper data is returned
     * Live server
     */
    @Test
    fun getDepth_shouldWorkLive() = runBlocking {
        val deferred = serviceLive.getDepth( "LRC-WETH",10)

        val result = deferred.await()
        Assert.assertEquals(serviceLive.id, result.id)
        Assert.assertEquals(serviceLive.jsonRpcVersion, result.jsonrpc)
        Assert.assertEquals("LRC-WETH", result.market)
        Assert.assertEquals("1.0000000000",result.buyDepth?.get(0)?.depthPrice.toString()) //TODO - this will have to adjust to account for changing live data
    }

    /**
     * Testing best case, all proper data is returned
     * Mock server
     */
    @Test
    fun getTicker_shouldWorkMock() = runBlocking {
        val deferred = serviceMock.getTicker()

        val result = deferred.await()
        Assert.assertEquals(serviceMock.id, result.id)
        Assert.assertEquals(serviceMock.jsonRpcVersion, result.jsonrpc)
        //TODO - find a way to either find a certain trading pair or just check that nums are >= 0
    }

    /**
     * Testing best case, all proper data is returned
     * Live server
     */
    @Test
    fun getTicker_shouldWorkLive() = runBlocking {
        val deferred = serviceLive.getTicker()

        val result = deferred.await()
        Assert.assertEquals(serviceLive.id, result.id)
        Assert.assertEquals(serviceLive.jsonRpcVersion, result.jsonrpc)
        //TODO - find a way to either find a certain trading pair or just check that nums are >= 0
    }

    /**
     * Testing best case, all proper data is returned
     * Mock server
     */
    @Test
    fun getTickers_shouldWorkMock() = runBlocking {
        val deferred = serviceMock.getTickers( "LRC-WETH")

        val result = deferred.await()
        Assert.assertEquals(serviceMock.id, result.id)
        Assert.assertEquals(serviceMock.jsonRpcVersion, result.jsonrpc)
        Assert.assertTrue(result.tickers!!.size > 0) //TODO - can this be done without the !!
    }

    /**
     * Testing best case, all proper data is returned
     * Live server
     */
    @Test
    fun getTickers_shouldWorkLive() = runBlocking {
        val deferred = serviceLive.getTickers( "LRC-WETH")

        val result = deferred.await()
        Assert.assertEquals(serviceLive.id, result.id)
        Assert.assertEquals(serviceLive.jsonRpcVersion, result.jsonrpc)
        Assert.assertTrue(result.tickers!!.size > 0) //TODO - can this be done without the !!
    }

    //TODO - getFills needs an example to work from, need to make some fills

    /**
     * Testing best case, all proper data is returned
     * Mock server
     */
    @Test
    fun getTrend_shouldWorkMock() = runBlocking {
        val deferred = serviceMock.getTrend( "LRC-WETH", "1Day")

        val result = deferred.await()
        Assert.assertEquals(serviceMock.id, result.id)
        Assert.assertEquals(serviceMock.jsonRpcVersion, result.jsonrpc)
        Assert.assertEquals("1Day",result.trends?.get(0)?.market, "LRC-WETH")
    }

    /**
     * Testing best case, all proper data is returned
     * Live server
     */
    @Test
    fun getTrend_shouldWorkLive() = runBlocking {
        val deferred = serviceLive.getTrend( "LRC-WETH", "1Day")

        val result = deferred.await()
        Assert.assertEquals(serviceLive.id, result.id)
        Assert.assertEquals(serviceLive.jsonRpcVersion, result.jsonrpc)
        Assert.assertEquals("1Day",result.trends?.get(0)?.market, "LRC-WETH")
    }

    /**
     * Testing best case, all proper data is returned
     * Mock server
     */
    @Test
    fun getRingMined_shouldWorkMock() = runBlocking {
        val deferred = serviceMock.getRingMined( "0xb903239f8543d04b5dc1ba6579132b143087c68db1b2168786408fcbce568238",1,20)

        val result = deferred.await()
        Assert.assertEquals(serviceMock.id, result.id)
        Assert.assertEquals(serviceMock.jsonRpcVersion, result.jsonrpc)
        Assert.assertEquals(1,result.pageIndex)
        Assert.assertEquals(20,result.pageSize)
        Assert.assertEquals(1,result.total)
        Assert.assertEquals("0x3d58a550136668074648778c3ac6757df582c0df7c7af12c76f39d4a43be5054",result.minedRings?.get(0)?.ringHash)
        Assert.assertEquals(BigInteger("5469397"),result.minedRings?.get(0)?.blockNumber)
        Assert.assertEquals("0x3ACDF3e3D8eC52a768083f718e763727b0210650",result.minedRings?.get(0)?.feeRecipient)
        Assert.assertEquals("0x3ACDF3e3D8eC52a768083f718e763727b0210650",result.minedRings?.get(0)?.miner)
        Assert.assertEquals(Date(1524156383),result.minedRings?.get(0)?.timestamp)
        Assert.assertEquals(BigInteger("323200000000000000"),result.minedRings?.get(0)?.totalLrcFee)
        Assert.assertEquals(2,result.minedRings?.get(0)?.tradeAmount)
        Assert.assertEquals("0x30940beef7acb033d10882861a469c19bb6c1aab9a15e6897d6bb0020d70ffa7",result.minedRings?.get(0)?.txHash)
        Assert.assertEquals(1,result.minedRings?.get(0)?.id)
        Assert.assertEquals(false,result.minedRings?.get(0)?.isRinghashReserved)
        Assert.assertEquals(false,result.minedRings?.get(0)?.fork)
    }

    /**
     * Testing best case, all proper data is returned
     * Live server
     */
    @Test
    fun getRingMined_shouldWorkLive() = runBlocking {
        val deferred = serviceLive.getRingMined( "0xb903239f8543d04b5dc1ba6579132b143087c68db1b2168786408fcbce568238",1,20)

        val result = deferred.await()
        Assert.assertEquals(serviceLive.id, result.id)
        Assert.assertEquals(serviceLive.jsonRpcVersion, result.jsonrpc)
        Assert.assertEquals(1,result.pageIndex)
        Assert.assertEquals(20,result.pageSize)
        Assert.assertEquals(0,result.total)
        Assert.assertEquals("0x3d58a550136668074648778c3ac6757df582c0df7c7af12c76f39d4a43be5054",result.minedRings?.get(0)?.ringHash)
        Assert.assertEquals(BigInteger("5469397"),result.minedRings?.get(0)?.blockNumber)
        Assert.assertEquals("0x3ACDF3e3D8eC52a768083f718e763727b0210650",result.minedRings?.get(0)?.feeRecipient)
        Assert.assertEquals("0x3ACDF3e3D8eC52a768083f718e763727b0210650",result.minedRings?.get(0)?.miner)
        Assert.assertEquals(Date(1524156383),result.minedRings?.get(0)?.timestamp)
        Assert.assertEquals(BigInteger("323200000000000000"),result.minedRings?.get(0)?.totalLrcFee)
        Assert.assertEquals(2,result.minedRings?.get(0)?.tradeAmount)
        Assert.assertEquals("0x30940beef7acb033d10882861a469c19bb6c1aab9a15e6897d6bb0020d70ffa7",result.minedRings?.get(0)?.txHash)
        Assert.assertEquals(1,result.minedRings?.get(0)?.id)
        Assert.assertEquals(false,result.minedRings?.get(0)?.isRinghashReserved)
        Assert.assertEquals(false,result.minedRings?.get(0)?.fork)
    }


}