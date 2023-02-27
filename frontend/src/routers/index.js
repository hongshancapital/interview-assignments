import React from 'react';
import { Navigate } from 'react-router-dom';
import Loadable from 'react-loadable';
import Loading from '@/components/Common/Loading';

const Home = Loadable({
  loader: () => import('@/pages/index.tsx'),
  loading: Loading,
});

const routes = [
  {
    path: '/',
    exact: true,
    render: () => <Navigate to={'/home'} />,
  },
  {
    path: '/home',
    component: Home,
    // routes: [
    //   {
    //     path: '/developer/',
    //     exact: true,
    //     component: Home,
    //     render: () => <Redirect to={'/developer/app'} />,
    //   },
    // ],
  },
];

export default routes;
