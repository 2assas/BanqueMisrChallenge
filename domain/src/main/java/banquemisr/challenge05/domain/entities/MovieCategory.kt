package banquemisr.challenge05.domain.entities

enum class MovieCategory(val apiPath: String) {
    NOW_PLAYING("now_playing"),
    POPULAR("popular"),
    UPCOMING("upcoming")
}