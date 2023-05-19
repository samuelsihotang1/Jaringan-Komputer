# Client-to-Client Chat

The provided program is a simple chat application that enables communication between a client and a server using sockets. The program follows a specific workflow to facilitate the chat process.

At the start, the program determines the mode, either "server" or "client," based on command-line arguments. In "server" mode, the program prompts the user for their ID, calculates a target port based on the sum of the ID's characters, and initiates a server socket to listen for incoming connections. On the other hand, in "client" mode, the program prompts the user for the target ID, calculates the target port using the same method, and establishes a socket connection to the server.

Once the connection is established, a chat loop begins where users can exchange messages. In the client mode, the user can input messages, which are encrypted using a simple encryption algorithm. The encrypted message is then sent to the server. The server receives the encrypted message, decrypts it, and displays the decrypted message from the client. Subsequently, the server prompts the user for a response, encrypts it, and sends it back to the client.

The client receives the encrypted response, decrypts it, and displays the decrypted message from the server. This chat loop continues until either the client or server inputs "exit" as a message, indicating the termination of the conversation.

Finally, when the chat is concluded, the program closes the connections and gracefully terminates. The program utilizes socket communication, encryption, and decryption functions to enable secure communication and facilitate the chat functionality between the client and server.
