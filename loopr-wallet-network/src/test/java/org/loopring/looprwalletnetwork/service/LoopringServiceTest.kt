package org.loopring.looprwalletnetwork.service

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
import org.loopring.looprwalletnetwork.models.loopring.responseObjects.LooprCutoff
import org.loopring.looprwalletnetwork.models.loopring.responseObjects.LooprOrderList
import java.lang.Long.parseLong
import java.math.BigDecimal
import java.util.*


@RunWith(MockitoJUnitRunner::class)
class LoopringServiceTest {

    private lateinit var serviceMock: LoopringService
    private lateinit var serviceLive: LoopringService

    private val dispatcher: Dispatcher = object : Dispatcher() {

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
                    return MockResponse().setResponseCode(200).setBody("{\"jsonrpc\":\"2.0\",\"id\":12312,\"result\":{\"data\":[{\"originalOrder\":{\"protocol\":\"0x8d8812b72d1e4ffCeC158D25f56748b7d67c1e78\",\"delegateAddress\":\"0x17233e07c67d086464fD408148c3ABB56245FA64\",\"address\":\"0x328Bf4C7772C7efEA43c3e0C741085481A0AC311\",\"hash\":\"0xe85590c6ac6096de02a4b1b1cf57fe2980c483d9a9a8eebdddbdbccc21445cd6\",\"tokenS\":\"LRC\",\"tokenB\":\"VITE\",\"amountS\":\"0x3e1b0038be886c60000\",\"amountB\":\"0xe605303a75d35880000\",\"validSince\":\"0x5af8ed0c\",\"validUntil\":\"0x5afa3e8c\",\"lrcFee\":\"0x1fcc27bc459d20000\",\"buyNoMoreThanAmountB\":true,\"marginSplitPercentage\":\"0x32\",\"v\":\"0x1c\",\"r\":\"0x415b91b4277947ac92a422d73528622f71075940b622487ac4ee0fabe2fbd326\",\"s\":\"0x618e2ce5a0cf7f9fa5ee3c486369125eb812ce135ed69e024c8ea7bcd8c3e242\",\"walletAddress\":\"0xb94065482Ad64d4c2b9252358D746B39e820A582\",\"authAddr\":\"0x2ab06e4d7606cF427b44C2aE01B1FA8b30c30824\",\"authPrivateKey\":\"0x82d50484609ee832fcff30abeb20a00935ba12746c0e18e391cb4a677bf72dd1\",\"market\":\"VITE-LRC\",\"side\":\"buy\",\"createTime\":1526263146,\"orderType\":\"market_order\"},\"dealtAmountS\":\"0x3e1affd8444b2a4fbbc\",\"dealtAmountB\":\"0xe605303a75d35880000\",\"cancelledAmountS\":\"0x0\",\"cancelledAmountB\":\"0x0\",\"status\":\"ORDER_FINISHED\"}],\"pageIndex\":1,\"pageSize\":1,\"total\":663}}")
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
                method == "loopring_getCutoff" ->
                    return MockResponse().setResponseCode(200).setBody("{\"jsonrpc\":\"2.0\",\"id\":64,\"result\":1501232222}")
                method == "loopring_getPriceQuote" ->
                    return MockResponse().setResponseCode(200).setBody("{\"jsonrpc\":\"2.0\",\"id\":64,\"result\":{\"currency\":\"CNY\",\"tokens\":[{\"symbol\":\"LRC\",\"price\":5.2055563217},{\"symbol\":\"IOST\",\"price\":0.3566289306},{\"symbol\":\"BNT\",\"price\":32.026390396},{\"symbol\":\"FOO\",\"price\":0.356182153},{\"symbol\":\"VITE\",\"price\":0.5336757253333333},{\"symbol\":\"SNT\",\"price\":0.9850778553},{\"symbol\":\"DAI\",\"price\":6.373984582},{\"symbol\":\"RDN\",\"price\":12.832176889},{\"symbol\":\"BAR\",\"price\":2.2808154852},{\"symbol\":\"ARP\",\"price\":0.600385191},{\"symbol\":\"KNC\",\"price\":13.718423474},{\"symbol\":\"REQ\",\"price\":1.6547167504},{\"symbol\":\"OMG\",\"price\":102.28092714},{\"symbol\":\"BAT\",\"price\":2.6060733839},{\"symbol\":\"EOS\",\"price\":109.74713358},{\"symbol\":\"WETH\",\"price\":4803.081528},{\"symbol\":\"ETH\",\"price\":4803.081528},{\"symbol\":\"ZRX\",\"price\":9.711915248},{\"symbol\":\"RHOC\",\"price\":11.740336349}]}}")
                method == "loopring_getEstimatedAllocatedAllowance" ->
                    return MockResponse().setResponseCode(200).setBody("{\"jsonrpc\":\"2.0\",\"id\":64,\"result\":\"0x2347ad6c\"}")
                method == "loopring_getFrozenLRCFee" ->
                    return MockResponse().setResponseCode(200).setBody("{\"jsonrpc\":\"2.0\",\"id\":64,\"result\":\"0x2347ad6c\"}")
                method == "loopring_getSupportedMarket" ->
                    return MockResponse().setResponseCode(200).setBody("{\"jsonrpc\":\"2.0\",\"id\":64,\"result\":[\"ZRX-LRC\",\"ZRX-WETH\",\"ZRX-BAR\",\"RHOC-BAR\",\"RHOC-LRC\",\"RHOC-WETH\",\"VITE-LRC\",\"VITE-WETH\",\"VITE-BAR\",\"LRC-WETH\",\"IOST-LRC\",\"IOST-WETH\",\"IOST-BAR\",\"BNT-LRC\",\"BNT-WETH\",\"BNT-BAR\",\"FOO-LRC\",\"FOO-WETH\",\"FOO-BAR\",\"OMG-BAR\",\"OMG-LRC\",\"OMG-WETH\",\"SNT-BAR\",\"SNT-LRC\",\"SNT-WETH\",\"RDN-LRC\",\"RDN-WETH\",\"RDN-BAR\",\"BAR-LRC\",\"BAR-WETH\",\"ARP-LRC\",\"ARP-WETH\",\"ARP-BAR\",\"KNC-LRC\",\"KNC-WETH\",\"KNC-BAR\",\"REQ-LRC\",\"REQ-WETH\",\"REQ-BAR\",\"BAT-LRC\",\"BAT-WETH\",\"BAT-BAR\",\"EOS-LRC\",\"EOS-WETH\",\"EOS-BAR\"]}")
                method == "loopring_getSupportedTokens" ->
                    return MockResponse().setResponseCode(200).setBody("{\"jsonrpc\":\"2.0\",\"id\":64,\"result\":[{\"protocol\":\"0xfa1a856cfa3409cfa145fa4e20eb270df3eb21ab\",\"symbol\":\"IOST\",\"source\":\"iostoken\",\"time\":0,\"deny\":false,\"decimals\":1000000000000000000,\"isMarket\":false,\"icoPrice\":null},{\"protocol\":\"0x744d70fdbe2ba4cf95131626614a1763df805b9e\",\"symbol\":\"SNT\",\"source\":\"status\",\"time\":0,\"deny\":false,\"decimals\":1000000000000000000,\"isMarket\":false,\"icoPrice\":null},{\"protocol\":\"0x86fa049857e0209aa7d9e616f7eb3b3b78ecfdb0\",\"symbol\":\"EOS\",\"source\":\"eos\",\"time\":0,\"deny\":false,\"decimals\":1000000000000000000,\"isMarket\":false,\"icoPrice\":null},{\"protocol\":\"0xef68e7c694f40c8202821edf525de3782458639f\",\"symbol\":\"LRC\",\"source\":\"loopring\",\"time\":0,\"deny\":false,\"decimals\":1000000000000000000,\"isMarket\":true,\"icoPrice\":null},{\"protocol\":\"0xc02aaa39b223fe8d0a0e5c4f27ead9083c756cc2\",\"symbol\":\"WETH\",\"source\":\"ethereum\",\"time\":0,\"deny\":false,\"decimals\":1000000000000000000,\"isMarket\":true,\"icoPrice\":null},{\"protocol\":\"0x89d24a6b4ccb1b6faa2625fe562bdd9a23260359\",\"symbol\":\"DAI\",\"source\":\"dai\",\"time\":0,\"deny\":false,\"decimals\":1000000000000000000,\"isMarket\":false,\"icoPrice\":null},{\"protocol\":\"0xd26114cd6ee289accf82350c8d8487fedb8a0c07\",\"symbol\":\"OMG\",\"source\":\"omisego\",\"time\":0,\"deny\":false,\"decimals\":1000000000000000000,\"isMarket\":false,\"icoPrice\":null},{\"protocol\":\"0xb5f64747127be058ee7239b363269fc8cf3f4a87\",\"symbol\":\"BAR\",\"source\":\"wax\",\"time\":0,\"deny\":false,\"decimals\":1000000000000000000,\"isMarket\":true,\"icoPrice\":null},{\"protocol\":\"0x8f8221afbb33998d8584a2b05749ba73c37a938a\",\"symbol\":\"REQ\",\"source\":\"request-network\",\"time\":0,\"deny\":false,\"decimals\":1000000000000000000,\"isMarket\":false,\"icoPrice\":null},{\"protocol\":\"0x1f573d6fb3f13d689ff844b4ce37794d79a7ff1c\",\"symbol\":\"BNT\",\"source\":\"bancor\",\"time\":0,\"deny\":false,\"decimals\":1000000000000000000,\"isMarket\":false,\"icoPrice\":null},{\"protocol\":\"0xf5b3b365fa319342e89a3da71ba393e12d9f63c3\",\"symbol\":\"FOO\",\"source\":\"storm\",\"time\":0,\"deny\":false,\"decimals\":100000000,\"isMarket\":false,\"icoPrice\":null},{\"protocol\":\"0x168296bb09e24a88805cb9c33356536b980d3fc5\",\"symbol\":\"RHOC\",\"source\":\"rchain\",\"time\":0,\"deny\":false,\"decimals\":100000000,\"isMarket\":false,\"icoPrice\":null},{\"protocol\":\"0x1b793e49237758dbd8b752afc9eb4b329d5da016\",\"symbol\":\"VITE\",\"source\":\"\",\"time\":0,\"deny\":false,\"decimals\":1000000000000000000,\"isMarket\":false,\"icoPrice\":\"1/9000\"},{\"protocol\":\"0xdd974d5c2e2928dea5f71b9825b8b646686bd200\",\"symbol\":\"KNC\",\"source\":\"kyber-network\",\"time\":0,\"deny\":false,\"decimals\":1000000000000000000,\"isMarket\":false,\"icoPrice\":null},{\"protocol\":\"0xe41d2489571d322189246dafa5ebde1f4699f498\",\"symbol\":\"ZRX\",\"source\":\"0x\",\"time\":0,\"deny\":false,\"decimals\":1000000000000000000,\"isMarket\":false,\"icoPrice\":null},{\"protocol\":\"0x255aa6df07540cb5d3d297f0d0d4d84cb52bc8e6\",\"symbol\":\"RDN\",\"source\":\"raiden-network-token\",\"time\":0,\"deny\":false,\"decimals\":1000000000000000000,\"isMarket\":false,\"icoPrice\":null},{\"protocol\":\"0xbeb6fdf4ef6ceb975157be43cbe0047b248a8922\",\"symbol\":\"ARP\",\"source\":\"\",\"time\":0,\"deny\":false,\"decimals\":1000000000000000000,\"isMarket\":false,\"icoPrice\":\"1/8000\"},{\"protocol\":\"0x0d8775f648430679a709e98d2b0cb6250d2887ef\",\"symbol\":\"BAT\",\"source\":\"basic-attention-token\",\"time\":0,\"deny\":false,\"decimals\":1000000000000000000,\"isMarket\":false,\"icoPrice\":null}]}")
                method == "loopring_getPortfolio" ->
                    return MockResponse().setResponseCode(200).setBody("{\"jsonrpc\":\"2.0\",\"id\":64,\"result\":[{\"token\":\"BAR\",\"amount\":\"9782200000000000000000\",\"percentage\":\"91.8582%\"},{\"token\":\"LRC\",\"amount\":\"116155166000000023386\",\"percentage\":\"2.7725%\"},{\"token\":\"EOS\",\"amount\":\"3000000000000000035\",\"percentage\":\"1.7029%\"},{\"token\":\"WETH\",\"amount\":\"63147725458498073\",\"percentage\":\"1.6022%\"},{\"token\":\"ZRX\",\"amount\":\"14999999996812749004\",\"percentage\":\"0.9368%\"},{\"token\":\"ETH\",\"amount\":\"33322393820000000\",\"percentage\":\"0.8455%\"},{\"token\":\"VITE\",\"amount\":\"100000000000000000000\",\"percentage\":\"0.2819%\"},{\"token\":\"BNT\",\"amount\":\"0\",\"percentage\":\"0.0000%\"},{\"token\":\"FOO\",\"amount\":\"109800000000000\",\"percentage\":\"0.0000%\"},{\"token\":\"ARP\",\"amount\":\"0\",\"percentage\":\"0.0000%\"},{\"token\":\"DAI\",\"amount\":\"0\",\"percentage\":\"0.0000%\"},{\"token\":\"IOST\",\"amount\":\"0\",\"percentage\":\"0.0000%\"},{\"token\":\"OMG\",\"amount\":\"0\",\"percentage\":\"0.0000%\"},{\"token\":\"BAT\",\"amount\":\"0\",\"percentage\":\"0.0000%\"},{\"token\":\"SNT\",\"amount\":\"0\",\"percentage\":\"0.0000%\"},{\"token\":\"REQ\",\"amount\":\"0\",\"percentage\":\"0.0000%\"},{\"token\":\"RHOC\",\"amount\":\"0\",\"percentage\":\"0.0000%\"},{\"token\":\"KNC\",\"amount\":\"0\",\"percentage\":\"0.0000%\"},{\"token\":\"RDN\",\"amount\":\"0\",\"percentage\":\"0.0000%\"}]}")
                method == "loopring_getTransactions" ->
                    return MockResponse().setResponseCode(200).setBody("{\"jsonrpc\":\"2.0\",\"id\":64,\"result\":{\"data\":[{\"protocol\":\"0xef68e7c694f40c8202821edf525de3782458639f\",\"owner\":\"0xeba7136a36da0f5e16c6bdbc739c716bb5b65a00\",\"from\":\"0x2234c96681e9533fdfd122bacbbc634efbafa0f0\",\"to\":\"0xeba7136a36da0f5e16c6bdbc739c716bb5b65a00\",\"txHash\":\"0x46b9ab33d6904718fc2d16ad1a133a35ae23045bb65893eb2c41b0984b78eca7\",\"symbol\":\"LRC\",\"content\":{\"market\":\"\",\"orderHash\":\"\",\"fill\":\"\"},\"blockNumber\":5550001,\"value\":\"79640000000000000000\",\"logIndex\":35,\"type\":\"receive\",\"status\":\"success\",\"createTime\":1525364262,\"updateTime\":1525364262,\"gas_price\":\"10000000000\",\"gas_limit\":\"500000\",\"gas_used\":\"362742\",\"nonce\":\"866\"},{\"protocol\":\"0xef68e7c694f40c8202821edf525de3782458639f\",\"owner\":\"0xeba7136a36da0f5e16c6bdbc739c716bb5b65a00\",\"from\":\"0xb94065482ad64d4c2b9252358d746b39e820a582\",\"to\":\"0xeba7136a36da0f5e16c6bdbc739c716bb5b65a00\",\"txHash\":\"0xb821eff03913deb0627cb16e0c7aa73e064d05df17c4c1c7574efdd903980f45\",\"symbol\":\"LRC\",\"content\":{\"market\":\"\",\"orderHash\":\"\",\"fill\":\"\"},\"blockNumber\":5541918,\"value\":\"89670000000000000000\",\"logIndex\":12,\"type\":\"receive\",\"status\":\"success\",\"createTime\":1525240133,\"updateTime\":1525240133,\"gas_price\":\"10000000000\",\"gas_limit\":\"1000000\",\"gas_used\":\"305601\",\"nonce\":\"481\"},{\"protocol\":\"0xef68e7c694f40c8202821edf525de3782458639f\",\"owner\":\"0xeba7136a36da0f5e16c6bdbc739c716bb5b65a00\",\"from\":\"0x3acdf3e3d8ec52a768083f718e763727b0210650\",\"to\":\"0xeba7136a36da0f5e16c6bdbc739c716bb5b65a00\",\"txHash\":\"0x26d62ad14d55a036282996e9d023aa98cdca8371a74f5b2ccac2ff15c759ca9f\",\"symbol\":\"LRC\",\"content\":{\"market\":\"\",\"orderHash\":\"\",\"fill\":\"\"},\"blockNumber\":5491578,\"value\":\"360000000000000000\",\"logIndex\":53,\"type\":\"receive\",\"status\":\"success\",\"createTime\":1524484434,\"updateTime\":1524484434,\"gas_price\":\"3350270758\",\"gas_limit\":\"1000000\",\"gas_used\":\"371290\",\"nonce\":\"278\"}],\"pageIndex\":1,\"pageSize\":10,\"total\":3}}")
                method == "loopring_unlockWallet" ->
                    return MockResponse().setResponseCode(200).setBody("{\"jsonrpc\":\"2.0\",\"id\":64,\"result\":\"unlock_notice_success\"}")
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
                LooprOrderList.ORDER_CANCEL,
                "cross-weth",
                "buy",
                2,
                40
                )

