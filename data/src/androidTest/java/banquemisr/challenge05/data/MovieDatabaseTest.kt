package banquemisr.challenge05.data

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import banquemisr.challenge05.data.dataSource.local.MovieDatabase
import banquemisr.challenge05.data.dataSource.local.entities.MovieEntity
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MovieDatabaseTest {

    private lateinit var movieDatabase: MovieDatabase

    @Before
    fun setup() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        movieDatabase = Room.inMemoryDatabaseBuilder(context, MovieDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    }

    @After
    fun teardown() {
        movieDatabase.close()
    }


    @Test
    fun testInsertAndRetrieveMovies() = runBlocking {
        val movieDao = movieDatabase.movieListDao()
        val movieEntity = MovieEntity(id = 1, title = "Inception", page = 1)

        movieDao.insertMovies(listOf(movieEntity))
        val retrievedMovies = movieDao.getMoviesByPage(1)

        assertEquals(listOf(movieEntity), retrievedMovies)
    }
}
