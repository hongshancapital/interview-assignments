//
//  AppListView.swift
//  AppList
//
//  Created by 黄伟 on 2022/6/9.
//

import SwiftUI

var favoritedAppIds: [Int] = []

struct AppListView: View {
    @ObservedObject var applistVM: AppListVM
    
    var body: some View {
        NavigationView {
            let count = applistVM.page * applistVM.pageCount
            List {
                ForEach(applistData.results.prefix(upTo: count > applistData.resultCount ? applistData.resultCount : count)) {appInfo in
                    Section {
                        let isFavorited = favoritedAppIds.contains(appInfo.id)
                        AppInfoRow(appInfoVM: AppInfoVM(appInfo: appInfo, isFavorited: isFavorited))
                            .listRowInsets(EdgeInsets(top: 10, leading: 15, bottom: 10, trailing: 20))
                    }
                }
                if count < applistData.resultCount {
                    Section(footer:
                                HStack(spacing: 5) {
                        ProgressView()
                        Text("Loading...").foregroundColor(Color.gray)
                    }
                    ) {}
                    .onAppear {
                        loadMore()
                    }
                    .frame(maxWidth: .infinity, alignment: .center)
                } else {
                    Section(footer: Text("No more data.").foregroundColor(Color.gray)
                    ) {}
                    .frame(maxWidth: .infinity, alignment: .center)
                }
            }
            .navigationTitle(Text("App"))
            .listStyle(.insetGrouped)
            .refreshable {
                refreshList()
            }
        }
    }
    
    private func refreshList() {
        applistVM.page = 1
    }
    
    private func loadMore() {
        DispatchQueue.main.asyncAfter(deadline: .now() + .seconds(1)) {
            applistVM.page += 1
        }
    }
}

struct AppListView_Previews: PreviewProvider {
    static var previews: some View {
        AppListView(applistVM: AppListVM())
    }
}
