package com.mendelin.catpediahilt

import com.mendelin.catpediahilt.data.local.dao.BreedEntity
import com.mendelin.catpediahilt.data.local.dao.toBreed
import org.junit.Assert.assertEquals
import org.junit.Test

class BreedEntityTest {
    @Test
    fun testBreedEntity() {
        val breedEntity = BreedEntity("abys", "Abyssinian", "Egypt", "https://cdn2.thecatapi.com/images/xnzzM6MBI.jpg")

        assertEquals(breedEntity.id, "abys")
        assertEquals(breedEntity.name, "Abyssinian")
        assertEquals(breedEntity.country, "Egypt")
        assertEquals(breedEntity.imageUrl, "https://cdn2.thecatapi.com/images/xnzzM6MBI.jpg")

        val breed = breedEntity.toBreed()
        assertEquals(breed.id, "abys")
        assertEquals(breed.name, "Abyssinian")
        assertEquals(breed.country, "Egypt")
        assertEquals(breed.imageUrl, "https://cdn2.thecatapi.com/images/xnzzM6MBI.jpg")
    }
}