import { apiprops } from "./apiprops";

async function findSolves(jwt: string, queryname:TypeOfRequest): Promise<FinalResults[]> {
    const ft = await fetch(`${apiprops.baseUrl}/cronometro/${queryname}?size=100`, {
        method: "GET",
        headers: {
            Authorization: `Bearer ${jwt}`,
        },
    });
    if (!ft.ok) throw (await ft.json()) as ErrorDto;
    return ft.json();
}

async function findGraphValues(jwt: string): Promise<FinalResultGraph[]> {
    const ft = await fetch(`${apiprops.baseUrl}/cronometro/graph`, {
        method: "GET",
        headers: {
            Authorization: `Bearer ${jwt}`,
        },
    });
    if (!ft.ok) throw (await ft.json()) as ErrorDto;
    return ft.json();
}

async function saveSolve(jwt: string, data: ListPresolve): Promise<FinalResults> {
    const ft = await fetch(`${apiprops.baseUrl}/cronometro`, {
        method: "POST",
        headers: {
            Authorization: `Bearer ${jwt}`,
            'Content-Type':'application/json'
        },
        body: JSON.stringify(data),
    });
    if (!ft.ok) throw (await ft.json()) as ErrorDto;
    return ft.json();
}



export const cronoapi = { findSolves, saveSolve, findGraphValues };