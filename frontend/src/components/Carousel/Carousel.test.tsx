import { render, screen, act } from '@testing-library/react';
import iphone from "../../assets/iphone.png";
import tablet from "../../assets/tablet.png";
import airpods from "../../assets/airpods.png";
import Carousel from './index';
import useList from "../../hooks/useList";

const banners = [
  {
    title: ["第一个"],
    desc: ["第一个desc1"],
    imgUrl: iphone,
    color: "#fff",
    bgColor: "#111",
  },
  {
    title: ["第二个"],
    desc: ["desc2"],
    imgUrl: tablet,
    bgColor: "#fafafa",
  },
  {
    title: ["第三个"],
    imgUrl: airpods,
    bgColor: "#f2f2f3",
  },
];

test("Carousel item title", () => {
  render(<Carousel className="carousel-root" list={banners} />);
  const title = screen.getAllByText(/第一个/i);
  expect(title.length).toBe(2);
});

test('useList hook', () => {
  let hookResult:any;

  const TestComponent = () => {
    hookResult = useList(banners)
    return null;
  };
  render(<TestComponent />);
  expect(hookResult[0]).toBe(0);
  
  act(() => { 
    hookResult[1](2)
  })
  expect(hookResult[0]).toBe(2);
  act(() => { 
    hookResult[1](0)
  })
  expect(hookResult[0]).toBe(0);
  
  act(() => { 
    hookResult[1]()
  })
  expect(hookResult[0]).toBe(1);
});