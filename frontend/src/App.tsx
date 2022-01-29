import React from 'react';
import { Carousel } from './component';
import airports from './assets/airpods.png';
import iphone from './assets/iphone.png';
import tablet from './assets/tablet.png';
import './app.css';

interface BannerProps {
  src: string;
  title: string[];
  context?: string[];
  className?: string;
}

const list: BannerProps[] = [
  {
    src: iphone,
    title: ['xPhone'],
    context: ['Lots to love.Less to spend.', 'Starting at $399.'],
    className: 'iphone-banner'
  },
  {
    src: tablet,
    title: ['Tablet'],
    context: ['Just the right amount of everything'],
    className: 'tablet-banner'
  },
  {
    src: airports,
    title: ['Buy a Tablet or xPhone for college.', 'Get arPods.'],
    className: 'airports-banner',
  }
]

export function Banner(props: BannerProps) {
  const { src, className, title, context } = props;
  return (
    <div className={`banner${className ? ` ${className}` : ''}`}
         data-testid={src} style={{backgroundImage: `url(${src})`}}>
      <h1 className="banner-title">
        {title.map(v => <p key={v}>{v}</p>)}
      </h1>
      {context?.map(v => <p className="banner-context" key={v}>{v}</p>)}
    </div>
  )
}

export default function App() {
  return(
    <Carousel>
      {list.map(record => <Carousel.Item key={record.src}>
        <Banner {...record} />
      </Carousel.Item>)}
    </Carousel>
  )
}
