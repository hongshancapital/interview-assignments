import React from 'react'
import { HashRouter, Routes, Route } from 'react-router-dom'
import Login from '../components/Login/login'

export default function Router() {
    return (
        <HashRouter>
            <Routes>
                <Route path="/" >
                    <Route index element={<Login />} />
                   
                </Route>
            </Routes>
        </HashRouter >
    )
}
