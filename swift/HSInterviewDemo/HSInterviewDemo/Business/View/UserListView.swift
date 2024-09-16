//
//  UserListView.swift
//  TestSwiftUI
//
//  Created by zhangshouyin on 2023/3/9.
//

import SwiftUI
import BBSwiftUIKit

struct UserListView: View {
    @ObservedObject var vm =  UserListViewModel()
    @State var hintStr = ""
    
    var body: some View {
        VStack {
            
            BBTableView(vm.modes) { item in
                UserCellView(data: item)
                    .background(RoundedRectangle(cornerRadius: 1).foregroundColor(Color(hex: 0xF2F2F2)))
            }
            .bb_reloadData($vm.isReloadData)
            .bb_pullDownToRefresh(isRefreshing: $vm.isRefreshing) {
                DispatchQueue.main.asyncAfter(deadline: .now() + refreshTime) {
                    vm.fetchData {
                        hintStr = ""
                        vm.isRefreshing = false
                    }
                }
            }
            .bb_pullUpToLoadMore(bottomSpace: 40) {
                if vm.isLoadingMore { return }
                if vm.modes.count > 50 {
                    hintStr = "No more data."
                    return
                }
                vm.isLoadingMore = true
                vm.fetchMoreData {
                    vm.isLoadingMore = false
                }
            }
            Text(hintStr)
            
        }.navigationBarTitle("App", displayMode: .large)
    }
}

struct UserListView_Previews: PreviewProvider {
    static var previews: some View {
        UserListView()
    }
}
