# Personel İnsan Kaynakları Sistemi

Küçük ve orta ölçekli bir ekibin **personel, birim ve şehir** kayıtlarını tek yerden yönetmesi için yazılmış bir web uygulaması.

Tarayıcıdan giriş yapılır. Yönetici yeni çalışan ekler, birime ve şehre bağlar, durumunu günceller. Standart kullanıcı aynı kayıtları sadece görür. Amaç; dağınık Excel listesi yerine süzülebilir, yetkiye göre açılan bir İK ekranı sunmak.

---

## Ekranda ne var

- **Giriş:** kullanıcı adı / şifre. Yetkisiz kimse personel listesine giremez.
- **Ana sayfa:** toplam / aktif / izinde / ayrılan sayıları, son eklenenler, birim ve şehir grafikleri.
- **Personel:** arama, sayfalama, şehir–birim–bölüm–durum filtresi, detay sayfası, CSV indirme.
- **Durum:** aktif, izinde, işten ayrıldı. Kayıt silinmez, pasife alınır.
- **Şehir ve birim:** personel kaydının bağlandığı tanım listeleri.
- **Raporlar:** birim ve şehir bazında kişi sayısı.
- **Ayarlar:** koyu / açık tema.

İki rol vardır:

| Hesap | Ne yapabilir |
|---|---|
| Yönetici (`admin`) | Ekleme, düzenleme, pasife alma, toplu işlem |
| Kullanıcı (`user`) | Liste, detay, rapor — yazma yok |

---

## Teknik yapı

| Katman | Teknoloji |
|---|---|
| Backend | Java 17, Spring Boot 2.7, Spring Security, JPA |
| Veritabanı | PostgreSQL |
| Kimlik | JWT + BCrypt |
| Frontend | Angular 19, PrimeNG, Axios |

Personel bir şehre ve birime `ManyToOne` ile bağlıdır. Adres ve iletişim bilgisi personele aittir. API HTTP kodları (400, 401, 403, 404, 409) anlamlıdır.

---

## Nasıl çalıştırılır

**Gerekenler:** Java 17, Node 20, PostgreSQL (`personel_dev` veritabanı, varsayılan port `5432`)

```bash
# 1) Yerel veritabanı ayarı (Git'e girmez)
cp backend/src/main/resources/application-local.properties.example \
   backend/src/main/resources/application-local.properties
```

`application-local.properties` içindeki kullanıcı / şifreyi kendi Postgres bilgine göre düzelt.

```bash
# 2) API
cd backend
./mvnw spring-boot:run
```

```bash
# 3) Arayüz
cd frontend
npm install
npm start
```

- Arayüz: http://localhost:4300
- API: http://localhost:18080
- Swagger: http://localhost:18080/swagger-ui.html

Uygulama `8080` kullanmaz.

### Deneme hesapları

İlk açılışta otomatik oluşur:

| Kullanıcı | Şifre | Rol |
|---|---|---|
| `admin` | `Admin123!` | Yönetici |
| `user` | `User123!` | Görüntüleme |

Listeyi doldurmak için (API açıkken):

```bash
node scripts/seed-demo.mjs
```

Yaklaşık 20 şehir, 18 birim ve 20 personel ekler. Tekrar çalışınca aynı kayıtları çoğaltmaz.

---

## Test

```bash
cd backend
./mvnw test
```

H2 bellek veritabanında çalışır. Yanlış şifre 401, tokensız yazma 401, boş şehir 400, olmayan kayıt 404 beklenir.

---

## Repo yapısı

```
backend/     REST API
frontend/    Angular arayüz
scripts/     Demo veri
```

