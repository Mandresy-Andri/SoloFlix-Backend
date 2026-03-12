# SoloFlix — CEO Interview Presentation Speech

> **Context**: You're presenting to the CEO of the Lansdale Group for an Entry Level Salesforce Developer position. This speech is designed to be **~8–10 minutes** and maps every talking point to the exact skills they're looking for.

> [!TIP]
> **Delivery tips**: Speak confidently but not arrogantly. You built this from scratch — own it. Pause after key points. If you have a laptop, have the live app and your code open side by side.

---

## 🎬 Opening (30 seconds)

> "Thank you for the opportunity. I'd love to walk you through a full-stack project I built independently called **SoloFlix** — a Netflix-style streaming application. I chose this project to show you because it touches on the exact skills this role requires: **Java backend development, JavaScript frontend work, API integration with third-party services, relational database design, and the ability to define clean system architecture** — all things I'll be doing day one as a Salesforce developer here at Lansdale Group."

---

## 🏗️ The Big Picture — Architecture (1.5 minutes)

> [!IMPORTANT]
> **What they care about**: *"Define appropriate system architecture and solution design for a given problem."* — This is where you prove you think in systems, not just code.

> "Let me start with the architecture. SoloFlix is built on a **three-tier architecture**:
>
> 1. A **React frontend** — the user-facing single-page application
> 2. A **Spring Boot backend** — the API server that handles business logic
> 3. A **MySQL database** — the persistence layer
>
> On top of that, the backend integrates with **two external APIs**: the TMDB movie database for real-time movie data, and YouTube for streaming trailers.
>
> I also designed — and at one point had running in production — a **separate OAuth2 authorization server** for token-based authentication. I disabled it to save hosting costs, but the architecture is still in the codebase and I can walk you through it.
>
> **Why this matters for Lansdale Group**: This is the same pattern you see in Salesforce projects — you've got the Salesforce platform itself, integrated external systems, and APIs connecting them. I've already built exactly that kind of multi-system architecture."

---

## ☕ The Backend — Java & API Design (3 minutes)

> [!IMPORTANT]
> **What they care about**: *"Programming skills, ideally both Java and Javascript"* and *"Design, develop, and test custom code solutions that integrate 3rd party solutions via API."*

### Spring Boot & Layered Design

> "The backend is written in **Java** using **Spring Boot**. I followed a **layered architecture** — the same pattern used in enterprise Salesforce development:
>
> - **Controllers** handle incoming HTTP requests — similar to how you'd set up REST endpoints in Salesforce
> - A **Service Layer** contains all the business logic
> - **Repositories** handle database operations through Spring Data JPA
>
> This separation of concerns means each layer is testable, maintainable, and easy to extend — which is critical when you're working with clients who have evolving requirements."

### Dual API: REST + GraphQL

> "What's unique about this project is that I implemented **two different API paradigms**:
>
> - **REST endpoints** for administrative operations — like seeding the database with movie data from TMDB
> - **GraphQL** for all client-facing queries — so the frontend can request exactly the data it needs, nothing more
>
> I wrote **7 GraphQL schema files** using a schema-first approach with the `extend type Query` pattern, and implemented **Query and Mutation resolvers** in Java that map to those schemas.
>
> **Why this matters**: In Salesforce, you work with REST APIs, SOAP APIs, and the GraphQL API. I've already built with both REST and GraphQL, so I understand how to pick the right tool and implement it cleanly."

### Third-Party API Integration (TMDB)

