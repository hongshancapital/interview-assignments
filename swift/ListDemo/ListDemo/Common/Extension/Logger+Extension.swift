//
//  Logger+Extension.swift
//  ListDemo
//
//  Created by kent.sun on 2023/2/2.
//

import Foundation
import os.log

extension Logger {
    private static var subsystem = Bundle.main.bundleIdentifier!
    
    static let network = Logger(subsystem: subsystem, category: "network")
    static let ui = Logger(subsystem: subsystem, category: "ui")
}
