package controller;

import java.util.List;

public class MainMenu {

    private List<String> options;

    public MainMenu() {
        initOptions();
    }

    private void initOptions() {
        options.add("Felhasználó regisztráció/bejelentkezés");
        options.add("Termékek belehelyezése a vásárló kosarába");
        options.add("Termék kivétele vásárló kosarából");
        options.add("Termék mennyiség növelése");
        options.add("Rendelés leadása");
        options.add("Termékek listázása/betöltése fájlból");
        options.add("Kilépés a WebShop-ból");
        System.out.println("Menü: ");
    }
}
