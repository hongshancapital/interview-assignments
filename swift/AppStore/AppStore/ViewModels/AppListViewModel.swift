//
//  AppListViewModel.swift
//  AppStore
//
//  Created by liuxing on 2022/4/11.
//

import Foundation

@MainActor class AppListViewModel: ObservableObject {
    @Published var appModelList: [AppModel] = [AppModel]()
    @Published var hasMoreData: Bool = false
    
}
