package com.mendelin.catpediahilt

import com.mendelin.catpediahilt.data.remote.CatpediaApi
import com.mendelin.catpediahilt.data.remote.CatpediaRepository
import com.mendelin.catpediahilt.data.remote.model.BreedsListModel
import com.mendelin.catpediahilt.data.remote.model.ImageModel
import com.mendelin.catpediahilt.data.remote.model.WeightItem
import com.mendelin.catpediahilt.domain.usecase.*
import com.mendelin.catpediahilt.presentation.main.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

@ExperimentalCoroutinesApi
class ViewModelTest {
    private lateinit var viewModel: MainViewModel

    @Before
    fun setUp() = runBlocking {
        Dispatchers.setMain(StandardTestDispatcher())
        viewModel = MainViewModel(
            getBreedsListUseCase(),
            getBreedInfoUseCase(),
            getCreateBreedUseCase(),
            getGetBreedsUseCase(),
            getCreateBreedDetailsUseCase(),
            getGetBreedDetailsUseCase(),
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun testInitialState() = runBlocking {
        assertEquals(viewModel.isFailed.value, Pair(false, ""))
        assertEquals(viewModel.isLoading.value, false)
        assertEquals(viewModel.breedsList.value, listOf(getBreedsListModel()))
    }

    @Test
    fun testGetBreedsList() = runBlocking {
        viewModel.fetchBreedsList().test {

        }
    }

    private suspend fun getBreedsListUseCase(): BreedsListUseCase {
        val mockApi = Mockito.mock(CatpediaApi::class.java)

        Mockito.`when`(mockApi.getBreedsList())
            .thenReturn(listOf(getBreedsListModel()))

        val repo = CatpediaRepository(mockApi)
        return BreedsListUseCase(repo)
    }

    private suspend fun getBreedInfoUseCase(): BreedInfoUseCase {

    }

    private suspend fun getCreateBreedUseCase(): CreateBreedUseCase {

    }

    private suspend fun getGetBreedsUseCase(): GetBreedsUseCase {

    }

    private suspend fun getCreateBreedDetailsUseCase(): CreateBreedDetailsUseCase {

    }

    private suspend fun getGetBreedDetailsUseCase(): GetBreedDetailsUseCase {

    }


    private fun getBreedsListModel() =
        BreedsListModel(
            weight = WeightItem("2-3", "3-4"),
            id = "abys",
            name = "Abyssinian",
            cfa_url = "",
            vetstreet_url = "",
            vcahospitals_url = "",
            temperament = "",
            origin = "",
            country_codes = "",
            country_code = "",
            description = "",
            life_span = "",
            indoor = 0,
            lap = 1,
            alt_names = "",
            adaptability = 0,
            affection_level = 0,
            child_friendly = 0,
            dog_friendly = 0,
            energy_level = 0,
            grooming = 0,
            health_issues = 0,
            intelligence = 0,
            shedding_level = 0,
            social_needs = 0,
            stranger_friendly = 0,
            vocalisation = 0,
            experimental = 0,
            hairless = 0,
            natural = 0,
            rare = 0,
            rex = 0,
            suppressed_tail = 0,
            short_legs = 0,
            wikipedia_url = "",
            hypoallergenic = 0,
            reference_image_id = "",
            image = ImageModel("", 100, 100, "https:/cdm.a.com")
        )
}