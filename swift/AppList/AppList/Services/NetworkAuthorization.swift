//
//  NetworkAuthorization.swift
//  AppList
//
//  Created by jay on 2022/2/22.
//

import Foundation
import CoreTelephony

struct NetworkAuthorization {
    var enabled: Bool {
        let cellularData = CTCellularData()
        let state = cellularData.restrictedState
        switch state {
        case .notRestricted:
            return true
        default:
            return false
        }
    }
}