        val result = deferred.await()
        Assert.assertEquals(12312, result.id)
        Assert.assertEquals(serviceMock.jsonRpcVersion, result.jsonrpc)
        Assert.assertEquals(1, result.pageIndex)
        Assert.assertEquals(1, result.pageSize)
        Assert.assertEquals(663, result.total)
        Assert.assertNotNull(result.orders)
        Assert.assertNotNull(result.orders?.get(0))
        Assert.assertEquals(BigInteger("3e1affd8444b2a4fbbc",16),result.orders?.get(0)?.dealtAmountToSell)
        Assert.assertEquals(BigInteger("e605303a75d35880000",16),result.orders?.get(0)?.dealtAmountToBuy)
        Assert.assertEquals(BigInteger("0"),result.orders?.get(0)?.cancelledAmountToSell)
        Assert.assertEquals(BigInteger("0"),result.orders?.get(0)?.cancelledAmountToBuy)
        Assert.assertEquals("ORDER_FINISHED",result.orders?.get(0)?.status)
        Assert.assertNotNull(result.orders?.get(0)?.originalOrder)
        Assert.assertEquals("0x8d8812b72d1e4ffCeC158D25f56748b7d67c1e78",result.orders?.get(0)?.originalOrder?.protocol)
        Assert.assertEquals("0x17233e07c67d086464fD408148c3ABB56245FA64",result.orders?.get(0)?.originalOrder?.delegateAddress)
        Assert.assertEquals("0x328Bf4C7772C7efEA43c3e0C741085481A0AC311",result.orders?.get(0)?.originalOrder?.address)
        Assert.assertEquals("0xe85590c6ac6096de02a4b1b1cf57fe2980c483d9a9a8eebdddbdbccc21445cd6",result.orders?.get(0)?.originalOrder?.hash)
        Assert.assertEquals("LRC",result.orders?.get(0)?.originalOrder?.toSell)
        Assert.assertEquals("VITE",result.orders?.get(0)?.originalOrder?.toBuy)
        Assert.assertEquals(BigInteger("0x3e1b0038be886c60000",16),result.orders?.get(0)?.originalOrder?.amtToSell)
        Assert.assertEquals(BigInteger("0xe605303a75d35880000",16),result.orders?.get(0)?.originalOrder?.amtToBuy)
        Assert.assertEquals("0x5af8ed0c",result.orders?.get(0)?.originalOrder?.validSince)
        Assert.assertEquals("0x5afa3e8c",result.orders?.get(0)?.originalOrder?.validUntil)
        Assert.assertEquals("0x1fcc27bc459d20000",result.orders?.get(0)?.originalOrder?.lrcFee)
        Assert.assertEquals(true,result.orders?.get(0)?.originalOrder?.buyNoMoreThanBuyAmt)
        Assert.assertEquals("0x32",result.orders?.get(0)?.originalOrder?.marginSplitPercentage)
        Assert.assertEquals("0x1c",result.orders?.get(0)?.originalOrder?.v)
        Assert.assertEquals("0x415b91b4277947ac92a422d73528622f71075940b622487ac4ee0fabe2fbd326",result.orders?.get(0)?.originalOrder?.r)
        Assert.assertEquals("0x618e2ce5a0cf7f9fa5ee3c486369125eb812ce135ed69e024c8ea7bcd8c3e242",result.orders?.get(0)?.originalOrder?.s)
        Assert.assertEquals("0xb94065482Ad64d4c2b9252358D746B39e820A582",result.orders?.get(0)?.originalOrder?.walletAddress)
        Assert.assertEquals("0x2ab06e4d7606cF427b44C2aE01B1FA8b30c30824",result.orders?.get(0)?.originalOrder?.authAddr)
        Assert.assertEquals("0x82d50484609ee832fcff30abeb20a00935ba12746c0e18e391cb4a677bf72dd1",result.orders?.get(0)?.originalOrder?.authPrivateKey)
        Assert.assertEquals("VITE-LRC",result.orders?.get(0)?.originalOrder?.market)
        Assert.assertEquals("buy",result.orders?.get(0)?.originalOrder?.side)
        Assert.assertEquals(Date(1526263146),result.orders?.get(0)?.originalOrder?.createTime)
        Assert.assertEquals("market_order",result.orders?.get(0)?.originalOrder?.orderType)

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

