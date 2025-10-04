public class Levi extends Personaje {
    public Levi(String nombre) {
        super(nombre, 100, 10); // más rápido, vida 100
    }

    // Técnica de precisión: alto chance de crítico (x2)
    protected void usarEspecial(Personaje oponente) {
        int base = rand.nextInt((MAX_DANO - MIN_DANO) + 1) + MIN_DANO;
        boolean critico = rand.nextDouble() < 0.7; // 70% probabilidad de crítico
        int dano = critico ? base * 2 : base;
        oponente.recibirDano(dano);
        System.out.printf("%s usa Técnica de Cuchillas! %s crítico -> %d daño a %s.\n",
                nombre, (critico ? "¡Golpe" : "Golpe"), dano, oponente.getNombre());
    }

    protected int getCooldownPorDefecto() { return 3; }
}
