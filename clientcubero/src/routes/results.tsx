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
import React from "react";

export const Route = createFileRoute("/results")({
  component: RouteComponent,
});

function RouteComponent() {
  const { mode } = Route.useSearch() as { mode: string };
  const { title, isCorrectMode } = viewResutlsQuery(mode);
  const { jwt, stompClient } = UseAppContext();
  const [fresult, setFresult] = React.useState<FinalResults[]>([]);
  const methodget = defineOrder(mode);
  const { data, isFetched, isError, isPending } = useQuery({
    queryKey: ["viewresults", mode, jwt],
    queryFn: () => cronoapi.findSolves(jwt, methodget),
  });

  React.useEffect(() => {
    if (data && data.length != 0) {
      setFresult(data);
    }else{
      setFresult([]);
    }

    return ()=>{
      setFresult([]);
    }
  }, [data?.length, mode]);

  const deleteOne = (id: string) => {
    stompClient.publish({
      destination: "/request/deleteone",
      body: JSON.stringify({ id }),
    });
  };

  React.useEffect(() => {
    if (stompClient.brokerURL?.trim()) {
      stompClient.onConnect = () => {
        stompClient.subscribe("/user/deleteresult/deleteone", (req) => {
          const { id } = JSON.parse(req.body) as { id: string };
          setFresult((d) => d.filter((p) => p.id !== id));
        });
      };
      stompClient.activate();
    }
    return () => {
      stompClient.deactivate();
    };
  }, [stompClient.brokerURL]);

  if (!isCorrectMode)
    return <Navigate to="/results" search={{ mode: "date" }} />;
  return (
    <ViewIsLogin>
      <NavResult />
      <h2 className="title title_solve">Resultados: {title}</h2>
      <div className="area_resutls">
        {isFetched &&
          !isError &&
          !isPending &&
          fresult.map((p) => (
            <AreaResultTable
              key={p.id}
              finalresulst={p}
              deleteOne={deleteOne}
            />
          ))}
      </div>
    </ViewIsLogin>
  );
}
