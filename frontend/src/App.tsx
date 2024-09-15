import './App.css';
import Carousel from './components/carousel'

const list: Array<string> = [
  require('./assets/airpods.png'),
  require('./assets/iphone.png'),
  require('./assets/tablet.png')
]
function App() {
  return <div className='App'>
    {/* write your component here */}
    <Carousel list={list} /></div>;
}

export default App;
