import React, {FC, HTMLAttributes, ReactElement, useState} from 'react';
import s from './index.module.css';
import Page from './Page';
import Nav from './Nav';

interface TTCarousel extends FC<HTMLAttributes<HTMLDivElement>> {
    Page: typeof Page
}

const Carousel: TTCarousel = (props) => {
    const {className, children, ...rest} = props;
    const [activeIndex, setActiveIndex] = useState(0);

    const handleNavActiveChange = (index: number) => {
        setActiveIndex(index);
    }

    const pages = React.Children.map(children, (ele, index) => {
        if (process.env.NODE_ENV === 'development') {
            if (!React.isValidElement(ele)) {
                throw new Error(`Carousel: 'Carousel' can only include 'Carousel.Page'.`);
                // @ts-ignore
            } else if (ele.type.displayName !== Page.displayName) {
                throw new Error(`Carousel: 'Carousel' can only include 'Carousel.Page'.`);
            } else if (!ele.key) {
                throw new Error(`Carousel: 'Carousel.Page' must set 'key' prop.`);
            }
        }

        const style = {
            transform: `translate(${(index - activeIndex) * 100}%)`
        }

        if (!React.isValidElement(ele)) {
            return <Page key={index} style={style}>{ele}</Page>
        }

        return React.cloneElement(ele as ReactElement, {style})
    })

    const cls = `${s.carouse} ${className || ''}`;
    return (
        <div className={cls} {...rest}>
            {pages}
            <Nav count={pages?.length || 0}
                 defaultActiveIndex={activeIndex}
                 duration={5000}
                 onActiveChange={handleNavActiveChange}
            />
        </div>
    );
}

Carousel.Page = Page;

export default Carousel;

