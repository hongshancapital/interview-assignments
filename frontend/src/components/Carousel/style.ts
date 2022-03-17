import styled, {keyframes} from 'styled-components';

const progress = keyframes`
    0% {
        width: 0%;
        opacity: 0;
    }
    100% {
        width: 100%;
        opacity: 1;
    }
`;

export const SlideContainer = styled.div`
    position: relative;
    box-sizing: border-box;
    width: 100vw;
    height: 500px;

    .slider-container {
        position: relative;
        width: 100%;
        height: 100%;
        overflow: hidden;
        
        .slider-nav {
            position: absolute;
            width: 100%;
            height: 100%;
        }
    }

    /* 自定义分页器的样式 */
    .swiper-pagination-customs {
        width: 30px;
        height: 4px;
        display: inline-block;
        background: rgb(169, 169, 169);
        opacity: 1;
        margin: 0 5px;
        position: relative;
        &.swiper-pagination-customs-active:after {
            content: "";
            background-color: blue;
            display: block;
            position: absolute;
            width: 0%;
            height: 100%;
            left: 0;
            top: 0;
            opacity: 0;
            animation: ${progress} 3s ease;
        }
    }
`;