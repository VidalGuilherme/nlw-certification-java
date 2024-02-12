# NLW Expert - Trilha JAVA
This repository contains a simple project built using Java and Spring Boot. The aim of this repository is to practice and share a simple API using Java Spring.


## Installation

1. Clone the repository:

```bash
$ git clone git@github.com:VidalGuilherme/nlw-certification-java.git
```

2. Install dependencies with Maven
3. Run with docker-compose (docker-compose up -d)

## Usage

1. Start the application with Maven
2. The API will be accessible at http://localhost:8080/

## Database
The project uses PostgresSQL as the database.
Spring boot to automatically load my database schema when I start it up.

```markdown
spring.jpa.generate-ddl=true
```

#### Seeds

You can also populate your database with pre-made seeds, located in "/resourcers/seeds" by running "CreateSeed.java"


## API Endpoints
The API provides the following endpoints:

#### QUESTIONS
```markdown
GET /questions - Retrieve a list of all questions.
```

```markdown
POST /questions - Register a new question.
Body:
{
	"technology": "React",
	"description": "Qual dos seguintes frameworks é uma alternativa ao React?",
	"alternatives": [
		{
			"description": "Preact",
			"isCorrect": true
		},
		{
			"description": "Angular",
			"isCorrect": false
		},
		{
			"description": "Vue",
			"isCorrect": false
		},
		{
			"description": "Svelte",
			"isCorrect": false
		}
	]
}
```

```markdown
PUT /questions/{id} - Alter an existing question.
{
	"technology": "React",
	"description": "Qual dos seguintes frameworks é uma alternativa ao React?",
	"alternatives": [
		{
			"description": "Preact",
			"isCorrect": true
		},
		{
			"description": "Angular",
			"isCorrect": false
		},
		{
			"description": "Vue 3",
			"isCorrect": false
		},
		{
			"description": "Svelte",
			"isCorrect": false
		}
	]
}
```

```markdown
DELETE /questions/{id} - Delete an existing questions.
```

```markdown
GET /questions/technology/{technology} - Retrieve a list of questions filtered by technology
```

#### CERTIFICATIONS
```markdown
GET /certifications - Retrieve a list of all certifications.
```

```markdown
POST /certifications - Register a new certification.
Body:
{
	"technology": "Angular",
	"questionsId": [
		"c916f454-07bb-4dfd-b126-642d289974ce",
        "c916f454-07bb-4dfd-b126-642d289974ce",
        "c916f454-07bb-4dfd-b126-642d289974ce"
	]
}

```

```markdown
PUT /certifications/{id} - Alter an existing certification.
Body:
{
	"technology": "Angular",
	"questionsId": [
		"c916f454-07bb-4dfd-b126-642d289974ce",        
        "c916f454-07bb-4dfd-b126-642d289974ce"
	]
}
```

```markdown
DELETE /certifications/{id} - Delete an existing certification.
```

```markdown
GET /certifications/technology/{technology} - Retrieve a list of certifications filtered by technology
```

#### STUDENTS

```markdown
POST /students/has_certification - Check if student has certification in the technology.
Body:
{
	"email":"mail@mail.com",
	"technology":"JAVA"
}
```

```markdown
POST /students/certification/answer - Record a student's responses for certification of a given technology.
Body:
{
	"email":"mail@mail.com",
	"technology":"JAVA",
	"questionsAnswers": [
		{
			"questionId": "c5f02721-6dc3-4fa6-b46d-6f2e8dca9c66",
			"alternativeId": "98f6891b-5f14-4b8e-bb87-46456a2610d4"
		},
		{
			"questionId": "b0ec9e6b-721c-43c7-9432-4d0b6eb15b01",
			"alternativeId": "9da03a4e-3c8d-4a32-87e1-9898938435c2"
		},
		{
			"questionId": "f85e9434-1711-4e02-9f9e-7831aa5c743a",
			"alternativeId": "e4dbf524-0a54-428a-b57c-853996fc8e19"
		}
	]
}
```

## Docker
You can run a database for this project with Docker by running the following command:

```bash
$ docker-compose up -d
```

To install Docker locally you can [click here](https://www.docker.com/products/docker-desktop/).