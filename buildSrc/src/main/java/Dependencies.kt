import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

object Sdk {
    const val compile = 30
    const val target = 30
    const val min = 24

    const val buildTool = "30.0.2"
    const val ndk = "22.0.6917172 rc1"
}

object Dep {
    fun Project.addDependencies(configName: String = "implementation") {
        dependencies {
            allLibs().forEach { (type, lib) -> if(type == "impl") add(configName, lib) else kapt(lib) }

            add("testImplementation", Test.junit)
            add("androidTestImplementation", Test.extJunit)
            add("androidTestImplementation", Test.espressoCore)
        }
    }

    private val libs = mutableMapOf<String, MutableMap<String, MutableList<String>>>()
    fun plugin(group:String):String = (libs[group] ?: error(""))[plugin]!!.first()

    private fun allLibs(excludeGroup: List<String> = emptyList()): MutableList<Pair<String, String>> {
        val allLibs = mutableListOf<Pair<String, String>>()

        libs.filter { !excludeGroup.contains(it.key) }.forEach { d ->
            d.value.filter { it.key != "plugin" }.forEach { (type, libs) ->
                libs.forEach { lib ->
                    allLibs.add(Pair(type, lib))
                    //println("${if(type == "impl") "implementation" else "kapt"}(\"${lib}\")")
                }
            }
        }

        return allLibs
    }

    private const val plugin = "plugin"
    private const val impl = "impl"
    private const val kapt = "kapt"

    const val kotlin = "1.4.20"
    const val compose_snap = "1.0.0-SNAPSHOT"
    const val compose_alpha = "1.0.0-alpha08"

    init {
        libs["Kotlin"] = hashMapOf(
            plugin to mutableListOf(
            "org.jetbrains.kotlin:kotlin-gradle-plugin:${kotlin}"),

            impl to mutableListOf(
            "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${kotlin}")
        )

        val gradle = "7.0.0-alpha03"
        libs["Gradle"] = hashMapOf(
            plugin to mutableListOf(
            "com.android.tools.build:gradle:${gradle}")
        )

        libs["Basic"] = hashMapOf(
            impl to mutableListOf(
            "com.google.code.gson:gson:2.8.6",
            "com.google.android.material:material:1.2.1",

            // AndroidX
            "androidx.core:core-ktx:1.5.0-alpha05",
            "androidx.appcompat:appcompat:1.3.0-alpha02",
            "androidx.constraintlayout:constraintlayout:2.0.4",
            "androidx.legacy:legacy-support-v4:1.0.0",
            "androidx.activity:activity-ktx:1.2.0-beta02",
            "androidx.ui:ui-tooling:1.0.0-alpha07")
        )

        val navigation = "2.3.2"
        libs["Navigation"] = hashMapOf(
            plugin to mutableListOf(
            "androidx.navigation:navigation-safe-args-gradle-plugin:${navigation}"),

            impl to mutableListOf(
            "androidx.navigation:navigation-fragment-ktx:${navigation}",
            "androidx.navigation:navigation-ui-ktx:${navigation}",

            // Navigation Compose
            "androidx.navigation:navigation-compose:1.0.0-alpha03")
        )

        libs["Compose"] = hashMapOf(
            impl to mutableListOf(
            "androidx.compose.ui:ui:${compose_snap}",
            "androidx.compose.animation:animation:${compose_alpha}",
            "androidx.compose.ui:ui-tooling:${compose_alpha}",

            "androidx.compose.foundation:foundation:${compose_alpha}",
            "androidx.compose.foundation:foundation-layout:${compose_snap}",

            "androidx.compose.material:material:${compose_alpha}",
            "androidx.compose.material:material-icons-core:${compose_snap}",
            "androidx.compose.material:material-icons-extended:1.0.0-alpha08",

            "androidx.compose.runtime:runtime:${compose_snap}",
            "androidx.compose.runtime:runtime-livedata:${compose_alpha}",
            "androidx.compose.runtime:runtime-rxjava3:1.0.0-alpha08")
        )
    }

    object Test {
        const val junit = "junit:junit:4.13.1"
        const val extJunit = "androidx.test.ext:junit:1.1.2"
        const val espressoCore = "androidx.test.espresso:espresso-core:3.3.0"
    }
}