//
//  DataViewModel.swift
//  SwiftHomeWork
//
//  Created by apple on 2022/4/12.
//

import Foundation
import Combine

@MainActor class DataViewModel: ObservableObject {
    
    @Published var apps = [Entity]()
    
    @Published var isEnd : Bool = false
    
    let service : AppService
    
    init(_ service: AppService) {
        self.service = service
    }
    
    func refresh() async throws {
        self.apps = try await service.refresh()
        self.isEnd = false
    }
    
    func loadMore() async throws {
        self.apps = try await service.loadMore()
        self.isEnd = true
    }
    
}
