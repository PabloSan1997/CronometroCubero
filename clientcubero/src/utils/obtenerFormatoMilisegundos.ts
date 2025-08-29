

export function obtenerFormatoMilisegundos(milisegundos: number): string {
  const minutos = Math.floor(milisegundos / 60000);
  const segundos = Math.floor((milisegundos % 60000) / 1000);
  const centesimas = Math.floor(milisegundos % 1000);

  const minutosStr = minutos > 0 ? `${minutos}` : "0";
  const segundosStr = segundos.toString().padStart(2, "0");
  const centesimasStr = centesimas.toString().padStart(3, "0");

  return `${minutosStr}:${segundosStr}.${centesimasStr}`;
}