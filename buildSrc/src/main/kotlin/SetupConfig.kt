object SetupConfig {
    const val compileSdkVersion = 33
    const val minSdkVersion = 23
    const val targetSdkVersion = 33
    const val applicationId = "com.developer.ivan.beerapp"
    val versionCode: Int
        get() = (System.currentTimeMillis() / 1000).toInt()
}
