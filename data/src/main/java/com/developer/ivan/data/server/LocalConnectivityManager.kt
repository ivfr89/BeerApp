package com.developer.ivan.data.server

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.developer.ivan.ConnectionManager

class LocalConnectivityManager(val app: Application) : ConnectionManager {

  override fun isConnected(): Boolean {
    val cm = app.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    cm?.run {
      cm.getNetworkCapabilities(cm.activeNetwork)?.run {
        return when {
          hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
          hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
          hasTransport(NetworkCapabilities.TRANSPORT_VPN) -> true
          hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
          hasTransport(NetworkCapabilities.TRANSPORT_WIFI_AWARE) -> true
          else -> false
        }
      }
    }
    return false
  }
}
