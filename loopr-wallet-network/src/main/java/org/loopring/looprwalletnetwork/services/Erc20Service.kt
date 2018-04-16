package org.loopring.looprwalletnetwork.services

import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import org.web3j.abi.TypeReference
import org.web3j.abi.datatypes.Address
import org.web3j.abi.datatypes.Function
import org.web3j.abi.datatypes.Type
import org.web3j.abi.datatypes.generated.Uint256
import org.web3j.crypto.Credentials
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.methods.response.TransactionReceipt
import org.web3j.tx.Contract
import org.web3j.tx.TransactionManager
import java.math.BigInteger
import java.util.*
import java.util.Collections.emptyList

/**
 * Created by arknw229 on 3/14/18.
 *
 * This class is used to wrap around the functionality of ERC20 tokens
 * @author arknw229
 */
class Erc20Service : Contract {

    private constructor(contractAddress: String,
                        web3j: Web3j,
                        credentials: Credentials,
                        gasPrice: BigInteger,
                        gasLimit: BigInteger,
                        binary: String
    ) : super(binary, contractAddress, web3j, credentials, gasPrice, gasLimit)

    private constructor(contractAddress: String,
                        web3j: Web3j,
                        transactionManager: TransactionManager,
                        gasPrice: BigInteger,
                        gasLimit: BigInteger,
                        binary: String
    ) : super(binary, contractAddress, web3j, transactionManager, gasPrice, gasLimit)

    /**
     * The total supply of an ERC20 token
     *
     * @return Returns a remoteCall with a BigInteger representing the supply
     */
    fun totalSupply(): Deferred<BigInteger> = async {
        val function = Function("totalSupply",
                Arrays.asList(),
                Arrays.asList<TypeReference<*>>(object : TypeReference<Uint256>() {}))

        executeRemoteCallSingleValueReturn(function, BigInteger::class.java).send()
    }

    /**
     * Provides the number of tokens held by a given address
     * Note that anyone can query any address’ balance, as all data on the blockchain is public
     *
     * @param tokenOwner the owner of the token
     * @return Returns a remoteCall with a BigInteger repesenting the balance
     */
    fun balanceOf(tokenOwner: String): Deferred<BigInteger> = async {
        val function = Function("balanceOf",
                Arrays.asList<Type<*>>(Address(tokenOwner)),
                Arrays.asList<TypeReference<*>>(object : TypeReference<Uint256>() {}))

        executeRemoteCallSingleValueReturn(function, BigInteger::class.java).send()
    }

    /**
     * Provides the number of tokens allowed to be transferred from a given address by another given address
     *
     * @param tokenOwner the owner of the token
     * @param spender the owner of the token
     * @return Returns a remoteCall with a BigInteger
     */
    fun allowance(tokenOwner: String, spender: String): Deferred<BigInteger> = async {
        val function = Function("allowance",
                Arrays.asList<Type<*>>(Address(tokenOwner), Address(spender)),
                Arrays.asList<TypeReference<*>>(object : TypeReference<Uint256>() {}))

        executeRemoteCallSingleValueReturn(function, BigInteger::class.java).send()
    }

    /**
     * Transfers a number of tokens directly from the message sender to another address
     * Note that there are no checks made on the recipient address
     *
     * Although [transfer] is fine for sending tokens from one user to another it doesn’t
     * work so well when tokens are being used to pay for a function in a smart contract.
     * This is because at the time the smart contract runs it has no access to details of
     * which addresses transferred funds where and so cannot assert that the user calling
     * the contract has paid the required amount of funds to operate the contract.
     *
     * @param to the recipient of the tokens
     * @param tokens the number of tokens to be sent (in smallest token unit)
     * @return Returns a remoteCall with a TransactionReceipt to give status of the transaction
     */
    fun transfer(to: String, tokens: BigInteger): Deferred<TransactionReceipt> = async {
        val function = Function(
                "transfer",
                Arrays.asList(Address(to), Uint256(tokens)),
                emptyList<TypeReference<*>>())
        executeRemoteCallTransaction(function).send()
    }

