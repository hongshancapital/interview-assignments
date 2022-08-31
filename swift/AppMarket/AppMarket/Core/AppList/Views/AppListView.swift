//
//  AppListView.swift
//  AppMarket
//
//  Created by xcz on 2022/8/25.
//

import SwiftUI

struct AppListView: View {
    
    @StateObject private var vm = AppListViewModel()
    
    var body: some View {

        if vm.isLoding && vm.appInfos.isEmpty {
            ProgressView()
        } else {
            List {
                
                ForEach (vm.appInfos, id: \.trackId) { appInfo in
                    AppListRowView(appInfo: appInfo)
                        .listRowSeparator(.hidden)
                        .listRowBackground(Color.clear)
                        .environmentObject(vm)
                }
                
                listFooterView
                    .listRowBackground(Color.clear)
                    .listRowSeparator(.hidden)
                    .onAppear{
                        Task {
                            await vm.fetchNextPageData()
                        }
                    }
                
            }
            .background(Color(uiColor: .secondarySystemBackground))
            .listStyle(.plain)
            .refreshable {
                await vm.fetchFirstPageData()
            }
        }
    }
    
}


extension AppListView {
    
    var listFooterView: some View {
        
        HStack(spacing: 5) {
            Spacer()
            if !vm.showNoMoreData {
                ProgressView()
            }
            Text(vm.showNoMoreData ? "No more data." : "Loading...")
                .foregroundColor(.secondary)
                .font(.callout)
            Spacer()
        }

    }
    
}


struct AppListView_Previews: PreviewProvider {
    static var previews: some View {
        NavigationView {
            AppListView()
                .navigationTitle("App")
        }
    }
}
