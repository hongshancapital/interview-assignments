import React from 'react';
import { render } from '@testing-library/react';
// import App from './App';
import Carousel from './Carousel-Page/components/Carousel';

describe('Swipper 组件测试', () => {
  const resourceList = [
    {
      style: { backgroundColor: 'aqua' },
      innerHTML: <span>please read readme.md--1</span>,
    },
    {
      style: { backgroundColor: 'rgb(47, 77, 57)' },
      innerHTML: <span>please read readme.md--2</span>,
    },
    {
      style: { backgroundColor: 'rgba(0, 0, 255, 0.387)' },
      innerHTML: <span>please read readme.md--3</span>,
    },
    {
      style: { backgroundColor: 'rgba(255, 0, 0, 0.425)' },
      innerHTML: <span>please read readme.md--4</span>,
    },
    {
      style: { backgroundColor: 'orange' },
      innerHTML: <span>please read readme.md--5</span>,
    },
  ];
  test('未正确传入 children，渲染失败', () => {
    const ins = render(
      <Carousel timeout={1000} direction='left' resourceList={resourceList}>123</Carousel>
    );
  
    const errorText = ins.getByText('未正确传入children');
    expect(errorText).toBeInTheDocument();
  });

  test('渲染正常', () => {
    const ins = render(
      <Carousel timeout={1000} direction='left' resourceList={resourceList}>
        {new Array(resourceList.length)
          .fill(void 0)
          .map((s) => Math.random())
          .map((s, i) => (
            <span className='slid' key={s}>
              {resourceList[i].innerHTML}
            </span>
          ))}
      </Carousel>
    );

    expect(ins).toBeDefined();
  });
});