    /**
     * Testing best case, all proper data is returned
     * Mock server
     */
    @Test
    fun getCutoff_shouldWorkMock() = runBlocking {
        val deferred = serviceMock.getCutoff("0x8888f1f195afa192cfee860698584c030f4c9db1", LooprCutoff.BLOCK_LATEST)

        val result = deferred.await()
        Assert.assertEquals(serviceMock.id, result.id)
        Assert.assertEquals(serviceMock.jsonRpcVersion, result.jsonrpc)
        Assert.assertNotNull(result.cutoff)
        Assert.assertTrue(result.cutoff?.getTime()!! > 1500000000)
    }

    /**
     * Testing best case, all proper data is returned
     * Live server
     */
    @Test
    fun getCutoff_shouldWorkLive() = runBlocking {
        val deferred = serviceLive.getCutoff("0x8888f1f195afa192cfee860698584c030f4c9db1", LooprCutoff.BLOCK_LATEST)

        val result = deferred.await()
        Assert.assertEquals(serviceLive.id, result.id)
        Assert.assertEquals(serviceLive.jsonRpcVersion, result.jsonrpc)
        Assert.assertNull(result.cutoff)
    }

    /**
     * Testing best case, all proper data is returned
     * Mock server
     */
    @Test
    fun getPriceQuote_shouldWorkMock() = runBlocking {
        val deferred = serviceMock.getPriceQuote("CNY")

        val result = deferred.await()
        Assert.assertEquals(serviceMock.id, result.id)
        Assert.assertEquals(serviceMock.jsonRpcVersion, result.jsonrpc)
        Assert.assertEquals("CNY",result.currency)
        Assert.assertNotNull(result.tokens?.get(0)?.price)
        Assert.assertTrue(result.tokens?.get(0)?.price!! > BigDecimal("0"))
        Assert.assertNotNull(result.tokens?.get(0)?.symbol)
    }

