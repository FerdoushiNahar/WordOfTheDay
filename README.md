# Word of the Day — Spring Boot REST API

Build a Spring Boot REST application that serves a **Word of the Day** with its definitions.  
It caches external API results for 24 hours and exposes clean endpoints.

## ✅ Functional Requirements

- **Endpoint:** `GET /wordOfTheDay` — returns today’s word and its definitions.
- **External APIs:**
    - **Random Word API:** https://random-word-api.herokuapp.com/word  
      Called once daily to fetch a random word (e.g., `unlaced`). Cache for 1 day.
    - **WordsAPI:** https://dictionaryapi.dev/ 
      Use the random word above to fetch its definitions. Also cached for 1 day.
- **Optional:** Keep a **history** of daily words (track previous words by date).

### Example Response

```json
{
  "word": "unlaced",
  "definitions": [
    {
      "definition": "not under constraint in action or expression",
      "partOfSpeech": "adjective"
    },
    {
      "definition": "with laces not tied",
      "partOfSpeech": "adjective"
    }
  ],
  "date": "2025-09-06"
}
# Project TODOs

## v1

### Required

#### Functional
- **Expose Word of the Day** at an endpoint  
  - Return a **unique word of the recent day** with its definition.  
  - **Cache the word for the entire day** to avoid unnecessary external calls.  

#### Externals
- (TBD: Define external API for fetching word definitions)

#### Non-Functional
- **Documentation**  
  - `README.md` explaining setup & usage  
  - API Documentation using **Swagger/OpenAPI**  
  - JavaDoc for classes & methods  

- **Testing**  
  - Unit Tests (**mandatory**)  
  - Integration Tests (**optional**)  

- **Code Quality**  
  - Follow **clean code principles**  
  - Use proper **naming conventions**  
  - Maintain an **organized project structure**  

---

## v2

### Package Structure

#### controller
- `WordOfTheDayController`

#### config
- `RestConfig.java`

#### exception
- `GlobalExceptionHandler`

#### model
- **domain** (core domain models)
- **dto**
  - `DefinitionResponse.java`

#### persistence
- **entity**
  - `DailyDefinitionCache.java`
  - `SavedWord.java`
- **repository**
  - `SavedWordRepository.java`
  - `DailyDefinitionCacheRepository.java`

#### service
- `DefinitionService.java`
- `WordService.java`

---

