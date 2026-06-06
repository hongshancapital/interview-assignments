//
//  ListView.swift
//  AppList
//
//  Created by mengyun on 2022/3/18.
//

import SwiftUI

struct ListView: View {
    
    @StateObject var vm: ListViewModel
    
    var body: some View {
        NavigationView{
            Group {
                if vm.loadingState == .PreLoading {
                    ProgressView()
                } else if vm.loadingState == .LoadingFailed {
                    Text("Failed to load data")
                } else {
                    dataList
                }
            }
            .navigationTitle("App")
        }
        .onAppear { vm.subscriptionList() }
        .navigationViewStyle(StackNavigationViewStyle())
        
    }
    
    private var dataList: some View  {
        List {
            ForEach(Array(vm.datas.enumerated()), id: \.offset) { i, cellModel in
                let isFav = vm.favs["\(cellModel.trackId)"] ?? false
                ListCellView(index: i, cellData: cellModel, isFav: isFav)
                    .environmentObject(vm)
                    .listRowSeparator(.hidden)
                    .listRowBackground(Color.clear)
            }
            FootLoadingView(loadingState: vm.loadingState)
                .listRowSeparator(.hidden)
                .listRowBackground(Color.clear)
                .onAppear {
                    vm.loadMore()
                }
        }
        .listStyle(.grouped)
        .refreshable {
            vm.refreshDatas()
        }
    }
}

struct SwiftUITableView_Previews: PreviewProvider {
    static var previews: some View {
        let vm = ListViewModel()
        ListView(vm: vm)
    }
}
