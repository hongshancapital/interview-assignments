import React, { useEffect, useState } from 'react';
type props = {
    speed:number
}
const Carousel: React.FC <props>= props => {
  const [index, setIndex] = useState(0);
  const [w, setW] = useState(0);
  

  const handleClick = () => {
    setIndex(index => index + 1);
  };
 const children = props.children||[]
 let l = children.length;
  const start = () => {
    let a = setInterval(() => {
      setW(w => {
        w = 1 + w;

        if (w >= 100) {
          handleClick();
          w = 0;
        }
        return w;
      });
    }, 20);
  };
  useEffect(() => {
    start();
  }, []);
  return (
    <>
      <div style={{ overflow: 'hidden', margin: 0, padding: 0 }}>
        <div
          style={{
            width: `${l*2400}px`,
            opacity: 1,
            transform: `translate3d(${-2400 * (index % 3)}px, 0px, 0px)`,
            transitionDuration: `${props.speed||1}s`,
            position: 'relative',
            top: 0,
            left: 0,
            display: 'block',
            marginLeft: 'auto',
            marginRight: 'auto',
          }}
        >
          {children.map((i, k) => (
            <div
              style={{
                float: 'left',
                height: ' 100%',
                minHeight: ' 1px',
                width: '2400px',
                
                lineHeight: ' 800px',

                padding: '2%',
                position: 'relative',
                textAlign: 'center',
              }}
              key={k}
            >
              {i}
            </div>
          ))}
        </div>
      </div>

      <div>
        <ul
          style={{
            position: 'absolute',

            display: 'block',
            width: '100%',
            padding: ' 0',
            margin: 0,
            listStyle: 'none',
            textAlign: 'center',
          }}
        >
          {children.map((i, k) => (
            <li
            key={k}
              style={{
                position: 'relative',
                display: 'inline-block',
                margin: '0 5px',
                padding: 0,
                width: '50px',
                height: '4px',
                backgroundColor: '#ccc',
                borderRadius:"100px",
                border: "0px solid #ccc",
              }}
            >
              <div
                style={{
                  width: `${index % 3 == k ? 50 * w * 0.01 : 0}px`,
                  height: '4px',
                  backgroundColor: '#777',
                  borderRadius:"100px",
                  border: "0px solid #ccc",
                }}
              ></div>
            </li>
          ))}
        </ul>
      </div>
    </>
  );
};

export default Carousel;
