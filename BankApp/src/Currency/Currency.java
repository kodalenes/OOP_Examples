package Currency;

public enum Currency {
    // Frankfurter API (ECB) tarafından desteklenen tüm para birimleri
    AUD("AUD"), // Avustralya Doları
    BGN("BGN"), // Bulgar Levası
    BRL("BRL"), // Brezilya Reali
    CAD("CAD"), // Kanada Doları
    CHF("CHF"), // İsviçre Frangı
    CNY("CNY"), // Çin Yuanı
    CZK("CZK"), // Çek Korunası
    DKK("DKK"), // Danimarka Kronu
    EUR("EUR"), // Euro
    GBP("GBP"), // İngiliz Sterlini
    HKD("HKD"), // Hong Kong Doları
    HUF("HUF"), // Macar Forinti
    IDR("IDR"), // Endonezya Rupisi
    ILS("ILS"), // İsrail Yeni Şekeli
    INR("INR"), // Hindistan Rupisi
    ISK("ISK"), // İzlanda Kronu
    JPY("JPY"), // Japon Yeni
    KRW("KRW"), // Güney Kore Wonu
    MXN("MXN"), // Meksika Pesosu
    MYR("MYR"), // Malezya Ringgiti
    NOK("NOK"), // Norveç Kronu
    NZD("NZD"), // Yeni Zelanda Doları
    PHP("PHP"), // Filipin Pesosu
    PLN("PLN"), // Polonya Zlotisi
    RON("RON"), // Rumen Leyi
    SEK("SEK"), // İsveç Kronu
    SGD("SGD"), // Singapur Doları
    THB("THB"), // Tayland Bahtı
    TRY("TRY"), // Türk Lirası (Baz Para Birimi)
    USD("USD"), // ABD Doları
    ZAR("ZAR"); // Güney Afrika Randı

    private final String code;
    Currency(String code)
    {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static Currency fromString(String text)
    {
        for (Currency c : Currency.values())
        {
            if (c.code.equalsIgnoreCase(text)) return c;
        }

        return null;
    }
}
