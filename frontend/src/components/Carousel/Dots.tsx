import { FC } from 'react';

type DotsProps = {
  dotNumber: number;
  activedIndex: number;
  animationDuration: number;
};

const Dots: FC<DotsProps> = ({
  dotNumber,
  activedIndex,
  animationDuration,
}) => {
  return (
    <div className='dot-container'>
      {new Array(dotNumber).fill(true).map((_, index) => {
        return (
          <div className='dot-box' key={index}>
            <div
              style={{ animationDuration: `${animationDuration}s` }}
              className={`dot ${
                activedIndex === index ? 'actived' : ''
              }`}></div>
          </div>
        );
      })}
    </div>
  );
};

export default Dots;
