import { cronoapi } from "@/api/cronoapi";
import { UseAppContext } from "@/ProviderContext";
import { useNavigate } from "@tanstack/react-router";

export function ButtonAddNewSolve({ solves }: { solves: PreSolve[] }) {
  const { jwt } = UseAppContext();
  const navigate = useNavigate();
  const saveResults = () => {
    cronoapi.saveSolve(jwt, { presolves: solves }).then(() => {
      navigate({ to: "/results", search: "date" });
    }).catch(console.log);
  };
  return (
    <button className="boton add_boton" onClick={saveResults}>Agregar</button>
  );
}
