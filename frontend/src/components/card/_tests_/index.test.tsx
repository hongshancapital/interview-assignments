import { render } from '@testing-library/react';
import Card, { CardTheme, cn } from '../index';
import iphoneUrl from '../../../assets/iphone.png';

describe('[card]组件名称：卡片', () => {
  test('[#1 - card]用例名称：正常渲染 renders card with title, subTitle, and imgUrl', () => {
    const title = 'Test Title';
    const subTitle = 'Test Subtitle';
    const imgUrl = iphoneUrl;
    const theme = CardTheme.Dark;
    const { container, getByText, getByAltText } = render(<Card title={title} subTitle={subTitle} imgUrl={imgUrl} theme={theme} />);
    const cardElement = container.querySelector(`.${cn('card')}`);
    const titleElement = getByText(title);
    const subTitleElement = getByText(subTitle);
    const imgElement = getByAltText('图片');
    expect(titleElement).toBeInTheDocument();
    expect(subTitleElement).toBeInTheDocument();
    expect(imgElement).toBeInTheDocument();
    expect(imgElement).toHaveAttribute('src', imgUrl);
    expect(cardElement).toHaveClass(cn('dark'));
  });

  test('[#2 - card]用例名称：无子主题subtitle', () => {
    const title = 'Test Title';
    const imgUrl = iphoneUrl;
    const { container, getByText } = render(<Card title={title} imgUrl={imgUrl} />);
    expect(container.firstElementChild).toBeTruthy();
    const titleElement = getByText(title);
    const subTitleElement = container.querySelector(`.${cn('subtitle')}`);
    expect(titleElement).toBeInTheDocument();
    expect(titleElement).toHaveClass(cn('title'))
    expect(subTitleElement).not.toBeInTheDocument();
  });

  test('[#3 - card]用例名称：默认主题 default theme', () => {
    const title = 'Card Title';
    const imgUrl = iphoneUrl;
    const { container } = render(<Card title={title} imgUrl={imgUrl} />);
    expect(container.firstElementChild).toBeTruthy();
    const cardElement = container.querySelector(`.${cn('card')}`);
    expect(cardElement).toHaveClass(cn('light'));
  });

  test('[#4 - card]用例名称：特定主题 specified theme', () => {
    const title = 'Card Title';
    const imgUrl = iphoneUrl;
    const { container } = render(<Card title={title} imgUrl={imgUrl} theme={CardTheme.Dark} />);
    expect(container.firstElementChild).toBeTruthy();
    const cardElement = container.querySelector(`.${cn('card')}`);
    expect(cardElement).toHaveClass(cn('dark'));
  });
});