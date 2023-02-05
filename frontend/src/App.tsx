import './App.css';
import airpods from './assets/airpods.png';
import tablet from './assets/tablet.png';
import iphone from './assets/iphone.png';
import { useEffect, useRef } from 'react';

function App() {
  return <div className='App'><Carousel /></div>;
}
function Carousel() {
  return (
    <div className='carousel '>
      <CarouselContent len={3} />
      <CarouselSlider len={3} />
    </div>
  )
}

function CarouselContent(props: { len: number }) {
  const ele = useRef<HTMLDivElement>(null);
  const slideFunc = (i = 0) => {
    console.log({ i })
    const slider = ele.current;
    if (slider) {
      const left = i > 0 ? (0 - i) * 100 + '%' : 0;
      console.log({ left })
      slider.setAttribute("style", `left:${left}`)
    }
  }
  useEffect(() => {
    let i = 0;
    slideFunc(i);
    const timer = setInterval(() => {
      i++;
      if (i == props.len) {
        i = 0;
      }
      slideFunc(i)
    }, 3000)
  })
  return (
    <div className='carousel__innerwrap' ref={ele}>
      <div className='carousel__box  bg--black'>
        <div className='carousel__text color--white'>
          <h1 className='title'>xPhone</h1>
          <p className='text'>Lots to love.Less to spend.<br />Starting at $399</p>
        </div>
        <img className='carousel__img' src={iphone} alt="" />
      </div>
      <div className='carousel__box'>
        <div className='carousel__text'>
          <h3 className='title'>Tablet</h3>
          <p className='text'>Just the right amount of everything.</p>
        </div>
        <img className='carousel__img carousel__img--or2' src={tablet} alt="" />
      </div>
      <div className='carousel__box bg--gray'>
        <div className='carousel__text'>
          <h3 className='title'>Buy  a Tablet or xPhone for college. <br /> Get airPods</h3>
        </div>
        <img className='carousel__img carousel__img--or3' src={airpods} alt="" />
      </div>
    </div>
  )
}

function CarouselSlider(props: { len: number }) {
  const arr = new Array(props.len).fill(0);
  const ele = useRef<HTMLDivElement>(null)
  const slideFunc = (i = 0) => {
    if (ele && ele.current) {
      // 激活当前滑块样式
      const sliders = ele.current.children;
      const activeEle = sliders[i].children[0];
      activeEle.setAttribute("style", "display:block;width:100%");
      // 清除上一个滑块的激活状态
      const lastIndex = i > 0 ? i - 1 : props.len - 1;
      setTimeout(() => {
        const lastSlider = sliders[lastIndex].children[0];
        lastSlider.setAttribute("style", "display:none");
        setTimeout(() => {
          lastSlider.setAttribute("style", "width:0");
        }, 500)
      }, 500)
    }
  }
  useEffect(() => {
    if (ele && ele.current) {
      let i = 0;
      slideFunc(i);
      const timer = setInterval(() => {
        i++;
        if (i == props.len) {
          i = 0;
        }
        slideFunc(i)
      }, 3000)
    };
  })
  return (
    <div className='carousel__slider' ref={ele}>
      {
        arr.map((v, index) => {
          return (
            <div key={`s_${index}`} className='sliderbar'>
              <div className='slider--active'></div>
            </div>
          )
        })
      }
    </div>
  )
}



export default App;
