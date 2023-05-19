# Group Chat

This program consists of two files: Server.java and Client.java. The program is a simple example of a client-server implementation using sockets in Java. The server is responsible for receiving messages from clients and broadcasting them to all connected clients. The client can act as either a message sender or receiver.

When the Server program is run, the user is prompted to enter a keyword. The program then calculates the sum of the ASCII values of each character in the keyword and uses that value as the port to create a ServerSocket. The ServerSocket listens for client connections on that port.

Every time the server accepts a connection from a client, it adds the client socket to the clients list. It then creates a new thread to handle the client connection. The thread runs the pengurusPengguna class, which implements the Runnable interface.

The pengurusPengguna class is responsible for managing the client connection. In the run() method, the client socket is used to read messages sent by the client. If the message is empty, it means the client has terminated the connection, and the client socket is removed from the clients list. If the message is not empty, the message is sent to all connected clients using a for loop.

When the Client program is run, the user is also prompted to enter a keyword. The ASCII values of each character in the keyword are summed to get the port value, which is used to establish a connection with the server using a Socket. The user is then prompted to enter a userID.

If the Client program is run with the "sender" argument, the user can enter messages to be sent to the server. The messages are sent using a PrintWriter that writes to the socket's OutputStream. The user can stop the program by entering the message "---". The socket is closed after the program has finished sending the messages.

If the Client program is run with the "receiver" argument, the client will connect to the server and receive messages from the server. The messages are read using a BufferedReader from the socket's InputStream. The program continuously reads messages from the server and displays them on the screen.

This program uses socket mechanisms to send and receive data between the client and server. The server uses a ServerSocket to accept client connections, while the client uses a Socket to establish a connection with the server. The use of threads allows the server to serve multiple clients concurrently.

In this program, communication between the client and server is done using a simple protocol. Each message sent by the client is prefixed with the userID and the timestamp of the message, and the server receives the message and broadcasts it to all connected clients.

That concludes the explanation of how this program works. This program provides a simple example of a client-server implementation using sockets in Java, where the server receives messages from clients and broadcasts them to all connected clients.