> "The most technically interesting part of the backend is the **TMDB integration**. When you hit the POST endpoint, here's what happens under the hood:
>
> 1. The Service Layer calls the TMDB Movies API using **Spring WebFlux's WebClient** — a reactive, non-blocking HTTP client
> 2. For **each movie** returned, it makes a second call to the TMDB Videos API to fetch the YouTube trailer key
> 3. It enriches the movie object with the full YouTube URL
> 4. It persists everything to MySQL
>
> I chose **WebClient over RestTemplate** because WebClient is non-blocking — it doesn't hold a thread while waiting for TMDB to respond. This is the modern approach in Spring, and it matters when you're making 20+ API calls in a loop.
>
> I also built in **error handling** for HTTP 429 (rate limiting), 4xx client errors, and 5xx server errors — because when you're integrating with external APIs, you have to anticipate failure gracefully.
>
> **The parallel to Salesforce**: This is exactly what you do when integrating third-party solutions with Salesforce — making callouts, handling responses, transforming data, and storing it. I've done all of that here."

---

## 🗄️ Database Design & SQL (1 minute)

> [!IMPORTANT]
> **What they care about**: *"Familiarity with SQL"* and defining solution design.

> "For the database, I used **MySQL** with **Spring Data JPA** as the ORM. The schema has a normalized design:
>
> - A core **Movie** table with all the TMDB fields mapped via Jackson annotations
> - **Category join tables** — Popular, Trending, Top Rated, Romance — each with a Many-to-One relationship to Movie. This means a single movie can appear in multiple categories
> - A **Users** table with email as the primary key
> - A **MyList** table that acts as a **many-to-many join** between Users and Movies — for personal watchlists
>
> I also wrote custom repository queries like [findByTitleContainingIgnoreCase](file:///c:/Users/andri/Documents/Code/Codes/Streaming-app/streaming-webapp-project/src/main/java/com/company/streamingwebappproject/repository/MovieRepository.java#14-15) for search and [findByUserEmail](file:///c:/Users/andri/Documents/Code/Codes/Streaming-app/streaming-webapp-project/src/main/java/com/company/streamingwebappproject/repository/MyListRepository.java#17-18) for user-specific lists — Spring Data generates the SQL automatically from the method names.
>
> **The parallel to Salesforce**: This relational thinking maps directly to Salesforce's object model — standard objects, custom objects, lookup and master-detail relationships, and SOQL queries."

---

## ⚛️ The Frontend — JavaScript & Web Design (1.5 minutes)

> [!IMPORTANT]
> **What they care about**: *"Javascript"*, *"Web Design skills"*, and *"Lightning Web Components"* (React and LWC share the same component-based paradigm).

> "The frontend is a **React 18** single-page application. Let me highlight a few things:
>
> **Component architecture** — I built reusable, prop-driven components. For example, my [MovieCarousel](file:///C:/Users/andri/Documents/Code/Codes/Streaming%20app%20frontend/streaming-app-frontend/src/components/MovieCarousel.js#10-115) component takes a single prop — the category name — and dynamically constructs a GraphQL query from it. One component handles all four categories with zero code duplication. This is the same component-based thinking you'd use with **Lightning Web Components** in Salesforce.
>
> **YouTube IFrame API integration** — The hero section embeds a full-screen YouTube trailer. I integrated directly with the YouTube IFrame API, loading the script dynamically and using an **IntersectionObserver** to auto-play when visible and pause when scrolled away. That's a native browser API — showing I work at a deeper level than just libraries.
>
> **UI Libraries** — I used **Ant Design** for forms and modals, **React Bootstrap** for the navigation bar, and **Slick Carousel** for the movie sliders. Knowing how to evaluate, adopt, and combine different UI libraries efficiently is a real-world skill.
>
> **Client-side routing and authentication** — React Router v6 handles navigation, and I built route guards that check for authentication tokens in localStorage before rendering protected pages.
>
> **The Salesforce parallel**: Lightning Web Components are fundamentally React-style — they're JavaScript components with props, lifecycle hooks, and reactive state. The mental model is the same. I'll ramp up on LWC very quickly."

---

## 🔐 Authentication Architecture (1 minute)

> "Even though the auth server is currently disabled, I want to walk through the design because it shows I understand **security architecture**:
>
> I implemented the **OAuth2 Password Grant** flow:
> 1. The frontend sends credentials to a separate **Authorization Server** I hosted on Railway
> 2. The auth server returns a **JWT access token**
> 3. The frontend stores it and sends it as a **Bearer token** on every API call
> 4. The Spring Boot backend, configured as a **Resource Server**, validates the token before processing the request
>
> I also wrote a custom **CORS filter** with highest priority ordering to handle cross-origin requests for the OAuth endpoints.
>
> I disabled this to save on hosting costs since I already have another project demonstrating it, but the architecture, the commented code, and the configurations are all there."

---

## 📊 What I'd Improve — Self-Awareness (1 minute)

> [!TIP]
> **Why include this**: CEOs love self-awareness. It shows maturity and growth mindset — which matters more than perfection for an entry-level role.

> "I want to be honest about what I'd improve if I were to keep iterating on this:
>
> - **Security**: Move token storage from `localStorage` to `httpOnly` cookies to prevent XSS attacks
> - **Error handling on the frontend**: Right now errors go to `console.error` — in production, I'd add user-facing error states and retry logic
> - **Testing**: I'd add unit tests on the backend with JUnit and Mockito, and integration tests for the API layer
> - **Performance**: Add pagination to the GraphQL queries instead of returning all results
> - **CI/CD**: Set up automated builds and deployments with Docker containers
>
> These are the kinds of improvements I'd be working on in a professional environment — things like code quality, test coverage, and deployment automation."

---

## 🎯 Closing — Connecting to Lansdale Group (45 seconds)

> "So to bring it full circle — here's why this project is relevant to the work at Lansdale Group:
>
> - You need developers who can **design architecture** — I architected a three-tier system with external API integration from scratch
> - You need people who can **write Java and JavaScript** — I've built with both, backend and frontend
> - You need developers who can **integrate third-party solutions via API** — I integrated TMDB and YouTube APIs with error handling and data transformation
> - You need people who can **work with databases and SQL** — I designed a normalized relational schema with JPA
> - You need developers who can **learn new platforms quickly** — I taught myself Spring Boot, GraphQL, WebFlux, and React for this project. I'm confident I can ramp up on **Apex, Lightning Web Components, and the Salesforce API** with the same approach
>
> I'm genuinely excited about the opportunity to grow under Lansdale Group's team of architects. I learn fast, I build things end-to-end, and I'm ready to apply that to solving real business problems on the Salesforce platform.
>
> Thank you."

---

## 📋 Quick Cheat Sheet — If They Ask Follow-ups

| If they ask about… | Key phrase to use |
|---|---|
| **Apex** | *"Apex is Java-like — I'm already fluent in Java with Spring Boot, so the syntax and OOP patterns transfer directly"* |
| **Lightning Web Components** | *"LWC follows the same component model as React — props, lifecycle hooks, reactive properties. The mental shift is minimal"* |
| **SOQL** | *"SOQL is similar to SQL, and I've designed relational schemas with JPA and written custom queries using Spring Data"* |
| **Salesforce API** | *"I've built with REST and GraphQL APIs, including auth headers, error handling, and data transformation — the Salesforce API follows the same patterns"* |
| **Teamwork** | *"This was a solo project, but I designed it with clean architecture and separation of concerns so it would be maintainable by a team"* |
| **Learning speed** | *"I taught myself Spring Boot, GraphQL, WebFlux, React, and three UI libraries for this project. I'm a fast, self-directed learner"* |
| **Excel / formulas** | *"I have strong analytical skills — the VLOOKUP pattern is similar to how I think about relational data and foreign key lookups in databases"* |
| **Why Salesforce?** | *"I want to solve real business problems, not just write code. Salesforce sits at the intersection of technology and business — and Lansdale Group's consulting model means I'll see that impact directly"* |
| **What interests you about Lansdale?** | *"Two things: the mentorship from senior architects, and the variety — consulting means different clients, different industries, different problems. That accelerates growth faster than anything"* |
