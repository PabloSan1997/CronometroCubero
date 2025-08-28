import { createFileRoute } from "@tanstack/react-router";
import "../../styles/graph.scss";
export const Route = createFileRoute("/graph/results")({
  component: RouteComponent,
});

import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  Title,
  Tooltip,
  Legend,
} from "chart.js";
import { Line } from "react-chartjs-2";
import { ViewIsLogin } from "@/components/ViewIsLogin";
import { obtenerFormatoMilisegundos } from "@/utils/obtenerFormatoMilisegundos";
import { useQuery } from "@tanstack/react-query";
import { UseAppContext } from "@/ProviderContext";
import { cronoapi } from "@/api/cronoapi";
import { getLocalTime } from "@/utils/getLocalTime";

ChartJS.register(
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  Title,
  Tooltip,
  Legend
);

const options = {
  responsive: true,
  plugins: {
    legend: {
      position: "top" as const,
      labels: {
        color: "#f5f5f5ff", // Color de las etiquetas de la leyenda
        font: {
          size: 14,
        },
      },
    },
    title: {
      display: true,
      text: "Tus resultados",
      color: "#ffffff", // Color del título
      font: {
        size: 18,
        weight: "bold" as const,
      },
    },
    tooltip: {
      titleColor: "#ffffffff", // Color del título del tooltip
      bodyColor: "#ffffff", // Color del texto del tooltip
      backgroundColor: "rgba(24, 2, 2, 0.86)", // Fondo del tooltip
    },
  },
  scales: {
    x: {
      ticks: {
        color: "#ffffff", // Color de las etiquetas del eje X
        font: {
          size: 12,
        },
      },
      grid: {
        color: "rgba(255, 255, 255, 0.2)", // Color de las líneas de grid del eje X
      },
      title: {
        display: true,
        text: "Fechas de solves",
        color: "#ffffff", // Color del título del eje X
        font: {
          size: 14,
        },
      },
    },
    y: {
      ticks: {
        color: "#ffffff", // Color de las etiquetas del eje Y
        font: {
          size: 12,
        },
        callback: function (tickValue: string | number) {
          // Convertir milisegundos a segundos y devolver como string
          const num =
            typeof tickValue === "number" ? tickValue : Number(tickValue);
          return obtenerFormatoMilisegundos(num);
        },
      },
      grid: {
        color: "rgba(255, 255, 255, 0.2)", // Color de las líneas de grid del eje Y
      },
      title: {
        display: true,
        text: "Resultados en segundos",
        color: "#ffffff", // Color del título del eje Y
        font: {
          size: 14,
        },
      },
    },
  },
};



function RouteComponent() {
  const {jwt} = UseAppContext();
  
  const {data:datagraph, isError, isFetched, isPending} = useQuery({
    queryKey:['graph', jwt],
    queryFn:()=>cronoapi.findGraphValues(jwt)
  });
  
  const finalSolves:FinalResultGraph[] = !isError && isFetched && !isPending?datagraph:[];
  
  const labels = finalSolves.map((solve) => getLocalTime(solve.createdAt));
  const avg5Data = finalSolves.map((solve) => solve.avg5);
  const mediaData = finalSolves.map((solve) => solve.media);
  return (
    <ViewIsLogin>
      <h2 className="title title_graph">Visualiza tus resultados</h2>
      <div className="area_graph">
        <Line
          className="graph"
          options={options}
          data={{
            labels,
            datasets: [
              {
                label: "Media",
                data: mediaData,
                borderColor: "rgba(255, 241, 44, 1)",
                backgroundColor: "rgba(146, 148, 7, 0.5)",
              },
              {
                label: "Avg5",
                data: avg5Data,
                borderColor: "rgba(24, 97, 255, 1)",
                backgroundColor: "rgba(34, 0, 156, 1)",
              },
            ],
          }}
        />
      </div>
    </ViewIsLogin>
  );
}
