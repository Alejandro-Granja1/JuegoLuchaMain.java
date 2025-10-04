public class Goku extends Personaje {
    public Goku(String nombre) {
        super(nombre, 120, 8); // vida max 120, velocidad 8
    }

    // Kamehameha: gran daño (40-60)
    protected void usarEspecial(Personaje oponente) {
        int dano = rand.nextInt((60 - 40) + 1) + 40;
        oponente.recibirDano(dano);
        System.out.printf("%s usa Kamehameha! Causando %d de daño masivo a %s.\n",
                nombre, dano, oponente.getNombre());
    }

    protected int getCooldownPorDefecto() { return 4; } // más poderoso, más cooldown
}
