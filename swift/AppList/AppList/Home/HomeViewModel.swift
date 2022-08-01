//
//  HomeViewModel.swift
//  AppList
//
//  Created by 钟逊超 on 2022/8/1.
//

import Foundation
import SwiftUI

class HomeViewModel: ObservableObject {
    
    /// 获取原始数据
    private lazy var appSourceList: AppResultModel? = {
        return try? FileUtils.loadJson(fileName: "Apps")
    }()
    
    func printAppList() {
        print(appSourceList?.results.count)
    }
}
