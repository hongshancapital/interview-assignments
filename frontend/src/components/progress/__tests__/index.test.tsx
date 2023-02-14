import React from 'react';
import { render, fireEvent } from '@testing-library/react';
import Progress from '../index';
import { IProgress } from '../interface';

const testProps: IProgress = {
    autoplay: true,
	active: true,
	onClick: jest.fn(),
}

describe('test Progress component', () => {
	it('测试组件是否正常渲染', () => {
		const wrapper = render(<Progress />)
		expect(wrapper).toBeDefined();
	})

	it('测试组件接收不同的 props，是否可以正常渲染:', () => {
		const wrapper = render(<Progress {...testProps} />)
		const elementWrapper = wrapper.baseElement;
		const element = elementWrapper.firstElementChild;
		expect(element).toBeInTheDocument();
		const progressElement = element?.getElementsByClassName('progress')[0];
		expect(progressElement?.childElementCount).toBe(1);
	})

	it('测试点击事件', () => {
		const wrapper = render(<Progress {...testProps} />)
		const elementWrapper = wrapper.baseElement;
		const element = elementWrapper.firstElementChild as HTMLButtonElement;
		const progressElement = element.getElementsByClassName('progress')[0];
		const progressBtn = progressElement.childNodes[0];
		expect(progressBtn).toHaveClass('progress-btn')
		// 模拟点击
		fireEvent.click(progressBtn)
		expect(testProps.onClick).toHaveBeenCalled()
	})
})

