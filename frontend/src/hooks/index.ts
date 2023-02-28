import { useState, useRef } from 'react';
import { useMemo } from 'react';

type noop = (this: any, ...args: any[]) => any;

type PickFunction<T extends noop> = (
    this: ThisParameterType<T>,
    ...args: Parameters<T>
) => ReturnType<T>;

export const TASK_STATUS = {
    INIT: '',
    PENDING: 'pending',
    DONE: 'done',
    CANCEL: 'cancel'
}

const isFunction = (value: unknown): value is Function => typeof value === 'function';

const isNumber = (value: unknown): value is number => typeof value === 'number';

export function useMemoizedFn<T extends noop>(fn: T) {

    if (!isFunction(fn)) {
        console.error(`useMemoizedFn expected parameter is a function, got ${typeof fn}`);
    }

    const fnRef = useRef<T>(fn);

    fnRef.current = useMemo(() => fn, [fn]);

    const memoizedFn = useRef<PickFunction<T>>();
    if (!memoizedFn.current) {
        memoizedFn.current = function (this, ...args) {
            return fnRef.current.apply(this, args);
        };
    }

    return memoizedFn.current as T;
}

export const useDelayTask = function (fn: () => void, delay: number) {
    if (!isFunction(fn)) {
        console.error('useDelayTask expected first parameter is a function')
    }

    if (!isNumber(delay)) {
        console.error('useDelayTask expected second parameter is a number')
    }

    const [taskStatus, setTaskStatus] = useState(TASK_STATUS.INIT);
    const taskId = useRef<NodeJS.Timeout | undefined>(undefined);

    // 取消延时任务
    const cancelDelayTask = useMemoizedFn(() => {
        if (taskId.current) {
            clearTimeout(taskId.current);
            taskId.current = undefined;
            setTaskStatus(TASK_STATUS.CANCEL)
        }
    })

    // 执行延时任务
    const runDelayTask = useMemoizedFn(() => {
        cancelDelayTask();
        setTaskStatus(TASK_STATUS.PENDING);
        taskId.current = setTimeout(() => {
            fn();
            setTaskStatus(TASK_STATUS.DONE)
            taskId.current = undefined;
        }, delay);
    });

    return {
        runDelayTask,
        cancelDelayTask,
        taskStatus
    }
}
