import { apiprops } from "./apiprops";



async function login(data:LoginDto):Promise<{jwt:string}>{
    const ft = await fetch(`${apiprops.baseUrl}/user/login`,{
        method:"POST",
        headers:{"Content-Type":"application/json"},
        body:JSON.stringify(data)
    });
    if(!ft.ok) throw await ft.json() as ErrorDto;
    return ft.json();
}

async function getUserInfo(jwt:string):Promise<UserInfo>{
    const ft = await fetch(`${apiprops.baseUrl}/user/userinfo`, {
        method:"GET",
        headers:{"Authorization":`Bearer ${jwt}`}
    });
    if(!ft.ok) throw await ft.json() as ErrorDto;
    return ft.json();
} 

export const userapi = {
    login,
    getUserInfo
}