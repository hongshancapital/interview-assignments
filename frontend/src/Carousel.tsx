import { useEffect, useState } from "react";
import './Carousel.css';
import iphone from './assets/iphone.png';
import tablet from './assets/tablet.png';
import airpods from './assets/airpods.png';

const INTERVAL_MS = 2000;

type CarouselItemProps = {
  index: number;
  left: string;
};

function CarouselItem(props: CarouselItemProps) {
  const colors = ['rgb(17, 17, 17)', 'rgb(250, 250, 250)', 'rgb(241, 241, 243)'];
  const intros = [
    (<div style={{ color: "white" }}>
      <p style={{ fontSize: "4rem", fontWeight: "bold" }}>xPhone</p>
      <p style={{ fontSize: "2rem" }}>Lots to love. Less to spend.</p>
      <p style={{ fontSize: "2rem" }}>Starting at $399</p>
    </div>),
    (<div>
      <p style={{ fontSize: "4rem", fontWeight: "bold" }}>Tablet</p>
      <p style={{ fontSize: "2rem" }}>Just the right amount of everything</p>
    </div>),
    (<div style={{ fontSize: "4rem", fontWeight: "bold" }}>
      <p>Buy a Tablet or xPhone for college.</p>
      <p>Get airPods.</p>
    </div>),
  ];
  const icons = [
    (<img src={iphone} style={{ height: "8rem" }} />),
    (<img src={tablet} style={{ height: "8rem" }} />),
    (<img src={airpods} style={{ height: "8rem" }} />),
  ];

  return <div className="carousel-item" style={{ backgroundColor: colors[props.index], left: props.left }}>
    <div className="intro" style={{ lineHeight: "0" }}>{intros[props.index]}</div>
    <div className="icon">{icons[props.index]}</div>
  </div>
}

type ProgressItemStatus = 'unset' | 'play' | 'end';

type ProgressItemProps = {
  status: ProgressItemStatus,
}

function ProgressItem(props: ProgressItemProps) {
  const [status, setStatus] = useState<ProgressItemStatus>('unset');

  useEffect(() => {
    if (props.status === 'play') {
      setStatus('unset');

      const timeout = setTimeout(() => {
        setStatus('play');
      }, 100);

      return () => clearTimeout(timeout);
    }
    else {
      setStatus(props.status);
    }
  }, [props.status]);

  let style = status === 'unset' ? {width: '0%'} : {width: '100%'};

  if (status === 'play') {
    style = {...style, ...{transition: `width ${INTERVAL_MS}ms`}};
  }

  return <div className="progress-item">
    <div className="progress-mask" style={style}></div>
  </div>
}

function Carousel() {
  const [index, setIndex] = useState<number>(0);
  const [reset, setReset] = useState<boolean>(false);

  useEffect(() => {
    const interval = setInterval(() => {
      setIndex((index + 1) % 3);
      setReset((index + 1) === 3);
    }, INTERVAL_MS * 2);
    return () => clearInterval(interval);
  });

  let carouselItems: any = [];
  for (let i = 0; i < 3; ++i) {
    carouselItems.push(<CarouselItem key={`carousel-item-${i}`} index={i} left={`${100 * i}%`} />);
  }
  let progressItems: any = [];
  for (let i = 0; i < 3; ++i) {
    let status: ProgressItemStatus = i < index ? 'end' : (i === index ? 'play' : 'unset');
    progressItems.push(<ProgressItem key={`progress-item-${i}`} status={status}/>)
  }

  return <div className="canvas" style={{ height: window.innerHeight }}>
    <div className="carousel-container" style={{ left: `-${index * 100}%`, transition: `left ${INTERVAL_MS}ms` }}>
      {carouselItems}
    </div>
    <div className="progress-area">
      <div className="progress-container">
        {progressItems}
      </div>
    </div>
  </div>
}

export default Carousel;