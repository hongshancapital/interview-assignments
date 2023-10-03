// lcov-report：
// 
// All files                |     100 |      100 |     100 |     100 | 
//  src                     |     100 |      100 |     100 |     100 | 
//   App.tsx                |     100 |      100 |     100 |     100 | 
//  src/components/Carousel |     100 |      100 |     100 |     100 | 
//   Carousel.tsx           |     100 |      100 |     100 |     100 | 
// -------------------------|---------|----------|---------|---------|-------------------
import { render, act, fireEvent } from '@testing-library/react';
import Carousel from './Carousel';

describe('Carousel', () => {
    // 无数据渲染
    test('loads and displays carousel without data', () => {
        const { container } = render(<Carousel carouselList={ [] } height={ 800 } width={ 800 }></Carousel>);
    
        // 轮播图组和滚动条是否正常渲染
        expect(container.querySelectorAll('.carousel-image').length).toBe(0);
        expect(container.querySelectorAll('.carousel-bar-item-out').length).toBe(0);
    });

    // 有数据渲染
    test('loads and displays carousel with data', () => {
        const carouselList = [
            { title: ['xPhone'], description: ['Lots to love. Less to spend.', 'Starting at $399.'], imageURL: 'image0' },
            { title: ['Tablet'], description: ['Just the right amount of evething.'], imageURL: 'image1' },
            { title: ['Buy a Tablet or xPhone for college.', 'Get arPods.'], description: [], imageURL: 'image2' }
        ];
        const { container } = render(<Carousel carouselList={ carouselList } height={ 800 } width={ 800 }></Carousel>);
    
        // 轮播图组和滚动条是否正常渲染
        expect(container.querySelector('.carousel-images')).toBeInTheDocument();
        expect(container.querySelector('.carousel-bar')).toBeInTheDocument();
    
        // 图组和滚动单元的数目是否一致
        const carouselImages = container.querySelectorAll('.carousel-image');
        expect(container.querySelectorAll('.carousel-bar-item-out').length).toBe(carouselImages.length);
    });
    
    // 交互
    test('carousel start after interval time', () => {
        jest.useFakeTimers();
        const carouselList = [
            { title: ['xPhone'], description: ['Lots to love. Less to spend.', 'Starting at $399.'], imageURL: 'image0' },
            { title: ['Tablet'], description: ['Just the right amount of evething.'], imageURL: 'image1' },
            { title: ['Buy a Tablet or xPhone for college.', 'Get arPods.'], description: [], imageURL: 'image2' }
        ];
        const intervalTime = 3000;
        const { container } = render(<Carousel carouselList={ carouselList } height={ 800 } width={ 800 }></Carousel>);

        // 初始图片在第1张
        expect(container.querySelector(`img[src=${carouselList[0].imageURL}]`)?.getAttribute('style')?.indexOf(`-${0 * 100}%`)).not.toBe(-1);
        // 进度条第1个单元被激活
        expect(container.querySelectorAll('.carousel-bar-item-in')[0]?.getAttribute('style')?.indexOf('animation')).not.toBe(-1);

        // 延时3秒后
        act(() => { jest.advanceTimersByTime(intervalTime); })
        // 图片在第2张
        expect(container.querySelector(`img[src=${carouselList[0].imageURL}]`)?.getAttribute('style')?.indexOf(`-${1 * 100}%`)).not.toBe(-1);
        // 进度条第2个单元被激活
        expect(container.querySelectorAll('.carousel-bar-item-in')[1]?.getAttribute('style')?.indexOf('animation')).not.toBe(-1);

        // 延时3秒后
        act(() => { jest.advanceTimersByTime(intervalTime); })
        // 图片在第3张
        expect(container.querySelector(`img[src=${carouselList[0].imageURL}]`)?.getAttribute('style')?.indexOf(`-${2 * 100}%`)).not.toBe(-1);
        // 进度条第3个单元被激活
        expect(container.querySelectorAll('.carousel-bar-item-in')[2]?.getAttribute('style')?.indexOf('animation')).not.toBe(-1);

        // 延时3秒后
        act(() => { jest.advanceTimersByTime(intervalTime); })
        // 图片回到初始第1张，开始循环
        expect(container.querySelector(`img[src=${carouselList[0].imageURL}]`)?.getAttribute('style')?.indexOf(`-${0 * 100}%`)).not.toBe(-1);
        // 进度条第1个单元被激活，开始循环
        expect(container.querySelectorAll('.carousel-bar-item-in')[0]?.getAttribute('style')?.indexOf('animation')).not.toBe(-1);

        // 点击进度条第1个单元
        fireEvent.click(container.querySelectorAll('.carousel-bar-item-in')[0]);
        // 图片在第1张
        expect(container.querySelector(`img[src=${carouselList[0].imageURL}]`)?.getAttribute('style')?.indexOf(`-${0 * 100}%`)).not.toBe(-1);
        
        // 点击进度条第2个单元
        fireEvent.click(container.querySelectorAll('.carousel-bar-item-in')[1]);
        // 图片在第2张
        expect(container.querySelector(`img[src=${carouselList[0].imageURL}]`)?.getAttribute('style')?.indexOf(`-${1 * 100}%`)).not.toBe(-1);
        
        // 点击进度条第3个单元
        fireEvent.click(container.querySelectorAll('.carousel-bar-item-in')[2]);
        // 图片在第3张
        expect(container.querySelector(`img[src=${carouselList[0].imageURL}]`)?.getAttribute('style')?.indexOf(`-${2 * 100}%`)).not.toBe(-1);

        jest.useRealTimers();
    });
});