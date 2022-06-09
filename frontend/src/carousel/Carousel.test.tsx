import React from 'react';
import { render, screen } from '@testing-library/react';
import Carousel from './Carousel';

test('render a Carousel without props', () => {
    const {container} = render(<Carousel/>);
    expect(container.querySelectorAll('.Carousel')).toHaveLength(1);
    expect(container.querySelectorAll('.page-box')).toHaveLength(1);
    expect(container.querySelectorAll('.tick-box')).toHaveLength(1);
    expect(container.querySelector('.page-box')).toHaveStyle({transitionDuration: 'unset', left: '0px'});
});

test('render a Carousel with props current-0 showDuration-5 switchDuration-0.5 open-true pageArray-[<div>111</div>,<div>222</div>]', () => {
    const {container} = render(<Carousel current={0} showDuration={5} switchDuration={0.5} open={true}>{[<div>111</div>,<div>222</div>]}</Carousel>);
    expect(container.querySelectorAll('.Tick')).toHaveLength(2);
    expect(container.querySelectorAll('.page')).toHaveLength(2);
});

test('render a Carousel with props current-0 showDuration-5 switchDuration-0.5 open-false pageArray-[<div>111</div>,<div>222</div>]', () => {
    const {container} = render(<Carousel current={0} showDuration={5} switchDuration={0.5} open={false}>{[<div>111</div>,<div>222</div>]}</Carousel>);
    expect(container.querySelectorAll('.Tick.active')).toHaveLength(0);
    expect(container.querySelectorAll('.page')).toHaveLength(2);
    expect(container.querySelectorAll('.page-box.switching')).toHaveLength(0);
    screen.debug();
});