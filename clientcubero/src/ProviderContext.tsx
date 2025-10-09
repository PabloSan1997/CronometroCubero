import React from "react";
import { userapi } from "./api/userapi";
import { Client } from "@stomp/stompjs";
import { apiprops } from "./api/apiprops";

interface IAppContextFull extends IAppContext {
  stompClient: Client;
}

const AppContext = React.createContext<IAppContextFull>({
  userInfo: { username: "", nickname: "" },
  jwt: "",
  login: function (data: LoginDto): void {
    throw new Error("Function not implemented." + data);
  },

  message: "",
  setMessage: function (msg: string): void {
    throw new Error("Function not implemented." + msg);
  },
  stompClient: new Client(),
  logout: function (): Promise<void> {
    throw new Error("Function not implemented.");
  },
  refresh: function (): Promise<string> {
    throw new Error("Function not implemented.");
  }
});

export function ProviderContext({ children }: { children: React.ReactNode }) {
  const [jwt, setJwt] = React.useState<string>("init");
  const [jwtsocket, setJwtsocket] = React.useState<string>("init");
  const [userInfo, setUserInfo] = React.useState<UserInfo>({
    username: "",
    nickname: "",
  });

  const [message, setMessage] = React.useState<string>("");
  const stompref = React.useRef(new Client());
  const refresh = async () => {
    try {
      const res = await userapi.refreshtoken();
      setJwt(res.jwt);
      return res.jwt;
    } catch (error) {
      setJwt("");
      setUserInfo({ username: "", nickname: "" });
      return '';
    }
  };
  const refreshtoken = async () => {
    try {
      const jwttoken = await userapi.getsockettoken();
      setJwtsocket(jwttoken.jwt);
    } catch (error) {
      setJwt("");
    }
  };

  React.useEffect(() => {
    refresh();
    refreshtoken();
  }, []);

  React.useEffect(() => {
    if (jwtsocket.trim() && userInfo.username.trim()) {
      stompref.current.brokerURL = `${apiprops.baseSocket}?jwt=${jwtsocket}`;
    }

    return () => {
      stompref.current.brokerURL = "";
    };
  }, [jwtsocket, userInfo.username]);

  React.useEffect(() => {
    (async () => {
      if (jwt && jwt != "init") {
        try {
          const res = await userapi.getUserInfo(jwt);
          setUserInfo(res);
        } catch (error) {
          if(error ==  "reinicio")
            refresh();
        }
      }
    })();
  }, [jwt]);

  const login = (data: LoginDto) => {
    setMessage("");
    userapi
      .login(data)
      .then((res) => {
        setJwt(res.jwt);
        setMessage("");
        refreshtoken();
      })
      .catch((err) => {
        const { message } = err as ErrorDto;
        setMessage(message);
      });
  };
  const logout = async () => {
    try {
      await userapi.logout();
      setUserInfo({ username: "", nickname: "" });
      setJwt("");

      stompref.current.deactivate();
    } catch (error) {}
  };
  return (
    <AppContext.Provider
      value={{
        refresh,
        userInfo,
        jwt,
        login,
        logout,
        message,
        setMessage,
        stompClient: stompref.current,
      }}
    >
      {children}
    </AppContext.Provider>
  );
}

export const UseAppContext = () => React.useContext(AppContext);
