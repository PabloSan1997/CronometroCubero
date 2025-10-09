import { apiprops } from "./apiprops";

async function login(data: LoginDto): Promise<{ jwt: string }> {
  const ft = await fetch(`${apiprops.baseUrl}/user/login`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    credentials: "include",
    body: JSON.stringify(data),
  });
  if (!ft.ok) throw (await ft.json()) as ErrorDto;
  return ft.json();
}

async function getUserInfo(jwt: string): Promise<UserInfo> {
  const ft = await fetch(`${apiprops.baseUrl}/user/userinfo`, {
    method: "GET",
    headers: { Authorization: `Bearer ${jwt}` },
  });
  if (!ft.ok) {
    const error = (await ft.json()) as ErrorDto;
    if (ft.status == 401 && error.message == "expiro") throw "reinicio";
    throw error;
  }
  return ft.json();
}

async function logout() {
  const ft = await fetch(`${apiprops.baseUrl}/user/logout`, {
    method: "POST",
    credentials: "include",
  });
  if (!ft.ok) throw (await ft.json()) as ErrorDto;
}

async function refreshtoken(): Promise<{ jwt: string }> {
  const ft = await fetch(`${apiprops.baseUrl}/user/refreshauth`, {
    method: "POST",
    credentials: "include",
  });
  if (!ft.ok) throw (await ft.json()) as ErrorDto;
  return ft.json();
}

async function getsockettoken(): Promise<{ jwt: string }> {
  const ft = await fetch(`${apiprops.baseUrl}/user/gettokensocket`, {
    method: "POST",
    credentials: "include",
  });
  if (!ft.ok) throw (await ft.json()) as ErrorDto;
  return ft.json();
}

export const userapi = {
  login,
  getUserInfo,
  logout,
  refreshtoken,
  getsockettoken,
};
