package com.TBN.steade

import com.TBN.steade.data.network.AuthResponse
import org.junit.Assert.*
import org.junit.Test

class AuthResponseTest {

    @Test
    fun `success returns true when token is present`() {
        val response = AuthResponse(token = "abc123")
        assertTrue(response.success)
    }

    @Test
    fun `success returns false when token is null`() {
        val response = AuthResponse(token = null)
        assertFalse(response.success)
    }

    @Test
    fun `success returns false when token is missing entirely`() {
        val response = AuthResponse(message = "Invalid credentials")
        assertFalse(response.success)
    }
}
