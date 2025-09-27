package utils

import android.content.Context
import android.net.wifi.WifiManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.InetAddress
import java.net.NetworkInterface
import java.util.*

object IpDiscovery {

    /**
     * Get the device's current IP address on WiFi network
     */
    fun getCurrentDeviceIp(context: Context): String? {
        return try {
            val wifiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
            val wifiInfo = wifiManager.connectionInfo
            val ipInt = wifiInfo.ipAddress

            if (ipInt != 0) {
                String.format(
                    Locale.getDefault(),
                    "%d.%d.%d.%d",
                    ipInt and 0xff,
                    ipInt shr 8 and 0xff,
                    ipInt shr 16 and 0xff,
                    ipInt shr 24 and 0xff
                )
            } else {
                getIpAddressFromNetworkInterface()
            }
        } catch (e: Exception) {
            getIpAddressFromNetworkInterface()
        }
    }

    /**
     * Alternative method to get IP address using NetworkInterface
     */
    private fun getIpAddressFromNetworkInterface(): String? {
        try {
            val interfaces = NetworkInterface.getNetworkInterfaces()
            while (interfaces.hasMoreElements()) {
                val networkInterface = interfaces.nextElement()
                val addresses = networkInterface.inetAddresses
                while (addresses.hasMoreElements()) {
                    val address = addresses.nextElement()
                    if (!address.isLoopbackAddress && address is InetAddress &&
                        !address.isLinkLocalAddress && address.hostAddress?.contains(":") == false) {
                        return address.hostAddress
                    }
                }
            }
        } catch (e: Exception) {
            // Ignore
        }
        return null
    }

    /**
     * Get network subnet for IP scanning
     * Returns something like "192.168.1" for scanning 192.168.1.1-254
     */
    fun getNetworkSubnet(context: Context): String? {
        val currentIp = getCurrentDeviceIp(context)
        return currentIp?.let { ip ->
            val parts = ip.split(".")
            if (parts.size >= 3) {
                "${parts[0]}.${parts[1]}.${parts[2]}"
            } else null
        }
    }

    /**
     * Generate common IP suggestions based on current network
     */
    fun getCommonIpSuggestions(context: Context): List<String> {
        val suggestions = mutableListOf<String>()
        val subnet = getNetworkSubnet(context)

        if (subnet != null) {
            // Common router/server IPs
            suggestions.addAll(listOf(
                "$subnet.1:8080",
                "$subnet.2:8080",
                "$subnet.3:8080",
                "$subnet.4:8080",
                "$subnet.5:8080",
                "$subnet.6:8080",
                "$subnet.100:8080",
                "$subnet.101:8080",
                "$subnet.254:8080"
            ))
        }

        return suggestions.distinct()
    }

}