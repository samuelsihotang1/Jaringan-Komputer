# Guess game

This program is a number guessing game between two players that can run in server mode or client mode. This program uses sockets to connect these players. The following is an explanation of how the program works simply.

The program starts by declaring the variables that will be used during the game, such as ServerSocket, Socket, BufferedReader, DataOutputStream, and so on. These variables will be used to send and receive data between players.

Next, the program determines whether to run as a server or client based on the arguments provided when running the program. If server mode is selected, the program will start the server socket, accept connections from clients, and prepare input/output streams for communication. If client mode is selected, the program will try to connect to the server specified by the user.

After that, the game starts. The program will ask for input numbers from players who are in turn. Players are only allowed to enter numbers from 0 to 9. The numbers entered will be encrypted using the Encryption function and sent to the opponent using the output stream.

After the player sends numbers, the program will receive input numbers from the opponent via the input stream. This input will be decrypted using the decryptWord function. The player whose turn it is will be asked to guess the number entered by the opponent. If the player's guess is correct, the player will get an additional 1 point. If wrong, the player will lose 1 point. The game will continue until a player enters "---" as the number or a communication error occurs.

At the end of the game, the program will close all connections and input/output streams used. If the program is running as a server, the server socket will be closed along with the socket it is connected to. If the program is running as a client, the client socket will be closed.

Thus an explanation of how the program works in a simple way. This program uses sockets to connect two players and allows them to communicate with each other in a number guessing game.
