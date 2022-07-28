import React from "react";
import { useRoutes } from "react-router-dom";
import Index from "../view/index";
import { RouteObject } from "./routerObject";
export const routesArr: RouteObject[] = [
  {
    path: "/index",
    element: <Index />,
  },
  {
    path: "*",
    element: <Index />,
  },
];
const Router = () => {
  const routes = useRoutes(routesArr);
  return routes;
};
export default Router;
