declare type ReactFC<T,> = React.FC<{
    children?: React.ReactNode | React.ReactNode[],
} &T
> 
declare type ReactFCStyle<T,> = ReactFC<{
    style?: React.CSSProperties,
    className?: string,
}&T>
