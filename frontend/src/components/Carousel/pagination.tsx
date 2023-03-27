import React from "react";
import { PRE_CLS } from "../constant"
import { PaginationPropsType } from "./PropTypes"

const prefixCls = PRE_CLS + "-carousel";

const Pagination = (props: PaginationPropsType) => {
    const {
        activeIndex,
        size,
        itemActivityStyle,
        pageItemStyle,
        etiming = 3000,
        onClickFn,
        ...resetProps
    } = props

    // let data = new Array(size).fill(0);
    // data.forEach((item, index) => item = index);

    const chooseIndex = (index: number) => {
        if (onClickFn) onClickFn(index);
    }

    let amnimationStyle = {
        animation: `fadenum ${etiming / 1000}s`
    }


    return <>
        <div className={`${prefixCls}-pagination`} {...resetProps}>
            {[...Array(size)].map((itemP, indexP) => {
                let isActiveStyle = indexP === activeIndex ? { ...itemActivityStyle, ...amnimationStyle } : {};
                return (
                    <div
                        className={`${prefixCls}-pagination-bullet`}
                        style={{ ...pageItemStyle }}
                        key={`${itemP}-${indexP}`}
                        onClick={() => chooseIndex(indexP)}
                    >
                        <div className={`${prefixCls}-pagination-bullet_active`} style={{ ...isActiveStyle }}></div>
                    </div>
                );
            })}
        </div>
    </>
}

export default Pagination;