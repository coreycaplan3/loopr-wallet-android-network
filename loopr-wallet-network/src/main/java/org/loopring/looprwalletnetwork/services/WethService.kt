package org.loopring.looprwalletnetwork.services

import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import java.math.BigInteger
import java.util.ArrayList
import java.util.Arrays
import org.web3j.abi.EventEncoder
import org.web3j.abi.TypeReference
import org.web3j.abi.datatypes.Address
import org.web3j.abi.datatypes.Event
import org.web3j.abi.datatypes.Function
import org.web3j.abi.datatypes.Type
import org.web3j.abi.datatypes.Utf8String
import org.web3j.abi.datatypes.generated.Uint256
import org.web3j.abi.datatypes.generated.Uint8
import org.web3j.crypto.Credentials
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.DefaultBlockParameter
import org.web3j.protocol.core.RemoteCall
import org.web3j.protocol.core.methods.request.EthFilter
import org.web3j.protocol.core.methods.response.TransactionReceipt
import org.web3j.tx.Contract
import org.web3j.tx.TransactionManager
import rx.Observable

/**
 *
 * Auto generated code.
 *
 * **Do not modify!**
 *
 * Please use the [web3j command line tools](https://docs.web3j.io/command_line.html),
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the
 * [codegen module](https://github.com/web3j/web3j/tree/master/codegen) to update.
 *
 *
 * Generated with web3j version 3.2.0.
 */
class WethService : Contract {

