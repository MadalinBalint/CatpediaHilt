package com.mendelin.catpediahilt

import app.cash.turbine.test
import com.mendelin.catpediahilt.data.local.OfflineRepository
import com.mendelin.catpediahilt.data.local.dao.CatsDao
import com.mendelin.catpediahilt.data.remote.CatpediaApi
import com.mendelin.catpediahilt.data.remote.CatpediaRepository
import com.mendelin.catpediahilt.data.remote.model.*
import com.mendelin.catpediahilt.domain.Resource
import com.mendelin.catpediahilt.domain.usecase.BreedsListUseCase
import com.mendelin.catpediahilt.domain.usecase.CreateBreedUseCase
import com.mendelin.catpediahilt.domain.usecase.GetBreedsUseCase
import com.mendelin.catpediahilt.presentation.breeds_list.BreedsUiState
import com.mendelin.catpediahilt.presentation.breeds_list.BreedsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import retrofit2.Response

@ExperimentalCoroutinesApi
class ViewModelTest {
    private lateinit var viewModel: BreedsViewModel

    @Before
    fun setUp() = runBlocking {
        Dispatchers.setMain(StandardTestDispatcher())
        viewModel = BreedsViewModel(
            getBreedsListUseCase(),
            getCreateBreedUseCase(),
            getGetBreedsUseCase(),
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun testInitialState() = runBlocking {
        assertEquals(viewModel.viewState.value,
            BreedsUiState(
                isLoading = false,
                breeds = emptyList(),
                isFailed = Pair(false, ""),
                isOffline = false
            )
        )
    }

   /* @Test
    fun testOfflineGetBreedsList() = runBlocking {
        viewModel.createOfflineBreedsList(newBreedsListModel().toBreed()).test {
            val loading = awaitItem()
            assert(loading, Resource.Loading)
            assertNull(loading.data)
            assertNull(loading.message)

            val success = awaitItem()
            assert(success is Resource.Success)
            assertNull(success.data)
            assertNull(success.message)

            cancelAndConsumeRemainingEvents()
        }
    }*/

    private suspend fun getBreedsListUseCase(): BreedsListUseCase {
        val mockApi = Mockito.mock(CatpediaApi::class.java)

        Mockito.`when`(mockApi.getBreedsList())
            .thenReturn(Response.success(listOf(newBreedsListModel())))

        val repo = CatpediaRepository(mockApi)
        return BreedsListUseCase(repo)
    }

    private suspend fun getCreateBreedUseCase(): CreateBreedUseCase {
        val mockApi = Mockito.mock(CatsDao::class.java)

        Mockito.`when`(mockApi.insertBreed(newBreed()))
            .thenReturn(Unit)

        val repo = OfflineRepository(mockApi)
        return CreateBreedUseCase(repo)
    }

    private suspend fun getGetBreedsUseCase(): GetBreedsUseCase {
        val mockApi = Mockito.mock(CatsDao::class.java)

        Mockito.`when`(mockApi.getBreedsList())
            .thenReturn(listOf(newBreedEntity()))

        val repo = OfflineRepository(mockApi)
        return GetBreedsUseCase(repo)
    }

    private fun newBreedsListModel() =
        BreedsListModel(
            weight = WeightModel("2-3", "3-4"),
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

    private fun newBreedEntity() = newBreedsListModel().toEntity()
    private fun newBreed() = newBreedsListModel().toBreed()
}