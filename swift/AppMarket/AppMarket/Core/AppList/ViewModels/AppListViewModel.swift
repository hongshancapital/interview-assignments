//
//  AppListViewModel.swift
//  AppMarket
//
//  Created by xcz on 2022/8/25.
//

import Foundation

class AppListViewModel: ObservableObject {
    
    @Published var appInfos: [AppInfoModel] = []
    @Published var isLoding: Bool = false
    @Published var showNoMoreData: Bool = false
    
    private var currentPage = 0
    private var dataService = AppListDataService()
    
    init() {
        Task {
            await fetchFirstPageData()
        }
    }
    

    @MainActor
    func fetchFirstPageData() async {
        
        guard !isLoding else { return }
        
        isLoding = true
        let retunedAppinfos = await dataService.fetchAppInfos()
        currentPage = 0
        appInfos.removeAll()
        appInfos.append(contentsOf: retunedAppinfos)
        showNoMoreData = false
        isLoding = false
    }
    
    @MainActor
    func fetchNextPageData() async {
        
        guard !isLoding else { return }
        
        isLoding = true
        let dataArray = await dataService.fetchAppInfos(page: currentPage + 1)
        showNoMoreData = dataArray.count < dataService.pageCount
        appInfos.append(contentsOf: dataArray)
        currentPage += dataArray.isEmpty ? 0 : 1
        isLoding = false
        
    }
    
    func  updateUserCollectedApps(appInfo: AppInfoModel) {
        if let index = appInfos.firstIndex(where: { $0.trackId == appInfo.trackId }) {
            appInfos[index].isCollected.toggle()
            dataService.updateCollectedApps(appInfo: appInfo)
        }
    }
    
}
