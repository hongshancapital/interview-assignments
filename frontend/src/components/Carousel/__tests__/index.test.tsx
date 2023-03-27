import React from "react";
import { render, fireEvent, screen } from '@testing-library/react';
import Carousel from "..";
import { PRE_CLS } from '../../constant';
import airport from '../../../assets/airpods.png'
import iphone from '../../../assets/iphone.png'
import tablet from '../../../assets/tablet.png'
import { act } from "react-dom/test-utils";

const prefixCls = PRE_CLS + "-carousel"

describe('<Carousel />', () => {
  it('Carousel renders', () => {
    // 参数
    const props = {
      data: [{ imgUrl: airport, clickUrl: "/", altInfo: "pic1" },
      { imgUrl: iphone, clickUrl: "/", altInfo: "pic2" },
      { imgUrl: tablet, clickUrl: "/", altInfo: "pic3" }],
      // afterClickFn:
    }
    const { baseElement } = render(<Carousel {...props} />);

    expect(screen.getByAltText("pic1")).toBeInTheDocument()
    expect(screen.getByAltText("pic2")).toBeInTheDocument()
    expect(screen.getAllByAltText("pic3")[0]).toBeInTheDocument()

    expect(screen.getByRole('test')).toHaveClass(`${prefixCls}-slide-item`)

    // screen.debug()

  });

  it('Carousel onclick', () => {
    const onClick = jest.fn();
    const props = {
      data: [{ imgUrl: airport, clickUrl: "/", altInfo: "pic1" },
      { imgUrl: tablet, clickUrl: "/", altInfo: "pic3" }],
      afterClickFn: (index: number) => {
        onClick(index)
      }
    }
    render(<Carousel {...props} />);

    act(() => {
      fireEvent.click(screen.getByRole("test"))
      expect(onClick).toBeCalled();
    })

  })
})

