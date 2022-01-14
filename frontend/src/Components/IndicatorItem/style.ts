import style, { css, keyframes } from 'styled-components';

const IndicatorFrame = keyframes`
    0% { width: 0;}
    100% { width: 100%;}
`;

const activeStyle = (duration: number) => css`
    animation: ${IndicatorFrame} ${duration + 'ms'} infinite;
`;

export const Item = style.span<{ active: boolean, duration: number }>`
    display: inline-block;
    position: relative;
    width: 50px;
    height: 3px;
    border-radius: 3px;
    background-color: #ccc;
    opacity: .8;
    margin-right: 5px;
    &::after {
        display: inline-block;
        content: '';
        position: absolute;
        left: 0;
        top: 0;
        bottom: 0;
        border-radius: 3px;
        background-color: white;
        width: 0;
        ${(props) => props.active && activeStyle(props.duration)};
    }
`;