//
//  Sample.swift
//  scdt_interview
//
//  Created by Ray Tao on 2022/7/13.
//

import Foundation

#if DEBUG

extension AppListDataSource {
    static func sample() -> AppListDataSource {
        return FileHelper.loadBundledJSON(file: "appList")
    }
}

extension AppListViewModel {
    static var all: [AppListViewModel] = {
        AppListDataSource.sample().results.map { model in
            return AppListViewModel(appList: model)
        }
    }()
    
    static var firstPageSamples: [AppListViewModel] = {
        pageSamples(size: 10, index: 0)
    }()
    
    static func pageSamples(size: Int, index: Int) -> [AppListViewModel] {
        let dataSource = AppListDataSource.sample();
        let count = dataSource.results.count
        let startIndex = index * size
        let endIndex = min(count, (index + 1) * size)
        guard count > startIndex else {
            return []
        }
        
        return AppListDataSource.sample().results[startIndex..<endIndex].map { model in
            return AppListViewModel(appList: model)
        }
    }
}

#endif