    /**
     * Testing best case, all proper data is returned
     * Live server
     */
    @Test
    fun getPriceQuote_shouldWorkLive() = runBlocking {
        val deferred = serviceLive.getPriceQuote("CNY")

        val result = deferred.await()
        Assert.assertEquals(serviceLive.id, result.id)
        Assert.assertEquals(serviceLive.jsonRpcVersion, result.jsonrpc)
        Assert.assertEquals("CNY",result.currency)
        Assert.assertNotNull(result.tokens?.get(0)?.price)
        Assert.assertTrue(result.tokens?.get(0)?.price!! > BigDecimal("0"))
        Assert.assertNotNull(result.tokens?.get(0)?.symbol)
    }

    /**
     * Testing best case, all proper data is returned
     * Mock server
     */
    @Test
    fun getEstimatedAllocatedAllowance_shouldWorkMock() = runBlocking {
        val deferred = serviceMock.getEstimatedAllocatedAllowance("0x8888f1f195afa192cfee860698584c030f4c9db1", "WETH")

        val result = deferred.await()
        Assert.assertEquals(serviceMock.id, result.id)
        Assert.assertEquals(serviceMock.jsonRpcVersion, result.jsonrpc)
        Assert.assertNotNull(result.allowance)
        Assert.assertEquals(BigInteger("2347ad6c",16), result.allowance)
    }

