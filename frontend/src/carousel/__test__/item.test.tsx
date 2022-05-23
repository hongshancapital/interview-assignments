import { render } from "@testing-library/react";
import "@testing-library/jest-dom";
import React from "react";
import Item from "../Item";
import { CarouselItemProps } from '../types';
import imgIphone from '../../assets/iphone.png';

describe('测试Item组件', () => {
  const data: CarouselItemProps = {
    className: 'test',
    titles: ['hello', 'world'],
    describes: ['react', 'vue'],
    titleClassName: 'title',
    describeClassName: 'text',
    img: imgIphone,
  };

  describe('组件配置以上参数', () => {
    const { container } = render(<Item {...data} />);
    const item = container.getElementsByClassName('carousel-item')[0];
    const textWrap = item.getElementsByClassName('carousel-item-text')[0];
    const titleWrap = textWrap.children[0];
    const describeWrap = textWrap.children[1];
    const img = item.getElementsByTagName('img')[0];

    it('组件正常加载', () => {
      expect(item).toBeInTheDocument();
      expect(titleWrap).toBeInTheDocument();
      expect(describeWrap).toBeInTheDocument();
      expect(img).toBeInTheDocument();
    });

    it('组件扩展样式生效', () => {
      expect(item.className).toContain('test');
      expect(titleWrap.className).toContain('title');
      expect(describeWrap.className).toContain('text');
    });
    
    it('组件文本内容和传入数据一致', () => {
      expect(titleWrap.children.length).toBe(2);
      expect(describeWrap.children.length).toBe(2);
      expect(titleWrap.children[0].innerHTML).toBe('hello');
      expect(titleWrap.children[1].innerHTML).toBe('world');
      expect(describeWrap.children[0].innerHTML).toBe('react');
      expect(describeWrap.children[1].innerHTML).toBe('vue');
    });

    it('组件图片路径正常', () => {
      expect(img.src).toContain(imgIphone);
    });
  });

  it('组件不传img没有img节点', () => {
    const newData = { ...data };
    delete newData.img;
    const { container } = render(<Item {...newData} />);
    const item = container.getElementsByClassName('carousel-item')[0];
    const img = item.getElementsByTagName('img')[0];
    expect(img).toBeUndefined();
  });
});
