import React from 'react';
import renderer from 'react-test-renderer';
import SellPoster from './index';
import iphoneSrc from '../../assets/iphone.png';

// 纯展示组件，测试时检查样式
test('SellPoster 样式检查', () => {
  const tree = renderer
    .create(<SellPoster width={1600} height={900} mode='dark' title={'xPhone'} description={['Lots to love. Less to spend.', 'Starting at $399.']} imgSrc={iphoneSrc}></SellPoster>)
    .toJSON();
  expect(tree).toMatchSnapshot();
});