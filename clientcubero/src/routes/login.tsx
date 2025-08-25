import { createFileRoute, Navigate } from "@tanstack/react-router";
import "../styles/login.scss";
import { UseAppContext } from "@/ProviderContext";
import React from "react";

export const Route = createFileRoute("/login")({
  component: RouteComponent,
});

const loginDataInit: LoginDto = {
  username: "",
  password: "",
};

function RouteComponent() {
  const { jwt, login } = UseAppContext();
  const [logdata, setLogData] = React.useState<LoginDto>(loginDataInit);
  const loginsumit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    login(logdata);
  };
  if(jwt?.trim())
    return <Navigate to="/results" />;
  return (
    <form className="login" onSubmit={loginsumit}>
      <h2>Login</h2>
      <label htmlFor="usernameinput">Username</label>
      <input 
      type="text" 
      placeholder="Escribir"
       id="usernameinput" 
       value={logdata.username}
       onChange={e=>setLogData({...logdata, username: e.target.value})}
       />
      <label htmlFor="passwordinput">Contraseña</label>
      <input 
      type="password" 
      placeholder="Escribir" 
      id="passwordinput"
      value={logdata.password}
      onChange={e=>setLogData({...logdata, password: e.target.value})}
       />
      <button type="submit">Entrar</button>
    </form>
  );
}
