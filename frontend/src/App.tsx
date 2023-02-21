import './App.css';
import { IntlProvider } from 'react-intl-hooks';
import { useEffect, useState } from 'react';
import { defaultLocale, languages } from './locale';
import Router from './router';

function App() {
    const [locale, setLocale] = useState<languages>((defaultLocale as languages));
    useEffect(() => {
        window.requestAnimFrame = (function () {
            return window.requestAnimationFrame ||
                window.webkitRequestAnimationFrame ||
                window.mozRequestAnimationFrame ||
                window.oRequestAnimationFrame ||
                window.msRequestAnimationFrame ||
                function (callback: Function, element: any) {
                    window.setTimeout(callback, 1000 / 60);
                };
        })();
    }, []);
    return <IntlProvider locale={locale}>
        <Router></Router>
    </IntlProvider>;
}

export default App;
