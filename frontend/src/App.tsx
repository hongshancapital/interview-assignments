import './App.css';
import Carousel from './components/Carousel/index';

function App() {
  return (<div className='App'>
            <Carousel class='app_content'>
                <div className="item one">1</div>
                <div className="item two">2</div>
                <div className="item three">3</div>
            </Carousel>
    </div>);
}

export default App;

