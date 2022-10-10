//
//  AppPagesListener.swift
//  SwiftUIJustPlay
//
//  Created by wangrenzhu2021 on 2022/5/9.
//

import SwiftUI
import SwiftUIFlux


class AppPagesListener : ObservableObject {
    var currentPage: Int = 1 {
        didSet {
            loadPage()
        }
    }
    
    func loadPage() {
        
    }
}


class HomePageListener : AppPagesListener {
    var dispatch: DispatchFunction?
    let pageSize: Int = 20
    
    override func loadPage() {
        dispatch?(JustPlayActions.FetchAppStoreList(page:
                                                        PageEndPoint.pageNo(pageIndex: currentPage,
                                                                            pageSize: pageSize)))
    }
    
    func refresh() {
        self.currentPage = 0
        dispatch?(JustPlayActions.FetchAppStoreList(page:
                                                        PageEndPoint.pageNo(pageIndex: currentPage,
                                                                            pageSize: pageSize)))
    }
}
