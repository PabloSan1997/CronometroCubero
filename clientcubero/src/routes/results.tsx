import { NavResult } from "@/components/NavResult";
import { viewResutlsQuery } from "@/utils/viewResutlsQuery";
import { createFileRoute, Navigate } from "@tanstack/react-router";
import "../styles/results.scss";
import { AreaResultTable } from "@/components/AreaResultTable";
import { ViewIsLogin } from "@/components/ViewIsLogin";
import { useQuery } from "@tanstack/react-query";
import { UseAppContext } from "@/ProviderContext";
import { cronoapi } from "@/api/cronoapi";
import { defineOrder } from "@/utils/defineOrder";

export const Route = createFileRoute("/results")({
  component: RouteComponent,
});

function RouteComponent() {
  const { mode } = Route.useSearch() as { mode: string };
  const { title, isCorrectMode } = viewResutlsQuery(mode);
  const {jwt} = UseAppContext();
  const methodget = defineOrder(mode);
  const {data, isFetched, isError, isPending} = useQuery({
    queryKey:['viewresults', mode, jwt],
    queryFn:()=> cronoapi.findSolves(jwt, methodget)
  })
  if (!isCorrectMode)
    return <Navigate to="/results" search={{ mode: "date" }} />;
  return (
    <ViewIsLogin>
      <NavResult />
      <h2 className="title title_solve">Resultados: {title}</h2>
      <div className="area_resutls">
        {isFetched && !isError && !isPending && data.map((p) => (
          <AreaResultTable key={p.id} finalresulst={p} />
        ))}
      </div>
    </ViewIsLogin>
  );
}
