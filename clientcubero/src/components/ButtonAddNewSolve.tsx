import { cronoapi } from "@/api/cronoapi";
import { UseAppContext } from "@/ProviderContext";
import { useNavigate } from "@tanstack/react-router";

export function ButtonAddNewSolve({
  solves,
  setShowButton,
}: {
  solves: PreSolve[];
  setShowButton(a: boolean): void;
}) {
  const { jwt } = UseAppContext();
  const navigate = useNavigate();
  const saveResults = () => {
    setShowButton(false);
    cronoapi
      .saveSolve(jwt, { presolves: solves })
      .then(() => {
        navigate({ to: "/results", search: "date" });
      })
      .catch((e) => {
        console.log(e);
        setShowButton(true);
      });
  };
  return (
    <button className="boton add_boton" onClick={saveResults}>
      Agregar
    </button>
  );
}
