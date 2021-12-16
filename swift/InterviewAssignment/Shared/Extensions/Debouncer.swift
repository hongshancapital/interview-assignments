//
//  Debouncer.swift
//  InterviewAssignment (iOS)
//
//  Created by Vic Zhang on 2021/12/16.
//

import Foundation

enum Debouncer {
    private static var timers = [String: DispatchSourceTimer]()
    
    static func debounce(_ interval: Double, key: String?, queue: DispatchQueue = .main, action: @escaping () -> Void) {
        let key = key ?? UUID().uuidString
        
        timers[key]?.cancel()

        let timer = DispatchSource.makeTimerSource(queue: queue)
        timer.schedule(deadline: .now() + interval, repeating: .infinity)
        timer.setEventHandler {
            action()
            timer.cancel()
            timers.removeValue(forKey: key)
        }
        timer.resume()
        timers[key] = timer
    }

}
