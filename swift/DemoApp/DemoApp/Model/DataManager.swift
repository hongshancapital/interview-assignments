//
//  DataManager.swift
//  DemoApp
//
//  Created by liang on 2022/5/19.
//

import Foundation
import UIKit

class DataManager {
    var dataProvider: DataProvider
    var appList: [AppModel] = []
    
    init(dataProvider: DataProvider) {
        self.dataProvider = dataProvider
        appList = dataProvider.appList
    }
}
