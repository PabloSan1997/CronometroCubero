

import { Link } from '@tanstack/react-router'

export  function NavResult() {
  return (
    <nav className="results_nav">
        <Link to='/results' search={{mode:'date'}}>Fechas</Link>
        <Link to='/results' search={{mode:'avg'}}>Mejor avg5</Link>
        <Link to='/results' search={{mode:'avgpeor'}}>Peor avg5</Link>
    </nav>
  )
}
