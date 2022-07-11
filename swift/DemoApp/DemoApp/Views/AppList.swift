//
//  AppList.swift
//  DemoApp
//
//  Created by Gao on 2022/7/9.
//

import SwiftUI

struct AppList: View {
    @StateObject var vm: ViewModel = ViewModel()
    
    var body: some View {
            NavigationView {
                Group {
                    if let errorMsg = vm.errorMessage {
                        Text(errorMsg)
                    }else{
                        if vm.loadingState == .Loading{
                            ProgressView()
                        }else{
                            List {
                                ForEach(vm.data) { appModel in
                                    AppRow(appModel: appModel)
                                        .environmentObject(vm)
                                        .listRowBackground(EmptyView())
                                        .listRowSeparator(.hidden)
                                        .onAppear{
                                            vm.isLoadMore(app: appModel)
                                        }
                                    
                                }
                                LoadingView(loadingState: vm.loadingState)
                                    .listRowSeparator(.hidden)
                                    .listRowBackground(Color.clear)
                            }
                            .refreshable {
                                vm.refreshSubject.send()
                            }
                            .listStyle(.grouped)
                        }
                    }
                }
                .navigationTitle("APP")
            }
    }
}

    
struct AppList_Previews: PreviewProvider {
    static var previews: some View {
        AppList()
    }
}
