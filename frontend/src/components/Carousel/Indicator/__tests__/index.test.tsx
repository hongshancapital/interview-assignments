import { fireEvent, render } from '@testing-library/react';
import React from 'react';
import Indicator from '../index';

describe('render indicator', () => {
    test('test curIndex props', () => {
        const container = render(<Indicator curIndex={1} total={3} interval={1000} />).container;
        const items = container.getElementsByClassName('indicator-item');
        Array.from(items).forEach((el, i) => {
            const fillEle = el.getElementsByClassName('indicator-fill')[0];
            const styles = fillEle.getAttribute('style');
            if (i === 1) {
                expect(styles).toEqual('width: 100%; transition: width 1000ms linear;');
            } else {
                expect(styles).toBeNull();
            }
        })
    });

    test('test total props', () => {
        const container = render(<Indicator curIndex={1} total={3} interval={1000} />).container;
        const items = container.getElementsByClassName('indicator-item');
        Array.from(items).forEach((el, i) => {
            const index = el.getAttribute('data-index');
            expect(Number(index)).toEqual(i);
        })
    });

    test('test interval props', () => {
        [1000,2000,3000].forEach((interval)=>{
            const container = render(<Indicator curIndex={0} total={3} interval={interval} />).container;
            const item = container.getElementsByClassName('indicator-item')[0];
            const fillEle = item.getElementsByClassName('indicator-fill')[0];
            expect(fillEle.getAttribute('style')).toEqual(`width: 100%; transition: width ${interval}ms linear;`);
        })
    })

    test('test click event handle',()=>{
        const fn = jest.fn();
        const container = render(<Indicator curIndex={0} total={3} interval={1000} onChange={fn} />).container;
        const ele = container.getElementsByClassName('indicator-item')[1];
        fireEvent.click(ele);
        expect(fn).toHaveBeenCalledWith(`1`);
    });
});