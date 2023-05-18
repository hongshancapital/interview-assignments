import './App.css';
import Carousel from './common/carousel'
import './App.css'

function App() {
    return (
        <div className='App' >
            <div className='big-size-carousel'>
                <Carousel>
                    <div className={'slider-wrapper slider-one'}>
                        <div className={'text-wrapper text-wrapper-one'}>
                            <span className={'text-title'}>xPhone</span>
                            <span>Lost to love.Less top spend.</span>
                            <span>Starting at $399.</span>
                        </div>
                    </div>
                    <div className={'slider-wrapper slider-two'}>
                        <div className={'text-wrapper text-wrapper-two'}>
                            <span className={'text-title'}>Tablet</span>
                            <span>just the right amount of everything.</span>
                        </div>
                    </div>
                    <div className={'slider-wrapper slider-three'}>
                        <div className={'text-wrapper text-wrapper-three'}>
                            <span className={'text-title'}>Buy a Table or xPhone for college.</span>
                            <span>Get airPods.</span>
                        </div>
                    </div>
                </Carousel>
            </div>
        </div>
    )
}

export default App;
