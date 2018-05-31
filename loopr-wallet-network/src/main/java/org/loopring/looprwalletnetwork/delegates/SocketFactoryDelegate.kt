package org.loopring.looprwalletnetwork.delegates

import java.net.InetAddress
import java.net.Socket
import javax.net.SocketFactory

/**
 * Created by corey on 5/31/18
 *
 * Project: loopr-wallet-android-network
 *
 * Purpose of Class: To setup tagging on thread sockets to comply with the Android ecosystem's
 * way of interacting with Web Sockets.
 *
 */
abstract class SocketFactoryDelegate(private val factory: SocketFactory = SocketFactory.getDefault()) : SocketFactory() {

    /**
     * Configures the socket for usage. Most instances will want to tag the socket's thread
     */
    abstract fun configureSocket(socket: Socket): Socket

    override fun createSocket(host: String?, port: Int): Socket {
        return configureSocket(factory.createSocket(host, port))
    }

    override fun createSocket(host: String?, port: Int, localHost: InetAddress?, localPort: Int): Socket {
        return configureSocket(factory.createSocket(host, port, localHost, localPort))
    }

    override fun createSocket(host: InetAddress?, port: Int): Socket {
        return configureSocket(factory.createSocket(host, port))
    }

    override fun createSocket(address: InetAddress?, port: Int, localAddress: InetAddress?, localPort: Int): Socket {
        return configureSocket(factory.createSocket(address, port, localAddress, localPort))
    }
}