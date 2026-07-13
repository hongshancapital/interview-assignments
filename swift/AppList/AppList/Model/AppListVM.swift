//
//  AppListVM.swift
//  AppList
//
//  Created by 黄伟 on 2022/6/10.
//

import Foundation
import SwiftUI

class AppListVM: ObservableObject {
    var pageCount: Int = 30
    @Published var page: Int = 1
    
    init() {
        UITableView.appearance().sectionFooterHeight = 0
    }
}
