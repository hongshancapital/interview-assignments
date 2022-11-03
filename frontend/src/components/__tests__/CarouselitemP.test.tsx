import React from "react"
import { render,screen } from '@testing-library/react'
import CarouselitemP from "../CarouselitemP"

it("测试CarouselitemP组件", () => {
    const htmlString = 'Lots to love,Less to spend.<br>Starting at $399.'
    render(<CarouselitemP className="title" htmlString={htmlString} role='carousel-item-p-test' />);
    expect(screen.getByRole('carousel-item-p-test').className).toBe('title');
    expect(screen.getByRole('carousel-item-p-test').innerHTML).toBe(htmlString);
});