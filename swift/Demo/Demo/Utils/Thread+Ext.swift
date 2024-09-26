//
//  Thread+Ext.swift
//  Demo
//
//  Created by Xiaoping Tang on 2023/4/9.
//

import Foundation
import UIKit
import SwiftUI

extension Thread {
    var isRunningTest: Bool {
        (self.threadDictionary.allKeys.compactMap({ return $0 as? String}).first { keyAsString in
            let array = keyAsString.split(separator: ".").map ({ $0.lowercased()})
            return array.contains("xctest")
        } != nil)
    }
}
