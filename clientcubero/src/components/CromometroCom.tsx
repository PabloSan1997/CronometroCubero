import React from 'react';

export function CromometroCom({resetAll}:{resetAll():void, addNewTime(d:PreSolve):void}) {
  const [viewhover, setViewOver] = React.useState(false);
  const algoritm = "U' D2 B' D' F' L U' F' U' F R2 F2 U2 L2 D2 R2 F2 R2 F L2";

  return (
    <div className="area_cronometro">
      <p className="algoritm" onClick={resetAll} onMouseEnter={()=> setViewOver(true)} onMouseLeave={()=>setViewOver(false)}>{viewhover?'Reiniciar':algoritm}</p>
      <span className="time">00:00.00</span>
    </div>
  );
}
