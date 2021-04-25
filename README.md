# Submission_3_Dicoding_BFAA

**<h1>Pada submission kali ini saya mendapatkan tugas baru untuk melanjutkan submission 2 dari kelas BFAA Dicoding dengan adanya beberapa fitur yang ditambahkan kedalam submission 2 sebelumnya dengan kriteria sebagai berikut.</h2>**

<h2>**Favorite User</h2>
Syarat:
Aplikasi harus bisa menambah dan menghapus user dari daftar favorite.
Aplikasi harus mempunyai halaman yang menampilkan daftar favorite.
Menampilkan halaman detail dari daftar favorite.
<p float="left">
<img src="https://user-images.githubusercontent.com/67593237/116003678-71131a00-a5ab-11eb-89fb-7c755256c33b.png" width="300" height="600">
  <img src="https://user-images.githubusercontent.com/67593237/116003928-d582a900-a5ac-11eb-9d5e-4b28e5fcec71.png" width="300" height="600">
</p>

<h2>**Reminder**</h2>
Syarat:
Terdapat pengaturan untuk menghidupkan dan mematikan reminder di halaman Setting.
Daily reminder untuk kembali ke aplikasi yang berjalan pada pukul 09.00 AM.
<p float="center">
<img src="https://user-images.githubusercontent.com/67593237/116004027-5b065900-a5ad-11eb-86e7-e1410b09cb72.png" width="300" height="600">
  <img src="https://user-images.githubusercontent.com/67593237/116003983-2a262400-a5ad-11eb-8fdb-c9fcf55aaf4b.png" width="300" height="600">
</p>

<h2>**Consumer App**</h2>
Syarat:
Membuat module baru yang menampilkan daftar user favorite.
Menggunakan Content Provider sebagai mekanisme untuk mengakses data dari satu aplikasi ke aplikasi lain.
<p float="center">
<img src="https://user-images.githubusercontent.com/67593237/116004223-3959a180-a5ae-11eb-8eca-a65744ef6941.png" width="300" height="600">
  <img src="https://user-images.githubusercontent.com/67593237/116005439-60ff3880-a5b3-11eb-954c-3ed27b04bf56.png" width="300" height="600">
</p>

<h1>**baik langsung saja ke penjelasan kriteria pertama "Favorite User"**</h1>
**#Didalam UsersDetailActivity, saya melakukan beberapa perubahan untuk pengambilan data detail user, yang mana awalnya menggunakan pengambilan data API url, dan sekarang untuk pengambilan datanya berupa data yang dikirim dari MainActivity ke dalam constant value EXTRA_USERS, lalu dimasukan ke bagian model data class Users().**

<p float="center">
  <img src="https://user-images.githubusercontent.com/67593237/116004599-f7c9f600-a5af-11eb-9190-2ee946903320.png" width="800" height="550">
</p>

**#Dibagian fungsi onCreate() saya menambahkan sebuah percabangan yang mana pada saat button/tombol hati pada detail user di tekan maka nilai dari value "isFavorite" akan berubah sesuai lawan kondisi nilai awal button tersebut, lalu nilai dari fungsi setFavorite akan merubah gambar dari button hati tersebut**
<p float="center">
<img src="https://user-images.githubusercontent.com/67593237/116004706-6313c800-a5b0-11eb-875c-1c5f25ba430e.png" width="300" height="600">
<img src="https://user-images.githubusercontent.com/67593237/116004500-6064a300-a5af-11eb-95e3-4f7b7c3c8f26.png" width="65%" height="65%">
  <img src="https://user-images.githubusercontent.com/67593237/116004964-7f643480-a5b1-11eb-95fd-7b0f608670ef.png" width="65%" height="65%">
</p>
**function ketika button diklik, Jika tombol tersebut bernilai false maka pada saat diklik, button tersebut akan mengirim data user ke DatabaseContract yang mana sesuai dengan yang ada di modul BFAA. Lalu jika tombol tersebut awalnya bernilai true, maka data user tersebut akan dihapus dari Favorite**
<img src="https://user-images.githubusercontent.com/67593237/116005030-c3efd000-a5b1-11eb-9843-2480d792cc8e.png" width="80%" height="80%">

**Untuk function menghapus dan menambah sebuah data ke SQLite sama seperti yang di ajarkan di kelas BFAA dan digunakan pada fungsi onClick yang ada di UsersDetail**

<img src="https://user-images.githubusercontent.com/67593237/116005267-b38c2500-a5b2-11eb-8a26-24721d1191b6.png" width="80%" height="80%">

<h1>**Lalu untuk Reminder**</h1>
**Sama seperti yang di ajarkan di kelas BFAA**

<h1>**Untuk ConsumerApp**</h1>
**Sama Seperti yang di ajarkan di kelas BFAA**



















