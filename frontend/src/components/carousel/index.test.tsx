import React from 'react';
import { render,act,fireEvent} from '@testing-library/react';
import Carousel from './index';
import { ICarouselInf } from "../../types/modelTypes";


function getData(): ICarouselInf[] {
  return [
    {
      id: 0,
      title: "xPhone",
      description: "Lots to love.Less to spend.\nStarting at $399.",
      imageUrl: '',
      link: '',
      color: '#fff'
    },
    {
      id: 1,
      title: "Tablet",
      description: "Just the right amount of everything",
      imageUrl: '',
      link: '',
      color: '#000'
    },
    {
      id: 2,
      title: "Buy a Tablet or xPhone for college.\nGet airPods.",
      description: '',
      imageUrl: '',
      link: '',
      color: '#000'
    }
  ]
}

test('carousel测试 - 结构', () => {

  const { getByText, getAllByRole } = render(<Carousel data={getData()} />);

  expect(getByText('xPhone')).toBeInTheDocument();
 
  expect(getAllByRole('heading').length).toEqual(3);

});

test('carousel测试 - 事件', () => {
  jest.useFakeTimers();

  const testCallback = {
    onChange: (data:ICarouselInf, index: number):void => {},
    onClick:  (data:ICarouselInf, index: number):void => {}
  };

  const onChange = jest.spyOn(testCallback, 'onChange');
  const onClick = jest.spyOn(testCallback, 'onClick');

  const { getByText } = render(<Carousel data={getData()} onChange={testCallback.onChange} onClick={testCallback.onClick} />)

  act(() => {
    jest.advanceTimersByTime(3000);
  })
  
  // 判断change事件是否执行
  expect(onChange).toHaveBeenCalled();


  // 触发点击事件
  fireEvent.click(getByText('xPhone'));
  
  expect(onClick).toHaveBeenCalled();

});

