import com.basebox.summarize.AIResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import org.koin.core.module.Module
import org.koin.dsl.module

class AIService {
    private val client = HttpClient(CIO)
    private val apiKey = "nxQ4DGBw0ChAwYzU59Pc6qa6FwflHY6h"//EnvVar.apiKey
    private val json = Json { ignoreUnknownKeys = true }

    suspend fun getSummary(text: String): String? {

        return withContext(Dispatchers.IO) {

        val requestBody = """
        {
            "model": "mistral-tiny",
            "messages": [
                {"role": "system", "content": "You are an AI assistant."},
                {"role": "user", "content": "$text"}
            ]
        }
        """.trimIndent()

            val response: HttpResponse = client.post("https://api.mistral.ai/v1/chat/completions") {
                headers {
                    append(HttpHeaders.Authorization, "Bearer $apiKey")
                    append(HttpHeaders.ContentType, "application/json")
                    append(HttpHeaders.Accept, "application/json")
                }
                setBody(requestBody)
            }

            if (response.status == HttpStatusCode.OK) {
                val jsonResponse: AIResponse =
                    json.decodeFromString<AIResponse>(response.body<String>())
                jsonResponse.choices.firstOrNull()?.message?.content?.trim()
            } else {
                null
            }
        }
    }
}


// Koin Module for Dependency Injection
val appModule: Module = module {
    single { AIService() }
}