    protected constructor(contractAddress: String, web3j: Web3j, credentials: Credentials, gasPrice: BigInteger, gasLimit: BigInteger) : super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit) {}

    protected constructor(contractAddress: String, web3j: Web3j, transactionManager: TransactionManager, gasPrice: BigInteger, gasLimit: BigInteger) : super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit) {}

    fun getTransferEvents(transactionReceipt: TransactionReceipt): List<TransferEventResponse> {
        val event = Event("Transfer",
                Arrays.asList<TypeReference<*>>(object : TypeReference<Address>() {

                }, object : TypeReference<Address>() {

                }),
                Arrays.asList<TypeReference<*>>(object : TypeReference<Uint256>() {

                }))
        val valueList = extractEventParameters(event, transactionReceipt)
        val responses = ArrayList<TransferEventResponse>(valueList.size)
        for (eventValues in valueList) {
            val typedResponse = TransferEventResponse()
            typedResponse._from = eventValues.indexedValues[0].value as String
            typedResponse._to = eventValues.indexedValues[1].value as String
            typedResponse._value = eventValues.nonIndexedValues[0].value as BigInteger
            responses.add(typedResponse)
        }
        return responses
    }

    fun transferEventObservable(startBlock: DefaultBlockParameter, endBlock: DefaultBlockParameter): Observable<TransferEventResponse> {
        val event = Event("Transfer",
                Arrays.asList<TypeReference<*>>(object : TypeReference<Address>() {

                }, object : TypeReference<Address>() {

                }),
                Arrays.asList<TypeReference<*>>(object : TypeReference<Uint256>() {

                }))
        val filter = EthFilter(startBlock, endBlock, getContractAddress())
        filter.addSingleTopic(EventEncoder.encode(event))
        return web3j.ethLogObservable(filter).map { log ->
            val eventValues = extractEventParameters(event, log)
            val typedResponse = TransferEventResponse()
            typedResponse._from = eventValues.indexedValues[0].value as String
            typedResponse._to = eventValues.indexedValues[1].value as String
            typedResponse._value = eventValues.nonIndexedValues[0].value as BigInteger
            typedResponse
        }
    }

    fun getApprovalEvents(transactionReceipt: TransactionReceipt): List<ApprovalEventResponse> {
        val event = Event("Approval",
                Arrays.asList<TypeReference<*>>(object : TypeReference<Address>() {

                }, object : TypeReference<Address>() {

                }),
                Arrays.asList<TypeReference<*>>(object : TypeReference<Uint256>() {

                }))
        val valueList = extractEventParameters(event, transactionReceipt)
        val responses = ArrayList<ApprovalEventResponse>(valueList.size)
        for (eventValues in valueList) {
            val typedResponse = ApprovalEventResponse()
            typedResponse._owner = eventValues.indexedValues[0].value as String
            typedResponse._spender = eventValues.indexedValues[1].value as String
            typedResponse._value = eventValues.nonIndexedValues[0].value as BigInteger
            responses.add(typedResponse)
        }
        return responses
    }

    fun approvalEventObservable(startBlock: DefaultBlockParameter, endBlock: DefaultBlockParameter): Observable<ApprovalEventResponse> {
        val event = Event("Approval",
                Arrays.asList<TypeReference<*>>(object : TypeReference<Address>() {

                }, object : TypeReference<Address>() {

                }),
                Arrays.asList<TypeReference<*>>(object : TypeReference<Uint256>() {

                }))
        val filter = EthFilter(startBlock, endBlock, getContractAddress())
        filter.addSingleTopic(EventEncoder.encode(event))
        return web3j.ethLogObservable(filter).map { log ->
            val eventValues = extractEventParameters(event, log)
            val typedResponse = ApprovalEventResponse()
            typedResponse._owner = eventValues.indexedValues[0].value as String
            typedResponse._spender = eventValues.indexedValues[1].value as String
            typedResponse._value = eventValues.nonIndexedValues[0].value as BigInteger
            typedResponse
        }
    }

    fun name(): Deferred<String> = async {
        val function = Function("name",
                Arrays.asList(),
                Arrays.asList<TypeReference<*>>(object : TypeReference<Utf8String>() {

                }))
        executeRemoteCallSingleValueReturn(function, String::class.java).send()
    }

    fun approve(_spender: String, _value: BigInteger): Deferred<TransactionReceipt> = async {
        val function = Function(
                "approve",
                Arrays.asList(org.web3j.abi.datatypes.Address(_spender),
                        org.web3j.abi.datatypes.generated.Uint256(_value)),
                emptyList())
        executeRemoteCallTransaction(function).send()
    }

    fun totalSupply(): Deferred<BigInteger> = async {
        val function = Function("totalSupply",
                Arrays.asList(),
                Arrays.asList<TypeReference<*>>(object : TypeReference<Uint256>() {

                }))
        executeRemoteCallSingleValueReturn(function, BigInteger::class.java).send()
    }

    fun transferFrom(_from: String, _to: String, _value: BigInteger): Deferred<TransactionReceipt> = async {
        val function = Function(
                "transferFrom",
                Arrays.asList(org.web3j.abi.datatypes.Address(_from),
                        org.web3j.abi.datatypes.Address(_to),
                        org.web3j.abi.datatypes.generated.Uint256(_value)),
                emptyList())
        executeRemoteCallTransaction(function).send()
    }

    fun withdraw(amount: BigInteger): Deferred<TransactionReceipt> = async {
        val function = Function(
                "withdraw",
                Arrays.asList<Type<*>>(org.web3j.abi.datatypes.generated.Uint256(amount)),
                emptyList())
        executeRemoteCallTransaction(function).send()
    }

    fun decimals(): Deferred<BigInteger> = async {
        val function = Function("decimals",
                Arrays.asList(),
                Arrays.asList<TypeReference<*>>(object : TypeReference<Uint8>() {

                }))
        executeRemoteCallSingleValueReturn(function, BigInteger::class.java).send()
    }

    fun balanceOf(_owner: String): Deferred<BigInteger> = async {
        val function = Function("balanceOf",
                Arrays.asList<Type<*>>(org.web3j.abi.datatypes.Address(_owner)),
                Arrays.asList<TypeReference<*>>(object : TypeReference<Uint256>() {

                }))
        executeRemoteCallSingleValueReturn(function, BigInteger::class.java).send()
    }

    fun symbol(): Deferred<String> = async {
        val function = Function("symbol",
                Arrays.asList(),
                Arrays.asList<TypeReference<*>>(object : TypeReference<Utf8String>() {

                }))
        executeRemoteCallSingleValueReturn(function, String::class.java).send()
    }

    fun transfer(_to: String, _value: BigInteger): Deferred<TransactionReceipt> = async {
        val function = Function(
                "transfer",
                Arrays.asList(org.web3j.abi.datatypes.Address(_to),
                        org.web3j.abi.datatypes.generated.Uint256(_value)),
                emptyList())
        executeRemoteCallTransaction(function).send()
    }

    fun deposit(weiValue: BigInteger): Deferred<TransactionReceipt> = async {
        val function = Function(
                "deposit",
                Arrays.asList(),
                emptyList())
        executeRemoteCallTransaction(function, weiValue).send()
    }

    fun allowance(_owner: String, _spender: String): Deferred<BigInteger> = async {
        val function = Function("allowance",
                Arrays.asList<Type<*>>(org.web3j.abi.datatypes.Address(_owner),
                        org.web3j.abi.datatypes.Address(_spender)),
                Arrays.asList<TypeReference<*>>(object : TypeReference<Uint256>() {

                }))
        executeRemoteCallSingleValueReturn(function, BigInteger::class.java).send()
    }

    class TransferEventResponse {
        var _from: String? = null

        var _to: String? = null

        var _value: BigInteger? = null
    }

    class ApprovalEventResponse {
        var _owner: String? = null

        var _spender: String? = null

        var _value: BigInteger? = null
    }

    companion object {
        private val BINARY = "6060604052341561000c57fe5b5b610d928061001c6000396000f300606060405236156100ad576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff16806306fdde03146100be578063095ea7b31461015757806318160ddd146101ae57806323b872dd146101d45780632e1a7d4d1461024a578063313ce5671461026a57806370a082311461029657806395d89b41146102e0578063a9059cbb14610379578063d0e30db0146103d0578063dd62ed3e146103da575b6100bc5b6100b9610443565b5b565b005b34156100c657fe5b6100ce6104e4565b604051808060200182810382528381815181526020019150805190602001908083836000831461011d575b80518252602083111561011d576020820191506020810190506020830392506100f9565b505050905090810190601f1680156101495780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b341561015f57fe5b610194600480803573ffffffffffffffffffffffffffffffffffffffff1690602001909190803590602001909190505061051e565b604051808215151515815260200191505060405180910390f35b34156101b657fe5b6101be610611565b6040518082815260200191505060405180910390f35b34156101dc57fe5b610230600480803573ffffffffffffffffffffffffffffffffffffffff1690602001909190803573ffffffffffffffffffffffffffffffffffffffff16906020019091908035906020019091905050610617565b604051808215151515815260200191505060405180910390f35b341561025257fe5b6102686004808035906020019091905050610947565b005b341561027257fe5b61027a610a2d565b604051808260ff1660ff16815260200191505060405180910390f35b341561029e57fe5b6102ca600480803573ffffffffffffffffffffffffffffffffffffffff16906020019091905050610a32565b6040518082815260200191505060405180910390f35b34156102e857fe5b6102f0610a7c565b604051808060200182810382528381815181526020019150805190602001908083836000831461033f575b80518252602083111561033f5760208201915060208101905060208303925061031b565b505050905090810190601f16801561036b5780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b341561038157fe5b6103b6600480803573ffffffffffffffffffffffffffffffffffffffff16906020019091908035906020019091905050610ab6565b604051808215151515815260200191505060405180910390f35b6103d8610443565b005b34156103e257fe5b61042d600480803573ffffffffffffffffffffffffffffffffffffffff1690602001909190803573ffffffffffffffffffffffffffffffffffffffff16906020019091905050610ca4565b6040518082815260200191505060405180910390f35b61048c600060003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205434610d2c565b600060003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020819055506104db60025434610d2c565b6002819055505b565b604060405190810160405280600b81526020017f457468657220546f6b656e00000000000000000000000000000000000000000081525081565b600081600160003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020819055508273ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff167f8c5be1e5ebec7d5bd14f71427d1e84f3dd0314c0f7b2291e5b200ac8c7c3b925846040518082815260200191505060405180910390a3600190505b92915050565b60025481565b60006000600160008673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002054905082600060008773ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002054101580156106e95750828110155b80156107755750600060008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205483600060008773ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020540110155b156109355782600060008673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206000828254019250508190555082600060008773ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020600082825403925050819055507fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff8110156108c75782600160008773ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020600082825403925050819055505b8373ffffffffffffffffffffffffffffffffffffffff168573ffffffffffffffffffffffffffffffffffffffff167fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef856040518082815260200191505060405180910390a36001915061093f565b6000915061093f565b5b509392505050565b610990600060003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205482610d4c565b600060003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020819055506109df60025482610d4c565b6002819055503373ffffffffffffffffffffffffffffffffffffffff166108fc829081150290604051809050600060405180830381858888f193505050501515610a295760006000fd5b5b50565b601281565b6000600060008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205490505b919050565b604060405190810160405280600481526020017f574554480000000000000000000000000000000000000000000000000000000081525081565b600081600060003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205410158015610b875750600060008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205482600060008673ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020540110155b15610c945781600060003373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000206000828254039250508190555081600060008573ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001908152602001600020600082825401925050819055508273ffffffffffffffffffffffffffffffffffffffff163373ffffffffffffffffffffffffffffffffffffffff167fddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef846040518082815260200191505060405180910390a360019050610c9e565b60009050610c9e565b5b92915050565b6000600160008473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019081526020016000205490505b92915050565b600060008284019050838110151515610d4157fe5b8091505b5092915050565b6000828211151515610d5a57fe5b81830390505b929150505600a165627a7a72305820197b06eae7be68ba129f01685b2c2fa836d9873b2de1861bdab047f4333beb7b0029\n"

        fun deploy(web3j: Web3j, credentials: Credentials, gasPrice: BigInteger, gasLimit: BigInteger): RemoteCall<WethService> {
            return Contract.deployRemoteCall(WethService::class.java, web3j, credentials, gasPrice, gasLimit, BINARY, "")
        }

        fun deploy(web3j: Web3j, transactionManager: TransactionManager, gasPrice: BigInteger, gasLimit: BigInteger): RemoteCall<WethService> {
            return Contract.deployRemoteCall(WethService::class.java, web3j, transactionManager, gasPrice, gasLimit, BINARY, "")
        }

        fun load(contractAddress: String, web3j: Web3j, credentials: Credentials, gasPrice: BigInteger, gasLimit: BigInteger): WethService {
            return WethService(contractAddress, web3j, credentials, gasPrice, gasLimit)
        }

        fun load(contractAddress: String, web3j: Web3j, transactionManager: TransactionManager, gasPrice: BigInteger, gasLimit: BigInteger): WethService {
            return WethService(contractAddress, web3j, transactionManager, gasPrice, gasLimit)
        }
    }
}
