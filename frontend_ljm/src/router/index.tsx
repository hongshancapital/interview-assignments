import React, { lazy, Suspense } from 'react';
import {
    createBrowserRouter,
    RouterProvider
} from "react-router-dom";
import Loading from '@com/Loading';
import { DefaultLayout } from "@/views/Layouts/";
const Home = lazy(() => import('@/views/Home/'));
const About = lazy(() => import('@/views/About/'));
const Demo = lazy(() => import('@/views/Demo/'));
const NotFound = lazy(() => import('@/views/NotFound'));

const router = createBrowserRouter([
  {
    path: "/",
    element: <DefaultLayout/>,
    children: [
      {
        path: "",
        index: true,
        element: <Home />,
      },
      {
        path: "about",
        index: true,
        element: <About />,
      },
      {
        path: "demo",
        index: true,
        element: <Demo />,
      },
    ],
  },
  // 404
  {
    path: "*",
    index: true,
    element: <NotFound/>
  },
]);

export default () => (
    <>
      <Suspense fallback={<Loading/>}>
        <RouterProvider router={router} />
      </Suspense>
    </>
)



// import { lazy, Suspense } from "react";
// import { Route, Routes } from "react-router-dom";
// import { DefaultLayout } from "./views/Layouts/index.tsx";
// const HomePage = lazy(() => import("./views/Home/index.tsx"));
// const AboutPage = lazy(() => import("./views/About/index.tsx"));
// const DemoPage = lazy(() => import("./views/Demo/index.tsx"));
//
// function RouteNotFound() {
//   return <div>Not found</div>;
// }
//
// export default function Router() {
//   return (
//     <Suspense fallback={<div>Page is Loading...</div>}>
//       <Routes>
//         <Route path="/" element={<DefaultLayout />}>
//           <Route index element={<HomePage />} />
//           <Route path="about" element={<AboutPage />} />
//           <Route path="demo" element={<DemoPage />} />
//           <Route path="*" element={<RouteNotFound />} />
//         </Route>
//       </Routes>
//     </Suspense>
//   );
// }
// import { BrowserRouter } from "react-router-dom";
//
// function ClientApp() {
//   return (
//     <BrowserRouter>
//       <App />
//     </BrowserRouter>
//   );
// }
