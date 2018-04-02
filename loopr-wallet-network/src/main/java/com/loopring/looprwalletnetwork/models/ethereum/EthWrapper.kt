package com.loopring.looprwalletnetwork.models.ethereum

import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import org.web3j.crypto.Credentials
import org.web3j.protocol.Web3j
import org.web3j.tx.Transfer
import org.web3j.utils.Convert
import java.math.BigDecimal

/**
 * Created by Corey Caplan on 4/2/18.
 *
 * Project: loopr-wallet-android-network
 *
 * Purpose of Class:
 *
 */
object EthWrapper {

    /**
     * Sends ether from the address in [credentials] to [destAddress].
     *
     * @param amount The amount (in ETH) to send (represented as a whole number)
     * @param destAddress The address to send the ETH
     * @param web3j The [Web3j] instance that is used to send the ETH.
     * @param credentials The user's [Credentials] for signing and sending the transaction
     * @return A deferred transaction hash for the send.
     * @throws java.io.IOException If there was a connectivity issue
     * @throws InterruptedException If the thread from which the send is occurring is interrupted
     * @throws org.web3j.protocol.exceptions.TransactionException If the transaction takes too
     * long to broadcast
     */
    fun sendEth(amount: BigDecimal, destAddress: String, web3j: Web3j, credentials: Credentials): Deferred<String> = async {
        Transfer.sendFunds(web3j, credentials, destAddress, amount, Convert.Unit.ETHER)
                .send()
                .transactionHash
    }

}