import Layer, { LayerProps } from "./Layer";
import { SlideProps } from "./Slide";
import ControllerLayer from './ControllerLayer';
import Processer from "./Processer";
import SideCtrl from "./SideCtrl";
import { useCarouselContext, LayerApi } from '../hooks/useCarouselContext';
import React, { useEffect, useReducer, useRef, useState } from "react";

export interface CarouselOptions {
    stayTime: number;
}

const defaultOptions: CarouselOptions = {
    stayTime: 5000,
};

export interface CarouselLayerOptions extends Omit<LayerProps, 'children'> {
    children?: SlideProps[];
}

export interface CarouselProps {
    options?: CarouselOptions;
    layers?: CarouselLayerOptions[];
    children?: JSX.Element | JSX.Element[];
};

// TODO options 参数待实现
export default function Carousel({ options = defaultOptions, layers, children }: CarouselProps) {
    const apis = useRef<LayerApi[]>([]);
    const expose = (layer: number, api: LayerApi): void => {
        apis.current[layer] = api;
    };
    const forEach = (callback: (api: LayerApi, layer: number) => void): void => {
        apis.current.forEach((api, i) => {
            callback(api, i);
        });
    };
    const find = (index: number): { layer: number, api: LayerApi; } => {
        return { layer: index, api: apis.current[index] };
    };
    const totalLength = useRef(0);
    const setTotalLength = (length: number) => {
        totalLength.current = length;
    };

    const contextRender = useCarouselContext({ forEach, find, expose, totalLength, setTotalLength });
    return contextRender(
        <div className='carousel'>
            {children}
            <ControllerLayer options={options} layer={Infinity} className="carousel-controller-layer">
                <div slide={0} className='side-ctrl'>
                    <SideCtrl side='left'></SideCtrl>
                    <SideCtrl side='right'></SideCtrl>
                </div>
                <div slide={0} className='bottom-ctrl'>
                    <Processer></Processer>
                </div>
            </ControllerLayer>
        </div>
    );
}