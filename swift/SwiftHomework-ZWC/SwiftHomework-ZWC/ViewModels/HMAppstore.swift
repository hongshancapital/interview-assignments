//
//  HMAppstore.swift
//  SwiftHomework-ZWC
//
//  Created by 油菜花 on 2022/2/16.
//

import SwiftUI

class HMAppstore: ObservableObject {
    
    enum NetWorkState {
        case failure
        case haveData
        case noData
    }
    
    var applicationListRawValue: [HMApplication] = [HMApplication]()
    
    private var currentIndexPage: Int
    private let pageSize: Int = 10
    @Published var netWorkState: NetWorkState = .noData
    @Published var isRequestCompleted: Bool = true
    var isHaveMoreData: Bool
    @Published var applicationList: [HMApplication] = [HMApplication]()
    
    init() {
        applicationList = [HMApplication]()
        applicationListRawValue = [HMApplication]()
        isHaveMoreData = true
        currentIndexPage = 1
        initializeApplicationList()
    }
}

// MARK: - API
extension HMAppstore {
    
    func initializeData() {
        initializeApplicationList()
    }
    
    func refrashApplicationList() {
        currentIndexPage = 1
        let fetchResult = getApplicationList(withPageIndex: currentIndexPage, andCount: pageSize)
        isHaveMoreData = true
        currentIndexPage = fetchResult.nextPageIndex ?? 1
        applicationList = fetchResult.applicationList
    }
    
    func fetchNextPageData() {
        guard isHaveMoreData else {
            return
        }
        let fetchResult = getApplicationList(withPageIndex: currentIndexPage, andCount: pageSize)
        isHaveMoreData = fetchResult.isHaveMoreData
        currentIndexPage = fetchResult.nextPageIndex ?? currentIndexPage
        applicationList.append(contentsOf: fetchResult.applicationList)
    }
    
    func collect(application app: HMApplication) {
        let appIndex = applicationList.firstIndex(where: {$0.id == app.id})
        let rawAppIndex = applicationListRawValue.firstIndex(where: {$0.id == app.id})
        guard let appIndex = appIndex else {
            return
        }
        guard let rawAppIndex = rawAppIndex else {
            return
        }
        applicationListRawValue[rawAppIndex].isCollected = !applicationListRawValue[rawAppIndex].isCollected
        applicationListRawValue[rawAppIndex].collectImageName = applicationListRawValue[rawAppIndex].isCollected ? "icon002" : "icon001"
        applicationList[appIndex].isCollected = !applicationList[appIndex].isCollected
        applicationList[appIndex].collectImageName = applicationList[appIndex].isCollected ? "icon002" : "icon001"
    }
    
    func getApplicationIndex(app: HMApplication) -> Int {
        applicationList.firstIndex(where: {$0.id == app.id}) ?? 0
    }
}

// MARK: - Network
private extension HMAppstore {
    
    func initializeApplicationList() {
        isRequestCompleted = false
        DispatchQueue.main.asyncAfter(deadline: DispatchTime.now()+1) {
            Network.shared.fetchApplicationList { [weak self] isSuccess, response, error in
                guard let self = self else {
                    return
                }
                self.isRequestCompleted = true
                guard isSuccess else {
                    print("\(error?.localizedDescription ?? "")")
                    self.netWorkState = .failure
                    return
                }
                
                guard let applicationList = response as? [HMApplication] else {
                    print("Model map error")
                    self.netWorkState = .noData
                    return
                }
                
                if applicationList.count > 0 {
                    self.netWorkState = .haveData
                } else {
                    self.netWorkState = .noData
                }
                self.applicationListRawValue = applicationList
                self.refrashApplicationList()
            }
        }
    }
    
}

// MARK: - Private methonds
extension HMAppstore {
    
    func getApplicationList(withPageIndex index:Int, andCount lenght: Int) -> (isHaveMoreData: Bool, nextPageIndex: Int?, applicationList: [HMApplication]) {
        
        let startIndex = (index - 1 < 0 ? 0 : index - 1) * lenght
        guard startIndex < applicationListRawValue.count else{
            return (false, nil, [HMApplication]())
        }
        var endIndex = startIndex + lenght - 1
        var isHaveMoreData: Bool = true
        var nextPageIndex: Int? = index
        
        if endIndex >= applicationListRawValue.count-1 {
            endIndex = applicationListRawValue.count - 1
            isHaveMoreData = false
            nextPageIndex = nil
        } else {
            nextPageIndex! += 1
        }
        
        let applicationList: [HMApplication] = Array(applicationListRawValue[startIndex...endIndex])
        return (isHaveMoreData, nextPageIndex, applicationList)
    }
}
