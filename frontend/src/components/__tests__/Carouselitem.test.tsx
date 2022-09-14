import React from "react"
import { render,screen } from '@testing-library/react'
import Carouselitem from "../Carouselitem"

it("测试Carouselitem组件", () => {
    const title = 'xPhone', describe = 'Lots to love,Less to spend.<br>Starting at $399.'
    render(<Carouselitem title={title} describe={describe} role='carousel-item-test' />);
    expect(screen.getByRole('carousel-item-test').className).toBe('item');
    expect(screen.getByRole('carousel-item-test').childNodes.length).toEqual(2);
    expect(screen.getByRole('carousel-item-test').lastChild?.nodeName.toLocaleLowerCase()).toBe('p');
});