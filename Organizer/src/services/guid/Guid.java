package services.guid;

//Unikalne ID 
public class Guid {
	public static String get() {
		return java.util.UUID.randomUUID().toString();
	}
}
