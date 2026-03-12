💳 Idempotent Key Service

  A simple payment processing system that guarantees safe retries using Idempotency Keys.
  
  This project demonstrates how modern payment APIs prevent duplicate transactions when clients retry requests due to network failures or timeouts.
  
  The system ensures that the same payment request is processed only once, even if the client sends the request multiple times.

🚀 Project Overview

  In real-world payment systems, network failures are common. When a client does not receive a response, it may retry the same API call. If the backend processes that request again, it can result in duplicate charges, which is unacceptable in financial systems.
  
  To solve this, many payment providers implement idempotency.
  
  In this project:
  
  Every payment request includes an Idempotency Key
  
  The server checks whether the key already exists
  
  If it exists → the previous response is returned
  
  If it does not exist → the payment is processed and stored
  
  This guarantees exactly-once processing behaviour for payment requests.

🏗️ System Architecture

<img width="627" height="525" alt="image" src="https://github.com/user-attachments/assets/39f5b0f2-902a-43fc-98f5-043fd8dbe023" />

🧰 Tech Stack

  Backend
  
    Java
    Spring Boot
    Spring Data JPA
    Hibernate

  Frontend
  
    ReactJS
    Axios
    React Router
  
  Database
  
    MySQL

🔑 How Idempotency Works

  1️⃣ The client generates a unique Idempotency Key
  
  Example:
  
      Idempotency-Key: abc123
  
  2️⃣ Client sends the payment request
  
      POST /api/payments
  
  3️⃣ Backend checks the idempotency_keys table
  
    If the key does not exist
    
    Payment is processed
    
    Response is stored
    
    Key is saved in database
    
    If the key already exists
    
    Backend does not process the payment again
    
    Previously stored response is returned
    
    This ensures that retries never create duplicate payments.

🔐 Request Hash Validation

  To prevent misuse of idempotency keys, the system also validates request payload integrity.
  
  When a request arrives:
  
    1) A hash of the request payload is generated
    
    2) The hash is stored with the idempotency key
    
    3) If the same key is used with a different payload, the system rejects the request
    
    This prevents scenarios where a client accidentally or intentionally tries to reuse a key for a different payment.

🖥️ Frontend Features

The React frontend provides a simple UI to simulate payment requests.

Pages

🏠 Home Page

  <img width="1920" height="1080" alt="image" src="https://github.com/user-attachments/assets/7b3e20c3-7eaa-43e9-979d-3a2263568eb8" />


Enter receiver UPI ID

Enter payment amount

Generate idempotency key

Submit payment

📜 Transaction History

  <img width="1920" height="1080" alt="Screenshot 2026-03-10 012956" src="https://github.com/user-attachments/assets/da254594-7ebe-4e8c-a475-007bd9d26019" />


  View previous transactions

✅ Success Page

  <img width="1920" height="1080" alt="image" src="https://github.com/user-attachments/assets/a692aed7-92e7-4bc3-9549-86045339d9b5" />


  Displays successful payment response

### Idempotency Retry Behaviour

Retrying the request with the same **Idempotency Key** and identical payload returns the previously stored response without creating a duplicate payment.

  <img width="1920" height="1080" alt="image" src="https://github.com/user-attachments/assets/fb515383-e9ba-4576-b745-3ad646e9e965" />


❌ Failure Page

  <img width="1920" height="1080" alt="Screenshot 2026-03-10 013117" src="https://github.com/user-attachments/assets/666a44c2-d4ce-44a6-93f7-63214747f82e" />


  Displays error message if payment fails


▶️ Running the Project Locally

  1️⃣ Clone the repository
  
    git clone https://github.com/yourusername/idempotency-key-service.git
    
  2️⃣ Start Backend
    cd backend
    mvn spring-boot:run
  
  Backend runs on:
  
    http://localhost:8080
  
  3️⃣ Start Frontend
  
    cd frontend
    npm install
    npm run dev
  
  Frontend runs on:
  
    http://localhost:5173

⚡ Why This Project Matters
  
  Idempotency is a critical concept in distributed systems, especially in systems that handle financial or transactional operations.
  
  In real-world environments, network issues, timeouts, or client-side retries can cause the same API request to be sent multiple times. Without proper safeguards, this can result in duplicate operations such as multiple payments or duplicate orders.
  
  This project demonstrates how backend systems can handle safe client retries by implementing an idempotency key mechanism. Instead of processing the same request repeatedly, the system detects previously processed requests and returns the stored response.
  
  This approach is commonly used in payment gateways and financial APIs to ensure consistent and reliable transaction processing.

🔮 Future Improvements

Some enhancements that could make the system closer to production-grade:

  ⚡ Redis-based idempotency store for faster lookups
  
  🐳 Docker support for easier deployment
  
  📩 Event-driven processing using Kafka
  
  🔒 Rate limiting to prevent abuse
  
  📊 Monitoring with Prometheus & Grafana
  
  ☁️ Cloud deployment (AWS / GCP)