    /**
     * Testing best case, all proper data is returned
     * Live server
     */
    @Test
    fun getEstimatedAllocatedAllowance_shouldWorkLive() = runBlocking {
        val deferred = serviceLive.getEstimatedAllocatedAllowance("0x8888f1f195afa192cfee860698584c030f4c9db1", "WETH")

        val result = deferred.await()
        Assert.assertEquals(serviceLive.id, result.id)
        Assert.assertEquals(serviceLive.jsonRpcVersion, result.jsonrpc)
        Assert.assertNotNull(result.allowance)
        Assert.assertEquals(BigInteger("0"), result.allowance)
    }

    /**
     * Testing best case, all proper data is returned
     * Mock server
     */
    @Test
    fun getFrozenLRCFee_shouldWorkMock() = runBlocking {
        val deferred = serviceMock.getFrozenLRCFee("0x8888f1f195afa192cfee860698584c030f4c9db1")

        val result = deferred.await()
        Assert.assertEquals(serviceMock.id, result.id)
        Assert.assertEquals(serviceMock.jsonRpcVersion, result.jsonrpc)
        Assert.assertNotNull(result.frozenFees)
        Assert.assertEquals(BigInteger("2347ad6c",16), result.frozenFees)
    }

    /**
     * Testing best case, all proper data is returned
     * Live server
     */
    @Test
    fun getFrozenLRCFee_shouldWorkLive() = runBlocking {
        val deferred = serviceLive.getFrozenLRCFee("0x8888f1f195afa192cfee860698584c030f4c9db1")

        val result = deferred.await()
        Assert.assertEquals(serviceLive.id, result.id)
        Assert.assertEquals(serviceLive.jsonRpcVersion, result.jsonrpc)
        Assert.assertNotNull(result.frozenFees)
        Assert.assertEquals(BigInteger("0",16), result.frozenFees)
    }

