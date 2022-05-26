//
//  AppList.swift
//  DemoApp
//
//  Created by liang on 2022/5/18.
//

import SwiftUI

struct AppList: View {
    @StateObject var dataMgr = DataManagerV2()
    var body: some View {
        if dataMgr.appList.count == 0 {
            ProgressView()
                .onAppear(perform: loadMore)
        } else {
            NavigationView {
                List {
                    ForEach(dataMgr.appList) { appModel in
                        AppRow(appModel: appModel)
                            .listRowBackground(EmptyView())
                            .listRowSeparator(.hidden)
                    }
                    HStack{
                        Spacer()
                        if dataMgr.hasMore {
                            ProgressView()
                                .padding(.trailing, 5)
                        }
                        Text(dataMgr.hasMore ? "Loading..." : "No more data")
                            .foregroundColor(Color(uiColor: .placeholderText))
                            .onAppear(perform: loadMore)
                        Spacer()
                    }
                    .listRowBackground(EmptyView())
                    .listRowSeparator(.hidden)
                }
                .navigationTitle("APP")
                .refreshable {
                    dataMgr.fetch(true)
                }
                .listStyle(.plain)
                .background(Color(uiColor: .systemGroupedBackground))
            }
        }
    }
    
    func loadMore() {
        if dataMgr.hasMore {
            dataMgr.fetch(false)
        }
    }
}

struct AppList_Previews: PreviewProvider {
    static var previews: some View {
        AppList()
    }
}
