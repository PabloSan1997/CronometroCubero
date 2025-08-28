export function getLocalTime(d:string):string{
    const nd = new Date(d);
    return nd.toLocaleString();
}