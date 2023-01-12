import * as React from "react";
import clsx from 'clsx';
import styles from './Slide.module.scss';

export interface ISlideProps {
    children: React.ReactNode;
    className?: string;
}

export const Slide = React.forwardRef<HTMLDivElement, ISlideProps>(({children, className}, ref) => {
    return <div ref={ref} className={clsx(styles.root, className)}>
        {children}
    </div>;
});