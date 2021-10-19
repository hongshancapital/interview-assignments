//
//  Debouncer.swift
//  SwiftUIDemo
//
//  Created by weizhao on 2021/10/17.
//  Copyright © 2021 weizhao. All rights reserved.
//

import Foundation

enum Debouncer {

    private static var scheduledTimers = [String: DispatchSourceTimer]()

    /// 函数防抖：仅当 interval 秒内无新的 action 传入才执行最后传入的 action，否则延后继续等待 interval 秒
    static func debounce(_ interval: Double, queue: DispatchQueue = .main, key: String? = nil, _ file: String = #file, _ line: Int = #line, action: @escaping () -> Void) {
        let key = key ?? "\(file)[\(line)]"
        scheduledTimers[key]?.cancel()

        let timer = DispatchSource.makeTimerSource(queue: queue)
        timer.schedule(deadline: .now() + interval, repeating: .infinity)
        timer.setEventHandler {
            action()
            timer.cancel()
            scheduledTimers.removeValue(forKey: key)
        }
        timer.resume()
        scheduledTimers[key] = timer
    }

}
