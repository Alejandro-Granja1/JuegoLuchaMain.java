public class Luffy extends Personaje {
    public Luffy(String nombre) {
        super(nombre, 110, 7); // 110 hp, velocidad 7
    }

    // Gomu Gomu: daño medio + chance de aturdir (pierde 1 turno)
    protected void usarEspecial(Personaje oponente) {
        int dano = rand.nextInt((45 - 25) + 1) + 25; // 25-45
        oponente.recibirDano(dano);
        boolean aturde = rand.nextDouble() < 0.3; // 30% aturde
        System.out.printf("%s usa Gomu Gomu! Causa %d daño a %s.%s\n",
                nombre, dano, oponente.getNombre(), (aturde ? " ¡Lo aturde!" : ""));
        // Nota: el efecto de aturdimiento no está implementado como estado persistente aquí.
        // Si quieres que aturdimiento tenga efecto real, lo agrego en la próxima iteración.
    }

    protected int getCooldownPorDefecto() { return 3; }
}
