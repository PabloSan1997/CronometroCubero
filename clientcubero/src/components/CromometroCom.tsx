import { UseAppContext } from "@/ProviderContext";
import { obtenerFormatoMilisegundos } from "@/utils/obtenerFormatoMilisegundos";
import React from "react";

export function CromometroCom({
  resetAll,
  addNewTime,
  countsolves,
}: {
  resetAll(): void;
  addNewTime(d: PreSolve): void;
  countsolves: number;
}) {
  const [viewhover, setViewOver] = React.useState(false);
  const [algoritm, setAlgoritm] = React.useState("");
  const [count, setCount] = React.useState<number>(0);
  const intervalo = React.useRef<NodeJS.Timeout | null>(null);
  const { stompClient } = UseAppContext();
  const [active, setActive] = React.useState(false);

  const resetTotal = () => {
    resetAll();
    stompClient.publish({ destination: "/request/getalgoritm" });
    if (intervalo.current) clearInterval(intervalo.current);
    setCount(0);
  };

  React.useEffect(() => {
    if (stompClient.brokerURL?.trim()) {
      stompClient.onConnect = () => {
        stompClient.subscribe("/user/newalgoritm/one", (res) => {
          const d = JSON.parse(res.body) as { algoritm: string };
          setAlgoritm(d.algoritm);
        });
        stompClient.publish({ destination: "/request/getalgoritm" });
      };
      stompClient.activate();
    }
    return () => {
      stompClient.deactivate();
    };
  }, [stompClient.brokerURL]);

  React.useEffect(() => {
    function playcron(e: KeyboardEvent) {
      if (e.key == " ") {
        window.removeEventListener("keyup", playcron);
        window.addEventListener("keypress", pausecron);
        const fist = new Date();
        intervalo.current = setInterval(() => {
          setCount(new Date().getTime() - fist.getTime());
        }, 16);
      }
    }
    function pausecron() {
      if (intervalo.current) clearInterval(intervalo.current);
      window.removeEventListener("keypress", pausecron);
      setTimeout(() => {
        setActive(true);
        window.addEventListener("keyup", playcron);
      }, 300);
    }
    if (countsolves < 5) window.addEventListener("keyup", playcron);

    return () => {
      window.removeEventListener("keyup", playcron);
      window.removeEventListener("keypress", pausecron);
      if (intervalo.current) clearInterval(intervalo.current);
    };
  }, [countsolves < 5]);

  React.useEffect(() => {
    if (active) {
      addNewTime({ algoritm, miliseconds: count });
      setActive(false);
      stompClient.publish({ destination: "/request/getalgoritm" });
    }
  }, [active]);

  return (
    <div className="area_cronometro">
      <p
        className="algoritm"
        onClick={resetTotal}
        onMouseEnter={() => setViewOver(true)}
        onMouseLeave={() => setViewOver(false)}
      >
        {viewhover ? "Reiniciar" : algoritm}
      </p>
      <span className="time">{obtenerFormatoMilisegundos(count)}</span>
    </div>
  );
}
