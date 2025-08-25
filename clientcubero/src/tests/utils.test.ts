import { viewResutlsQuery } from '../utils/viewResutlsQuery';
import { calculatePrefinalResutls } from '../utils/calculatePrefinalResutls';
import { describe, expect, it } from "vitest";


const presolvee:PreSolve[] = [
    {
        miliseconds: 5000,
        algoritm: "U' D2 B' D' F' L  U' F' U' F  R2 F2 U2 L2 D2 R2 F2 R2 F  L2"
    },
    {
        miliseconds: 3000,
        algoritm: "R U R' U' R' F R F' U2 R' F R F'"
    },
    {
        miliseconds: 6000,
        algoritm: "F R U R' U' F' U2 R U R' U'"
    },
    {
        miliseconds: 5000,
        algoritm: "L' U' L U L F' L' F U2 L F' L' F"
    },
    {
        miliseconds: 5000,
        algoritm: "B U B' U' B' L B L' U2 B L B' L'"
    }
];
const finalresult:PreFinalResults={
  max:6000,
  min:3000,
  media:4800,
  avg5:5000
}

describe("tests de todos los utils", () => {
  describe("viewResutlsQuery", () => {
    it("deberia devolver el titulo y isCorrectMode true si el modo es date", () => {
      expect(viewResutlsQuery("date")).toEqual({
        title: "fechas del mas nuevo al mas antiguo",
        isCorrectMode: true,
      });
    });
    it("deberia devolver el titulo y isCorrectMode true si el modo es avg", () => {
      expect(viewResutlsQuery("avg")).toEqual({
        title: "AVG5 de mejor a peor",
        isCorrectMode: true,
      });
    });
    it("deberia devolver el titulo y isCorrectMode true si el modo es avgpeor", () => {
      expect(viewResutlsQuery("avgpeor")).toEqual({
        title: "AVG5 de peor a mejor",
        isCorrectMode: true,
      });
    });
    it("deberia devolver titulo vacio e isCorrectMode false si el modo no es ninguno de los anteriores", () => {
      expect(viewResutlsQuery("otro")).toEqual({
        title: "",
        isCorrectMode: false,
      });
    });
  });
  describe('calcular pre final results', ()=>{
    it('array vacio debe regresar todo en cero', ()=>{
      expect(calculatePrefinalResutls([])).toEqual({
        max:0,
        min:0,
        media:0,
        avg5:0
      })
    })
  });
  it('array con datos debe regresar los resultados correctos', ()=>{
    expect(calculatePrefinalResutls(presolvee)).toEqual(finalresult)
  });
});
