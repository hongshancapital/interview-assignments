import * as React from 'react';
import './index.scss';

export default function Bullets({
  total,
  current,
  duration = 5,
  setCurrent,
}: {
  total: number;
  current: number;
  duration: number;
  setCurrent: Function;
}) {
  const [width, setWidth] = React.useState<number>(30);
  const widthRef = React.useRef<number>(0);

  React.useEffect(() => {
    widthRef.current = width;
  }, [width]);

  function start() {
    const fps = 60;
    const step = 100 / fps / duration;
    if (widthRef.current <= 100) {
      setWidth(v => v + step);
    } else {
      setCurrent((v: number) => {
        if (v === total) return 1;
        return v + 1;
      });
      setWidth(() => 0);
    }
    window.requestAnimationFrame(start);
  }

  React.useEffect(() => {
    start();
  }, []);

  return (
    <div className='Bullets'>
      {Array.from({ length: total }).map((v, i) => (
        <div className='Bullets-Item' key={i} onClick={() => setCurrent(i + 1)}>
          {i + 1 < current ? <div className='Bullets-Item-Done' /> : null}
          {i + 1 === current ? (
            <div className='Bullets-Item-Done' style={{ width: `${width}%` }} />
          ) : null}
        </div>
      ))}
    </div>
  );
}
