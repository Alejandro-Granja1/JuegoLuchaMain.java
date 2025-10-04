import java.util.Random;

public abstract class Personaje {
    protected String nombre;
    protected int hp;
    protected int maxHp;
    protected int velocidad; // influencia iniciativa
    protected int cooldownEspecial; // cuántos turnos faltan para poder usar especial
    protected final int MIN_DANO = 10;
    protected final int MAX_DANO = 30;
    protected Random rand = new Random();

    public Personaje(String nombre, int maxHp, int velocidad) {
        this.nombre = nombre;
        this.maxHp = maxHp;
        this.hp = maxHp;
        this.velocidad = velocidad;
        this.cooldownEspecial = 0; // listo desde el inicio
    }

    public String getNombre() { return nombre; }
    public int getPuntosDeVida() { return hp; }
    public boolean estaVivo() { return hp > 0; }
    public int getVelocidad() { return velocidad; }

    // Ataque base (daño aleatorio entre MIN_DANO y MAX_DANO)
    public void atacar(Personaje oponente) {
        int dano = rand.nextInt((MAX_DANO - MIN_DANO) + 1) + MIN_DANO;
        oponente.recibirDano(dano);
        System.out.printf("%s ataca a %s causando %d de daño.\n", nombre, oponente.getNombre(), dano);
    }

    public void recibirDano(int dano) {
        hp -= dano;
        if (hp < 0) hp = 0;
    }

    // Método para usar habilidad especial: retorna true si se usó
    public boolean intentarUsarEspecial(Personaje oponente) {
        if (cooldownEspecial == 0) {
            usarEspecial(oponente);
            cooldownEspecial = getCooldownPorDefecto(); // set cooldown tras uso
            return true;
        } else {
            return false;
        }
    }

    // Cada personaje define su especial
    protected abstract void usarEspecial(Personaje oponente);

    // Define cuántos turnos dura el cooldown por defecto (cada personaje puede sobreescribir)
    protected int getCooldownPorDefecto() { return 3; }

    // Debe llamarse al final de cada turno para reducir cooldown
    public void avanzarTurno() {
        if (cooldownEspecial > 0) cooldownEspecial--;
    }

    // Representación visual simple de la barra de vida
    public String barraVida() {
        int totalBars = 20;
        double pct = (double) hp / maxHp;
        int filled = (int) Math.round(pct * totalBars);
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < filled; i++) sb.append("|");
        for (int i = filled; i < totalBars; i++) sb.append(" ");
        sb.append("] ");
        sb.append(hp).append("/").append(maxHp);
        sb.append(" (CD:").append(cooldownEspecial).append(")");
        return sb.toString();
    }
}
