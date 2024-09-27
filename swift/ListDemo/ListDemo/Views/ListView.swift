//
//  SwiftUIView.swift
//  ListDemo
//
//  Created by Chr1s on 2022/2/21.
//

import SwiftUI

struct ListView: View {
    
    @StateObject var vm: ListViewModel
    
    init(dataService: ApiProtocol) {
        _vm = StateObject(wrappedValue: ListViewModel(dataService: dataService))
    }
    
    var body: some View {
        NavigationView {
            Group {
                if let errorMsg = vm.errorMessage {
                    Text(errorMsg)
                } else {
                    if vm.isLoading == .Loading {
                        ProgressView()
                    } else {
                        showList()
                    }
                }
            }
            .navigationTitle("App")
        }
    }
}

extension ListView {
    func showList() -> some View {
        List {
            ForEach(Array(vm.datas.enumerated()), id: \.offset) { i, element in
                ListCellView(index: i, cell: element)
                    .environmentObject(vm)
                    .listRowSeparator(.hidden)
                    .listRowBackground(Color.clear)
                    .onAppear {
                        // 判断是否显示到最后的cell
                        vm.isLoadMore(cell: element)
                    }
            }
            LoadingIndicator(style: vm.isLoading)
                .listRowSeparator(.hidden)
                .listRowBackground(Color.clear)
        }
        .listStyle(.grouped)
        .refreshable {
            // 上拉刷新
            vm.refreshSubject.send()
        }
    }
}

struct SwiftUIView_Previews: PreviewProvider {
    static let s = ApiService()
    static var previews: some View {
        ListView(dataService: s)
    }
}
