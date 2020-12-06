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
    val libs = mutableMapOf<String, MutableMap<String, MutableList<String>>>()
    fun plugin(group:String):String = (libs[group] ?: error(""))[gradle]!!.first()

    fun allLibs(excludeGroup: List<String> = emptyList()): MutableList<Pair<String, String>> {
        val allLibs = mutableListOf<Pair<String, String>>()

        libs.filter { !excludeGroup.contains(it.key) }.forEach { d ->
            d.value.filter { it.key != "gradle" }.forEach { (type, libs) ->
                libs.forEach { lib ->
                    allLibs.add(Pair(type, lib))

                    println("${if(type == "impl") "implementation" else "kapt"}(\"${lib}\")")
                }
            }
        }

        return allLibs
    }

    private const val gradle = "gradle"
    private const val impl = "impl"
    private const val kapt = "kapt"

    init {
        libs["Kotlin"] = hashMapOf(
            impl to mutableListOf(
            "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}")
        )

        libs["Basic"] = hashMapOf(
            impl to mutableListOf(
            "com.google.code.gson:gson:2.8.6",
            "androidx.core:core-ktx:1.5.0-alpha05",
            "androidx.appcompat:appcompat:1.3.0-alpha02",
            "androidx.constraintlayout:constraintlayout:2.0.4",
            "androidx.legacy:legacy-support-v4:1.0.0",
            "com.google.android.material:material:1.2.1",
            "androidx.ui:ui-tooling:1.0.0-alpha07")
        )

        val navigation = "2.3.2"
        libs["Navigation"] = hashMapOf(
            gradle to mutableListOf(
            "androidx.navigation:navigation-safe-args-gradle-plugin:${navigation}"),

            impl to mutableListOf(
            "androidx.navigation:navigation-fragment-ktx:${navigation}",
            "androidx.navigation:navigation-ui-ktx:${navigation}")
        )

        val compose = "1.0.0-alpha08"
        libs["Compose"] = hashMapOf(
            impl to mutableListOf(
            "androidx.compose.ui:ui:${compose}",
            "androidx.compose.animation:animation:${compose}",
            "androidx.compose.ui:ui-tooling:${compose}",

            "androidx.compose.foundation:foundation:${compose}",
            "androidx.compose.foundation:foundation-layout:${compose}",

            "androidx.compose.material:material:${compose}",
            "androidx.compose.material:material-icons-core:${compose}",
            "androidx.compose.material:material-icons-extended:${compose}",

            "androidx.compose.runtime:runtime:${compose}",
            "androidx.compose.runtime:runtime-livedata:${compose}",
            "androidx.compose.runtime:runtime-rxjava3:${compose}")
        )
    }

    object Test {
        const val junit = "junit:junit:4.13.1"
        const val extJunit = "androidx.test.ext:junit:1.1.2"
        const val espressoCore = "androidx.test.espresso:espresso-core:3.3.0"
    }
}