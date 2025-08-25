import { CromometroCom } from "@/components/CromometroCom";
import { PreResutltsTeble } from "@/components/PreResutltsTeble";
import { createFileRoute } from "@tanstack/react-router";
import "../styles/home.scss";
import React from "react";
import { ViewIsLogin } from "@/components/ViewIsLogin";

const presolvesborrame: PreSolve[] = [
  {
    miliseconds: 55489,
    algoritm: "U' D2 B' D' F' L  U' F' U' F  R2 F2 U2 L2 D2 R2 F2 R2 F  L2",
  },
  {
    miliseconds: 55489,
    algoritm: "R U R' U' R' F R F' U2 R' F R F'",
  },
  {
    miliseconds: 55489,
    algoritm: "F R U R' U' F' U2 R U R' U'",
  },
  {
    miliseconds: 55489,
    algoritm: "L' U' L U L F' L' F U2 L F' L' F",
  },
  {
    miliseconds: 4355489,
    algoritm: "B U B' U' B' L B L' U2 B L B' L'",
  },
];

export const Route = createFileRoute("/home")({
  component: RouteComponent,
});

function RouteComponent() {
  const [presolves, setPresolves] =
    React.useState<PreSolve[]>(presolvesborrame);
  const deletePreolve = (i: number) => {
    const newpresolves = presolves.filter((_p, index) => index !== i);
    setPresolves(newpresolves);
  };
  const resetAll = () => {
    setPresolves([]);
  }
  const addNewTime = (newtime: PreSolve) => {
    setPresolves([...presolves, newtime]);
  };
  return (
    <ViewIsLogin>
      <CromometroCom resetAll={resetAll} addNewTime={addNewTime}/>
      <h2 className="title title_solve title_presolves">
        <span onClick={() => setPresolves([])}>Resultados</span>
      </h2>
      <PreResutltsTeble solves={presolves} deletePreolve={deletePreolve} />
    </ViewIsLogin>
  );
}
