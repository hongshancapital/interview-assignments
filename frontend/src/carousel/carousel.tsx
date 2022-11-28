import { ReactNode, HTMLAttributes } from 'react';

export type Attributes = Pick<HTMLAttributes<HTMLDivElement>, 'className' | 'style'>

function Carousel(props: {
    children?: ReactNode,
    active?: number,
    sliderProps?: Attributes
} & Attributes) {
    const { children, active = 0, ...attrs } = props
    if (!children) {
        return <></>
    }
    return (
        <div className='react-assignment-carousel' {...attrs}>
            <div style={{ transform: `translateX(-${active * 100}%)` }}>
                {children}
            </div>
        </div>
    );
}

export { Carousel }
