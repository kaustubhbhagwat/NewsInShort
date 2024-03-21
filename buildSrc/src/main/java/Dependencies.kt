object Dependencies {
    val coreKtx by lazy{"androidx.core:core-ktx:${Version.coreKtx}"}
    val appcompat by lazy{"androidx.appcompat:appcompat:${Version.appcompat}"}
    val material by lazy{"com.google.android.material:material:${Version.material}"}
    val lifecycleRuntimeKtx by lazy{"androidx.lifecycle:lifecycle-runtime-ktx:${Version.lifecycleRuntimeKtx}"}
    val activityCompose by lazy{"androidx.activity:activity-compose:${Version.activityCompose}"}
    val composeBom by lazy{"androidx.compose:compose-bom:${Version.activityCompose}"}
    val composeUi by lazy{"androidx.compose.ui:ui:${Version.composeUi}"}
    val composeUiGraphics by lazy{"androidx.compose.ui:ui-graphics:${Version.composeUiGraphics}"}
    val composeUiToolingPreview by lazy{"androidx.compose.ui:ui-tooling:${Version.composeUiToolingPreview}"}
    val composeMaterial3 by lazy{"androidx.compose.material3:material3:${Version.composeMaterial3}"}
    val navigationCompose by lazy{"androidx.navigation:navigation-compose:${Version.navigationCompose}"}
    val composeUiTooling by lazy{"androidx.wear.compose:compose-ui-tooling:${Version.composeUiTooling}"}
    val composeTestJunit4  by lazy{"androidx.compose.ui:ui-test-junit4:${Version.composeTestJunit4}"}
    val composeUiTestManifest  by lazy{"androidx.compose.ui:ui-test-manifest:${Version.composeUiTestManifest}"}
    val lifecycleViewModelKtx  by lazy{"androidx.lifecycle:lifecycle-viewmodel-ktx:${Version.lifecycleViewModelKtx}"}
}

object Modules{
    const val utilities = ":utilities"
}