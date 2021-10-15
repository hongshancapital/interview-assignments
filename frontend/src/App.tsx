import React from 'react';
import './App.css';
import CarouselDemo from './components/Carousel/example/demo';
import { useCounter } from './hooks';

const Counter: React.FC<{}> = () => {
  const [current] = useCounter({ maxCount: 1000 });
  return <span>{current}</span>;
};

function App() {
  return (
    <div className="App">
      <CarouselDemo />
      <Counter />
    </div>
  );
}

export default App;
