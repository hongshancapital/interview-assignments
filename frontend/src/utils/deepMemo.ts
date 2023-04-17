import equal from 'fast-deep-equal';
import { memo } from 'react';

const deepMemo = <ComponentType extends React.ComponentType<any>>(
    Component: ComponentType
): ComponentType => {
    // the type casting is required to make typescript happy :)
    // see: https://github.com/DefinitelyTyped/DefinitelyTyped/issues/37087
    return memo(Component, equal) as any as ComponentType;
};

export default deepMemo;
