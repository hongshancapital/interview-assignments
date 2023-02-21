
import { Routes, Route } from 'react-router-dom';
import Home from '../views/home';

const Router = () => {

    return (
        <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/home" element={<Home />} />
        </Routes>
    );
};
export default Router;
