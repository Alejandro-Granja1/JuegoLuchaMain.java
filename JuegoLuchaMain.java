import java.util.Random;
import java.util.Scanner;

public class JuegoLuchaMain {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("=== Juego de Lucha - Selecciona dos combatientes ===");
        System.out.println("Opciones: goku, levi, luffy");
        System.out.print("Tipo Jugador 1 (ej. goku): ");
        String tipo1 = sc.nextLine().trim().toLowerCase();
        System.out.print("Tipo Jugador 2 (ej. luffy): ");
        String tipo2 = sc.nextLine().trim().toLowerCase();

        Personaje p1 = PersonajeFactory.crear(tipo1, capitalize(tipo1));
        Personaje p2 = PersonajeFactory.crear(tipo2, capitalize(tipo2));

        if (p1 == null || p2 == null) {
            System.out.println("Error en selección. Ejecuta de nuevo con nombres válidos (goku, levi, luffy).");
            sc.close();
            return;
        }

        JuegoLucha juego = new JuegoLucha(p1, p2);
        juego.iniciarPelea();

        sc.close();
    }

    private static String capitalize(String s) {
        if (s == null || s.isEmpty()) return s;
        return s.substring(0,1).toUpperCase() + s.substring(1);
    }
}

class JuegoLucha {
    private Personaje a;
    private Personaje b;
    private Random rand = new Random();

    public JuegoLucha(Personaje a, Personaje b) {
        this.a = a;
        this.b = b;
    }

    public void iniciarPelea() {
        System.out.println("\n=== Iniciando pelea ===");
        System.out.printf("%s vs %s\n\n", a.getNombre(), b.getNombre());
        imprimirEstado();

        int ronda = 1;
        while (a.estaVivo() && b.estaVivo()) {
            System.out.printf("\n--- Ronda %d ---\n", ronda);

            // Determinar orden por velocidad (si igual, azar)
            Personaje primero = determinarPrimero();
            Personaje segundo = (primero == a) ? b : a;

            ejecutarTurno(primero, segundo);
            if (segundo.estaVivo()) {
                ejecutarTurno(segundo, primero);
            }

            // Avanzar cooldowns
            a.avanzarTurno();
            b.avanzarTurno();

            imprimirEstado();
            ronda++;
            try { Thread.sleep(350); } catch (InterruptedException e) { /* ignore */ }
        }

        // Resultado
        if (a.estaVivo()) {
            System.out.println("\n>>> " + a.getNombre() + " ha ganado la pelea!");
        } else {
            System.out.println("\n>>> " + b.getNombre() + " ha ganado la pelea!");
        }
    }

    private void ejecutarTurno(Personaje atacante, Personaje defensor) {
        System.out.printf("Turno: %s (HP: %d) -> %s (HP: %d)\n",
                atacante.getNombre(), atacante.getPuntosDeVida(),
                defensor.getNombre(), defensor.getPuntosDeVida());

        // 30% de probabilidad de intentar usar el especial si está disponible
        boolean intentaEspecial = rand.nextDouble() < 0.30;
        if (intentaEspecial && atacante.intentarUsarEspecial(defensor)) {
            // especial usado - ya imprimido por el método especial
        } else {
            atacante.atacar(defensor);
        }
    }

    private Personaje determinarPrimero() {
        if (a.getVelocidad() == b.getVelocidad()) {
            return rand.nextBoolean() ? a : b;
        } else {
            return (a.getVelocidad() > b.getVelocidad()) ? a : b;
        }
    }

    private void imprimirEstado() {
        System.out.println("\nEstado:");
        System.out.printf("%s %s\n", a.getNombre(), a.barraVida());
        System.out.printf("%s %s\n", b.getNombre(), b.barraVida());
    }
}
