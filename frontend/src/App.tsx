import './App.css';
import {Carousel} from './components';

const Item = Carousel.Item;

function App() {
    return <div className='App' title='彭程的前端面试'>
        <Carousel showIndicators>
            <Item key='1'>
                <div className="block red">1</div>
            </Item>
            <Item key='2'>
                <div className="block blue">2</div>
            </Item>
            <Item key='3'>
                <div className="block green">3</div>
            </Item>
        </Carousel>
    </div>;
}

export default App;
