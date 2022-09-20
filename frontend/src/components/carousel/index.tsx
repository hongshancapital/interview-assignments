import * as React from 'react';
import './carousel.css';
interface CarouselProps {
  children: Array<React.ReactNode>;
  interval: number;
}
function Carousel(props: CarouselProps) {
  const [active, setActive] = React.useState(0);
  const [process, setProcess] = React.useState(0);
  const next = () => {
    setProcess(0)
    const length = props.children.length
    const newActive = (active + 1) > (length - 1) ? 0 : (active + 1)
    setActive(newActive)
  }
  React.useEffect(() => {
    setTimeout(() => {
      const newProcess = process + 20
      if (newProcess >= props.interval) {
        next()
      } else {
        setProcess(newProcess)
      }
    }, 20);
  });
  const style = {
    width: props.children.length * 100 + 'vw',
    transform: `translateX(-${active}00vw)`
  }
  return (<div className="carousel">
    <div className='carousel-inside' style={style}>
      {
        props.children.map((prop: React.ReactNode, index) => {
          return <div className='carousel-item' key={index}>
            {prop}
          </div>
        })
      }
    </div>
    <div className='sclick'>
      {
        props.children.map((prop: React.ReactNode, index: number) => {
          return <div className='sclick-item' key={index}>
            <div className='sclick-bg' >
              <div className='sclick-inside' style={{ 
                width: (100 * process / props.interval) + '%', 
                display: index === active ? 'block' : 'none' 
              }}>
              </div>
            </div>
          </div>
        })
      }
    </div>
  </div>);
}
export default Carousel;