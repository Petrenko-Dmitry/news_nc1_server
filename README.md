Getting Started
1. Set Up MySQL
To set up the MySQL database, navigate to the project's root directory where the docker-compose.yml file is located.
2. Start the Server (news_nc1_server)
Once the MySQL container is running, you can start the server. Depending on your project structure, you can typically run it using:
3. Start the Client (news_nc1_client)
After the server runs, the client's interaction with the server is started.

Functionality
The server receives data from the client and stores it in the MySQL database. You can use the client to send requests to the server, which then processes the data and saves it for future use.
