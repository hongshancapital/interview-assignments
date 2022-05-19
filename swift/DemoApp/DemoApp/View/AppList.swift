//
//  AppList.swift
//  DemoApp
//
//  Created by liang on 2022/5/18.
//

import SwiftUI

struct AppList: View {
    @StateObject var dataMgr = DataManager(dataProvider: DataProvider())
    @State var isLoading = false
    var body: some View {
        if dataMgr.appList.count == 0 {
            ProgressView()
                .onAppear(perform: loadMore)
        } else {
            NavigationView {
                List {
                    ForEach(dataMgr.appList) { appModel in
                        AppRow(appModel: appModel)
                    }
                    HStack{
                        if dataMgr.hasMore {
                            ProgressView()
                        }
                        Text(dataMgr.hasMore ? "Loading..." : "No more data")
                            .onAppear(perform: loadMore)
                    }
                }
                .navigationTitle("APP")
                .refreshable {
                    isLoading = true
                    dataMgr.refresh {
                        isLoading = false
                    }
                }
            }
        }
    }
    
    func loadMore() {
        if !isLoading && dataMgr.hasMore {
            isLoading = true
            dataMgr.fetchMore {
                isLoading = false
                debugPrint("afdfs")
            }
        }
    }
}

struct AppList_Previews: PreviewProvider {
    static var previews: some View {
        AppList()
    }
}
