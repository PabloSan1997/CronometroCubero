import { calculatePrefinalResutls } from "@/utils/calculatePrefinalResutls";
import { obtenerFormatoMilisegundos } from "@/utils/obtenerFormatoMilisegundos";

export function PreResutltsTeble({
  solves,
  deletePreolve,
}: {
  solves: PreSolve[];
  deletePreolve(i: number): void;
}) {
  const { min, max, media, avg5 } = calculatePrefinalResutls(solves);

  return (
    <div className="area_table">
      <table className="table_solves table_presoles">
        <thead>
          <tr>
            <th className="num">Num</th>
            <th className="tiempo">Tiempo</th>
            <th className="algoritmo">Algoritmo</th>
          </tr>
        </thead>
        <tbody>
          {solves.map((solve, index) => (
            <tr
              key={index}
              className="tr_presolves hover_delete"
              onClick={() => deletePreolve(index)}
            >
              <td>{index + 1}</td>
              <td>{obtenerFormatoMilisegundos(solve.miliseconds)}</td>
              <td className="td_algoritm">{solve.algoritm}</td>
            </tr>
          ))}
        </tbody>
      </table>
      {solves.length == 5 && (
        <table className="finalresults prefinalresutls">
          <thead>
            <tr>
              <th>Max</th>
              <th>Min</th>
              <th>Media</th>
              <th>Avg5</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td>{obtenerFormatoMilisegundos(max)}</td>
              <td>{obtenerFormatoMilisegundos(min)}</td>
              <td>{obtenerFormatoMilisegundos(media)}</td>
              <td>{obtenerFormatoMilisegundos(avg5)}</td>
            </tr>
          </tbody>
        </table>
      )}
    </div>
  );
}
