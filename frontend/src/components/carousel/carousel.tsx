
import { FC, useState, useEffect } from 'react';
import  './carousel.css';
import iphone from '../../assets/iphone.png';
import tablet from '../../assets/tablet.png';
import airpods from '../../assets/airpods.png';

type List = {
  title: string | string[],
  paragraph?: string | string[],
  image: string,
  color: 'white' | 'black',
}

const list: List[] = [
  {
    title: 'xPhone',
    paragraph: ['Lots to love.Less to spend.', 'Starting at $399.'],
    image: iphone,
    color: 'white',
  },
  {
    title: 'Tablet',
    paragraph: 'Just the right amount of everything',
    image: tablet,
    color: 'black',
  },
  {
    title: ['Buy a Tablet or xPhone for college.', 'Get airPods'],
    image: airpods,
    color: 'black',
  },
];

const transitionMove = 'transform linear 0.2s';

const barWidth = 92;

const Carousel: FC = () => {
  const [posX, setPosX] = useState<number>(0);
  const [moveEffect, setMoveEffect] = useState<string>(transitionMove);
  const [num, setNum] = useState<number>(0);
  const progressbarRender = () => {
    if (num < barWidth) {
      setNum(num + 0.005);
    } else {
      if (posX < list.length - 1) { 
        setMoveEffect(transitionMove);
        setPosX(posX + 1);
      } else {
        setMoveEffect('none');
        setPosX(0);
      }
      setNum(0);
    }
  };
  const clickHandle = (i: number) => {
    setPosX(i);
    setNum(0);
  };
  useEffect(progressbarRender, [num, posX]);
  return (
    <div className='container'>
      <ul className='viewbox' style={{width: `${list.length * 100}vw`, transform: `translateX(-${posX * 100}vw)`, transition: moveEffect}}>
        {
          list.map(({ title, paragraph, image, color }) => (
            <li key={title.toString()} style={{ background: `url('${image}') no-repeat center/contain`}}>
              <div className='text-desc'>
                {
                  Array.isArray(title) ? (
                    title.map((t) => <h3 key={t.toString()} className={color}>{t}</h3>)
                  )
                  : (<h3 className={color}>{title}</h3>)
                }
                {
                  Array.isArray(paragraph) ? (
                    paragraph.map((p) => <p key={p.toString()} className={color}>{p}</p>)
                  )
                  : (<p className={color}>{paragraph}</p>)
                }
              </div>
            </li>
          ))
        }
      </ul>
      <ol className='indicator'>
        {
          list.map(({ title }, index) => (<li key={title.toString()} style={{width: `${barWidth}px`}} onClick={() => clickHandle(index)}><div className='bar' style={{width: index === posX ? `${num}px` : 0}} /></li>))
        }
      </ol>
    </div>
  )
}

export default Carousel;