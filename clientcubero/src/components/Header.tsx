import { UseAppContext } from "@/ProviderContext";
import "../styles/header.scss";
import { HeaderNav } from "./HeaderNav";
import logo from '/logo.svg';

export function Header() {
  const {jwt, logout, userInfo} = UseAppContext();
  const { username, nickname } = userInfo;

  return (
    <header>
      <h1><img src={logo} alt="logo" className="logo"/> <span>Mi Cronometro</span></h1>
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
