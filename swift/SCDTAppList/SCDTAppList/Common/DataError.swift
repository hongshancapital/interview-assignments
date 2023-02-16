//
//  DataError.swift
//  SCDTAppList
//
//  Created by freeblow on 2023/2/14.
//

import Foundation
import SwiftUI

enum DataError: Error, Equatable{
    case loadData(msg: String)
    case other(msg: String)
    case none
    
    func msg()-> String{
        var ret = ""
        switch self{
        case .loadData(let msgl):
            ret = msgl
        case .other(msg: let msgo):
            ret = msgo
        case .none:
            ret = "data error"
        }
        return ret
    }
}
