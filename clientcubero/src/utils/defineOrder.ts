export const defineOrder = (mode: string): TypeOfRequest => {
  if (mode == "date") return "date";
  if (mode == "avg") return "best";
  if (mode == "avgpeor") return "worst";
  return "date";
};