    /**
     * A token holder gives another address (usually of a smart contract) approval to transfer
     * up to a certain number of tokens, known as an allowance. The token holder uses approve()
     * to provide this information
     *
     * @param spender the spender of the tokens
     * @param tokens the number of tokens to approve
     * @return Returns a remoteCall with a TransactionReceipt to give status of the transaction
     */
    fun approve(spender: String, tokens: BigInteger): Deferred<TransactionReceipt> = async {
        val function = Function(
                "approve",
                Arrays.asList(Address(spender), Uint256(tokens)),
                emptyList<TypeReference<*>>())
        executeRemoteCallTransaction(function).send()
    }

    /**
     * Send `tokens` amount of tokens from address `from` to address `to`
     * The transferFrom method is used for a withdraw workflow, allowing contracts to send
     * tokens on your behalf, for example to "deposit" to a contract address and/or to charge
     * fees in sub-currencies; the command should fail unless the _from account has
     * deliberately authorized the sender of the message via some mechanism
     *
     * @param from the spender of the tokens
     * @param to the receiver of the tokens
     * @param tokens the number of tokens to approve
     * @return Returns a remoteCall with a TransactionReceipt to give status of the transaction
     */
    fun transferFrom(from: String, to: String, tokens: BigInteger): Deferred<TransactionReceipt> = async {
        val function = Function(
                "transferFrom",
                Arrays.asList(Address(from), Address(to), Uint256(tokens)),
                emptyList<TypeReference<*>>())
        executeRemoteCallTransaction(function).send()
    }

    companion object {

        /**
         * This will create a new instance of the smart contract on the Ethereum blockchain using
         * the supplied credentials, and constructor parameter values
         *
         * @param web3j the web3j object that connects with the network
         * @param credentials the credentials of the wallet conducting the transaction
         * @param gasPrice price of gas for this transaction
         * @param gasLimit gas limit for the transaction
         * @param binary The binary transaction information. This can be found when the contract
         * was first deployed.
         * @return RemoteCall with response of the request
         */
        fun deploy(
                web3j: Web3j,
                credentials: Credentials,
                gasPrice: BigInteger,
                gasLimit: BigInteger,
                binary: String
        ) = deployRemoteCall(Erc20Service::class.java, web3j, credentials, gasPrice, gasLimit, binary, "")

        /**
         * This will create a new instance of the smart contract on the Ethereum blockchain using
         * the supplied credentials, and constructor parameter values
         *
         * @param web3j the web3j object that connects with the network
         * @param transactionManager the transaction manager with the relevant transaction information
         * @param gasPrice price of gas for this transaction
         * @param gasLimit gas limit for the transaction
         * @param binary The binary transaction information. This can be found when the contract
         * was first deployed.
         * @return RemoteCall with response of the reqquest
         */
        fun deploy(
                web3j: Web3j,
                transactionManager: TransactionManager,
                gasPrice: BigInteger,
                gasLimit: BigInteger,
                binary: String
        ) = deployRemoteCall(Erc20Service::class.java, web3j, transactionManager, gasPrice, gasLimit, binary, "")

        /**
         * Constructs an instance of a smart contract wrapper with an existing smart contract
         *
         * @param web3j the web3j object that connects with the network
         * @param credentials the credentials of the wallet conducting the transaction
         * @param gasPrice price of gas for this transaction
         * @param gasLimit gas limit for the transaction
         * @param binary The binary transaction information. This can be found when the contract
         * was first deployed.
         * @return RemoteCall with response of the request
         */
        fun getInstance(
                contractAddress: String,
                web3j: Web3j,
                credentials: Credentials,
                gasPrice: BigInteger,
                gasLimit: BigInteger,
                binary: String
        ) = Erc20Service(contractAddress, web3j, credentials, gasPrice, gasLimit, binary)

        /**
         * Constructs an instance of a smart contract wrapper with an existing smart contract
         *
         * @param web3j the web3j object that connects with the network
         * @param transactionManager the transaction manager with the relevant transaction information
         * @param gasPrice price of gas for this transaction
         * @param gasLimit gas limit for the transaction
         * @param binary The binary transaction information. This can be found when the contract
         * was first deployed.
         * @return RemoteCall with response of the request
         */
        fun getInstance(
                contractAddress: String,
                web3j: Web3j,
                transactionManager: TransactionManager,
                gasPrice: BigInteger,
                gasLimit: BigInteger,
                binary: String
        ) = Erc20Service(contractAddress, web3j, transactionManager, gasPrice, gasLimit, binary)

    }

}
