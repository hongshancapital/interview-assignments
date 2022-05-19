import * as React from 'react';
import './index.scss';

export default function Bullets({
  total,
  current,
  duration = 5,
}: {
  total: number;
  current: number;
  duration: number;
}) {
  return (
    <div className='Bullets'>
      {Array.from({ length: total }).map((v, i) => (
        <div className='Bullets-Item' key={i}>
          {i + 1 < current ? <div className='Bullets-Item-Done' /> : null}
          {i + 1 === current ? (
            <div
              className='Bullets-Item-Progress'
              style={{ animationDirection: `${duration}s` }}
            />
          ) : null}
        </div>
      ))}
    </div>
  );
}
