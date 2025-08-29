import React from "react";
import { logstorage } from "./utils/logstorage";
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
  logout: function (): void {
    throw new Error("Function not implemented.");
  },
  message: "",
  setMessage: function (msg: string): void {
    throw new Error("Function not implemented." + msg);
  },
  stompClient: new Client(),
});

export function ProviderContext({ children }: { children: React.ReactNode }) {
  const [jwt, setJwt] = React.useState<string>(logstorage.read());
  const [userInfo, setUserInfo] = React.useState<UserInfo>({
    username: "",
    nickname: "",
  });
  const [message, setMessage] = React.useState<string>("");
  const stompref = React.useRef(new Client());

  React.useEffect(() => {
    if (jwt.trim() && userInfo.username.trim()) {
      stompref.current.brokerURL = `${apiprops.baseSocket}?jwt=${jwt}`;
    }

    return () => {
      stompref.current.brokerURL = "";
    };
  }, [jwt, userInfo.username]);

  React.useEffect(() => {
    if (jwt) {
      userapi
        .getUserInfo(jwt)
        .then(setUserInfo)
        .catch(() => {
          logout();
        });
    } else {
      logout();
    }
  }, [jwt]);

  const login = (data: LoginDto) => {
    setMessage("");
    userapi
      .login(data)
      .then((res) => {
        setJwt(res.jwt);
        logstorage.save(res.jwt);
        setMessage("");
      })
      .catch((err) => {
        const { message } = err as ErrorDto;
        setMessage(message);
      });
  };
  const logout = () => {
    setUserInfo({ username: "", nickname: "" });
    setJwt("");
    logstorage.save("");
    stompref.current.deactivate();
  };
  return (
    <AppContext.Provider
      value={{
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
