//
//  Log.swift
//  AppCollections
//
//  Created by Guang Lei on 2022/3/12.
//

import Foundation

func log(_ level: Log.Level, message: String, component: Log.Component) {
    print("[Log] [\(level.flag)] [\(component.flag)]: \(message)")
}

class Log {
    
    enum Component {
        case network
    }
    
    enum Level {
        case info
        case error
    }
}

private extension Log.Component {
    var flag: String {
        switch self {
        case .network: return "Network"
        }
    }
}

private extension Log.Level {
    var flag: String {
        switch self {
        case .info: return "Info"
        case .error: return "Error"
        }
    }
}
