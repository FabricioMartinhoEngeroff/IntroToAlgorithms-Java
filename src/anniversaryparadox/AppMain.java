package anniversaryparadox;

public class AppMain {
    public static void main(String[] args) {
        BirthdayParadoxService service = new BirthdayParadoxService(365);

        // imprime de 1 a 60 pessoas
        for (int people = 1; people <= 60; people++) {
            System.out.println(service.resultFor(people));
        }

        // mostra o mínimo para atingir 50%
        int min50 = service.minPeopleFor(0.50);
        System.out.printf("%nMínimo para ≥ 50%%: %d pessoas%n", min50);

        // exemplo: mínimo para 99%
        int min99 = service.minPeopleFor(0.99);
        System.out.printf("Mínimo para ≥ 99%%: %d pessoas%n", min99);
    }
}