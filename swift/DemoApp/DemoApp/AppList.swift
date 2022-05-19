//
//  AppList.swift
//  DemoApp
//
//  Created by liang on 2022/5/18.
//

import SwiftUI

struct AppList: View {
    var dataMgr: DataManager
    var body: some View {
        List {
            ForEach(dataMgr.appList) { appModel in
                AppRow(appModel: appModel)
            }
        }
    }
}

struct AppList_Previews: PreviewProvider {
    static var previews: some View {
        AppList(dataMgr: DataManager(dataProvider: DataProvider()))
    }
}
