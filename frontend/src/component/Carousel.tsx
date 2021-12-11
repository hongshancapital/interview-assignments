import React from 'react';
import { Carousel as AntdCarousel } from 'antd';

interface InitProps {
    initData: Array<object>;
}

interface Item {
    title?: string | undefined;
    text?: string | undefined;
    color?: string | undefined;
    url?: string;
    id?: number;
}
const Carousel: React.FC<InitProps> = (props) => {
  const { initData } = props;
    return (
      <div>
        <AntdCarousel autoplay className="banner">
          {initData.map((item:Item,index:number)=>{
             return(<div key={item.id}><ul
                          style={{
                            background: `url("${item.url}") center center / cover no-repeat`,
                              height: '100vh',
                              color: '#fff',
                              textAlign: 'center',
                              fontSize: '22px',
                              display: 'flex',
                              flexDirection: 'column',
                              alignItems: 'center',
                              justifyContent: 'center',
                              margin: 0
                        }}>
                  <li><h1 style={{color: item.color}}>{item.title}</h1></li>
                  <li><p style={{color: item.color}}>{item.text}</p></li>
              </ul></div>)
          })}
        </AntdCarousel>
      </div>
    )
}

export default Carousel;