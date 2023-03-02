import { useState, useRef, useEffect } from 'react';
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
    const isPauseDelayTask = useRef(false); // 当前是否处于暂停执行延时任务状态

    /**
     * 执行延时任务
     */
    const runDelayTask = useMemoizedFn(() => {
        clearDelayTask();
        setTaskStatus(TASK_STATUS.PENDING);
        isPauseDelayTask.current = false;
        taskId.current = setTimeout(() => {
            // 如果任务没有暂停，则正常执行
            if (!isPauseDelayTask.current) {
                fn();
                setTaskStatus(TASK_STATUS.DONE)
            }
            taskId.current = undefined;
        }, delay);
    });

    /**
     * 清除延时任务
     */
    const clearDelayTask = useMemoizedFn(() => {
        if (taskId.current) {
            clearTimeout(taskId.current);
            taskId.current = undefined;
            setTaskStatus(TASK_STATUS.CANCEL)
        }
    });

    /**
     * 暂停任务
     */
    const pauseDelayTask = () => {
        isPauseDelayTask.current = true;
        setTaskStatus(TASK_STATUS.CANCEL);
    };

    /**
     * 恢复当前延时任务
     * 已经过期的任务无法恢复，如果有提供恢复任务失败的回调，则执行该回调
     * @param recoverDelayTaskFailCallback 恢复任务失败时的回调
     */
    const recoverDelayTask = (recoverDelayTaskFailCallback?: () => void) => {
        isPauseDelayTask.current = false;
        if (!taskId.current && isFunction(recoverDelayTaskFailCallback)) {
            recoverDelayTaskFailCallback();
        }
    };

    // 组件销毁时清理未执行的定时器任务
    useEffect(() => {
        return () => {
            if (taskId.current) {
                clearTimeout(taskId.current);
                taskId.current = undefined;
            }
        }
    }, []);

    return {
        runDelayTask,
        clearDelayTask,
        pauseDelayTask,
        recoverDelayTask,
        taskStatus,
    }
}
