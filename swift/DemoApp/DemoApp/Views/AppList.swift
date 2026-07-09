//
//  AppList.swift
//  DemoApp
//
//  Created by 王俊 on 2022/8/30.
//

import SwiftUI

struct AppList: View {
    @EnvironmentObject var modelData: ModelData
    
    var body: some View {
        List() {
            ForEach(modelData.appInfos, id: \.trackId) { appInfo in
                AppRow(appInfo: appInfo)
                    .listRowSeparator(.hidden)
                    .listRowBackground(bgGrayColor)
            }
            HStack() {
                Spacer()
                if modelData.allDataIsLoaded {
                    Text("No more data.")
                        .foregroundColor(.gray)
                } else {
                    ProgressView()
                    Text("Loading...")
                        .foregroundColor(.gray)
                }
                Spacer()
            }
            .listRowBackground(bgGrayColor)
            .onAppear {
                modelData.loadMoreDataIfNeeded()
            }
        }
        .refreshable {
            modelData.reloadData()
        }
        .listStyle(.plain)
        .background(bgGrayColor)
    }
}

struct AppList_Previews: PreviewProvider {
    static var previews: some View {
        AppList().environmentObject(ModelData())
    }
}
