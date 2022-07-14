//
//  AppListViewModel.swift
//  scdt_interview
//
//  Created by Ray Tao on 2022/7/13.
//

import Foundation

struct AppListViewModel: Identifiable, Codable {
    var id: String { appList.id }
    
    let appList: AppListModel
    var appIcon: String { appList.artworkUrl100 }
    var appName: String { appList.trackCensoredName }
    var appDes: String { appList.appListModelDescription }
    
    init(appList: AppListModel) {
        self.appList = appList
    }

}
