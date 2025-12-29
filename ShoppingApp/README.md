# 🛒 Alışveriş Sepeti Uygulaması

Java tabanlı nesne yönelimli mağaza yönetim sistemi.

---

## 📋 Özellikler

### 🛍️ Kullanıcı Modülü

#### **1. Ürün İşlemleri**
- **Ürün Listeleme**: Tüm ürünler stok miktarlarıyla gösterilir
- **Ürün Arama**: Büyük/küçük harf duyarsız arama
- **Sepete Ekleme**: Ürün adı ve miktar belirterek sepete ekleme
- **JSON Kaydet/Yükle**: Tüm ürünler `product_file` dosyasına JSON formatında kaydedilir ve yüklenir
  - ✅ **Polimorfik Destek**: Ürün tipleri (Book, Electronics, Food, Clothes) korunur
  - ✅ Uygulama kapanırken otomatik kayıt

#### **2. Sepet Yönetimi**
- **Görüntüleme**: 
  - Ürün adı, birim fiyat, indirimli fiyat ve adet gösterilir
  - **Subtotal**: Sepetteki toplam indirimli tutar otomatik hesaplanır
- **Çıkarma**: 
  - Kısmi çıkarma (örn: 5 adetten 2'sini çıkar)
  - Tam çıkarma (sepetteki tüm adedi sil)
  - Çıkarılan miktar otomatik olarak stoğa geri eklenir

#### **3. Stok Kontrol Sistemi**
- ✅ Sepete ekleme sırasında **anlık stok kontrolü**
- ✅ Yetersiz stok durumunda `InsufficientStockException` hatası
- ✅ Eklenen miktar **anında stoktan düşer**
- ✅ Sepetten çıkarılan miktar **stoğa geri eklenir**
- ✅ **Negatif stok durumu önlenir**

**Örnek Senaryo:**
```
- Laptop stoğu: 10 adet
- 3 adet sepete ekle → Stok: 7
- 5 adet daha ekle → Stok: 2
- 3 adet daha eklemeye çalış → ❌ HATA: "Insufficient stock! Laptop remaining:2"
```

#### **4. Ödeme Sistemi**

**İndirim Mekanizması:**
| Ürün Tipi | İndirim Oranı | Açıklama |
|-----------|---------------|----------|
| `Book` | %20 | Kitap ürünlerinde indirim |
| `Electronics` | %10 | Elektronik ürünlerde indirim |
| `Food` | %10 | Gıda ürünlerinde indirim |
| `Clothes` | %30 | Giyim ürünlerinde indirim |

**İndirim Hesaplama:**
- Her ürün tipi kendi indirimini hesaplar (`getDiscountAmount()`)
- Sepetteki **adet ile çarpılır**: `indirim = ürün_indirimi × adet`
- Toplam tutardan düşülür

**Ödeme Yöntemleri:**

**1️⃣ Kredi Kartı**
- Kart numarası ve CVC girişi
- Banka sistemi simülasyonu (2 saniye bekleme)
- Onay süreci simülasyonu

**2️⃣ Nakit**
- Ödenen miktar girişi
- **Yetersiz para kontrolü**: Ödenen < Toplam → ❌ İşlem iptal
- **Para üstü hesaplama**: Ödenen > Toplam → Fark hesaplanır

**Başarılı Ödeme:**
- Ödeme başarılı olduğunda sepet otomatik olarak temizlenir
- Stok bilgileri korunur (ödeme öncesi zaten güncellenmiştir)

#### **5. Girdi Validasyonu**
- ✅ **Tip kontrolü**: Sayısal girdi beklenen yerlerde harf girilirse uyarı verilir ve tekrar istenir
- ✅ **Boş string kontrolü**: Boş girişler yakalanır
- ✅ **Negatif değer kontrolü**: Negatif miktar/fiyat girilmesi engellenir

#### **6. Hata Yönetimi**
| Exception | Durum | Çözüm |
|-----------|-------|-------|
| `InsufficientStockException` | Stok yetersiz | Ekleme işlemi iptal, stok değişmez |
| `EmptyCartException` | Boş sepet ile ödeme | Ödeme yapılamaz |
| `ProductCantFoundException` | Ürün bulunamadı | İşlem iptal |
| `NumberFormatException` | Geçersiz sayı girişi | Tekrar giriş istenir |

---

### 👨‍💼 Admin Paneli

**Giriş:**
- 🔐 Şifre korumalı (Varsayılan: `1234`)
- Yanlış şifrede erişim engellenir

**Yetkiler:**
1. **Ürün Ekleme**: 
   - İsim, fiyat, stok miktarı girilir
   - Sadece `Product` tipinde ürün eklenebilir
2. **Ürün Silme**:
   - İsme göre arama
   - Bulunan ürün mağazadan kaldırılır
3. **Otomatik Kaydetme**:
   - Admin panelinden veya uygulamadan çıkışta tüm ürünler JSON dosyasına otomatik kaydedilir

---

## 🔐 Güvenlik Özellikleri

- ✅ Admin paneli şifre korumalı
- ✅ Tüm kullanıcı girdileri validasyondan geçer
- ✅ Negatif stok değeri engellenmiş
- ✅ Boş string girişleri yakalanır
- ✅ Exception'lar merkezi olarak yönetilir