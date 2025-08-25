import { NavResult } from "@/components/NavResult";
import { viewResutlsQuery } from "@/utils/viewResutlsQuery";
import { createFileRoute, Navigate } from "@tanstack/react-router";
import "../styles/results.scss";
import { AreaResultTable } from "@/components/AreaResultTable";
import { ViewIsLogin } from "@/components/ViewIsLogin";

export const Route = createFileRoute("/results")({
  component: RouteComponent,
});

const finalResutlsborrame: FinalResults[] = [
  {
    id: 1,
    date: "2024-06-15T12:00:00Z",
    max: 60000,
    min: 45000,
    media: 52000,
    avg5: 51000,
    solves: [
      {
        id: "1",
        miliseconds: 55000,
        algoritm: "R U R' U' R' F R F' U2 R' F R F'",
      },
      { id: "2", miliseconds: 52000, algoritm: "F R U R' U' F' U2 R U R' U'" },
      {
        id: "3",
        miliseconds: 60000,
        algoritm: "L' U' L U L F' L' F U2 L F' L' F",
      },
      {
        id: "4",
        miliseconds: 45000,
        algoritm: "B U B' U' B' L B L' U2 B L B' L'",
      },
      {
        id: "5",
        miliseconds: 48000,
        algoritm: "U' D2 B' D' F' L  U' F' U' F  L2",
      },
    ],
  },
  {
    id: 2,
    date: "2024-06-14T12:00:00Z",
    max: 58000,
    min: 46000,
    media: 53000,
    avg5: 52000,
    solves: [
      {
        id: "1",
        miliseconds: 57000,
        algoritm: "R U R' U' R' F R F' U2 R' F R F'",
      },
      { id: "2", miliseconds: 52000, algoritm: "F R U R' U' F' U2 R U R' U'" },
      {
        id: "3",
        miliseconds: 58000,
        algoritm: "L' U' L U L F' L' F U2 L F' L' F",
      },
      {
        id: "4",
        miliseconds: 46000,
        algoritm: "B U B' U' B' L B L' U2 B L B' L'",
      },
      {
        id: "5",
        miliseconds: 49000,
        algoritm: "U' D2 B' D' F' L  U' F' U' F  L2",
      },
    ],
  },
];
function RouteComponent() {
  const { mode } = Route.useSearch() as { mode: string };
  const { title, isCorrectMode } = viewResutlsQuery(mode);

  if (!isCorrectMode)
    return <Navigate to="/results" search={{ mode: "date" }} />;
  return (
    <ViewIsLogin>
      <NavResult />
      <h2 className="title title_solve">Resultados: {title}</h2>
      <div className="area_resutls">
        {finalResutlsborrame.map((p) => (
          <AreaResultTable key={p.id} finalresulst={p} />
        ))}
      </div>
    </ViewIsLogin>
  );
}
