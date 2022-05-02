//
//  RequestResult.swift
//  SwiftUIApp
//
//  Created by Univex on 2022/5/2.
//

import Foundation


public enum RequsetState{
    case success
    case fail
}

public struct RequsetResult {
    var data: Data
    var state: RequsetState
    var failMessage: String = ""
    
    init(state: RequsetState = .fail) {
        self.state = state
        self.data = Data()
    }
}
