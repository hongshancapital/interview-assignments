import './App.css';
import Carousel,{carouselRef} from './common/carousel'
import { useRef } from "react"
import './App.css'

function App() {
    const test = useRef<carouselRef>(null)
    const next = ()=>{
        test?.current?.next()
    }
    const prev = ()=>{
        test?.current?.prev()
    }
    return (
        <div className='App' >
            <p>小尺寸+切换按钮</p>
            <div className='small-size-carousel'>
                <Carousel  autoplay={false} showBtn={true}>
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
            <p>中尺寸+外层自定义按钮</p>
            <div className='middle-size-carousel'>
                <Carousel ref={test}  dotPosition='top'>
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
                <div className='prev-btn' onClick={()=>{prev()}}>上一个</div>
                <div className='next-btn' onClick={()=>{next()}}>下一个</div>
            </div>
            <p>大尺寸+水平自动播放 5s延迟</p>
            <div className='big-size-carousel'>
                <Carousel  autoplay={true} delay={5}>
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
            <p>大尺寸+垂直自动播放</p>
            <div className='big-size-carousel'>
                <Carousel  autoplay={true} dotPosition='right'>
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
