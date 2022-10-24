import React, { useLayoutEffect, useState } from 'react';
import Switch from './components/Switch';
import airPods from './assets/airpods.png';
import tablet from './assets/tablet.png';
import iphone from './assets/iphone.png';
import eazyDebounce from './eazyDebounce';
import "./App.css";

function App() {
  const [style, setStyle] = useState<{ width: number, height: number }>();

  useLayoutEffect(() => {
    const handleResize = eazyDebounce(() => {
      const { clientHeight, clientWidth } = document.documentElement;
      setStyle({ width: clientWidth, height: clientHeight });
    });
    handleResize();
    window.addEventListener('resize', handleResize);
    return () => window.removeEventListener('resize', handleResize);
  }, []);
  return (
    <div className="App" style={style}>
      <Switch
        pages={[
          {
            title: 'xPhone',
            content:'Lots to love. Less to spend. Starting at $399.',
            style: 'black',
            logo: iphone
          },
          {
            title: 'Tablet',
            content:'Just the right amount of everything',
            style: 'white',
            logo: tablet
          },
          {
            title: 'Buy a Tabled or xPhone for college. Get arPods',
            style: 'gray',
            logo: airPods
          }
        ]}
      />
    </div>
  );
}

export default App;
