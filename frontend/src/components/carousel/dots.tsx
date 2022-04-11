import React from "react";

interface ICarouselDotsItemProps {
  onClick: (index: number) => void;
  activeSlick: number;
  dotsLength: number;
  interval: number;
}

export default function Dots(props: ICarouselDotsItemProps): JSX.Element {
  const { 
    onClick,
    activeSlick,
    dotsLength,
    interval
  } = props;

  function _onClick(index: number): void {
    onClick(index);
  }

  return <div className='carousel-dots-group'>
      {
        Array.from({ length: dotsLength }, (item, index) => {
          return (<div className='carousel-dots-item' key={index} onClick={_onClick.bind(null, index)}>
            <div 
              className={`carousel-dots-item-dot ${index === activeSlick ? 'active' : ''}`}
              style={{
                animation: `${index === activeSlick ? 'dot-active' : ''} ${interval}ms linear infinite`
              }}
              ></div>
          </div>)
        })
      }
    </div>
}
