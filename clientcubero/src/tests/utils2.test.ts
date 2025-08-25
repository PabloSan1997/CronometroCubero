import { obtenerFormatoMilisegundos } from "../utils/obtenerFormatoMilisegundos";
import { describe, expect, it } from "vitest";

describe("Probando mas utils", () => {
  it("Los milisegundos se formatean correctamente", () => {
    expect(obtenerFormatoMilisegundos(305000)).toBe("5:05.000");
    expect(obtenerFormatoMilisegundos(1552524)).toBe("25:52.524");
    expect(obtenerFormatoMilisegundos(723001)).toBe("12:03.001");
    expect(obtenerFormatoMilisegundos(45000)).toBe("0:45.000");
  });
});
