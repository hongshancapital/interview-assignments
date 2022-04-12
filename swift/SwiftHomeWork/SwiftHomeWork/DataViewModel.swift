//
//  DataViewModel.swift
//  SwiftHomeWork
//
//  Created by apple on 2022/4/12.
//

import Foundation
import Combine

class DataViewModel: ObservableObject {
    
    @Published  var welcome : Welcome?
    
    let network = NetService()
    
    func request(page : Int) async throws {
        self.welcome = try await network.getData()
    }
    
}
