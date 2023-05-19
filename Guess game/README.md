# Guess game

Program yang diberikan adalah sebuah permainan tebak angka antara dua pemain yang dapat berjalan dalam mode server atau mode client. Program ini menggunakan socket untuk menghubungkan pemain-pemain tersebut. Berikut ini adalah penjelasan tentang cara kerja program tersebut secara sederhana.

Program dimulai dengan mendeklarasikan variabel-variabel yang akan digunakan selama permainan, seperti ServerSocket, Socket, BufferedReader, DataOutputStream, dan sebagainya. Variabel-variabel ini akan digunakan untuk mengirim dan menerima data antara pemain.

Selanjutnya, program menentukan mode apakah berjalan sebagai server atau client berdasarkan argumen yang diberikan saat menjalankan program. Jika mode server dipilih, program akan memulai server socket, menerima koneksi dari client, dan menyiapkan input/output stream untuk komunikasi. Jika mode client dipilih, program akan mencoba terhubung ke server yang ditentukan oleh pengguna.

Setelah itu, permainan dimulai. Program akan meminta inputan angka dari pemain yang sedang giliran. Pemain hanya diizinkan memasukkan angka dari 0 hingga 9. Angka yang dimasukkan akan dienkripsi menggunakan fungsi enkripsiKata dan dikirimkan ke lawan dengan menggunakan output stream.

Setelah pemain mengirimkan angka, program akan menerima inputan angka dari lawan melalui input stream. Inputan ini akan didekripsi menggunakan fungsi dekripsiKata. Pemain yang sedang giliran akan diminta untuk menebak angka yang diinputkan oleh lawan. Jika tebakan pemain benar, pemain akan mendapatkan tambahan 1 poin. Jika salah, pemain akan kehilangan 1 poin. Permainan akan terus berlanjut sampai ada pemain yang memasukkan "---" sebagai angkanya atau terjadi kesalahan dalam komunikasi.

Pada akhir permainan, program akan menutup semua koneksi dan input/output stream yang digunakan. Jika program berjalan sebagai server, server socket akan ditutup bersama dengan socket yang terhubung. Jika program berjalan sebagai client, socket client akan ditutup.

Demikianlah penjelasan tentang cara kerja program tersebut secara sederhana. Program ini menggunakan socket untuk menghubungkan dua pemain dan memungkinkan mereka saling berkomunikasi dalam permainan tebak angka.
