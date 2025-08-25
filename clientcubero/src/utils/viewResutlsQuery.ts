export function viewResutlsQuery(mode: string): {
  title: string;
  isCorrectMode: boolean;
} {
  if (mode === "date") 
    return { title: "fechas del mas nuevo al mas antiguo", isCorrectMode: true };
  if (mode === "avg")
    return { title: "AVG5 de mejor a peor", isCorrectMode: true };
  if (mode === "avgpeor")
    return { title: "AVG5 de peor a mejor", isCorrectMode: true };
  return { title: "", isCorrectMode: false };
}
