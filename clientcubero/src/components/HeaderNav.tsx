import { Link } from "@tanstack/react-router";



export function HeaderNav() {
  
  return (
    <nav>
        <Link to="/home">Cronometro</Link>
        <Link to="/results" search={{mode:'date'}}>Resultados</Link>
        <Link to="/graph/results">Grafica</Link>
    </nav>
  )
}
