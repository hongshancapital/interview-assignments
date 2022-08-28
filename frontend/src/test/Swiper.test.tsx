import { render, act } from "@testing-library/react";
import React from "react";
import ReactDOM from "react-dom";
import { Swiper } from "../components/Swiper";

jest.useFakeTimers()

it('test if the Swiper is working correctly', () => {
    const pages = [
        <div>000</div>,
        <div>111</div>,
        <div>222</div>
    ]
    const cur = render(<Swiper contents={pages} duration={3000} />)
    const textStyle = (cur.container.getElementsByClassName('swiper-content')[0] as HTMLDivElement).style.transform
    const textWidth = (cur.container.getElementsByClassName('swiper-content')[0] as HTMLDivElement).style.width
    expect(textStyle).toContain('translate3d(0px,0,0)')
    jest.advanceTimersByTime(3100)
    const textStyleNew = (cur.container?.getElementsByClassName('swiper-content')[0] as HTMLDivElement).style.transform
    expect(textStyleNew).toContain(`translate3d(-${textWidth},0,0)`)
});