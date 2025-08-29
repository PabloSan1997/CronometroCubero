import { CromometroCom } from "@/components/CromometroCom";
import { PreResutltsTeble } from "@/components/PreResutltsTeble";
import { createFileRoute } from "@tanstack/react-router";
import "../styles/home.scss";
import React from "react";
import { ViewIsLogin } from "@/components/ViewIsLogin";
import { ButtonAddNewSolve } from "@/components/ButtonAddNewSolve";

export const Route = createFileRoute("/home")({
  component: RouteComponent,
});

function RouteComponent() {
  const [presolves, setPresolves] = React.useState<PreSolve[]>([]);

  const deletePreolve = (i: number) => {
    const newpresolves = presolves.filter((_p, index) => index !== i);
    setPresolves(newpresolves);
  };

  const resetAll = () => {
    setPresolves([]);
  };

  const addNewTime = (newtime: PreSolve) => {
    setPresolves([...presolves, newtime]);
  };

  return (
    <ViewIsLogin>
      <CromometroCom
        resetAll={resetAll}
        addNewTime={addNewTime}
        countsolves={presolves.length}
      />
      <div className="area_title_button">
        <h2 className="title title_solve title_presolves">
          <span onClick={() => setPresolves([])}>Resultados</span>
        </h2>
        {presolves.length == 5 && (<ButtonAddNewSolve solves={presolves}/>)}
      </div>
      <PreResutltsTeble solves={presolves} deletePreolve={deletePreolve} />
    </ViewIsLogin>
  );
}
