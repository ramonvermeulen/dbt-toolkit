package com.github.ramonvermeulen.dbtToolkit.services

import com.intellij.testFramework.LightPlatformTestCase
import java.io.File
import java.io.FileNotFoundException
import java.lang.reflect.InvocationTargetException

class ProcessExecutorServiceDotEnvTest : LightPlatformTestCase() {
    private lateinit var env: MutableMap<String, String>
    private lateinit var dotenvFile: File

    public override fun setUp() {
        super.setUp()
        env = mutableMapOf()
        dotenvFile = File(javaClass.classLoader.getResource("dotenv/testCases.env")!!.file)
        val service = ProcessExecutorService(project)
        val method = service.javaClass.getDeclaredMethod("loadDotEnv", MutableMap::class.java, File::class.java)
        method.isAccessible = true
        method.invoke(service, env, dotenvFile)
    }

    fun `test basic environment variable`() {
        assertEquals("basic", env["BASIC"])
    }

    fun `test reads after a skipped line`() {
        assertEquals("after_line", env["AFTER_LINE"])
    }

    fun `test defaults empty values to empty string`() {
        assertEquals("", env["EMPTY"])
        assertEquals("", env["EMPTY_SINGLE_QUOTES"])
        assertEquals("", env["EMPTY_DOUBLE_QUOTES"])
        assertEquals("", env["EMPTY_BACKTICKS"])
    }

    fun `test escapes single quoted values`() {
        assertEquals("single_quotes", env["SINGLE_QUOTES"])
    }

    fun `test respects surrounding spaces in single quotes`() {
        assertEquals("    single quotes    ", env["SINGLE_QUOTES_SPACED"])
    }

    fun `test escapes double quoted values`() {
        assertEquals("double_quotes", env["DOUBLE_QUOTES"])
    }

    fun `test respects surrounding spaces in double quotes`() {
        assertEquals("    double quotes    ", env["DOUBLE_QUOTES_SPACED"])
    }

    fun `test respects double quotes inside single quotes`() {
        assertEquals("double \"quotes\" work inside single quotes", env["DOUBLE_QUOTES_INSIDE_SINGLE"])
    }

    fun `test respects spacing for badly formed brackets`() {
        assertEquals("{ port: \$MONGOLAB_PORT}", env["DOUBLE_QUOTES_WITH_NO_SPACE_BRACKET"])
    }

    fun `test respects single quotes inside double quotes`() {
        assertEquals("single 'quotes' work inside double quotes", env["SINGLE_QUOTES_INSIDE_DOUBLE"])
    }

    fun `test respects backticks inside single quotes`() {
        assertEquals("`backticks` work inside single quotes", env["BACKTICKS_INSIDE_SINGLE"])
    }

    fun `test respects backticks inside double quotes`() {
        assertEquals("`backticks` work inside double quotes", env["BACKTICKS_INSIDE_DOUBLE"])
    }

    fun `test respects double quotes inside backticks`() {
        assertEquals("double \"quotes\" work inside backticks", env["DOUBLE_QUOTES_INSIDE_BACKTICKS"])
    }

    fun `test respects single quotes inside backticks`() {
        assertEquals("single 'quotes' work inside backticks", env["SINGLE_QUOTES_INSIDE_BACKTICKS"])
    }

    fun `test respects double and single quotes inside backticks`() {
        assertEquals("double \"quotes\" and single 'quotes' work inside backticks", env["DOUBLE_AND_SINGLE_QUOTES_INSIDE_BACKTICKS"])
    }

    fun `test expands newlines but only if double quoted`() {
        assertEquals("expand\nnew\nlines", env["EXPAND_NEWLINES"])
    }

    fun `test does not expand newlines if unquoted`() {
        assertEquals("dontexpand\\nnewlines", env["DONT_EXPAND_UNQUOTED"])
    }

    fun `test does not expand newlines if single quoted`() {
        assertEquals("dontexpand\\nnewlines", env["DONT_EXPAND_SQUOTED"])
    }

    fun `test ignores commented lines`() {
        assertNull(env["COMMENTS"])
    }

    fun `test ignores inline comments`() {
        assertEquals("inline comments", env["INLINE_COMMENTS"])
    }

    fun `test ignores inline comments and respects # character inside of single quotes`() {
        assertEquals("inline comments outside of #singlequotes", env["INLINE_COMMENTS_SINGLE_QUOTES"])
    }

    fun `test ignores inline comments and respects # character inside of double quotes`() {
        assertEquals("inline comments outside of #doublequotes", env["INLINE_COMMENTS_DOUBLE_QUOTES"])
    }

