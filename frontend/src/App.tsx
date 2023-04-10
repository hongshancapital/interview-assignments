import './App.css';
import Carousel from './components/Carousel'
function App() {
  let img = [
    {
        name:'第一张幻灯片',
        img:''
    },
    {
        name:'第二张幻灯片',
        img:''
    },
    {
        name:'第三张幻灯片',
        img:''
    },
]
  return <div className='App'>{/* write your component here */}
        <Carousel img={img} delay={2000}></Carousel>
  </div>;
}

export default App;
