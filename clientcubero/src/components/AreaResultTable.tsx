import { getLocalTime } from "@/utils/getLocalTime";
import { obtenerFormatoMilisegundos } from "@/utils/obtenerFormatoMilisegundos";

export function AreaResultTable({
  finalresulst,
}: {
  finalresulst: FinalResults;
}) {
  const { max, media, min, avg5, solves, createdAt } = finalresulst;
  return (
    <div className="area_table_results">
      <button>X</button>
      <h3>{getLocalTime(createdAt)}</h3>
      <div className="area_table">
        <table className="table_solves">
          <thead>
            <tr>
              <th className="num">Num</th>
              <th className="tiempo">Tiempo</th>
              <th className="algoritmo">Algoritmo</th>
            </tr>
          </thead>
          <tbody>
            {solves.map((solve, index) => (
              <tr key={index}>
                <td>{index + 1}</td>
                <td>{obtenerFormatoMilisegundos(solve.miliseconds)}</td>
                <td className="td_algoritm">{solve.algoritm}</td>
              </tr>
            ))}
          </tbody>
        </table>
        <table className="finalresults">
          <thead>
            <tr>
              <th>Avg5</th>
              <th>Media</th>
              <th>Max</th>
              <th>Min</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td>{obtenerFormatoMilisegundos(avg5)}</td>
              <td>{obtenerFormatoMilisegundos(media)}</td>
              <td>{obtenerFormatoMilisegundos(max)}</td>
              <td>{obtenerFormatoMilisegundos(min)}</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  );
}
