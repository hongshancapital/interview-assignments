import { useEffect, useMemo } from "react";
import _ from "lodash";
import createEmotion from "create-emotion";

type Factory = (emotion: any, props: any) => any;

let squence = 1;
const generateKey = () => {
    const cache = [];
    let n = squence;
    while (n > 0) {
        let m = n % 26;
        if (m === 0) m = 26;
        cache.unshift(String.fromCharCode(m % 97 + 96));
        n = (n - m) / 26;
    }
    const key = `styx-${cache.join("")}`;
    squence++;
    return key;
}

const createUseStyles = (factory: Factory) => {
    return (props?: object) => {
        const emotion = useMemo(() => createEmotion({ key: generateKey() }), []);
        const cache = useMemo<any>(() => ({}), []);

        useEffect(() => {
            return () => emotion?.flush();
        }, [emotion]);

        if (!cache.styles || !_.isEqual(cache.props, props)) {
            cache.props = props;
            cache.styles = factory(emotion, props);
        }

        return cache.styles;
    }
}

export default createUseStyles;
