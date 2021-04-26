# remake-lynda-online-course-platform

Website yang dikerjakan secara berkelompok berbasiskan google cloud, yang memanfaatkan fitur google storage sebagai tempat menyimpan file dan google firestore sebagai database berbasiskan NoSQL.

Website ini telah diremake agar semirip mungkin dengan projek aslinya. Terdapat beberapa fitur yang tidak bisa digunakan seperti fitur pengiriman email dan real-time

chatting. Pengumpulan database menggunakan teknik scrapping (code lynda.py dan hasil scrapping dapat dilihat pada folder web scrapping) yang mana target scrapping adalah 

https://www.linkedin.com/learning/me. Projek ini dibuat pada awal tahun 2019, menggunakan package JAVA EE yang mana memanfaatkan servlet dan JSP sebagai fitur utamanya.

Kunjungi website https://lynda-310811.appspot.com/ untuk melihatnya secara langsung dan gunakan email **bearcahill@gmail.com dengan password bearcahill**. Pada website tersebut,

tidak dapat dilakukan proses registrasi karena terkendala pengiriman email verifikasi. Karena ini adalah website remake, maka database juga beberapa ada yang hilang, dan 

sudah dibuat semirip mungkin. Kunjungi https://lynda-310811.appspot.com/adminPage.jsp?koderahasia=hehe untuk melihat dashboard bagian admin.

**Pendapat pribadi:** 

Dengan menggunakan NoSQL, maka penggunaannya berbeda dengan relational database seperti MySQL. Pada pengerjaan projek ini, NoSQL firebase diperlakukan seperti

relational database, yang mana merupakan sebuah kesalahan (seperti melakukan auto generate id sebuah document, pada projek ini, semua dokumen pada collection akan dibaca lalu

dihitung jumlah document yang sudah terbaca + 1. Karena ini adalah cloud (pay-as-you-go) maka tagihan akan membengkak, jika diharuskan membaca semua dokumen hanya untuk mendapatkan

autogenerate id. Seharusnya terdapat 1 dokumen yang menyimpan data-data yang terkait dengan collectionnya, seperti menyimpan data jumlah document

yang ada. Hal ini juga mungkin yang mengakibatkan website berjalan dengan lambat karena harus membaca banyak dokumen database.

Gambaran NoSQL Firebase:

https://ibb.co/njVsLkX
