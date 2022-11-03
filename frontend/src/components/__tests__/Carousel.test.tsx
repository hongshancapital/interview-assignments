import React from "react"
import { render, screen, fireEvent } from '@testing-library/react'
import { unmountComponentAtNode } from "react-dom";
import { act } from "react-dom/test-utils";
import Carousel from "../Carousel"

let container:any = null;
beforeEach(() => {
  container = document.createElement("div");
  document.body.appendChild(container);
});

afterEach(() => {
  unmountComponentAtNode(container);
  container.remove();
  container = null;
});
const items:Array<any> = [
    {title: 'xPhone', describe: 'Lots to love,Less to spend.<br/>Starting at $399.'},
    {title: 'Tablet', describe: 'Just the right amount of everything.'},
    {title: 'Buy a Tablet or xPhone for college.<br/>Get arPods.', describe: ''}
]
jest.useFakeTimers();
describe('测试Carousel组件',()=>{
    it("测试默认结构", () => {
        render(<Carousel 
            role='carousel-test' 
            items={items}
          />);
        expect(screen.getByRole('carousel-test').className).toBe('Carousel');
        expect(screen.getByRole('carousel-test').childNodes.length).toEqual(2);
        expect(screen.getByRole('carousel-test').firstChild?.nodeName.toLocaleLowerCase()).toBe('div');
        expect(screen.getByRole('carousel-test').firstElementChild?.className).toBe('wrap');
        expect(screen.getByRole('carousel-test').firstElementChild?.childNodes.length).toBe(3);
        expect(screen.getByRole('carousel-test').lastChild?.nodeName.toLocaleLowerCase()).toBe('div');
        expect(screen.getByRole('carousel-test').lastElementChild?.className).toBe('indicators');
        expect(screen.getByRole('carousel-test').lastElementChild?.childNodes.length).toBe(3);
        jest.advanceTimersByTime(4000);
        expect(screen.getByRole('carousel-test')
            .firstElementChild
            ?.getAttributeNode('style')
            ?.value.replace(/\s/g,'')
        )
        .toEqual(expect.stringContaining('width:300%;transform:translateX(-33'));
    });
    it("测试不显示indicators结构", () => {
        render(<Carousel 
            role='carousel-test' 
            items={items}
            showIndicators={false}
          />);
        expect(screen.getByRole('carousel-test').className).toBe('Carousel');
        expect(screen.getByRole('carousel-test').childNodes.length).toEqual(1);
        expect(screen.getByRole('carousel-test').firstChild?.nodeName.toLocaleLowerCase()).toBe('div');
        expect(screen.getByRole('carousel-test').firstElementChild?.className).toBe('wrap');
        expect(screen.getByRole('carousel-test').firstElementChild?.childNodes.length).toBe(3);
        expect(screen.getByRole('carousel-test').lastElementChild?.className).not.toBe('indicators');
    });
    it("测试autoPlay为false(不自动播放)结构", () => {
        render(<Carousel 
            role='carousel-test' 
            items={items}
            autoPlay={false}
          />);
          jest.advanceTimersByTime(4000);
          expect(screen.getByRole('carousel-test')
              .firstElementChild
              ?.getAttributeNode('style')
              ?.value.replace(/\s/g,'')
          )
          .toEqual(expect.stringContaining('width:300%;transform:translateX(0'));
    });
    it("测试indicators事件", () => {
        act(() => {
            render(<Carousel 
                role='carousel-test' 
                items={items}
                autoPlay={false}
                />,
                container
            );
        });
        const indicator:any = screen.getAllByRole('indicators-item')[1]

        fireEvent.click(indicator);
        jest.advanceTimersByTime(2000);
        expect(screen.getByRole('carousel-test')
            .firstElementChild
            ?.getAttributeNode('style')
            ?.value.replace(/\s/g,'')
        )
        .toEqual(expect.stringContaining('width:300%;transform:translateX(-33'));
        
        fireEvent.mouseLeave(indicator);
        jest.advanceTimersByTime(4000);
        expect(screen.getByRole('carousel-test')
            .firstElementChild
            ?.getAttributeNode('style')
            ?.value.replace(/\s/g,'')
        )
        .toEqual(expect.stringContaining('width:300%;transform:translateX(-33'));
        
    });
})