    fun `test ignores inline comments and respects # character inside of backticks`() {
        assertEquals("inline comments outside of #backticks", env["INLINE_COMMENTS_BACKTICKS"])
    }

    fun `test treats # character as start of comment`() {
        assertEquals("inline comments start with a", env["INLINE_COMMENTS_SPACE"])
    }

    fun `test respects equals signs in values`() {
        assertEquals("equals==", env["EQUAL_SIGNS"])
    }

    fun `test retains inner quotes`() {
        assertEquals("{\"foo\": \"bar\"}", env["RETAIN_INNER_QUOTES"])
        assertEquals("{\"foo\": \"bar\"}", env["RETAIN_INNER_QUOTES_AS_STRING"])
        assertEquals("{\"foo\": \"bar's\"}", env["RETAIN_INNER_QUOTES_AS_BACKTICKS"])
    }

    fun `test retains spaces in string`() {
        assertEquals("some spaced out string", env["TRIM_SPACE_FROM_UNQUOTED"])
    }

    fun `test parses email addresses completely`() {
        assertEquals("therealnerdybeast@example.tld", env["USERNAME"])
    }

    fun `test parses keys and values surrounded by spaces`() {
        assertEquals("parsed", env["SPACED_KEY"])
    }

    fun `test parses buffer into an object`() {
        val payload = mutableMapOf<String, String>()
        val bufferFile = File.createTempFile("buffer", ".env")
        bufferFile.writeText("BUFFER=true")
        val service = ProcessExecutorService(project)
        val method = service.javaClass.getDeclaredMethod("loadDotEnv", MutableMap::class.java, File::class.java)
        method.isAccessible = true
        method.invoke(service, payload, bufferFile)
        assertEquals("true", payload["BUFFER"])
    }

    fun `test can parse line endings a`() {
        val expectedPayload = mapOf("SERVER" to "localhost", "PASSWORD" to "password", "DB" to "tests")
        val payload = mutableMapOf<String, String>()
        val bufferFile = File.createTempFile("buffer", ".env")
        bufferFile.writeText("SERVER=localhost\rPASSWORD=password\rDB=tests\r")
        val service = ProcessExecutorService(project)
        val method = service.javaClass.getDeclaredMethod("loadDotEnv", MutableMap::class.java, File::class.java)
        method.isAccessible = true
        method.invoke(service, payload, bufferFile)
        assertEquals(expectedPayload, payload)
    }

    fun `test can parse line endings b`() {
        val expectedPayload = mapOf("SERVER" to "localhost", "PASSWORD" to "password", "DB" to "tests")
        val payload = mutableMapOf<String, String>()
        val bufferFile = File.createTempFile("buffer", ".env")
        bufferFile.writeText("SERVER=localhost\nPASSWORD=password\nDB=tests\n")
        val service = ProcessExecutorService(project)
        val method = service.javaClass.getDeclaredMethod("loadDotEnv", MutableMap::class.java, File::class.java)
        method.isAccessible = true
        method.invoke(service, payload, bufferFile)
        assertEquals(expectedPayload, payload)
    }

    fun `test can parse line endings c`() {
        val expectedPayload = mapOf("SERVER" to "localhost", "PASSWORD" to "password", "DB" to "tests")
        val payload = mutableMapOf<String, String>()
        val bufferFile = File.createTempFile("buffer", ".env")
        bufferFile.writeText("SERVER=localhost\r\nPASSWORD=password\r\nDB=tests\r\n")
        val service = ProcessExecutorService(project)
        val method = service.javaClass.getDeclaredMethod("loadDotEnv", MutableMap::class.java, File::class.java)
        method.isAccessible = true
        method.invoke(service, payload, bufferFile)
        assertEquals(expectedPayload, payload)
    }

    fun `test non-existing dotenv file path`() {
        val service = ProcessExecutorService(project)
        val method = service.javaClass.getDeclaredMethod("loadDotEnv", MutableMap::class.java, File::class.java)
        method.isAccessible = true
        val env = mutableMapOf<String, String>()
        assertThrows(InvocationTargetException::class.java) {
            try {
                method.invoke(service, env, File("incorrect/path/to/.env"))
            } catch (e: InvocationTargetException) {
                if (e.cause is FileNotFoundException) {
                    throw e // test succeeds
                } else {
                    throw AssertionError("Expected FileNotFoundException, but got ${e.cause}")
                }
            }
        }
    }
}
