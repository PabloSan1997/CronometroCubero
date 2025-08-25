import React from "react";
import { logstorage } from "./utils/logstorage";

const AppContext = React.createContext<IAppContext>({
  userInfo: { username: "", nickname: "" },
  jwt: "",
  login: function (data: LoginDto): void {
    throw new Error("Function not implemented." + data);
  },
  logout: function (): void {
    throw new Error("Function not implemented.");
  },
});

export function ProviderContext({ children }: { children: React.ReactNode }) {
  const [jwt, setJwt] = React.useState<string>(logstorage.read());
  const [userInfo, setUserInfo] = React.useState<UserInfo>({
    username: "",
    nickname: "",
  });

  React.useEffect(() => {
    if (jwt) {
      setUserInfo({ username: "usuario", nickname: "El usuario" });
    }else{
        logout();
    }
  }, [jwt]);

  const login = (data: LoginDto) => {
    console.log("login", data);
    setUserInfo({ username: data.username, nickname: "El nuevo usuario" });
    setJwt("token");
    logstorage.save("token");
  };
  const logout = () => {
    setUserInfo({ username: "", nickname: "" });
    setJwt("");
    logstorage.save("");
  };
  return (
    <AppContext.Provider value={{ userInfo, jwt, login, logout }}>
      {children}
    </AppContext.Provider>
  );
}


export const UseAppContext = () => React.useContext(AppContext);
