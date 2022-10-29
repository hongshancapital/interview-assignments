import React from 'react';
import MultiLine from '../MultiLine';
import { render } from '@testing-library/react';

it('测试 MultiLine 组件', () => {
  const testText = 'Hello\nparksben.\nHere is a\nsentence.';

  const { container } = render(<MultiLine text={testText} />);

  // 渲染出的 <br/> 标签数量等于文本中的换行符数量
  expect(container.querySelectorAll('br').length).toEqual(
    testText.match(/\n/g)?.length || 0
  );
});
