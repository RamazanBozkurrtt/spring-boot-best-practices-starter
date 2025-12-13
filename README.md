# Spring Boot Best Practices Starter

Bu proje, ölçeklenebilir ve bakımı kolay Spring Boot uygulamaları geliştirmek için oluşturulmuş, endüstri standartlarını (Best Practices) temel alan bir başlangıç şablonudur.

Kurumsal düzeyde bir REST API mimarisinin ihtiyaç duyduğu temel yapı taşlarını; merkezi hata yönetimini, standart cevap formatlarını ve katmanlı mimari prensiplerini içerir.

## Özellikler

Bu şablon aşağıdaki temel yapıları hazır olarak sunar:

* **Global Exception Handling:** `@ControllerAdvice` kullanılarak tüm uygulama genelindeki hataların (Business Exception, Validation Exception vb.) tek bir merkezden yönetilmesi ve istemciye standart bir hata formatında dönülmesi.
* **Generic Response Wrapper:** Başarılı veya hatalı tüm API cevaplarının standart bir JSON formatında (data, message, status, timestamp) sunulması.
* **DTO (Data Transfer Object) Pattern:** Entity nesnelerinin API katmanına sızdırılmaması için ModelMapper/MapStruct entegrasyonu ile DTO dönüşüm yapısı.
* **Input Validation:** `jakarta.validation` kütüphanesi ile gelen isteklerin (Request Body) doğrulanması ve anlamlı hata mesajlarının üretilmesi.
* **Layered Architecture:** Controller, Service, Repository ve Entity katmanlarının net bir şekilde ayrıştırıldığı temiz kod yapısı.
* **Swagger / OpenAPI:** API dokümantasyonu için hazır konfigürasyon.
* **Spring Security ve Jwt token ile rol tabanlı yetkilendirme sistemi.

## Teknoloji Yığını

* Java 17 (veya 21)
* Spring Boot 3.x
* Spring Web
* Spring Data JPA
* PostgreSQL
* Lombok
* Maven

## Proje Mimarisi ve Paket Yapısı

Proje, Sorumlulukların Ayrılığı (Separation of Concerns) ilkesine göre yapılandırılmıştır:

* `controller`: Sadece HTTP isteklerini karşılar ve validasyon işlemlerini yönetir.
* `service`: İş mantığının (Business Logic) bulunduğu katmandır.
* `repository`: Veritabanı erişim işlemlerini yönetir.
* `entity`: Veritabanı tablolarına karşılık gelen nesnelerdir.
* `dto`: İstemci ile veri alışverişinde kullanılan nesnelerdir (Request/Response).
* `exception`: Özel hata sınıfları ve Global Exception Handler bu pakette bulunur.
* `core`: Proje genelinde kullanılan yardımcı sınıflar ve yapılandırmalar (Generic Response vb.).
* `Pageable`: Proje de verimlilik sağlamak adına pageable mimari kullanılıyor.

## Kurulum ve Çalıştırma

Projeyi yerel ortamınızda çalıştırmak için aşağıdaki adımları izleyin:

1.  Repoyu klonlayın:
    ```bash
    git clone [https://github.com/kullaniciadi/spring-boot-best-practices-starter.git](https://github.com/kullaniciadi/spring-boot-best-practices-starter.git)
    ```

2.  Proje dizinine gidin:
    ```bash
    cd spring-boot-best-practices-starter
    ```

3.  Maven ile bağımlılıkları yükleyin ve projeyi derleyin:
    ```bash
    mvn clean install
    ```

4.  Uygulamayı çalıştırın:
    ```bash
    mvn spring-boot:run
    ```

## Kullanım Örnekleri
