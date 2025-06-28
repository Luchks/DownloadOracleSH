
package backend;

public class DBManager {
    public static void saveToPostgres(String data) {
        System.out.println("Guardando en PostgreSQL: " + data);
    }

    public static void saveToMongo(String data) {
        System.out.println("Guardando en MongoDB: " + data);
    }
}
