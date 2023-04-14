import { Link, Outlet } from "react-router-dom";
export function DefaultLayout() {
  return (
    <>
      <nav style={{ display: "flex", gap: "8px" }}>
        <Link to="/">Home</Link>
        <Link to="/about">About</Link>
        <Link to="/demo">Demo</Link>
        <Link to="/404">404</Link>
      </nav>
      <Outlet />
    </>
  );
}