    /**
     * Testing best case, all proper data is returned
     * Mock server
     */
    @Test
    fun getSupportedMarket_shouldWorkMock() = runBlocking {
        val deferred = serviceMock.getSupportedMarket()

        val result = deferred.await()
        Assert.assertEquals(serviceMock.id, result.id)
        Assert.assertEquals(serviceMock.jsonRpcVersion, result.jsonrpc)
        Assert.assertNotNull(result.pairs)
        Assert.assertNotNull(result.pairs?.get(0))
        Assert.assertEquals("ZRX-LRC", result.pairs?.get(0))
    }

    /**
     * Testing best case, all proper data is returned
     * Live server
     */
    @Test
    fun getSupportedMarket_shouldWorkLive() = runBlocking {
        val deferred = serviceLive.getSupportedMarket()

        val result = deferred.await()
        Assert.assertEquals(serviceLive.id, result.id)
        Assert.assertEquals(serviceLive.jsonRpcVersion, result.jsonrpc)
        Assert.assertNotNull(result.pairs)
        Assert.assertNotNull(result.pairs?.get(0))
    }

    /**
     * Testing best case, all proper data is returned
     * Mock server
     */
    @Test
    fun getSupportedTokens_shouldWorkMock() = runBlocking {
        val deferred = serviceMock.getSupportedTokens()

        val result = deferred.await()
        Assert.assertEquals(serviceMock.id, result.id)
        Assert.assertEquals(serviceMock.jsonRpcVersion, result.jsonrpc)
        Assert.assertNotNull(result.tokens)
        Assert.assertNotNull(result.tokens?.get(0))
        Assert.assertEquals("0xfa1a856cfa3409cfa145fa4e20eb270df3eb21ab", result.tokens?.get(0)?.protocol)
        Assert.assertEquals("IOST", result.tokens?.get(0)?.symbol)
        Assert.assertEquals("iostoken", result.tokens?.get(0)?.source)
        Assert.assertEquals(Date(0), result.tokens?.get(0)?.time)
        Assert.assertEquals(false, result.tokens?.get(0)?.deny)
        Assert.assertEquals(BigInteger("1000000000000000000"), result.tokens?.get(0)?.decimals)
        Assert.assertEquals(false, result.tokens?.get(0)?.isMarket)
        Assert.assertNull(result.tokens?.get(0)?.icoPrice)
    }

