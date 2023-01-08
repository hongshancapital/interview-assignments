import './App.css';
import {Carousel} from './components/Carousel'

const items = [{
  img: 'iphone',
  desc: 
    <section>
      <h2>xPhone</h2>
      <p>Lots to love.Less to spend.</p>
      <p>Starting at $399.</p>
    </section>,
}, {
  img: 'tablet',
  desc: 
    <section>
      <h2>Tablet</h2>
      <p>Just the right amount of everything.</p>
    </section>,
}, {
  img: 'airpods',
  desc: 
    <section>
      <span>Buy a Tablet or xPhone for college.</span>
      <span>Get arPods.</span>
    </section>,
}]

function App() {
  return <div className='App'>
    <Carousel items={items}/>
  </div>;
}

export default App;
