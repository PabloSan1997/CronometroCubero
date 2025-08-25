import { UseAppContext } from "@/ProviderContext";
import "../styles/header.scss";
import { HeaderNav } from "./HeaderNav";

export function Header() {
  const {jwt, logout, userInfo} = UseAppContext();
  const { username, nickname } = userInfo;
  return (
    <header>
      <h1>Mi cronometro</h1>
      {jwt?.trim() && (
        <>
          <HeaderNav />
          <div className="area_user_info">
            <span>{username}</span>
            <span>{nickname}</span>
          </div>
          <button onClick={logout}>Cerrar sesión</button>
        </>
      )}
    </header>
  );
}
