import Carousel from "./index";
import { render } from '@testing-library/react';

test('carousel display list item', () => {
  const data = ['你好1', '你好2', '你好3'];
  const { getByText } = render(
    <Carousel
      data={data}
      renderItem={(item) => <>{item.item}</>}
      keyExtractor={(_, index) => index}
    />
  );
  const item1 = getByText('你好1');
  expect(item1).toBeDefined();
});
