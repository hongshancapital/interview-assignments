import { render } from '@testing-library/react';
import React from 'react';
import CarouselItem, { CarouselItemProps } from '..';


describe('CarouselItem', () => {
    const config: CarouselItemProps = {
        title: 'xPhone',
        description: (<div role='document'>Lots to love. Less to spend.<br />Starting at $399</div>),
        img: 'http://abc.com/ico',
        style: {
            color: '#fff',
            backgroundColor: '#111',
            backgroundSize: '50% auto',
        },
        link: 'http://link.com/abc',
    };
    test('title, description and img props', () => {
        const { container, getByRole } = render(<CarouselItem {...config} />);
        const titleEle = container.getElementsByClassName('carousel-item-header')[0];
        const { innerHTML: titleText ,href} = titleEle.getElementsByTagName('a')[0];
        const desEle = getByRole('document');
        const wrapEle = container.getElementsByClassName('carousel-item')[0];
        const styles = wrapEle.getAttribute('style');
        expect(titleText).toEqual(config.title);
        expect(desEle).toBeInTheDocument();
        expect(styles).toEqual(`background-image: url(http://abc.com/ico); color: rgb(255, 255, 255); background-color: rgb(17, 17, 17); background-size: 50% auto;`)
        expect(href).toEqual(config.link);
    })
})