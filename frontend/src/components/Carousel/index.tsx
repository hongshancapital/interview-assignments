import * as React from 'react';
import Bullets from './Bullets';
import './index.scss';

export interface DataField {
  title?: string | string[];
  img: string;
  subTitle?: string | string[];
  theme?: 'black' | 'transparent';
}

function Item({ title, subTitle, img, theme = 'transparent' }: DataField) {
  function renderText(text: string, className: string) {
    return (
      <div
        className={className}
        style={{ color: theme === 'black' ? 'white' : 'black' }}>
        {text}
      </div>
    );
  }

  function renderTitle() {
    if (!title) return null;
    if (Array.isArray(title)) {
      return title.map(t => renderText(t, 'carousel-title'));
    }
    return renderText(title, 'carousel-title');
  }

  function renderSubTitle() {
    if (!subTitle) return null;
    if (Array.isArray(subTitle)) {
      return subTitle.map(t => renderText(t, 'carousel-subtitle'));
    }
    return renderText(subTitle, 'carousel-subtitle');
  }

  return (
    <div className='carousel-item' style={{ background: theme }}>
      <div
        className='bg'
        style={{
          background: `url(${img})`,
          backgroundSize: 'cover',
          backgroundPosition: 'center',
        }}></div>
      <div className='slogon'>
        {renderTitle()}
        {renderSubTitle()}
      </div>
    </div>
  );
}

export default function Carousel({
  data,
  style,
  loop = true,
  duration = 5,
}: {
  data: DataField[];
  loop?: boolean;
  duration?: number;
  style?: React.CSSProperties;
}) {
  const [current, setCurrent] = React.useState<number>(1);
  const total = data.length;

  React.useEffect(() => {
    const timer = setInterval(() => {
      setCurrent(v => {
        if (v === total) return 1;
        return v + 1;
      });
    }, duration * 1000);
    return () => clearInterval(timer);
  }, []);

  return (
    <div className='carousel-wrapper' style={style}>
      <div
        className='carousel-inner'
        style={{
          width: `${total}00%`,
          transform: `translateX(-${current - 1}00vw)`,
          transition: 'all .5s linear',
        }}>
        {data.map((v, idx) => (
          <Item {...v} key={idx} />
        ))}
      </div>
      <Bullets total={total} current={current} duration={duration} />
    </div>
  );
}
