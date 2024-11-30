package banquemisr.challenge05.data.dataSource.local

import androidx.room.TypeConverter

class Converters {

    // Convert List<String> to a String (stored as a comma-separated list)
    @TypeConverter
    fun fromGenres(genres: List<String>?): String? {
        return genres?.joinToString(",") // Convert list to a comma-separated string
    }

    // Convert String (comma-separated list) back to List<String>
    @TypeConverter
    fun toGenres(genresString: String?): List<String>? {
        return genresString?.split(",")?.map { it.trim() } // Convert string back to list
    }
}
