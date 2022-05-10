import React, { FC } from 'react';
import { HashRouter, Routes, Route } from 'react-router-dom';
import Home from '../Home';
import Short from '../Short';

const Layout: FC = () => {
    return (
        <div className="layout">
            <h1>Carousel基本使用</h1>
            <HashRouter>
                <Routes>
                    <Route path="/" element={<Home />} />
                    <Route path="short" element={<Short />} />
                </Routes>    
            </HashRouter>
        </div>
    )
};

export default Layout;