    /**
     * Testing best case, all proper data is returned
     * Live server
     */
    @Test
    fun getSupportedTokens_shouldWorkLive() = runBlocking {
        val deferred = serviceLive.getSupportedTokens()

        val result = deferred.await()
        Assert.assertEquals(serviceLive.id, result.id)
        Assert.assertEquals(serviceLive.jsonRpcVersion, result.jsonrpc)
        Assert.assertNotNull(result.tokens)
        Assert.assertNotNull(result.tokens?.get(0))
        Assert.assertNotNull(result.tokens?.get(0)?.protocol)
        Assert.assertNotNull(result.tokens?.get(0)?.symbol)
        Assert.assertNotNull(result.tokens?.get(0)?.source)
        Assert.assertNotNull(result.tokens?.get(0)?.time)
        Assert.assertNotNull(result.tokens?.get(0)?.deny)
        Assert.assertNotNull(result.tokens?.get(0)?.decimals)
        Assert.assertNotNull(result.tokens?.get(0)?.isMarket)
    }

    /**
     * Testing best case, all proper data is returned
     * Mock server
     */
    @Test
    fun getPortfolio_shouldWorkMock() = runBlocking {
        val deferred = serviceMock.getPortfolio("0xeba7136a36da0f5e16c6bdbc739c716bb5b65a00")

        val result = deferred.await()
        Assert.assertEquals(serviceMock.id, result.id)
        Assert.assertEquals(serviceMock.jsonRpcVersion, result.jsonrpc)
        Assert.assertNotNull(result.tokens)
        Assert.assertNotNull(result.tokens?.get(0))
        Assert.assertEquals("BAR", result.tokens?.get(0)?.token)
        Assert.assertEquals(BigInteger("9782200000000000000000"), result.tokens?.get(0)?.amount)
        Assert.assertEquals("91.8582%", result.tokens?.get(0)?.percentage)
    }

    /**
     * Testing best case, all proper data is returned
     * Live server
     */
    @Test
    fun getPortfolio_shouldWorkLive() = runBlocking {
        val deferred = serviceLive.getPortfolio("0xeba7136a36da0f5e16c6bdbc739c716bb5b65a00")

        val result = deferred.await()
        Assert.assertEquals(serviceLive.id, result.id)
        Assert.assertEquals(serviceLive.jsonRpcVersion, result.jsonrpc)
        Assert.assertNotNull(result.tokens)
        Assert.assertNotNull(result.tokens?.get(0))
        Assert.assertNotNull(result.tokens?.get(0)?.token)
        Assert.assertNotNull(result.tokens?.get(0)?.amount)
        Assert.assertNotNull(result.tokens?.get(0)?.percentage)
    }

