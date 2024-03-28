object Dependencies {
    val coreKtx by lazy{"androidx.core:core-ktx:${Version.coreKtx}"}
    val appcompat by lazy{"androidx.appcompat:appcompat:${Version.appcompat}"}
    val material by lazy{"com.google.android.material:material:${Version.material}"}
    val lifecycleRuntimeKtx by lazy{"androidx.lifecycle:lifecycle-runtime-ktx:${Version.lifecycleRuntimeKtx}"}
    val activityCompose by lazy{"androidx.activity:activity-compose:${Version.activityCompose}"}
    val composeBom by lazy{"androidx.compose:compose-bom:${Version.compose}"}
    val composeUi by lazy{"androidx.compose.ui:ui:${Version.composeUi}"}
    val composeUiGraphics by lazy{"androidx.compose.ui:ui-graphics:${Version.composeUiGraphics}"}
    val composeUiToolingPreview by lazy{"androidx.compose.ui:ui-tooling:${Version.composeUiToolingPreview}"}
    val composeMaterial3 by lazy{"androidx.compose.material3:material3:${Version.composeMaterial3}"}
    val navigationCompose by lazy{"androidx.navigation:navigation-compose:${Version.navigationCompose}"}
    val composeUiTooling by lazy{"androidx.wear.compose:compose-ui-tooling:${Version.composeUiTooling}"}
    val composeTestJunit4  by lazy{"androidx.compose.ui:ui-test-junit4:${Version.composeTestJunit4}"}
    val composeUiTestManifest  by lazy{"androidx.compose.ui:ui-test-manifest:${Version.composeUiTestManifest}"}
    val lifecycleViewModelKtx  by lazy{"androidx.lifecycle:lifecycle-viewmodel-ktx:${Version.lifecycleViewModelKtx}"}


    val hiltAndroid  by lazy{"com.google.dagger:hilt-android:${Version.hilt}"}
    val hiltAndroidCompiler  by lazy{"com.google.dagger:hilt-android-compiler:${Version.hilt}"}
    val hiltCompiler  by lazy{"androidx.hilt:hilt-compiler:${Version.hiltCompiler}"}
    val hiltNavigationCompose by lazy {"androidx.hilt:hilt-navigation-compose:${Version.hiltNavigationCompose}" }

    val retrofit by lazy {"com.squareup.retrofit2:retrofit:${Version.retrofit}"}
    val okHttp by lazy {"com.squareup.okhttp3:okhttp:${Version.okHttp}"}
    val gsonConverter by lazy {"com.squareup.retrofit2:converter-gson:${Version.gsonConverter}"}
    val moshi by lazy {"com.squareup.moshi:moshi-kotlin:${Version.moshi}"}
    val moshiConverter by lazy {"com.squareup.retrofit2:converter-moshi:${Version.moshiConverter}"}
    val loggingInterceptor by lazy {"com.squareup.okhttp3:logging-interceptor:${Version.loggingInterceptor}"}
}

object Modules{
    const val utilities = ":utilities"
}