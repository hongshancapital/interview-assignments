import { ReactNode } from 'react';
import { useTimeout } from './useTimeout';
import { Attributes } from './carousel';

export type OnTimeout = () => void
export type OnClick = (i: number) => void

function Progress(props: {
    timeout: number,
    onTimeout?: OnTimeout,
}) {
    const { timeout, onTimeout } = props
    const progress = useTimeout(onTimeout, timeout)
    return (
        <div style={{ width: `${progress * 100}%`, height: '100%', background: 'white' }}></div>
    );
}

function Bar(props: {
    timeout?: number,
    onTimeout?: OnTimeout,
    onClick?: () => void
}) {
    const { timeout, onTimeout, onClick } = props
    return (
        <div onClick={onClick}>
            {timeout && <Progress timeout={timeout} onTimeout={onTimeout} />}
        </div>
    );
}


export function Progressbars(props: {
    length: number,
    active: number,
    timeout?: number,
    onTimeout?: OnTimeout,
    onClick?: OnClick
} & Attributes) {
    const { length, active, timeout, onTimeout, onClick, ...attrs } = props;
    const children: ReactNode[] = []
    for (let i = 0; i < length; i++) {
        children.push(<Bar
            key={i}
            onClick={() => onClick && onClick(i)}
            timeout={i === active ? timeout : undefined}
            onTimeout={onTimeout} />)
    }
    return (
        <div className='react-assignment-progressbars' {...attrs}>
            {children}
        </div>
    );
}
