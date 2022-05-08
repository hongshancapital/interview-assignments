//
//  AppPagesListener.swift
//  SwiftUIJustPlay
//
//  Created by wangrenzhu2021 on 2022/5/9.
//

import SwiftUI
import SwiftUIFlux


class AppPagesListener {
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
    
    override func loadPage() {
        dispatch?(JustPlayActions.FetchAppStoreList(page:
                                                        PageEndPoint.pageNo(pageIndex: currentPage,
                                                                            pageSize: 20)))
    }
}
