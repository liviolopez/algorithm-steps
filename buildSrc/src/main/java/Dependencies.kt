object Sdk {
    const val compile = 30
    const val target = 30
    const val min = 22

    const val buildTool = "30.0.2"
    const val ndk = "22.0.6917172 rc1"
}

object Versions {
    const val kotlin = "1.4.20"
    const val gradle = "7.0.0-alpha02"
}

object Dep {
    fun get(group:String, type:String, name:String):String = (libs[group] ?: error(""))[type]!![name] ?: error("")

    fun allLibs(excludeGroup: List<String> = emptyList()): MutableList<Pair<String, String>> {
        val allLibs = mutableListOf<Pair<String, String>>()

        libs.filter { !excludeGroup.contains(it.key) }.forEach { d ->
            d.value.filter { it.key != "gradle" }.forEach { (type, libs) ->
                libs.forEach { lib ->
                    allLibs.add(Pair(type, lib.value))
                    println("${if(type == "impl") "implementation" else "kapt"}(\"${lib.value}\")")
                }
            }
        }

        return allLibs
    }

    private const val gradle = "gradle"
    private const val impl = "impl"
    private const val kapt = "kapt"

    private object V {
        const val navigation = "2.3.2"
        const val compose = "1.0.0-alpha08"
    }

    private val libs = mapOf(
        "Kotlin" to hashMapOf(
            impl to mapOf(
            "kotlin" to "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}")
        ),

        "Basic" to hashMapOf(
            impl to mapOf(
            "gson" to "com.google.code.gson:gson:2.8.6",
            "coreKtx" to "androidx.core:core-ktx:1.3.2",
            "appcompat" to "androidx.appcompat:appcompat:1.2.0",
            "constraintlayout" to "androidx.constraintlayout:constraintlayout:2.0.4",
            "legacy" to "androidx.legacy:legacy-support-v4:1.0.0",
            "material" to "com.google.android.material:material:1.2.1")
        ),

        "Navigation" to mapOf(
            gradle to mapOf(
            "plugin" to "androidx.navigation:navigation-safe-args-gradle-plugin:${V.navigation}"),

            impl to mapOf(
            "navigation" to "androidx.navigation:navigation-fragment-ktx:${V.navigation}",
            "navigationUi" to "androidx.navigation:navigation-ui-ktx:${V.navigation}")
        ),

        "Compose" to mapOf(
            impl to mapOf(
            "ui" to "androidx.compose.ui:ui:${V.compose}",
            "foundation" to "androidx.compose.foundation:foundation:${V.compose}",
            "material" to "androidx.compose.material:material:${V.compose}",
            "tooling" to "androidx.compose.ui:ui-tooling:${V.compose}",
            "livedata" to "androidx.compose.runtime:runtime-livedata:${V.compose}",
            "rxjava3" to "androidx.compose.runtime:runtime-rxjava3:${V.compose}")
        )
    )

    object Test {
        const val junit = "junit:junit:4.13.1"
        const val extJunit = "androidx.test.ext:junit:1.1.2"
        const val espressoCore = "androidx.test.espresso:espresso-core:3.3.0"
    }
}