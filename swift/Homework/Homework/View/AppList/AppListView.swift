//
// Homework
// HomeView.swift
//
// Created by wuyikai on 2022/4/27.
// 
// 

import SwiftUI

struct AppListView: View {
    @StateObject private var appStore = AppStore()

    var body: some View {
        List {
            ForEach(appStore.apps, id: \.id) { appInfo in
                Section {
                    AppInfoRow(appInfo: appInfo)
                        .background(.white)
                }
                .listRowBackground(EmptyView().background(.white))
            }
            LoadingView(noMoreData: appStore.nomoreData)
                .listRowBackground(EmptyView())
                .offset(x: 0, y: -10)
                .onAppear {
                    Task {
                        try? await appStore.loadNextPage()
                    }
                }
        }
        .listStyle(.insetGrouped)
        .navigationTitle("App")
    }
}

struct AppListView_Previews: PreviewProvider {
    static var previews: some View {
        AppListView()
    }
}
