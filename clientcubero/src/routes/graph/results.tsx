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

const finalSolves: FinalResultGraph[] = [
  { createdAt: "2023-01-01", avg5: 544421, media: 154441 },
  { createdAt: "2023-02-01", avg5: 1554315, media: 15158 },
  { createdAt: "2023-03-01", avg5: 11875, media: 1452855 },
  { createdAt: "2023-04-01", avg5: 88768, media: 9654654 },
  { createdAt: "2023-05-01", avg5: 254654, media: 365454 },
];

function RouteComponent() {
  const labels = finalSolves.map((solve) => solve.createdAt);
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
