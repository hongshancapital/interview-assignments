import './App.css'
import Carousel from './components/Carousel'

function App() {
    return (
        <div className="App">
            <Carousel autoplayInterval={3000}>
                <div className="carousel-item">1</div>
                <div className="carousel-item">2</div>
                <div className="carousel-item">3</div>
            </Carousel>
        </div>
    )
}

export default App
