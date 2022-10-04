//
//  ConsoleLog.swift
//  GCore
//
//  Created by lizhao on 2022/9/20.
//

import UIKit

public class ConsoleLog {

    /// 当前打印等级
    private static let logHistory = NSMutableArray(capacity: 100)
    private static let dateFormatter = DateFormatter().then({ $0.dateFormat = "yyyy-MM-dd HH:mm:ss" })

    #if DEBUG
    private static var level = Level.verbose
    #endif
    public static var logHistoryLevel = Level.error

    public enum Level: UInt8, CustomDebugStringConvertible, Comparable, CaseIterable {

        case verbose = 0
        case debug = 1
        case info = 2
        case warning = 3
        case error = 4
        case never = 255

        public var title: String {
            switch self {
            case .never:
                return "never"
            case .verbose:
                return "verbose"
            case .debug:
                return "debug"
            case .info:
                return "info"
            case .warning:
                return "warning"
            case .error:
                return "error"
            }
        }

        public var debugDescription: String {
            switch self {
            case .never:
                return ""
            case .verbose:
                return " 📗 [Verbose]"
            case .debug:
                return " 📗 [Debug]"
            case .info:
                return " 📘 [Info]"
            case .warning:
                return " 📙 [Warning]"
            case .error:
                return " 📕 [Error]"
            }
        }

        public static func <(lhs: Level, rhs: Level) -> Bool {
            return lhs.rawValue < rhs.rawValue
        }

        public static func <=(lhs: Level, rhs: Level) -> Bool {
            return lhs.rawValue <= rhs.rawValue
        }

        public static func >=(lhs: Level, rhs: Level) -> Bool {
            return lhs.rawValue >= rhs.rawValue
        }

        public static func >(lhs: Level, rhs: Level) -> Bool {
            return lhs.rawValue > rhs.rawValue
        }

    }

    #if DEBUG
    private static func canLog(level: Level) -> Bool {
        return level >= ConsoleLog.level
    }
    #endif

    private class func timestamp() -> String {
        return dateFormatter.string(from: Date())
    }

    private class func threadName() -> String {
        if Thread.isMainThread {
            return ""
        } else {
            if let threadName = Thread.current.name, !threadName.isEmpty {
                return threadName
            } else {
                return String(format: "%p", Thread.current)
            }
        }
    }

    /// 打印一些不重要的东西（最低优先级）
    public class func verbose(_ message: @autoclosure () -> Any,
                              context: @autoclosure () -> Any? = nil,
                              _ file: String = #file,
                              _ function: String = #function,
                              line: Int = #line) {
        #if DEBUG
        custom(level: .verbose, message: message(), context: context(), file: file, function: function, line: line)
        #endif
    }

    /// 打印调试期间有用的东西（低优先级）
    public class func debug(_ message: @autoclosure () -> Any,
                            context: @autoclosure () -> Any? = nil,
                            _ file: String = #file,
                            _ function: String = #function,
                            line: Int = #line) {
        #if DEBUG
        custom(level: .debug, message: message(), context: context(), file: file, function: function, line: line)
        #endif
    }

    /// 打印普通数据但不是问题或错误的东西（普通优先级）
    public class func info(_ message: @autoclosure () -> Any,
                           context: @autoclosure () -> Any? = nil,
                           _ file: String = #file,
                           _ function: String = #function,
                           line: Int = #line) {
        #if DEBUG
        custom(level: .info, message: message(), context: context(), file: file, function: function, line: line)
        #endif
    }

    /// 打印警告（高优先级）
    public class func warning(_ message: @autoclosure () -> Any,
                              context: @autoclosure () -> Any? = nil,
                              _ file: String = #file,
                              _ function: String = #function,
                              line: Int = #line) {
        #if DEBUG
        custom(level: .warning, message: message(), context: context(), file: file, function: function, line: line)
        #endif
    }

    /// 打印错误（最高优先级）
    public class func error(_ message: @autoclosure () -> Any,
                            context: @autoclosure () -> Any? = nil,
                            _ file: String = #file,
                            _ function: String = #function,
                            line: Int = #line) {
        custom(level: .error, message: message(), context: context(), file: file, function: function, line: line)
    }

    /// 自定义打印
    public class func custom(level: Level,
                             message: @autoclosure () -> Any,
                             context: @autoclosure () -> Any? = nil,
                             file: String = #file,
                             function: String = #function,
                             line: Int = #line) {
        #if DEBUG
        guard ConsoleLog.canLog(level: level) else {
            return
        }
        #endif

        let log = "\(level.debugDescription) \((file as NSString).lastPathComponent):\(line).\(function)\n\t\(message()) \(String(describing: context() ?? ""))"
        #if DEBUG
        print("\(log)\n")
        #endif
        if level >= ConsoleLog.logHistoryLevel {
            if ConsoleLog.logHistory.count > 20 {
                ConsoleLog.logHistory.removeObjects(in: NSRange(location: 0, length: ConsoleLog.logHistory.count - 20))
            }
            ConsoleLog.logHistory.add(log)
        }
    }

    public class func exportLogHistory() -> String {
        var log = ""
        for item in ConsoleLog.logHistory {
            guard let str = item as? String else {
                continue
            }
            log = str + "\n\n\n---------\n\n\n" + log
        }
        return log
    }
}
