val ktlint by configurations.creating

dependencies {
    ktlint("com.pinterest:ktlint:0.46.1") {
        attributes {
            attribute(Bundling.BUNDLING_ATTRIBUTE, objects.named(Bundling.EXTERNAL))
        }
    }
}
val ktLint by tasks.creating(JavaExec::class) {
    description = "Check Kotlin code style."
    classpath = ktlint
    main = "com.pinterest.ktlint.Main"
    args = listOf("src/**/*.kt")
}
val ktlintCheckStyle by tasks.creating(JavaExec::class) {
    description = "Check Kotlin code style and save results with checkstyle format"
    classpath = ktlint
    main = "com.pinterest.ktlint.Main"
    args = listOf(
        "--reporter=plain",
        "--reporter=checkstyle,output=build/reports/ktlint/ktlint-results.xml",
        "src/**/*.kt",
        "!src/main/java/com/nc43tech/components/**"
    )
}
val ktlintFormat by tasks.creating(JavaExec::class) {
    description = "Fix Kotlin code style deviations."
    classpath = ktlint
    main = "com.pinterest.ktlint.Main"
    args = listOf("-F", "src/**/*.kt")
}
