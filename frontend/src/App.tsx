import { CSSProperties, ReactNode } from 'react';
import './App.css';
import Carousel from './components/carousel';

function App() {
  const defaultStyles: CSSProperties = {
    width: '100%',
    height: '100%',
    display: 'flex',
    flexDirection: 'column',
    justifyContent: 'center',
    alignItems: 'center',
  };
  const items: { name: string; dom: ReactNode }[] = [
    {
      name: 'iphone',
      dom: (
        <>
          <h2 className="title">xPhone</h2>
          <div className="text">Lots to love. Less to spend.</div>
          <div>Starting at $399.</div>
        </>
      ),
    },
    {
      name: 'tablet',
      dom: (
        <>
          <h2 className="title">Tablet</h2>
          <div className="text">Just the right amount of everything.</div>
        </>
      ),
    },
    {
      name: 'airpods',
      dom: (
        <>
          <h2 className="title">Buy a Tablet or xPhone for college.</h2>
          <h2 className="text">Get airPods.</h2>
        </>
      ),
    },
  ];

  return (
    <div className="App">
      <div>learn react</div>
      <Carousel style={{ height: '100vh' }}>
        {items.map((item) => (
          <div key={item.name} className={item.name} style={defaultStyles}>
            {item.dom}
          </div>
        ))}
      </Carousel>
    </div>
  );
}

export default App;
