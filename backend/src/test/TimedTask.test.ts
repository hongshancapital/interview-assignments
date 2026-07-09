import TimedTask from '../util/TimedTask';
import {describe, expect, test} from '@jest/globals';


jest.useFakeTimers();

describe('TimedTask', () => {
    afterEach(() => {
        jest.clearAllTimers();
    });

    describe('constructor', () => {
        it('should call callback immediately without startAt', () => {
            const callback = jest.fn();
            const task = new TimedTask({ callback, interval: 100 });
            expect(callback).toHaveBeenCalledTimes(1);
            expect(task.handle).not.toBeNull();
            expect(callback).toHaveBeenCalledTimes(1);
        });

        it('should call callback after startAt time', () => {
            const callback = jest.fn();
            const startAt = new Date(Date.now() + 1000);
            const task = new TimedTask({ callback, interval: 100, startAt });
            expect(callback).not.toHaveBeenCalled();
            expect(task.handle).not.toBeNull();
            expect(callback).toHaveBeenCalledTimes(0);
        });

        it('should throw error if count is not a positive integer', () => {
            const callback = jest.fn();
            expect(() => new TimedTask({ callback, interval: 100, count: -1 })).toThrowError('illegal count:-1');
            expect(() => new TimedTask({ callback, interval: 100, count: 0 })).toThrowError('illegal count:0');
            expect(() => new TimedTask({ callback, interval: 100, count: 1.5 })).toThrowError('illegal count:1.5');
        });

        it('should throw error if startAt is in the past', () => {
            const callback = jest.fn();
            const startAt = new Date(Date.now() - 1000);
            expect(() => new TimedTask({ callback, interval: 100, startAt })).toThrowError('illegal startAt:');
        });
    });

    describe('cancel', () => {
        it('should cancel task', () => {
            const callback = jest.fn();
            const task = new TimedTask({ callback, interval: 100 });
            task.cancel();
            expect(task.handle).toBeNull();
            expect(callback).toHaveBeenCalledTimes(1);
        });

        it('should do nothing if task is not scheduled', () => {
            const callback = jest.fn();
            const task = new TimedTask({ callback, interval: 100 });
            task.cancel();
            task.cancel();
            expect(task.handle).toBeNull();
            expect(callback).toHaveBeenCalledTimes(1);
        });
    });

    describe('isFinish', () => {
        it('should return true if count is reached', () => {
            const callback = jest.fn();
            const task = new TimedTask({callback, interval: 100, count: 3});
            expect(task.isFinish()).toBe(false);
            jest.advanceTimersByTime(100);
            expect(task.isFinish()).toBe(false);
            jest.advanceTimersByTime(100);
            expect(task.isFinish()).toBe(true);
            jest.advanceTimersByTime(100);
            expect(task.isFinish()).toBe(true);
        });

        it('should return false if count is not reached', () => {
            const callback = jest.fn();
            const task = new TimedTask({callback, interval: 100, count: 3});
            expect(task.isFinish()).toBe(false);
            jest.advanceTimersByTime(100);
            expect(task.isFinish()).toBe(false);
        });
    });
});

