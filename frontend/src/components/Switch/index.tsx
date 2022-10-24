import React, { useEffect, useState } from 'react';
import Page, { PageProps } from '../Page';
import Progress from '../Progress';

import './index.css';

interface SwitchProps {
  pages: Omit<PageProps, 'index'>[];
}
const Switch: React.FC<SwitchProps> = ({ pages }) => {
  const [currentIndex, setCurrentIndex] = useState(1);
  const style = {
    left: `-${currentIndex-1}00%`
  };
  useEffect(() => {
    let timer = 0;
    const loop = () => {
      timer = (setTimeout as Window['setTimeout'])(() => {
        setCurrentIndex(index => {
          if (index >= pages.length) return 1;
          return ++index;
        });
        loop();
      }, 3000);
    };
    loop();
    return () => clearTimeout(timer);
  }, [pages]);
  if (!pages || !pages.length) return null;
  return (
    <>
      <div
        className='switch_container'
        style={{ width: `${pages.length}00%`, ...style }}
      >
        {pages.map((v , i) => <Page {...v} key={i} index={i} />)}
      </div>
      <Progress count={pages.length} index={currentIndex} />
    </>
  );
};

export default Switch;