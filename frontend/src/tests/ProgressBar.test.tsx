import React from "react";
import { render, cleanup, act  } from "@testing-library/react";
import { replaceRaf } from "raf-stub";
import ProgressBar from "../components/ProgressBar";

const startTime = performance.now();
replaceRaf([], { startTime });

describe('ProgressBar Component', () => {
	const TIME = 2000;

  afterEach(() => {
    requestAnimationFrame.reset();
    cleanup();
  });

  test('测试当前的进度', () => {
		const { getByTestId } = render(<ProgressBar isCurrent={true} time={TIME} />);

		expect(getByTestId('nav-progress')).toHaveAttribute('style', 'width: 0%;');
		
		// 执行第2帧 每帧1000毫秒
		act(() => requestAnimationFrame.step(2,1000));
		
		// 进度为50%
		expect(getByTestId('nav-progress')).toHaveAttribute('style', 'width: 50%;');
	});

	test('测试其余的进度', () => {
		const { getByTestId } = render(<ProgressBar isCurrent={false} time={TIME} />);
		
		// 宽度为0%
		expect(getByTestId('nav-progress')).toHaveAttribute('style', 'width: 0%;');
	});

});