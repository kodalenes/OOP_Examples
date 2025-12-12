package Utils;

import Account.BankAccount;
import Account.CheckingAccount;
import Account.SavingAccount;
import com.google.gson.*;

import java.lang.reflect.Type;

public class BankAccountAdapter implements JsonDeserializer<BankAccount> {

    @Override
    public BankAccount deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        //JsonObjet json daki veri yapsidir yani bilgileri icerir
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        //JSON icindeki acctype degiskenini buluyor
        JsonElement typeElement = jsonObject.get("accType");

        if (typeElement == null)
        {
            throw new JsonParseException("Error: Cannot find accType at JSON");
        }
        //hesap tipini string olarak sakliyoruz
        String accType = typeElement.getAsString();

        return switch (accType.toUpperCase()){
            case "CHECKING" -> jsonDeserializationContext.deserialize(jsonElement, CheckingAccount.class);
            case "SAVING" -> jsonDeserializationContext.deserialize(jsonElement, SavingAccount.class);
            default -> throw new JsonParseException("Unknown account type: " + accType);
        };
    }
}
