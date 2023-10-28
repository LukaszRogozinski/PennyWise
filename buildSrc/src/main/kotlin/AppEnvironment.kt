enum class AppEnvironment(
    val id: String,
    val label: String?,
    val applicationIdSuffix: String
) {
    Dev(
        id = "dev",
        label = "Dev",
        applicationIdSuffix = ".dev"
    ),
    Prod(
        id = "prod",
        label = null,
        applicationIdSuffix = ".prod"
    );

    operator fun invoke() = id

    companion object {
        const val Dimension = "environment"
    }
}

