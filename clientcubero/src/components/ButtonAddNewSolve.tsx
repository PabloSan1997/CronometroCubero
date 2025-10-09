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
  const { refresh } = UseAppContext();
  const { jwt } = UseAppContext();
  const navigate = useNavigate();
  const saveResults = async () => {
    setShowButton(false);
    try {
      await cronoapi.saveSolve(jwt, { presolves: solves });
      navigate({ to: "/results", search: "date" });
    } catch (error) {
      if (error == "reinicio") {
        const newjwt = await refresh();
        await cronoapi.saveSolve(newjwt, { presolves: solves });
        navigate({ to: "/results", search: "date" });
      }
      setShowButton(true);
    }
  };
  return (
    <button className="boton add_boton" onClick={saveResults}>
      Agregar
    </button>
  );
}
