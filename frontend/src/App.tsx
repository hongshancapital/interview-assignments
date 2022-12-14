import './App.css';
import Carousel from './Carousel';

function App() {
  return (
    <div className='App'>
      <Carousel style={{width: '800px', height: '500px', border: '1px solid black'}}>
        <div style={{height: '100%', overflow: 'hidden', backgroundColor: 'black', color: 'white'}}>
          <h2>xPhone</h2>
          <p>Lots to love. Less to spend.</p>
          <p>Starting at $399.</p>
        </div>
        <div>
          <h2>Tablet</h2>
          <p>Just the right amount of everything.</p>
        </div>
        <div style={{height: '100%', overflow: 'hidden', backgroundColor: '#f2f2f2'}}>
          <h2>Buy a Tablet or xPhone for college.</h2>
          <h2>Get arPods</h2>
        </div>
      </Carousel>
    </div>
  );
}

export default App;
