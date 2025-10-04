public class PersonajeFactory {
    public static Personaje crear(String tipo, String nombre) {
        switch (tipo.toLowerCase()) {
            case "goku": return new Goku(nombre);
            case "levi":
            case "livai":
            case "levai": return new Levi(nombre);
            case "luffy": return new Luffy(nombre);
            default: return null;
        }
    }
}
