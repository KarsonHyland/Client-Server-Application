# Client-Server-Application
The application consists of a centralized Math server and two or more clients. The server will provide basic math calculation services to the client(s). 
There will be 1 server with at least 2 clients that can connect to it.
The server will:
  * Keep track of all users and how long they are there
  * log client details
  * connect with multiple clients at oncce
  * accept strings for basic math
  * respond in order of requests and close when the request is done and log it.
The clients will:
  * give the name during the intial attachment to the serve and then wait until it gets a successful acknowledgment about connection.
  * send basic math string calculations at random times to the server
  * should be able to send a close connection request and that terminates the application

client sends basic math string to the server -> the server accepts the string and performs the calculations -> another client joins the server -> the first client closes -> the second client closes -> the program ends.
