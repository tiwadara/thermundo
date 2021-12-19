package com.tiwa.thermondo.constant

import com.tiwa.thermondo.data.constant.Constants
import org.junit.Assert.*
import org.junit.Test


class ConstantsTest {
    @Test
    fun string_values_are_as_expected() {
        assertEquals( "Couldn't reach the server. Check your internet connection", Constants.NO_INTERNET_ERROR)
        assertEquals("An unknown error occurred", Constants.ERROR_MESSAGE)
        assertEquals("https://api.shrtco.de/v2/", Constants.BASE_URL)
        assertEquals("short_links_table", Constants.SHORT_LINK_TABLE)
        assertEquals("short_links_database.db", Constants.DATABASE_NAME)
    }
}