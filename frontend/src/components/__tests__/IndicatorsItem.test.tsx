import React from "react"
import { render, screen, fireEvent } from '@testing-library/react'
import IndicatorsItem from "../IndicatorsItem"

describe('测试IndicatorsItem组件',()=>{
    it("测试结构", () => {
        const activeStyle = {
                width: `0px`
            }, 
            fn:any = jest.fn()
        render(<IndicatorsItem activeStyle={activeStyle} indicatorsClick={fn}  indicatorsMouseLeave={fn} role='indicators-item-test' />);
        expect(screen.getByRole('indicators-item-test').className).toBe('item');
        expect(screen.getByRole('indicators-item-test').childNodes.length).toEqual(2);
        expect(screen.getByRole('indicators-item-test').lastChild?.nodeName.toLocaleLowerCase()).toBe('div');
        expect(screen.getByRole('indicators-item-test').lastElementChild?.getAttributeNode('style')?.value.replace(/\s/g,'')).toBe('width:0px;');
    });
    it("测试事件", () => {
        const activeStyle = {
                width: `0px`
            }, 
            indicatorsClick:any = jest.fn(),
            indicatorsMouseLeave:any = jest.fn()

        render(<IndicatorsItem activeStyle={activeStyle} indicatorsClick={indicatorsClick}  indicatorsMouseLeave={indicatorsMouseLeave} role='indicators-item-test' />);
        fireEvent.click(screen.getByRole('indicators-item-test'));
        fireEvent.mouseLeave(screen.getByRole('indicators-item-test'));
        expect(indicatorsClick).toBeCalled();
        expect(indicatorsMouseLeave).toBeCalled();
    });
})