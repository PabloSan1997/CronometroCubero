import { UseAppContext } from "@/ProviderContext";
import { Navigate } from "@tanstack/react-router";
import type React from "react";

export function ViewIsLogin({ children }: { children: React.ReactNode }) {
  const { jwt } = UseAppContext();
  if (!jwt?.trim()) return <Navigate to="/login" />;

  return <>{children}</>;
}
