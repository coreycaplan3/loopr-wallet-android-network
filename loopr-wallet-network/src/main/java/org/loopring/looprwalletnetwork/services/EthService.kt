package org.loopring.looprwalletnetwork.services

import kotlinx.coroutines.experimental.async
import org.web3j.crypto.Credentials
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.methods.response.TransactionReceipt
import org.web3j.tx.RawTransactionManager
import org.web3j.tx.Transfer
import org.web3j.utils.Convert
import java.math.BigInteger

/**
 * Created by Corey Caplan on 4/2/18.
 *
 * Project: loopr-wallet-android-network
 *
 * Purpose of Class: A class used for sending ETH to other addresses.
 *
 */
@Suppress("unused")
class EthService private constructor(private val web3j: Web3j) {

    /**
     * Sends ether from the address in [credentials] to [toAddress].
     *
     * @param amount The amount (in WEI) to send (represented as a [BigInteger], padded with 18
     * numbers for the decimal places)
     * @param toAddress The address to send the ETH
     * @param credentials The user's [Credentials] for signing and sending the transaction
     * @return A deferred transaction hash for the send.
     * @param gasPrice The price of gas in GWEI.
     * @param gasLimit The price of gas in GWEI. IE [Transfer.GAS_LIMIT]
     * @return A [TransactionReceipt] if the send was successful or throws an exception otherwise.
     * @throws java.io.IOException If there was a connectivity issue
     * @throws InterruptedException If the thread from which the send is occurring is interrupted
     * @throws org.web3j.protocol.exceptions.TransactionException If the transaction takes too
     * long to broadcast
     */
    fun sendEther(
            amount: BigInteger,
            toAddress: String,
            credentials: Credentials,
            gasPrice: BigInteger,
            gasLimit: BigInteger
    ) = async {
        val transactionManager = RawTransactionManager(web3j, credentials)

        return@async Transfer(web3j, transactionManager)
                .sendFunds(toAddress, amount.toBigDecimal(), Convert.Unit.WEI, gasPrice, gasLimit)
                .send()
    }

    companion object {

        /**
         * Gets an instance of [EthService]
         */
        fun getService(web3j: Web3j) = EthService(web3j)

    }

}