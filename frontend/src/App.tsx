import React, { Suspense } from 'react';
// import routes from './routers/index.js';
// import { renderRoutes } from 'react-router-config';
import {BrowserRouter, Route, Routes} from 'react-router-dom';
import Loadable from 'react-loadable';
import Loading from '@/components/Common/Loading';

import "./App.css";

const Home = Loadable({
  loader: () => import('@/pages/index'),
  loading: Loading,
});

function App() {
  // return <div className="App"><Carousel /></div>;
  return (
    <Suspense fallback={<div></div>}>
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Home/>} />
        </Routes>
      </BrowserRouter>
    </Suspense>
  )
}

export default App;
