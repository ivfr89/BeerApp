object VersionName {

    private const val DEFAULT_LOCAL_VERSION_NAME = "1.0.0"
    private const val DEFAULT_LOCAL_VERSION_SUFFIX = ""

    fun getCustomVersionName(
        referenceVersionName: String = DEFAULT_LOCAL_VERSION_NAME,
        referenceVersionSuffix: String = DEFAULT_LOCAL_VERSION_SUFFIX
    ): String {
        val finalReferenceVersionName: String = referenceVersionName
        val finalReferenceVersionSuffix: String = referenceVersionSuffix
        val separator = "_"

        var finalVersionName = finalReferenceVersionName

        if (!finalReferenceVersionSuffix.isNullOrEmpty())
            finalVersionName = finalVersionName + separator + finalReferenceVersionSuffix

        return finalVersionName
    }
}
