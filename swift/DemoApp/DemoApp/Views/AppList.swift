//
//  AppList.swift
//  DemoApp
//
//  Created by Gao on 2022/7/9.
//

import SwiftUI

struct AppList: View {
    @StateObject var vm: ViewModel = ViewModel()
    @EnvironmentObject var modelData: ModelData
    var body: some View {
        if vm.appList.count == 0 {
            ProgressView()
                .onAppear(perform: loadData)
        }else{
            NavigationView {
                List {
                    ForEach(vm.appList) { appModel in
                        AppRow(appModel: appModel)
                            .listRowBackground(EmptyView())
                            .listRowSeparator(.hidden)
                        
                    }
                    HStack{
                        Spacer()
                        if !vm.hasNoMoreData {
                            ProgressView()
                                .padding(.trailing, 5)
                        }
                        Text(!vm.hasNoMoreData ? "Loading..." : "No more data")
                            .foregroundColor(Color(uiColor: .placeholderText))
                            .onAppear(perform: loadMore)
                        Spacer()
                    }
                    .listRowBackground(EmptyView())
                    .listRowSeparator(.hidden)
                }
                .navigationTitle("APP")
                .refreshable {
                    vm.isRefreshing = true;
                    vm.loadData()
                }
                .listStyle(.plain)
                .background(Color(uiColor: .systemGroupedBackground))
                }
            }
    }
    func loadData() {
        
    }
    func loadMore(){

    }

}
    
  


struct AppList_Previews: PreviewProvider {
    static var previews: some View {
        AppList().environmentObject(ModelData())
    }
}
