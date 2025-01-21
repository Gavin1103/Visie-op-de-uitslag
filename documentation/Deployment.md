# Deployment documentation

**Hosting:** Digital Ocean
<br>
**Domain:** visieopdeuitslag
<br>
**Frontend url:** https://www.visieopdeuitslag.nl
<br>
**Backend url:** https://www.visieopdeuitslag.nl/api
<br>
**Port:** 7420

###  **Network Diagram**
```mermaid
graph TD
    User -->|HTTPS| Frontend[Frontend Vue.js - visieopdeuitslag.nl]
    Frontend -->|HTTPS| Backend[Backend Spring Boot - visieopdeuitslag.nl/api]
    Backend -->|Database Connection| DB[Database]
    subgraph CI_CD[CI/CD Pipeline]
        A[Build Frontend] --> B[Build Backend]
        C[Deploy Frontend] --> D[Deploy Backend]
        A -->|Artifacts: dist| C
        B -->|Artifacts: JAR| D
    end
    C --> Frontend
    D --> Backend
```

---

### **Sequence Diagram**
```mermaid
sequenceDiagram
    participant User
    participant Frontend as Frontend (Vue.js)
    participant Backend as Backend (Spring Boot)
    participant Database as Database
    participant CI/CD as CI/CD Pipeline

    CI/CD->>Frontend: Deploy frontend artifacts (dist/)
    CI/CD->>Backend: Deploy backend artifacts (JAR)
    User->>Frontend: Access https://www.visieopdeuitslag.nl
    Frontend->>Backend: API Request (e.g., GET /api/topics)
    Backend->>Database: Query Topics
    Database-->>Backend: Topics Data
    Backend-->>Frontend: JSON Response
    Frontend-->>User: Rendered Topics
```



