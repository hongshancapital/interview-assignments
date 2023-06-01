import { ReactNode } from "react";
import './slide.css';

export const LastSlide = ({children}: {children: ReactNode}) => {
    return (<div className="last-slide">{children}</div>);
};