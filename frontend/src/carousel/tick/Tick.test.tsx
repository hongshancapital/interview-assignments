import React from 'react';
import { render, screen} from '@testing-library/react';
import Tick from './Tick';

test('render a tick without props', () => {
    const tick = render(<Tick />);
    expect(tick.container.querySelectorAll('.Tick')).toHaveLength(1);
    expect(tick.container.querySelectorAll('.tick-bar')).toHaveLength(1);
    expect(tick.container.querySelector('.tick-bar')).toHaveStyle({transitionDuration: 'unset'});//?.getAttribute('style')).toEqual('transition-duration: unset;');
});

test('render a tick with props open-true and index-1 and parent.current-2 and parent.showDuration-5', () => {
    const {container} = render(<Tick open={true} index={1} parent={{current: 2, showDuration: 5}} />);
    expect(container.querySelectorAll('.Tick.active')).toHaveLength(0);
    expect(container.querySelector('.tick-bar')).toHaveStyle({transitionDuration: 'unset'});//?.getAttribute('style')).toEqual('transition-duration: unset;')
});


test('render a tick with props open-true and index-1 and parent.current-1 and parent.showDuration-5', () => {
    const {container} = render(<Tick open={true} index={1} parent={{current: 1, showDuration: 5}} />);
    expect(container.querySelectorAll('.Tick.active')).toHaveLength(1);
    expect(container.querySelector('.tick-bar')).toHaveStyle({transitionDuration: '5s'});//?.getAttribute('style')).toEqual('transition-duration: 5s;')
});

test('render a tick with props open-false and index-1 and parent.current-1 and parent.showDuration-5', () => {
    const {container} = render(<Tick open={false} index={1} parent={{current: 1, showDuration: 5}} />);
    expect(container.querySelectorAll('.Tick.active')).toHaveLength(0);
    expect(container.querySelector('.tick-bar')).toHaveStyle({transitionDuration: 'unset'});//?.getAttribute('style')).toEqual('transition-duration: unset;')
});