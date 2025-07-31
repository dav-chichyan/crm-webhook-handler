# crm-webhook-handler

A service that receives webhooks from CRM systems via HTTPS, processes them through RabbitMQ using a topic exchange and two queues, and delivers structured events to internal applications (such as Armenian software) within a secure local network.

---

## Features

- **HTTPS Webhook Endpoint:** Securely receive CRM events.
- **RabbitMQ Integration:**
    - Uses a **topic exchange** to route messages.
    - Employs **two queues** for reliable and scalable processing.
    - Dockerized RabbitMQ ensures durability and persistence of events (requests are not lost if services restart).
    - **Automatic message deletion:** Messages are deleted after a max number of processing attempts (`maxAttempts = 10`).
    - **Backoff Policy:** Failed deliveries are retried with exponential backoff (`delay = 3000ms`, `multiplier = 2`), preventing queue overload.
- **Internal Delivery:** Forwards webhook events to other applications on the local network.
- **Extensible:** Easily adaptable to more consumers.

---

## Technologies Used

- **Java & Spring Boot**
- **RabbitMQ** (topic exchange, two queues)
- **Docker** (for RabbitMQ container)
- **HTTPS / REST APIs**

---

## Usage

1. **Run RabbitMQ via Docker:**
    ```bash
    docker run -d --hostname rabbitmq --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:management
    ```

2. **Configure your CRM** to send webhooks to the service’s HTTPS endpoint.

3. **Run the application:**
    ```bash
    mvn clean install
    java -jar target/your-jar-name.jar
    ```

---

## Resilience & Reliability

- **Message durability**: Dockerized RabbitMQ ensures no webhook requests are lost if a service restarts.
- **Dead-letter handling**: Messages are deleted after 10 failed delivery attempts.
- **Backoff strategy**: Each failed message waits 3 seconds, then doubles the delay each time (up to 10 attempts), reducing queue overload risk.

---


## Contributing

Contributions and issues are welcome!  
Feel free to open an issue or submit a pull request.