    /**
     * Testing best case, all proper data is returned
     * Mock server
     */
    @Test
    fun getTransactions_shouldWorkMock() = runBlocking {
        val deferred = serviceMock.getTransactions(
                "0xeba7136a36da0f5e16c6bdbc739c716bb5b65a00",
                "0x46b9ab33d6904718fc2d16ad1a133a35ae23045bb65893eb2c41b0984b78eca7",
                "LRC","success","receive",1,20
        )

        val result = deferred.await()
        Assert.assertEquals(serviceMock.id, result.id)
        Assert.assertEquals(serviceMock.jsonRpcVersion, result.jsonrpc)
        Assert.assertEquals(1,result.pageIndex)
        Assert.assertEquals(10,result.pageSize)
        Assert.assertEquals(3,result.total)
        Assert.assertNotNull(result.transactions)
        Assert.assertNotNull(result.transactions?.get(0))
        Assert.assertEquals("0xeba7136a36da0f5e16c6bdbc739c716bb5b65a00",result.transactions?.get(0)?.owner)
        Assert.assertEquals("0x2234c96681e9533fdfd122bacbbc634efbafa0f0",result.transactions?.get(0)?.from)
        Assert.assertEquals("0xeba7136a36da0f5e16c6bdbc739c716bb5b65a00",result.transactions?.get(0)?.to)
        Assert.assertEquals("0x46b9ab33d6904718fc2d16ad1a133a35ae23045bb65893eb2c41b0984b78eca7",result.transactions?.get(0)?.hash)
        Assert.assertEquals("LRC",result.transactions?.get(0)?.symbol)
        Assert.assertEquals(parseLong("5550001"),result.transactions?.get(0)?.blockNumber)
        Assert.assertEquals(BigInteger("79640000000000000000"),result.transactions?.get(0)?.value)
        Assert.assertEquals("receive",result.transactions?.get(0)?.type)
        Assert.assertEquals("success",result.transactions?.get(0)?.status)
        Assert.assertEquals(Date(1525364262),result.transactions?.get(0)?.createTime)
        Assert.assertEquals(Date(1525364262),result.transactions?.get(0)?.updateTime)
    }

    /**
     * Testing best case, all proper data is returned
     * Live server
     */
    @Test
    fun getTransactions_shouldWorkLive() = runBlocking {
        val deferred = serviceLive.getTransactions(
                "0xeba7136a36da0f5e16c6bdbc739c716bb5b65a00",
                "0x46b9ab33d6904718fc2d16ad1a133a35ae23045bb65893eb2c41b0984b78eca7",
                "LRC","success","receive",1,20
        )

        val result = deferred.await()
        Assert.assertEquals(serviceLive.id, result.id)
        Assert.assertEquals(serviceLive.jsonRpcVersion, result.jsonrpc)
        Assert.assertEquals(1,result.pageIndex)
        Assert.assertEquals(20,result.pageSize)
        Assert.assertEquals(3,result.total)
        Assert.assertNotNull(result.transactions)
        Assert.assertNotNull(result.transactions?.get(0))
        Assert.assertNotNull(result.transactions?.get(0)?.owner)
        Assert.assertNotNull(result.transactions?.get(0)?.from)
        Assert.assertNotNull(result.transactions?.get(0)?.to)
        Assert.assertNotNull(result.transactions?.get(0)?.hash)
        Assert.assertNotNull(result.transactions?.get(0)?.symbol)
        Assert.assertNotNull(result.transactions?.get(0)?.blockNumber)
        Assert.assertNotNull(result.transactions?.get(0)?.value)
        Assert.assertNotNull(result.transactions?.get(0)?.type)
        Assert.assertNotNull(result.transactions?.get(0)?.status)
        Assert.assertNotNull(result.transactions?.get(0)?.createTime)
        Assert.assertNotNull(result.transactions?.get(0)?.updateTime)
    }

    /**
     * Testing best case, all proper data is returned
     * Mock server
     */
    @Test
    fun unlockWallet_shouldWorkMock() = runBlocking {
        val deferred = serviceMock.unlockWallet("0xeba7136a36da0f5e16c6bdbc739c716bb5b65a00")

        val result = deferred.await()
        Assert.assertEquals(serviceMock.id, result.id)
        Assert.assertEquals(serviceMock.jsonRpcVersion, result.jsonrpc)
        Assert.assertEquals("unlock_notice_success",result.response)
    }

    /**
     * Testing best case, all proper data is returned
     * Live server
     */
    @Test
    fun unlockWallet_shouldWorkLive() = runBlocking {
        val deferred = serviceLive.unlockWallet("0xeba7136a36da0f5e16c6bdbc739c716bb5b65a00")

        val result = deferred.await()
        Assert.assertEquals(serviceLive.id, result.id)
        Assert.assertEquals(serviceLive.jsonRpcVersion, result.jsonrpc)
        Assert.assertEquals("unlock_notice_success",result.response)
    }

    /**
     * Testing best case, all proper data is returned
     * Mock server
     */
    @Test
    fun notifyTransactionSubmitted_shouldWorkMock() = runBlocking {
        val deferred = serviceMock.unlockWallet("0xeba7136a36da0f5e16c6bdbc739c716bb5b65a00")

        val result = deferred.await()
        Assert.assertEquals(serviceMock.id, result.id)
        Assert.assertEquals(serviceMock.jsonRpcVersion, result.jsonrpc)
        Assert.assertEquals("unlock_notice_success",result.response)
    }


}